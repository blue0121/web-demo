package io.jutil.web.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest {

	@LocalServerPort
	protected int port;

	@Autowired
	protected RestTemplate restTemplate;

	protected void init() {

	}

	protected String buildUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

}
