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

/**
 * JDBC 프로파일을 표현합니다.
 * 
 * @since 0.8.2
 * @author xeraph@eediom.com
 * 
 */
public class JdbcProfile {
	private String name;
	private String connectionString;
	private boolean readOnly;
	private String user;

	// used for create profile only
	private String password;

	/**
	 * JDBC 프로파일 이름을 반환합니다.
	 * 
	 * @return JDBC 프로파일 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * JDBC 프로파일 이름을 설정합니다.
	 * 
	 * @param name
	 *            JDBC 프로파일 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * JDBC 접속 문자열을 반환합니다.
	 * 
	 * @return JDBC 접속 문자열
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * JDBC 접속 문자열을 설정합니다.
	 * 
	 * @param connectionString
	 *            JDBC 접속 문자열
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * JDBC 접속의 읽기 전용 여부를 반환합니다. JDBC 드라이버에 따라 읽기 전용 모드가 지원되지 않을 수 있습니다.
	 * 
	 * @return 읽기 전용 여부
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * JDBC 접속의 읽기 전용 여부를 설정합니다. JDBC 드라이버에 따라 읽기 전용 모드가 지원되지 않을 수 있습니다.
	 * 
	 * @param readOnly
	 *            읽기 전용 여부
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * JDBC 접속 계정 이름을 반환합니다.
	 * 
	 * @return JDBC 접속 계정 이름
	 */
	public String getUser() {
		return user;
	}

	/**
	 * JDBC 접속 계정 이름을 설정합니다.
	 * 
	 * @param user
	 *            JDBC 접속 계정 이름
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * JDBC 접속 암호를 반환합니다.
	 * 
	 * @return JDBC 접속 암호
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * JDBC 접속 암호를 설정합니다.
	 * 
	 * @param password
	 *            JDBC 접속 암호
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "name=" + name + ", connection_string=" + connectionString + ", readonly=" + readOnly + ", user=" + user
				+ ", password=" + password;
	}
}
