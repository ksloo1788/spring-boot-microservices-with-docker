package com.jefflks.gateway.filter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

import com.jefflks.gateway.util.CommonUtils;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiKeyAuthenticationFilter implements GlobalFilter {

	Logger log = LoggerFactory.getLogger(ApiKeyAuthenticationFilter.class);
	
	@Value("${spring.application.apiId:}")
	private String apiIdStore;

	@Value("${spring.application.apiKey:}")
	private String apiKeyStore;

	@Value("${spring.application.apiSubscription:}")
	private String apiSubscriptionStore;
	
	private List<String> apiIDList = new ArrayList<String>();
	
	private List<String> apiKeyList = new ArrayList<String>();
	
	private List<String> apiSubscriptionList = new ArrayList<String>();
	
	private Map<String, List<String>> apiSubscriptionMap = new HashMap<String, List<String>>();
	
	private boolean propertiesLoaded = false;

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		List<String> apiId = exchange.getRequest().getHeaders().get("x-api-id");
		List<String> apiKey = exchange.getRequest().getHeaders().get("x-api-key");
		log.info(CommonUtils.removeLineBreak("x-api-id" + CommonUtils.removeLineBreak(apiId)));
		log.info(CommonUtils.removeLineBreak("x-api-key" + CommonUtils.removeLineBreak(apiKey)));
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String routeId = route != null ? route.getId() : null;

		if (StringUtils.isEmpty(routeId) || CollectionUtils.isEmpty(apiId) || CollectionUtils.isEmpty(apiKey)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid/Missing Input from HTTP Request Header.");
		}else {
			loadApiAuthenticationAndAuthorisationDetail();
			if(!isAuthenticated(apiId.get(0), apiKey.get(0))) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"Authentication Failure for current request.");
			}else if(!isAuthorised(routeId, apiId.get(0))){
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Unauthorised for current request.");
			}
		}
		return chain.filter(exchange);

	}

	void loadApiAuthenticationAndAuthorisationDetail() {
		if(propertiesLoaded) {
			log.info("Properties is already loaded. Skipped for reload.");
		}else {
			log.info("Start to load");
			apiIDList = Arrays.asList(apiIdStore.trim().split(","));		
			apiKeyList = Arrays.asList(apiKeyStore.trim().split(","));		
			apiSubscriptionList = Arrays.asList(apiSubscriptionStore.trim().split(","));
				
			if (!CollectionUtils.isEmpty(apiSubscriptionList)) {
				for (String subsription : apiSubscriptionList) {
					if (!StringUtils.isEmpty(subsription)) {
						String apiID = subsription.substring(0, subsription.indexOf("["));
						String sServiceList = subsription.substring(subsription.indexOf("[") + 1, subsription.length() - 1);
						List<String> serviceList = Arrays.asList(sServiceList.trim().split("&"));
						apiSubscriptionMap.put(apiID, serviceList);
					}
				}
			}
			log.info(CommonUtils.removeLineBreak(apiIDList));
			log.info(CommonUtils.removeLineBreak(apiKeyList));
			log.info(CommonUtils.removeLineBreak(apiSubscriptionList));
			log.info(CommonUtils.removeLineBreak(apiSubscriptionMap));
			propertiesLoaded = true;
			log.info("Done load");
		}
	}
	
	private boolean isAuthenticated(String apiId, String apiKey) {

		Boolean authenticated = false;

		List<String> apiIdStoreList = apiIDList;
		List<String> apiKeyStoreList = apiKeyList;
		if (apiIdStoreList.size() == apiKeyStoreList.size()) {
			for (int i = 0; i < apiIdStoreList.size(); i++) {
				if (apiId.trim().equals(apiIdStoreList.get(i).trim()) && bCryptPasswordEncoder
						.matches(apiIdStoreList.get(i) + "[" + apiKeyStoreList.get(i) + "]", "$2a$10$" + apiKey)) {
					authenticated = true;
					return authenticated;
				}
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Configuration error for API ID and Key.");
		}
		return authenticated;
	}

	private boolean isAuthorised(String routeId, String apiId) {

		Boolean authorised = false;
		log.info(CommonUtils.removeLineBreak(routeId));
		if (!ObjectUtils.isEmpty(routeId) && !ObjectUtils.isEmpty(apiId) && routeId.contains("_")) {
			List<String> subscriptionList = apiSubscriptionMap.get(apiId);
			if (!CollectionUtils.isEmpty(subscriptionList)) {
				String serviceName = routeId.substring(routeId.indexOf("_")+1, routeId.length());
				for (String subsribedService : subscriptionList) {
					if (StringUtils.equalsIgnoreCase(serviceName, subsribedService)) {
						authorised = true;
						return authorised;
					}
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Subscription is not found for API ID ["+apiId+"].");
			}
		}
		return authorised;
	}
}