package com.roadm.manager.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SystemMessage {
	private static final String BUNDLE_NAME = "systemConstant";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private SystemMessage() {
	}

	/**
	 * get the value from the properties file
	 * 
	 * @param key
	 *            the key in the properties file
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static Integer getInteger(String key) {
        try {
            String value = RESOURCE_BUNDLE.getString(key);
            return Integer.valueOf(value);
        } catch (MissingResourceException e) {
            return 0;
        }
    }
    
    public static Integer getInteger(String key,int default_alue) {
        try {
            String value = RESOURCE_BUNDLE.getString(key);
            return Integer.valueOf(value);
        } catch (MissingResourceException e) {
            return default_alue;
        }
    }
    
    public static Boolean getBoolean(String key) {
        try {
            String value = RESOURCE_BUNDLE.getString(key);
            return Boolean.valueOf(value);
        } catch (MissingResourceException e) {
            return false;
        }
    }
	
}
