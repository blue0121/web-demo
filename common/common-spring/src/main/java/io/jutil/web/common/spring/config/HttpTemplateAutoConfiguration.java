package io.jutil.web.common.spring.config;

import io.jutil.web.common.spring.property.HttpProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2022-12-22
 */
@Configuration
@EnableConfigurationProperties(HttpProperties.class)
@ConditionalOnProperty(prefix = "jutil.common.http", name = "enabled", havingValue = "true")
public class HttpTemplateAutoConfiguration {


}
