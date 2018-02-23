package com.redhat.it.util.docker;

import com.redhat.it.util.docker.RepositoryScope.Actions;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RepositoryScopeTest {

	@Test
	public void shouldProduceValidRepositoryScope() {
		final Scope repositoryScope = new RepositoryScope("jboss/wildfly", Actions.pull, Actions.push);
		assertThat(repositoryScope.toString(), equalTo("repository:jboss/wildfly:pull,push"));
	}
}
