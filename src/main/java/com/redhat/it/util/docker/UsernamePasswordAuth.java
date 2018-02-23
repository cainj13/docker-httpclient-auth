package com.redhat.it.util.docker;

import java.util.AbstractMap;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * Convenience method for abstracting username/password auth
 */
public class UsernamePasswordAuth implements BasicAuthHeaderable, DirectAccessGrantable {

	private final String username;
	private final String password;

	public UsernamePasswordAuth(final String username, final String password) {
		Objects.requireNonNull("username cannot be null", username);
		Objects.requireNonNull("password cannot be null", password);

		this.username = username;
		this.password = password;
	}

	@Override
	public Map.Entry<String, String> asBasicAuthHeader() {
		final String basicAuthString = username + ":" + password;
		return new AbstractMap.SimpleImmutableEntry("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuthString.getBytes()));
	}

	@Override
	public Map<String, String> asDirectAccessGrantParams() {
		// TODO
		return null;
	}
}
