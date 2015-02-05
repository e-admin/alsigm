package es.ieci.tecdoc.fwktd.core.spring.context;

import org.springframework.context.ApplicationContext;

/**
 * Clase que encapsula el contexto de aplicación de Spring.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AppContext {

	/**
	 * Contexto de aplicación de Spring.
	 */
	private static ApplicationContext ctx;

	/**
	 * Establece el contexto de aplicación de Spring.
	 *
	 * @param applicationContext
	 *            Contexto de aplicación.
	 */
	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		ctx = applicationContext;
	}

	/**
	 * Obtiene el contexto de aplicación de Spring.
	 *
	 * @return Contexto de aplicación.
	 */
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	/**
	 * Obtiene un bean del contexto.
	 * @param name Nombre del bean.
	 * @return Bean.
	 */
	public static Object getBean(String name) {
		if (ctx == null) {
			return null;
		}

		return ctx.getBean(name);
	}

	/**
	 * Obtiene un bean del contexto.
	 * @param name Nombre del bean.
	 * @param args Argumentos para el bean.
	 * @return Bean.
	 */
	public static Object getBean(String name, Object... args) {
		if (ctx == null) {
			return null;
		}

		return ctx.getBean(name, args);
	}
}
