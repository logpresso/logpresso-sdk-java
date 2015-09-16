package com.logpresso.client.http.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

import org.araqne.codec.Base64;
import org.araqne.codec.FastEncodingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamingResultEncoder {
	private final Logger slog = LoggerFactory.getLogger(StreamingResultEncoder.class);
	private ThreadPoolExecutor executor;
	private int poolSize;

	public StreamingResultEncoder(String name, int poolSize) {
		if (poolSize < 1)
			throw new IllegalArgumentException("pool size should be positive");

		this.poolSize = poolSize;
		this.executor = new ThreadPoolExecutor(poolSize, poolSize, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
				poolSize), new NamedThreadFactory(name), new CallerRunsPolicy());

		slog.debug("logpresso: created encoder thread pool [{}]", poolSize);
	}

	public List<Map<String, Object>> encode(List<Object> rows, boolean useGzip) throws InterruptedException, ExecutionException {
		int flushSize = (rows.size() + poolSize) / poolSize;
		List<Map<String, Object>> chunks = new ArrayList<Map<String, Object>>();
		List<Future<Map<String, Object>>> futures = new ArrayList<Future<Map<String, Object>>>();

		int total = rows.size();
		int from = 0;
		boolean exit = false;
		while (!exit) {
			int to = from + flushSize;
			if (to >= total) {
				to = total;
				exit = true;
			}

			List<Object> slice = rows.subList(from, to);
			Future<Map<String, Object>> future = executor.submit(new Encoder(slice, useGzip));
			futures.add(future);

			from = to;
		}

		for (Future<Map<String, Object>> f : futures) {
			do {
				Map<String, Object> chunk = f.get();
				if (chunk != null) {
					chunks.add(chunk);
				}
			} while (!f.isDone());
		}

		return chunks;
	}

	public void close() {
		executor.shutdown();
		slog.debug("logpresso: closed encoder thread pool [{}]", poolSize);
	}

	private class Encoder extends FunctorBase<Map<String, Object>> {
		private List<Object> rows;
		private boolean useGzip;

		public Encoder(List<Object> rows, boolean useGzip) {
			super(slog);
			this.rows = rows;
			this.useGzip = useGzip;
		}

		@Override
		@SuppressWarnings("unchecked")
		protected Map<String, Object> callSafely() throws Exception {
			// row-oriented to column-oriented
			int len = rows.size();
			Map<String, Object[]> columns = new HashMap<String, Object[]>();

			int i = 0;
			for (Object o : rows) {
				Map<String, Object> rows = (Map<String, Object>) o;
				for (Entry<String, Object> e : rows.entrySet()) {
					String key = e.getKey();
					Object[] items = columns.get(key);
					if (items == null) {
						items = new Object[len];
						columns.put(key, items);
					}

					items[i] = e.getValue();
				}

				i++;
			}

			// encode and compress
			Map<String, Object> msg = new HashMap<String, Object>();

			FastEncodingRule enc = new FastEncodingRule();
			ByteBuffer bb = enc.encode(columns);

			ByteBuffer compressed = null;
			int compressedSize = 0;

			if (useGzip) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				GZIPOutputStream zos = null;

				try {
					zos = new GZIPOutputStream(bos);
					zos.write(bb.array());
					zos.finish();

					byte[] out = bos.toByteArray();
					compressed = ByteBuffer.wrap(out);
					compressedSize = out.length;
				} finally {
					if (zos != null)
						zos.close();
				}
			} else {
				Deflater c = new Deflater();
				try {
					c.setInput(bb.array(), 0, bb.array().length);
					c.finish();

					compressed = ByteBuffer.allocate(bb.array().length * 2);
					compressedSize = c.deflate(compressed.array());
					compressed = ByteBuffer.wrap(Arrays.copyOf(compressed.array(), compressedSize));
				} finally {
					c.end();
				}
			}

			msg.put("size", bb.array().length);
			msg.put("bin", new String(Base64.encode(compressed.array())));
			return msg;
		}
	}

	private class NamedThreadFactory implements ThreadFactory {
		private final String prefix;

		public NamedThreadFactory(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, prefix);
		}
	}

	private abstract class FunctorBase<T> implements Callable<T> {
		private final Logger logger;

		public FunctorBase(Logger logger) {
			this.logger = logger;
		}

		@Override
		public final T call() {
			try {
				return callSafely();
			} catch (Throwable t) {
				if (logger != null)
					logger.error("unexpected error while running Task", t);
				else {
					System.err.println("unexpected error while running Task");
					t.printStackTrace(System.err);
				}
				throw new IllegalStateException(t);
			}
		}

		protected abstract T callSafely() throws Exception;

	}

}
