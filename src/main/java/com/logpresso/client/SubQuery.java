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

import java.util.ArrayList;
import java.util.List;

/**
 * 서브 쿼리 상태를 표현합니다.
 * 
 * @since 0.9.1
 * @author xeraph@eediom.com
 */
public class SubQuery {
	private int id;
	private List<QueryCommand> commands = new ArrayList<QueryCommand>();

	/**
	 * 서브 쿼리 식별자를 반환합니다.
	 * 
	 * @return 서브 쿼리 식별자
	 */
	public int getId() {
		return id;
	}

	/**
	 * 서브 쿼리 식별자를 설정합니다.
	 * 
	 * @param id
	 *            서브 쿼리 식별자
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 서브 쿼리의 커맨드 목록을 반환합니다.
	 * 
	 * @return 쿼리 커맨드 목록
	 */
	public List<QueryCommand> getCommands() {
		return commands;
	}

	/**
	 * 서브 쿼리의 커맨드 목록을 설정합니다.
	 * 
	 * @param commands
	 *            쿼리 커맨드 목록
	 */
	public void setCommands(List<QueryCommand> commands) {
		this.commands = commands;
	}
}
