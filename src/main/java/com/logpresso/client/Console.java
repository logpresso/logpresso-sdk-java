/*
 * Copyright 2013 Eediom Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.logpresso.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import com.logpresso.client.http.WebSocketTransport;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * 커맨드라인을 통해 로그프레소 서버에 접속하여 명령을 실행할 수 있도록 지원합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Console {
	private BufferedReader br;
	private Logpresso client;
	private String host;
	private String loginName;
	private String password;

	/**
	 * 콘솔 클라이언트 진입점
	 */
	public static void main(String[] args) throws IOException {
		ConsoleAppender ca = new ConsoleAppender(new PatternLayout());
		ca.setThreshold(Level.INFO);
		org.apache.log4j.BasicConfigurator.configure(ca);

		Map<String, String> opts = getOpts(args);
		if (opts.containsKey("-e")) {
			oneShotQuery(opts);
			return;
		}

		new Console().run();

	}

	private static void oneShotQuery(Map<String, String> opts) throws IOException {
		CSVWriter csvWriter = null;
		try {
			Logpresso client = null;
			try {
				String query = null;
				String queryPath = opts.get("-f");
				if (queryPath != null) {
					File f = new File(queryPath);
					if (!f.exists()) {
						System.err.println("query file not found: " + f.getAbsolutePath());
						System.exit(-1);
					}

					if (!f.canRead()) {
						System.err.println("check query file permission: " + f.getAbsolutePath());
						System.exit(-1);
					}

					query = readQueryFile(f);
				}

				if (query == null)
					query = getOpt(opts, "-e", "Error: -e, query string is missing");

				String host = getOpt(opts, "-h", "Error: -h, host is required");
				String loginName = getOpt(opts, "-u", "Error: -u, login name is required");
				String password = getOpt(opts, "-p", "Error: -p, password is required");
				String port = getOpt(opts, "-P", "Error: -P, port is required");
				String cols = opts.get("-c");
				String useSsl = opts.get("-s");
				String skipCertCheck = opts.get("-S");

				String[] headers = null;
				String[] line = null;
				if (cols != null && !cols.trim().isEmpty()) {
					headers = cols.split(",");
					for (int i = 0; i < headers.length; i++)
						headers[i] = headers[i].trim();

					line = new String[headers.length];
				}

				if (useSsl != null) {
					if (skipCertCheck != null)
						client = new Logpresso(new WebSocketTransport(true, true));
					else
						client = new Logpresso(new WebSocketTransport(true));

				} else {
					if (skipCertCheck != null)
						throw new IllegalArgumentException("Error: -S, must be used with -s(SSL) option");
					else
						client = new Logpresso();
				}

				client.connect(host, Integer.valueOf(port), loginName, password);
				String lineEnd = System.getProperty("line.separator");
				csvWriter = new CSVWriter(new OutputStreamWriter(System.out), CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.DEFAULT_QUOTE_CHARACTER, lineEnd);

				Cursor cursor = client.query(query);
				while (cursor.hasNext()) {
					Tuple m = cursor.next();

					if (line == null)
						System.out.println(m);
					else {
						for (int i = 0; i < line.length; i++) {
							Object o = m.get(headers[i]);
							line[i] = o == null ? "" : o.toString();
						}

						csvWriter.writeNext(line);
					}
				}
			} finally {
				ensureClose(csvWriter);
				ensureClose(client);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	private static String readQueryFile(File f) {
		BufferedReader br = null;
		FileInputStream is = null;
		StringBuilder sb = new StringBuilder();
		try {
			is = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;

				if (!line.trim().startsWith("#"))
					sb.append(" " + line);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} finally {
			ensureClose(is);
		}
		return sb.toString();
	}

	private static void ensureClose(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
			}
		}
	}

	private static String getOpt(Map<String, String> opts, String key, String msg) {
		String val = opts.get(key);
		if (val == null)
			throw new IllegalArgumentException(msg);
		return val;
	}

	private static Map<String, String> getOpts(String[] args) {
		String name = null;
		String value = "";

		Map<String, String> opts = new HashMap<String, String>();
		for (String arg : args) {
			if (arg.startsWith("-")) {
				if (name != null) {
					opts.put(name, value);
					value = "";
				}

				name = arg;
			} else {
				value = arg;
			}
		}

		if (name != null)
			opts.put(name, value);
		return opts;
	}

	/**
	 * 콘솔 명령 루프를 실행합니다.
	 */
	public void run() throws IOException {
		w("Logpresso Console 1.0.0");
		w("Type \"help\" for more information");

		br = new BufferedReader(new InputStreamReader(System.in));

		try {
			while (true) {
				System.out.print(getPrompt());
				String line = br.readLine();
				if (line == null)
					break;

				if (line.trim().isEmpty())
					continue;

				String[] tokens = tokenize(line);
				if (tokens.length == 0)
					continue;

				String cmd = tokens[0].trim();
				if (cmd.equals("quit") || cmd.equals("exit"))
					break;
				else if (cmd.equals("help"))
					help();
				else if (cmd.equals("connect"))
					connect(tokens);
				else if (cmd.equals("disconnect"))
					disconnect();
				else if (cmd.equals("query"))
					query(tokens);
				else if (cmd.equals("create_query"))
					createQuery(tokens);
				else if (cmd.equals("start_query"))
					startQuery(tokens);
				else if (cmd.equals("stop_query"))
					stopQuery(tokens);
				else if (cmd.equals("remove_query"))
					removeQuery(tokens);
				else if (cmd.equals("fetch"))
					fetch(tokens);
				else if (cmd.equals("queries"))
					queries();
				else if (cmd.equals("query_status"))
					queryStatus(tokens);
				else if (cmd.equals("create_table"))
					createTable(tokens);
				else if (cmd.equals("drop_table"))
					dropTable(tokens);
				else if (cmd.equals("tables"))
					listTables();
				else if (cmd.equals("table"))
					manageTable(tokens);
				else if (cmd.equals("loggers"))
					listLoggers();
				else if (cmd.equals("logger_factories"))
					listLoggerFactories();
				else if (cmd.equals("parser_factories"))
					listParserFactories();
				else if (cmd.equals("parser_factory"))
					getParserFactoryInfo(tokens);
				else if (cmd.equals("parsers"))
					listParsers();
				else if (cmd.equals("transformer_factories"))
					listTransformerFactories();
				else if (cmd.equals("transformer_factory"))
					getTransformerFactoryInfo(tokens);
				else if (cmd.equals("transformers"))
					listTransformers();
				else if (cmd.equals("create_transformer"))
					createTransformer(tokens);
				else if (cmd.equals("remove_transformer"))
					removeTransformer(tokens);
				else if (cmd.equals("create_parser"))
					createParser(tokens);
				else if (cmd.equals("remove_parser"))
					removeParser(tokens);
				else if (cmd.equals("test_parse"))
					testParse(tokens);
				else if (cmd.equals("create_logger"))
					createLogger(tokens);
				else if (cmd.equals("remove_logger"))
					removeLogger(tokens);
				else if (cmd.equals("start_logger"))
					startLogger(tokens);
				else if (cmd.equals("stop_logger"))
					stopLogger(tokens);
				else if (cmd.equals("index_tokenizers"))
					listIndexTokenizers(tokens);
				else if (cmd.equals("indexes"))
					listIndexes(tokens);
				else if (cmd.equals("index"))
					getIndexInfo(tokens);
				else if (cmd.equals("create_index"))
					createIndex(tokens);
				else if (cmd.equals("drop_index"))
					dropIndex(tokens);
				else if (cmd.equals("test_index_tokenize"))
					testIndexTokenize(tokens);
				else if (cmd.equals("accounts"))
					listAccounts(tokens);
				else if (cmd.equals("create_account"))
					createAccount(tokens);
				else if (cmd.equals("remove_account"))
					removeAccount(tokens);
				else if (cmd.equals("passwd"))
					changePassword(tokens);
				else if (cmd.equals("grant"))
					grantPrivilege(tokens);
				else if (cmd.equals("revoke"))
					revokePrivilege(tokens);
				else if (cmd.equals("archives"))
					listArchiveConfigs(tokens);
				else if (cmd.equals("create_archive"))
					createArchiveConfig(tokens);
				else if (cmd.equals("remove_archive"))
					removeArchiveConfig(tokens);
				else if (cmd.equals("engines"))
					engines();
				else
					w("syntax error");

			}
		} finally {
			if (client != null) {
				w("closing logdb connection...");
				client.close();
				w("bye!");
			}
		}

	}

	private void connect(String[] tokens) {
		if (tokens.length < 3) {
			w("Usage: connect <host:port> <loginname> [<password>]");
			return;
		}

		if (client != null) {
			w("already connected");
			return;
		}

		String addr = tokens[1];
		String[] addrTokens = addr.split(":");

		host = addrTokens[0];
		int port = 8888;
		if (addrTokens.length > 1)
			port = Integer.valueOf(addrTokens[1]);

		try {
			InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			w("invalid hostname " + host + ", connect failed");
			return;
		}

		loginName = tokens[2];

		password = "";
		if (tokens.length > 3)
			password = tokens[3];

		try {
			client = new Logpresso();
			client.connect(host, port, loginName, password);
			w("connected to " + host + " as " + loginName);
		} catch (Throwable t) {
			w(t.getMessage());
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
				}
				client = null;
			}
		}
	}

	private void disconnect() {
		if (client == null) {
			w("not connected yet");
			return;
		}

		w("closing connection...");
		try {
			client.close();
		} catch (IOException e) {
		}
		w("disconnected");
		client = null;
	}

	private void queries() {
		if (client == null) {
			w("connect first please");
			return;
		}
		try {
			List<Query> queries = client.getQueries();
			if (queries.size() == 0) {
				w("no result");
				return;
			}

			for (Query query : queries) {
				w(query.toString());
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void queryStatus(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: query_status <query_id>");
			return;
		}

		try {
			Query query = client.getQuery(Integer.valueOf(tokens[1]));
			if (query == null) {
				w("query not found");
				return;
			}

			w(query.toString());
			for (QueryCommand cmd : query.getCommands())
				w("\t" + cmd);
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void query(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		long begin = System.currentTimeMillis();
		String queryString = join(tokens);
		w("querying [" + queryString + "] ...");

		long count = 0;
		Cursor cursor = null;
		try {
			cursor = client.query(queryString);
			while (cursor.hasNext()) {
				Object o = cursor.next();
				w(o.toString());
				count++;
			}

			long end = System.currentTimeMillis();
			w("total " + count + " row(s), elapsed " + (end - begin) + "ms");
		} catch (Throwable t) {
			if (client != null && client.isClosed())
				client = null;

			w("query failed: " + t.getMessage());
		} finally {
			if (cursor != null) {
				try {
					cursor.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void createQuery(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: create_query <query_string>");
			return;
		}

		try {
			String queryString = join(tokens);
			int id = client.createQuery(queryString);
			w("created query " + id);
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private String join(String[] tokens) {
		StringBuilder sb = new StringBuilder();
		int p = 0;
		for (int i = 1; i < tokens.length; i++) {
			String t = tokens[i];
			if (p++ != 0)
				sb.append(" ");
			sb.append(t);
		}

		return sb.toString();
	}

	private void startQuery(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: start_query <query_id>");
			return;
		}

		try {
			int id = Integer.valueOf(tokens[1]);
			client.startQuery(id);
			w("started query " + id);
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void stopQuery(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: stop_query <query_id>");
			return;
		}

		try {
			int id = Integer.valueOf(tokens[1]);
			client.stopQuery(id);
			w("stopped query " + id);
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void removeQuery(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: remove_query <query_id>");
			return;
		}
		try {
			int id = Integer.valueOf(tokens[1]);
			client.removeQuery(id);
			w("removed query " + id);
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void fetch(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 4) {
			w("Usage: fetch <query_id> <offset> <limit>");
			return;
		}

		int id = Integer.valueOf(tokens[1]);
		long offset = Long.valueOf(tokens[2]);
		int limit = Integer.valueOf(tokens[3]);

		try {
			Map<String, Object> page = client.getResult(id, offset, limit);
			List<Object> rows = (List<Object>) page.get("result");
			for (Object row : rows)
				w(row.toString());
			w(rows.size() + " row(s)");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void createTable(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: create_table <table_name> <engine_type>");
			return;
		}

		try {
			client.createTable(tokens[1], tokens[2]);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void dropTable(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: drop_table <table_name>");
			return;
		}

		try {
			client.dropTable(tokens[1]);
			w("dropped");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listTables() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			for (TableSchema table : client.listTables()) {
				w("Table [" + table.getName() + "]");
				for (Entry<String, String> e : table.getMetadata().entrySet())
					w(" * " + e.getKey() + "=" + e.getValue());
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void manageTable(String[] tokens) throws IOException {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: table <table_name> [<key>] [<value>]");
			return;
		}

		try {
			String tableName = tokens[1];
			if (tokens.length == 2) {
				TableSchema table = client.getTableInfo(tableName);
				w("Table [" + table.getName() + "]");
				w("");

				// old version don't return primary storage config
				if (table.getPrimaryStorage() != null) {
					w("Primary Storage: type " + table.getPrimaryStorage().getType());
					w("------------------------------");
					if (table.getPrimaryStorage().getBasePath() != null)
						w("Base Path: " + table.getPrimaryStorage().getBasePath());

					for (TableConfig config : table.getPrimaryStorage().getConfigs()) {
						w(config.toString());
					}
				}

				if (table.getReplicaStorage() != null) {
					w("");
					w("Replica Storage: type " + table.getReplicaStorage().getType());
					w("------------------------------");
					if (table.getReplicaStorage().getBasePath() != null)
						w("Base Path: " + table.getReplicaStorage().getBasePath());

					for (TableConfig config : table.getReplicaStorage().getConfigs()) {
						w(config.toString());
					}
				}

				if (!table.getMetadata().isEmpty()) {
					w("");
					w("Metadata");
					w("----------");
					for (Entry<String, String> e : table.getMetadata().entrySet())
						w(" * " + e.getKey() + "=" + e.getValue());
				}
			} else {
				String key = tokens[2];
				if (tokens.length == 3) {
					Set<String> keys = new HashSet<String>();
					keys.add(key);
					client.unsetTableMetadata(tableName, keys);
					w("unset");
				} else if (tokens.length == 4) {
					Map<String, String> config = new HashMap<String, String>();
					String value = tokens[3];
					config.put(key, value);
					client.setTableMetadata(tableName, config);
					w("set");
				}
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listLoggers() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			w("Loggers");
			w("---------");
			for (Logger logger : client.listLoggers())
				w(logger.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listLoggerFactories() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			w("Logger Factories");
			w("------------------");
			for (LoggerFactory f : client.listLoggerFactories())
				w(f.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listParserFactories() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			w("Parser Factories");
			w("------------------");
			for (ParserFactory f : client.listParserFactories())
				w(f.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void getParserFactoryInfo(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: parser_factory <factory name>");
			return;
		}

		try {
			w("Parser Factory");
			w("------------------");
			ParserFactory f = client.getParserFactoryInfo(tokens[1]);
			w(f.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listParsers() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			List<Parser> parsers = client.listParsers();
			if (parsers.size() == 0) {
				w("no result");
				return;
			}

			w("Parsers");
			w("----------");
			for (Parser parser : parsers)
				w(parser.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void createParser(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: create_parser <factory_name> <name>");
			return;
		}

		try {
			ParserFactory f = client.getParserFactoryInfo(tokens[1]);

			Parser p = new Parser();
			p.setFactoryName(tokens[1]);
			p.setName(tokens[2]);

			for (ConfigSpec type : f.getConfigSpecs()) {
				inputOption(p, type);
			}

			client.createParser(p);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void inputOption(Parser parser, ConfigSpec spec) throws IOException {
		String directive = spec.isRequired() ? "(required)" : "(optional)";
		System.out.print(spec.getDisplayName() + " " + directive + "? ");
		String value = br.readLine();
		if (!value.isEmpty())
			parser.getConfigs().put(spec.getName(), value);

		if (value.isEmpty() && spec.isRequired()) {
			inputOption(parser, spec);
		}
	}

	private void removeParser(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: remove_parser <name>");
			return;
		}

		try {
			client.removeParser(tokens[1]);
			w("removed");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void testParse(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: test_parse <parser name> <line>");
			return;
		}

		try {
			String name = tokens[1];
			String line = tokens[2];

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("line", line);

			List<Map<String, Object>> rows = client.testParser(name, data);

			w("Parsed rows");
			w("-------------");
			for (Map<String, Object> row : rows) {
				w(row.toString());
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}

	}

	private void listTransformerFactories() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			w("Transformer Factories");
			w("------------------");
			for (TransformerFactory f : client.listTransformerFactories())
				w(f.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void getTransformerFactoryInfo(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: transformer_factory <factory name>");
			return;
		}

		try {
			w("Transformer Factory");
			w("------------------");
			TransformerFactory f = client.getTransformerFactoryInfo(tokens[1]);
			w(f.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listTransformers() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			List<Transformer> transformers = client.listTransformers();
			if (transformers.size() == 0) {
				w("no result");
				return;
			}

			w("Transformers");
			w("--------------");
			for (Transformer transformer : transformers)
				w(transformer.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void createTransformer(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: create_transformer <factory_name> <name>");
			return;
		}

		try {
			TransformerFactory f = client.getTransformerFactoryInfo(tokens[1]);

			Transformer p = new Transformer();
			p.setFactoryName(tokens[1]);
			p.setName(tokens[2]);

			for (ConfigSpec type : f.getConfigSpecs()) {
				inputOption(p, type);
			}

			client.createTransformer(p);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void inputOption(Transformer parser, ConfigSpec spec) throws IOException {
		String directive = spec.isRequired() ? "(required)" : "(optional)";
		System.out.print(spec.getDisplayName() + " " + directive + "? ");
		String value = br.readLine();
		if (!value.isEmpty())
			parser.getConfigs().put(spec.getName(), value);

		if (value.isEmpty() && spec.isRequired()) {
			inputOption(parser, spec);
		}
	}

	private void removeTransformer(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: remove_transformer <name>");
			return;
		}

		try {
			client.removeTransformer(tokens[1]);
			w("removed");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void createLogger(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 4) {
			w("Usage: create_logger <factory name> <namespace> <name>");
			return;
		}

		try {
			Logger logger = new Logger();
			logger.setFactoryName(tokens[1]);
			logger.setNamespace(tokens[2]);
			logger.setName(tokens[3]);

			LoggerFactory f = client.getLoggerFactoryInfo(tokens[1]);

			for (ConfigSpec type : f.getConfigSpecs()) {
				inputOption(logger, type);
			}

			client.createLogger(logger);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void inputOption(Logger logger, ConfigSpec spec) throws IOException {
		String directive = spec.isRequired() ? "(required)" : "(optional)";
		System.out.print(spec.getDisplayName() + " " + directive + "? ");
		String value = br.readLine();
		if (!value.isEmpty())
			logger.getConfigs().put(spec.getName(), value);

		if (value.isEmpty() && spec.isRequired()) {
			inputOption(logger, spec);
		}
	}

	private void removeLogger(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: remove_logger <logger fullname>");
			return;
		}

		try {
			client.removeLogger(tokens[1]);
			w("removed");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void startLogger(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: start_logger <logger fullname> <interval (millisec)>");
			return;
		}

		try {
			client.startLogger(tokens[1], Integer.valueOf(tokens[2]));
			w("started with interval " + tokens[2] + "ms");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listIndexTokenizers(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			for (IndexTokenizerFactory tokenizer : client.listIndexTokenizerFactories()) {
				w(tokenizer.toString());
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listIndexes(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: indexes <table name>");
			return;
		}

		try {
			for (Index index : client.listIndexes(tokens[1])) {
				w(index.toString());
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void getIndexInfo(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: index <table name> <index name>");
			return;
		}

		try {
			Index index = client.getIndexInfo(tokens[1], tokens[2]);
			w(index.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void stopLogger(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: stop_logger <logger fullname>");
			return;
		}

		try {
			client.stopLogger(tokens[1], 5000);
			w("stopped");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void createIndex(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: create_index <table name> <index name>");
			return;
		}

		try {
			String tableName = tokens[1];
			String indexName = tokens[2];

			Index index = new Index();
			index.setTableName(tableName);
			index.setIndexName(indexName);

			w("Available Index Tokenizers");
			w("----------------------------");
			List<IndexTokenizerFactory> tokenizers = client.listIndexTokenizerFactories();
			for (IndexTokenizerFactory tokenizer : tokenizers) {
				w(tokenizer.toString());
			}

			System.out.print("select tokenizer? ");
			String tokenizerName = br.readLine().trim();

			IndexTokenizerFactory selected = null;
			for (IndexTokenizerFactory tokenizer : tokenizers) {
				if (tokenizer.getName().equals(tokenizerName))
					selected = tokenizer;
			}

			if (selected == null) {
				w("invalid index tokenizer");
				return;
			}

			index.setTokenizerName(tokenizerName);

			for (IndexConfigSpec type : selected.getConfigSpecs()) {
				inputOption(index, type);
			}

			System.out.print("use bloom filter (y/N)? ");
			index.setUseBloomFilter(br.readLine().trim().equalsIgnoreCase("y"));

			if (index.isUseBloomFilter()) {
				System.out.print("bloom filter lv0 capacity (enter to use default)? ");
				String t = br.readLine().trim();
				if (!t.isEmpty())
					index.setBloomFilterCapacity0(Integer.valueOf(t));

				System.out.print("bloom filter lv0 error rate (0<x<1, enter to use default)? ");
				t = br.readLine().trim();
				if (!t.isEmpty())
					index.setBloomFilterErrorRate0(Double.valueOf(t));

				System.out.print("bloom filter lv1 capacity (enter to use default)? ");
				if (!t.isEmpty())
					index.setBloomFilterCapacity1(Integer.valueOf(t));

				System.out.print("bloom filter lv1 error rate (0<x<1, enter to use default)? ");
				if (!t.isEmpty())
					index.setBloomFilterErrorRate1(Double.valueOf(t));
			}

			System.out.print("base path (optional)? ");
			String basePath = br.readLine().trim();
			if (basePath.isEmpty())
				basePath = null;
			index.setBasePath(basePath);

			System.out.print("build past index (y/n)? ");
			String s = br.readLine().trim();
			index.setBuildPastIndex(s.equalsIgnoreCase("y"));

			if (index.isBuildPastIndex()) {
				System.out.print("min day (yyyymmdd or enter to skip)? ");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				String minDayStr = br.readLine().trim();
				if (minDayStr != null)
					index.setMinIndexDay(dateFormat.parse(minDayStr));
			}

			client.createIndex(index);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void inputOption(Index index, IndexConfigSpec spec) throws IOException {
		String directive = spec.isRequired() ? "(required)" : "(optional)";
		System.out.print(spec.getName() + " " + directive + "? ");
		String value = br.readLine();
		if (!value.isEmpty())
			index.getTokenizerConfigs().put(spec.getKey(), value);

		if (value.isEmpty() && spec.isRequired()) {
			inputOption(index, spec);
		}
	}

	private void dropIndex(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: drop_index <table name> <index name>");
			return;
		}

		try {
			client.dropIndex(tokens[1], tokens[2]);
			w("dropped");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void testIndexTokenize(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 4) {
			w("Usage: test_index_tokenize <table name> <index name> <line>");
			return;
		}

		try {
			String tableName = tokens[1];
			String indexName = tokens[2];
			String line = tokens[3];

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("line", line);

			Set<String> s = client.testIndexTokenizer(tableName, indexName, data);
			w("Fulltext Tokens");
			w("-----------------");
			for (String t : s)
				w(t);

		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void listAccounts(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			for (Account account : client.listAccounts())
				w(account.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}

	}

	private void createAccount(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: create_account <login name> <password>");
			return;
		}

		try {
			Account account = new Account();
			account.setLoginName(tokens[1]);
			account.setPassword(tokens[2]);
			client.createAccount(account);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void removeAccount(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: remove_account <login name>");
			return;
		}

		try {
			client.removeAccount(tokens[1]);
			w("removed");
		} catch (Throwable t) {
			w(t.getMessage());
		}

	}

	private void changePassword(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: passwd <login name> <password>");
			return;
		}

		try {
			client.changePassword(tokens[1], tokens[2]);
			w("changed");
		} catch (Throwable t) {
			w(t.getMessage());
		}

	}

	private void grantPrivilege(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: grant <login name> <table name>");
			return;
		}

		try {
			client.grantPrivilege(new Privilege(tokens[1], tokens[2]));
			w("granted");
		} catch (Throwable t) {
			w(t.getMessage());
		}

	}

	private void revokePrivilege(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: revoke <login name> <table name>");
			return;
		}

		try {
			client.revokePrivilege(new Privilege(tokens[1], tokens[2]));
			w("revoked");
		} catch (Throwable t) {
			w(t.getMessage());
		}

	}

	private void listArchiveConfigs(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			w("Archive Configs");
			w("-----------------");
			for (ArchiveConfig config : client.listArchiveConfigs()) {
				w(config.toString());
			}
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void createArchiveConfig(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 3) {
			w("Usage: create_archive <logger fullname> <table name> [<host name>]");
			return;
		}

		try {
			ArchiveConfig config = new ArchiveConfig();
			config.setLoggerName(tokens[1]);
			config.setTableName(tokens[2]);
			if (tokens.length > 3)
				config.setHost(tokens[3]);
			config.setEnabled(true);

			client.createArchiveConfig(config);
			w("created");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void removeArchiveConfig(String[] tokens) {
		if (client == null) {
			w("connect first please");
			return;
		}

		if (tokens.length < 2) {
			w("Usage: remove_archive <logger fullname>");
			return;
		}

		try {
			client.removeArchiveConfig(tokens[1]);
			w("removed");
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private void engines() {
		if (client == null) {
			w("connect first please");
			return;
		}

		try {
			List<StorageEngine> engines = client.listStorageEngines();
			for (StorageEngine e : engines)
				w(e.toString());
		} catch (Throwable t) {
			w(t.getMessage());
		}
	}

	private String getPrompt() {
		if (client != null)
			return "logpresso@" + host + "> ";
		return "logpresso> ";
	}

	private void help() {
		w("connect <host> <loginname> <password>");
		w("\tconnect to specified logpresso instance");

		w("disconnect");
		w("\tdisconnect database connection");

		w("queries");
		w("\tprint all queries initiated by this session");

		w("query <query string>");
		w("\tcreate, start and fetch query result at once");

		w("create_query <query string>");
		w("\tcreate query with specified query string, and return allocated query id");

		w("start_query <query id>");
		w("\tstart query");

		w("stop_query <query_id>");
		w("\tstop running query");

		w("remove_query <query_id>");
		w("\tstop and remove query");

		w("fetch <query_id> <offset> <limit>");
		w("\tfetch result set of specified window. you can fetch partial result before query is ended");
	}

	private static void w(String s) {
		System.out.println(s);
	}

	private static String[] tokenize(String line) {
		StringBuilder sb = new StringBuilder();
		List<String> args = new ArrayList<String>();

		boolean quoteOpen = false;
		boolean squoteOpen = false;
		boolean escape = false;

		int i = 0;
		while (true) {
			if (i >= line.length())
				break;

			char c = line.charAt(i);

			i++;

			if (c == '\\') {
				if (escape) {
					escape = false;
					sb.append(c);
				} else if (squoteOpen) {
					sb.append(c);
				} else {
					escape = true;
				}
				continue;
			}

			if (c == '"') {
				if (escape) {
					escape = false;
					sb.append(c);
				} else if (squoteOpen) {
					sb.append(c);
				} else {
					quoteOpen = !quoteOpen;
					if (!quoteOpen) {
						args.add(sb.toString());
						sb = new StringBuilder();
					}
				}
				continue;
			}

			if (c == '\'') {
				if (escape) {
					escape = false;
					sb.append(c);
				} else {
					quoteOpen = !quoteOpen;
					squoteOpen = !squoteOpen;
					if (!quoteOpen) {
						args.add(sb.toString());
						sb = new StringBuilder();
					}
				}
				continue;
			}

			if (c == ' ' && !(quoteOpen)) {
				String parsed = sb.toString();
				if (!parsed.trim().isEmpty())
					args.add(parsed);
				sb = new StringBuilder();
				continue;
			}

			if (c != '\\' && escape) {
				sb.append('\\');
				escape = false;
			}

			sb.append(c);
		}

		String parsed = sb.toString();
		if (!parsed.trim().isEmpty())
			args.add(sb.toString());

		return args.toArray(new String[0]);
	}

}
