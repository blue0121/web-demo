package io.jutil.web.common.springmvc.config;

import io.jutil.web.common.core.util.JsonUtil;
import io.jutil.web.common.springmvc.BaseTest;
import io.jutil.web.common.springmvc.controller.TestRequest;
import io.jutil.web.common.springmvc.controller.TestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-01-02-19:42
 */
public class FastJsonTest extends BaseTest {

	@BeforeEach
	public void beforeEach() {
		this.init();
	}

	@Test
	public void testString() {
		var url = this.buildUrl("/test/blue");
		var response = httpTemplate.getSync(url, Map.of("Accept", "text/plain"));
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertEquals("Hello, blue", response.getBody());
	}

	@Test
	public void testJson() {
		var url = this.buildUrl("/test");
		var request = new TestRequest("blue");
		var response = httpTemplate.postSync(url, JsonUtil.output(request));
		Assertions.assertEquals(200, response.getStatusCode());

		var body = response.convertTo(TestResponse.class);
		Assertions.assertEquals("blue", body.getName());
		Assertions.assertEquals("Hello, blue", body.getMessage());
	}
}
