package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComprobarConexionBBDDx2yFTPAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ComprobarConexionBBDDx2yFTPAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			boolean conexionExp = false, conexionImp = false, conexionFtp = false;
			
			try {
				String direccionExp = null, puertoExp = null, usuarioExp = null, passwordExp = null;
				
				direccionExp = request.getParameter(Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP);
				if (Utilidades.esNuloOVacio(direccionExp))
					direccionExp = "";
				
				puertoExp = request.getParameter(Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP);
				if (Utilidades.esNuloOVacio(puertoExp))
					puertoExp = "";
				
				usuarioExp = request.getParameter(Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP);
				if (Utilidades.esNuloOVacio(usuarioExp))
					usuarioExp = "";
				
				passwordExp = request.getParameter(Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP);
				if (Utilidades.esNuloOVacio(passwordExp))
					passwordExp = "";
				
				conexionExp = Utilidades.comprobarConexionBBDD(direccionExp, puertoExp, usuarioExp, passwordExp);
				
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_EXP, new Boolean(conexionExp));
				
				
				String direccionImp = null, puertoImp = null, usuarioImp = null, passwordImp = null;
				
				direccionImp = request.getParameter(Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP);
				if (Utilidades.esNuloOVacio(direccionImp))
					direccionImp = "";
				
				puertoImp = request.getParameter(Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP);
				if (Utilidades.esNuloOVacio(puertoImp))
					puertoImp = "";
				
				usuarioImp = request.getParameter(Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP);
				if (Utilidades.esNuloOVacio(usuarioImp))
					usuarioImp = "";
				
				passwordImp = request.getParameter(Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP);
				if (Utilidades.esNuloOVacio(passwordImp))
					passwordImp = "";
				
				conexionImp = Utilidades.comprobarConexionBBDD(direccionImp, puertoImp, usuarioImp, passwordImp);
				
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_IMP, new Boolean(conexionImp));
				
				
				String direccionFtp = null, puertoFtp = null, usuarioFtp = null, passwordFtp = null;
				
				direccionFtp = request.getParameter(Defs.PARAMETRO_DIRECCION_FTP);
				if (Utilidades.esNuloOVacio(direccionFtp))
					direccionFtp = "";
				
				puertoFtp = request.getParameter(Defs.PARAMETRO_PUERTO_FTP);
				if (Utilidades.esNuloOVacio(puertoFtp))
					puertoFtp = "";
				
				usuarioFtp = request.getParameter(Defs.PARAMETRO_USUARIO_FTP);
				if (Utilidades.esNuloOVacio(usuarioFtp))
					usuarioFtp = "";
				
				passwordFtp = request.getParameter(Defs.PARAMETRO_PASSWORD_FTP);
				if (Utilidades.esNuloOVacio(passwordFtp))
					passwordFtp = "";
				
				conexionFtp = Utilidades.comprobarConexionFTP(direccionFtp, puertoFtp, usuarioFtp, passwordFtp);
				
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_FTP, new Boolean(conexionFtp));
				
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_EXP, new Boolean(conexionExp));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_IMP, new Boolean(conexionImp));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_FTP, new Boolean(conexionFtp));
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
