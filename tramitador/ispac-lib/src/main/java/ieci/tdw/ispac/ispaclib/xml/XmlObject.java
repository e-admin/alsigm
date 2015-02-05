package ieci.tdw.ispac.ispaclib.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACPropertyUnknown;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class XmlObject implements IItem, Serializable, Cloneable {
	
	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(XmlObject.class);
	
	/** Propiedades del ítem. */
	protected Properties properties = null;

	/** Mapa de propiedades del ítem. */
	protected Map propertiesMap = null;

	/** Nombre de la propiedade clave. */
	protected String propKey = null;

	
	/**
	 * Constructor.
	 * @param properties Propiedades del ítem.
	 * @param key Clave del ítem.
	 */
	public XmlObject(Properties properties, String key) {
		this.properties = properties;
		this.propKey = key;
		if (properties != null) {
			this.propertiesMap = properties.toMap();
		} else {
			this.propertiesMap = new HashMap();
		}
			
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @return XML.
	 */
	public String toXml() {
		return new StringBuffer()
			.append(XmlTag.getXmlInstruction("ISO-8859-1"))
			.append(toString())
			.toString();
	}
	
	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public abstract String getXmlValues();

	private Method getMethod(String methodName) {
		
		Method method = null;
		Method [] classMethods = getClass().getMethods();
		
		for (int i = 0; (method == null) && (i < classMethods.length); i++) {
			if (classMethods[i].getName().equalsIgnoreCase(methodName)) {
				method = classMethods[i];
			}
		}
		
		return method;
	}

	/**
	 * Obtiene el valor de la propiedad como un objeto gen&eacute;rico.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el objeto con el valor de la propiedad
	 * @throws ISPACException
	 */
	public Object get(String sProperty) throws ISPACException {
		
		Object value = null;
		
		if ( (sProperty != null) && (sProperty.length() > 0) ) {
			
			try {
				Method method = getMethod("get" + sProperty);
				if (method == null) {
					method = getMethod("is" + sProperty);
					if (method == null) {
						throw new ISPACPropertyUnknown("No existe la propiedad " 
								+ sProperty);
					}
				}

				value = method.invoke(this, null);

			} catch (Exception e) {
				logger.warn("Error al obtener un propiedad", e);
				throw new ISPACPropertyUnknown("No existe la propiedad " 
						+ sProperty);
			}
		}

		return value;
	}

	/**
	 * Obtiene el valor de la propiedad clave del IItem como un objeto gen&eacute;rico.
	 * @return el objeto con el valor de la propiedad clave
	 * @throws ISPACException
	 */
	public Object getKey() throws ISPACException {
		return get(propKey);
	}

	/**
	 * Obtiene el valor de la propiedad clave del IItem como Integer.
	 * @return el objeto Integer con el valor de la propiedad clave
	 * @throws ISPACException
	 */
	public Integer getKeyInteger() throws ISPACException {
		Number value = (Number) getKey();
		return new Integer(value.intValue());
	}

	/**
	 * Obtiene el valor de la propiedad clave del IItem como Long.
	 * @return el objeto Long con el valor de la propiedad clave
	 * @throws ISPACException
	 */
	public Long getKeyLong() throws ISPACException {
		Number value = (Number) getKey();
		return new Long(value.longValue());
	}

	/**
	 * Obtiene el valor de la propiedad clave del IItem como un entero.
	 * @return el valor entero de la propiedad clave
	 * @throws ISPACException
	 */
	public int getKeyInt() throws ISPACException {
		Number value = (Number) getKey();
		return value.intValue();
	}
	
	/**
	 * Obtiene el valor de la propiedad clave del IItem como un entero.
	 * @param sProperty la propiedad que es clave
	 * @return el valor entero de la propiedad clave
	 * @throws ISPACException
	 */
	public int getKeyInt(String sProperty) throws ISPACException {
		Number value = (Number) get(sProperty);
		return value.intValue();
	}

	/**
	 * Obtiene el valor de la propiedad  del IItem como un entero.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor entero de la propiedad
	 * @throws ISPACException
	 */
	public int getInt(String sProperty) throws ISPACException {
		Number value = (Number)get(sProperty);
		return value.intValue();
	}


	/**
	 * Obtiene el valor de la propiedad  del IItem como un entero largo.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor entero largo de la propiedad
	 * @throws ISPACException
	 */
	public long getLong(String sProperty) throws ISPACException {
		Number value = (Number)get(sProperty);
		return value.longValue();
	}

	/**
	 * Obtiene el valor como tipo entero corto de la propiedad del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como entero corto de la propiedad
	 * @throws ISPACException
	 */
	public short getShort(String sProperty) throws ISPACException {
		Number value = (Number)get(sProperty);
		return value.shortValue();
	}

	/**
	 * Obtiene el valor como tipo float de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como float de la propiedad
	 * @throws ISPACException
	 */
	public float getFloat(String sProperty) throws ISPACException {
		Number value = (Number)get(sProperty);
		return value.floatValue();
	}

	/**
	 * Obtiene el valor como tipo double de la propiedad del IItem.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como double de la propiedad
	 * @throws ISPACException
	 */
	public double getDouble(String sProperty) throws ISPACException {
		Number value = (Number)get(sProperty);
		return value.doubleValue();
	}


	/**
	 * Obtiene el valor de la propiedad  del IItem como un objeto BigDecimal.
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como BigDecimal de la propiedad
	 * @throws ISPACException
	 */
	public BigDecimal getBigDecimal(String sProperty) throws ISPACException {
		return (BigDecimal) get(sProperty);
	}

	/**
	 * Obtiene el valor como tipo byte de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como byte de la propiedad
	 * @throws ISPACException
	 */
	public byte getByte(String sProperty) throws ISPACException {
		Number value = (Number)get(sProperty);
		return value.byteValue();
	}

	/**
	 * Obtiene el valor como tipo String de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como String de la propiedad
	 * @throws ISPACException
	 */
	public String getString(String sProperty) throws ISPACException {
		Property prop=(Property)propertiesMap.get(sProperty);
		if (prop==null)
			throw new ISPACException("No existe la propiedad "+sProperty);
		return TypeConverter.getString(prop.getType(),get(sProperty));
	}

	/**
	 * Obtiene el valor como tipo fecha de la propiedad  del IItem .
	 * @param sProperty la propiedad cuyo valor se solicita
	 * @return el valor como fecha de la propiedad
	 * @throws ISPACException
	 */
	public Date getDate(String sProperty) throws ISPACException {
		return (Date) get(sProperty);
	}


	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el objeto con el valor
	 * @throws ISPACException
	 */
	public void set(String sProperty, Object value) throws ISPACException {
		if ( (sProperty != null) && (sProperty.length() > 0) ) {
			
			try {
				Method method = getMethod("set" + sProperty);
				if (method == null) {
					throw new ISPACPropertyUnknown("No existe la propiedad " 
							+ sProperty);
				}

				value = method.invoke(this, new Object[] { value });

			} catch (Exception e) {
				logger.warn("Error al obtener un propiedad", e);
				throw new ISPACPropertyUnknown("No existe la propiedad " 
						+ sProperty);
			}
		}
	}

	/**
	 * Cambia el valor de la propiedad clave del IItem
	 * @param value el objeto con el valor
	 * @throws ISPACException
	 */
	public void setKey(Object value) throws ISPACException {
		set(propKey, value);
	}

	/**
	 * Cambia el valor de la propiedad clave del IItem
	 * @param value el nuevo valor entero para la propiedad
	 * @throws ISPACException
	 */
	public void setKey(int value) throws ISPACException {
		setKey(new Integer(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor entero para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, int value) throws ISPACException {
		set(sProperty, new Integer(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor entero largo para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, long value) throws ISPACException {
		set(sProperty, new Long(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor entero corto para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, short value) throws ISPACException {
		set(sProperty, new Short(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo float para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, float value) throws ISPACException {
		set(sProperty, new Float(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo double para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, double value)	throws ISPACException {
		set(sProperty, new Double(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo BigDecimal para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, BigDecimal value) throws ISPACException {
		set(sProperty, (Object) value);
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo byte para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, byte value) throws ISPACException {
		set(sProperty, new Byte(value));
	}

	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo cadena para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, String value)	throws ISPACException {
		set(sProperty, (Object) value);
	}


	/**
	 * Cambia el valor de una propiedad
	 * @param sProperty  la propiedad cuyo valor se quiere cambiar
	 * @param value el nuevo valor de tipo fecha para la propiedad
	 * @throws ISPACException
	 */
	public void set(String sProperty, Date value) throws ISPACException {
		set(sProperty, (Object) value);
	}

	/**
	 * Guarda los valores modificados de las propiedades en su origen de datos.
	 *
	 * @param context el contexto del cliente conectado al framework
	 * @throws ISPACException
	 */
	public void store(IClientContext context) throws ISPACException {}

	/**
	 * Carga los valores de las propiedades desde el origen de datos para el IItem
	 *
	 * @param context el contexto del cliente conectado al framework
	 * @throws ISPACException
	 */
	public void load(IClientContext context) throws ISPACException {}

	/**
	 * Elimina el IItem de su origen de datos.
	 *
	 * @param context el contexto del cliente conectado al framework
	 * @throws ISPACException
	 */
	public void delete(IClientContext context) throws ISPACException {}

	/**
	 * Devuelve el objeto Properties con la descripci&oacute;n de todas las propiedades
	 * que contiene este IItem
	 * @return el objeto Properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Devuelve la descripci&oacute;n de la propiedad cuyo nombre es name.
	 * @param name nombre de la propiedad
	 * @return la descripci&oacute;n de la propiedad.
	 */
	public Property getProperty(String name) {
		return (Property)propertiesMap.get(name);
	}

	/**
	 * Crea y devuelve un mapa con todos los valores de las propiedades.
	 * @return el mapa de nombres de propiedades a sus valores correspondientes
	 */
	public Map toMap() {
		return null;
	}

	public void reset()throws ISPACException {
		
	}

	public void reset(boolean keepFieldId)throws ISPACException{
	}
	
}
