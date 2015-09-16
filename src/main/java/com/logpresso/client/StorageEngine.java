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

import java.util.List;

public class StorageEngine {
	private String name;
	private List<StorageEngineConfigSpec> primaryConfigSpecs;
	private List<StorageEngineConfigSpec> replicaConfigSpecs;

	public StorageEngine(String name, List<StorageEngineConfigSpec> primaryConfigSpecs,
			List<StorageEngineConfigSpec> replicaConfigSpecs) {
		this.name = name;
		this.primaryConfigSpecs = primaryConfigSpecs;
		this.replicaConfigSpecs = replicaConfigSpecs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StorageEngineConfigSpec> getPrimaryConfigSpecs() {
		return primaryConfigSpecs;
	}

	public void setPrimaryConfigSpecs(List<StorageEngineConfigSpec> primaryConfigSpecs) {
		this.primaryConfigSpecs = primaryConfigSpecs;
	}

	public List<StorageEngineConfigSpec> getReplicaConfigSpecs() {
		return replicaConfigSpecs;
	}

	public void setReplicaConfigSpecs(List<StorageEngineConfigSpec> replicaConfigSpecs) {
		this.replicaConfigSpecs = replicaConfigSpecs;
	}

	@Override
	public String toString() {
		String s = "name=" + name;
		s += ", primary options={";
		int i = 0;
		for (StorageEngineConfigSpec spec : primaryConfigSpecs) {
			if (i++ != 0)
				s += ", ";
			s += spec.getDisplayName();
			if (spec.getDescription() != null)
				s += ": " + spec.getDescription();
		}
		s += "}";

		i = 0;
		if (replicaConfigSpecs != null && !replicaConfigSpecs.isEmpty()) {
			s += ", replica options={";
			for (StorageEngineConfigSpec spec : replicaConfigSpecs) {
				if (i++ != 0)
					s += ", ";
				s += spec.getDisplayName();
				if (spec.getDescription() != null)
					s += ": " + spec.getDescription();
			}
			s += "}";
		}

		return s;
	}
}
