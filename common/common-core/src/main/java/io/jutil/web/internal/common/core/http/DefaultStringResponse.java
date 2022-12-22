package io.jutil.web.internal.common.core.http;

import io.jutil.web.common.core.http.StringResponse;
import io.jutil.web.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
public class DefaultStringResponse extends AbstractResponse<String> implements StringResponse {
	public DefaultStringResponse(HttpResponse<String> response) {
		super(response);

		if (log.isDebugEnabled()) {
			log.info("Http Response, code: {}, headers: {}, body: {}",
					this.getStatusCode(), this.getHeaders(), this.getBody());
		} else {
			log.info("Http Response, code: {}", this.getStatusCode());
		}
	}

	@Override
	public <T> T convertTo(Class<T> clazz) {
		return JsonUtil.fromString(this.getBody(), clazz);
	}
}
