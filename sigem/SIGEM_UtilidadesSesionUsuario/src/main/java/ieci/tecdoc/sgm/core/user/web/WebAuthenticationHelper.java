package ieci.tecdoc.sgm.core.user.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class WebAuthenticationHelper {

	public static final String KEY_AUTH_URL 	=	"ieci.tecdoc.sgm.autenticacion.web.authWeb.url";
	public static final String KEY_AUTH_PROTOCOL=	"ieci.tecdoc.sgm.autenticacion.web.authWeb.protocol";
	public static final String KEY_AUTH_PORT 	=	"ieci.tecdoc.sgm.autenticacion.web.authWeb.port";

	public static final String KEY_AUTH_DISCONNECT_URL 	=		"ieci.tecdoc.sgm.autenticacion.web.authWeb.disconect.url";
	public static final String KEY_AUTH_DISCONNECT_PROTOCOL=	"ieci.tecdoc.sgm.autenticacion.web.authWeb.disconect.protocol";
	public static final String KEY_AUTH_DISCONNECT_PORT 	=	"ieci.tecdoc.sgm.autenticacion.web.authWeb.disconect.port";

	/**
	 * Instancia del logger
	 */
	private static final Logger logger = Logger.getLogger(WebAuthenticationHelper.class);

	private static final String PROPERTIES_FILE_NAME = "Authentication.properties";
	private static Properties propiedades;


	static{
		propiedades = new Properties();
		try {
			propiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			logger.fatal("Error inicilizando propiedades de autenticación Web.", e);
		}
	}

	public static String getProperty(String pcName){
		return propiedades.getProperty(pcName);
	}

	public static String getWebAuthURL(HttpServletRequest request, String aplicacion){
		//StringBuffer sbUrl = new StringBuffer(getProperty(KEY_AUTH_PROTOCOL));
		//sbUrl.append(request.getServerName()).append(getProperty(KEY_AUTH_PORT)).append(getProperty(KEY_AUTH_URL))
		//		.append(aplicacion);

		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(getProperty(KEY_AUTH_URL)).append(aplicacion);

		String entidadId = (String)request.getParameter(ConstantesSesionUser.ID_ENTIDAD);
		if(SesionUserHelper.isNuloOVacio(entidadId)){
			entidadId = (String)request.getAttribute(ConstantesSesionUser.ID_ENTIDAD);
		}
		if(!SesionUserHelper.isNuloOVacio(entidadId)) {
			sbUrl.append('&').append(ConstantesSesionUser.ID_ENTIDAD).append("=").append(entidadId);
		}

		String tramiteId = (String)request.getParameter(ConstantesSesionUser.TRAMITE_ID);
		if(SesionUserHelper.isNuloOVacio(tramiteId)){
			tramiteId = (String)request.getAttribute(ConstantesSesionUser.TRAMITE_ID);
		}
		if(!SesionUserHelper.isNuloOVacio(tramiteId)) {
			sbUrl.append('&').append(ConstantesSesionUser.TRAMITE_ID).append("=").append(tramiteId);
		}

		String lang = (String)request.getParameter(ConstantesSesionUser.LANG);
		if(SesionUserHelper.isNuloOVacio(lang)){
			lang = (String)request.getAttribute(ConstantesSesionUser.LANG);
		}
		String country = (String)request.getParameter(ConstantesSesionUser.COUNTRY);
		if(SesionUserHelper.isNuloOVacio(country)){
			country = (String)request.getAttribute(ConstantesSesionUser.COUNTRY);
		}

		String idioma = (String)request.getParameter(ConstantesSesionUser.IDIOMA);
		if(SesionUserHelper.isNuloOVacio(idioma)){
			idioma = (String)request.getAttribute(ConstantesSesionUser.IDIOMA);
		}

		if(!SesionUserHelper.isNuloOVacio(idioma)) {
			int index = idioma.indexOf("_");
			lang = idioma.substring(0, index);
			country = idioma.substring(index+1, idioma.length());
		}

		if(!SesionUserHelper.isNuloOVacio(lang)) {
			sbUrl.append('&').append(ConstantesSesionUser.LANG).append("=").append(lang);
		}

		if(!SesionUserHelper.isNuloOVacio(country)) {
			sbUrl.append('&').append(ConstantesSesionUser.COUNTRY).append("=").append(country);
		}

		return sbUrl.toString();
	}

	public static String getWebAuthDesconectURL(HttpServletRequest request){
		//StringBuffer sbUrl = new StringBuffer(getProperty(KEY_AUTH_DISCONNECT_PROTOCOL));
		//sbUrl.append(request.getServerName()).append(getProperty(KEY_AUTH_DISCONNECT_PORT)).append(getProperty(KEY_AUTH_DISCONNECT_URL));

		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(getProperty(KEY_AUTH_DISCONNECT_URL));

		String sessionId = (String)request.getSession().getAttribute(ConstantesSesionUser.ID_SESION);
		sbUrl.append(sessionId);
		
		String entidadId = (String)request.getSession().getAttribute(ConstantesSesionUser.ID_ENTIDAD);
		if(!SesionUserHelper.isNuloOVacio(entidadId)) {
			sbUrl.append('&').append(ConstantesSesionUser.ID_ENTIDAD).append("=").append(entidadId);
		}
		
		return sbUrl.toString();
	}

}
