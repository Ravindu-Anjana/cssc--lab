package com.csse.sundayrefactoring.config;

import com.csse.sundayrefactoring.service.XMLInspectService;

import java.util.Properties;


public class PropertyConfigs {

	public static final Properties properties = new Properties();

	static {
		try {
			properties.load(XMLInspectService.class.getResourceAsStream("../resources/config.properties"));
		} catch (Exception e) {
			
		}
	}
}
