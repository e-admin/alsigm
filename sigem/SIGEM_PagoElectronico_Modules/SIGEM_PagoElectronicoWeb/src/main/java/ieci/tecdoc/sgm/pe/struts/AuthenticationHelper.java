package ieci.tecdoc.sgm.pe.struts;
/*
 *  $Id: AuthenticationHelper.java,v 1.5.2.2 2008/07/09 08:52:18 jnogales Exp $
 */
import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.pe.struts.cert.CertInfo;
import ieci.tecdoc.sgm.pe.struts.cert.UserCertificateUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class AuthenticationHelper {

	private static final Logger logger = Logger.getLogger(AuthenticationHelper.class);
	private static final String SESSION_ID_KEY = "SESION_ID";
	private static final String ENTIDAD_ID_KEY = "ENTIDAD_ID";	
	
	public static boolean saveUser(HttpServletRequest request) throws Exception{
		String cSessionId = null;
		String cEntidadId = null;
		CertInfo oSolicitante = UserCertificateUtil.getUserData(request);
		if(oSolicitante == null){
			cSessionId = getSessionId(request);
			if(cSessionId == null){
				return false;
			}
			
			cEntidadId = getEntidadId(request);
			Entidad oEntidad = new Entidad();
			oEntidad.setIdentificador(cEntidadId);
			if(cEntidadId == null){
				return false;
			}			
			ServicioSesionUsuario oServicio = LocalizadorServicios.getServicioSesionUsuario();
			oSolicitante = getCertInfo(oServicio.getInfoUsuario(cSessionId, oEntidad));
//			oSolicitante = getCertInfo(SesionManager.getSender(cSessionId));
			oSolicitante.setM_sessionId(cSessionId);
			request.getSession().setAttribute(Constantes.KEY_USER_DATA, oSolicitante);	
			return true;
		}else{
			return true;
		}
	}
	
	
	private static String getSessionId(HttpServletRequest request){
		String sessionIdIni = (String)request.getParameter(SESSION_ID_KEY);
		if(sessionIdIni == null || sessionIdIni.equals("") || sessionIdIni.equals("null")){
			sessionIdIni = (String)request.getSession().getAttribute(SESSION_ID_KEY);
			if(sessionIdIni == null || sessionIdIni.equals("") || sessionIdIni.equals("null")){
				return null;
			}
		}
		return sessionIdIni;
	}

	private static String getEntidadId(HttpServletRequest request){
		String entidadIdIni = (String)request.getParameter(ENTIDAD_ID_KEY);
		if(entidadIdIni == null || entidadIdIni.equals("") || entidadIdIni.equals("null")){
			entidadIdIni = (String)request.getSession().getAttribute(ENTIDAD_ID_KEY);
			if(entidadIdIni == null || entidadIdIni.equals("") || entidadIdIni.equals("null")){
				return null;
			}
		}
		return entidadIdIni;
	}
	
	private static CertInfo getCertInfo(InfoUsuario poSolicitante){
		
		CertInfo oCertinfo = new CertInfo();
		
		oCertinfo.setM_issuer(poSolicitante.getCertificateIssuer());
		oCertinfo.setM_serialNumber(poSolicitante.getCertificateSN());
		
        // Persona fisica o juridica
        if (poSolicitante.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {
        
        	oCertinfo.setM_nif(poSolicitante.getId());
        	oCertinfo.setM_nombre(poSolicitante.getName());
        }
        else {
        	// Persona juridica (representante legal)
        	oCertinfo.setM_nif(poSolicitante.getCIF());
        	oCertinfo.setM_nombre(poSolicitante.getSocialName());
        }
        
		return oCertinfo;
	}
	
}
