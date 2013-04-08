package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.form.CambioClaveForm;
import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.AdministracionException;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CambiarClaveUsuarioAction extends Action {

	private static final Logger logger = Logger.getLogger(CambiarClaveUsuarioAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {

		CambioClaveForm formCambioClave = (CambioClaveForm)form;

		try {

			// Validación del formulario
			ActionErrors errors = formCambioClave.validate(getResources(request), getLocale(request));
			if (errors.isEmpty()) {

				if (Utilidades.isNuloOVacio(formCambioClave.getInterno())) {
					
					//
					// Cambiar la clave de un usuario administrador
					//

					ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();

					Usuario usuario = new Usuario();
					usuario.setUsuario(formCambioClave.getUsername());
					usuario.setPassword(formCambioClave.getCurrentPassword());
					
					// Actualizar la clave del usuario
					oServicio.actualizaPasswordUsuario(usuario, formCambioClave.getNewPassword());

				} else {
					
					//
					// Cambiar la clave de un usuario interno
					//

					ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
					
					// Información actual del usuario
					DatosUsuario user = new DatosUsuario();
					user.setUser(formCambioClave.getUsername());
					user.setPassword(formCambioClave.getCurrentPassword());
					
					// Información de la entidad
					ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
					entidad.setIdentificador(formCambioClave.getIdEntidadInterno());
					
					// Autenticar al usuario con la clave actual
					user = oServicio.authenticateUser(user, entidad);
					if (user != null) {
	
						// Actualizar la clave del usuario
						user.setPassword(formCambioClave.getNewPassword());
						oServicio.updateUser(user, entidad);
						
					} else {
						
						// Mostrar error de autenticación
			    		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("autenticacion.error"));
						saveErrors(request, errors);
	
						return mapping.findForward("error");
					}
				}

			} else {
				
				// Mostrar error de validación del formulario
				saveErrors(request, errors);
				return mapping.findForward("error");
			}
			
		} catch (AdministracionException e) {
			
			logger.error("Se ha producido un error al cambiar la clave del usuario", e);

			// Mostrar error de validación del formulario
			ActionErrors errors = new ActionErrors();

			if (e.getErrorCode() == Long.parseLong(ConstantesServicios.SERVICE_ADMINISTRATION_ERROR_PREFIX + ieci.tecdoc.sgm.admin.AdministracionException.EC_USER_AUTH)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("autenticacion.error"));
			} else {
	    		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("cambioClave.error", e.getLocalizedMessage()));
			}

			saveErrors(request, errors);

			return mapping.findForward("error");

		} catch (Exception e) {
			
			logger.error("Se ha producido un error al cambiar la clave del usuario", e);

			// Mostrar error de validación del formulario
			ActionErrors errors = new ActionErrors();
    		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("cambioClave.error", e.getLocalizedMessage()));
			saveErrors(request, errors);
			
			return mapping.findForward("error");
	   	}
		
		ActionForward forward = mapping.findForward("success");
		return new ActionForward(forward.getName(),
				forward.getPath() + "?url=" + URLEncoder.encode(formCambioClave.getUrl(), "ISO-8859-1"),
				forward.getRedirect());
	}
}
