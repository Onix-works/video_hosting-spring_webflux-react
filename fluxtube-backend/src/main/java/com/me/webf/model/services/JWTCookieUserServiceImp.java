package com.me.webf.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.me.webf.model.JWTCookieUser;
import com.me.webf.model.repositories.JWTCookieUserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JWTCookieUserServiceImp implements JWTCookieUserService {
	
	@Autowired
	private JWTCookieUserRepository userRepository;
	

	@Override
	public Flux<JWTCookieUser> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Mono<JWTCookieUser> findById(String id) {	
		return userRepository.findById(id);
	}

	@Override
	public Mono<JWTCookieUser> findByUsername(String id) {		
		return userRepository.findByUsername(id);
	}

	@Override
	public Mono<JWTCookieUser> save(Mono<JWTCookieUser> user) {		
		return userRepository.save(user);
	}
	
	@Override
	public Mono<JWTCookieUser> save(JWTCookieUser user) {		
		return userRepository.save(user);
	}
	
	@Override
	public Mono<JWTCookieUser> deleteUserById(String id) {		
		return userRepository.deleteJWTCookieUserById(id);
	}

	@Override
	public Mono<Boolean> existsByName(String name) {		
		return userRepository.existsByUsername(name);
	}
}
