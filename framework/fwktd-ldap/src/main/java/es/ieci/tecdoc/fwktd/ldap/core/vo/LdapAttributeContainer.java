package es.ieci.tecdoc.fwktd.ldap.core.vo;

import java.util.Set;

/**
 * Contenedor de atributos a retornar para búsquedas
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface LdapAttributeContainer {

	/**
	 * Permite obtener un atributo
	 * @param key Clave del atributo
	 * @return Valor del atributo
	 */
	Object get(Object key);

	/**
	 * Devuelve si el contenedor esta vacio
	 * @return si el contenedor esta vacio
	 */
	boolean isEmpty();

	/**
	 * Devuelve un set con las claves
	 * @return set con las claves
	 */
	Set keySet();

	/**
	 * Establece el valor de un atributo
	 * @param key Clave del atributo
	 * @param value Valor del atributo
	 */
	void put(Object key, Object value);

	/**
	 * Devuelve el tamanio del contenedor
	 * @return tamanio del contenedor
	 */
	int size();

}