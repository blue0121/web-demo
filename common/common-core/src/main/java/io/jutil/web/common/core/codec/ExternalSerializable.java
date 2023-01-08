package io.jutil.web.common.core.codec;

/**
 * 外部(自定义)序列化
 *
 * @author Jin Zheng
 * @since 2023-01-06
 */
public interface ExternalSerializable {

	/**
	 * 编码
	 *
	 * @param encoder
	 */
	void encode(Encoder encoder);

	/**
	 * 解码
	 *
	 * @param decoder
	 */
	void decode(Decoder decoder);

}
