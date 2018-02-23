package com.redhat.it.util.docker;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UsernamePasswordAuthTest {

	@Test
	public void shouldBuildBasicAuthHeaderCorrectly() {
		final BasicAuthHeaderable credentials = new UsernamePasswordAuth("user1", "password");
		final Map.Entry<String, String> basicAuthHeader = credentials.asBasicAuthHeader();
		assertThat(basicAuthHeader.getKey(), equalTo("Authorization"));
		assertThat(basicAuthHeader.getValue(), equalTo("Basic dXNlcjE6cGFzc3dvcmQ="));
	}
}
