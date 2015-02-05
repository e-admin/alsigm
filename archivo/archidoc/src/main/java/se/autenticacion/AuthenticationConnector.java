package se.autenticacion;

import java.util.List;

import javax.security.auth.login.LoginException;

import se.Parametrizable;

import common.model.UserInfo;

/**
 * Interfaz para los conectores de autenticación con sistemas externos.
 */
public interface AuthenticationConnector extends Parametrizable {
	/**
	 * Realiza la autenticación del usuario.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @param password
	 *            Clave del usuario.
	 * @param ip
	 *            Dirección IP del usuario.
	 * @return Identificador único del usuario.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public String authenticate(String username, String password, String ip)
			throws LoginException;

	/**
	 * Obtiene la informacion acerca del usuario.
	 * 
	 * @param idUser
	 *            Identificador del usuario.
	 * @return Información del usuario.
	 */
	public UserInfo getUserInfo(String idUser);

	/**
	 * Busqueda del usuario por nombre. Funcionara a modo de LIKE.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @return Lista de usuarios.
	 */
	public List findUserByName(String username);

}
