/*
 * Copyright 2014 Eediom Inc.
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

/**
 * 이 인터페이스는 쿼리 결과를 실시간 스트리밍하는데 필요한 명세를 제공합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public interface StreamingResultSet {
	/**
	 * 
	 * @param query
	 *            연관된 쿼리 개체
	 * @param rows
	 *            부분적인 쿼리 결과 행 목록
	 * @param last
	 *            마지막 콜백 호출 여부
	 */
	void onRows(Query query, List<Tuple> rows, boolean last);
}
