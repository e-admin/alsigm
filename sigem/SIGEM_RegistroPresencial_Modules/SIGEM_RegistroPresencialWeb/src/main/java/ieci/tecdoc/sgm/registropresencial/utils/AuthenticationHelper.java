package ieci.tecdoc.sgm.registropresencial.utils;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;


public class AuthenticationHelper {

	private static final Logger logger = Logger.getLogger(AuthenticationHelper.class);

	public static boolean authenticate(HttpServletRequest request) {

		UseCaseConf ucc = (UseCaseConf) request.getSession().getAttribute(Keys.J_USECASECONF);
    	if(ucc== null) ucc = new UseCaseConf();

    	//Synchronized Si mandamos más de una petición para la misma sesión pueden haber problemas de concurrencia en el Servicio Web
    	String keySession = (String)request.getSession().getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
    	if(keySession==null) {
    		keySession=request.getSession().getId();
    	}

    	synchronized (keySession) {
        	if(AutenticacionBackOffice.autenticar(request)) {
		   		if(ucc.getUserName()==null) {
		   			Sesion sesion = AutenticacionBackOffice.obtenerDatos(request);
		   			ucc.setEntidadId(sesion.getIdEntidad());
		   			ucc.setUserName(sesion.getUsuario());
		   		}
		   	} else {
		   		return false;
		   	}
    	}

    	request.getSession().setAttribute(Keys.J_USECASECONF, ucc);
    	return true;
	}


	public static String getWebAuthURL(HttpServletRequest request){
		return AutenticacionBackOffice.obtenerUrlLogin(request,"RP");
	}

	public static String getWebAuthDesconectURL(HttpServletRequest request){
		return AutenticacionBackOffice.obtenerUrlLogout(request);
	}
}
