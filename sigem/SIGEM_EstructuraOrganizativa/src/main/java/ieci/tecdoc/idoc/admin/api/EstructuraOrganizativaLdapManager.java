package ieci.tecdoc.idoc.admin.api;


import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;
import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.ldap.LdapBasicFns;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.core.ldap.LdapUasFns;
import ieci.tecdoc.idoc.admin.api.exception.EstructuraOrganizativaLdapErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.internal.LdapGroupImpl;
import ieci.tecdoc.idoc.admin.internal.LdapUserImpl;
import ieci.tecdoc.idoc.admin.internal.UserProfileImpl;
import ieci.tecdoc.idoc.admin.internal.UserProfilesImpl;
import ieci.tecdoc.sbo.config.CfgDefs;
import ieci.tecdoc.sbo.config.CfgLdapConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.uas.base.UasBaseError;
import ieci.tecdoc.sbo.uas.ldap.UasBnoAuth;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtilLdap;
import ieci.tecdoc.sbo.uas.std.UasBnoAuthEx;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.exception.IeciTdException;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.GestionUsuariosBackOfficeException;

import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapURL;

public class EstructuraOrganizativaLdapManager {

	private static final Logger logger = Logger.getLogger(EstructuraOrganizativaLdapManager.class);
	
	public EstructuraOrganizativaLdapManager() {}
	
	/*************************************
	 * Método que determina si la entidad está asociada a un Ldap
	 * @param entidad Entidad
	 * @return boolean (true si es de Ldap, false si no lo es)
	 * @throws Exception
	 */
	public static boolean esEntidadLdap( String entidad ) throws Exception {
		
		DbConnection dbConn=new DbConnection();
		try {
			
			  dbConn.open(DBSessionManager.getSession(entidad));
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
			
			  dbConn.open(DBSessionManager.getSession(entidad));
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
	public static String authenticate(String username, String password, String entidad) throws GestionUsuariosBackOfficeException {
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
				throw new GestionUsuariosBackOfficeException(EstructuraOrganizativaLdapErrorCodes.EC_BAD_USER_OR_PASS, e);
			} else if (UasBaseError.EC_INVALID_AUTH_SPEC.equals(e.getErrorCode())) {
				throw new GestionUsuariosBackOfficeException(EstructuraOrganizativaLdapErrorCodes.EC_BAD_USER_OR_PASS, e);
			} else {
				throw new GestionUsuariosBackOfficeException(EstructuraOrganizativaLdapErrorCodes.ERROR_INESPERADO, e);
			}
		} catch(Exception e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario " + username, e);
			throw new GestionUsuariosBackOfficeException(EstructuraOrganizativaLdapErrorCodes.ERROR_INESPERADO, e);
		} 
	}
	
	/*********************************************
	 * Método que obtiene los datos del usuario Ldap
	 * @param user - Nombre de usuario
	 * @param userId - Id del usuario (id de invesdoc)
	 * @param entidad - Entidad
	 * @return LdapUserImpl
	 * @throws GestionUsuariosBackOfficeException
	 */
	
	public static LdapUserImpl getUser(String user, String userId, String entidad) throws GestionUsuariosBackOfficeException {
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
    		throw new GestionUsuariosBackOfficeException(EstructuraOrganizativaLdapErrorCodes.EC_GET_USER, e); 
		} 
    	
		return null;
	}
	
	public LdapGroup getLdapGroup(String ldadGuid, String entidad) throws Exception {

		DbConnection dbConn = new DbConnection();
		try{
				dbConn.open(DBSessionManager.getSession(entidad));
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
				dbConn.open(DBSessionManager.getSession(entidad));
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
			dbConn.open(DBSessionManager.getSession(entidad));
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
				dbConn.open(DBSessionManager.getSession(entidad));
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
			dbConn.open(DBSessionManager.getSession(entidad));
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

		profile = new UserProfileImpl(id, UserDefs.PRODUCT_SYSTEM, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, UserDefs.PRODUCT_USER, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, UserDefs.PRODUCT_IDOC, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, UserDefs.PRODUCT_IFLOW, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, UserDefs.PRODUCT_ISICRES, 
				idPerfil);
		_profiles.add(profile);

		profile = new UserProfileImpl(id, UserDefs.PRODUCT_VOLUME, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);
		
		profile = new UserProfileImpl(id, UserDefs.PRODUCT_CATALOG, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);
		
		profile = new UserProfileImpl(id, UserDefs.PRODUCT_PORTAL, 
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		return _profiles;
	}
}
	
