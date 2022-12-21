package io.jutil.web.common.core.http;


import io.jutil.web.common.core.dict.HttpMethod;
import io.jutil.web.internal.common.core.http.DefaultHttpTemplateBuilder;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
public interface HttpTemplate {
	String FILE_UPLOAD_KEY = "file";

	static HttpTemplateBuilder builder() {
		return new DefaultHttpTemplateBuilder();
	}

	// get request sync
	default StringResponse getSync(String uri) {
		return this.requestSync(uri, HttpMethod.GET.getName(), null, null);
	}
	default StringResponse getSync(String uri, Map<String, String> headers) {
		return this.requestSync(uri, HttpMethod.GET.getName(), null, headers);
	}
	// get request async
	default CompletableFuture<StringResponse> getAsync(String uri) {
		return this.requestAsync(uri, HttpMethod.GET.getName(), null, null);
	}
	default CompletableFuture<StringResponse> getAsync(String uri, Map<String, String> headers) {
		return this.requestAsync(uri, HttpMethod.GET.getName(), null, headers);
	}

	// post request sync
	default StringResponse postSync(String uri, String body) {
		return this.requestSync(uri, HttpMethod.POST.getName(), body, null);
	}
	default StringResponse postSync(String uri, String body, Map<String, String> headers) {
		return this.requestSync(uri, HttpMethod.POST.getName(), body, headers);
	}
	// post request async
	default CompletableFuture<StringResponse> postAsync(String uri, String body) {
		return this.requestAsync(uri, HttpMethod.POST.getName(), body, null);
	}
	default CompletableFuture<StringResponse> postAsync(String uri, String body, Map<String, String> headers) {
		return this.requestAsync(uri, HttpMethod.POST.getName(), body, headers);
	}

	// request sync
	default StringResponse requestSync(String uri, String method, String body) {
		return this.requestSync(uri, method, body, null);
	}
	StringResponse requestSync(String uri, String method, String body, Map<String, String> header);
	// request async
	default CompletableFuture<StringResponse> requestAsync(String uri, String method, String body) {
		return this.requestAsync(uri, method, body, null);
	}
	CompletableFuture<StringResponse> requestAsync(String uri, String method, String body, Map<String, String> header);

	// download sync
	default PathResponse downloadSync(String uri, Path file) {
		return this.downloadSync(uri, HttpMethod.GET.getName(), null, file, null);
	}
	default PathResponse downloadSync(String uri, String method, String body, Path file) {
		return this.downloadSync(uri, method, body, file, null);
	}
	PathResponse downloadSync(String uri, String method, String body, Path file, Map<String, String> header);

	// download async
	default CompletableFuture<PathResponse> downloadAsync(String uri, Path file) {
		return this.downloadAsync(uri, HttpMethod.GET.getName(), null, file, null);
	}
	default CompletableFuture<PathResponse> downloadAsync(String uri, String method, String body, Path file) {
		return this.downloadAsync(uri, method, body, file, null);
	}
	CompletableFuture<PathResponse> downloadAsync(String uri, String method, String body, Path file, Map<String, String> header);

	// upload sync
	default StringResponse uploadSync(String uri, String method, Path file) {
		Map<String, Path> fileParam = Map.of(FILE_UPLOAD_KEY, file);
		return this.uploadSync(uri, method, null, fileParam, null);
	}
	default StringResponse uploadSync(String uri, String method, Path file, Map<String, String> header) {
		Map<String, Path> fileParam = Map.of(FILE_UPLOAD_KEY, file);
		return this.uploadSync(uri, method, null, fileParam, header);
	}
	StringResponse uploadSync(String uri, String method, Map<String, String> textParam, Map<String, Path> fileParam, Map<String, String> header);

	// upload async
	default CompletableFuture<StringResponse> uploadAsync(String uri, String method, Path file) {
		Map<String, Path> fileParam = Map.of(FILE_UPLOAD_KEY, file);
		return this.uploadAsync(uri, method, null, fileParam, null);
	}
	default CompletableFuture<StringResponse> uploadAsync(String uri, String method, Path file, Map<String, String> header) {
		Map<String, Path> fileParam = Map.of(FILE_UPLOAD_KEY, file);
		return this.uploadAsync(uri, method, null, fileParam, header);
	}
	CompletableFuture<StringResponse> uploadAsync(String uri, String method, Map<String, String> textParam, Map<String, Path> fileParam, Map<String, String> header);

	String getId();

	String getBaseUrl();

	String getUsername();

	String getPassword();

	Map<String, String> getDefaultHeaders();

}
