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

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logpresso.client.Message;

/**
 * 현재 블록킹 호출 중인 모든 RPC 호출의 목록을 관리합니다.
 * 
 * @since 0.5.0
 * @author xeraph@eediom.com
 * 
 */
public class WebSocketBlockingTable {
	private final Logger logger = LoggerFactory.getLogger(WebSocketBlockingTable.class);
	private Message interruptSignal = new Message();
	private ConcurrentMap<String, WaitingCall> lockMap = new ConcurrentHashMap<String, WaitingCall>();
	private WebSocketSession session;

	public WebSocketBlockingTable(WebSocketSession webSocketSession) {
		this.session = webSocketSession;
	}

	public WaitingCall set(String guid) {
		WaitingCall item = new WaitingCall(guid);
		lockMap.put(guid, item);
		return item;
	}

	public void signal(String guid, Message response) {
		if (logger.isDebugEnabled())
			logger.debug("logpresso: signal call response {}", guid);

		WaitingCall item = lockMap.get(guid);
		if (item == null) {
			logger.warn("logpresso: no waiting item {}, maybe timeout", guid);
			return;
		}

		synchronized (item) {
			item.done(response);
			item.notifyAll();
		}
	}

	public Message await(WaitingCall item) throws InterruptedException {
		if (logger.isDebugEnabled())
			logger.debug("logpresso: waiting call response id {}", item.getGuid());

		try {
			synchronized (item) {
				while (item.getResult() == null)
					item.wait();
			}

			if (item.getResult() == interruptSignal)
				throw new InterruptedException("call cancelled: " + item.getGuid());

		} finally {
			if (logger.isDebugEnabled())
				logger.debug("logpresso: removing blocking lock id {}", item.getGuid());

			lockMap.remove(item.getGuid());
		}

		return item.getResult();
	}

	public Message await(WaitingCall item, long timeout) throws InterruptedException, TimeoutException {
		long before = new Date().getTime();

		try {
			synchronized (item) {
				while (item.getResult() == null) {
					item.wait(timeout);

					if (new Date().getTime() - before >= timeout) {
						if (logger.isDebugEnabled())
							logger.debug("logpresso: blocking timeout of id {}", item.getGuid());
						break;
					}
				}
			}

			if (item.getResult() == interruptSignal)
				throw new InterruptedException("call cancelled: " + item.getGuid());
			else if (item.getResult() != null)
				return item.getResult();
			else
				throw new TimeoutException("websocket message read timeout");
		} finally {
			if (logger.isDebugEnabled())
				logger.debug("logpresso: blocking finished for id {}", item.getGuid());

			lockMap.remove(item.getGuid());
		}
	}

	public void close() {
		logger.debug("interrupting all blocking calls: " + session.toString());
		// cancel all blocking calls
		for (String guid : lockMap.keySet()) {
			WaitingCall item = lockMap.get(guid);
			if (item == null)
				continue;

			synchronized (item) {
				item.done(interruptSignal);
				item.notifyAll();
			}
		}

		lockMap.clear();
	}
}
