package com.jefflks.gateway.configuration;

import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FallbackConfiguration {

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions
				.route(RequestPredicates.path("/fallback"),
						this::handleFallback);
	}

	public Mono<ServerResponse> handleFallback(ServerRequest request) {
		return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.TEXT_PLAIN)
			      .bodyValue(HttpStatus.SERVICE_UNAVAILABLE.toString());
	}
}
