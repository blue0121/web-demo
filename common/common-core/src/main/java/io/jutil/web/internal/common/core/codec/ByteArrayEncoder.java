package io.jutil.web.internal.common.core.codec;

import io.jutil.web.common.core.codec.Encoder;
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
public class ByteArrayEncoder implements Encoder {

	private int index = 0;
	private int capacity = 0;
	private byte[] buffer;

	public ByteArrayEncoder() {
	}
	@Override
	public void resetWriteIndex() {
		this.resetWriteIndex(CodecConstant.ZERO_IDX);
	}

	@Override
	public void resetWriteIndex(int offset) {
		if (offset == CodecConstant.ZERO_IDX) {
			this.index = CodecConstant.ZERO_IDX;
			return;
		}

		if (offset < CodecConstant.ZERO_IDX || offset >= capacity) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + capacity);
		}

		this.index = offset;
	}

	@Override
	public void skipWriteIndex(int offset) {
		if (index + offset < CodecConstant.ZERO_IDX || index + offset >= capacity) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + (capacity - index));
		}

		this.index += offset;
	}

	private void addCapacity(int size) {
		AssertUtil.positive(size, "Size");
		int oldCap = capacity;
		int times = (capacity - index + size + CodecConstant.INC_CAP - 1) / CodecConstant.INC_CAP;
		this.capacity = times << CodecConstant.INC_LEFT_SHIFT;
		if (this.capacity == oldCap) {
			return;
		}
		byte[] newBuf = new byte[this.capacity];
		if (buffer != null) {
			System.arraycopy(buffer, CodecConstant.ZERO_IDX, newBuf, CodecConstant.ZERO_IDX, buffer.length);
		}
		this.buffer = newBuf;
	}

	@Override
	public void writeByte(byte val) {
		this.addCapacity(CodecConstant.BYTE_LEN);
		this.buffer[index++] = val;
	}

	@Override
	public void writeBytes(byte[] val) {
		AssertUtil.notEmpty(val, "Value");
		this.addCapacity(val.length);
		System.arraycopy(val, CodecConstant.ZERO_IDX, buffer, index, val.length);
		this.index += val.length;
	}

	@Override
	public void writeShort(short val) {
		this.addCapacity(CodecConstant.SHORT_LEN);
		buffer[index++] = (byte) ((val >> 8) & 0xff);
		buffer[index++] = (byte) (val & 0xff);
	}

	@Override
	public void writeInt(int val) {
		this.addCapacity(CodecConstant.INT_LEN);
		buffer[index++] = (byte) ((val >> 24) & 0xff);
		buffer[index++] = (byte) ((val >> 16) & 0xff);
		buffer[index++] = (byte) ((val >> 8) & 0xff);
		buffer[index++] = (byte) (val & 0xff);
	}

	@Override
	public void writeLong(long val) {
		this.addCapacity(CodecConstant.LONG_LEN);
		buffer[index++] = (byte) ((val >> 56) & 0xff);
		buffer[index++] = (byte) ((val >> 48) & 0xff);
		buffer[index++] = (byte) ((val >> 40) & 0xff);
		buffer[index++] = (byte) ((val >> 32) & 0xff);
		buffer[index++] = (byte) ((val >> 24) & 0xff);
		buffer[index++] = (byte) ((val >> 16) & 0xff);
		buffer[index++] = (byte) ((val >> 8) & 0xff);
		buffer[index++] = (byte) (val & 0xff);
	}

	@Override
	public void writeFloat(float val) {
		this.writeInt(Float.floatToRawIntBits(val));
	}

	@Override
	public void writeDouble(double val) {
		this.writeLong(Double.doubleToRawLongBits(val));
	}

	@Override
	public void writeBoolean(boolean val) {
		this.addCapacity(CodecConstant.BYTE_LEN);
		buffer[index++] = val ? (byte) 1 : (byte) 0;
	}

	@Override
	public void writeChar(char val) {
		this.addCapacity(CodecConstant.SHORT_LEN);
		buffer[index++] = (byte) ((val >> 8) & 0xff);
		buffer[index++] = (byte) (val & 0xff);
	}

	@Override
	public void writeString(String val) {
		if (val == null || val.isEmpty()) {
			this.writeInt(CodecConstant.LEN_EMPTY);
			return;
		}
		byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
		this.writeInt(bytes.length);
		this.writeBytes(bytes);
	}

	@Override
	public void writeDate(Date val) {
		if (val == null) {
			this.writeLong(CodecConstant.LEN_EMPTY);
			return;
		}

		this.writeLong(val.getTime());
	}

	@Override
	public void writeLocalDateTime(LocalDateTime val) {
		if (val == null) {
			this.writeLong(CodecConstant.LEN_EMPTY);
			return;
		}
		var instant = val.atZone(ZoneId.systemDefault()).toInstant();
		this.writeLong(instant.toEpochMilli());
	}

	@Override
	public void writeInstant(Instant val) {
		if (val == null) {
			this.writeLong(CodecConstant.LEN_EMPTY);
			return;
		}
		this.writeLong(val.toEpochMilli());
	}

	@Override
	public int getWriteIndex() {
		return index;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	public byte[] getByteArray() {
		if (index == CodecConstant.ZERO_IDX) {
			return new byte[CodecConstant.ZERO_IDX];
		}
		byte[] newBuf = new byte[index];
		System.arraycopy(buffer, CodecConstant.ZERO_IDX, newBuf, CodecConstant.ZERO_IDX, index);
		return newBuf;
	}
}
