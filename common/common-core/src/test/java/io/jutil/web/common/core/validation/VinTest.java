package io.jutil.web.common.core.validation;

import io.jutil.web.common.core.validation.annotation.Vin;
import io.jutil.web.common.core.validation.group.GetOperation;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2023-01-03
 */
public class VinTest {
	@ParameterizedTest
	@CsvSource({"01234567891234567,true", ",true", "0,false"})
	public void testValid(String text, boolean valid) {
		var obj = new TestObject(text);
		if (valid) {
			ValidationUtil.valid(obj, GetOperation.class);
		} else {
			Assertions.assertThrows(ValidationException.class,
					() -> ValidationUtil.valid(obj, GetOperation.class),
					"无效的车架号");
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TestObject {
		@Vin(groups = GetOperation.class)
		private String vin;
	}
}
