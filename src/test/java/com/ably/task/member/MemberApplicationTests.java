package com.ably.task.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApplicationTests {

	@LocalServerPort
	int port;

	RestTemplate client = new RestTemplate();

	private String greetingUrl() {
		return "http://localhost:" + port + "/greeting";
	}

	@DisplayName("1. 인증 실패")
	@Test
	void test_1() {
		String response = client.getForObject(greetingUrl(), String.class);
		System.out.println(response);
	}

}
