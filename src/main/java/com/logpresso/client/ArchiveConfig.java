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

import java.util.Map;

/**
 * 로그 수집기에서 수집되는 데이터를 실시간으로 테이블에 저장하도록 하는 테이블 저장 설정을 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class ArchiveConfig {
	private String loggerName;
	private String tableName;
	private String host;

	/**
	 * monitor primary logger and replicate primary logger status.
	 * node-name\namespace\name format.
	 * 
	 * @since 0.8.6
	 */
	private String primaryLogger;

	/**
	 * check backup logger status when system has been recovered.
	 * node-name\namespace\name format.
	 * 
	 * @since 0.8.6
	 */
	private String backupLogger;

	private Map<String, String> metadata;
	private boolean enabled;

	/**
	 * 로그 수집기 이름을 조회합니다.
	 * 
	 * @return 로그 수집기 이름, 이름공간\이름 형식.
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * 로그 수집기 이름을 설정합니다.
	 * 
	 * @param loggerName
	 *            로그 수집기 이름, 이름공간\이름 형식.
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	/**
	 * 테이블 이름을 반환합니다.
	 * 
	 * @return 테이블 이름
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 테이블 이름을 설정합니다.
	 * 
	 * @param tableName
	 *            테이블 이름
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 호스트 태그를 반환합니다.
	 * 
	 * @return 호스트 태그
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 호스트 태그를 설정합니다. 호스트 태그 설정 시 _host 필드가 설정한 값으로 테이블 행마다 추가됩니다.
	 * 
	 * @param host
	 *            호스트 태그
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 프라이머리 로그 수집기 이름을 반환합니다.
	 * 
	 * @return 프라이머리 로그 수집기 이름. 노드\이름공간\이름 형식.
	 */
	public String getPrimaryLogger() {
		return primaryLogger;
	}

	/**
	 * 프라이머리 로그 수집기 이름을 설정합니다.
	 * 
	 * @param primaryLogger
	 *            프라이머리 로그 수집기 이름. 노드\이름공간\이름 형식.
	 */
	public void setPrimaryLogger(String primaryLogger) {
		this.primaryLogger = primaryLogger;
	}

	/**
	 * 백업 로그 수집기 이름을 반환합니다.
	 * 
	 * @return 백업 로그 수집기 이름. 노드\이름공간\이름 형식.
	 */
	public String getBackupLogger() {
		return backupLogger;
	}

	/**
	 * 백업 로그 수집기 이름을 설정합니다.
	 * 
	 * @param backupLogger
	 *            백업 로그 수집기 이름. 노드\이름공간\이름 형식.
	 */
	public void setBackupLogger(String backupLogger) {
		this.backupLogger = backupLogger;
	}

	/**
	 * 메타데이터 목록을 반환합니다.
	 * 
	 * @return 메타데이터 목록
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * 메타데이터 목록을 설정합니다.
	 * 
	 * @param metadata
	 *            메타데이터 목록
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/**
	 * 로그 저장 활성화 여부를 반환합니다.
	 * 
	 * @return 활성화 여부
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 로그 저장 활성화 여부를 설정합니다.
	 * 
	 * @param enabled
	 *            활성화 여부
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "logger=" + loggerName + ", table=" + tableName + ", enabled=" + enabled + ", host=" + host + ", primary logger="
				+ primaryLogger + ", backup logger=" + backupLogger + ", metadata=" + metadata;
	}
}
