package io.jutil.web.common.spring.config;

import io.jutil.web.common.spring.property.HttpConfigProperties;
import io.jutil.web.common.spring.property.HttpHeaderProperties;
import io.jutil.web.common.spring.property.HttpProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 1.0 2020-07-16
 */
public class HttpTemplateBeanPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor, BeanFactoryAware {

	private HttpProperties httpProperties;
	private HttpClient httpClient;

	private BeanFactory beanFactory;
	private BeanDefinitionRegistry registry;

	public HttpTemplateBeanPostProcessor() {
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		this.registry = (BeanDefinitionRegistry) factory;
	}

	private void initHttpClient() {
		this.httpClient = HttpClient.newHttpClient();
	}

	private void registryHttpClient(HttpConfigProperties config) {
		Map<String, String> headerMap = new HashMap<>();
		if (config.getHeaders() != null && !config.getHeaders().isEmpty()) {
			for (HttpHeaderProperties header : config.getHeaders()) {
				headerMap.put(header.getName(), header.getValue());
			}
		}
		RootBeanDefinition definition = new RootBeanDefinition(HttpTemplateFactoryBean.class);
		MutablePropertyValues propertyValues = definition.getPropertyValues();
		propertyValues.addPropertyValue("defaultHeaders", headerMap);
		propertyValues.addPropertyValue("id", config.getId());
		propertyValues.addPropertyValue("baseUrl", config.getBaseUrl());
		propertyValues.addPropertyValue("username", config.getUsername());
		propertyValues.addPropertyValue("password", config.getPassword());
		propertyValues.addPropertyValue("httpClient", httpClient);
		registry.registerBeanDefinition(config.getId(), definition);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (httpProperties != null) {
			synchronized (this) {
				if (httpProperties != null) {
					return bean;
				}
			}
		}

		httpProperties = beanFactory.getBean(HttpProperties.class);
		if (httpProperties.getConfigs() == null) {
			this.initHttpClient();
			return bean;
		}

		for (HttpConfigProperties config : httpProperties.getConfigs()) {
			this.registryHttpClient(config);
		}

		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
