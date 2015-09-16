/**
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @since 1.1.3
 * @author xeraph
 * 
 */
public class Procedure {
	private String name;
	private String description;
	private String queryString;
	private List<ProcedureParameter> parameters = new ArrayList<ProcedureParameter>();
	private String owner;
	private Set<String> grantLogins = new HashSet<String>();
	private Set<String> grantGroups = new HashSet<String>();
	private Date created = new Date();
	private Date modified = new Date();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public List<ProcedureParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ProcedureParameter> parameters) {
		this.parameters = parameters;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Set<String> getGrantLogins() {
		return grantLogins;
	}

	public void setGrantLogins(Set<String> grantLogins) {
		this.grantLogins = grantLogins;
	}

	public Set<String> getGrantGroups() {
		return grantGroups;
	}

	public void setGrantGroups(Set<String> grantGroups) {
		this.grantGroups = grantGroups;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "name=" + name + ", parameters=" + parameters + ", query=" + queryString + ", created=" + df.format(created)
				+ ", modified=" + df.format(modified) + ", owner=" + owner + ", grant_logins=" + grantLogins + ", grant_groups="
				+ grantGroups;
	}

}
