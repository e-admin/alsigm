package es.ieci.tecdoc.fwktd.ldap.core.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clase para almacenar los atributos a obtener de entradas Ldap.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class LdapAttributeContainerVO implements LdapAttributeContainer {

	/**
	 * Valores de configuracion
	 */
	private Map configMap = new HashMap();

	/**
	 * @return el configMap
	 */
	public Map getConfigMap() {
		return configMap;
	}

	/**
	 * @param configMap el configMap a fijar
	 */
	public void setConfigMap(Map configMap) {
		this.configMap = configMap;
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public Object get(Object key) {
		return configMap.get(key);
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.HashMap#isEmpty()
	 */
	public boolean isEmpty() {
		return configMap.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.HashMap#keySet()
	 */
	public Set keySet() {
		return configMap.keySet();
	}


	/**
	 * {@inheritDoc}
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Object key, Object value) {
		configMap.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.HashMap#size()
	 */
	public int size() {
		return configMap.size();
	}

}
