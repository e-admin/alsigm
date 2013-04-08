package common.actions;

import gcontrol.ControlAccesoConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.SigemMultiEntityUtil;

import common.bi.GestionSessionBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;

/**
 * Gestiona la salida de la aplicación.
 */
public class SigemLogoutAction extends BaseAction {
	/**
	 * Limpia los recursos de la sesión y la invalida cuando el usuario sale de la aplicación.
	 * @param mapping {@link ActionMapping} con los mapeos asociado.
	 * @param form {@link ActionForm} asociado al action.
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		super.executeLogic(mapping, form, request, response);


		ServiceRepository services = ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
        //Obtenemos el servicio para el usuario conectado
		GestionSistemaBI gs = services.lookupGestionSistemaBI();
        GestionSessionBI gsesion = services.lookupGestionSessionBI();

		// Multientidad - Obtener la dirección de vuelta
		String url = SigemMultiEntityUtil.getUrlLogout(request);

        // Nos aseguramos que el usuario tenga una sesión correcta.
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			//Registramos la salida del usuario
			gs.doLogout();

			//Eliminamos la informacion de sesion
			gsesion.logout( session.getId() );

			//Invalidar la sesión.
			session.invalidate();
		}

		// Multientidad - Obtener la dirección de vuelta
		request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, url);
	}
}