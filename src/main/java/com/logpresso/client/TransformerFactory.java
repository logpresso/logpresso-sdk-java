package com.logpresso.client;

import java.util.ArrayList;
import java.util.List;

/**
 * 트랜스포머를 생성하는데 필요한 설정 명세를 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class TransformerFactory {
	private String name;
	private String displayName;
	private String description;
	private List<ConfigSpec> configSpecs = new ArrayList<ConfigSpec>();

	/**
	 * 트랜스포머 유형 이름을 반환합니다.
	 * 
	 * @return 트랜스포머 유형 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 트랜스포머 유형 이름을 설정합니다.
	 * 
	 * @param name
	 *            트랜스포머 유형 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 트랜스포머 유형의 UI 표시 이름을 반환합니다.
	 * 
	 * @return UI 표시 이름
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * 트랜스포머 유형의 UI 표시 이름을 설정합니다.
	 * 
	 * @param displayName
	 *            UI 표시 이름
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 트랜스포머 유형에 대한 UI 표시 설명을 반환합니다.
	 * 
	 * @return UI 표시 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 트랜스포머 유형에 대한 UI 표시 설명을 설정합니다.
	 * 
	 * @param description
	 *            UI 표시 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 트랜스포머를 생성하는데 필요한 설정 명세 목록을 반환합니다.
	 * 
	 * @return 트랜스포머 설정 명세 목록
	 */
	public List<ConfigSpec> getConfigSpecs() {
		return configSpecs;
	}

	/**
	 * 트랜스포머를 생성하는데 필요한 설정 명세 목록을 설정합니다.
	 * 
	 * @param configSpecs
	 *            트랜스포머 설정 명세 목록
	 */
	public void setConfigSpecs(List<ConfigSpec> configSpecs) {
		this.configSpecs = configSpecs;
	}

	@Override
	public String toString() {
		return "name=" + name + ", description=" + description + ", config specs=" + configSpecs;
	}
}
