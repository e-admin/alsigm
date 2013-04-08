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

public class ModificarEntidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ModificarEntidadAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_MODIFICAR = "modificar";
	public static final String FORWARD_FAILURE = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				EntidadForm entidadForm = (EntidadForm)form;

				String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				
				ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
				
				if (entidadForm == null || Utilidades.esNuloOVacio(entidadForm.getIdEntidad())) {
					String idEntidad = (String)request.getAttribute(Defs.PARAMETRO_ID_ENTIDAD);
					
					if (Utilidades.permisoAdministrarEntidad(usuario, idEntidad)) {
						Entidad entidad = oServicio.obtenerEntidad(idEntidad);
						if (form == null) form = new EntidadForm();
						((EntidadForm)form).setIdEntidad(entidad.getIdentificador());
						((EntidadForm)form).setNombreCorto(entidad.getNombreCorto());
						((EntidadForm)form).setNombreLargo(entidad.getNombreLargo());
						((EntidadForm)form).setCodigoINE(entidad.getCodigoINE());
						ServicioGeoLocalizacion oServicioGeo = LocalizadorServicios.getServicioGeoLocalizacion();
						if (!Utilidades.esNuloOVacio(entidad.getCodigoINE())) {
							String prov = entidad.getCodigoINE().substring(0, 2);
							String mun = entidad.getCodigoINE().substring(2, entidad.getCodigoINE().length());
							((EntidadForm)form).setProvincia(prov);
							((EntidadForm)form).setMunicipio(mun);
							//session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, oServicioGeo.obtenerMunicipios(new Integer(prov).intValue()));
							session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
						}
						//session.setAttribute(Defs.PARAMETRO_PROVINCIAS, oServicioGeo.obtenerProvincias());
						session.setAttribute(Defs.PARAMETRO_PROVINCIAS, new ArrayList());
						request.setAttribute(Defs.PARAMETRO_ENTIDAD, entidad);
						return mapping.findForward(FORWARD_MODIFICAR);
					} else {
						logger.debug("El usuario " + usuario + " ha intentado modificar la entidad " + idEntidad + " sin tener permisos");
						return mapping.findForward(GLOBAL_FORWARD_ERROR);
					}
				} else {
					boolean error = false;
					String idProvincia = null;
					
					if (entidadForm == null) {
						return mapping.findForward(GLOBAL_FORWARD_ERROR);
					} else if (Utilidades.esNuloOVacio(entidadForm.getIdEntidad()) || Utilidades.esNuloOVacio(entidadForm.getNombreCorto()) ||
							Utilidades.esNuloOVacio(entidadForm.getNombreLargo()) ) {
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
							ServicioGeoLocalizacion oServicioGeo = LocalizadorServicios.getServicioGeoLocalizacion();
							//session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, oServicioGeo.obtenerMunicipios(new Integer(idProvincia).intValue()));
							session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
						} else session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
						return mapping.findForward(FORWARD_MODIFICAR);
					}
					
					Entidad entidad = new Entidad();
					entidad.setIdentificador(entidadForm.getIdEntidad().toUpperCase());
					entidad.setNombreCorto(entidadForm.getNombreCorto());
					entidad.setNombreLargo(entidadForm.getNombreLargo());
					entidad.setCodigoINE(entidadForm.getCodigoINE());
					oServicio.actualizarEntidad(entidad);
	
					request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.entidad.modificacion_correcta");
					return mapping.findForward(FORWARD_SUCCESS);
				}
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.entidad.modificacion_incorrecta");
				return mapping.findForward(FORWARD_FAILURE);
			} 
	}
}
