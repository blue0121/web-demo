package io.jutil.web.common.redis.property;

/**
 * @author Jin Zheng
 * @since 2022-12-27
 */
public enum RedissonProtocol {
	/**
	 * connection: single
	 */
	SINGLE,

	/**
	 * connection: sentinel
	 */
	SENTINEL,

	/**
	 * connection: cluster
	 */
	CLUSTER;
}
