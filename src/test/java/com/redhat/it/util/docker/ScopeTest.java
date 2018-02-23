package com.redhat.it.util.docker;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ScopeTest {

	@Test
	public void shouldProduceValidScope() {
		final Scope scope = new Scope("repository", "jboss/wildfly", new HashSet<>(Arrays.asList("push","pull")));
		assertThat(scope.toString(), equalTo("repository:jboss/wildfly:pull,push"));
	}
}
