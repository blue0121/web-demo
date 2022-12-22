package io.jutil.web.common.spring.config;

import blue.base.core.http.HttpClient;
import io.jutil.web.common.core.http.HttpTemplate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-20
 */
public class HttpClientFactoryBean implements FactoryBean<HttpTemplate>, InitializingBean {
	private String id;
	private String baseUrl;
	private int timeout;
	private String username;
	private String password;
	private String proxy;
	private Map<String, String> defaultHeaders;
	private Executor executor;

	private HttpTemplate httpTemplate;

	public HttpClientFactoryBean() {
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
		this.httpTemplate = HttpClient.builder()
				.setId(id)
				.setBaseUrl(baseUrl)
				.setTimeout(timeout)
				.setUsername(username)
				.setPassword(password)
				.setProxy(proxy)
				.setDefaultHeaders(defaultHeaders)
				.setExecutor(executor)
				.build();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public void setDefaultHeaders(Map<String, String> defaultHeaders) {
		this.defaultHeaders = defaultHeaders;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
}
