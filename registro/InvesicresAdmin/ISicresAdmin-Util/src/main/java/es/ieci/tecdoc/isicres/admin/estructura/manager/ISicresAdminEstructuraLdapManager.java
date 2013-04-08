package es.ieci.tecdoc.isicres.admin.estructura.manager;


import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapURL;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapBasicFns;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapGroup;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapUser;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.LdapGroupImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.LdapUserImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.UserProfileImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.UserProfilesImpl;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminLdapKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminGestionUsuariosException;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgDefs;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;
import es.ieci.tecdoc.isicres.admin.sbo.uas.base.UasAuthToken;
import es.ieci.tecdoc.isicres.admin.sbo.uas.base.UasBaseError;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasBnoAuth;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasBnoAuthEx;

public class ISicresAdminEstructuraLdapManager {

	private static final Logger logger = Logger.getLogger(ISicresAdminEstructuraLdapManager.class);

	public ISicresAdminEstructuraLdapManager() {}

	/*************************************
	 * Método que determina si la entidad está asociada a un Ldap
	 * @param entidad Entidad
	 * @return boolean (true si es de Ldap, false si no lo es)
	 * @throws Exception
	 */
	public static boolean esEntidadLdap( String entidad ) throws Exception {

		DbConnection dbConn=new DbConnection();
		try {

			  dbConn.open(DBSessionManager.getSession());
		      CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();
		      CfgLdapConfig ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);

	          if( ldapCfg.getEngine() == CfgDefs.LDAP_ENGINE_NONE) {
	        	  return false;
	          } else {
	        	  return true;
	          }

		} catch (Exception e) {
			throw e;
		}

