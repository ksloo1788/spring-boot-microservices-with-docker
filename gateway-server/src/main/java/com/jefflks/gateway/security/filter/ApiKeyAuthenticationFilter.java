package com.jefflks.gateway.security.filter;

import java.security.SecureRandom;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.jefflks.gateway.constant.PropertiesConstant;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiKeyAuthenticationFilter implements GlobalFilter {

	Logger log = LoggerFactory.getLogger(ApiKeyAuthenticationFilter.class);

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		List<String> apiId = exchange.getRequest().getHeaders().get("x-api-id");
		List<String> apiKey = exchange.getRequest().getHeaders().get("x-api-key");
		log.info("x-api-key" + apiKey);
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String routeId = route != null ? route.getId() : null;

		if (StringUtils.isEmpty(routeId) || CollectionUtils.isEmpty(apiId) || CollectionUtils.isEmpty(apiKey)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid/Missing Input from HTTP Request Header.");
		}else if(!isAuthenticated(apiId.get(0), apiKey.get(0))) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"Authentication Failure for current request.");
		}else if(!isAuthorised(routeId, apiId.get(0))){
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"Unauthorised for current request.");
		}
		return chain.filter(exchange);

	}

	private boolean isAuthenticated(String apiId, String apiKey) {

		Boolean isAuthenticated = false;

		List<String> apiIdStoreList = PropertiesConstant.apiIDList;
		List<String> apiKeyStoreList = PropertiesConstant.apiKeyList;
		if (apiIdStoreList.size() == apiKeyStoreList.size()) {
			for (int i = 0; i < apiIdStoreList.size(); i++) {
				if (apiId.trim().equals(apiIdStoreList.get(i).trim()) && bCryptPasswordEncoder
						.matches(apiIdStoreList.get(i) + "[" + apiKeyStoreList.get(i) + "]", "$2a$10$" + apiKey)) {
					isAuthenticated = true;
					return isAuthenticated;
				}
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Configuration error for API ID and Key.");
		}
		return isAuthenticated;
	}

	private boolean isAuthorised(String routeId, String apiId) {

		Boolean isAuthorised = false;
		log.info(routeId);
		if (!ObjectUtils.isEmpty(routeId) && !ObjectUtils.isEmpty(apiId) && routeId.contains("_")) {

			List<String> subscriptionList = PropertiesConstant.apiSubscriptionMap.get(apiId);
			if (!CollectionUtils.isEmpty(subscriptionList)) {
				String serviceName = routeId.substring(routeId.indexOf("_")+1, routeId.length());
				for (String subsribedService : subscriptionList) {
					if (serviceName.equalsIgnoreCase(subsribedService)) {
						isAuthorised = true;
						return isAuthorised;
					}
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Subscription is not found for API ID ["+apiId+"].");
			}
		}
		return isAuthorised;
	}
}