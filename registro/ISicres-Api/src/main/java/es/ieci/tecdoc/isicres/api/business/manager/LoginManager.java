package es.ieci.tecdoc.isicres.api.business.manager;

import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public interface LoginManager {

	/**
	 * Método que autentica en la aplicación
	 * @param usuario - {@link UsuarioVO}
	 *
	 * @return {@link UsuarioVO}
	 */
	public abstract UsuarioVO login(UsuarioVO usuario);


	/**
	 * Método que cierra (logout de la aplicación) la sesión del usuario
	 *
	 * @param usuario - {@link UsuarioVO}
	 */
	public abstract void logout(UsuarioVO usuario);
}
