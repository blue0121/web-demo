package io.jutil.web.common.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jin Zheng
 * @since 2022-12-29
 */
@SpringBootApplication
public class Application {
	public Application() {
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
