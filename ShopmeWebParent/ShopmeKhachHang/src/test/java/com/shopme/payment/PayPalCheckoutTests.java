package com.shopme.payment;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class PayPalCheckoutTests {
	
	private static final String URL_GET_ACCESS_TOKEN = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
	private static final String URL_GET_ORDER = "https://api-m.sandbox.paypal.com/v1/checkout/orders";
	
	private static final String CLIENT_ID = "AQNSC3xO7KE2S89dFSmtHXklFanLCWzkl4P8WvZP_0bwA71EXlSPNXvsmOz6zSl0qpWFu_xBLVcNWIEw";
	private static final String CLIENT_SECRET = "EKdb241iuRVApitj-vOL2HtpVwZhmDhilv7M-pq7ITlwccth6a6WLaB4eGp3XNB-YnWPG-sGlv8kvC4T";
	@Test
	public void testGetAccessToken() {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en_US");
		headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
		
		MultiValueMap<String, String> bodyParamMap = new LinkedMultiValueMap<>();
		bodyParamMap.add("grant_type", "client_credentials");		
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(bodyParamMap, headers);
		
//		ResponseEntity<String> response = restTemplate.postForEntity(URL_GET_ACCESS_TOKEN, request, String.class);
		
		AccessTokenResponse response = restTemplate.postForObject(URL_GET_ACCESS_TOKEN, 
				request, AccessTokenResponse.class);
		
//		System.out.println(response.toString());
		
		System.out.println(response.getAccessToken());
		
	}
	@Test
	public void testGetOrderDetails() {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en_US");
		headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
		
//		String orderId = "3L504664XF773054R";
		String orderId = "1KY288218F267360A";
		
		String url = URL_GET_ORDER + "/" + orderId;
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, 
				request, String.class);
		System.out.println(response.toString());

//		ResponseEntity<PayPalOrderResponse> response = restTemplate.exchange(url, HttpMethod.GET, 
//												request, PayPalOrderResponse.class);
//		PayPalOrderResponse responseBody = response.getBody();
//		
//		System.out.println("Order ID: " + responseBody.getId());		
//		System.out.println("Total Amount: " + responseBody.getGrossTotalAmount().getValue());
	}
}
