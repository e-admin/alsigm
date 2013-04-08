package ieci.tecdoc.sgm.admsistema.action;

import java.io.File;
import java.util.List;

import ieci.tecdoc.sgm.admsistema.proceso.Limpiar;
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

public class BorrarProcesoAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(BorrarProcesoAction.class);

	public static final String FORWARD_BORRAR = "borrar";
	public static final String FORWARD_FAILURE = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();

			String usuario = (String)session.getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
			if (Utilidades.esNuloOVacio(usuario))
				usuario = "";

			String idProceso = (String)request.getParameter(Defs.PARAMETRO_ID_PROCESO);
			String tipoProceso = (String)request.getParameter(Defs.PARAMETRO_TIPO_PROCESO);

			if (!Utilidades.esNuloOVacio(idProceso) && !Utilidades.esNuloOVacio(tipoProceso)) {
				String directorio = "";
				if(Defs.EXPORTAR.equals(tipoProceso))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.EXPORTAR;
				else if(Defs.IMPORTAR.equals(tipoProceso))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.IMPORTAR;
				else if(Defs.ACCION_MULTIENTIDAD.equals(tipoProceso))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.ACCION_MULTIENTIDAD;
				directorio = directorio + File.separator + idProceso;

				if (Limpiar.borrar(directorio)) {
					request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.proceso.borrar_archivos.correcto");
				} else {
					request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.error.proceso.borrar_archivos.incorrecto");
				}
			}

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

			return mapping.findForward(FORWARD_BORRAR);
		} catch(Exception e) {
			logger.error("Se ha producido un error inesperado", e);
			return mapping.findForward(FORWARD_FAILURE);
		}
	}

}
