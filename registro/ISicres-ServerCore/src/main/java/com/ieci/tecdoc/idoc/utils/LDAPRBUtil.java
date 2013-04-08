package com.ieci.tecdoc.idoc.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public class LDAPRBUtil {

    private static final String RB_NAME = "ldap";
    
	private static Logger _logger = Logger.getLogger(LDAPRBUtil.class);
    private ResourceBundle rb = null;
    private static Map resourceBundles = new HashMap(3);


    public LDAPRBUtil(Locale locale) {
        rb = ResourceBundle.getBundle(RB_NAME, locale);
    }

    public static synchronized LDAPRBUtil getInstance(Locale locale) {
        LDAPRBUtil rbUtil = null;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (resourceBundles.containsKey(locale)) {
            rbUtil = (LDAPRBUtil) resourceBundles.get(locale);
        } else {
            rbUtil = new LDAPRBUtil(locale);
            resourceBundles.put(locale, rbUtil);
        }
        return rbUtil;
    }
    
    public String getProperty(String key) {
        return getProperty(key, "@@" + key + "@@");
    }

    public String getProperty(String key, String defaultValue) {
        String result = defaultValue;

        try {
            result = rb.getString(key);
        } catch (MissingResourceException mrE) {
        }

        return result;
    }
}
