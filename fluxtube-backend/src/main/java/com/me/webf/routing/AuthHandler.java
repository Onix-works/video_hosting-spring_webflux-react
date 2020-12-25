package com.me.webf.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.me.webf.misc.AuthRequest;
import com.me.webf.model.services.JWTCookieUserService;
import com.me.webf.security.JWTUtil;

import reactor.core.publisher.Mono;


@Component
public class AuthHandler {

    private JWTCookieUserService userSerice;
    private PasswordEncoder passwordEncoder;
    private JWTUtil jwtUtil;
    
    @Autowired
    public AuthHandler(JWTCookieUserService userSerice, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userSerice = userSerice;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
    	 return serverRequest.body(BodyExtractors.toMono(AuthRequest.class))
                 .flatMap(authRequest  -> {
                	 return userSerice.findByUsername(authRequest.getUsername())
                			 .flatMap(userDetails -> {
                                 String incomePass = authRequest.getPassword();
                                 String userPass = userDetails.getPassword();
                                 if (passwordEncoder.matches(incomePass, userPass)) {
                                     return ServerResponse
                                             .ok()
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .cookie(ResponseCookie.from("token", jwtUtil.generateToken(userDetails))
                                            		 .httpOnly(true)
                                            		 .secure(true)
                                            		 .sameSite("Lax")
                                            		 .maxAge(3600)
                                            		 .build())
                                             .build();                  
                                 } else {
                                     return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                    		 .build();
                                 }
                             }).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
                 });
    }
    
    public Mono<ServerResponse> logout(ServerRequest serverRequest) {
                	return ServerResponse
                			.ok()
                            .cookie(ResponseCookie.from("token", "")
                           		 .httpOnly(true)
                           		 .secure(true)
                           		 .sameSite("Lax")
                           		 .maxAge(0)
                           		 .build())
                            .body(Mono.just("true"), String.class);           
    }
    
    public Mono<ServerResponse> checkIfAuthenticated(ServerRequest serverRequest) {
    	return ServerResponse
    			.ok()
    			.body(ReactiveSecurityContextHolder.getContext()
    					.map(SecurityContext::getAuthentication)
    					.map(Authentication::getPrincipal), Authentication.class);
    }
}
