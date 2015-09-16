package com.logpresso.client;

import java.util.HashMap;
import java.util.Map;

/**
 * 트랜스포머 인스턴스를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class Transformer {
	private String name;
	private String factoryName;
	private Map<String, String> configs = new HashMap<String, String>();

	/**
	 * 트랜스포머 이름을 반환합니다.
	 * 
	 * @return 트랜스포머 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 트랜스포머 이름을 설정합니다.
	 * 
	 * @param name
	 *            트랜스포머 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 트랜스포머 유형의 이름을 반환합니다.
	 * 
	 * @return 트랜스포머 유형의 이름
	 */
	public String getFactoryName() {
		return factoryName;
	}

	/**
	 * 트랜스포머 유형의 이름을 설정합니다.
	 * 
	 * @param factoryName
	 *            트랜스포머 유형의 이름
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	/**
	 * 트랜스포머 설정 목록을 반환합니다.
	 * 
	 * @return 트랜스포머 설정 목록
	 */
	public Map<String, String> getConfigs() {
		return configs;
	}

	/**
	 * 트랜스포머 설정 목록을 설정합니다. 설정 키/값 쌍은 트랜스포머 유형의 설정 명세를 따릅니다.
	 * 
	 * @param configs
	 *            트랜스포머 설정 목록.
	 * @see TransformerFactory#getConfigSpecs()
	 */
	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}

	@Override
	public String toString() {
		return "name=" + name + ", factory=" + factoryName + ", configs=" + configs;
	}
}
