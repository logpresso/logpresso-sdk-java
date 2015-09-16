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
 * 로그 수집기를 생성하는데 필요한 설정 명세를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class LoggerFactory {
	private String fullName;
	private String displayName;
	private String namespace;
	private String name;
	private String description;
	private List<ConfigSpec> configSpecs = new ArrayList<ConfigSpec>();

	/**
	 * 로그 수집기 유형 이름을 반환합니다.
	 * 
	 * @return 로그 수집기 유형 이름. 이름공간\이름 형식.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * 로그 수집기 유형 이름을 설정합니다.
	 * 
	 * @param fullName
	 *            로그 수집기 유형 이름. 이름공간\이름 형식.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 로그 수집기 유형의 UI 표시 이름을 반환합니다.
	 * 
	 * @return UI 표시 이름
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * 로그 수집기 유형의 UI 표시 이름을 설정합니다.
	 * 
	 * @param displayName
	 *            UI 표시 이름
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 로그 수집기 유형의 이름공간을 반환합니다.
	 * 
	 * @return 이름공간. 일반적으로 local, 원격 시스템이 연동된 경우 GUID가 지정됨.
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * 로그 수집기 유형의 이름공간을 설정합니다.
	 * 
	 * @param namespace
	 *            이름공간. 일반적으로 local, 원격 시스템이 연동된 경우 GUID가 지정됨.
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 로그 수집기 유형의 이름을 반환합니다.
	 * 
	 * @return 로그 수집기 유형의 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 로그 수집기 유형의 이름을 설정합니다.
	 * 
	 * @param name
	 *            로그 수집기 유형의 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 로그 수집기 유형에 대한 설명을 반환합니다.
	 * 
	 * @return 로그 수집기 유형에 대한 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 로그 수집기 유형에 대한 설명을 설정합니다.
	 * 
	 * @param description
	 *            로그 수집기 유형에 대한 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 로그 수집기 유형의 설정 명세 목록을 반환합니다.
	 * 
	 * @return 설정 명세 목록
	 */
	public List<ConfigSpec> getConfigSpecs() {
		return configSpecs;
	}

	/**
	 * 로그 수집기 유형의 설정 명세 목록을 설정합니다.
	 * 
	 * @param configSpecs
	 *            설정 명세 목록
	 */
	public void setConfigSpecs(List<ConfigSpec> configSpecs) {
		this.configSpecs = configSpecs;
	}

	@Override
	public String toString() {
		return String.format("fullname=%s, type=%s, description=%s", fullName, displayName, description);
	}
}
