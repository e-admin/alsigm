package es.ieci.tecdoc.isicres.admin.sbo.uas.ldap;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.db.DbError;
import es.ieci.tecdoc.isicres.admin.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapError;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.sbo.uas.base.*;


public final class UasMdoAuth
{

	//~ Constructors -----------------------------------------------------------

	private UasMdoAuth(){}

	//~ Methods ----------------------------------------------------------------

	
	
	
   public static UasAuthToken authenticateUser(DbConnection dbConn, LdapConnCfg ldapCfg,
												           UasAuthConfig authConfig,
												           String name, String pwd,
												           int cntsTriesNum, String entidad)
									   throws Exception
	{
	  
	   UasAuthToken   token    = null;
		LdapConnection ldapConn = null;

		try
		{

			ldapConn = new LdapConnection();
			
			ldapConn.open(ldapCfg);
			
			token = authenticateUser(dbConn, ldapConn, authConfig, name,
											 pwd, cntsTriesNum, entidad);
			ldapConn.close();

			return token;

		}
		 catch(Exception e)
		{

			LdapConnection.ensureClose(ldapConn, e);

			return token;

		}		

	}
	
	public static UasAuthToken authenticateUser(DbConnection dbConn, LdapConnection ldapConn,
												           UasAuthConfig authConfig,
												           String name, String pwd,
												           int cntsTriesNum, String entidad)
									   throws Exception
	{

		UasAuthToken token  = null;
		String		 userDn;

		if((name == null) || name.equals("") || (pwd == null) ||
			   pwd.equals(""))
		{

			throw new IeciTdException(UasBaseError.EC_INVALID_AUTH_SPEC,
									        UasBaseError.EM_INVALID_AUTH_SPEC);
		
		}
			
		checkLoginAttemptCount(authConfig, cntsTriesNum);

		if(authConfig.isUserSearchByDn()) {
			checkValidUserDn(ldapConn, name);
			
			userDn = name;
		}
		else {
		    userDn = findUserDn(ldapConn, authConfig, name, entidad);
		}
		
		checkPasswordValid(ldapConn, userDn, pwd);
		
		token = buildAuthToken(dbConn, ldapConn, authConfig, userDn, name, entidad);

		return token;

	}
	
	public static UasAuthToken authenticateUser(DbConnection dbConn, LdapConnCfg ldapCfg,
												           UasAuthConfig authConfig,
												           String name, String entidad)
									   throws Exception
	{

		UasAuthToken   token    = null;
		LdapConnection ldapConn = null;

		try
		{

			ldapConn = new LdapConnection();
			
			ldapConn.open(ldapCfg);
			
			token = authenticateUser(dbConn, ldapConn, authConfig, name, entidad);
			
			ldapConn.close();

			return token;

		}
		 catch(Exception e)
		{

			LdapConnection.ensureClose(ldapConn, e);

			return token;

		}

	}

	public static UasAuthToken authenticateUser(DbConnection dbConn, LdapConnection ldapConn,
												           UasAuthConfig authConfig,
												           String name, String entidad)
										                 throws Exception
	{

		UasAuthToken token  = null;
		String		 userDn;

		if((name == null) || name.equals(""))
		{

			throw new IeciTdException(UasBaseError.EC_INVALID_AUTH_SPEC,
									        UasBaseError.EM_INVALID_AUTH_SPEC);
			
		}

		if(authConfig.isUserSearchByDn())
		{

			checkValidUserDn(ldapConn, name);
		
			userDn = name;

		}
		else
			userDn = findUserDn(ldapConn, authConfig, name, entidad);

		token = buildAuthToken(dbConn, ldapConn, authConfig, userDn, name, entidad);

		return token;

	}

	private static void checkLoginAttemptCount(UasAuthConfig authConfig,
											             int cntsTriesNum)
							  throws Exception
	{

		if(cntsTriesNum >= authConfig.getMaxNumTries())
		{

			throw new IeciTdException(UasBaseError.EC_TOO_MANY_LOGIN_ATTEMPTS,
									        UasBaseError.EM_TOO_MANY_LOGIN_ATTEMPTS);
			
		}

	}

	private static void checkValidUserDn(LdapConnection ldapConn, String userDn)
								                throws Exception
	{

		String userGuid;

		try
		{

			userGuid = LdapUasFns.findUserGuidByDn(ldapConn, userDn);
		
		}
		 catch(IeciTdException e)
		{

			if(e.getErrorCode().equals(LdapError.EC_INVALID_DN))
			{

            throw new IeciTdException(UasBaseError.EC_INVALID_USER_NAME,
                                      UasBaseError.EM_INVALID_USER_NAME);

				
			}
			else
				throw e;

		}

	}

