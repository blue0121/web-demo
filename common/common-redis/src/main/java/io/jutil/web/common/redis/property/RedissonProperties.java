package io.jutil.web.common.redis.property;

import io.jutil.web.common.core.util.AssertUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2022-12-27
 */
@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "jutil.common.redisson")
public class RedissonProperties implements InitializingBean {
	private String protocol;
	private String masterName;
	private String host;
	private String password;
	private int database;
	private int timeout;

	private RedissonProtocol redissonProtocol;

	@Override
	public void afterPropertiesSet() throws Exception {
		AssertUtil.notEmpty(protocol, "Protocol");
		AssertUtil.notEmpty(host, "Host");

		for (var p : RedissonProtocol.values()) {
			if (p.name().equalsIgnoreCase(protocol)) {
				this.redissonProtocol = p;
				return;
			}
		}
		throw new IllegalArgumentException("无效protocol值: " + protocol);
	}
}
