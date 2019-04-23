package com.logpresso.client;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Workflow {
	private String guid = UUID.randomUUID().toString();
	private String name;
	private String description;
	private String owner;
	private Date created = new Date();
	private Date updated = new Date();
	private String triggerType;
	private Map<String, String> triggerConfigs;
	private boolean enabled = true;
	private List<Task> tasks = new ArrayList<Task>();

	@SuppressWarnings("unchecked")
	public static Workflow parse(Map<String, Object> m) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
		Workflow w = new Workflow();
		w.setGuid((String) m.get("guid"));
		w.setName((String) m.get("name"));
		w.setDescription((String) m.get("description"));
		w.setOwner((String) m.get("owner"));
		w.setCreated(df.parse((String) m.get("created"), new ParsePosition(0)));
		w.setUpdated(df.parse((String) m.get("updated"), new ParsePosition(0)));
		w.setTriggerType((String) m.get("trigger_type"));
		w.setTriggerConfigs((Map<String, String>) m.get("trigger_configs"));
		w.setEnabled((Boolean) m.get("enabled"));

		List<Object> l = (List<Object>) m.get("tasks");
		if (l != null) {
			for (Object o : l) {
				w.tasks.add(Task.parse((Map<String, Object>) o));
			}
		}
		return w;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public Map<String, String> getTriggerConfigs() {
		return triggerConfigs;
	}

	public void setTriggerConfigs(Map<String, String> triggerConfigs) {
		this.triggerConfigs = triggerConfigs;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "guid=" + guid + ", name=" + name + ", trigger_type=" + triggerType + ", trigger_configs=" + triggerConfigs
		        + ", description=" + description + ", enabled=" + enabled + ", owner=" + owner + ", created=" + df.format(created)
		        + ", updated=" + df.format(updated);
	}

}
