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
 * 설정 키, 이름, 설명, 필수 설정 여부로 구성된 인덱스의 설정 항목 명세를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class IndexConfigSpec {
	private String key;
	private boolean required;
	private String name;
	private String description;

	/**
	 * 인덱스 설정 키를 반환합니다.
	 * 
	 * @return 설정 키
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 인덱스 설정 키를 설정합니다.
	 * 
	 * @param key
	 *            설정 키
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 설정 필수 여부를 반환합니다.
	 * 
	 * @return 설정 필수 여부
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * 설정 필수 여부를 설정합니다.
	 * 
	 * @param required
	 *            설정 필수 여부
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * UI 표시 이름을 반환합니다.
	 * 
	 * @return UI 표시 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * UI 표시 이름을 설정합니다.
	 * 
	 * @param name
	 *            UI 표시 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * UI 표시 설명을 반환합니다.
	 * 
	 * @return UI 표시 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * UI 표시 설명을 설정합니다.
	 * 
	 * @param description
	 *            UI 표시 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "key=" + key + ", required=" + required + ", name=" + name + ", description=" + description;
	}
}
