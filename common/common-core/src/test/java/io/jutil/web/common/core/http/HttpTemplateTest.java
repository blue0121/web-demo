package io.jutil.web.common.core.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2022-12-21
 */
public class HttpTemplateTest extends BaseHttpTest {

    @BeforeEach
    public void beforeEach() {
        this.init();
    }

    @Test
    public void testGetString() {
        var url = "/test/blue";
        var response = httpTemplate.getSync(url);
        Assertions.assertEquals(200, response.getCode());
        Assertions.assertEquals("Hello, blue", response.getBody());
    }
}
