package com.me.webf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
public class WebfluxProjectApplication  {


	public static void main(String[] args) {
		SpringApplication.run(WebfluxProjectApplication.class, args);
	}
	Logger log = LoggerFactory.getLogger(WebfluxProjectApplication.class);
	
	@Bean
	public RouterFunction<ServerResponse> htmlRouter(
	  @Value("classpath:/static/index.html") Resource html) {
	    return RouterFunctions.route(RequestPredicates.GET("/*"), request 
	    		-> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
	    );
	}

}

