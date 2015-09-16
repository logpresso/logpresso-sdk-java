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
package com.logpresso.client.http.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logpresso.client.Message;

/**
 * 아라크네 메시지버스 RPC 전문을 JSON으로 인코딩하거나 디코딩합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class MessageCodec {
	private MessageCodec() {
	}

	public static Message decode(String text) {
		try {
			JSONTokener tokenizer = new JSONTokener(new StringReader(text));
			JSONArray container = (JSONArray) tokenizer.nextValue();
			JSONObject header = container.getJSONObject(0);
			JSONObject body = container.getJSONObject(1);

			Message msg = new Message();

			msg.setGuid(header.getString("guid").trim());
			msg.setType(Message.Type.valueOf(header.getString("type").trim()));
			msg.setSource(header.getString("source"));
			msg.setTarget(header.getString("target"));
			msg.setMethod(header.getString("method").trim());

			if (header.has("requestId"))
				msg.setRequestId(header.getString("requestId").trim());

			if (header.has("errorCode")) {
				msg.setErrorCode(header.getString("errorCode"));
				if (!header.isNull("errorMessage"))
					msg.setErrorMessage(header.getString("errorMessage"));
			}

			msg.setParameters(parse(body));
			return msg;
		} catch (JSONException e) {
			throw new IllegalStateException("json parse error: " + text, e);
		}
	}

	public static String encode(Message msg) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("guid", msg.getGuid());
		if (msg.getRequestId() != null)
			headers.put("requestId", msg.getRequestId());

		headers.put("type", msg.getType().toString());
		headers.put("method", msg.getMethod());
		headers.put("session", msg.getSession());
		headers.put("source", msg.getSource());
		headers.put("target", msg.getTarget());

		if (msg.getErrorCode() != null) {
			headers.put("errorCode", msg.getErrorCode());
			headers.put("errorMessage", msg.getErrorMessage());
		}

		return jsonize(headers, msg.getParameters());
	}

	private static Map<String, Object> parse(JSONObject obj) {
		Map<String, Object> m = new HashMap<String, Object>();
		String[] names = JSONObject.getNames(obj);
		if (names == null)
			return m;

		for (String key : names) {
			try {
				Object value = obj.get(key);
				if (value == JSONObject.NULL)
					value = null;
				else if (value instanceof JSONArray)
					value = parse((JSONArray) value);
				else if (value instanceof JSONObject)
					value = parse((JSONObject) value);

				m.put(key, value);
			} catch (JSONException e) {
				Logger logger = LoggerFactory.getLogger(MessageCodec.class);
				logger.error("logpresso: invalid msgbus json - " + obj, e);
			}
		}

		return m;
	}

	private static List<Object> parse(JSONArray arr) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < arr.length(); i++) {
			try {
				Object o = arr.get(i);
				if (o == JSONObject.NULL)
					list.add(null);
				else if (o instanceof JSONArray)
					list.add(parse((JSONArray) o));
				else if (o instanceof JSONObject)
					list.add(parse((JSONObject) o));
				else
					list.add(o);
			} catch (JSONException e) {
				Logger logger = LoggerFactory.getLogger(MessageCodec.class);
				logger.error("logpresso: invalid msgbus json - " + arr, e);
			}
		}
		return list;
	}

	private static String jsonize(Map<String, Object> headers, Map<String, Object> properties) {
		StringWriter writer = new StringWriter(1024);
		JSONWriter jsonWriter = new JSONWriter(writer);

		try {
			jsonWriter.array();

			jsonWriter.object();

			for (String key : headers.keySet()) {
				jsonWriter.key(key).value(headers.get(key));
			}

			jsonWriter.endObject();

			jsonWriter.object();

			properties = convertDate(properties);
			for (String key : properties.keySet()) {
				jsonWriter.key(key).value(properties.get(key));
			}

			jsonWriter.endObject();

			jsonWriter.endArray();
		} catch (Exception e) {
			throw new IllegalStateException("cannot encode json", e);
		}

		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> convertDate(Map<String, Object> properties) {
		Map<String, Object> m = new HashMap<String, Object>();
		if (properties == null)
			return m;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

		for (String key : properties.keySet()) {
			Object value = properties.get(key);

			if (value instanceof Date)
				m.put(key, dateFormat.format((Date) value));
			else if (value instanceof Map)
				m.put(key, convertDate((Map<String, Object>) value));
			else if (value instanceof Collection) {
				Collection<Object> c = new ArrayList<Object>();
				for (Object v : (Collection<?>) value) {
					if (v instanceof Date)
						c.add(dateFormat.format((Date) v));
					else
						c.add(v);
				}
				m.put(key, c);
			} else
				m.put(key, value);
		}

		return m;
	}
}
