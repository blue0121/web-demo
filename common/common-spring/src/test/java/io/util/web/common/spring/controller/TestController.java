package io.util.web.common.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping(value = "/test",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public TestResponse say(@RequestBody TestRequest request) {
		log.info("name: {}", request.getName());
		var response = new TestResponse();
		response.setName(request.getName());
		response.setMessage("Hello, " + request.getName());
		return response;
	}

}
