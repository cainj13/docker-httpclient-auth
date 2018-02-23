package com.redhat.it.util.docker;

import java.util.AbstractMap;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Convenience method for abstracting username/password auth
 */
public class UsernamePasswordAuth implements AuthHeaderable, OauthGrantable {

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
	public Map<String, String> asGrantParams() {
		final Map<String, String> grantParams = new HashMap<>();
		grantParams.put("grant_type", "password");
		grantParams.put("username", username);
		grantParams.put("password", password);
		return grantParams;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final UsernamePasswordAuth that = (UsernamePasswordAuth) o;

		if (username != null ? !username.equals(that.username) : that.username != null) return false;
		return password != null ? password.equals(that.password) : that.password == null;
	}

	@Override
	public int hashCode() {
		int result = username != null ? username.hashCode() : 0;
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}
}