		finally {
			if( dbConn != null) {
				dbConn.close();
			}
		}
	}

	/***********************************
	 * Método que obtiene un objeto UasAuthToken a partir del
	 * usuario, contraseña y entidad
	 * @param username - Nombre de usuario
	 * @param password - Contraseña
	 * @param entidad - Entidad
	 * @return UasAuthToken
	 * @throws Exception
	 */

	public static UasAuthToken obtenerAuthTokenUsuarioLdap(String username, String password, String entidad) throws Exception {

		LdapConnection ldapConn = new LdapConnection();
        try {
//        	  LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
//	          ldapConn.open(connCfg);
//	          UasAuthConfig authCfg = UasConfigUtilLdap.createUasAuthConfig(entidad);

		      UasAuthToken uasAuthToken = UasBnoAuth.authenticateUser(username,
      			                                                      password, 1, entidad);
      	      return uasAuthToken;

        } catch (Exception e) {
				throw e;
		} finally {
			if( ldapConn != null) {
				ldapConn.close();
			}
		}
	}

	/****************************************
	 * Método que obtiene un UasAuthToken a partir del
	 * usuario, contraseña y entidad
	 * @param username - Nombre de usuario
	 * @param password - Contraseña
	 * @param entidad - Entidad
	 * @return UasAuthToken
	 * @throws Exception
	 */

	public static UasAuthToken obtenerAuthTokenUsuarioIdoc(String username, String password, String entidad) throws Exception {

		UasAuthToken uasAuthToken = UasBnoAuthEx.authenticateUser(username, password, 1, entidad);
      	return uasAuthToken;
	}

	/**************************************
	 * Método que obtiene el Id de usuario de Invesdoc, comprueba
	 * primero si la entidad está asociada a un Ldap, si es así
	 * busca si el usuario existe en la tabla IUSERLDAPUSERHDR y devuelve su id.
	 * Si la entidad no es de Ldap busca el la tabla IUSERUSERHDR y devuelve su id
	 * @param username - Nombre de usuario
	 * @param password - Contraseña
	 * @param entidad - Entidad
	 * @return String - Id de usuario
	 * @throws Exception
	 */

	public static String obtenerIdUsuario(String username, String password, String entidad) throws Exception {

		String id = null;
		UasAuthToken uasAuthToken = null;
		DbConnection dbConn=new DbConnection();

		try {

			  dbConn.open(DBSessionManager.getSession());
		      CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();
		      CfgLdapConfig ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);

  	          if( ldapCfg.getEngine() == CfgDefs.LDAP_ENGINE_NONE) {
  	        	  uasAuthToken = UasBnoAuthEx.authenticateUser(username, password, 1, entidad);
  	          }
  	          else
  	          {
	  	          uasAuthToken = obtenerAuthTokenUsuarioLdap(username, password, entidad);
  	          }

  	          if (logger.isDebugEnabled()) {
				logger.debug("UasAuthToken => " + uasAuthToken);
			  }

			// Obtener el identificador del usuario autenticado
			if (uasAuthToken != null) {
				int userId = uasAuthToken.getUser().getId();
				if (logger.isInfoEnabled()) {
					logger.info("Usuario [" + username + "] autenticado: " + userId);
				}

				id = String.valueOf(userId);
			}

			return id;
		} catch (Exception e) {
			throw e;
		}

		finally {
			if( dbConn != null) {
				dbConn.close();
			}
		}
	}

	/**********************************************
	 * Realiza la autenticación del usuario y retorna el identificador del mismo.
	 * @param conn Conexión con la base de datos.
	 * @param username Nombre del usuario.
	 * @param password Clave del usuario.
	 * @return Identificador del usuario autenticado.
	 * @throws AutenticacionUsuarioException si ha habido algún error en la autenticación.
	 */
	public static String authenticate(String username, String password, String entidad) throws ISicresAdminGestionUsuariosException {
		String idUser = null;

		try {

			UasAuthToken uasAuthToken  = obtenerAuthTokenUsuarioLdap(username, password, entidad);

			if (logger.isDebugEnabled()) {
				logger.debug("UasAuthToken => " + uasAuthToken);
			  }

			// Obtener el identificador del usuario autenticado
			if (uasAuthToken != null) {
				int userId = uasAuthToken.getUser().getId();
				if (logger.isInfoEnabled()) {
					logger.info("Usuario [" + username + "] autenticado: " + userId);
				}

				idUser = String.valueOf(userId);
			}

			return idUser;

		} catch(IeciTdException e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario de ldap " + username, e);
			if (UasBaseError.EC_INVALID_USER_NAME.equals(e.getErrorCode())) {
				throw new ISicresAdminGestionUsuariosException(ISicresAdminLdapKeys.EC_BAD_USER_OR_PASS, e);
			} else if (UasBaseError.EC_INVALID_AUTH_SPEC.equals(e.getErrorCode())) {
				throw new ISicresAdminGestionUsuariosException(ISicresAdminLdapKeys.EC_BAD_USER_OR_PASS, e);
			} else {
				throw new ISicresAdminGestionUsuariosException(ISicresAdminLdapKeys.ERROR_INESPERADO, e);
			}
		} catch(Exception e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario " + username, e);
			throw new ISicresAdminGestionUsuariosException(ISicresAdminLdapKeys.ERROR_INESPERADO, e);
		}
	}

	/*********************************************
	 * Método que obtiene los datos del usuario Ldap
	 * @param user - Nombre de usuario
	 * @param userId - Id del usuario (id de invesdoc)
	 * @param entidad - Entidad
	 * @return LdapUserImpl
	 * @throws ISicresAdminGestionUsuariosException
	 */

	public static LdapUserImpl getUser(String user, String userId, String entidad) throws ISicresAdminGestionUsuariosException {
		try {
    		if (userId != null) {

    			IeciTdShortTextArrayList dns = null;
    			LdapConnection ldapConn = new LdapConnection();

    			try {

    	        	LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
    		        ldapConn.open(connCfg);

    		        String userStart = UasConfigUtilLdap.createUasAuthConfig(entidad).getUserStart();
	    			LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
	                String s = Ldap_url.getDN();

	                if( !userStart.equals("")) {
	                	if( userStart.indexOf(Ldap_url.getDN()) != -1) {
	                		int i = userStart.indexOf(Ldap_url.getDN());
	                		s = userStart.substring(0,i-1);
	                	}
	                } else {
	                	s = "";
	                }

	    			dns = LdapBasicFns.findEntryDns(ldapConn, s, UasConfigUtilLdap.createUasAuthConfig(entidad).getUserScope(), user);

	                String dn = "";
	    			if( dns != null) {
	    				int numOcurrencias = dns.count();
	    	            if (numOcurrencias ==1){
	    	                dn=dns.get(0);
	    	            }
	    	            String guid = LdapUasFns.findUserGuidByDn(ldapConn, dn);
	        			LdapUserImpl userImpl = new LdapUserImpl();
	        			userImpl.loadFromGuid(guid, entidad);
	    				return userImpl;
	    			}
    	        } catch (Exception e) {
    				throw e;
    			} finally {
    				if( ldapConn != null) {
    					ldapConn.close();
    				}
    			}
    		}

		} catch (Exception e) {
    		logger.error("Error al buscar el usuario con id: " + userId, e);
    		throw new ISicresAdminGestionUsuariosException(ISicresAdminLdapKeys.EC_GET_USER, e);
		}

		return null;
	}

	public LdapGroup getLdapGroup(String ldadGuid, String entidad) throws Exception {

		DbConnection dbConn = new DbConnection();
		try{
				dbConn.open(DBSessionManager.getSession());
				LdapGroupImpl ldapGroupImpl = new LdapGroupImpl();
				ldapGroupImpl.loadFromGuid(ldadGuid, entidad);

				return ldapGroupImpl;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(dbConn.existConnection()){
					dbConn.close();
				}
			}catch(Exception e){

			}
		}
	}

	public LdapGroup getLdapGroup(int id, String entidad) throws Exception {

		DbConnection dbConn = new DbConnection();
		try{
				dbConn.open(DBSessionManager.getSession());
				LdapGroupImpl ldapGroupImpl = new LdapGroupImpl();
				ldapGroupImpl.load(id, entidad);

				return ldapGroupImpl;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(dbConn.existConnection()){
					dbConn.close();
				}
			}catch(Exception e){

			}
		}
	}

	public LdapGroup getLdapGroupByDeptId(int deptId, String entidad)
			throws Exception {

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession());
			LdapGroupImpl ldapGroupImpl = new LdapGroupImpl();
			ldapGroupImpl.loadFromDeptId(deptId, entidad);

			return ldapGroupImpl;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (dbConn.existConnection()) {
					dbConn.close();
				}
			} catch (Exception e) {

			}
		}
	}

	public LdapGroup createLdapGroup(String ldadGuid, String Dn, int type, String entidad) throws Exception {

		DbConnection dbConn = new DbConnection();
		try{
				dbConn.open(DBSessionManager.getSession());
				LdapGroupImpl ldapGroupImpl = new LdapGroupImpl();
				ldapGroupImpl.createGroup(ldadGuid, Dn, type, entidad);
				ldapGroupImpl.store(entidad);

				return ldapGroupImpl;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(dbConn.existConnection()){
					dbConn.close();
				}
			}catch(Exception e){

			}
		}
	}

	public LdapUser createLdapUser(String ldadGuid, String name, int idPerfil, String entidad) throws Exception {

		DbConnection dbConn = new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			LdapUserImpl ldapUserImpl = new LdapUserImpl();
			ldapUserImpl.createUserWithProfiles(ldadGuid, name, entidad, getProfilesNewUserLdap(ldapUserImpl.getId(), idPerfil));
			ldapUserImpl.store(entidad);
			return ldapUserImpl;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(dbConn.existConnection()){
					dbConn.close();
				}
			}catch(Exception e){

			}
		}
	}

	/**
	 * Establece los perfiles a la hora de crear un usuario LDAP
	 *
	 * @param id Identificador del elemento.
	 * @throws Exception Si se produce algún error en el establecimiento de los
	 * perfiles del elemento.
	 */

	private UserProfilesImpl getProfilesNewUserLdap(int id, int idPerfil) throws Exception
	{
		UserProfileImpl profile;
		UserProfilesImpl _profiles = new UserProfilesImpl();

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_SYSTEM,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_USER,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_IDOC,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_IFLOW,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_ISICRES,
				idPerfil);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_VOLUME,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_CATALOG,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, ISicresAdminUserDefsKeys.PRODUCT_PORTAL,
				ISicresAdminUserDefsKeys.PROFILE_NONE);
		_profiles.add(profile);

		return _profiles;
	}
}

