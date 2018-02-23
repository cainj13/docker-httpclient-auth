package com.redhat.it.util.docker;

import java.util.Map;

/**
 * Data structre that can be represented as a basic auth header
 */
public interface BasicAuthHeaderable {

	/**
	 * @return Entry in which the key is the header key, and value is the header content.  I.E. key="Authorization" value="Basic ABC..DEF"
	 */
	Map.Entry<String, String> asBasicAuthHeader();
}
