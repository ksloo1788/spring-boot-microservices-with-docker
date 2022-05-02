package com.jefflks.gateway.error;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.jefflks.gateway.util.CommonUtils;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalErrorAttributes.class);

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {		
		Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        errorAttributes.put("locale", Locale.getDefault());
        log.info(CommonUtils.removeLineBreak("RequestId: "+errorAttributes.get("requestId").toString() + " Trace: " + errorAttributes.get("trace").toString()));
        errorAttributes.remove("trace");
        return errorAttributes;
	}
}