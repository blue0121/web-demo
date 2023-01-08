package io.jutil.web.common.core.util;

import io.jutil.web.common.core.codec.ExternalSerializable;
import io.jutil.web.internal.common.core.codec.ByteArrayDecoder;
import io.jutil.web.internal.common.core.codec.ByteArrayEncoder;

import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
public class SerializableUtil {
	private SerializableUtil() {
	}

	public static byte[] encode(ExternalSerializable target) {
		AssertUtil.notNull(target, "Serializable");

		var encoder = new ByteArrayEncoder();
		target.encode(encoder);
		return encoder.getByteArray();
	}

	public static String encodeBase64(ExternalSerializable target) {
		var data = encode(target);
		if (data == null || data.length == 0) {
			return null;
		}
		return Base64.getEncoder().encodeToString(data);
	}

	public static void decode(ExternalSerializable target, byte[] data) {
		AssertUtil.notNull(target, "MessageSerializable");

		var decoder = new ByteArrayDecoder(data);
		target.decode(decoder);
	}

	public static void decode(ExternalSerializable target, String base64) {
		AssertUtil.notEmpty(base64, "Serializable data");
		var data = Base64.getDecoder().decode(base64);
		decode(target, data);
	}
}
