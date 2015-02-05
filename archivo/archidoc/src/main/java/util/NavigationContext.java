/*
 * Created on 15-feb-2005
 *
 */
package util;

import javax.servlet.http.HttpSession;

/**
 * @author LUISANVE
 * 
 */
public class NavigationContext {
	protected final static String NAVIGATION_CONTEX_BIND_NAME = "NAVIGATION_CONTEXT";

	HttpSession sesionUsuario = null;
	String onCancelURI = null;

	protected NavigationContext(HttpSession sesion) {
		// this.sesionUsuario = sesion;
	}
	/*
	 * public void setOnCancelURI(String URIPath, ParClaveValor[] parametros) {
	 * StringBuffer uri = new StringBuffer(); uri.append("/action");
	 * uri.append(URIPath); if (parametros != null && parametros.length > 0) {
	 * uri.append("?"); int ordenParametro = 0; for
	 * (;ordenParametro<parametros.length-1;ordenParametro++)
	 * uri.append(parametros
	 * [ordenParametro].clave).append("=").append(parametros
	 * [ordenParametro].valor).append("&");
	 * uri.append(parametros[ordenParametro]
	 * .clave).append("=").append(parametros[ordenParametro].valor); }
	 * this.onCancelURI = uri.toString(); }
	 * 
	 * public String getOnCancelURI() { return this.onCancelURI; }
	 * 
	 * public static NavigationContext getNavigationContext(HttpSession sesion)
	 * { NavigationContext contexto =
	 * (NavigationContext)sesion.getAttribute(NAVIGATION_CONTEX_BIND_NAME); if
	 * (contexto == null) { contexto = new NavigationContext(sesion);
	 * sesion.setAttribute(NAVIGATION_CONTEX_BIND_NAME, contexto); } return
	 * contexto; }
	 */
}
