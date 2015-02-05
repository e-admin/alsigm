package es.ieci.tecdoc.fwktd.core.messages;

import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Clase para obtener mensajes de un bundle específico
 */
public class ApplicationMessages {
    
	public static final String BUNDLE_ERRORS = "es.ieci.tecdoc.fwktd.core.resources.errorcodes";
	public static final String BUNDLE_MESSAGES = "es.ieci.tecdoc.fwktd.core.resources.messagecodes";
	
    /**
     * Locale por defecto
     */
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    /**
     * Map para almacenar los recursos de cada idioma
     */
    private static final HashMap mapResources = new HashMap();

    /**
     * Constructor privado
     */
    protected ApplicationMessages() {
    }
    
    /**
     * Método para devolver una clave que identifique al locale
     * @param bundleName Bundle a utilizar 
     * @param locale Locale del que se quiere obtener la clave única
     * @return clave única del locale
     */
    private static String getLocaleKey(String bundleName, Locale locale){
    	return bundleName+"-"+locale.getCountry()+"-"+locale.getLanguage()+"-"+locale.getVariant();
    }
    
    /**
     * Permite obtener un valor a partir de una clave
     * @param bundleName Recurso a utilizar
     * @param key Clave de la que se quiere obtener el valor
     * @return Cadena asociada a la clave
     */
    public static String getString(String bundleName, String key) {
    	return getString(bundleName, key, null);
    }

    /**
     * Permite obtener un valor a partir de una clave y un locale
     * 
     * @param bundleName Recurso a utilizar
     * @param key Clave de la que se quiere obtener el valor
     * @param locale Locale del que se quiere obtener la clave
     * @return Cadena asociada a la clave
     */
    public static String getString(String bundleName, String key, Locale locale) {
        try {
            String keyMap = getLocaleKey(bundleName, DEFAULT_LOCALE);
            if (locale!=null){
                keyMap = getLocaleKey(bundleName, locale);
            }

            ResourceBundle bundle = (ResourceBundle) mapResources.get(keyMap);
            if (bundle==null){
                try {
                    synchronized(mapResources){
                    	if (locale!=null)
                    		bundle = ResourceBundle.getBundle(bundleName, locale);
                    	else
                    		bundle = ResourceBundle.getBundle(bundleName);
                        mapResources.put(keyMap, bundle);
                    }
                } catch (Exception e) {
                    return key;
                }
            }
            
            String ret = bundle.getString(key);
            
            // Si la cadena está vacía intentar cogerla de los recursos por defecto
            if ((ret == null)|| ("".equals(ret))){
            	bundle = (ResourceBundle) mapResources.get(getLocaleKey(bundleName, DEFAULT_LOCALE));
                if (bundle==null){
                    try {
                        synchronized(mapResources){
                        	bundle = ResourceBundle.getBundle(bundleName);
                            mapResources.put(keyMap, bundle);
                        }
                    } catch (Exception e) {
                        return key;
                    }
                }
                
                ret = bundle.getString(key);
            }
            
            return ret;
            
        } catch (MissingResourceException e) {
            return key;
        }
    }
}
