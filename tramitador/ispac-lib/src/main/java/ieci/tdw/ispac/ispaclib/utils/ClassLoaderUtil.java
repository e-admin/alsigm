package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Utilidad para instanciar clases del classloader.
 *
 */
public class ClassLoaderUtil {
	
	/** Cargador de clases. */
	private static final ClassLoader CLASS_LOADER = 
		ClassLoaderUtil.class.getClassLoader();


	/**
	 * Comprueba si una clase es accesible o no por el classloader.
	 * @param className nombre de la clase.
	 * @return true si la clase es accesible para el classloader; false en caso
	 *         contrario
	 */
	public static boolean findClass(String className) {
		boolean found = false;
		try {
			CLASS_LOADER.loadClass(className);
			found = true;
		} catch (ClassNotFoundException e) {
			found = false;
		}
		return found;
	}

	/**
	 * Devuelve una instancia de la clase cuyo nombre es pasado como parámetro.
	 * @param className nombre de la clase.
	 * @return objeto de la clase; null si no se pudo obtener instancia
	 * @exception ISPACException si el classloader no encuentra la clase
	 */
	public static Object getInstance(String className) throws ISPACException {
		Object result = null;
		try {
			Class clazz = CLASS_LOADER.loadClass(className);
			if (clazz != null) {
				result = clazz.newInstance();
			}
		} catch (Exception e) {
			throw new ISPACException ("ClassLoaderUtil:getInstance(): Clase:"
					+ className +" no encontrada", e);
		}
		return result;
	}

	/**
	 * Devuelve una instancia de la clase cuyo nombre es pasado como parámetro.
	 * @param className nombre de la clase.
	 * @return objeto de la clase; null si no se pudo obtener instancia
	 * @exception ISPACException si el classloader no encuentra la clase
	 */
	public static Object getInstance(Class clazz) throws ISPACException {
		Object result = null;
		try {
			if (clazz != null) {
				result = clazz.newInstance();
			}
		} catch (Exception e) {
			throw new ISPACException ("ClassLoaderUtil:getInstance(): Clase:"
					+ clazz +" no encontrada", e);
		}
		return result;
	}

	/**
	 * Informa de si una clase implementa o no una determinada interfaz.
	 * @param className nombre de la clase
	 * @param interfaceName nombre de la interfaz
	 * @return true si la clase implementa interfaz; false en caso contrario
	 * @exception ISPACException si el classloader no encuentra la clase o no se puede
	 * obtener una instancia de la clase
	 */
	public static boolean implementsInterface(String className, 
			String interfaceName) throws ISPACException {

		Class clazz = null;

		try {
			clazz = CLASS_LOADER.loadClass (className);
		} catch (ClassNotFoundException e) {
			throw new ISPACException (
					"ClassLoaderUtil:classImplementsInterface(): Clase:"
					+ className +" no encontrada", e);
		}

		if (clazz != null) {
			Class[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				if (interfaces[i].getName().equals(interfaceName)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Obtiene un recurso a partir de nombre.
	 * @param resourceName Nombre del recurso.
	 * @return IinputStream del recurso.
	 */
	public static InputStream getResourceAsStream(String resourceName) {
		return CLASS_LOADER.getResourceAsStream(resourceName);
	}


	public static Object executeStaticMethod(String className, String methodName, Object[] params) 
			throws ISPACException {
		
		Object result = null;
		
		try {
			
			if (StringUtils.isNotBlank(className) && StringUtils.isNotBlank(methodName)) {
				
				// Cargar la clase
				Class clazz = Class.forName(className);

				// Obtener el método de la clase
				Method method = clazz.getMethod(methodName, getParamClasses(params));
				result = method.invoke(null, params);
			}
		} 
		catch (Exception e) {
			throw new ISPACException(e);
		}
		
		return result;
	}
	
	public static Class[] getParamClasses(Object[] params) {
		Class[] classes = null;
		
		if (!ArrayUtils.isEmpty(params)) {
			classes = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				classes[i] = params[i].getClass();
			}
		}
		
		return classes;
	}
}
