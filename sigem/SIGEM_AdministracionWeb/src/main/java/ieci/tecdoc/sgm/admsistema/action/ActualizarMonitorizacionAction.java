package ieci.tecdoc.sgm.admsistema.action;

import java.io.File;
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

public class ActualizarMonitorizacionAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ActualizarMonitorizacionAction.class);

	public static final String FORWARD_ACTUALIZAR = "actualizar";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();

				String tipo = request.getParameter(Defs.PARAMETRO_TIPO_PROCESO);
				String proceso = request.getParameter(Defs.PARAMETRO_ID_PROCESO);

				String directorio = "";
				if (Defs.EXPORTAR.equals(tipo))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.EXPORTAR;
				else if (Defs.IMPORTAR.equals(tipo))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.IMPORTAR;
				else if (Defs.ACCION_MULTIENTIDAD.equals(tipo))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.ACCION_MULTIENTIDAD;

				String fichero = directorio + File.separator + proceso + File.separator + Defs.ESTATUS;

				String usuario = (String)session.getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				if (Utilidades.esNuloOVacio(usuario))
					usuario = "";

				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				String[] entidades = Utilidades.obtenerIdentificadoresEntidades(
						Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario))
					);

				List procesosExportacion = Utilidades.obtenerProcesos(entidades, Defs.EXPORTAR);
				List procesosImportacion = Utilidades.obtenerProcesos(entidades, Defs.IMPORTAR);
				List procesosAccionesMultientidad = Utilidades.obtenerProcesos(entidades, Defs.ACCION_MULTIENTIDAD);

				request.setAttribute(Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES, procesosExportacion);
				request.setAttribute(Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES, procesosImportacion);
				request.setAttribute(Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD, procesosAccionesMultientidad);

				request.setAttribute(Defs.PARAMETRO_ID_PROCESO, proceso);
				request.setAttribute(Defs.PARAMETRO_FICHERO_MONITORIZACION, fichero);

				return mapping.findForward(FORWARD_ACTUALIZAR);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.PARAMETRO_FICHERO_MONITORIZACION, null);
				return mapping.findForward(FORWARD_ACTUALIZAR);
			}
	}
}
