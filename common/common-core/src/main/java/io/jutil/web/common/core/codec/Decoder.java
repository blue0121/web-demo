package io.jutil.web.common.core.codec;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-01-06
 */
public interface Decoder {

	void resetReadIndex();

	void resetReadIndex(int offset);

	void skipReadIndex(int offset);

	byte readByte();

	int readBytes(byte[] buf);

	short readShort();

	int readInt();

	long readLong();

	float readFloat();

	double readDouble();

	boolean readBoolean();

	char readChar();

	String readString();

	Date readDate();

	LocalDateTime readLocalDateTime();

	Instant readInstant();

	<T extends Number> T readNumber(Class<T> clazz);

	int getReadIndex();

	int getSize();

	int getCapacity();
}
