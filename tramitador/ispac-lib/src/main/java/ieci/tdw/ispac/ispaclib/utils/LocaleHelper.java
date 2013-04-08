package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.ispaclib.common.constants.ScopeConstants;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LocaleHelper {

	/**
	 * Establece el locale aplicable a la aplicación.
	 * @param request Información del request.
	 * @param localeString Cadena con el locale.
	 */
	public static void setLocale(HttpServletRequest request, String localeString) {

		Locale locale = parseLocale(localeString);
		if (locale == null) {
			locale = request.getLocale();
		}
		
		setLocale(request, locale);
	}
	
	/**
	 * Establece el locale aplicable a la aplicación.
	 * @param request Información del request.
	 * @param locale Locale.
	 */
	public static void setLocale(HttpServletRequest request, Locale locale) {
    	
    	// Sesión HTTP
		HttpSession session = request.getSession();
		
		// Establecer el locale en la sesión
		session.setAttribute(ScopeConstants.LOCALE_PARAM_NAME, locale);

		// Establecer el locale para Struts
        session.setAttribute(org.apache.struts.Globals.LOCALE_KEY, locale);

		// Establecer el locale para JSTL
		session.setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", locale);
		
	}
	
    /**
     * Obtiene el locale aplicable a la aplicación. 
     * @param request Información del request.
     * @return Locale.
     */
    public static Locale getLocale(HttpServletRequest request) {
    	
    	// Sesión HTTP
		HttpSession session = request.getSession();
		
		// Obtener el locale de sesión
		Locale locale = (Locale) session.getAttribute(ScopeConstants.LOCALE_PARAM_NAME);
		if (locale == null) {
			
			// Obtener el locale del navegador del cliente
			locale = request.getLocale();
		}

		return locale;
    }
    
    protected static Locale parseLocale(String localeString) {
    	
		Locale locale = null;

		if (StringUtils.isNotBlank(localeString)) {
			
			String language = "";
			String country = "";
			String variant = "";

			int ix = localeString.indexOf("_");
			if (ix > 0) {
				language = localeString.substring(0, ix);

				int ix2 = localeString.indexOf("_", ix + 1);
				if (ix2 > 0) {
					country = localeString.substring(ix + 1, ix2);
					variant = localeString.substring(ix2 + 1);
				} else {
					country = localeString.substring(ix + 1);
				}
			} else {
				language = localeString;
			}

			locale = new Locale(language, country, variant);
		}

		return locale;
	}

}
