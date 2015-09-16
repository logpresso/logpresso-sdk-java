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
 * 이름, 설명, 화면 표시 이름, 필수 설정 여부, 기본값으로 구성된 설정 항목 명세를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class ConfigSpec {
	private String name;
	private String description;
	private String displayName;
	private String type;
	private boolean required;
	private String defaultValue;

	/**
	 * 설정 키를 반환합니다.
	 * 
	 * @return 설정 키
	 */
	public String getName() {
		return name;
	}

	/**
	 * 설정 키를 설정합니다.
	 * 
	 * @param name
	 *            설정 키
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 설정 항목에 대한 설명을 반환합니다.
	 * 
	 * @return 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 설정 항목에 대한 설명을 설정합니다.
	 * 
	 * @param description
	 *            설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * UI에 표시할 설정 항목의 이름을 표시합니다.
	 * 
	 * @return UI 표시 이름
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * UI에 표시할 설정 항목의 이름을 설정합니다.
	 * 
	 * @param displayName
	 *            UI 표시 이름
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 설정 항목의 타입을 반환합니다.
	 * 
	 * @return string 혹은 integer
	 */
	public String getType() {
		return type;
	}

	/**
	 * 설정 항목의 타입을 설정합니다.
	 * 
	 * @param type
	 *            string 혹은 integer
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 필수 설정 여부를 반환합니다.
	 * 
	 * @return 필수인 경우 true, 선택인 경우 false
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * 필수 설정 여부를 설정합니다.
	 * 
	 * @param required
	 *            필수인 경우 true, 선택인 경우 false
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * 설정 항목의 기본값을 반환합니다.
	 * 
	 * @return 기본값
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 설정 항목의 기본값을 설정합니다.
	 * 
	 * @param defaultValue
	 *            기본값
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "{name=" + name + ", description=" + description + ", display name=" + displayName + ", type=" + type
				+ ", required=" + required + ", default value=" + defaultValue + "}";
	}

}
