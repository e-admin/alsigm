package es.ieci.tecdoc.fwktd.core.locale.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public interface LocaleUtil {
	/**
	 * Método que establece el locale a usar a  partir de la request 
	 * @param request
	 */
	public void setLocale(HttpServletRequest request);
	/**
	 * Método que estable el locale a usar a partir del locale suministrado
	 * @param request
	 * @param locale
	 */
	public void setLocale(HttpServletRequest request, Locale locale);
	
	/**
	 * Método que obtiene el el locale que está establecido para usarse 
	 * @param request
	 * @return
	 */
	public Locale getLocale(HttpServletRequest request );

}
