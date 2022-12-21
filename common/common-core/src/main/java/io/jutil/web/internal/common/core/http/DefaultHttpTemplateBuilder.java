package io.jutil.web.internal.common.core.http;

import io.jutil.web.common.core.http.HttpTemplate;
import io.jutil.web.common.core.http.HttpTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;
import java.util.Base64;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2021-07-02
 */
public class DefaultHttpTemplateBuilder implements HttpTemplateBuilder {
    private static Logger logger = LoggerFactory.getLogger(DefaultHttpTemplateBuilder.class);

    private String id;
    private String baseUrl;
    private String username;
    private String password;
    private Map<String, String> defaultHeaders;

    private HttpClient httpClient;

	public DefaultHttpTemplateBuilder() {
	}

    @Override
    public HttpTemplate build() {
        DefaultHttpTemplate client = new DefaultHttpTemplate(this);
        return client;
    }

    /*private void init() {
        HttpClient.Builder builder = HttpClient.newBuilder();
        if (timeout > 0) {
            builder.connectTimeout(Duration.ofMillis(timeout));
        }
        if (proxy != null && !proxy.isEmpty()) {
            String[] proxys = proxy.split(":");
            if (proxys.length != 2) {
                throw new IllegalArgumentException("Invalid proxy: " + proxy);
            }

            builder.proxy(ProxySelector.of(new InetSocketAddress(proxys[0], Integer.parseInt(proxys[1]))));
        }
        if (defaultHeaders == null) {
            defaultHeaders = new HashMap<>();
        }
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            defaultHeaders.put("Authorization", this.authorization());
        }
        this.httpClient = builder.build();
        logger.info("Initialize HttpClient, id: {}, baseUrl: {}, timeout: {}ms, proxy: {}, username: {}, password: {}," +
                " defaultHeaders: {}", id, baseUrl, timeout, proxy, username, password, defaultHeaders);
    }*/

    private String authorization() {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Override
    public HttpTemplateBuilder setId(String id) {
	    this.id = id;
        return this;
    }

    @Override
    public HttpTemplateBuilder setBaseUrl(String baseUrl) {
	    this.baseUrl = baseUrl;
        return this;
    }

    @Override
    public HttpTemplateBuilder setUsername(String username) {
	    this.username = username;
        return this;
    }

    @Override
    public HttpTemplateBuilder setPassword(String password) {
	    this.password = password;
        return this;
    }

    @Override
    public HttpTemplateBuilder setDefaultHeaders(Map<String, String> headers) {
	    this.defaultHeaders = headers;
        return this;
    }

    @Override
    public HttpTemplateBuilder setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}