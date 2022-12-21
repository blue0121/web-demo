package io.jutil.web.internal.common.core.http;

import io.jutil.web.common.core.http.StringResponse;
import io.jutil.web.common.core.util.JsonUtil;

import java.net.http.HttpResponse;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
public class DefaultStringResponse extends AbstractResponse<String> implements StringResponse {
	public DefaultStringResponse(HttpResponse<String> response) {
		super(response);
	}

	@Override
	public <T> T convertTo(Class<T> clazz) {
		return JsonUtil.fromString(this.getBody(), clazz);
	}
}
