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

/**
 * 커서는 쿼리 결과를 순회하는 인터페이스를 제공합니다.
 * 
 * @author xeraph@eediom.com
 * 
 */
public interface Cursor extends Iterator<Tuple>, Closeable {

}
