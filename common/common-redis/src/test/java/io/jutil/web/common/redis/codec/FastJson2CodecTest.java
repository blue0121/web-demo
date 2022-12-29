package io.jutil.web.common.redis.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2022-12-29
 */
public class FastJson2CodecTest {
	public FastJson2CodecTest() {
	}

    @Test
    public void test() throws Exception {
        var codec = new FastJson2Codec();
        var u1 = new User(1, "blue");

        var byteBuf = codec.getValueEncoder().encode(u1);
        User u2 = (User) codec.getValueDecoder().decode(byteBuf, null);

		byteBuf.resetReaderIndex();
		var bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		var json = new String(bytes);
	    System.out.println(json);

        Assertions.assertEquals(u1, u2);
    }

}
