package es.ieci.tecdoc.fwktd.core.config.business.vo;

import java.util.HashMap;
import java.util.Map;


/**
 * Clase basada en un Map para almacenar las propiedades de configuración generales
 */
public class ConfigurationObjectMapImpl implements ConfigurationObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Map para almacenar los valores de configuración
	 */
	protected Map valores = new HashMap();
	
	

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.config.ConfigurationObject#get(java.lang.String)
	 */
	public Object get(String key){
		if (key != null){
			return valores.get(key);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.config.ConfigurationObject#set(java.lang.String, java.lang.Object)
	 */
	public void set(String key, Object value){
		valores.put (key, value);
	}


	
}
