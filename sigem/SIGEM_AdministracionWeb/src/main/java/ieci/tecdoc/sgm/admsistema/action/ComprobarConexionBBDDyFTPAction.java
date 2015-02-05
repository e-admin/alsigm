package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComprobarConexionBBDDyFTPAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ComprobarConexionBBDDyFTPAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			boolean conexion = false;
			boolean conexionFtp = false;
			
			try {
				String direccion = null, puerto = null, usuario = null, password = null;
				
				direccion = request.getParameter(Defs.PARAMETRO_DIRECCION_BASE_DATOS);
				if (Utilidades.esNuloOVacio(direccion))
					direccion = "";
				
				puerto = request.getParameter(Defs.PARAMETRO_PUERTO_BASE_DATOS);
				if (Utilidades.esNuloOVacio(puerto))
					puerto = "";
				
				usuario = request.getParameter(Defs.PARAMETRO_USUARIO_BASE_DATOS);
				if (Utilidades.esNuloOVacio(usuario))
					usuario = "";
				
				password = request.getParameter(Defs.PARAMETRO_PASSWORD_BASE_DATOS);
				if (Utilidades.esNuloOVacio(password))
					password = "";
				
				conexion = Utilidades.comprobarConexionBBDD(direccion, puerto, usuario, password);
				
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION, new Boolean(conexion));
				
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
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION, new Boolean(conexion));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_FTP, new Boolean(conexionFtp));
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
