package ieci.tecdoc.sgm.core.services.administracion;

import ieci.tecdoc.sgm.core.services.entidades.Entidad;


/*
 * $Id: ServicioAdministracion.java,v 1.1.2.11 2008/04/22 18:47:59 afernandez Exp $
 */
public interface ServicioAdministracion {

	/**
	 * Da de alta un usuario de admimnistracion
	 * @param usuario Datos del usuario
	 * @throws AdministracionException Si se produce algún error
	 */
	public void altaUsuario(Usuario usuario) throws AdministracionException;
	
	/**
	 * Da de baja un usuario de admimnistracion
	 * @param usuario Datos del usuario
	 * @throws AdministracionException Si se produce algún error
	 */	
	public void bajaUsuario(Usuario usuario) throws AdministracionException;
	
	/**
	 * Obtiene los datos de un usuario de admimnistracion
	 * @param usuario Nombre de usuario
	 * @throws AdministracionException Si se produce algún error
	 */	
	public Usuario obtenerUsuario(String usuario) throws AdministracionException;
	
	/**
	 * Actualiza un usuario de admimnistracion
	 * @param usuario Datos del usuario
	 * @throws AdministracionException Si se produce algún error
	 */	
	public void actualizaUsuario(Usuario usuario) throws AdministracionException;

	/**
	 * Auntentica un usuario de admimnistracion
	 * @param usuario Código de usuario
	 * @param password contraseña de usuario
	 * @throws AdministracionException Si se produce algún error
	 */	
	public boolean autenticaUsuario(String usuario, String password) throws AdministracionException;

	/**
	 * Actualiza la contraseñade un usuario de admimnistracion
	 * @param usuario Código de usuario
	 * @param password contraseña de usuario
	 * @throws AdministracionException Si se produce algún error
	 */	
	public void actualizaPasswordUsuario(Usuario usuario, String password) throws AdministracionException;

	/**
	 * Da de alta permisos de administración para una aplicación 
	 * sobre un usuario de administración
	 * @param perfil Datos de los permisos
	 * @throws AdministracionException Si se produce algún error
	 */	
	public void altaPerfil(Perfil perfil) throws AdministracionException;
	
	/**
	 * Da de baja permisos de administración para una aplicación 
	 * sobre un usuario de administración
	 * @param perfil Datos de los permisos
	 * @throws AdministracionException Si se produce algún error
	 */	
	public void bajaPerfil(Perfil perfil) throws AdministracionException;
	
	/**
	 * Elimina los permisos de administración de un usuario para una entidad
	 * @param idEntidad Identificador de entidad
	 * @param idUsuario Identificador de usuario
	 * @throws AdministracionException Si se produce algún error
	 */
	public void bajaPerfil(String idEntidad, String  idUsuario) throws AdministracionException;
	
	/**
	 * Elimina todos los permisos de administración para un usuario
	 * @param idEntidad Identificador de entidad
	 * @param idUsuario Identificador de usuario
	 * @throws AdministracionException Si se produce algún error
	 */
	public void bajaPerfil(String  idUsuario) throws AdministracionException;
	
	/**
	 * Actualiza los permisos de administración sobre las aplicaciones para un usuario 
	 * en una entidad
	 * @param idAplicacion Listado de aplicaciones para los que el usuario tiene permisos
	 * @param idUsuario Identificador de usuario
	 * @param idEntidad Identificador de entidad
	 * @throws AdministracionException Si se produce algún error
	 */
	public void actualizaPerfiles(String[] idAplicacion, String idUsuario, String idEntidad) throws AdministracionException;
	
	/**
	 * Recupera toda la información de permisos de administración de un usuario
	 * @param usuario Identificador de usuario
	 * @return Perfil[] Listado con los datos de los permisos
	 * @throws AdministracionException Si se produce algún error
	 */
	public Perfil[] getPerfiles(String usuario) throws AdministracionException;

	/**
	 * Recupera la información de los permisos de administracion para un usuario
	 * en una entidad
	 * @param usuario Identificador de usuario
	 * @param entidad Identificador de entidad
	 * @return Perfil[] Listado con los datos de los permisos
	 * @throws AdministracionException Si se produce algún error
	 */
	public Perfil[] getPerfiles(String usuario, String entidad) throws AdministracionException;

	/**
	 * Recupera el listado de entidades para las que tiene permisos de 
	 * administración un usuario
	 * @param usuario Identificador de usuario
	 * @return Entidad[] Listado con los datos de las entidades
	 * @throws AdministracionException Si se produce algún error
	 */
	public Entidad[] getEntidades(String usuario) throws AdministracionException;
	

	/**
	 * Recupera la información de las aplicaciones para las que un usuario
	 * tiene permisos de administración en una entidad
	 * @param usuario Identificador de usuario
	 * @param entidad Identificador de entidad
	 * @return Aplicacion[] Listado con los datos de las aplicaciones
	 * @throws AdministracionException Si se produce algún error
	 */
	public Aplicacion[] getAplicaciones(String usuario, String entidad) throws AdministracionException;
	
	/**
	 * Recupera la información de las aplicaciones para las que un usuario
	 * tiene permisos de administración en una entidad
	 * @return Aplicacion[] Listado con los datos de las aplicaciones
	 * @throws AdministracionException Si se produce algún error
	 */
	public Aplicacion[] getAplicaciones() throws AdministracionException;

	
	/**
	 * Devuelve los datos de una aplicación
	 * @param idAplicacion Identificador de la aplicación
	 * @return Datos de la aplicación 
	 * @throws AdministracionException Si se produce algún error
	 */
	public Aplicacion getAplicacion(String idAplicacion) throws AdministracionException;

	/**
	 * Devuelve el listado de usuarios de administración de una entidad
	 * @param idEntidad Identificador de entidad	
	 * @return Usuario[] Listado de usuarios administradores
	 * @throws AdministracionException Si se produce algún error
	 */
	public Usuario[] getUsuariosEntidad(String idEntidad) throws AdministracionException;
	
	/**
	 * Devuelve el listado de acciones de multientidad
	 * @return AccionMultientidad[] Listado de acciones de multientidad
	 * @throws AdministracionException Si se produce algún error
	 */
	public AccionMultientidad[] getAccionesMultientidad() throws AdministracionException;
	
	/**
	 * Devuelve una accion de multientidad a partir de su identificador
	 * @param idAccion identificador de la accion de multientidad
	 * @return Datos de la accion de multientidad
	 * @throws AdministracionException Si se produce algún error
	 */
	public AccionMultientidad getAccionMultientidad(String idAccion) throws AdministracionException;

}
