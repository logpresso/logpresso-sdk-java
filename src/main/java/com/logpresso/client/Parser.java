/*
 * Copyright 2014 Eediom Inc.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 파서 인스턴스 정보를 표현합니다.
 * 
 * @since 0.8.0
 * @author xeraph@eediom.com
 */
public class Parser {
	private String name;
	private String factoryName;

	// @since 0.9.0
	private List<TableField> fieldDefinitions;

	private Map<String, String> configs = new HashMap<String, String>();

	/**
	 * 파서 이름을 반환합니다.
	 * 
	 * @return 파서 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 파서 이름을 설정합니다.
	 * 
	 * @param name
	 *            파서 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 파서 유형의 이름을 반환합니다.
	 * 
	 * @return 파서 유형의 이름
	 */
	public String getFactoryName() {
		return factoryName;
	}

	/**
	 * 파서 유형의 이름을 설정합니다.
	 * 
	 * @param factoryName
	 *            파서 유형의 이름
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	/**
	 * 이 파서를 이용하여 파싱된 데이터의 스키마를 반환합니다.
	 * 
	 * @return 필드 정의 목록
	 */
	public List<TableField> getFieldDefinitions() {
		return fieldDefinitions;
	}

	/**
	 * 이 파서를 이용하여 파싱된 데이터의 스키마를 설정합니다.
	 * 
	 * @param fieldDefinitions
	 *            필드 정의 목록
	 */
	public void setFieldDefinitions(List<TableField> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}

	/**
	 * 파서 설정 목록을 반환합니다.
	 * 
	 * @return 파서 설정 목록
	 */
	public Map<String, String> getConfigs() {
		return configs;
	}

	/**
	 * 파서 설정 목록을 설정합니다. 설정 키/값 쌍은 파서 유형의 설정 명세를 따릅니다.
	 * 
	 * @param configs
	 *            파서 설정 목록
	 * @see ParserFactory#getConfigSpecs()
	 */
	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}

	@Override
	public String toString() {
		return "name=" + name + ", factory=" + factoryName + ", configs=" + configs;
	}
}
