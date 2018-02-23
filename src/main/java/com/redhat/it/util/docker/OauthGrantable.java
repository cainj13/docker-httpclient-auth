package com.redhat.it.util.docker;

import java.util.Map;

/**
 * Denotes a data structure that can be used to populate refresh token grant parameters.  This map should include:
 *
 * <ul>
 *     <li>grant_type</li>
 *     <li>n number of grant-specific params</li>
 * </ul>
 *
 * For example, if using a <code>grant_type</code> of <code>password</code>, the map would look like this:
 *
 * <ul>
 *     <li>grant_type=password</li>
 *     <li>username=user1</li>
 *     <li>password=password</li>
 * </ul>
 */
public interface OauthGrantable {

	Map<String, String> asGrantParams();
}
