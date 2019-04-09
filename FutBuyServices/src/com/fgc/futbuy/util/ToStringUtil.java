package com.fgc.futbuy.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class ToStringUtil {
	
	public static String toString(Object o) {
		return ToStringBuilder.reflectionToString(o);
	}
}
