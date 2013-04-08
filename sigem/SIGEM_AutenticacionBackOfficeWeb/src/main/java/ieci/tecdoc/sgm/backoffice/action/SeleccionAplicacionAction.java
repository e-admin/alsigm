package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.sgm.backoffice.form.EntidadAccesoForm;
import ieci.tecdoc.sgm.backoffice.temporal.Aplicacion;
import ieci.tecdoc.sgm.backoffice.utils.Defs;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.backoffice.ServicioAdministracionSesionesBackOffice;
import ieci.tecdoc.sgm.core.services.admsesion.backoffice.Sesion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma;
import ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SeleccionAplicacionAction extends Action{
	
	private static final Logger logger = Logger.getLogger(SeleccionAplicacionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try{
			HttpSession session = request.getSession();
			
			EntidadAccesoForm formEntidad =  (EntidadAccesoForm)form;
			
			ArrayList idiomas = LectorIdiomas.getIdiomas();
			session.setAttribute(ConstantesIdioma.IDIOMAS_DISPONIBLES, idiomas);

			String idAplicacion = null, idEntidad = null, key = null, idEntidadAlmacenada = null;
			
			key = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
			if (Utilidades.isNuloOVacio(key)) {
				key = new String("");
			}
			
			idEntidadAlmacenada = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD_ALMACENADA);
			if (Utilidades.isNuloOVacio(idEntidadAlmacenada)) {
				idEntidadAlmacenada = new String("");
			}
			
			if (formEntidad != null)
				idEntidad = formEntidad.getEntidadId();
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
				if (Utilidades.isNuloOVacio(idEntidad)) {
					idEntidad = new String("");
				}
			}
			
			idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
			if (Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = new String("");
			}
			
			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);
			
			if (!Utilidades.isNuloOVacio(key)) {
				if ((!Utilidades.isNuloOVacio(idEntidad) || !Utilidades.isNuloOVacio(idEntidadAlmacenada)) && !Utilidades.isNuloOVacio(idAplicacion)) {
					if (idEntidadAlmacenada.equals(idEntidad)) {
						if (!ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO.equals(idAplicacion)) {
							session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);
							ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
							String url = oServicio.obtenerDireccionAplicacion(idAplicacion);
							url = AutenticacionBackOffice.comprobarURL(request, url);
							request.setAttribute(Defs.PARAMETRO_URL, url);
							return mapping.findForward("redirigir"); 
						} else {
							ServicioAdministracionSesionesBackOffice oCliente = LocalizadorServicios.getServicioAdministracionSesionesBackOffice();
							Sesion sesionBO = oCliente.obtenerSesion(key);
							String especifico = sesionBO.getDatosEspecificos();
							if (!Utilidades.isNuloOVacio(especifico)) {
								if (especifico.indexOf(AlmacenarDatosArchivoAction.TAG_TIPO_USUARIO) != -1) {
									ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
									String url = oServicio.obtenerDireccionAplicacion(idAplicacion);
									url = AutenticacionBackOffice.comprobarURL(request, url);
									request.setAttribute(Defs.PARAMETRO_URL, url);
									return mapping.findForward("redirigir");
								} else {
									//PONER UN PARAMETRO PARA QUE LO DIGA
									List oLista = Utilidades.obtenerListaAplicaciones(request, key);
									session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION, idAplicacion);
									request.setAttribute("aplicaciones", oLista);
							   		return mapping.findForward("aplicaciones");
								}
							} else {
								//PONER UN PARAMETRO PARA QUE LO DIGA
								List oLista = Utilidades.obtenerListaAplicaciones(request, key);
								session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION, idAplicacion);
								request.setAttribute("aplicaciones", oLista);
						   		return mapping.findForward("aplicaciones");
							}
						}
					} else {
						return mapping.findForward("success"); 
					}
				}
			}
			
			if ((Utilidades.isNuloOVacio(idEntidad) && Utilidades.isNuloOVacio(idEntidadAlmacenada))){
				List oLista = AdministracionHelper.obtenerListaEntidades();
				if(oLista.size()==1) {
					Entidad oEntidad = (Entidad)oLista.get(0);
					session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, oEntidad.getIdentificador());
				} else {
					request.setAttribute("entidades", oLista);
			   		return mapping.findForward("entidades");
				}
			} 
			
			if (Utilidades.isNuloOVacio(idAplicacion)) {
				List oLista = Utilidades.obtenerListaAplicaciones(request, key);
				if(oLista.size()==1) {
					Aplicacion oAplicacion = (Aplicacion)oLista.get(0);
					session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION, oAplicacion.getIdentificador());
			   		return mapping.findForward("success");
				} else {
					request.setAttribute("aplicaciones", oLista);
			   		return mapping.findForward("aplicaciones");
				}
			} else {
		   		return mapping.findForward("success");
			} 
		
		}catch(Exception e){
			logger.error("Se ha producido un error al seleccionar la aplicacion", e.fillInStackTrace());
			return mapping.findForward("aplicaciones");
	   	}		
	}
}
