package com.jefflks.gateway.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.lang.Nullable;

public class CommonUtils {

	public static String removeLineBreak(@Nullable String inputString) {
		if(inputString == null) {
			return "null";
		}else {
			return inputString.replaceAll("[\r\n]","");
		}
	}
	
	
	public static String removeLineBreak(@Nullable Collection<?> inputCollection) {
		if(inputCollection == null) {
			return "null";
		}else {
			return inputCollection.toString().replaceAll("[\r\n]","");
		}
	}
	
	public static String removeLineBreak(@Nullable Map<?, ?> inputMap) {
		if(inputMap == null) {
			return "null";
		}else {
			return inputMap.toString().replaceAll("[\r\n]","");
		}
	}
	
}
