package io.jutil.web.common.springmvc.config;

import io.jutil.web.common.core.util.JsonUtil;
import io.jutil.web.common.springmvc.BaseTest;
import io.jutil.web.common.springmvc.controller.ErrorCode;
import io.jutil.web.common.springmvc.controller.TestRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-01-02-21:26
 */
public class ErrorCodeTest extends BaseTest {

	@BeforeEach
	public void beforeEach() {
		this.init();
	}

	@Test
	public void testJson() {
		var url = this.buildUrl("/test");
		var request = new TestRequest("");
		var response = httpTemplate.postSync(url, JsonUtil.output(request));
		Assertions.assertEquals(400, response.getStatusCode());

		var body = response.convertTo(ErrorCode.class);
		Assertions.assertEquals(400000, body.getCode());
		Assertions.assertEquals("无效参数: 名称不能为空", body.getMessage());
	}
}
