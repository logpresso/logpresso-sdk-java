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

import java.util.List;

/**
 * 인덱스 토크나이저를 생성하는데 필요한 설정 명세를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class IndexTokenizerFactory {
	private String name;
	private List<IndexConfigSpec> configSpecs;

	/**
	 * 인덱스 토크나이저 유형 이름을 반환합니다.
	 * 
	 * @return 인덱스 토크나이저 유형 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 인덱스 토크나이저 유형 이름을 설정합니다.
	 * 
	 * @param name
	 *            인덱스 토크나이저 유형 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 인덱스 토크나이저의 설정 명세 목록을 반환합니다.
	 * 
	 * @return 인덱스 토크나이저 설정 명세 목록
	 */
	public List<IndexConfigSpec> getConfigSpecs() {
		return configSpecs;
	}

	/**
	 * 인덱스 토크나이저의 설정 명세 목록을 설정합니다.
	 * 
	 * @param configSpecs
	 *            인덱스 토크나이저 설정 명세 목록
	 */
	public void setConfigSpecs(List<IndexConfigSpec> configSpecs) {
		this.configSpecs = configSpecs;
	}

	@Override
	public String toString() {
		return "name=" + name + ", config specs=" + configSpecs;
	}
}
