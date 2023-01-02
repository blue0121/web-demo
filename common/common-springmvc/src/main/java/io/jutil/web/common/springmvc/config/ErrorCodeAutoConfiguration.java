package io.jutil.web.common.springmvc.config;

import io.jutil.web.common.springmvc.exception.ErrorCodeExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Jin Zheng
 * @since 2023-01-02-17:02
 */
@Configuration
@Import(ErrorCodeExceptionHandler.class)
public class ErrorCodeAutoConfiguration {
}
