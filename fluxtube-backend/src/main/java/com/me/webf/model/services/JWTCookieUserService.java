package com.me.webf.model.services;

import com.me.webf.model.JWTCookieUser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JWTCookieUserService {

	Flux<JWTCookieUser> findAll();

	Mono<JWTCookieUser> findById(String id);
	
	Mono<JWTCookieUser> findByUsername(String id);

	Mono<JWTCookieUser> save(Mono<JWTCookieUser> user);

	Mono<JWTCookieUser> deleteUserById(String id);

	Mono<JWTCookieUser> save(JWTCookieUser user);

	Mono<Boolean> existsByName(String pathVariable);

}
