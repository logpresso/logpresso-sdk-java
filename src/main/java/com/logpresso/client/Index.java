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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 인덱스 설정을 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Index {
	private String tableName;
	private String indexName;
	private String tokenizerName;
	private Map<String, String> tokenizerConfigs = new HashMap<String, String>();
	private boolean useBloomFilter;
	private int bloomFilterCapacity0 = 1250000;
	private double bloomFilterErrorRate0 = 0.001f;
	private int bloomFilterCapacity1 = 10000000;
	private double bloomFilterErrorRate1 = 0.02f;
	private Date minIndexDay;
	private String basePath;
	private boolean buildPastIndex;

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
	 * 인덱스 이름을 반환합니다.
	 * 
	 * @return 인덱스 이름
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * 인덱스 이름을 설정합니다.
	 * 
	 * @param indexName
	 *            인덱스 이름
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * 인덱스 토크나이저 이름을 반환합니다.
	 * 
	 * @return 인덱스 토크나이저 이름
	 */
	public String getTokenizerName() {
		return tokenizerName;
	}

	/**
	 * 인덱스 토크나이저 이름을 설정합니다.
	 * 
	 * @param tokenizerName
	 *            인덱스 토크나이저 이름
	 */
	public void setTokenizerName(String tokenizerName) {
		this.tokenizerName = tokenizerName;
	}

	/**
	 * 인덱스 토크나이저 설정 목록을 반환합니다.
	 * 
	 * @return 인덱스 토크나이저 설정 목록
	 */
	public Map<String, String> getTokenizerConfigs() {
		return tokenizerConfigs;
	}

	/**
	 * 인덱스 토크나이저 설정 목록을 설정합니다.
	 * 
	 * @param tokenizerConfigs
	 *            인덱스 토크나이저 설정 목록
	 */
	public void setTokenizerConfigs(Map<String, String> tokenizerConfigs) {
		this.tokenizerConfigs = tokenizerConfigs;
	}

	/**
	 * 블룸필터 생성 여부를 반환합니다.
	 * 
	 * @return 블룸필터 생성 여부
	 */
	public boolean isUseBloomFilter() {
		return useBloomFilter;
	}

	/**
	 * 블룸필터 생성 여부를 설정합니다.
	 * 
	 * @param useBloomFilter
	 *            블룸필터 생성 여부
	 */
	public void setUseBloomFilter(boolean useBloomFilter) {
		this.useBloomFilter = useBloomFilter;
	}

	/**
	 * 레벨 0 블룸필터의 토큰 수용 갯수를 반환합니다.
	 * 
	 * @return 레벨 0 블룸필터의 토큰 수용 갯수
	 */
	public int getBloomFilterCapacity0() {
		return bloomFilterCapacity0;
	}

	/**
	 * 레벨 0 블룸필터의 토큰 수용 갯수를 설정합니다.
	 * 
	 * @param bloomFilterCapacity0
	 *            레벨 0 블룸필터의 토큰 수용 갯수
	 */
	public void setBloomFilterCapacity0(int bloomFilterCapacity0) {
		this.bloomFilterCapacity0 = bloomFilterCapacity0;
	}

	/**
	 * 레벨 0 블룸필터의 오류율을 반환합니다.
	 * 
	 * @return 레벨 0 블룸필터의 오류율 (0 &lt; x &lt; 1)
	 */
	public double getBloomFilterErrorRate0() {
		return bloomFilterErrorRate0;
	}

	/**
	 * 레벨 0 블룸필터의 오류율을 반환합니다.
	 * 
	 * @param bloomFilterErrorRate0
	 *            레벨 0 블룸필터의 오류율 (0 &lt; x &lt; 1)
	 */
	public void setBloomFilterErrorRate0(double bloomFilterErrorRate0) {
		this.bloomFilterErrorRate0 = bloomFilterErrorRate0;
	}

	/**
	 * 레벨 1 블룸필터의 토큰 수용 갯수를 반환합니다.
	 * 
	 * @return 레벨 1 블룸필터의 토큰 수용 갯수
	 */
	public int getBloomFilterCapacity1() {
		return bloomFilterCapacity1;
	}

	/**
	 * 레벨 1 블룸필터의 토큰 수용 갯수를 설정합니다.
	 * 
	 * @param bloomFilterCapacity1
	 *            레벨 1 블룸필터의 토큰 수용 갯수
	 */
	public void setBloomFilterCapacity1(int bloomFilterCapacity1) {
		this.bloomFilterCapacity1 = bloomFilterCapacity1;
	}

	/**
	 * 레벨 1 블룸필터의 오류율을 반환합니다.
	 * 
	 * @return 레벨 1 블룸필터의 오류율 (0 &lt; x &lt; 1)
	 */
	public double getBloomFilterErrorRate1() {
		return bloomFilterErrorRate1;
	}

	/**
	 * 레벨 1 블룸필터의 오류율을 반환합니다.
	 * 
	 * @param bloomFilterErrorRate1
	 *            레벨 1 블룸필터의 오류율 (0 &lt; x &lt; 1)
	 */
	public void setBloomFilterErrorRate1(double bloomFilterErrorRate1) {
		this.bloomFilterErrorRate1 = bloomFilterErrorRate1;
	}

	/**
	 * 인덱싱 대상 구간의 시작일자를 반환합니다.
	 * 
	 * @return 인덱스 구간의 시작일자
	 */
	public Date getMinIndexDay() {
		return minIndexDay;
	}

	/**
	 * 인덱싱 대상 구간의 시작일자를 설정합니다.
	 * 
	 * @param minIndexDay
	 *            인덱스 구간의 시작일자
	 */
	public void setMinIndexDay(Date minIndexDay) {
		this.minIndexDay = minIndexDay;
	}

	/**
	 * 인덱스 파티션 경로를 반환합니다.
	 * 
	 * @return 인덱스 파티션 경로
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * 인덱스 파티션 경로를 설정합니다.
	 * 
	 * @param basePath
	 *            인덱스 파티션 경로
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * 기존 데이터에 대한 인덱스 생성 여부를 반환합니다.
	 * 
	 * @return 기존 데이터에 대한 인덱스 생성 여부
	 */
	public boolean isBuildPastIndex() {
		return buildPastIndex;
	}

	/**
	 * 기존 데이터에 대한 인덱스 생성 여부를 설정합니다.
	 * 
	 * @param buildPastIndex
	 *            기존 데이터에 대한 인덱스 생성 여부
	 */
	public void setBuildPastIndex(boolean buildPastIndex) {
		this.buildPastIndex = buildPastIndex;
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String s = null;
		if (minIndexDay != null)
			s = f.format(minIndexDay);

		String bloomFilterConfig = "bloomfilter=" + useBloomFilter;
		if (useBloomFilter) {
			bloomFilterConfig += "[lv0: " + bloomFilterCapacity0 + ", " + bloomFilterErrorRate0 + ", ";
			bloomFilterConfig += "lv1: " + bloomFilterCapacity1 + ", " + bloomFilterErrorRate1 + "]";
		}

		return "table=" + tableName + ", index=" + indexName + "," + bloomFilterConfig + ", tokenizer=" + tokenizerName
				+ ", tokenizer configs=" + tokenizerConfigs + ", base path=" + basePath + ", min index day=" + s
				+ ", build past index=" + buildPastIndex;
	}

}
