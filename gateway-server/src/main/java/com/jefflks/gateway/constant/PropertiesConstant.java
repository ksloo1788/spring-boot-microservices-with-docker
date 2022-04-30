package com.jefflks.gateway.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PropertiesConstant {

	Logger log = LoggerFactory.getLogger(PropertiesConstant.class);

	public static List<String> apiIDList;
	public static List<String> apiKeyList;
	public static List<String> apiSubscriptionList;
	public static Map<String, List<String>> apiSubscriptionMap;

	@Value("${spring.application.apiId:}")
	private String apiIdStore;

	@Value("${spring.application.apiKey:}")
	private String apiKeyStore;

	@Value("${spring.application.apiSubscription:}")
	private String apiSubscriptionStore;

	@PostConstruct
	void loadApiAuthenticationAndAuthorisationDetail() {
		log.info("Start to load");
		apiIDList = Arrays.asList(apiIdStore.trim().split(","));
		log.info(apiIDList.toString());
		apiKeyList = Arrays.asList(apiKeyStore.trim().split(","));
		log.info(apiKeyList.toString());
		apiSubscriptionList = Arrays.asList(apiSubscriptionStore.trim().split(","));
		apiSubscriptionMap = new HashMap<String, List<String>>();

		if (!CollectionUtils.isEmpty(apiSubscriptionList)) {
			for (String subsription : apiSubscriptionList) {
				if (!StringUtils.isEmpty(subsription)) {
					String apiID = subsription.substring(0, subsription.indexOf("["));
					String sServiceList = subsription.substring(subsription.indexOf("[") + 1, subsription.length() - 1);
					List<String> serviceList = Arrays.asList(sServiceList.trim().split("&"));
					apiSubscriptionMap.put(apiID, serviceList);
					log.info(apiSubscriptionMap.toString());
				}
			}
		}
		log.info("Done load");
	}

}
