package ieci.tecdoc.sgm.core.services.autenticacion;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

public interface ServicioAutenticacionUsuarios {

	/**
	 * Método que obtiene los datos de un determinado usuario.
	 * Comprueba que el nombre de usuario y contraseña sean correctos.
	 * @param usuario Nombre de usuario
	 * @param password Contraseña.
	 * @return User Datos del usuario.
	 * @throws AutenticacionUsuarioException En caso de producirse alguna excepción.
	 */
	public DatosUsuario authenticateUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException;
	
	/**
	 * Método que recupera los datos de un usuario.
	 * @param user Nombre de usuario.
	 * @return Datos del usuario
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public DatosUsuario getUser(DatosUsuario user, Entidad entidad ) throws AutenticacionUsuarioException;
	
	/**
	 * Elimina la información de un usuario
	 * @param user Usuario de acceso.
	 * @return User Datos de usuario.
	 * @throws AutenticacionUsuarioException Si se produce algún error.
	 */
	public void deleteUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException;

	/**
	 * Devuelve una lista de usuarios que cumplen el criterio de búsqueda que llega como parámetro
	 * @param criteria Criterio de búsqueda.
	 * @return Array de DatosUsuario.
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public DatosUsuario[] findUsers(CriterioBusquedaUsuarios criteria, Entidad entidad) throws AutenticacionUsuarioException;
	
	/**
	 * Actualiza los datos de un usuario.
	 * @param user Datos del usuario.
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public void updateUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException;
	
	/**
	 * Método que da da alta en el sistema un nuevo usuario.
	 * @param user Datos de usuario.
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public void createUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException;
	
}
