/*
 * Copyright 2013 Eediom Inc.
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

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

/**
 * 커서는 쿼리 결과를 순회하는 인터페이스를 제공합니다.
 */
public interface Cursor extends Iterator<Tuple>, Closeable {
	int getQueryId();

	/**
	 * 필드 정렬 순서를 반환합니다. 필드 정렬 순서에 표시된 필드가 쿼리 결과에 존재하지 않을 수 있으며, 쿼리 결과의 모든 필드를 나열하지
	 * 않습니다. 필드 정렬 순서는 실제 출력 필드에서 고려되어야 할 순서를 의미하므로, 필드 정렬 순서에 나타나지 않은 결과 필드는 사전순으로
	 * 정렬해야 합니다.
	 * 
	 * @return 필드 정렬 순서 목록. 필드 정렬 순서가 정의되지 않은 경우 null을 반환합니다.
	 */
	List<String> getFieldOrder();

	/**
	 * queryWithSummary()를 사용하거나, 요약 정보를 생성하도록 설정한 쿼리의 경우 필드 요약 정보를 반환합니다.
	 * 
	 * @return 필드 요약 정보. 요약 생성 옵션이 지정되지 않은 쿼리는 null을 반환합니다.
	 */
	List<FieldSummary> getSummary();
}
