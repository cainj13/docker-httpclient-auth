package com.redhat.it.util.docker;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Scope {

	private final String resourceClass;
	private final String resourceName;
	private final Set<String> actions;

	public Scope(final String resourceClass, final String resourceName, final Set<String> actions) {
		this.resourceClass = resourceClass;
		this.resourceName = resourceName;
		this.actions = new HashSet<>(actions);
	}

	@Override
	public String toString() {
		return String.format("%s:%s:%s", resourceClass, resourceName, actions.stream().collect(Collectors.joining(",")));
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Scope scope = (Scope) o;

		if (resourceClass != null ? !resourceClass.equals(scope.resourceClass) : scope.resourceClass != null)
			return false;
		if (resourceName != null ? !resourceName.equals(scope.resourceName) : scope.resourceName != null) return false;
		return actions != null ? actions.equals(scope.actions) : scope.actions == null;
	}

	@Override
	public int hashCode() {
		int result = resourceClass != null ? resourceClass.hashCode() : 0;
		result = 31 * result + (resourceName != null ? resourceName.hashCode() : 0);
		result = 31 * result + (actions != null ? actions.hashCode() : 0);
		return result;
	}
}
