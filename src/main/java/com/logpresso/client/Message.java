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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 아라크네 메시지버스 RPC 전문을 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Message {
	/**
	 * 아라크네 메시지버스 RPC 전문의 유형을 표현합니다.
	 * 
	 * @author xeraph
	 * 
	 */
	public enum Type {
		/**
		 * 사용되지 않음
		 */
		None,

		/**
		 * RPC 요청
		 */
		Request,

		/**
		 * RPC 응답
		 */
		Response,

		/**
		 * 비동기 트랩 (서버 푸시)
		 */
		Trap;
	}

	private String guid;
	private Type type;
	private String session;
	private String requestId;
	private String source;
	private String target;
	private String method;
	private Map<String, Object> parameters;

	private String errorCode;
	private String errorMessage;

	public Message() {
		parameters = new HashMap<String, Object>();
		guid = UUID.randomUUID().toString();
		type = Type.Request;
		parameters = new HashMap<String, Object>();
		source = "0";
		target = "0";
	}

	/**
	 * 전문 식별자 GUID를 반환합니다.
	 * 
	 * @return 전문 식별자 GUID
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 전문 식별자 GUID를 설정합니다.
	 * 
	 * @param guid
	 *            전문 식별자 GUID
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 메시지버스 RPC 유형을 반환합니다.
	 * 
	 * @return RPC 유형
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 메시지버스 RPC 유형을 설정합니다.
	 * 
	 * @param type
	 *            RPC 유형
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 세션 키를 반환합니다.
	 * 
	 * @return 세션 키
	 */
	public String getSession() {
		return session;
	}

	/**
	 * 세션 키를 설정합니다.
	 * 
	 * @param session
	 *            세션 키
	 */
	public void setSession(String session) {
		this.session = session;
	}

	/**
	 * 이 전문이 응답 전문인 경우 요청 전문의 식별자 GUID를 반환합니다. 그렇지 않은 경우 null을 반환합니다.
	 * 
	 * @return RPC 요청 전문의 식별자
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * 요청 전문의 식별자를 설정합니다.
	 * 
	 * @param requestId
	 *            RPC 요청 전문의 식별자
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * RPC 요청자의 식별자를 반환합니다.
	 * 
	 * @return RPC 요청자의 식별자
	 */
	public String getSource() {
		return source;
	}

	/**
	 * RPC 요청자의 식별자를 설정합니다.
	 * 
	 * @param source
	 *            RPC 요청자의 식별자
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * RPC 응답자의 식별자를 반환합니다.
	 * 
	 * @return RPC 응답자의 식별자
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * RPC 응답자의 식별자를 설정합니다.
	 * 
	 * @param target
	 *            RPC 응답자의 식별자
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * RPC 메소드 이름을 반환합니다.
	 * 
	 * @return RPC 메소드 이름. "패키지.클래스.메소드이름" 형식
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * RPC 메소드 이름을 설정합니다.
	 * 
	 * @param method
	 *            RPC 메소드 이름. "패키지.클래스.메소드이름" 형식
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * RPC 매개변수 목록을 반환합니다.
	 * 
	 * @return RPC 매개변수 목록
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * RPC 매개변수 목록을 설정합니다.
	 * 
	 * @param parameters
	 *            RPC 매개변수 목록
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 특정 매개변수의 값을 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return 매개변수 값
	 */
	public Object get(String key) {
		return parameters.get(key);
	}

	/**
	 * 특정 매개변수 키를 포함하는지 여부를 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return 매개변수 키를 포함하는 경우 true, 그렇지 않으면 false
	 */
	public boolean containsKey(String key) {
		return parameters.containsKey(key);
	}

	/**
	 * 특정 매개변수의 값을 Long 타입으로 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return Long으로 캐스팅된 매개변수 값
	 */
	public Long getLong(String key) {
		if (!parameters.containsKey(key))
			return null;

		Object v = parameters.get(key);
		if (v == null)
			return null;

		if (v instanceof Integer)
			return (long) ((Integer) v);
		else
			return (Long) v;
	}

	/**
	 * 특정 매개변수의 값을 Integer 타입으로 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return Integer로 캐스팅된 매개변수 값
	 */
	public Integer getInt(String key) {
		if (!parameters.containsKey(key))
			return null;

		return (Integer) parameters.get(key);
	}

	/**
	 * 특정 매개변수의 값을 String 타입으로 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return String으로 캐스팅된 매개변수 값
	 */
	public String getString(String key) {
		if (!parameters.containsKey(key))
			return null;

		return (String) parameters.get(key);
	}

	/**
	 * 특정 매개변수의 값을 Boolean 타입으로 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return Boolean으로 캐스팅된 매개변수 값
	 */
	public Boolean getBoolean(String key) {
		if (!parameters.containsKey(key))
			return null;

		return (Boolean) parameters.get(key);
	}

	/**
	 * 특정 매개변수의 값을 Date 타입으로 반환합니다.
	 * 
	 * @param key
	 *            매개변수 키
	 * @return Date로 캐스팅된 매개변수 값
	 */
	public Date getDate(String key) {
		if (!parameters.containsKey(key))
			return null;

		return (Date) parameters.get(key);
	}

	/**
	 * RPC 예외가 발생한 경우 오류 코드 값을 반환합니다. 정상 실행된 경우 null을 반환합니다.
	 * 
	 * @return RPC 예외 오류 코드 값
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * RPC 예외 오류 코드를 설정합니다.
	 * 
	 * @param errorCode
	 *            RPC 예외 오류 코드 값
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * RPC 예외가 발생한 경우 오류 메시지를 반환합니다. 정상 실행된 경우 null을 반환합니다.
	 * 
	 * @return RPC 예외 오류 메시지
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * RPC 예외가 발생한 경우 오류 메시지를 설정합니다.
	 * 
	 * @param errorMessage
	 *            RPC 예외 오류 메시지
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return String.format("header [guid=%s, session=%d, source=%s, target=%s, method=%s, error=%s]", guid, session, source,
				target, method, errorMessage);
	}

}
