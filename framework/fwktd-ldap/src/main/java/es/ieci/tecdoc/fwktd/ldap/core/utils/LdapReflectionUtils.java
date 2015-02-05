package es.ieci.tecdoc.fwktd.ldap.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.naming.ldap.Control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import es.ieci.tecdoc.fwktd.ldap.core.exception.LdapException;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;

/**
 * Clase de utilidad para reflectividad
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public final class LdapReflectionUtils {

	/**
	 * Logger para la clase
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LdapReflectionUtils.class);

	/**
	 * Constructor privado
	 */
	private LdapReflectionUtils(){

	}

	/**
	 * Permite crear una instancia de la clase pasada como parametro.
	 * @param clazz Clase de la que se desea crear una instancia
	 * @param paramTypes Tipos de parametros
	 * @param params Valores de los parametros
	 * @return Instancia de la clase
	 * @throws LdapException
	 */
	private static Control createRequestControl(final Class clazz, final Class[] paramTypes, final Object[] params){
		Constructor constructor = ClassUtils.getConstructorIfAvailable(clazz, paramTypes);
		if (constructor == null) {
			LdapExceptionUtils.generateErrorException(
							LdapErrorCodes.ERR_10005_CONTROL_CONTRUCTOR_NOT_FOUND,
							new String[] {clazz.toString(), StringUtils.arrayToCommaDelimitedString(paramTypes) },
							LOGGER);
		}

		Control result = null;
		try {
			result = (Control) constructor.newInstance(params);
		}
		catch (Exception e) {
			LdapExceptionUtils.generateErrorException(
					LdapErrorCodes.ERR_10006_CONTROL_INSTANCE_FAILED,
					new String [] {clazz.toString(), StringUtils.arrayToCommaDelimitedString(paramTypes), StringUtils.arrayToCommaDelimitedString(params)},
					LOGGER, e);
		}

		return result;
	}

	/**
	 * Permite obtener un metodo de una clase
	 * @param clazz Clase de la que se quiere obtener el metodo
	 * @param classMethod Metodo de la clase
	 * @param paramTypes Tipos de parametros del metodo
	 * @return Metodo de la clase
	 * @throws LdapException
	 */
	public static Method getClassMethod(final Class clazz, final String classMethod, final Class[] paramTypes){
		Method method = ClassUtils.getMethodIfAvailable(clazz, classMethod, paramTypes);
		if (method == null) {
			LdapExceptionUtils.generateErrorException(
					LdapErrorCodes.ERR_10007_CLASS_METHOD_NOT_FOUND,
					new String [] {classMethod, clazz.toString(), StringUtils.arrayToCommaDelimitedString(paramTypes)},
					LOGGER);

		}

		return method;
	}


	/**
	 * Permite crear instancias de controles de resultados paginados para jdk 1.4 y 1.5
	 * @param clazz Clase de resultados paginados
	 * @param pageSize tamanio de pagina
	 * @param critical criticidad del control
	 * @return Instancia del control de resultados paginados
	 * @throws LdapException
	 */
	public static Control createPagedResultsRequestControl(final Class clazz, final int pageSize, final boolean critical){
		Control control = null;

		try {
			control = createRequestControl(clazz, new Class[] { int.class, boolean.class },
					new Object[] {new Integer(pageSize), Boolean.valueOf(critical)});
		} catch (LdapException e) {
			LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10001_PAGED_RESULTS_CONTROL_CREATION, LOGGER);
		}

		if (control == null){
			control = createRequestControl(clazz, new Class[] { int.class },
					new Object[] {new Integer(pageSize)});
		}

		if (control == null){
			LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10001_PAGED_RESULTS_CONTROL_CREATION, LOGGER);
		}

		return control;
	}

	/**
	 * Permite crear instancias de controles de resultados paginados para jdk 1.4 y 1.5
	 * @param clazz Clase de resultados paginados
	 * @param pageSize tamanio de pagina
	 * @param cookie cookie de la busqueda anterior
	 * @param critical criticidad del control
	 * @return Instancia del control de resultados paginados
	 * @throws LdapException
	 */
	public static Control createPagedResultsRequestControl(final Class clazz, final int pageSize, final byte[] cookie, final boolean critical){
		return createRequestControl(clazz, new Class[] { int.class, byte[].class, boolean.class },
				new Object[] {new Integer(pageSize), cookie, Boolean.valueOf(critical)});
	}
}
