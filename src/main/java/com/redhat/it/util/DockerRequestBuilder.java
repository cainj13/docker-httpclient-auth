package com.redhat.it.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DockerRequestBuilder {

	private final String requestUrl;
	private final Map<String, String> params = new HashMap<>();
	private final String httpMethod;

	private Optional<String> basicAuthUsername;
	private Optional<String> basicAuthPassword;

	public DockerRequestBuilder(final String requestUrl, final String httpMethod) {
		this.requestUrl = requestUrl;
		this.httpMethod = httpMethod;
	}

	public DockerRequestBuilder service(final String service) {
		params.put("service", service);
		return this;
	}

	public DockerRequestBuilder grant_type(final String grantType) {
		params.put("grant_type", grantType);
		return this;
	}

	public DockerRequestBuilder access_type(final String accessType) {
		params.put("access_type", accessType);
		return this;
	}

	public DockerRequestBuilder client_id(final String clientId) {
		params.put("client_id", clientId);
		return this;
	}

	public DockerRequestBuilder refresh_token(final String refreshToken) {
		params.put("refresh_token", refreshToken);
		return this;
	}

	public DockerRequestBuilder offline_token(final boolean offlineToken) {
		params.put("offline_token", Boolean.toString(offlineToken));
		return this;
	}

	public DockerRequestBuilder username(final String username) {
		params.put("username", username);
		return this;
	}

	public DockerRequestBuilder password(final String password) {
		params.put("password", password);
		return this;
	}

	public DockerRequestBuilder scope(final String scope) {
		params.put("scope", scope);
		return this;
	}

	public DockerRequestBuilder withBasicAuthHeader(final String username, final String password) {
		basicAuthUsername = Optional.of(username);
		basicAuthPassword = Optional.of(password);

		return this;
	}

	final HttpUriRequest build() throws URISyntaxException, UnsupportedEncodingException {
//		if (HttpMethod.GET.equals(httpMethod)) {
			final URIBuilder uriBuilder = new URIBuilder(requestUrl);
			params.entrySet().stream()
					.forEach(entry -> uriBuilder.addParameter(entry.getKey(), entry.getValue()));

			final HttpGet getRequest = new HttpGet(uriBuilder.build());
			if (basicAuthUsername.isPresent() && basicAuthPassword.isPresent()) {
				final String basicAuthString = basicAuthUsername.get() + ":" + basicAuthPassword.get();
				getRequest.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuthString.getBytes()));
			}

			return getRequest;

//		} else if (HttpMethod.POST.equals(httpMethod)) {
//			final HttpPost postRequest = new HttpPost(requestUrl);
//
//			final List<NameValuePair> postParams = new ArrayList<>();
//			params.entrySet().stream()
//					.forEach(entry -> postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())));
//			postRequest.setEntity(new UrlEncodedFormEntity(postParams));
//
//			return  postRequest;
//		}

//		throw new IllegalArgumentException("Unsupported Http Method specified");
	}
}
