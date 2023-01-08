package io.jutil.web.internal.common.core.codec;

import io.jutil.web.common.core.codec.Decoder;
import io.jutil.web.common.core.util.AssertUtil;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-01-06
 */
public class ByteArrayDecoder implements Decoder {

	private int index = 0;
	private int size = 0;
	private int capacity = 0;
	private byte[] buffer;

	public ByteArrayDecoder(byte[] buf) {
		this.addBuffer(buf);
	}

	public void addBuffer(byte[] buf) {
		AssertUtil.notEmpty(buf, "Buffer");
		int oldSize = this.size;
		this.size += buf.length;
		int times = (this.size + CodecConstant.INC_CAP - 1) / CodecConstant.INC_CAP;
		int cap = times << CodecConstant.INC_LEFT_SHIFT;
		if (this.capacity == cap) {
			System.arraycopy(buf, 0, this.buffer, oldSize, buf.length);
			return;
		}
		this.capacity = cap;
		byte[] newBuf = new byte[capacity];
		if (this.buffer == null) {
			System.arraycopy(buf, CodecConstant.ZERO_IDX, newBuf, CodecConstant.ZERO_IDX, buf.length);
		} else {
			System.arraycopy(buffer, CodecConstant.ZERO_IDX, newBuf, CodecConstant.ZERO_IDX, oldSize);
			System.arraycopy(buf, CodecConstant.ZERO_IDX, newBuf, oldSize, buf.length);
		}
		this.buffer = newBuf;
	}

	@Override
	public void resetReadIndex() {
		this.resetReadIndex(CodecConstant.ZERO_IDX);
	}

	@Override
	public void resetReadIndex(int offset) {
		if (offset == CodecConstant.ZERO_IDX) {
			this.index = CodecConstant.ZERO_IDX;
			return;
		}

		if (offset < CodecConstant.ZERO_IDX || offset >= size) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + size);
		}
		this.index = offset;
	}

	@Override
	public void skipReadIndex(int offset) {
		if (index + offset < CodecConstant.ZERO_IDX || index + offset >= size) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + (size - index));
		}
		this.index += offset;
	}

	private void checkIndex(int offset) {
		if (index + offset > size) {
			throw new IndexOutOfBoundsException("缓冲区溢出");
		}
	}

	@Override
	public byte readByte() {
		this.checkIndex(CodecConstant.BYTE_LEN);
		return buffer[index++];
	}

	@Override
	public int readBytes(byte[] buf) {
		AssertUtil.notEmpty(buf, "Buf");
		int read = Math.min(buf.length, size - index);
		if (read > CodecConstant.ZERO_IDX) {
			System.arraycopy(buffer, index, buf, CodecConstant.ZERO_IDX, read);
			this.index += read;
		}
		return read;
	}

	@Override
	public short readShort() {
		this.checkIndex(CodecConstant.SHORT_LEN);
		return (short) (((0xff & buffer[index++]) << 8) | (0xff & buffer[index++]));
	}

	@Override
	public int readInt() {
		this.checkIndex(CodecConstant.INT_LEN);
		return ((0xff & buffer[index++]) << 24)
				| ((0xff & buffer[index++]) << 16)
				| ((0xff & buffer[index++]) << 8)
				| (0xff & buffer[index++]);
	}

	@Override
	public long readLong() {
		this.checkIndex(CodecConstant.LONG_LEN);
		return ((0xffL & buffer[index++]) << 56)
				| ((0xffL & buffer[index++]) << 48)
				| ((0xffL & buffer[index++]) << 40)
				| ((0xffL & buffer[index++]) << 32)
				| ((0xffL & buffer[index++]) << 24)
				| ((0xffL & buffer[index++]) << 16)
				| ((0xffL & buffer[index++]) << 8)
				| (0xffL & buffer[index++]);
	}

	@Override
	public float readFloat() {
		return Float.intBitsToFloat(this.readInt());
	}

	@Override
	public double readDouble() {
		return Double.longBitsToDouble(this.readLong());
	}

	@Override
	public boolean readBoolean() {
		byte val = this.readByte();
		return !(val == (byte)0);
	}

	@Override
	public char readChar() {
		this.checkIndex(CodecConstant.SHORT_LEN);
		return (char) (((0xff & buffer[index++]) << 8) | (0xff & buffer[index++]));
	}

	@Override
	public String readString() {
		var exists = this.readByte();
		if (exists == CodecConstant.BYTE_NULL) {
			return null;
		}
		if (exists == CodecConstant.BYTE_EMPTY) {
			return "";
		}
		this.skipReadIndex(CodecConstant.BYTE_SKIP_OFFSET);
		var len = this.readInt();
		this.checkIndex(len);
		String str = new String(buffer, index, len, StandardCharsets.UTF_8);
		this.index += len;
		return str;
	}

	private boolean readIsNull() {
		var exist = this.readByte();
		this.skipReadIndex(CodecConstant.BYTE_SKIP_OFFSET);
		return exist == CodecConstant.BYTE_NULL;
	}

	@Override
	public Date readDate() {
		if (this.readIsNull()) {
			return null;
		}
		var val = this.readLong();
		return new Date(val);
	}

	@Override
	public LocalDateTime readLocalDateTime() {
		if (this.readIsNull()) {
			return null;
		}
		var instant = this.readInstantInternal();
		return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private Instant readInstantInternal() {
		var second = this.readLong();
		var nano = this.readInt();
		return Instant.ofEpochSecond(second, nano);
	}

	@Override
	public Instant readInstant() {
		if (this.readIsNull()) {
			return null;
		}
		return this.readInstantInternal();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Number> T readNumber(Class<T> clazz) {
		if (this.readIsNull()) {
			return null;
		}
		Number val = null;
		if (clazz == Byte.class) {
			val = this.readByte();
		} else if (clazz == Short.class) {
			val = this.readShort();
		} else if (clazz == Integer.class) {
			val = this.readInt();
		} else if (clazz == Long.class) {
			val = this.readLong();
		} else if (clazz == Float.class) {
			val = this.readFloat();
		} else if (clazz == Double.class) {
			val = this.readDouble();
		}

		return (T) val;
	}

	@Override
	public int getReadIndex() {
		return index;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
