package com.spring.util;

public class StringHelper {
	public static boolean isEmpty(Object param) {
		if (null == param || "".equals(param.toString().trim()) || "null".equalsIgnoreCase(param.toString().trim()))
			return true;
		return false;
	}
}