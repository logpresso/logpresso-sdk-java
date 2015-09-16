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
 * DB 계정의 권한을 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Privilege {
	private String loginName;
	private String tableName;

	// reserved
	private List<String> permissions = new ArrayList<String>();

	public Privilege() {
	}

	public Privilege(String loginName, String tableName) {
		this.loginName = loginName;
		this.tableName = tableName;
	}

	/**
	 * 계정 이름을 반환합니다.
	 * 
	 * @return 계정 이름
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 계정 이름을 설정합니다.
	 * 
	 * @param loginName
	 *            계정 이름
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 테이블 이름을 반환합니다.
	 * 
	 * @return 테이블 이름
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 테이블 이름을 설정합니다.
	 * 
	 * @param tableName
	 *            테이블 이름
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 퍼미션 목록을 반환합니다.
	 * 
	 * @return 퍼미션 목록
	 */
	public List<String> getPermissions() {
		return permissions;
	}

	/**
	 * 퍼미션 목록을 설정합니다.
	 * 
	 * @param permissions
	 *            퍼미션 목록
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return tableName;
	}
}
