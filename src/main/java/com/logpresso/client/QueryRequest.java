package com.logpresso.client;

import java.util.Map;

public class QueryRequest {
	private String queryString;
	private StreamingResultSet rs;
	private Map<String, Object> queryContext;
	private boolean useSummary;

	public QueryRequest(String queryString) {
		this.queryString = queryString;
	}

	public String getQueryString() {
		return queryString;
	}

	public StreamingResultSet getStreamingResultSet() {
		return rs;
	}

	public void setStreamingResultSet(StreamingResultSet rs) {
		this.rs = rs;
	}

	public Map<String, Object> getQueryContext() {
		return queryContext;
	}

	public void setQueryContext(Map<String, Object> queryContext) {
		this.queryContext = queryContext;
	}

	public boolean isUseSummary() {
		return useSummary;
	}

	public void setUseSummary(boolean useSummary) {
		this.useSummary = useSummary;
	}
}