	private static String findUserDn(LdapConnection ldapConn,
									         UasAuthConfig authConfig, String name, String entidad)
							    throws Exception
	{

		String userDn = null;

		try
		{

			userDn = LdapUasFns.findUserDn(ldapConn, authConfig.getUserStart(),
										          authConfig.getUserScope(),
										          authConfig.getUserAttrName(), name, entidad);

		}
		 catch(IeciTdException e)
		{

			if(e.getErrorCode().equals(LdapError.EC_NOT_FOUND))
			{

            throw new IeciTdException(UasBaseError.EC_INVALID_USER_NAME,
                                      UasBaseError.EM_INVALID_USER_NAME);

				
			}
			else
				throw e;

		}

		return userDn;

	}

	private static void checkPasswordValid(LdapConnection ldapConn,
										            String userDn, String userPwd)
									               throws Exception
	{

		LdapConnection conn = null;

		try
		{

			conn = new LdapConnection();
			
			openUserLdapConnection(conn, ldapConn.getEngine(),
								        ldapConn.getProvider(), ldapConn.getUrl(),
								        userDn, userPwd, ldapConn.getPool(),
								        ldapConn.getPoolTimeout());
			conn.close();

		}
		 catch(Exception e)
		{

			LdapConnection.ensureClose(conn, e);

		}

	}

	private static void openUserLdapConnection(LdapConnection ldapConn,
											             int engine, int provider,
											             String url, String userDn,
											             String userPwd, boolean pool,
											             int poolTimeout)
							  throws Exception
	{

		try
		{

			ldapConn.open(engine, provider, url, userDn, userPwd, pool,
						  poolTimeout);

		}
		 catch(IeciTdException e)
		{

			if(e.getErrorCode().equals(LdapError.EC_INVALID_AUTH_SPEC))
			{
			
			   throw new IeciTdException(UasBaseError.EC_INVALID_AUTH_SPEC,
										        UasBaseError.EM_INVALID_AUTH_SPEC);
			   
			}
			else
				throw e;

		}

	}

	private static int findUser(DbConnection dbConn, String guid, String userName, String entidad) throws Exception
	{

		int userId = 0;


		try {
			userId = UasDaoLdapUserTbl.selectId(dbConn, guid);
		} catch(es.ieci.tecdoc.isicres.admin.base.exception.IeciTdException e){
			 if(e.getErrorCode().equals(DbError.EC_NOT_FOUND)){
				 throw new IeciTdException(UasError.EC_NO_USER_IN_IDOC_SYSTEM,
					        UasError.EM_NO_USER_IN_IDOC_SYSTEM);
			 }
			 else
			 {
				 throw e;
			 }
		} catch (Exception e) {
			throw new IeciTdException(UasError.EC_NO_USER_IN_IDOC_SYSTEM,
			        UasError.EM_NO_USER_IN_IDOC_SYSTEM);
		}

		return userId;

	}
	

	private static int findGroup(DbConnection dbConn, String guid)
							     throws Exception
	{

	   int groupId = -1;

		try
		{

			groupId = UasDaoLdapGroupTbl.selectId(dbConn, guid);

		}
		catch(es.ieci.tecdoc.isicres.admin.base.exception.IeciTdException e)
		{

			if(e.getErrorCode().equals(DbError.EC_NOT_FOUND))
				groupId = -1;
			else
				throw e;

		}

		return groupId;
	}

	private static UasAuthToken buildAuthToken(DbConnection dbConn, LdapConnection ldapConn,
											             UasAuthConfig authConfig,
											             String userDn,
											             String userName,
											             String entidad)
										 throws Exception
	{

		UasAuthToken			    token;
		String					    userGuid;
		String					    groupGuid;
		IeciTdShortTextArrayList groupGuids;
		int						    count;
		int						    i;
		int						    userId;
		int						    groupId = 0;
		
		token    = new UasAuthToken();
		userGuid = LdapUasFns.findUserGuidByDn(ldapConn, userDn);
		// userId   = findUser(dbConn, userGuid, userDn, entidad);
		// Se ha modificado el paso del parámetro userDn por el de userName, ya que se utiliza para el caso en el que el usuario no existe
		// y se da de alta dentro de la función y para ello debe usar el userName, no el userDn
		userId   = findUser(dbConn, userGuid, userName, entidad);
		
		token.setUser(userId);

		groupGuids = LdapUasFns.findUserGroupGuids(ldapConn,
												   authConfig.getGroupStart(),
												   authConfig.getGroupScope(),
												   userDn);
		count	   = groupGuids.count();

		for(i = 0; i < count; i++)
		{

			groupGuid = groupGuids.get(i);

         groupId = findGroup(dbConn, groupGuid);

			if(groupId != -1)
				token.addGroup(groupId);

		}

		return token;

	}
	
}
 // class
