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
import java.util.UUID;

/**
 * 예약된 쿼리를 표현합니다.
 * 
 * @since 0.9.5
 * @author xeraph@eediom.com
 * 
 */
public class ScheduledQuery {
	private String guid = UUID.randomUUID().toString();
	private String title;
	private String cronSchedule;
	private String owner;
	private String queryString;
	private boolean saveResult;
	private boolean useAlert;
	private String alertQuery;

	private int suppressInterval;
	private String mailProfile;
	private String mailFrom;
	private String mailTo;
	private String mailSubject;

	private boolean enabled = true;
	private Date created = new Date();

	/**
	 * 예약된 쿼리의 식별자를 반환합니다.
	 * 
	 * @return 예약된 쿼리 식별자
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 예약된 쿼리 식별자를 설정합니다.
	 * 
	 * @param guid
	 *            예약된 쿼리 식별자
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 예약된 쿼리의 이름을 반환합니다.
	 * 
	 * @return 예약된 쿼리의 이름
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 예약된 쿼리의 이름을 설정합니다.
	 * 
	 * @param title
	 *            예약된 쿼리의 이름
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * CRON 스케줄을 반환합니다.
	 * 
	 * @return CRON 스케줄
	 */
	public String getCronSchedule() {
		return cronSchedule;
	}

	/**
	 * CRON 스케줄을 설정합니다.
	 * 
	 * @param cronSchedule
	 *            CRON 스케줄
	 */
	public void setCronSchedule(String cronSchedule) {
		this.cronSchedule = cronSchedule;
	}

	/**
	 * 예약된 쿼리의 소유자 계정을 반환합니다.
	 * 
	 * @return 소유자 계정
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * 예약된 쿼리의 소유자 계정을 설정합니다.
	 * 
	 * @param owner
	 *            소유자 계정
	 */
	public void setOwner(String owner) {
		this.owner = owner;
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
	 *            쿼리문자열
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * 예약된 쿼리의 실행 결과의 저장 여부를 반환합니다.
	 * 
	 * @return 예약된 쿼리의 결과 저장 여부
	 */
	public boolean isSaveResult() {
		return saveResult;
	}

	/**
	 * 예약된 쿼리의 실행 결과의 저장 여부를 설정합니다.
	 * 
	 * @param saveResult
	 *            예약된 쿼리의 결과 저장 여부
	 */
	public void setSaveResult(boolean saveResult) {
		this.saveResult = saveResult;
	}

	/**
	 * 경보 기능 사용 여부를 반환합니다.
	 * 
	 * @return 경보 기능 사용 여부
	 */
	public boolean isUseAlert() {
		return useAlert;
	}

	/**
	 * 경보 기능 사용 여부를 설정합니다.
	 * 
	 * @param useAlert
	 *            경보 기능 사용 여부
	 */
	public void setUseAlert(boolean useAlert) {
		this.useAlert = useAlert;
	}

	/**
	 * 경보 쿼리 문자열을 반환합니다.
	 * 
	 * @return 경보 쿼리 문자열
	 */
	public String getAlertQuery() {
		return alertQuery;
	}

	/**
	 * 경보 쿼리 문자열을 설정합니다.
	 * 
	 * @param alertQuery
	 *            경보 쿼리 문자열
	 */
	public void setAlertQuery(String alertQuery) {
		this.alertQuery = alertQuery;
	}

	/**
	 * 경보 발생 후 동일 경보 발생 시 무시할 주기를 반환합니다.
	 * 
	 * @return 경보 무시 기간 (밀리초)
	 */
	public int getSuppressInterval() {
		return suppressInterval;
	}

	/**
	 * 경보 발생 후 동일 경보 발생 시 무시할 주기를 설정합니다.
	 * 
	 * @param suppressInterval
	 *            경보 무시 기간 (밀리초)
	 */
	public void setSuppressInterval(int suppressInterval) {
		this.suppressInterval = suppressInterval;
	}

	/**
	 * 경보 메일 전송 시 사용할 SMTP 프로파일 이름을 반환합니다.
	 * 
	 * @return SMTP 프로파일 이름
	 */
	public String getMailProfile() {
		return mailProfile;
	}

	/**
	 * 경보 메일 전송 시 사용할 SMTP 프로파일 이름을 설정합니다.
	 * 
	 * @param mailProfile
	 *            SMTP 프로파일 이름
	 */
	public void setMailProfile(String mailProfile) {
		this.mailProfile = mailProfile;
	}

	/**
	 * 보낸 사람 메일 주소를 반환합니다.
	 * 
	 * @return 보낸 사람 메일 주소
	 */
	public String getMailFrom() {
		return mailFrom;
	}

	/**
	 * 보낸 사람 메일 주소를 설정합니다.
	 * 
	 * @param mailFrom
	 *            보낸 사람 메일 주소
	 */
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * 받는 사람 메일 주소를 반환합니다.
	 * 
	 * @return 받는 사람 메일 주소
	 */
	public String getMailTo() {
		return mailTo;
	}

	/**
	 * 받는 사람 메일 주소를 설정합니다.
	 * 
	 * @param mailTo
	 *            받는 사람 메일 주소
	 */
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	/**
	 * 경보 메일의 제목 템플릿을 반환합니다.
	 * 
	 * @return 경보 메일의 제목 템플릿
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * 경보 메일의 제목 템플릿을 설정합니다.
	 * 
	 * @param mailSubject
	 *            경보 메일의 제목 템플릿
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * 예약된 쿼리의 활성화 여부를 반환합니다.
	 * 
	 * @return 예약된 쿼리의 활성화 여부
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 예약된 쿼리의 활성화 여부를 설정합니다.
	 * 
	 * @param enabled
	 *            예약된 쿼리의 활성화 여부
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 예약된 쿼리의 생성일자를 반환합니다.
	 * 
	 * @return 예약된 쿼리의 생성일자
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * 예약된 쿼리의 생성일자를 설정합니다.
	 * 
	 * @param created
	 *            예약된 쿼리의 생성일자
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "guid=" + guid + ", title=" + title + ", cron=" + cronSchedule + ", owner=" + owner + ", query=" + queryString
				+ ", save_result=" + saveResult + ", use_alert=" + useAlert + ", alert_query=" + alertQuery
				+ ", suppress_interval=" + suppressInterval + ", mail_profile=" + mailProfile + ", mail_from=" + mailFrom
				+ ", mail_to=" + mailTo + ", mail_subject=" + mailSubject + ", enabled=" + enabled + ", created="
				+ df.format(created);
	}
}
