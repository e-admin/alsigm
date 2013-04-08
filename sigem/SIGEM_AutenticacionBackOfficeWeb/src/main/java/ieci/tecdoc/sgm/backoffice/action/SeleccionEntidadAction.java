package ieci.tecdoc.sgm.backoffice.action;

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

public class SeleccionEntidadAction extends Action{
	
	private static final Logger logger = Logger.getLogger(SeleccionEntidadAction.class);
			
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try{	
			HttpSession session = request.getSession();
			String idEntidad = null, idAplicacion = null, key = null;
			
			ArrayList idiomas = LectorIdiomas.getIdiomas();
			session.setAttribute(ConstantesIdioma.IDIOMAS_DISPONIBLES, idiomas);

			key = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
			if (Utilidades.isNuloOVacio(key)) {
				key = new String("");
			}
			
			idEntidad = (String)request.getParameter(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
				if (Utilidades.isNuloOVacio(idEntidad)) {
					idEntidad = new String("");
				}
			} 
			
			idAplicacion = (String)request.getParameter(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
			if (Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
				if (Utilidades.isNuloOVacio(idAplicacion)) {
					idAplicacion = new String("");
				}
			}
			
			if (!Utilidades.isNuloOVacio(key)) {
				ServicioAdministracionSesionesBackOffice oCliente = LocalizadorServicios.getServicioAdministracionSesionesBackOffice();
				boolean valida = oCliente.validarSesion(key);
				if (valida) {
					Sesion sesionBO = oCliente.obtenerSesion(key);
					if (sesionBO != null) {
						session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION, idAplicacion);
						if (Utilidades.isNuloOVacio(idEntidad))
							idEntidad = sesionBO.getIdEntidad();
						session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);
						session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD_ALMACENADA,  sesionBO.getIdEntidad());
						if (!Utilidades.isNuloOVacio(idAplicacion)) {
							if (idEntidad.equals(sesionBO.getIdEntidad())) {
								if (!ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO.equals(idAplicacion)) {
									ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
									String url = oServicio.obtenerDireccionAplicacion(idAplicacion);
									url = AutenticacionBackOffice.comprobarURL(request, url);
									request.setAttribute(Defs.PARAMETRO_URL, url);
									return mapping.findForward("redirigir");
								} else {
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
											return mapping.findForward("success");
										}
									} else {
										//PONER UN PARAMETRO PARA QUE LO DIGA
										return mapping.findForward("success");
									}
								}
							} else {
								return mapping.findForward("success");
							}
						} else {
							return mapping.findForward("success");
						}
					} else session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO, "");
				} else session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO, "");
			}
			
			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);
			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD_ALMACENADA, "");
			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION, idAplicacion);
			
			if (Utilidades.isNuloOVacio(idEntidad)) {
				List oLista = AdministracionHelper.obtenerListaEntidades();
				if(oLista.size()==1) {
					Entidad oEntidad = (Entidad)oLista.get(0);
					session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, oEntidad.getIdentificador());
			   		return mapping.findForward("login");
				} else {
					request.setAttribute("entidades", oLista);
			   		return mapping.findForward("entidades");
				}
			} else {
		   		return mapping.findForward("login");
			} 
		}catch(Exception e){
			logger.error("Se ha producido un error al seleccionar la entidad", e.fillInStackTrace());
			return mapping.findForward("entidades");
	   	}		
	}
}
