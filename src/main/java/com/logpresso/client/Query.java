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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 쿼리 개체의 상태 정보를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Query {
	private Logpresso client;
	private int id;
	private String queryString;
	private String status;
	private long loadedCount;
	private boolean background;
	private int stamp;

	// @since 0.9.0
	private Date startTime;

	// @since 0.9.0
	private Date finishTime;

	// @since 1.0.0
	private Integer errorCode;
	private String errorDetail;

	private String cancelReason;

	private Long elapsed;
	private List<QueryCommand> commands = new ArrayList<QueryCommand>();
	private CopyOnWriteArrayList<WaitingCondition> waitingConditions;

	// @since 0.9.1
	private List<SubQuery> subQueries = new ArrayList<SubQuery>();

	// @since 1.1.0
	private List<String> fieldOrder;

	// @since 1.1.0
	private List<FieldSummary> fieldSummary;

	public Query(Logpresso client, int id, String queryString) {
		this.client = client;
		this.id = id;
		this.queryString = queryString;
		this.status = "Stopped";
		this.loadedCount = 0;
		this.waitingConditions = new CopyOnWriteArrayList<WaitingCondition>();
	}

	/**
	 * 쿼리 식별자를 반환합니다.
	 * 
	 * @return 쿼리 식별자
	 */
	public int getId() {
		return id;
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
	 * 쿼리 상태의 버전을 반환합니다. 숫자가 클수록 최신 정보입니다. 스탬프는 클라이언트와 서버 통신 간에 메시지 전달이 지연될 때 구
	 * 버전이 새 버전 정보를 덮어쓰는 일을 막는 용도로 사용됩니다.
	 * 
	 * @return 스탬프 값
	 */
	public int getStamp() {
		return stamp;
	}

	/**
	 * 쿼리 상태의 버전을 설정합니다.
	 * 
	 * @param stamp
	 *            스탬프 값
	 */
	public void setStamp(int stamp) {
		this.stamp = stamp;
	}

	/**
	 * 쿼리 결과가 적재된 건수를 반환합니다.
	 * 
	 * @return 쿼리 결과 적재 건수
	 */
	public long getLoadedCount() {
		return loadedCount;
	}

	/**
	 * 쿼리 동작 상태를 반환합니다. Waiting, Running, Stopped, Cancelled, Ended 중 하나의 상태를
	 * 가집니다.
	 * 
	 * @return 쿼리 동작 상태.
	 */
	public String getStatus() {
		return getStatus(false);
	}

	/**
	 * 쿼리 동작 상태를 반환합니다. Waiting, Running, Stopped, Cancelled, Ended 중 하나의 상태를
	 * 가집니다.
	 * 
	 * @param refresh
	 *            RPC를 통한 쿼리 상태 갱신 여부
	 * @since 0.8.3
	 */
	public String getStatus(boolean refresh) {
		if (refresh) {
			try {
				// comet client do not support refresh
				if (client != null)
					client.getQuery(id);
			} catch (IOException e) {
			}
		}

		return status;
	}

	/**
	 * 쿼리 결과가 지정된 건수 이상 준비될 때까지 스레드 실행을 차단합니다.
	 * 
	 * @param count
	 *            대기할 건수, null인 경우 쿼리 종료 시까지 대기
	 */
	public void waitUntil(Long count) {
		WaitingCondition cond = new WaitingCondition(count);
		try {
			waitingConditions.add(cond);
			synchronized (cond.signal) {
				try {
					while (!status.equals("Ended") && !status.equals("Cancelled") && (count == null || loadedCount < count))
						cond.signal.wait(100);
				} catch (InterruptedException e) {
				}
			}
		} finally {
			waitingConditions.remove(cond);
		}
	}

	/**
	 * 쿼리 결과 적재 건수를 갱신합니다.
	 * 
	 * @param count
	 *            쿼리 결과 적재 건수
	 * @param stamp
	 *            스탬프 버전
	 */
	public void updateCount(long count, long stamp) {
		if (stamp != 0 && this.stamp >= stamp)
			return;
		loadedCount = count;

		for (WaitingCondition cond : waitingConditions) {
			if (cond.threshold != null && cond.threshold <= loadedCount) {
				synchronized (cond.signal) {
					cond.signal.notifyAll();
				}
			}
		}
	}

	/**
	 * 쿼리 동작 상태를 갱신합니다.
	 * 
	 * @param status
	 *            쿼리 동작 상태
	 * @param stamp
	 *            스탬프 버전
	 */
	public void updateStatus(String status, long stamp) {
		if (stamp != 0 && this.stamp >= stamp)
			return;
		this.status = status;
		if (status.equals("Ended") || status.equals("Cancelled")) {
			for (WaitingCondition cond : waitingConditions) {
				synchronized (cond.signal) {
					cond.signal.notifyAll();
				}
			}
		}
	}

	/**
	 * 백그라운드 실행 여부를 반환합니다.
	 * 
	 * @return 백그라운드 실행 여부
	 */
	public boolean isBackground() {
		return background;
	}

	/**
	 * 백그라운드 실행 여부를 설정합니다.
	 * 
	 * @param background
	 *            백그라운드 실행 여부
	 */
	public void setBackground(boolean background) {
		this.background = background;
	}

	/**
	 * 쿼리 시작 시각을 반환합니다.
	 * 
	 * @return 쿼리 시작 시각
	 * @since 0.9.0
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 쿼리 시작 시각을 설정합니다.
	 * 
	 * @param startTime
	 *            쿼리 시작 시각
	 * @since 0.9.0
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 쿼리 종료 시각을 반환합니다.
	 * 
	 * @return 쿼리 종료 시각
	 * @since 0.9.0
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * 쿼리 종료 시작을 설정합니다.
	 * 
	 * @param finishTime
	 *            쿼리 종료 시각
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * 쿼리 시작 후 경과된 시간을 반환합니다.
	 * 
	 * @return 쿼리 시작 후 경과 시간 (밀리초)
	 */
	public Long getElapsed() {
		return elapsed;
	}

	/**
	 * 쿼리 시작 후 경과된 시각을 설정합니다.
	 * 
	 * @param elapsed
	 *            쿼리 시작 후 경과 시간 (밀리초)
	 */
	public void setElapsed(Long elapsed) {
		this.elapsed = elapsed;
	}

	/**
	 * 쿼리를 구성하는 커맨드 목록을 반환합니다.
	 * 
	 * @return 쿼리 커맨드 목록
	 */
	public List<QueryCommand> getCommands() {
		return commands;
	}

	/**
	 * 쿼리를 구성하는 커맨드 목록을 설정합니다.
	 * 
	 * @param commands
	 *            쿼리 커맨드 목록
	 */
	public void setCommands(List<QueryCommand> commands) {
		this.commands = commands;
	}

	/**
	 * 서브 쿼리 목록을 반환합니다.
	 * 
	 * @return 서브 쿼리 목록
	 */
	public List<SubQuery> getSubQueries() {
		return subQueries;
	}

	/**
	 * 서브 쿼리 목록을 설정합니다.
	 * 
	 * @param subQueries
	 *            서브 쿼리 목록
	 */
	public void setSubQueries(List<SubQuery> subQueries) {
		this.subQueries = subQueries;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public List<String> getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(List<String> fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	public List<FieldSummary> getFieldSummary() {
		return fieldSummary;
	}

	public void setFieldSummary(List<FieldSummary> fieldSummary) {
		this.fieldSummary = fieldSummary;
	}

	private class WaitingCondition {
		private Long threshold;
		private Object signal = new Object();

		public WaitingCondition(Long threshold) {
			this.threshold = threshold;
		}
	}

	@Override
	public String toString() {
		return "id=" + id + ", query=" + queryString + ", status=" + status + ", loaded=" + loadedCount;
	}

}
