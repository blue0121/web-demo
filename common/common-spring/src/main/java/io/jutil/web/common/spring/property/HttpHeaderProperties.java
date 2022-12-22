package io.jutil.web.common.spring.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 1.0 2020-07-27
 */
@Getter
@Setter
@NoArgsConstructor
public class HttpHeaderProperties {
	private String name;
	private String value;

}
