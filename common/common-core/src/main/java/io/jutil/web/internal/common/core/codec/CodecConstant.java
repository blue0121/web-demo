package io.jutil.web.internal.common.core.codec;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
class CodecConstant {
	static final int INC_LEFT_SHIFT = 7;
	static final int INC_CAP = 1 << INC_LEFT_SHIFT;
	static final int BYTE_LEN = 1;
	static final int SHORT_LEN = 2;
	static final int INT_LEN = 4;
	static final int LONG_LEN = 8;
	static final int ZERO_IDX = 0;
	static final byte BYTE_NULL = -1;
	static final byte BYTE_EMPTY = 0;
	static final int BYTE_SKIP_OFFSET = -1;
}
