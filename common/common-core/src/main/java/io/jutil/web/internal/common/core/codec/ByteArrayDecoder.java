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
		var len = this.readInt();
		if (len == 0) {
			return "";
		}
		this.checkIndex(len);
		String str = new String(buffer, index, len, StandardCharsets.UTF_8);
		this.index += len;
		return str;
	}

	@Override
	public Date readDate() {
		var val = this.readLong();
		if (val == 0L) {
			return null;
		}
		return new Date(val);
	}

	@Override
	public LocalDateTime readLocalDateTime() {
		var val = this.readLong();
		if (val == 0L) {
			return null;
		}
		var instant = Instant.ofEpochMilli(val);
		return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	@Override
	public Instant readInstant() {
		var val = this.readLong();
		if (val == 0L) {
			return null;
		}
		return Instant.ofEpochMilli(val);
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
