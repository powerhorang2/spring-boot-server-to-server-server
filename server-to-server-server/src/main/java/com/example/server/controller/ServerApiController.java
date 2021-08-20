package com.example.server.controller;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.server.dto.Req;
import com.example.server.dto.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {
	
	// https://openapi.naver.com/v1/search/local.json
	// query=%EC%BB%A8%EC%8A%A4%EB%A7%88%EC%BC%80%ED%8C%85
	// display=10
	// start=1
	// sort=random
	
	@GetMapping("/naver")
	public String navar(@RequestParam(required = true, defaultValue = "대명동") String query) {
		
		URI uri = UriComponentsBuilder
				.fromUriString("https://openapi.naver.com")
				.path("/v1/search/local.json")
				.queryParam("query", query)
				.queryParam("display", 10)
				.queryParam("start", 1)
				.queryParam("sort", "random")
				.encode(Charset.forName("UTF-8"))
				.build()
				.toUri();
		
		log.info("uri : {}", uri);
				
		RestTemplate restTemplate = new RestTemplate();
		
		// 제네릭 Void 는 도저히 모르겠다.
		RequestEntity<Void> req = RequestEntity
				.get(uri)
				.header("X-Naver-Client-Id", "j7m7RQ8bZPts3THck_04")
				.header("X-Naver-Client-Secret", "TrUkT5jnyW")
				.build();
		
		ResponseEntity<String> result = restTemplate.exchange(req, String.class);
		return result.getBody();		
	}

	@GetMapping("/hello")
	public User hello(@RequestParam String name, @RequestParam int age) {
		User user = new User();
		user.setName(name);
		user.setAge(age);
		return user;
	}
	
	@PostMapping("/user/{userId}/name/{userName}")
	public Req<User> post(
					@RequestBody Req<User> user,
					@PathVariable int userId,
					@PathVariable String userName,
					@RequestHeader("x-authorization") String authorization,
					@RequestHeader("custom-header") String customHeader) {
		log.info("userId : {}, userName : {}", userId, userName);
		log.info("authorization : {}, customHeader : {}", authorization, customHeader);
		log.info("clent req : {}", user);
		
		Req<User> response = new Req<>();
		response.setHeader(
				new Req.Header()
		);
		
		response.setBody(user.getBody());
		
		
		return response;
	}
}
