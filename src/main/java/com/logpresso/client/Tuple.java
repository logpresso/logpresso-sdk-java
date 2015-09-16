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
import java.util.Map;

/**
 * 테이블의 단위 데이터 항목을 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Tuple {
	private final Map<String, Object> map;

	public Tuple() {
		this(new HashMap<String, Object>());
	}

	public Tuple(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * 지정한 필드의 값을 반환합니다.
	 * 
	 * @param key
	 *            필드 이름
	 * @return 필드 값
	 */
	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * 필드를 할당합니다.
	 * 
	 * @param key
	 *            필드 이름
	 * @param value
	 *            필드 값
	 */
	public void put(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * 필드를 삭제합니다.
	 * 
	 * @param key
	 *            필드 이름
	 * @return 필드 삭제 여부
	 */
	public Object remove(String key) {
		return map.remove(key);
	}

	/**
	 * 필드 포함 여부를 반환합니다.
	 * 
	 * @param key
	 *            필드 이름
	 * @return 필드 포함 여부
	 */
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	/**
	 * 전체 필드 키/값 쌍을 반환합니다.
	 * 
	 * @return 필드 키/값 쌍
	 */
	public Map<String, Object> toMap() {
		return map;
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
