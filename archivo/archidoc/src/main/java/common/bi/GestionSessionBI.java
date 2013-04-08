package common.bi;

import java.util.List;

import common.session.vos.SessionVO;

/**
 * Bussines Interface con los metodos de negocio para la gestion de las sesiones
 * de usuario.
 */
public interface GestionSessionBI {
	public abstract String login(String ticket, String idUser);

	public abstract String loginExpulsando(String ticket, String idUser);

	public abstract void keepAlive(String ticket);

	public abstract void logout(String ticket);

	public abstract boolean isAlive(String ticket);

	List getSessionesActivasActuales();

	SessionVO getSessionUser(String idUser);
}
