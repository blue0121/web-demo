package io.jutil.web.common.core.validation;

import io.jutil.web.common.core.validation.annotation.Mobile;
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
public class MobileTest {
	@ParameterizedTest
	@CsvSource({"13000000000,true", ",true", "1300000000a,false", "1300000000,false"})
	public void testValid(String text, boolean valid) {
		var obj = new TestObject(text);
		if (valid) {
			ValidationUtil.valid(obj, GetOperation.class);
		} else {
			Assertions.assertThrows(ValidationException.class,
					() -> ValidationUtil.valid(obj, GetOperation.class),
					"无效的手机号码");
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TestObject {
		@Mobile(groups = GetOperation.class)
		private String mobile;
	}
}
