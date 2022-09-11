package com.harsha.couponservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class CouponScervice {

	@GetMapping("/getcoupon/{client}")
	public ResponseEntity<?> coupon(@PathVariable(name = "client") String client,
			@RequestHeader(name = "AppToken") String appToken) {
		ResponseEntity<?> response = null;
		try {

			HttpComponentsClientHttpRequestFactory clienthhtprequest = new HttpComponentsClientHttpRequestFactory();
			clienthhtprequest.setConnectionRequestTimeout(1000);
			clienthhtprequest.setReadTimeout(1000);
			RestTemplate restTemplate = new RestTemplate(clienthhtprequest);

			MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
			header.add("AppToken", appToken);
			HttpEntity<Object> requestEntity = new HttpEntity<>(header);
			response = restTemplate.exchange("http://localhost:8892/coupondb/getcoupon/" + client, HttpMethod.GET,
					requestEntity, CouponEntity.class);
		} catch (HttpClientErrorException e) {

			if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {

			}

			e.printStackTrace();
			Error error = new Error();
			error.setErrorMessage(e.getResponseBodyAsString());
			return new ResponseEntity<Error>(error, e.getStatusCode());

		} catch (Exception e) {

		}

		return new ResponseEntity<CouponEntity>((CouponEntity) response.getBody(), HttpStatus.OK);

	}

	@PostMapping("/addcoupon")
	public ResponseEntity<?> addCoupon(@RequestBody CouponEntity couponEntity,
			@RequestHeader(name = "AppToken") String appToken) {
		ResponseEntity<?> response = null;
		try {

			HttpComponentsClientHttpRequestFactory clienthhtprequest = new HttpComponentsClientHttpRequestFactory();
			clienthhtprequest.setConnectionRequestTimeout(1000);
			clienthhtprequest.setReadTimeout(1000);
			RestTemplate restTemplate = new RestTemplate(clienthhtprequest);

			MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
			header.add("AppToken", appToken);
			HttpEntity<CouponEntity> requestEntity = new HttpEntity<>(couponEntity, header);
			response = restTemplate.exchange("http://localhost:8892/coupondb/addcoupon/", HttpMethod.POST,
					requestEntity, CouponEntity.class);
		} catch (HttpClientErrorException e) {

			if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {

			}

			e.printStackTrace();
			Error error = new Error();
			error.setErrorMessage(e.getResponseBodyAsString());
			return new ResponseEntity<Error>(error, e.getStatusCode());

		} catch (Exception e) {

		}

		return new ResponseEntity<CouponEntity>((CouponEntity) response.getBody(), HttpStatus.OK);

	}
}
