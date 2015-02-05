package se.usuarios;

import javax.security.auth.Subject;

import se.usuarios.exceptions.AppUserException;

import common.exceptions.ArchivoModelException;

/**
 * Interfaz con el comportamiento de todos los interfaces remotos para la
 * gestión de usuarios de la aplicación.
 */
public interface AppUserRI {

	/**
	 * Obtiene la información del usuario en la aplicación.
	 * 
	 * @param subject
	 *            Información de la autenticación del usuario.
	 * @return Información del usuario en la aplicación.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	public AppUser getAppUser(Subject subject) throws AppUserException;

	/**
	 * Obtiene la información del usuario en la aplicación.
	 * 
	 * @param userAuthId
	 *            Identificador del usuario en el Sistema Gestor de Usuarios.
	 * @param userAuthType
	 *            Tipo de usuario.
	 * @param userName
	 *            Nombre del usuario.
	 * @param remoteIpAddress
	 *            Dirección IP del usuario.
	 * @param entity
	 *            Entidad para multientidad
	 * @param sessionAdm
	 *            Sesión de administracion
	 * @return Información del usuario en la aplicación.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	public AppUser getAppUser(String userAuthId, String userAuthType,
			String userName, String remoteIpAddress, String entity,
			String sessionAdm) throws AppUserException, ArchivoModelException;

}
