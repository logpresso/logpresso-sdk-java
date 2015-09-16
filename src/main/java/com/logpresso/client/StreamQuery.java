/**
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 스트림 쿼리의 설정과 상태를 표현합니다.
 * 
 * @since 0.9.5
 * @author xeraph@eediom.com
 * 
 */
public class StreamQuery {
	private String name;
	private String description;

	private int interval;
	private String queryString;

	// logger, table, or stream
	private String sourceType;

	private List<String> sources = new ArrayList<String>();

	private String owner;
	private boolean enabled;
	private Date created = new Date();
	private Date modified = new Date();

	/**
	 * 스트림 쿼리 이름을 반환합니다.
	 * 
	 * @return 스트림 쿼리 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 스트림 쿼리 이름을 설정합니다.
	 * 
	 * @param name
	 *            스트림 쿼리 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 스트림 쿼리에 대한 설명을 반환합니다.
	 * 
	 * @return 스트림 쿼리 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 스트림 쿼리에 대한 설명을 설정합니다.
	 * 
	 * @param description
	 *            스트림 쿼리 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 스트림 쿼리의 새로고침 주기를 반환합니다.
	 * 
	 * @return 새로고침 주기 (초)
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * 스트림 쿼리의 새로고침 주기를 설정합니다. 새로고침 주기를 0으로 설정하려면 스트림 쿼리를 구성하는 모든 커맨드가 스트리밍 가능한
	 * 커맨드이어야 합니다. stats나 sort 같은 커맨드는 입력 끝이 있어야 작업이 완료되므로 0보다 큰 새로고침 주기를 필요로
	 * 합니다.
	 * 
	 * @param interval
	 *            새로고침 주기 (초)
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * 쿼리 문자열을 반환합니다.
	 * 
	 * @return 쿼리 문자열
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * 쿼리 문자열을 설정합니다.
	 * 
	 * @param queryString
	 *            쿼리 문자열
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * 스트림 쿼리의 실시간 입력 유형을 반환합니다. 입력 유형은 logger, table, stream 중 하나입니다.
	 * 
	 * @return 실시간 입력 유형
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * 스트림 쿼리의 실시간 입력 유형을 설정합니다. 입력 유형은 logger, table, stream 중 하나입니다.
	 * 
	 * @param sourceType
	 *            실시간 입력 유형
	 */
	public void setSourceType(String sourceType) {
		if (sourceType != null && !sourceType.equals("logger") && !sourceType.equals("table") && !sourceType.equals("stream"))
			throw new IllegalArgumentException();

		this.sourceType = sourceType;
	}

	/**
	 * 스트림 쿼리의 입력 데이터 원본의 목록을 반환합니다.
	 * 
	 * @return 데이터 원본 목록
	 */
	public List<String> getSources() {
		return sources;
	}

	/**
	 * 스트림 쿼리의 입력 데이터 원본의 목록을 설정합니다.
	 * 
	 * @param sources
	 *            데이터 원본 목록
	 */
	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	/**
	 * 스트림 쿼리의 소유자를 반환합니다.
	 * 
	 * @return 스트림 쿼리의 소유자
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * 스트림 쿼리의 소유자를 설정합니다.
	 * 
	 * @param owner
	 *            스트림 쿼리의 소유자
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * 스트림 쿼리의 활성화 여부를 반환합니다.
	 * 
	 * @return 스트림 쿼리의 활성화 여부
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 스트림 쿼리의 활성화 여부를 설정합니다.
	 * 
	 * @param enabled
	 *            스트림 쿼리의 활성화 여부
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 스트림 쿼리의 생성일자를 반환합니다.
	 * 
	 * @return 스트림 쿼리의 생성일자
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * 스트림 쿼리의 생성일자를 설정합니다.
	 * 
	 * @param created
	 *            스트림 쿼리의 생성일자
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * 스트림 쿼리의 마지막 수정일자를 반환합니다.
	 * 
	 * @return 스트림 쿼리의 마지막 수정일자
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * 스트림 쿼리의 마지막 수정일자를 설정합니다.
	 * 
	 * @param modified
	 *            스트림 쿼리의 마지막 수정일자
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "name=" + name + ", interval=" + interval + ", query=" + queryString + ", source_type=" + sourceType
				+ ", sources=" + sources + ", owner=" + owner + ", enabled=" + enabled + ", created=" + df.format(created)
				+ ", modified=" + df.format(modified) + ", description=" + description;
	}

}
