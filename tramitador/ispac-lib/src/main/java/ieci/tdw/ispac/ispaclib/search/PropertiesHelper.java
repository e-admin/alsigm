package ieci.tdw.ispac.ispaclib.search;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesHelper {

	Locale locale = null;
	Properties properties = null;
	ResourceBundle resourceBundle = null;
	
    private static final String UNDEFINED_KEY = "???";

	public PropertiesHelper() {
	}

	public PropertiesHelper(Locale locale, Properties properties, ResourceBundle resourceBundle) {
		this.locale = locale;
		this.properties = properties;
		this.resourceBundle = resourceBundle;
	}

	public String getMessage(String key, boolean nullValue){
		String value = null;
		if (locale != null){
			value = properties.getProperty(locale.toString() +"."+ key);
			if (value == null){
				value = properties.getProperty(locale.getLanguage() +"."+ key);
			}
		}
		if (value == null){
			value = properties.getProperty(key);
			if (value == null){
				value = getResource(key);
			}
		}
		
		if (value ==null && !nullValue){
			return UNDEFINED_KEY + key + UNDEFINED_KEY;
		}
		return value;
	}

	public String getMessage(String key){
		return getMessage(key, false);
	}

    private String getResource(String resourceKey){
        String title = null;
        if (resourceBundle != null){
        	try{
        		title = resourceBundle.getString(resourceKey);
        	}catch (MissingResourceException e){
        		title = null;
        	}
        }
        return title;
    }	

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getMessagesResources() {
		return resourceBundle;
	}

	public void setMessagesResources(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}    
}
