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
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.logpresso.client.http.impl.TrapListener;

/**
 * 세션 인터페이스는 로그프레소 접속 및 RPC 통신에 필요한 명세를 제공합니다.
 * 
 * @since 0.5.0
 * @author xeraph@eediom.com
 * 
 */
public interface Session {
	/**
	 * 세션의 종료 여부를 조회합니다.
	 * 
	 * @return 접속이 종료된 경우 true를 반환합니다.
	 */
	boolean isClosed();

	/**
	 * 로그프레소 로그인을 수행합니다.
	 * 
	 * @param loginName
	 *            계정 이름
	 * @param password
	 *            암호
	 */
	void login(String loginName, String password) throws IOException;

	/**
	 * 로그프레소 로그인을 수행합니다.
	 * 
	 * @param loginName
	 *            계정 이름
	 * @param password
	 *            암호
	 * @param force
	 *            동시 접속 수 초과 시 강제로 이전 접속을 끊고 접속하려는 경우에는 true로 지정
	 */
	void login(String loginName, String password, boolean force) throws IOException;

	/**
	 * 로그아웃을 수행합니다.
	 */
	void logout() throws IOException;

	/**
	 * 로그프레소 RPC 메소드를 호출합니다.
	 * 
	 * @param method
	 *            메소드 이름. 패키지.클래스.메소드 형식.
	 * @return RPC 수행 결과를 반환합니다.
	 */
	Message rpc(String method) throws IOException;

	/**
	 * 로그프레소 RPC 메소드를 호출합니다.
	 * 
	 * @param method
	 *            메소드 이름. 패키지.클래스.메소드 형식.
	 * @param params
	 *            매개변수 목록
	 * @return RPC 수행 결과를 반환합니다.
	 */
	Message rpc(String method, Map<String, Object> params) throws IOException;

	/**
	 * 로그프레소 RPC 메소드를 호출합니다.
	 * 
	 * @param method
	 *            메소드 이름. 패키지.클래스.메소드 형식.
	 * @param timeout
	 *            밀리세컨드 단위 타임아웃
	 * @return RPC 수행 결과를 반환합니다.
	 * @throws TimeoutException 
	 */
	Message rpc(String method, int timeout) throws IOException, TimeoutException;

	/**
	 * 로그프레소 RPC 메소드를 호출합니다.
	 * 
	 * @param method
	 *            메소드 이름. 패키지.클래스.메소드 형식.
	 * @param params
	 *            매개변수 목록
	 * @param timeout
	 *            밀리세컨드 단위 타임아웃
	 * @return RPC 수행 결과를 반환합니다.
	 */
	Message rpc(String method, Map<String, Object> params, int timeout) throws IOException, TimeoutException;

	/**
	 * 로그프레소 RPC 메소드를 호출합니다.
	 * 
	 * @param req
	 *            메시지버스 요청 전문 개체
	 * @param timeout
	 *            밀리세컨드 단위 타임아웃
	 * @return RPC 수행 결과를 반환합니다.
	 * @throws TimeoutException 
	 */
	Message rpc(Message req, int timeout) throws IOException, TimeoutException;

	/**
	 * 로그프레소 서버에서 트랩 수신을 시작합니다.
	 * 
	 * @param callbackName
	 *            트랩을 수신할 콜백 이름
	 */
	void registerTrap(String callbackName) throws IOException;

	/**
	 * 로그프레소 서버에서 트랩 수신을 중지합니다.
	 * 
	 * @param callbackName
	 *            트랩 수신을 중지할 콜백 이름
	 */
	void unregisterTrap(String callbackName) throws IOException;

	/**
	 * 트랩 수신기를 등록합니다.
	 * 
	 * @param listener
	 *            트랩 수신 개체
	 */
	void addListener(TrapListener listener);

	/**
	 * 트랩 수신기를 등록 해제합니다.
	 * 
	 * @param listener
	 *            트랩 수신 개체
	 */
	void removeListener(TrapListener listener);

	/**
	 * 세션을 종료합니다.
	 */
	void close() throws IOException;
}
