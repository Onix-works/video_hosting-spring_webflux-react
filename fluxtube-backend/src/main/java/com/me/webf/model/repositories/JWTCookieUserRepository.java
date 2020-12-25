package com.me.webf.model.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.me.webf.model.JWTCookieUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface JWTCookieUserRepository extends ReactiveMongoRepository<JWTCookieUser, String> {
	Flux<JWTCookieUser> findAll();

	Mono<JWTCookieUser> findById(String id);
	
	Mono<JWTCookieUser> findByUsername(String id);

	Mono<JWTCookieUser> save(Mono<JWTCookieUser> user);

	Mono<JWTCookieUser> deleteJWTCookieUserById(String id);

	Mono<Boolean> existsByUsername(String name);
}