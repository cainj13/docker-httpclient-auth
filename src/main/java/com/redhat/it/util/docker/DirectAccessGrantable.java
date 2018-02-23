package com.redhat.it.util.docker;

import java.util.Map;

/**
 * Denotes a data structure that can be used to populate direct access grant parameters
 */
public interface DirectAccessGrantable {

	Map<String, String> asDirectAccessGrantParams();
}
