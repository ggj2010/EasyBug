package com.framework.constant;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;

public class ConfigXML {
	
	public final static String URL = "config/config.xml";
	private static CompositeConfiguration config;
	
	public static String getParam(String configName) {
		if (config == null) {
			initUserLog();
		}
		return config.getString(configName);
	}
	
	private static void initUserLog() {
		config = new CompositeConfiguration();
		try {
			config.addConfiguration(new org.apache.commons.configuration.XMLConfiguration(URL));
		}
		catch (ConfigurationException e) {
			config = null;
		}
	}
}
