package com.logpresso.client;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private String key;
	private String type;
	private String subtype;
	private boolean isRequired;

	private Map<String, String> displayNames = new HashMap<String, String>();
	private Map<String, String> descriptions = new HashMap<String, String>();
	private Map<String, String> defaultValues = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	public static Parameter parse(Map<String, Object> o) {
		Parameter p = new Parameter();
		p.key = (String) o.get("key");
		p.type = (String) o.get("type");
		p.subtype = (String) o.get("subtype");
		p.isRequired = (Boolean) o.get("required");
		p.displayNames = (Map<String, String>) o.get("display_names");
		p.descriptions = (Map<String, String>) o.get("descriptions");
		p.defaultValues = (Map<String, String>) o.get("default_values");
		return p;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public Map<String, String> getDisplayNames() {
		return displayNames;
	}

	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}

	public Map<String, String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}

	public Map<String, String> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(Map<String, String> defaultValues) {
		this.defaultValues = defaultValues;
	}

	@Override
	public String toString() {
		return type + " " + key;
	}

}
