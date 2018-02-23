# Apache HttpClient + Docker Auth
Basic library to quickly form proper Docker Auth requests for use with Apache's HttpClient libraries

##### Request a Token Using Docker Token GET call:
```
        final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		final HttpUriRequest dockerAuthRequest = new DockerAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.apply(new URI(dockerAuthUrl));

		final CloseableHttpResponse response = httpClient.execute(dockerAuthRequest);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

```

##### + Additional Parameter Customization
```
		final HttpUriRequest dockerAuthRequest = new DockerAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.offline_token(true)
				.client_id("my-client")
				.scope(new RepositoryScope("jboss/keycloak", RepositoryScope.Actions.PULL))
				.apply(new URI(dockerAuthUrl));

```
##### Request a Token Using Oauth POST call:
```
        final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		final HttpUriRequest oauthAuthRequest = new OAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.apply(new URI(dockerOauthUrl));

		final CloseableHttpResponse response = httpClient.execute(oauthAuthRequest);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

```
##### + Additoinal Oauth Paramaeter Customization:
```
		final HttpUriRequest oauthAuthRequest = new OAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.access_type("offline_access")
				.client_id("my-client")
				.scope(new RepositoryScope("jboss/widlfly:latest", RepositoryScope.Actions.push, RepositoryScope.Actions.pull))
				.apply(new URI(dockerOauthUrl));
```
##### Use a refresh token:
```
		final HttpUriRequest oauthAuthRequest = new OAuthRequestBuilder(new RefreshTokenAuth("12AGF...43G"), dockerService)
				.apply(new URI(dockerOauthUrl));
```

Happy coding!