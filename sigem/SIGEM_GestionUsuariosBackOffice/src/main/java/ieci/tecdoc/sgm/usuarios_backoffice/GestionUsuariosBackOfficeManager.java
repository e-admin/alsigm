package ieci.tecdoc.sgm.usuarios_backoffice;

import ieci.tecdoc.idoc.admin.api.EstructuraOrganizativaLdapManager;
import ieci.tecdoc.sbo.uas.base.UasBaseError;
import ieci.tecdoc.sgm.base.exception.IeciTdException;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.usuarios_backoffice.database.UasBnoUser;
import ieci.tecdoc.sgm.usuarios_backoffice.database.UasDaoUserRecO;
import ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl;
import ieci.tecdoc.sgm.usuarios_backoffice.datatype.UserImpl;
import ieci.tecdoc.sgm.usuarios_backoffice.exception.CodigosErrorGestionUsuariosBackOfficeException;
import ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class GestionUsuariosBackOfficeManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GestionUsuariosBackOfficeManager.class);
	

	/**
	 * Realiza la autenticación del usuario y retorna el identificador del mismo.
	 * @param conn Conexión con la base de datos.
	 * @param username Nombre del usuario.
	 * @param password Clave del usuario.
	 * @return Identificador del usuario autenticado.
	 * @throws AutenticacionUsuarioException si ha habido algún error en la autenticación.
	 */
	public static String authenticate(String username, String password, String entidad) throws GestionUsuariosBackOfficeException {
		String idUser = null;
		
		try {
			
			idUser = EstructuraOrganizativaLdapManager.obtenerIdUsuario(username, password, entidad);
			
		} catch(IeciTdException e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario " + username, e);
			if (UasBaseError.EC_INVALID_USER_NAME.equals(e.getErrorCode())) {
				throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_BAD_USER_OR_PASS, e);
			} else if (UasBaseError.EC_INVALID_AUTH_SPEC.equals(e.getErrorCode())) {
				throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_BAD_USER_OR_PASS, e);
			} else {
				throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.ERROR_INESPERADO, e);
			}
		} catch(Exception e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario " + username, e);
			throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.ERROR_INESPERADO, e);
		} 
		
		return idUser;
	}

	/**
	 * Obtiene la información del usuario.
	 * @param conn Conexión con la base de datos.
	 * @param userId identificador del usuario.
	 * @return Información del usuario.
	 * @throws AutenticacionUsuarioException si ocurre algún error.
	 */
	public static DatosUsuarioImpl getUser(String userId, String entidad) throws GestionUsuariosBackOfficeException {
		DatosUsuarioImpl user = null;
		
    	try {
    		if (userId != null) {
    			
				// Obtener la información del usuario
	    		UasDaoUserRecO idocUser = UasBnoUser.findUserById(Integer.parseInt(userId), entidad);
				if (logger.isDebugEnabled()) {
					logger.debug("UasDaoUserRecO => " + idocUser);
				}

				// Copiar la información del usuario
				if (idocUser != null) {
		    		user = getDatosUsuario(idocUser);
				}
    		}
	    	
		} catch (Exception e) {
    		logger.error("Error al buscar el usuario con id: " + userId, e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_GET_USER, e);
		} 
    	
    	return user;
	}

	/**
	 * Obtiene la información de los usuarios a partir del nombre.
	 * @param conn Conexión con la base de datos.
	 * @param username Nombre del usuario.
	 * @return Datos de los usuarios.
	 * @throws AutenticacionUsuarioException si ocurre algún error.
	 */
	public static DatosUsuarioImpl[] findUsersByName(String username, String entidad) throws GestionUsuariosBackOfficeException {
		List userDataList = new ArrayList();
		
    	try {
    		if (username != null) {
	    		// Información de usuarios
		    	List userRecList = UasBnoUser.findUsersByName(username, entidad);
		    	if (userRecList != null) {
		    		for (int i = 0; i < userRecList.size(); i++) {
		    			UasDaoUserRecO userRec = (UasDaoUserRecO) userRecList.get(i);
		    			if (logger.isDebugEnabled()) {
		    				logger.debug("UasDaoUserRecO => " + userRec);
		    			}
	
		    			if (userRec != null) {
		    				userDataList.add(getDatosUsuario(userRec));
		        		}
		    		}
		    	}
    		}

		} catch (Exception e) {
    		logger.error("Error al buscar usuarios por nombre: " + username, e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_FIND_USERS, e);
		} 
		
		return (DatosUsuarioImpl[]) userDataList.toArray(new DatosUsuarioImpl[userDataList.size()]);
	}

	/**
	 * Crea un usuario.
	 * @param conn Conexión con la base de datos.
	 * @param user Datos del usuario.
	 * @throws AutenticacionUsuarioException si ocurre algún error.
	 */
	public static void createUser(DatosUsuarioImpl user, String entidad) throws GestionUsuariosBackOfficeException {
		//Connection dbConn = null;
		
    	try {
    		//dbConn = DBSessionManager.getSession(DataSourceManagerMultientidad.BACKOFFICE_DATASOURCE_NAME, entidad);
    		
    		if (user != null) {
    			
    			// Componer la información del usuario
    			UserImpl idocUser = new UserImpl();
    			idocUser.setName(user.getUser());
    			idocUser.setPassword(user.getPassword());
    			idocUser.setDescription(user.getName());
    			
    			idocUser.store(entidad);
    			
    			if (logger.isDebugEnabled()) {
    				logger.debug("User creado => " + idocUser);
    			}
    		}

		} catch (Exception e) {
    		logger.error("Error al crear el usuario: " + user, e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_ADD_USER, e);
		} finally {
			//cerrarConexion(dbConn);
		}
	}

	/**
	 * Modifica la información del usuario.
	 * @param conn Conexión con la base de datos.
	 * @param user Datos del usuario.
	 * @throws AutenticacionUsuarioException si ocurre algún error.
	 */
	public static void updateUser(DatosUsuarioImpl user, String entidad) throws GestionUsuariosBackOfficeException {
		try {
    		if (user != null) {
    			
    			// Obtener la información del usuario
    			UserImpl idocUser = new UserImpl();
    			idocUser.load(entidad, Integer.parseInt(user.getId()));

    			if (logger.isDebugEnabled()) {
    				logger.debug("User original => " + idocUser);
    			}

    			// Modificar la información del usuario
    			idocUser.setName(user.getUser());
    			idocUser.setPassword(user.getPassword());
    			idocUser.setDescription(user.getName());

    			idocUser.store(entidad);

    			if (logger.isDebugEnabled()) {
    				logger.debug("User modificado => " + idocUser);
    			}
    		}

		} catch (Exception e) {
    		logger.error("Error al modificar el usuario: " + user, e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_UPDATE_USER, e);
		} finally {
			//cerrarConexion(dbConn);
		}
	}

	/**
	 * Elimina la información del usuario.
	 * @param conn Conexión con la base de datos.
	 * @param userId identificador del usuario.
	 * @throws AutenticacionUsuarioException si ocurre algún error.
	 */
	public static void deleteUser(String userId, String entidad) throws GestionUsuariosBackOfficeException {
    	try {
    		if ((userId != null) && (userId.trim().length() > 0)) {
    			// Obtener la información del usuario
    			UserImpl idocUser = new UserImpl();
    			idocUser.load(null, Integer.parseInt(userId));
    			
    			idocUser.delete(null);

    			if (logger.isDebugEnabled()) {
    				logger.debug("User eliminado => " + idocUser);
    			}
    		}

		} catch (Exception e) {
    		logger.error("Error al eliminar el usuario: " + userId, e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_DELETE_USER, e);
		} 
	}
	
	/**
	 * Obtiene los datos del usuario en el formato del API.
	 * @param idocUser Información del usuario de invesDoc.
	 * @return Datos del usuario.
	 */
	private static DatosUsuarioImpl getDatosUsuario(UasDaoUserRecO idocUser) {
		DatosUsuarioImpl user = null;
		
		if (idocUser != null) {
			user = new DatosUsuarioImpl();
			user.setId(String.valueOf(idocUser.getId()));
			user.setUser(idocUser.getName());
			user.setPassword(null);
			user.setName(idocUser.getRemarks());
			user.setLastname(null);
			user.setEmail(null);
		}
		
		return user;
	}

	
	/*private static void cerrarConexion(Connection dbConn) {
		try {
			if (dbConn != null && !dbConn.isClosed())
				dbConn.close();
		}catch(Exception e){ }
	}*/
	
	/*private static void cerrarConexion(DbConnection dbConn) {
		try {
			if (dbConn != null && dbConn.existConnection())
				dbConn.close();
		}catch(Exception e){ }
	}*/
}
