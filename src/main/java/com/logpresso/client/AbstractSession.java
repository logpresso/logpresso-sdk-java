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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

import com.logpresso.client.http.impl.TrapListener;

/**
 * 이 추상 클래스는 공통적인 로그프레소 세션의 기능을 구현합니다.
 * 
 * @since 0.5.0
 * @author xeraph@eediom.com
 * 
 */
public abstract class AbstractSession implements Session {
	protected boolean isClosed;
	protected boolean isLogin = false;
	protected CopyOnWriteArraySet<TrapListener> listeners = new CopyOnWriteArraySet<TrapListener>();

	@Override
	public boolean isClosed() {
		return isClosed;
	}

	@Override
	public void login(String loginName, String password) throws IOException {
		login(loginName, password, false);
	}
	
	@Override
	public void login(String loginName, String password, boolean force) throws IOException {
		login(loginName, password, force, 30000);
	}

	@Override
	public void login(String loginName, String password, boolean force, int timeout) throws IOException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("login_name", loginName);
			params.put("password", password);
			params.put("use_error_return", true);

			Message resp = rpc("org.araqne.logdb.msgbus.ManagementPlugin.login", params);
			if (resp.containsKey("error_code")) {
				String errorCode = resp.getString("error_code");
				isLogin = false;
				throw new LoginFailureException(errorCode);
			} else
				isLogin = true;
		} catch (MessageException e) {
			isLogin = false;
			if (e.getMessage() != null && e.getMessage().contains("msgbus-handler-not-found"))
				throw new LoginFailureException("msgbus-handler-not-found");
			else
				throw e;
		} catch (IOException t) {
			isLogin = false;
			throw t;
		}
	}

	@Override
	public void logout() throws IOException {
		rpc("org.araqne.logdb.msgbus.ManagementPlugin.logout");
	}

	@Override
	public Message rpc(String method) throws IOException {
		try {
			return rpc(method, 0);
		} catch (TimeoutException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Message rpc(String method, Map<String, Object> params) throws IOException {
		try {
			return rpc(method, params, 0);
		} catch (TimeoutException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Message rpc(String method, int timeout) throws IOException, TimeoutException {
		Message req = new Message();
		req.setMethod(method);
		return rpc(req, timeout);
	}

	@Override
	public Message rpc(String method, Map<String, Object> params, int timeout) throws IOException, TimeoutException {
		Message req = new Message();
		req.setMethod(method);
		req.setParameters(params);
		return rpc(req, timeout);
	}

	public void registerTrap(String callbackName) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("callback", callbackName);
		rpc("org.araqne.msgbus.PushPlugin.subscribe", params);
	}

	public void unregisterTrap(String callbackName) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("callback", callbackName);
		rpc("org.araqne.msgbus.PushPlugin.unsubscribe", params);
	}

	public void addListener(TrapListener listener) {
		listeners.add(listener);
	}

	public void removeListener(TrapListener listener) {
		listeners.remove(listener);
	}

	public void close() throws IOException {
		try {
			if (isClosed())
				return;

			isClosed = true;

		} catch (Throwable t) {
		}
	}
}
