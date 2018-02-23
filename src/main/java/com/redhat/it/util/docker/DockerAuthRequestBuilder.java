package com.redhat.it.util.docker;

import org.apache.http.client.methods.HttpUriRequest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Provides the ability to construct request sfor use against a docker endpoint
 *
 * @see <a href="https://docs.docker.com/registry/spec/auth/token/">Docker Token Spec</a>
 */
public class DockerAuthRequestBuilder implements Supplier<HttpUriRequest> {

	private final BasicAuthHeaderable credentials;
	private final String service;
	private boolean offline_token = false;
	private String client_id = "docker-httpclient-ath";
	private final Set<String> scopes = new HashSet<>();

	/**
	 * Constructs a basic docker auth request
	 *
	 * @param credentials user credentials associated with the request
	 * @param service name of the service that hosts the resource.  Typically an identifier associated with the protected
	 *                registry (I.E. https://my.proptected.registry).
	 */
	public DockerAuthRequestBuilder(final BasicAuthHeaderable credentials, final String service) {
		Objects.requireNonNull(credentials, "Cannot make docker authentication request without credentials");
		Objects.requireNonNull(service, "Cannot make docker authentication request without service parameter");

		this.credentials = credentials;
		this.service = service;
	}

	/**
	 * Customizes the client_id parameter sent to the auth server
	 *
	 * @param client_id identifier used for the client, does not need to be registered with the server
	 * @see <a href="https://tools.ietf.org/html/rfc6749#appendix-A.1">OAuth 2 Spec - client_id format</a>
	 */
	public DockerAuthRequestBuilder client_id(final String client_id) {
		this.client_id = client_id;
		return this;
	}

	/**
	 * Denotes whether the client wants an offline token back from the server.  False by default.
	 *
	 * @param offline_token true to request an offline token, false otherwise
	 */
	public DockerAuthRequestBuilder offline_token(final boolean offline_token) {
		this.offline_token = offline_token;
		return this;
	}

	/**
	 * Appends any unique Scope types to the request.  Empty by default.
	 *
	 * @param scope Scope to add to the existing scopes of the request
	 */
	public DockerAuthRequestBuilder scope(final Scope scope) {
		scopes.add(scope.toString());
		return this;
	}

	@Override
	public HttpUriRequest get() {
		return null;
	}
}
