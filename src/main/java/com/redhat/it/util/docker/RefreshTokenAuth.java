package com.redhat.it.util.docker;

import com.redhat.it.util.docker.Constants.Oauth;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience method for abstracting username/password auth
 */
public class RefreshTokenAuth implements OauthGrantable {

	private final String refresh_token;

	/**
	 * @param refresh_token base64-encoded refresh token to include in the grant parameters
	 */
	public RefreshTokenAuth(final String refresh_token) {
		this.refresh_token = refresh_token;
	}

	@Override
	public Map<String, String> asGrantParams() {
		Map<String,String> grantParams = new HashMap<>();
		grantParams.put(Oauth.GRANT_TYPE, Oauth.REFRESH_TOKEN);
		grantParams.put(Oauth.REFRESH_TOKEN, refresh_token);
		return grantParams;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final RefreshTokenAuth that = (RefreshTokenAuth) o;

		return refresh_token != null ? refresh_token.equals(that.refresh_token) : that.refresh_token == null;
	}

	@Override
	public int hashCode() {
		return refresh_token != null ? refresh_token.hashCode() : 0;
	}
}
