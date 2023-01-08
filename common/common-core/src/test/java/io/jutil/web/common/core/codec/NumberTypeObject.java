package io.jutil.web.common.core.codec;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
@Getter
@Setter
@NoArgsConstructor
public class NumberTypeObject implements ExternalSerializable {
	private Byte valByte;
	private Short valShort;
	private Integer valInt;
	private Long valLong;
	private Float valFloat;
	private Double valDouble;

	@Override
	public void encode(Encoder encoder) {
		encoder.writeNumber(valByte);
		encoder.writeNumber(valShort);
		encoder.writeNumber(valInt);
		encoder.writeNumber(valLong);
		encoder.writeNumber(valFloat);
		encoder.writeNumber(valDouble);
	}

	@Override
	public void decode(Decoder decoder) {
		this.valByte = decoder.readNumber(Byte.class);
		this.valShort = decoder.readNumber(Short.class);
		this.valInt = decoder.readNumber(Integer.class);
		this.valLong = decoder.readNumber(Long.class);
		this.valFloat = decoder.readNumber(Float.class);
		this.valDouble = decoder.readNumber(Double.class);
	}
}
