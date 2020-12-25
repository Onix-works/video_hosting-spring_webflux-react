package com.me.webf;

import javax.annotation.PostConstruct;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.me.webf.misc.AuthRequest;
import com.me.webf.model.JWTCookieUser;
import com.me.webf.model.services.JWTCookieUserService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class SecurityTests {
	
	@MockBean
	JWTCookieUserService userService;
	
	@Autowired
	private WebTestClient webClient;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private JWTCookieUser testuser;
	
	@PostConstruct
	public void init() {
		
		testuser = new JWTCookieUser("testuser");
		testuser.setPassword(passwordEncoder.encode("testpassword"));
		testuser.setAvatarId("testid");
		testuser.getVideoInfoIds().add("1");
		
		Mockito.when(userService.findByUsername(Mockito.anyString()))
			.thenReturn(Mono.just(testuser));
	}
	
	@Test
	void testSecureURIWhenUnauthorized() {
		
		webClient.post()
		.uri("/api/videos")
		.exchange()
		.expectStatus()
		.isUnauthorized();
	}
	
	@Test
	void testLogin() {	
		
		AuthRequest req = new AuthRequest("testuser", "testpassword" );

		webClient.post()
		.uri("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(req)
		.exchange()
		.expectStatus()
		.isOk()
		.expectHeader().value("Set-Cookie", Matchers.startsWith("token="));
	}
	
	@Test
	void testLoginWrongCredentials() {
		
		AuthRequest req = new AuthRequest("wronguser", "wrongpassword" );

		webClient.post()
		.uri("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(req)
		.exchange()
		.expectStatus()
		.isUnauthorized()
		.expectHeader().doesNotExist("Set-Cookie");
	
	}

	@WithMockUser
	@Test
	void testLogout() {
		
		webClient.get()
		.uri("/api/logout")
		.exchange()
		.expectStatus()
		.isOk()
		.expectHeader().value("Set-Cookie", Matchers.startsWith("token=; Max-Age=0;"));
	
	}
	
	@WithMockUser(username = "testuser", password = "testpassword")
	@Test
	void testCheckIfAuthenticated() {
		
		webClient.get()
		.uri("/api/user")
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody(AuthRequest.class).isEqualTo(new AuthRequest("testuser", "testpassword"));
	
	}
}
