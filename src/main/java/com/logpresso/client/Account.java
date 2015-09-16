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

import java.util.ArrayList;
import java.util.List;

/**
 * DB 계정 정보를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Account {
	private String loginName;
	private String password;
	private List<Privilege> privileges = new ArrayList<Privilege>();

	/**
	 * 로그인 이름을 반환합니다.
	 * 
	 * @return 로그인 이름
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 로그인 이름을 설정합니다.
	 * 
	 * @param loginName
	 *            로그인 이름
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 암호를 반환합니다. 서버에서 계정 목록 조회 시에는 암호가 반환되지 않습니다.
	 * 
	 * @return 암호
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 암호를 설정합니다.
	 * 
	 * @param password
	 *            암호
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 계정의 권한 목록을 반환합니다.
	 * 
	 * @return 권한 목록
	 */
	public List<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * 계정의 권한 목록을 설정합니다.
	 * 
	 * @param privileges
	 *            권한 목록
	 */
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return loginName + ", privileges=" + privileges;
	}
}
