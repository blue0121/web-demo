package io.jutil.web.common.core.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Slf4j
@RestController
public class TestController {

	@GetMapping("/test/{name}")
	public String say(@PathVariable("name") String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

}
