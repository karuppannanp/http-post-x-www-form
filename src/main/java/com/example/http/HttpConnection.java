package com.example.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author KXP8101
 *
 */
@Component
public class HttpConnection {

	@Bean
	public RestTemplate getRestTemplate() {

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		RequestConfig config = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

		CloseableHttpClient defaultHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
				.setConnectionManager(connectionManager).build();

		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(2);

		// Set up a default HTTP request factory
		ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				defaultHttpClient);

		return new RestTemplate(clientHttpRequestFactory);
	}

	public String postRequest(String host, String url, String prgCode, String numberOfCards, String barCodeType) throws Exception {
		String finalUrl = "http://" + host + ":80" + url;
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("prgcode", prgCode);
		map.add("numberofcards", numberOfCards);
		map.add("barcodetype", barCodeType);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> responseEntity = getRestTemplate().exchange(finalUrl, HttpMethod.POST, entity, String.class);
		return responseEntity.getBody();
	}
}
