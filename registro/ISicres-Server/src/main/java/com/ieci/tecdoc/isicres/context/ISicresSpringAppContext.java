package com.ieci.tecdoc.isicres.context;

import org.springframework.context.ApplicationContext;

/**
 * Clase que proporciona acceso al contexto de spring de ISicres
 *
 * @author Iecisa
 *
 */
public class ISicresSpringAppContext {

	private static ApplicationContext ctx;

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		ctx = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
}
