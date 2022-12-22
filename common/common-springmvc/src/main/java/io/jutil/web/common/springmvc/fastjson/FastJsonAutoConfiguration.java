package io.jutil.web.common.springmvc.fastjson;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Configuration
public class FastJsonAutoConfiguration implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		var converter = this.getConvertor();
		converters.add(0, converter);
	}

	private FastJsonHttpMessageConverter getConvertor() {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
		FastJsonConfig config = new FastJsonConfig();

		converter.setFastJsonConfig(config);
		return converter;
	}

	@Bean
	@ConditionalOnMissingBean(RestTemplate.class)
	public RestTemplate restTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		builder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		builder.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		var restTemplate = builder.build();
		var converterList = restTemplate.getMessageConverters();
		var iterator = converterList.iterator();
		while (iterator.hasNext()) {
			var converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
			if (converter instanceof GsonHttpMessageConverter
					|| converter instanceof MappingJackson2HttpMessageConverter) {
				iterator.remove();
			}
		}

		converterList.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
		converterList.add(this.getConvertor());

		return restTemplate;
	}
}
