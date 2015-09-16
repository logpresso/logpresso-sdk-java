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

import java.util.Map;

/**
 * 아라크네 메시지버스 RPC 예외를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class MessageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;
	private Map<String, Object> parameters;

	public MessageException(String code, String msg, Map<String, Object> parameters) {
		this.code = code;
		this.msg = msg;
		this.parameters = parameters;
	}

	/**
	 * RPC 예외 오류 코드를 반환합니다.
	 * 
	 * @return RPC 예외의 오류 코드
	 */
	public String getCode() {
		return code;
	}

	/**
	 * RPC 예외 메시지를 반환합니다.
	 * 
	 * @return RPC 예외 메시지
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * RPC 예외와 관련된 부수적인 정보들을 반환합니다.
	 * 
	 * @return 예외 매개변수 목록
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * 출력시 사용할 예외 메시지 문자열을 반환합니다.
	 */
	@Override
	public String getMessage() {
		if (msg != null)
			return code + ": " + msg;
		return code;
	}
}
