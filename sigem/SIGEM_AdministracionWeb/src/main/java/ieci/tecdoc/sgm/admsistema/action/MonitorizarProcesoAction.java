package ieci.tecdoc.sgm.admsistema.action;

import java.util.List;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MonitorizarProcesoAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(MonitorizarProcesoAction.class);
	
	public static final String FORWARD_MONITORIZAR = "monitorizar";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				
				String usuario = (String)session.getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				if (Utilidades.esNuloOVacio(usuario))
					usuario = "";
				
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				String[] entidades = Utilidades.obtenerIdentificadoresEntidades(
						Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario))
					);
				
				List procesosExportacion = Utilidades.obtenerProcesos(entidades, Defs.EXPORTAR);
				List procesosImportacion = Utilidades.obtenerProcesos(entidades, Defs.IMPORTAR);
				List procesosAccionMultientidad = Utilidades.obtenerProcesos(entidades, Defs.ACCION_MULTIENTIDAD);
				
				request.setAttribute(Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES, procesosExportacion);
				request.setAttribute(Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES, procesosImportacion);
				request.setAttribute(Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD, procesosAccionMultientidad);
				
				return mapping.findForward(FORWARD_MONITORIZAR);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				return mapping.findForward(FORWARD_ERROR);
			}
	}

}
