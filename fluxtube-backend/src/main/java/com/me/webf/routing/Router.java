package com.me.webf.routing;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router {

	@Bean
	public RouterFunction<ServerResponse> route(RestHandler restHandler) {

		return RouterFunctions.resources("/**", new ClassPathResource("/static/"))
				.andRoute(RequestPredicates.GET("/api/videos/{id}")
						.and(RequestPredicates.queryParam("vd", "")),
						restHandler::findVideoById)
				.andRoute(RequestPredicates.GET("/api/videos/{id}")
						.and(RequestPredicates.queryParam("th", "")),
						restHandler::findThumbnailById)
				.andRoute(RequestPredicates.GET("/api/videos/{id}"), restHandler::findVideoInfoById)
				.andRoute(RequestPredicates.GET("/api/videos"), restHandler::findAllVideoInfos)
				.andRoute(RequestPredicates.POST("/api/videos"), restHandler::saveVideoInfo)
				.andRoute(RequestPredicates.DELETE("/api/videos/{id}"), restHandler::deleteVideoInfoById)
				.andRoute(RequestPredicates.GET("/api/user")
						.and(RequestPredicates.queryParam("ch", (page) -> {
					return true;
				})), restHandler::checkNameAvailability)
				.andRoute(RequestPredicates.POST("/api/user"), restHandler::saveUser)
				.andRoute(RequestPredicates.GET("/api/user")
						.and(RequestPredicates.queryParam("av", (av) -> {
					return true;
				})), restHandler::findAvatarById)
				.andRoute(RequestPredicates.GET("/api/user/{name}"), restHandler::findUserByName)
				.andRoute(RequestPredicates.GET("/api/page")
						.and(RequestPredicates.queryParam("page", (page) -> {
					if (Integer.parseInt(page) >= 0)
						return true;
					else
						return false;
				})).and(RequestPredicates.queryParam("size", (size) -> {
					if (Integer.parseInt(size) > 0)
						return true;
					else
						return false;
				})), restHandler::findPage)
				.andRoute(RequestPredicates.GET("/api/page")
						.and(RequestPredicates.queryParam("size", (size) -> {
					if (Integer.parseInt(size) > 0)
						return true;
					else
						return false;
				})), restHandler::getPageCount);	
	}

	@Bean
	public RouterFunction<ServerResponse> authRoutes(AuthHandler authHandler) {
		return RouterFunctions.route(RequestPredicates.POST("/api/login"), authHandler::login)
				.andRoute(RequestPredicates.GET("/api/logout"), authHandler::logout)
				.andRoute(RequestPredicates.GET("/api/user"), authHandler::checkIfAuthenticated);
	}

}
