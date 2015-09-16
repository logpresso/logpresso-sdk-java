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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 테이블 스키마를 구성하는 개별 필드의 정보를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class TableField {

	private String name;
	private String type;

	// 0 for unknown
	private int length;
	
	public static TableField parse(String s) {
		if (s == null)
			throw new IllegalArgumentException("field definition should not be null");

		Pattern p = Pattern.compile("(\\S+)\\s+([^() ]+)(?:\\s*\\(\\s*(\\d+)\\s*\\))*");
		Matcher m = p.matcher(s);
		if (!m.find())
			throw new IllegalStateException("invalid field definition format: " + s);

		String fieldName = m.group(1);
		String type = m.group(2);
		int len = 0;
		if (m.group(3) != null)
			len = Integer.valueOf(m.group(3));

		return new TableField(fieldName, type, len);
	}

	public TableField() {
	}

	public TableField(String name, String type) {
		this(name, type, 0);
	}

	public TableField(String name, String type, int length) {
		this.name = name;
		this.type = type;
		this.length = length;
	}

	/**
	 * 필드 이름을 반환합니다.
	 * 
	 * @return 필드 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 필드 이름을 설정합니다.
	 * 
	 * @param name
	 *            필드 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 필드 타입을 반환합니다.
	 * 
	 * @return 필드 타입
	 */
	public String getType() {
		return type;
	}

	/**
	 * 필드 타입을 설정합니다.
	 * 
	 * @param type
	 *            필드 타입
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 필드 길이를 반환합니다.
	 * 
	 * @return 필드 길이
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 필드 길이를 설정합니다.
	 * 
	 * @param length
	 *            필드 길이
	 */
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		if (length > 0)
			return name + " " + type + "(" + length + ")";
		return name + " " + type;
	}
}
