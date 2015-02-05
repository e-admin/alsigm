package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.form.AccionMultientidadForm;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para el listado de las acciones de multientidad
 * @author IECISA
 *
 */
public class ListadoAccionesMultientidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ListadoAccionesMultientidadAction.class);

	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_FAILURE = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			// Limpiar sesión y fichero temporal de anteriores configuraciones de acciones multientidad
			AccionMultientidadVO accionMultientidadVO = (AccionMultientidadVO) request.getSession().getAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD);
			if (accionMultientidadVO != null) {

				// La configuración de la acción multientidad se ha cancelado
				String cancel = request.getParameter("cancel");
				if (StringUtils.isNotBlank(cancel) && cancel.equals("true")) {

					if (StringUtils.isNotBlank(accionMultientidadVO.getFicheroTemporal())) {
						File tmpFile = new File(accionMultientidadVO.getFicheroTemporal());
						if (tmpFile.exists()) {
							tmpFile.delete();
						}
					}
				}

				request.getSession().removeAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD);
			}

			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			AccionMultientidadForm accionMultientidadForm = (AccionMultientidadForm) form;
			AccionMultientidad[] accionMultientidad = oServicio.getAccionesMultientidad();
			request.setAttribute(Defs.LISTADO_ACCIONES_MULTIENTIDAD, accionMultientidad);
			if (!ArrayUtils.isEmpty(accionMultientidad)){
				accionMultientidadForm.setIdAccion(accionMultientidad[0].getIdentificador());
			}
			return mapping.findForward(FORWARD_SUCCESS);
		} catch(Exception e) {
			logger.error("Se ha producido un error inesperado", e);
			request.setAttribute(Defs.LISTADO_ACCIONES_MULTIENTIDAD, new ArrayList());
			request.setAttribute(Defs.MENSAJE_ERROR, "acciones.multientidad.mensaje.error.listado.acciones");
			return mapping.findForward(FORWARD_FAILURE);
		}
	}
}
