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
 * 파서를 생성하는데 필요한 설정 명세를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class ParserFactory {
	private String name;
	private String displayName;
	private String description;
	private List<ConfigSpec> configSpecs = new ArrayList<ConfigSpec>();

	/**
	 * 파서 유형의 이름을 반환합니다.
	 * 
	 * @return 파서 유형의 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 파서 유형의 이름을 설정합니다.
	 * 
	 * @param name
	 *            파서 유형의 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 파서 유형의 UI 표시 이름을 반환합니다.
	 * 
	 * @return UI 표시 이름
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * 파서 유형의 UI 표시 이름을 설정합니다.
	 * 
	 * @param displayName
	 *            UI 표시 이름
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 파서 유형의 UI 표시 설명을 반환합니다.
	 * 
	 * @return UI 표시 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 파서 유형의 UI 표시 설명을 설정합니다.
	 * 
	 * @param description
	 *            UI 표시 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 파서를 생성하는데 필요한 설정 명세 목록을 반환합니다.
	 * 
	 * @return 파서 설정 명세 목록
	 */
	public List<ConfigSpec> getConfigSpecs() {
		return configSpecs;
	}

	/**
	 * 파서를 생성하는데 필요한 설정 명세 목록을 설정합니다.
	 * 
	 * @param configSpecs
	 *            파서 설정 명세 목록
	 */
	public void setConfigSpecs(List<ConfigSpec> configSpecs) {
		this.configSpecs = configSpecs;
	}

	@Override
	public String toString() {
		return "name=" + name + ", description=" + description + ", config specs=" + configSpecs;
	}
}
