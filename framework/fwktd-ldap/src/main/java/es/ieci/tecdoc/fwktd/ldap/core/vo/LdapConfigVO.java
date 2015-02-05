package es.ieci.tecdoc.fwktd.ldap.core.vo;

import java.util.HashMap;
import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;

/**
 * Clase con la configuracion base para conectar a LDAP
 * @author Iecisa
 * @version $Revision: 114 $
 *
 */
public abstract class LdapConfigVO {

	/**
	 * Map con los valores de configuracion
	 */
	private Map map;

	/**
	 * Constructor
	 * @param map Map de configuracion
	 */
	public LdapConfigVO (final Map map){
		super();
		this.map=map;
	}

	/**
	 * Contructor por defecto
	 */
	public LdapConfigVO (){
		super();
		this.map=new HashMap();
	}

	/**
	 * Obtiene el map de configuracion
	 * @return map de configuracion
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Permite establecer el map de configuracion
	 * @param map Map de configuracion
	 */
	public void setMap(final Map map) {
		this.map = map;
	}

	/**
	 * Obtiene un valor de configuracion
	 * @param key Clave del valor de configuracion
	 * @return Valor de la clave de configuracion
	 */
	public String getConfigValue(final String key) {
		return (String) map.get(key);
	}

	/**
	 * Permite establecer un valor de configuracion
	 * @param value valor a establecer
	 * @param key clave del valor
	 */
	public void setConfigValue(final Object value,final String key) {
		map.put(value,key);
	}

	/**
	 * Obtiene un valor de configuracion
	 * @param key Clave del valor de configuracion
	 * @return Valor de la clave de configuracion
	 */
	public Object getConfigObject(final String key) {
		return (Object) map.get(key);
	}

	/**
	 * Permite establecer un objeto de configuracion
	 * @param key clave del valor
	 * @param value objeto a establecer
	 */
	public void setConfigObject(final String key, final Object value) {
		map.put(value,key);
	}

	/**
	 * Obtiene un valor string de la configuracion
	 * @param parameterName Clave del parametro
	 * @param defaultValue Valor por defecto
	 * @return valor string de configuracion correspondiente a la clave
	 */
	protected String getStringParameter(final String parameterName, final String defaultValue) {
		String value = getConfigValue(parameterName);
		if (value==null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Obtiene un valor booleano de la configuracion
	 * @param parameterName Clave del parametro
	 * @param defaultValue Valor por defecto
	 * @return valor booleano de configuracion correspondiente a la clave
	 */
	protected boolean getBooleanParameter(final String parameterName, final boolean defaultValue){
		String value = getStringParameter(parameterName, null);

		if ((value==null)||(LdapConstants.EMPTY_PARAMETER.equals(value))){
			return defaultValue;
		}

		return LdapConstants.TRUE_PARAMETER.equals(value);
	}
}
