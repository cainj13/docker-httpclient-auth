package com.redhat.it.util.docker;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Provides the ability to construct Oauth2 requests for use against a docker endpoint
 *
 * @see <a href="https://docs.docker.com/registry/spec/auth/oauth/">Docker Oauth2 Spec</a>
 */
public class OAuthRequestBuilder implements Function<URI, HttpUriRequest> {

	private final OauthGrantable credentials;
	private final String service;
	private String client_id = DockerAuthRequestBuilder.DEFAULT_CLIENT_ID;
	private Optional<String> access_type = Optional.empty();
	private Optional<Scope> scope = Optional.empty();

	public OAuthRequestBuilder(final OauthGrantable credentials, final String service) {
		this.credentials = credentials;
		this.service = service;
	}

	/**
	 * Customizes the client_id parameter sent to the auth server
	 *
	 * @param client_id identifier used for the client, does not need to be registered with the server
	 * @see <a href="https://tools.ietf.org/html/rfc6749#appendix-A.1">OAuth 2 Spec - client_id format</a>
	 */
	public OAuthRequestBuilder client_id(final String client_id) {
		this.client_id = client_id;
		return this;
	}

	/**
	 * Denotes what kind of access is being requested.  Most commonly use to request "offline" token.
	 */
	public OAuthRequestBuilder access_type(final String access_type) {
		this.access_type = Optional.ofNullable(access_type);
		return this;
	}

	/**
	 * Sets the scope for the request.  Apparently there's a way to have multiple scopes with this grant type, but the Docker
	 * docs state some nonsensical phrase about "from the WWW-Authenticate header shown above" with nothing shown above.  Who knows.
	 *
	 * @param scope requested scope for this token
	 */
	public OAuthRequestBuilder scope(final Scope scope) {
		this.scope = Optional.ofNullable(scope);
		return this;
	}

	@Override
	public HttpUriRequest apply(final URI uri) {
		final List<NameValuePair> postParams = new ArrayList<>();

		credentials.asGrantParams().entrySet().stream()
				.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
				.forEach(postParams::add);

		postParams.add(new BasicNameValuePair("service", service));
		postParams.add(new BasicNameValuePair("client_id", client_id));

		access_type.ifPresent(access_type -> postParams.add(new BasicNameValuePair("access_type", access_type)));
		scope.ifPresent(scope -> postParams.add(new BasicNameValuePair("scope", scope.toString())));

		try {
			final HttpPost postRequest = new HttpPost(uri);
			postRequest.setEntity(new UrlEncodedFormEntity(postParams));

			return postRequest;
		} catch (UnsupportedEncodingException e) {
			throw new DockerAuthRequestException("Unable to encode POST form parameters", e);
		}
	}
}
