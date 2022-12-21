package io.jutil.web.common.core.dict;

/**
 * HTTP 方法
 *
 * @author zhengj
 * @since 1.0 2017年1月14日
 */
public final class HttpMethod extends Dictionary {
	public static final HttpMethod GET = new HttpMethod(1, "GET", Color.PRIMARY);
	public static final HttpMethod POST = new HttpMethod(2, "POST", Color.DANGER);
	public static final HttpMethod PUT = new HttpMethod(3, "PUT", Color.WARNING);
	public static final HttpMethod DELETE = new HttpMethod(4, "DELETE", Color.INFO);
	public static final HttpMethod PATCH = new HttpMethod(4, "PATCH", Color.SUCCESS);

	private HttpMethod(int index, String name, Color color) {
		super(index, name, color);
	}

}
