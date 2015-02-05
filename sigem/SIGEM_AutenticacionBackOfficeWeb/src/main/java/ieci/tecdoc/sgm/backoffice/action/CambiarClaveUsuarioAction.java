package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.sgm.backoffice.form.CambioClaveForm;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		try {
			
			HttpSession session = request.getSession();
			CambioClaveForm formCambioClave = (CambioClaveForm)form;

			// Obtener el identificador de la entidad
			String idEntidad = formCambioClave.getIdEntidad();
			if (Utilidades.isNuloOVacio(idEntidad)){
				idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
			}

			// Validación del formulario
			ActionErrors errors = formCambioClave.validate(getResources(request), getLocale(request));
			if (errors.isEmpty()) {

				ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
				
				// Información actual del usuario
				DatosUsuario user = new DatosUsuario();
				user.setUser(formCambioClave.getUsername());
				user.setPassword(formCambioClave.getCurrentPassword());
				
				// Información de la entidad
				Entidad entidad = new Entidad();
				entidad.setIdentificador(idEntidad);
				
				// Autenticar al usuario con la clave actual
				user = oServicio.authenticateUser(user, entidad);
				if (user != null) {

					// Actualizar la clave del usuario
					user.setPassword(formCambioClave.getNewPassword());
					oServicio.updateUser(user, entidad);
					
					ActionForward forward = mapping.findForward("success");
					return new ActionForward(forward.getName(),
							forward.getPath() + "?url=" + formCambioClave.getUrl(),
							forward.getRedirect());

				} else {
					
					// Mostrar error de autenticación
		    		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("autenticacion.error"));
					saveErrors(request, errors);

					return mapping.findForward("error");
				}

			} else {
				
				// Mostrar error de validación del formulario
				saveErrors(request, errors);
				return mapping.findForward("error");
			}
			
		} catch(Exception e) {
			
			logger.error("Se ha producido un error al cambiar la clave del usuario", e);

			// Mostrar error de validación del formulario
			ActionErrors errors = new ActionErrors();
    		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("cambioClave.error", e.getLocalizedMessage()));
			saveErrors(request, errors);
			
			return mapping.findForward("error");
	   	}	
	}
}
