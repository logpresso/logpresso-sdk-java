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
import java.util.Date;

/**
 * 스트림 쿼리 상태를 표현합니다.
 * 
 * @since 0.9.5
 * @author xeraph@eediom.com
 * 
 */
public class StreamQueryStatus {

	private StreamQuery query;
	private long inputCount;
	private Date lastRefresh;
	private boolean running;

	/**
	 * 스트림 쿼리 구성을 반환합니다.
	 * 
	 * @return 스트림 쿼리 설정
	 */
	public StreamQuery getStreamQuery() {
		return query;
	}

	/**
	 * 스트림 쿼리 구성을 설정합니다.
	 * 
	 * @param query
	 *            스트림 쿼리 구성
	 */
	public void setStreamQuery(StreamQuery query) {
		this.query = query;
	}

	/**
	 * 스트림 쿼리에 입력된 데이터 건수를 반환합니다.
	 * 
	 * @return 입력 데이터 건수
	 */
	public long getInputCount() {
		return inputCount;
	}

	/**
	 * 스트림 쿼리에 입력된 데이터 건수를 설정합니다.
	 * 
	 * @param inputCount
	 *            입력 데이터 건수
	 */
	public void setInputCount(long inputCount) {
		this.inputCount = inputCount;
	}

	/**
	 * 마지막으로 스트림 쿼리가 다시 생성된 시각을 반환합니다.
	 * 
	 * @return 마지막 새로고침 시각
	 */
	public Date getLastRefresh() {
		return lastRefresh;
	}

	/**
	 * 마지막으로 스트림 쿼리가 다시 생성된 시각을 설정합니다.
	 * 
	 * @param lastRefresh
	 *            마지막 새로고침 시각
	 */
	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}

	/**
	 * 스트림 쿼리의 동작 여부를 반환합니다. 쿼리 생성이 지연되거나 쿼리 생성이 실패하는 경우 활성화 이후에도 스트림 쿼리가 동작하지
	 * 않을 수 있습니다.
	 * 
	 * @return 스트림 쿼리 동작 여부
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * 스트림 쿼리의 동작 여부를 설정합니다.
	 * 
	 * @param running
	 *            스트림 쿼리 동작 여부
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "name=" + query.getName() + ", input=" + inputCount + ", last refresh=" + df.format(lastRefresh) + ", running="
				+ running;
	}

}
