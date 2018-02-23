package com.redhat.it.util.docker;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.MatcherAssert;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Base64;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class AuthServerIT {
	private static final Logger log = LoggerFactory.getLogger(AuthServerIT.class);

	static String dockerAuthUrl;
	static String dockerOauthUrl;
	static String dockerService;
	static String username;
	static String password;

	CloseableHttpClient httpClient;

	@BeforeClass
	public static void setUp_beforeClass() {
		try {
			Assume.assumeNotNull(dockerAuthUrl = System.getProperty("docker.auth.url"));
			Assume.assumeNotNull(dockerOauthUrl = System.getProperty("docker.oauth.url"));
			Assume.assumeNotNull(dockerService = System.getProperty("docker.service"));
			Assume.assumeNotNull(username = System.getProperty("docker.username"));
			Assume.assumeNotNull(password = System.getProperty("docker.password"));
		} catch (AssumptionViolatedException e) {
			log.warn("AuthServerIT will not run without the following build properties: -Ddocker.auth.url= -Ddocker.oauth.url= -Ddocker.username= -Ddocker.password=");
			throw e;
		}
	}

	@Before
	public void setUp() {
		httpClient = HttpClientBuilder.create().build();
	}

	@After
	public void tearDown() throws IOException {
		httpClient.close();
	}

	@Test
	public void should200WhenValidDockerAuth() throws Exception {
		final HttpUriRequest dockerAuthRequest = new DockerAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.apply(new URI(dockerAuthUrl));

		final CloseableHttpResponse response = httpClient.execute(dockerAuthRequest);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}

	@Test
	public void shouldIssueOfflineTokenForDockerAuth() throws Exception {
		final HttpUriRequest dockerAuthRequest = new DockerAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.offline_token(true)
				.apply(new URI(dockerAuthUrl));

		final CloseableHttpResponse response = httpClient.execute(dockerAuthRequest);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

		// Check that the JSON is well-formed according to docker spec
		final JSONObject jsonResponse = getEntityJson(response);
		MatcherAssert.assertThat("Access token (access_token) not included in successful auth response", jsonResponse.has("access_token"), equalTo(true));
		MatcherAssert.assertThat("Access token (token) not included in successful auth response", jsonResponse.has("token"), equalTo(true));
		MatcherAssert.assertThat("access_token and token field differ", jsonResponse.get("access_token"), equalTo(jsonResponse.get("token")));

		// Should issue offline token if we ask for one
		log.debug("Refresh token response: \n\n" + jsonResponse + "\n\n");
		MatcherAssert.assertThat("Refresh token was not issued when requested.", jsonResponse.has("refresh_token"), equalTo(true));

		// Check that the individual tokens are correct
		final String refresh_token = jsonResponse.getString("refresh_token");
		final JSONObject refreshToken = new JSONObject(new String(Base64.getDecoder().decode(refresh_token.split("\\.")[1])));
		MatcherAssert.assertThat(refreshToken.getString("azp"), equalTo("docker-registry"));
		MatcherAssert.assertThat(refreshToken.getString("exp"), equalTo("0"));
		MatcherAssert.assertThat(refreshToken.has("session_state"), equalTo(true));
	}

	@Test
	public void should200WhenValidOauthAuth() throws Exception {
		final HttpUriRequest oauthAuthRequest = new OAuthRequestBuilder(new UsernamePasswordAuth(username, password), dockerService)
				.apply(new URI(dockerOauthUrl));

		final CloseableHttpResponse response = httpClient.execute(oauthAuthRequest);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}

	private static JSONObject getEntityJson(final CloseableHttpResponse response) throws IOException, JSONException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return new JSONObject(result.toString());
	}
}
