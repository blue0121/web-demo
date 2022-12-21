package io.jutil.web.common.core.http;

import java.net.http.HttpClient;

/**
 * @author Jin Zheng
 * @since 2021-07-02
 */
public interface HttpTemplateBuilder {

	HttpTemplate build();

	HttpTemplateBuilder setId(String id);

	HttpTemplateBuilder setBaseUrl(String baseUrl);

	HttpTemplateBuilder setUsername(String username);

	HttpTemplateBuilder setPassword(String password);

	HttpTemplateBuilder putHeader(String name, String value);

	HttpTemplateBuilder setHttpClient(HttpClient httpClient);

}
