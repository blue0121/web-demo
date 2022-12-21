package io.jutil.web.common.spring.fastjson;

import io.jutil.web.common.spring.BaseControllerTest;
import io.jutil.web.common.spring.controller.TestRequest;
import io.jutil.web.common.spring.controller.TestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
public class FastJsonAutoConfigurationTest extends BaseControllerTest {

	@BeforeEach
	public void beforeEach() {
		this.init();
	}
	@Test
	public void testString() {
		var url = this.buildUrl("/test/blue");
		var response = restTemplate.getForEntity(url, String.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("Hello, blue", response.getBody());
	}

	@Test
	public void testJson() {
		var url = this.buildUrl("/test");
		var request = new TestRequest("blue");
		var response = restTemplate.postForEntity(url, request, TestResponse.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("blue", response.getBody().getName());
		Assertions.assertEquals("Hello, blue", response.getBody().getMessage());
	}

}
