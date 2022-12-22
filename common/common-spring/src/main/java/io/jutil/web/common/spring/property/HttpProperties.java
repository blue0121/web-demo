package io.jutil.web.common.spring.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 1.0 2020-07-15
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("jutil.common.http")
public class HttpProperties {
	private List<HttpConfigProperties> configs;

}
