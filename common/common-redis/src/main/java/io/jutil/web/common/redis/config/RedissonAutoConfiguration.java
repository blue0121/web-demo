package io.jutil.web.common.redis.config;

import io.jutil.web.common.redis.property.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2022-12-27
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(prefix = "jutil.common.redisson", name = "enabled", havingValue = "true")
public class RedissonAutoConfiguration {
    private static final String PREFIX = "redis://";
    private static final String SPLIT = "[\\s,;\\|]+";
	public RedissonAutoConfiguration() {
	}

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson(RedissonProperties prop) {
		var protocol = prop.getRedissonProtocol();
		var config = switch (protocol) {
			case SINGLE -> this.getSingleConfig(prop);
			case SENTINEL -> this.getSentinelConfig(prop);
			case CLUSTER -> this.getClusterConfig(prop);
		};

		return Redisson.create(config);
	}

	private Config getSingleConfig(RedissonProperties prop) {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(PREFIX + prop.getHost())
				.setConnectTimeout(prop.getTimeout())
				.setDatabase(prop.getDatabase())
				.setPassword(prop.getPassword());
		return config;
	}

	private Config getSentinelConfig(RedissonProperties prop) {
		Config config = new Config();
		config.useSentinelServers()
				.setMasterName(prop.getMasterName())
				.addSentinelAddress(this.getNodes(prop))
				.setConnectTimeout(prop.getTimeout())
				.setDatabase(prop.getDatabase())
				.setPassword(prop.getPassword());
		return config;
	}

	private Config getClusterConfig(RedissonProperties prop) {
		Config config = new Config();
		config.useClusterServers()
				.addNodeAddress(this.getNodes(prop))
				.setConnectTimeout(prop.getTimeout())
				.setPassword(prop.getPassword());
		return config;
	}

	private String[] getNodes(RedissonProperties prop) {
		if (prop.getHost() == null || prop.getHost().isEmpty()) {
			throw new IllegalArgumentException("Redisson host is required");
		}
		String[] nodes = prop.getHost().split(SPLIT);
		List<String> nodeList = new ArrayList<>();
		for (int i = 0; i < nodes.length; i++) {
			if (StringUtils.hasText(nodes[i])) {
				nodeList.add(PREFIX + nodes[i].trim());
			}
		}
		log.info("Redisson address: {}", nodeList);
		return nodeList.toArray(new String[0]);
	}

}
