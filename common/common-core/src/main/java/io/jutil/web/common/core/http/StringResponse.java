package io.jutil.web.common.core.http;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
public interface StringResponse extends Response<String> {

	<T> T convertTo(Class<T> clazz);

}
