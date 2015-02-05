package ieci.tecdoc.sgm.admsistema.action;

import java.util.ArrayList;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListadoMunicipiosAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ListadoMunicipiosAction.class);
	
	public static final String FORWARD_SUCCESS = "municipios";
	public static final String FORWARD_ERROR = "municipios";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
				String idProvincia = request.getParameter(Defs.PARAMETRO_PROVINCIA);
				//request.setAttribute(Defs.PARAMETRO_MUNICIPIOS, oServicio.obtenerMunicipios(new Integer(idProvincia).intValue()));
				request.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
