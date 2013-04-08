package util;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import se.usuarios.AppUserRIImpl;
import es.archigest.framework.web.filter.security.common.SecurityGlobals;

/**
 * Clase de utilidad para multientidad
 * @author Iecisa
 * @version $Revision: 2965 $
 *
 */
public class SigemMultiEntityUtil extends MultiEntityUtil {

	/**
	 * Permite obtener la url de salidad para la aplicación
	 * @param request Petición actual
	 * @return Url de logout
	 */
	public static String getUrlLogout(HttpServletRequest request){

		// Información de la autenticación del usuario
		Subject subject = (Subject) request.getSession(true).getAttribute(SecurityGlobals.SUBJECT);
		if (AppUserRIImpl.getSessionKey(subject)!=null)
			return AutenticacionBackOffice.obtenerUrlLogout(request);
		else
			return AutenticacionAdministracion.obtenerUrlLogout(request);
	}

}
