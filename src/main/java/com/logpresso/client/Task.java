package com.logpresso.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Task {
	private String guid = UUID.randomUUID().toString();
	private String name;
	private String description;
	private String workflowGuid;
	private String type;
	private Map<String, String> configs;
	private List<Parameter> inputParams = new ArrayList<Parameter>();
	private List<Parameter> outputParams = new ArrayList<Parameter>();

	@SuppressWarnings("unchecked")
	public static Task parse(Map<String, Object> m) {
		Task t = new Task();
		t.guid = (String) m.get("guid");
		t.name = (String) m.get("name");
		t.description = (String) m.get("description");
		t.workflowGuid = (String) m.get("workflow_guid");
		t.type = (String) m.get("type");
		t.configs = (Map<String, String>) m.get("configs");

		List<Object> inputObjects = (List<Object>) m.get("input_params");
		List<Object> outputObjects = (List<Object>) m.get("output_params");
		if (inputObjects != null) {
			for (Object o : inputObjects) {
				t.inputParams.add(Parameter.parse((Map<String, Object>) o));
			}
		}

		if (outputObjects != null) {
			for (Object o : outputObjects) {
				t.outputParams.add(Parameter.parse((Map<String, Object>) o));
			}
		}

		return t;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

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

	public String getWorkflowGuid() {
		return workflowGuid;
	}

	public void setWorkflowGuid(String workflowGuid) {
		this.workflowGuid = workflowGuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}

	public List<Parameter> getInputParams() {
		return inputParams;
	}

	public void setInputParams(List<Parameter> inputParams) {
		this.inputParams = inputParams;
	}

	public List<Parameter> getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(List<Parameter> outputParams) {
		this.outputParams = outputParams;
	}

	@Override
	public String toString() {
		return "guid=" + guid + ", name=" + name + ", description=" + description + ", type=" + type + ", configs=" + configs
		        + ", input_params=" + inputParams + ", output_params=" + outputParams;
	}

}
