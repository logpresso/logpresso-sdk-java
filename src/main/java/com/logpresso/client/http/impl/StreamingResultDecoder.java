package com.logpresso.client.http.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.araqne.codec.Base64;
import org.araqne.codec.EncodingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 쿼리 결과를 스트리밍 받을 때 병렬적으로 압축을 해제하고 아라크네 코덱으로 인코딩된 쿼리 결과 바이너리를 디코딩합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class StreamingResultDecoder {
	private final Logger slog = LoggerFactory.getLogger(StreamingResultDecoder.class);
	private ThreadPoolExecutor executor;

	public StreamingResultDecoder(String name, int poolSize) {
		executor = new ThreadPoolExecutor(poolSize, poolSize, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(poolSize),
				new NamedThreadFactory(name), new CallerRunsPolicy());
	}

	public List<Object> decode(List<Map<String, Object>> chunks) throws ExecutionException {

		List<Future<List<Object>>> futures = new ArrayList<Future<List<Object>>>();
		for (Map<String, Object> chunk : chunks) {
			Future<List<Object>> f = executor.submit(new Decoder(chunk));
			futures.add(f);
		}

		List<Object> result = new ArrayList<Object>();

		for (Future<List<Object>> f : futures) {
			do {
				try {
					result.addAll(f.get());
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
					throw e;
				}
			} while (!f.isDone());
		}

		return result;
	}

	public void close() {
		executor.shutdown();
	}

	private class Decoder extends FunctorBase<List<Object>> {
		private Map<String, Object> chunk;

		public Decoder(Map<String, Object> chunk) {
			super(slog);
			this.chunk = chunk;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected List<Object> callSafely() throws Exception {
			int originalSize = (Integer) chunk.get("size");
			byte[] buf = Base64.decode((String) chunk.get("bin"));

			byte[] output = new byte[originalSize];

			Inflater inflater = new Inflater();
			inflater.setInput(buf, 0, buf.length);
			try {
				inflater.inflate(output);
				inflater.reset();
			} catch (DataFormatException e) {
				throw new IllegalStateException(e);
			} finally {
				inflater.end();
			}

			Map<String, Object> m = (Map<String, Object>) EncodingRule.decode(ByteBuffer.wrap(output));

			if (m.isEmpty())
				return Arrays.asList();

			Object[] rows = null;
			for (String key : m.keySet()) {
				Object[] o = (Object[]) m.get(key);
				if (rows == null)
					rows = new Object[o.length];

				int i = 0;
				for (Object item : o) {
					Map<String, Object> row = (Map<String, Object>) rows[i];
					if (row == null) {
						row = new HashMap<String, Object>();
						rows[i] = row;
					}

					if (item != null)
						row.put(key, item);

					i++;
				}
			}

			return Arrays.asList(rows);
		}
	}

	public static class NamedThreadFactory implements ThreadFactory {
		private final String prefix;

		public NamedThreadFactory(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, prefix);
		}
	}

	public abstract class FunctorBase<T> implements Callable<T> {
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
