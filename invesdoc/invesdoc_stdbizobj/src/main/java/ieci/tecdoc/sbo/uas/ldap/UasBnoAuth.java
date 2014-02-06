package ieci.tecdoc.sbo.uas.ldap;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.ldap.LdapConnCfg;

import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;


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
												           int cntsTriesNum)
									   throws Exception
	{

		UasAuthToken   token    = null;		
		LdapConnCfg    ldapCfg  = null;
		UasAuthConfig  authCfg  = null;

		try
		{

			DbConnection.open(CfgMdoConfig.getDbConfig());
			
			ldapCfg = UasConfigUtil.createLdapConnConfig();
			authCfg = UasConfigUtil.createUasAuthConfig();			
			
			token = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name, 
			                                    pwd, cntsTriesNum);
			
			DbConnection.close();

			return token;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return token;

		}

	}	
	
	public static UasAuthToken authenticateUser(String name)
										 throws Exception
	{

		UasAuthToken   token    = null;		
		LdapConnCfg    ldapCfg  = null;
		UasAuthConfig  authCfg  = null;

		try
		{

			DbConnection.open(CfgMdoConfig.getDbConfig());
			
			ldapCfg = UasConfigUtil.createLdapConnConfig();
			authCfg = UasConfigUtil.createUasAuthConfig();	
			
			token = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name);
			
			DbConnection.close();

			return token;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return token;

		}

	}
	
}
 // class
