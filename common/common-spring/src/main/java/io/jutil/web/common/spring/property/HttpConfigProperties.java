package io.jutil.web.common.spring.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 1.0 2020-07-15
 */
@Getter
@Setter
@NoArgsConstructor
public class HttpConfigProperties {
	private String id;
	private String baseUrl;
	private String username;
	private String password;
	private int timeout;
	private List<HttpHeaderProperties> headers;
	private String proxy;

}
