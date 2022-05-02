package com.jefflks.gateway.error;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

	public GlobalExceptionHandler(GlobalErrorAttributes gea, ApplicationContext applicationContext,
			ServerCodecConfigurer serverCodecConfigurer) {
		super(gea, new WebProperties.Resources(), applicationContext);
		super.setMessageWriters(serverCodecConfigurer.getWriters());
		super.setMessageReaders(serverCodecConfigurer.getReaders());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {

		final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
		String sErrorCode = String.valueOf(errorPropertiesMap.get("status"));
		int errCode = 500 ;
		if(!StringUtils.isEmpty(sErrorCode)) {
			errCode = StringUtils.isNumeric(sErrorCode) ? Integer.valueOf(500) : Integer.valueOf(sErrorCode);
		}
		return ServerResponse.status(HttpStatus.valueOf(errCode)).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(errorPropertiesMap));
	}
}