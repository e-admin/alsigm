package ieci.tecdoc.sgm.admsistema.action;

import java.util.ArrayList;

import ieci.tecdoc.sgm.admsistema.form.EntidadForm;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NuevaEntidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(NuevaEntidadAction.class);
	
	public static final String FORWARD_IMPORTAR = "importar";
	public static final String FORWARD_CLONAR = "clonar";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				EntidadForm entidadForm = (EntidadForm)form;
				boolean error = false;
				String idProvincia = null;
				
				if (entidadForm == null) {
					return mapping.findForward(FORWARD_ERROR);
				} else if (Utilidades.esNuloOVacio(entidadForm.getNombreCorto()) || Utilidades.esNuloOVacio(entidadForm.getNombreLargo()) ) {
					idProvincia = entidadForm.getProvincia();
					request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.entidad.campos_obligatorios");
					error = true;
				} else if (!Utilidades.esNuloOVacio(entidadForm.getProvincia()) && Utilidades.esNuloOVacio(entidadForm.getMunicipio())){
					idProvincia = entidadForm.getProvincia();
					request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.entidad.marcar_municipio");
					error = true;				
				} else if (Utilidades.esNuloOVacio(entidadForm.getProvincia()) && !Utilidades.esNuloOVacio(entidadForm.getMunicipio())) {
					request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.entidad.marcar_provincia");
					error = true;
				} 
				
				if (error) {
					if (!Utilidades.esNuloOVacio(idProvincia)) {
						ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
						//session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, oServicio.obtenerMunicipios(new Integer(idProvincia).intValue()));
						session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
					} else session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
					return mapping.findForward(FORWARD_ERROR);
				}
				
				ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
				Entidad entidad = new Entidad();
				entidad.setIdentificador(oServicio.obtenerIdentificadorEntidad());
				entidad.setNombreCorto(entidadForm.getNombreCorto());
				entidad.setNombreLargo(entidadForm.getNombreLargo());
				if (!Utilidades.esNuloOVacio(entidadForm.getCodigoINE()))
					entidad.setCodigoINE(entidadForm.getCodigoINE());

				String accion = (String)session.getAttribute(Defs.ACTION_FORMULARIO_NUEVA_ENTIDAD);
				if (Defs.ACCION_NUEVA_ENTIDAD.equals(accion)) {
					session.setAttribute(Defs.PARAMETRO_ENTIDAD, entidad);
					return mapping.findForward(FORWARD_IMPORTAR);
				} else if (Defs.ACCION_CLONAR_ENTIDAD.equals(accion)) {
					session.setAttribute(Defs.PARAMETRO_ENTIDAD_IMPORTAR, entidad);
					return mapping.findForward(FORWARD_CLONAR);
				} else {
					return mapping.findForward(FORWARD_ERROR);
				}
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				return mapping.findForward(FORWARD_ERROR);
			} 
	}
}
