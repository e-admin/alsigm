package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.Serializable;

/**
 * Proporciona una implementación base para obtener y cambiar el valor de propiedades
 * siguiendo las directrices de los objetos JavaBean. Se utiliza normalmente para  
 * construir páginas jsp.
 * @see ItemBean
 */
public abstract class ObjectBean implements Serializable
{
	/**
	 * Obtiene el valor de la propiedad identificada por su nombre: keyname.
	 *
	 * @param keyname clave por la que está almacenado el objeto.
	 * @return el objeto mencionado.
	 * @throws ISPACException
	 */

	public abstract Object getProperty(String keyname)
	throws ISPACException;

	/**
	 * Almacena un objeto para la propiedad de nombre keyname
	 *
	 * @param keyname   clave por la que se almacena el objeto.
	 * @param value     objeto a almacenar.
	 * @throws ISPACException
	 */
	public abstract void setProperty(String key, Object value)
	throws ISPACException;
}
