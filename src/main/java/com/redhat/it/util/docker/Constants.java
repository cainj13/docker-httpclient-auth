package com.redhat.it.util.docker;

public class Constants {

	public static final String DEFAULT_CLIENT_ID = "docker-httpclient-auth";

	public static class Parameters {
		public static final String CLIENT_ID = "client_id";
		public static final String SERVICE = "service";
		public static final String SCOPE = "scope";
		public static final String ACCESS_TYPE = "access_type";
		public static final String OFFLINE_TOKEN = "offline_token";
	}

	public static class Oauth {
		public static final String GRANT_TYPE = "grant_type";
		public static final String REFRESH_TOKEN = "refresh_token";
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
	}
}
