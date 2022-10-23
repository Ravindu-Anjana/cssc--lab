package com.csse.sundayrefactoring.config;

import com.csse.sundayrefactoring.service.XMLInspectService;
import java.util.Properties;


public class PropertyConfigs {

	public static final Properties properties = new Properties();
	public static final String FILE_PATH = "../resources/config.properties";

	static {
		try {
			properties.load(XMLInspectService.class.getResourceAsStream(FILE_PATH));
		} catch (Exception e) {
			
		}
	}
}
