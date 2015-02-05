 /*
 * Created on 14-jul-2004
 *
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 *
 * Interfaz que han de cumplimentar necesariamente todos los objetos de negocio.
 * Por tanto todas las interacciones con el framework tendr&aacute;n el mismo patr&oacute;n de desarrollo.
 * El IItem expone a traves de sus m&eacute;todos el acceso a un objeto gen&eacute;rico compuesto por
 * propiedades, a las cuales se les puede leer y modificar sus valores,
 * y obtener  la descripci&oacute;n de esas propiedades que lo componen.
 *
 * Adem&aacute;s es posible obtener la representaci&oacute;n del objeto como un mapa de nombres de
 * propiedad a sus valores correspondientes  o como texto en formato XML
 */
public interface IItem extends Serializable
{

	/**
	 * Obtiene el valor de la propiedad como un objeto gen&eacute;rico.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el objeto con el valor de la propiedad
	 * @throws ISPACException
	 */
	public Object get(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor de la propiedad clave del IItem como un objeto gen&eacute;rico.
	 * @return el objeto con el valor de la propiedad clave
	 * @throws ISPACException
	 */
	public Object getKey() throws ISPACException;

	/**
	 * Obtiene el valor de la propiedad clave del IItem como Integer.
	 * @return el objeto Integer con el valor de la propiedad clave
	 * @throws ISPACException
	 */
	public Integer getKeyInteger() throws ISPACException;

	/**
	 * Obtiene el valor de la propiedad clave del IItem como Long.
	 * @return el objeto Long con el valor de la propiedad clave
	 * @throws ISPACException
	 */
	public Long getKeyLong() throws ISPACException;

	/**
	 * Obtiene el valor de la propiedad clave del IItem como un entero.
	 * @return el valor entero de la propiedad clave
	 * @throws ISPACException
	 */
	public int getKeyInt() throws ISPACException;
	
	/**
	 * Obtiene el valor de la propiedad clave del IItem como un entero.
	 * @param sProperty la propiedad que es clave
	 * @return el valor entero de la propiedad clave
	 * @throws ISPACException
	 */
	public int getKeyInt(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor de la propiedad  del IItem como un entero.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor entero de la propiedad
	 * @throws ISPACException
	 */
	public int getInt(String sProperty) throws ISPACException;


	/**
	 * Obtiene el valor de la propiedad  del IItem como un entero largo.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor entero largo de la propiedad
	 * @throws ISPACException
	 */
	public long getLong(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor como tipo entero corto de la propiedad del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como entero corto de la propiedad
	 * @throws ISPACException
	 */
	public short getShort(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor como tipo float de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como float de la propiedad
	 * @throws ISPACException
	 */
	public float getFloat(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor como tipo double de la propiedad del IItem.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como double de la propiedad
	 * @throws ISPACException
	 */
	public double getDouble(String sProperty) throws ISPACException;


	/**
	 * Obtiene el valor de la propiedad  del IItem como un objeto BigDecimal.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como BigDecimal de la propiedad
	 * @throws ISPACException
	 */
	public BigDecimal getBigDecimal(String sProperty)
			throws ISPACException;

	/**
	 * Obtiene el valor como tipo byte de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como byte de la propiedad
	 * @throws ISPACException
	 */
	public byte getByte(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor como tipo String de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como String de la propiedad
	 * @throws ISPACException
	 */
	public String getString(String sProperty) throws ISPACException;

	/**
	 * Obtiene el valor como tipo fecha de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como fecha de la propiedad
	 * @throws ISPACException
	 */
	public Date getDate(String sProperty) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el objeto con el valor
	 * @throws ISPACException
	 */
	public void set(String sProperty, Object value) throws ISPACException;

	/**
	 * Cambia el valor de la propiedad clave del IItem
	 * @param value el objeto con el valor
	 * @throws ISPACException
	 */
	public void setKey(Object value) throws ISPACException;

	/**
	 * Cambia el valor de la propiedad clave del IItem
	 * @param value el nuevo valor entero para la propiedad
	 * @throws ISPACException
	 */
	public void setKey(int value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor entero para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, int value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor entero largo para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, long value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor entero corto para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, short value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo float para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, float value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo double para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, double value)	throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo BigDecimal para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, BigDecimal value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo byte para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, byte value) throws ISPACException;

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo cadena para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, String value)	throws ISPACException;


	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo fecha para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, Date value) throws ISPACException;


	/**
	 * Guarda los valores modificados de las propiedades en su origen de datos.
	 *
	 * @param context el contexto del cliente conectado al framework
	 * @throws ISPACException
	 */
	public void store(IClientContext context) throws ISPACException;

	/**
	 * Carga los valores de las propiedades desde el origen de datos para el IItem
	 *
	 * @param context el contexto del cliente conectado al framework
	 * @throws ISPACException
	 */
	public void load(IClientContext context) throws ISPACException;

	/**
	 * Elimina el IItem de su origen de datos.
	 *
	 * @param context el contexto del cliente conectado al framework
	 * @throws ISPACException
	 */
	public void delete(IClientContext context) throws ISPACException;




	/**
	 * Devuelve el objeto Properties con la descripci&oacute;n de todas las propiedades
	 * que contiene este IItem
	 * @return el objeto Properties
	 */
	public Properties getProperties();

	/**
	 * Devuelve la descripci&oacute;n de la propiedad cuyo nombre es name.
	 * @param name nombre de la propiedad
	 * @return la descripci&oacute;n de la propiedad.
	 */
	public Property getProperty(String name);

	/**
	 * El valor de todas las propiedades en una cadena en formato XML completo
	 * @return
	 */
	public String toXml();

	/**
	 * El valor de todas las propiedades en una cadena en formato XML
	 * @return
	 */
	public String getXmlValues();

	/**
	 * Crea y devuelve un mapa con todos los valores de las propiedades.
	 * @return el mapa de nombres de propiedades a sus valores correspondientes
	 */
	public Map toMap();
	
	
	/**
	 * Limpia el contenido de todas la propiedades 
	 */
	public void reset() throws ISPACException;
	
	/**
	 * @param keepFieldId true si queremos que se mantenga el campo clave sin resetear, false en caso contrario
	 * @throws ISPACException
	 */
	public void reset(boolean keepBasicFields)throws ISPACException;
	
	
}