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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.araqne.api.DateFormat;

/**
 * 로그 수집기 인스턴스의 상태 정보를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Logger {
	private String factoryName;
	private String namespace;
	private String name;

	/**
	 * @since 1.0.8
	 */
	private boolean enabled;

	private String description;
	private boolean passive;
	private int interval;
	private String cronSchedule;
	private String startTime;
	private String endTime;
	private String status;
	private Date lastStartAt;
	private Date lastRunAt;
	private Date lastLogAt;
	private long logCount;
	private long dropCount;
	private long updateCount;

	private Map<String, String> configs = new HashMap<String, String>();
	private Map<String, Object> states = new HashMap<String, Object>();

	/**
	 * 로그 수집기와 연관된 로그 수집기 유형의 이름을 반환합니다.
	 * 
	 * @return 로그 수집기 유형의 이름
	 */
	public String getFactoryName() {
		return factoryName;
	}

	/**
	 * 로그 수집기와 연관된 로그 수집기 유형의 이름을 설정합니다.
	 * 
	 * @param factoryName
	 *            로그 수집기 유형의 이름
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	/**
	 * 로그 수집기의 완전한 이름을 반환합니다.
	 * 
	 * @return 완전한 이름. 이름공간\이름 형식.
	 */
	public String getFullName() {
		return namespace + "\\" + name;
	}

	/**
	 * 로그 수집기의 이름공간을 반환합니다.
	 * 
	 * @return 이름공간. 로컬인 경우 local, 원격 장비인 경우 원격 장비의 식별자가 GUID로 설정됨.
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * 로그 수집기의 이름공간을 설정합니다.
	 * 
	 * @param namespace
	 *            이름공간. 로컬인 경우 local, 원격 장비인 경우 원격 장비의 식별자가 GUID로 설정됨.
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 로그 수집기의 이름을 반환합니다.
	 * 
	 * @return 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 로그 수집기의 이름을 설정합니다.
	 * 
	 * @param name
	 *            이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 로거의 활성화 여부를 반환합니다.
	 * 
	 * @since 1.0.8
	 * @return 활성화 여부
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 로거의 활성화 여부를 설정합니다.
	 * 
	 * @since 1.0.8
	 * @param enabled
	 *            활성화 여부
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 로그 수집기에 대한 설명을 반환합니다.
	 * 
	 * @return 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 로그 수집기에 대한 설명을 설정합니다.
	 * 
	 * @param description
	 *            설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 로그 수집기의 설정 목록을 반환합니다.
	 * 
	 * @return 설정 목록
	 */
	public Map<String, String> getConfigs() {
		return configs;
	}

	/**
	 * 로그 수집기의 설정 목록을 설정합니다. 설정 키/값 쌍은 로그 수집기 유형의 설정 명세를 따릅니다.
	 * 
	 * @param configs
	 *            설정 목록
	 * @see LoggerFactory#getConfigSpecs()
	 */
	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}

	/**
	 * 로그 수집기의 수집 상태를 반환합니다.
	 * 
	 * @return 로그 수집기의 수집 상태
	 */
	public Map<String, Object> getStates() {
		return states;
	}

	/**
	 * 로그 수집기의 수집 상태를 설정합니다.
	 * 
	 * @param states
	 *            로그 수집기의 수집 상태
	 */
	public void setStates(Map<String, Object> states) {
		this.states = states;
	}

	/**
	 * 패시브 로그 수집기 여부를 반환합니다.
	 * 
	 * @return 패시브인 경우 true, 액티브인 경우 false
	 */
	public boolean isPassive() {
		return passive;
	}

	/**
	 * 패시브 로그 수집기 여부를 설정합니다.
	 * 
	 * @param passive
	 *            패시브인 경우 true, 액티브인 경우 false
	 */
	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	/**
	 * 밀리초 단위의 로그 수집 주기를 반환합니다.
	 * 
	 * @return 로그 수집 주기 (밀리초)
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * 밀리초 단위의 로그 수집 주기를 설정합니다. 패시브 로그 수집기의 경우 수집 주기를 0으로 지정합니다.
	 * 
	 * @param interval
	 *            로그 수집 주기 (밀리초)
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * CRON 문법으로 정의된 수집 일정을 반환합니다.
	 * 
	 * @return CRON 표현식
	 */
	public String getCronSchedule() {
		return cronSchedule;
	}

	/**
	 * CRON 문법으로 정의된 수집 일정을 설정합니다.
	 * 
	 * @param cronSchedule
	 *            수집 일정 CRON 표현식
	 */
	public void setCronSchedule(String cronSchedule) {
		this.cronSchedule = cronSchedule;
	}

	/**
	 * 수집 시작 시각을 반환합니다. 액티브 로거인 경우에만 설정이 유효합니다.
	 * 
	 * @return HHmm 포맷의 수집 시작 시각
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 수집 시작 시각을 설정합니다. 액티브 로거의 경우에만 설정이 유효합니다.
	 * 
	 * @param startTime
	 *            수집 시작 시각 (HHmm 포맷)
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 수집 종료 시각을 반환합니다. 액티브 로거인 경우에만 설정이 유효합니다.
	 * 
	 * @return HHmm 포맷의 수집 시작 시각
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * 수집 종료 시각을 설정합니다. 액티브 로거의 경우에만 설정이 유효합니다.
	 * 
	 * @param endTime
	 *            수집 시작 시각 (HHmm 포맷)
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 로그 수집기 동작 상태를 반환합니다.
	 * 
	 * @return 동작 중이면 running, 정지되어 있으면 stopped
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 로그 수집기 동작 상태를 설정합니다.
	 * 
	 * @param status
	 *            동작 중이면 running, 정지되어 있으면 stopped
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 로그 수집기를 마지막으로 시작시킨 시각을 반환합니다.
	 * 
	 * @return 마지막 시작 시각, 시작시킨 적이 없는 경우 null을 반환.
	 */
	public Date getLastStartAt() {
		return lastStartAt;
	}

	/**
	 * 로그 수집기를 마지막으로 시작시킨 시각을 설정합니다.
	 * 
	 * @param lastStartAt
	 *            마지막 시작 시각
	 */
	public void setLastStartAt(Date lastStartAt) {
		this.lastStartAt = lastStartAt;
	}

	/**
	 * 로그 수집기가 마지막으로 실행된 시각을 반환합니다.
	 * 
	 * @return 마지막 실행 시각, 실행한 적이 없는 경우 null을 반환.
	 */
	public Date getLastRunAt() {
		return lastRunAt;
	}

	/**
	 * 로그 수집기가 마지막으로 실행된 시각을 설정합니다.
	 * 
	 * @param lastRunAt
	 *            마지막 실행 시각
	 */
	public void setLastRunAt(Date lastRunAt) {
		this.lastRunAt = lastRunAt;
	}

	/**
	 * 로그 수집기가 수집한 마지막 로그의 시각을 반환합니다. 이 시각은 로그의 타임스탬프를 인식시킨 경우 로그프레소 서버의 시각과
	 * 관계없이 데이터에 기록된 시각이 표시됩니다.
	 * 
	 * @return 마지막 로그의 시각
	 */
	public Date getLastLogAt() {
		return lastLogAt;
	}

	/**
	 * 로그 수집기가 수집한 마지막 로그의 시각을 설정합니다.
	 * 
	 * @param lastLogAt
	 *            마지막 로그의 시각, 로그를 수집한 적이 없으면 null을 반환.
	 */
	public void setLastLogAt(Date lastLogAt) {
		this.lastLogAt = lastLogAt;
	}

	/**
	 * 수집한 누적 로그 건수를 반환합니다.
	 * 
	 * @return 누적 로그 수집 건수
	 */
	public long getLogCount() {
		return logCount;
	}

	/**
	 * 수집한 누적 로그 건수를 설정합니다.
	 * 
	 * @param logCount
	 *            로그 수집 건수 (누적)
	 */
	public void setLogCount(long logCount) {
		this.logCount = logCount;
	}

	/**
	 * 버린 누적 로그 건수를 반환합니다.
	 * 
	 * @return 버린 로그 건수 (누적)
	 */
	public long getDropCount() {
		return dropCount;
	}

	/**
	 * 버린 로그 건수를 설정합니다.
	 * 
	 * @param dropCount
	 *            버린 로그 건수 (누적)
	 */
	public void setDropCount(long dropCount) {
		this.dropCount = dropCount;
	}

	/**
	 * 로그 수집 상태 갱신 횟수를 반환합니다. HA 시 최신 버전을 확인하는 용도로 사용됩니다.
	 * 
	 * @return 로그 수집 상태 갱신 횟수
	 */
	public long getUpdateCount() {
		return updateCount;
	}

	/**
	 * 로그 수집 상태 갱신 횟수를 설정합니다.
	 * 
	 * @param updateCount
	 *            로그 수집 상태 갱신 횟수
	 */
	public void setUpdateCount(long updateCount) {
		this.updateCount = updateCount;
	}

	@Override
	public String toString() {
		String format = "yyyy-MM-dd HH:mm:ss";
		String start = DateFormat.format(format, lastStartAt);
		String run = DateFormat.format(format, lastRunAt);
		String log = DateFormat.format(format, lastLogAt);
		String status = getStatus().toString().toLowerCase();
		if (passive)
			status += " (passive)";
		else
			status += " (interval=" + interval + "ms)";

		String details = "";
		if (configs != null && configs.size() > 0)
			details += ", configs=" + configs;

		if (states != null && states.size() > 0)
			details += ", states=" + states;

		return String.format("name=%s, factory=%s, status=%s, log count=%d, last start=%s, last run=%s, last log=%s" + details,
				getFullName(), factoryName, status, getLogCount(), start, run, log);
	}
}
