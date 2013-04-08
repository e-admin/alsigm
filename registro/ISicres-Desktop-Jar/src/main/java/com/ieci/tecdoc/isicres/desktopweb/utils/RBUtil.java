package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author LMVICENTE
 * @creationDate 30-abr-2004 16:05:03
 * @version
 * @since
 */

public class RBUtil {

    /********************
     Public attributes
    ********************/

    /********************
     Protected attributes
    ********************/

    /********************
     Private attributes
    ********************/

    private static final String RB_NAME = "resources/ISicres-DesktopWeb";
    
    private ResourceBundle rb = null;
    private static Map resourceBundles = new HashMap(3);

    /********************
     Constructors
    ********************/

    public RBUtil(Locale locale) {
        rb = ResourceBundle.getBundle(RB_NAME, locale);
    }

    /********************
     Public methods
    ********************/

    public static synchronized RBUtil getInstance(Locale locale) {
        RBUtil rbUtil = null;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (resourceBundles.containsKey(locale)) {
            rbUtil = (RBUtil) resourceBundles.get(locale);
        } else {
            rbUtil = new RBUtil(locale);
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

    /********************
     Protected methods
    ********************/

    /********************
     Private methods
    ********************/

    /********************
     Inner classes
    ********************/

    /********************
     Test brench
    ********************/

}
