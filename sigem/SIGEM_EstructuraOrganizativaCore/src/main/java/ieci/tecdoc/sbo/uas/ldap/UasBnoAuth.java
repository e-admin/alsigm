package ieci.tecdoc.sbo.uas.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.ldap.LdapConnCfg;

import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sgm.base.dbex.DbConnection;


public final class UasBnoAuth
{

	//~ Constructors -----------------------------------------------------------

	private UasBnoAuth(){}
	
	//~ Methods ----------------------------------------------------------------

	/**
	 * @autor IECISA
	 * 
	 * @param name				 Login del usuario
	 * @param pwd				 Password introducida por el usuario al logarse
	 * @param cntsTriesNum	 Número de intentos de login que ha realizado el usuario
	 * 
	 * @return UasAuthToken	Información completa del usuario
	 * @throws Exception		Exception if the application business logic throws an exception
	 * 
	 * @since V1.0
	 */
	public static UasAuthToken authenticateUser(String name, String pwd,
												           int cntsTriesNum, String entidad)
									   throws Exception
	{

		UasAuthToken   token    = null;		
		LdapConnCfg    ldapCfg  = null;
		UasAuthConfig  authCfg  = null;

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			
			ldapCfg = UasConfigUtil.createLdapConnConfig(entidad);
			authCfg = UasConfigUtil.createUasAuthConfig(entidad);			
			
			token = UasMdoAuth.authenticateUser(dbConn, ldapCfg, authCfg, name, 
			                                    pwd, cntsTriesNum, entidad);
			return token;

		}
		 catch(Exception e)
		{

			return token;
		}finally{
			dbConn.close();
		}

	}	
	
	public static UasAuthToken authenticateUser(String name, String entidad)
										 throws Exception
	{

		UasAuthToken   token    = null;		
		LdapConnCfg    ldapCfg  = null;
		UasAuthConfig  authCfg  = null;

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			
			ldapCfg = UasConfigUtil.createLdapConnConfig(entidad);
			authCfg = UasConfigUtil.createUasAuthConfig(entidad);	
			
			token = UasMdoAuth.authenticateUser(dbConn, ldapCfg, authCfg, name, entidad);

			return token;

		}
		 catch(Exception e)
		{

			return token;

		}finally{
			dbConn.close();
		}

	}
	
}
 // class
