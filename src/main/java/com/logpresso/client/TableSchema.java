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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 테이블 설정을 표현합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public class TableSchema {
	private String name;

	private int id;

	private StorageEngineConfig primaryStorage;

	private StorageEngineConfig replicaStorage;

	private List<StorageEngineConfig> secondaryStorages = new ArrayList<StorageEngineConfig>();

	private List<TableField> fieldDefinitions;

	private Map<String, String> metadata = new HashMap<String, String>();
	
	public TableSchema() {
	}

	/**
	 * @param name
	 *            테이블 이름
	 * @param type
	 *            주 스토리지 엔진 타입
	 */
	public TableSchema(String name, String type) {
		this.name = name;
		this.primaryStorage = new StorageEngineConfig(type);
	}

	@Deprecated
	public TableSchema(String name, Map<String, String> metadata) {
		this.name = name;
		this.metadata = metadata;
	}

	public TableSchema clone() {
		TableSchema c = new TableSchema();
		c.setName(name);
		c.setId(id);
		c.setPrimaryStorage(primaryStorage.clone());

		if (replicaStorage != null)
			c.setReplicaStorage(replicaStorage.clone());

		List<StorageEngineConfig> l = new ArrayList<StorageEngineConfig>();
		for (StorageEngineConfig s : secondaryStorages)
			l.add(s.clone());

		c.setSecondaryStorages(l);

		c.setFieldDefinitions(cloneFieldDefinitions(fieldDefinitions));
		if (metadata != null)
			c.setMetadata(new HashMap<String, String>(metadata));

		return c;
	}

	private List<TableField> cloneFieldDefinitions(List<TableField> l) {
		if (l == null)
			return null;

		List<TableField> cloned = new ArrayList<TableField>();
		for (TableField d : l)
			cloned.add(TableField.parse(d.toString()));
		return cloned;
	}

	/**
	 * 테이블 이름을 반환합니다.
	 * 
	 * @return 테이블 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 테이블 이름을 설정합니다.
	 * 
	 * @param name
	 *            테이블 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StorageEngineConfig getPrimaryStorage() {
		return primaryStorage;
	}

	public void setPrimaryStorage(StorageEngineConfig primaryStorage) {
		this.primaryStorage = primaryStorage;
	}

	public StorageEngineConfig getReplicaStorage() {
		return replicaStorage;
	}

	public void setReplicaStorage(StorageEngineConfig replicaStorage) {
		this.replicaStorage = replicaStorage;
	}

	public List<StorageEngineConfig> getSecondaryStorages() {
		return secondaryStorages;
	}

	public void setSecondaryStorages(List<StorageEngineConfig> secondaryStorages) {
		this.secondaryStorages = secondaryStorages;
	}

	/**
	 * 테이블 메타데이터를 반환합니다.
	 * 
	 * @return 테이블 메타데이터
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * 테이블 메타데이터를 설정합니다.
	 * 
	 * @param metadata
	 *            테이블 메타데이터
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/**
	 * 스키마를 구성하는 필드 정의 목록을 반환합니다.
	 * 
	 * @return 필드 정의 목록
	 */
	public List<TableField> getFieldDefinitions() {
		return fieldDefinitions;
	}

	/**
	 * 스키마를 구성하는 필드 정의 목록을 설정합니다.
	 * 
	 * @param fieldDefinitions
	 *            필드 정의 목록
	 */
	public void setFieldDefinitions(List<TableField> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("table", name);
		m.put("id", id);
		m.put("type", primaryStorage.getType());
		m.put("base_path", primaryStorage.getBasePath());
		m.put("primary_configs", serialize(primaryStorage));
		m.put("replica_configs", serialize(replicaStorage));
		m.put("metadata", metadata);

		return m;
	}

	private Map<String, Object> serialize(StorageEngineConfig c) {
		if (c == null)
			return null;
		
		Map<String, Object> m = new HashMap<String, Object>();
		for (TableConfig t : c.getConfigs()) {
			if (t.getValues().size() <= 1)
				m.put(t.getKey(), t.getValue());
			else
				m.put(t.getKey(), t.getValues());
		}

		return m;
	}

	@Override
	public String toString() {
		String replica = "";
		if (replicaStorage != null)
			replica = ", replica={" + replicaStorage + "}";

		String m = "";
		if (metadata != null && !metadata.isEmpty())
			m = ", metadata=" + metadata;

		return "name=" + name + ", primary={" + primaryStorage + "}" + replica + m;
	}
}
