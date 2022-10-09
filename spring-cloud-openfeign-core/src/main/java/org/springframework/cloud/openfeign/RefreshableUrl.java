/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.openfeign;

/**
 * This model will just wrap url inside an object so that relevant proxy instance can be
 * created using {@link RefreshableUrlFactoryBean}.
 *
 * @author Jasbir Singh
 */
public class RefreshableUrl {

	private String url;

	public RefreshableUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
