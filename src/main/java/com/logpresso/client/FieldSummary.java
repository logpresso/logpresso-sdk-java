package com.logpresso.client;

import java.util.Map;

public class FieldSummary {
	private String name;
	private String type;
	private long count;
	private Object min;
	private Object max;
	private Double avg;

	public static FieldSummary parse(Map<String, Object> m) {
		FieldSummary f = new FieldSummary();
		f.setName((String) m.get("name"));
		f.setType((String) m.get("type"));
		f.setCount(((Number) m.get("count")).longValue());
		f.setMin(m.get("min"));
		f.setMax(m.get("max"));
		if (m.get("avg") != null)
			f.setAvg(((Number) m.get("avg")).doubleValue());
		return f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Object getMin() {
		return min;
	}

	public void setMin(Object min) {
		this.min = min;
	}

	public Object getMax() {
		return max;
	}

	public void setMax(Object max) {
		this.max = max;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	@Override
	public String toString() {
		return String.format("field %s (type=%s, count=%d, min=%s, max=%s, avg=%f)", name, type, count,
				min != null ? min.toString() : null, max != null ? max.toString() : null, avg);
	}

}
