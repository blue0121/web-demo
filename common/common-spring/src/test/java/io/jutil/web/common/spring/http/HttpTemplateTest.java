package io.jutil.web.common.spring.http;

import io.jutil.web.common.core.http.HttpTemplate;
import io.jutil.web.common.core.util.JsonUtil;
import io.jutil.web.common.spring.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2022-12-23
 */
@ActiveProfiles("http")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpTemplateTest {
    @LocalServerPort
    protected int port;

	@Autowired
    @Qualifier("test1")
    private HttpTemplate httpTemplate;

    @Test
    public void testPost() {
        var url = "http://localhost:" + port + "/test";
        var request = new TestRequest("blue");
        var response = httpTemplate.postSync(url, JsonUtil.output(request));
        Assertions.assertEquals(200, response.getStatusCode());
        var entity = response.convertTo(TestResponse.class);
        Assertions.assertEquals("blue", entity.getName());
        Assertions.assertEquals("Hello, blue", entity.getMessage());
    }
}
