package es.ieci.tecdoc.fwktd.core.locale.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Clase que setea el locale correspondiente para ser usado  en funcion de un parámetro de la request y lo deja habilitado en la sesion.
 * (jsl y struts)
 *
 */
public class LocaleUtilStrutsJstlImpl extends LocaleUtilAbstractImpl {
	
	public final static String LOCALE_PARAM_NAME ="APP_LOCALE";
	public final static String REQUEST_PARAM_NAME="idioma";
	
	public LocaleUtilStrutsJstlImpl(){
		super();
		//para Struts
		sessionLocaleKeys.add("org.apache.struts.action.LOCALE");
		//para jstl
		sessionLocaleKeys.add("javax.servlet.jsp.jstl.fmt.locale.session");
		sessionLocaleKeys.add(LOCALE_PARAM_NAME);
	}

	public Locale getCurrentLocale(HttpServletRequest request) {
		// Sesión HTTP
		HttpSession session = request.getSession();
		
		// Obtener el locale de sesión
		Locale locale = (Locale) session.getAttribute(LOCALE_PARAM_NAME);
		if (locale == null) {
			
			// Obtener el locale del navegador del cliente
			locale = request.getLocale();
		}

		return locale;
	}

	protected String getLocaleString(HttpServletRequest request) {
		
		String result="";
		result= request.getParameter(REQUEST_PARAM_NAME);
		return result;
	}

	

}
