package com.redhat.it.util.docker;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RepositoryScope extends Scope {

	public static final String REPOSITORY = "repository";

	public RepositoryScope(final String resourceName, final Actions... actions) {
		super(REPOSITORY, resourceName, Arrays.stream(actions)
				.map(Actions::name)
				.collect(Collectors.toSet()));
	}

	public enum Actions {
		push, pull;
	}
}
