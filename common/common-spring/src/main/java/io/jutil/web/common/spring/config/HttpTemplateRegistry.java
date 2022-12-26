package io.jutil.web.common.spring.config;

import io.jutil.web.common.spring.property.HttpConfigProperties;
import io.jutil.web.common.spring.property.HttpHeaderProperties;
import io.jutil.web.common.spring.property.HttpProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2022-12-26
 */
public class HttpTemplateRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private HttpProperties httpProperties;

	public HttpTemplateRegistry() {
	}

    private HttpClient initHttpClient() {
        return HttpClient.newHttpClient();
    }

    private void registryHttpClient(BeanDefinitionRegistry registry, HttpClient httpClient,
                                    HttpConfigProperties config) {
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
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (httpProperties.getConfigs() == null || httpProperties.getConfigs().isEmpty()) {
            return;
        }

        var httpClient = this.initHttpClient();
        for (var config : httpProperties.getConfigs()) {
            this.registryHttpClient(registry, httpClient, config);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        var result = Binder.get(environment).bind("jutil.common.http", HttpProperties.class);
        this.httpProperties = result.get();
    }
}
