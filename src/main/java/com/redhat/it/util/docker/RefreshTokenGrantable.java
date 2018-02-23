package com.redhat.it.util.docker;

import java.util.Map;

/**
 * Denotes a data structure that can be used to populate refresh token grant parameters
 */
public interface RefreshTokenGrantable {

	Map<String, String> asRefreshTokenGrantParams();
}
