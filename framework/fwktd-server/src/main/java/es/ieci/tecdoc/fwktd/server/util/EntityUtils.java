/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import es.ieci.tecdoc.fwktd.util.reflection.ReflectionUtils;

/**
 * Clase de utilidad para acceso a datos con Ibatis.
 * 
 * @author IECISA
 * 
 */
public class EntityUtils {

	/**
	 * Devuelve el nombre del campo que hace de clave primaria en el objeto o.
	 * Get primary key field name from object. Busca un campo que se llame "id",
	 * uno que contenga "Id" o un campo llamado "version".
	 * 
	 * @param o
	 *            el objeto a examinar
	 * @return el nombre del campo que hace de clave primaria
	 */
	public static String getPrimaryKeyFieldName(Object o) {
		Field[] fieldlist = ReflectionUtils.getAllDeclaredFields(o.getClass());
		String fieldName = null;
		for (Field fld : fieldlist) {
			if (fld.getName().equalsIgnoreCase("id")) {
				fieldName = fld.getName();
				break;
			}
		}
		return fieldName;
	}

	/**
	 * Devuelve el tipo de la clase de la clave primaria del objeto.
	 * 
	 * @param o
	 *            el objeto a examinar
	 * @return la clase de la clave primaria del objeto
	 */
	public static Class<?> getPrimaryKeyFieldType(Object o) {
		Field[] fieldlist = ReflectionUtils.getAllDeclaredFields(o.getClass());
		Class<?> fieldType = null;
		for (Field fld : fieldlist) {
			if (fld.getName().equalsIgnoreCase("id")) {
				fieldType = fld.getType();
				break;
			}
		}
		return fieldType;
	}

	/**
	 * Devuelve el valor de la clave primaria del objeto usando reflectividad.
	 * 
	 * @param o
	 *            el objeto a examinar
	 * @return el valor de la clave primaria del objeto como un Object
	 */
	public static Object getPrimaryKeyValue(Object o) {
		// Usamos Java Reflection para encontrar la primera propiedad que tiene
		// el nombre "id" o "Id"
		String fieldName = getPrimaryKeyFieldName(o);
		String getterMethod = "get"
				+ Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);

		try {
			Method getMethod = o.getClass().getMethod(getterMethod,
					(Class[]) null);
			return getMethod.invoke(o, (Object[]) null);
		} catch (Exception e) {
			logger.error("No se ha podido invocar al método '" + getterMethod
					+ "' en la clase " + ClassUtils.getShortName(o.getClass()),
					e);
		}
		return null;
	}

	/**
	 * Prepara el objeto o para guardar o actualizar buscando un campo "version"
	 * e incrementando su valor si existe. Una implementación alternativa sería
	 * buscar una anotación @Version.
	 * 
	 * @param o
	 *            el objeto a examinar
	 */
	public static void prepareObjectForSaveOrUpdate(Object o) {
		try {
			Field[] fieldlist = ReflectionUtils.getAllDeclaredFields(o
					.getClass());
			for (Field fld : fieldlist) {
				String fieldName = fld.getName();
				if (fieldName.equals("version")) {
					Method setMethod = o.getClass().getMethod("setVersion",
							Integer.class);
					Object value = o.getClass().getMethod("getVersion",
							(Class[]) null).invoke(o, (Object[]) null);
					if (value == null) {
						setMethod.invoke(o, 1);
					} else {
						setMethod.invoke(o, (Integer) value + 1);
					}
				}
			}
		} catch (Exception e) {
			logger.error("No se ha podido preparar '"
					+ ClassUtils.getShortName(o.getClass())
					+ "' para insercion/actualizacion", e);
		}
	}

	/**
	 * Establece el valor de la clave primaria del objeto
	 * 
	 * @param o
	 *            el objeto a examinar
	 * @param clazz
	 *            el tipo de la clave primariathe class type of the primary key
	 * @param value
	 *            el valor de la nueva clave primaria
	 */
	public static void setPrimaryKey(Object o, Class<?> clazz, Object value) {
		String fieldName = getPrimaryKeyFieldName(o);
		String setMethodName = "set"
				+ Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);

		try {
			Method setMethod = o.getClass().getMethod(setMethodName, clazz);
			if (value != null) {
				setMethod.invoke(o, value);
			}
		} catch (Exception e) {
			logger
					.error(
							MessageFormat
									.format(
											"No se ha podido establecer ''{0}.{1} con el valor {2}",
											ClassUtils.getShortName(o
													.getClass()), fieldName,
											value), e);
		}
	}

	/**
	 * 
	 * Checkstyle rule: las clases de utilidad no deben tener constructor
	 * público.
	 */
	private EntityUtils() {
	}

	// Members
	protected static final Logger logger = LoggerFactory
			.getLogger(EntityUtils.class);

}
