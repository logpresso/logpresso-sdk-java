package com.logpresso.client;

import java.util.List;

/**
 * @author kyun
 */
public interface FailureListener {
	void onInsertFailure(String tableName, List<Tuple> tuples, Throwable t);
}
