package io.jutil.web.common.redis.codec;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.jutil.web.common.core.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.handler.State;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

/**
 * @author Jin Zheng
 * @since 2022-12-26
 */
public class FastJson2Codec extends BaseCodec {
	private static final FastJson2Encoder ENCODER = new FastJson2Encoder();
	private static final FastJson2Decoder DECODER = new FastJson2Decoder();

	public FastJson2Codec() {
	}

    @Override
    public Decoder<Object> getValueDecoder() {
        return DECODER;
    }

    @Override
    public Encoder getValueEncoder() {
        return ENCODER;
    }

	public static class FastJson2Decoder implements Decoder<Object> {

		@Override
		public Object decode(ByteBuf buf, State state) {
			var size = buf.readableBytes();
			var bytes = new byte[size];
			buf.readBytes(bytes);
			return JSON.parseObject(bytes, Object.class, JsonUtil.AUTO_TYPE_FILTER);
		}
	}

	public static class FastJson2Encoder implements Encoder {

		@Override
		public ByteBuf encode(Object in) {
			var out = ByteBufAllocator.DEFAULT.buffer();
			var bytes = JSON.toJSONBytes(in, JSONWriter.Feature.WriteClassName);
			out.writeBytes(bytes);
			return out;
		}
	}
}
