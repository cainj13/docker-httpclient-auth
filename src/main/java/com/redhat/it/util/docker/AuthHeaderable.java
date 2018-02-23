package com.redhat.it.util.docker;

import java.util.Map;

/**
 * Data structre that can be represented as an auth header.  This will most commonly be used for basic auth, I.E.:
 *
 * credentials: user1/password
 * Map.Entry representation: key="Authorization", value="Basic dXNlcjE6cGFzc3dvcmQ="
 */
public interface AuthHeaderable {

	/**
	 * @return Entry in which the key is the header key, and value is the header content.  I.E. key="Authorization" value="Basic ABC..DEF"
	 */
	Map.Entry<String, String> asBasicAuthHeader();
}
