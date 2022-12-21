package io.jutil.web.common.core.dict;

import com.alibaba.fastjson2.JSON;
import io.jutil.web.common.core.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
public class StatusTest {
	public StatusTest() {
	}

    @Test
    public void testToString() {
        var status = Status.ACTIVE;
        var json = JsonUtil.output(status);
        System.out.println(json);
        var obj = JSON.parseObject(json);
        Assertions.assertEquals(0, obj.getIntValue("value"));
        Assertions.assertEquals("正常", obj.getString("name"));
        Assertions.assertEquals("primary", obj.getString("color"));
    }

}
