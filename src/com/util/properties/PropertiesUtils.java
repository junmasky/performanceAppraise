package com.util.properties;

import java.io.IOException;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.util.log.Log;
import com.util.string.StringUtil;

public class PropertiesUtils {
	private static Log log=new Log(PropertiesUtils.class);
	
	public static String getProperty(String key,String filePath){
		String value = "";
		try {
			value = PropertiesLoaderUtils.loadAllProperties(filePath).getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return StringUtil.showNull(value);
	}
}
