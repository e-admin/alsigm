package common.startup;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.TreeModelListener;

import common.Constants;
import common.bi.GestionSessionBI;
import common.bi.ServiceRepository;

/**
 * @author LUISANVE
 * 
 */
public class ArchidocSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {

	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession destroyedSession = event.getSession();

		AppUser user = (AppUser) destroyedSession
				.getAttribute(Constants.USUARIOKEY);

		if (user != null) {
			// Eliminamos la informacion de la sesion del usuario
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));
			GestionSessionBI gsesion = services.lookupGestionSessionBI();

			gsesion.logout(destroyedSession.getId());

			// FIXME REVISAR
			ServiceRepository
					.getInstance(ServiceClient.create(user))
					.lookupGestorEstructuraDepositoBI()
					.getEstructuraDeposito()
					.removeTreeModelListener(
							(TreeModelListener) destroyedSession
									.getAttribute("estructuraDeposito"));
			// EstructuraDeposito.obtenerEstructura().removeTreeModelListener((TreeModelListener)destroyedSession.getAttribute("estructuraDeposito"));
		}
	}
}
