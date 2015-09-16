/*
 * Copyright 2015 Eediom Inc.
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
import java.util.List;

public class StorageEngineConfig {
	private String type;
	private String basePath;
	private List<TableConfig> configs = new ArrayList<TableConfig>();

	public StorageEngineConfig() {
	}

	public StorageEngineConfig(String type) {
		this.type = type;
	}

	public StorageEngineConfig(String type, String basePath) {
		this.type = type;
		this.basePath = basePath;
	}

	public StorageEngineConfig clone() {
		StorageEngineConfig c = new StorageEngineConfig();
		c.setType(type);
		c.setBasePath(basePath);
		c.setConfigs(clone(configs));
		return c;
	}

	private List<TableConfig> clone(List<TableConfig> l) {
		List<TableConfig> cloned = new ArrayList<TableConfig>();
		for (TableConfig config : l)
			cloned.add(config.clone());
		return cloned;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public List<TableConfig> getConfigs() {
		return configs;
	}

	public TableConfig getConfig(String key) {
		for (TableConfig c : configs)
			if (c.getKey().equals(key))
				return c;
		return null;
	}

	public void setConfigs(List<TableConfig> configs) {
		this.configs = configs;
	}

	@Override
	public String toString() {
		String bp = "";
		if (basePath != null)
			bp = ", base_path=" + basePath;

		String c = "";
		if (!configs.isEmpty())
			c = ", configs=" + configs;

		return "type=" + type + c + bp;
	}
}
