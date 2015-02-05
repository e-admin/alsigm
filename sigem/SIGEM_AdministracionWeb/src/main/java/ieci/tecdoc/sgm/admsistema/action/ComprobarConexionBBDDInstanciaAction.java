package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComprobarConexionBBDDInstanciaAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ComprobarConexionBBDDInstanciaAction.class);

	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

			boolean conexion = false;
			try {
				String direccion = null, puerto = null, instancia = null;
				String usuarioRP = null, passwordRP = null;

				direccion = request.getParameter(Defs.PARAMETRO_DIRECCION_BASE_DATOS);
				if (Utilidades.esNuloOVacio(direccion))
					direccion = "";

				puerto = request.getParameter(Defs.PARAMETRO_PUERTO_BASE_DATOS);
				if (Utilidades.esNuloOVacio(puerto))
					puerto = "";

				usuarioRP = request.getParameter(Defs.PARAMETRO_USUARIO_BASE_DATOS_RP);
				if (Utilidades.esNuloOVacio(usuarioRP))
					usuarioRP = "";

				passwordRP = request.getParameter(Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP);
				if (Utilidades.esNuloOVacio(passwordRP))
					passwordRP = "";

				instancia = request.getParameter(Defs.PARAMETRO_INSTANCIA);
				if (Utilidades.esNuloOVacio(instancia))
					instancia = "";

				conexion = Utilidades.comprobarConexionBBDDEsquema(direccion, puerto, instancia, usuarioRP, passwordRP);
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_RP, new Boolean(conexion));

				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_AD, new Boolean(false));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_GE, new Boolean(false));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_RP, new Boolean(false));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_TE, new Boolean(false));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_AUDIT, new Boolean(false));
				request.setAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_SIR, new Boolean(false));
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
