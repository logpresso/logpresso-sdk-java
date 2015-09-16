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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.logpresso.client.Message;

/**
 * 현재 응답 수신을 대기하는 중인 메시지버스 RPC 호출을 표현합니다.
 * 
 * @since 0.5.0
 * @author xeraph@eediom.com
 * 
 */
class WaitingCall {
	private String guid;
	private Message result;
	private Date date = new Date();
	private CountDownLatch done = new CountDownLatch(1);

	public WaitingCall(String guid) {
		this.guid = guid;
	}

	public String getGuid() {
		return guid;
	}

	public Message getResult() {
		return result;
	}

	public Date getDate() {
		return date;
	}

	public void await(int timeout) throws InterruptedException {
		done.await(timeout, TimeUnit.MILLISECONDS);
	}

	public void done(Message result) {
		this.result = result;
		done.countDown();
	}
}
