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
 * 쿼리 파이프라인을 구성하는 개별 커맨드의 정보를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class QueryCommand {
	private String name;
	private String command;
	private String status;
	private long pushCount;

	/**
	 * @since 0.9.1
	 */
	private List<QueryCommand> commands = new ArrayList<QueryCommand>();

	/**
	 * 쿼리 커맨드 이름을 반환합니다.
	 * 
	 * @return 쿼리 커맨드 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 쿼리 커맨드 이름을 설정합니다.
	 * 
	 * @param name
	 *            쿼리 커맨드 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 커맨드 문자열을 반환합니다.
	 * 
	 * @return 커맨드 문자열
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * 커맨드 문자열을 설정합니다.
	 * 
	 * @param command
	 *            커맨드 문자열
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * 커맨드 동작 상태를 반환합니다.
	 * 
	 * @return 커맨드 동작 상태
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 커맨드 동작 상태를 설정합니다.
	 * 
	 * @param status
	 *            커맨드 동작 상태
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 다음 단계의 커맨드로 넘긴 데이터 건수를 반환합니다.
	 * 
	 * @return 넘긴 데이터 건수
	 */
	public long getPushCount() {
		return pushCount;
	}

	/**
	 * 다음 단계의 커맨드로 넘긴 데이터 건수를 설정합니다.
	 * 
	 * @param pushCount
	 *            넘긴 데이터 건수
	 */
	public void setPushCount(long pushCount) {
		this.pushCount = pushCount;
	}

	/**
	 * 쿼리 커맨드 목록을 반환합니다.
	 * 
	 * @since 0.9.1
	 */
	public List<QueryCommand> getCommands() {
		return commands;
	}

	/**
	 * 쿼리 커맨드 목록을 설정합니다.
	 * 
	 * @param commands
	 *            쿼리 커맨드 목록
	 * @since 0.9.1
	 */
	public void setCommands(List<QueryCommand> commands) {
		this.commands = commands;
	}

	@Override
	public String toString() {
		return "[" + status + "] " + command + " - passed " + pushCount;
	}
}
