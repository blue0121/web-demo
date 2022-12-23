package io.jutil.web.common.spring.config;

import io.jutil.web.common.core.http.HttpTemplate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.http.HttpClient;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-20
 */
public class HttpTemplateFactoryBean implements FactoryBean<HttpTemplate>, InitializingBean {
	private String id;
	private String baseUrl;
	private String username;
	private String password;
	private Map<String, String> defaultHeaders;
	private HttpClient httpClient;

	private HttpTemplate httpTemplate;

	public HttpTemplateFactoryBean() {
	}

	@Override
	public HttpTemplate getObject() throws Exception {
		return httpTemplate;
	}

	@Override
	public Class<?> getObjectType() {
		return HttpTemplate.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.httpTemplate = HttpTemplate.builder()
				.setId(id)
				.setBaseUrl(baseUrl)
				.setUsername(username)
				.setPassword(password)
				.putDefaultHeaders(defaultHeaders)
				.setHttpClient(httpClient)
				.build();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDefaultHeaders(Map<String, String> defaultHeaders) {
		this.defaultHeaders = defaultHeaders;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
