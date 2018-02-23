package com.redhat.it.util.docker;

public class DockerAuthRequestException extends RuntimeException {

	public DockerAuthRequestException(final String message) {
		super(message);
	}

	public DockerAuthRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DockerAuthRequestException(final Throwable cause) {
		super(cause);
	}

	public DockerAuthRequestException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
