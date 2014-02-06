package ieci.tecdoc.sbo.uas.ldap;

import java.sql.Connection;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;


public final class UasBnoAuthEx
{

	//~ Constructors -----------------------------------------------------------

	private UasBnoAuthEx(){}
	
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
	public static UasAuthToken authenticateUser(Connection jdbcCnt, String name,
                                               String pwd, int cntsTriesNum)
									   throws Exception
	{

		UasAuthToken   token    = null;		
		LdapConnCfg    ldapCfg  = null;
		UasAuthConfig  authCfg  = null;

		try
		{

			DbConnection.open(jdbcCnt);
			
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
	
	/**
	 * @autor IECISA
	 * 
	 * @param dbConConfig		Objeto que contiene la información de la conexión directa a BBDD sin
	 * 								utilizar el pool de conexiones del Servidor de Aplicaciones
	 * @param ldapCfg			Objeto que contiene la información de la conexión directa al servidor
	 * 								de LDAP
	 * @param authConfig 
	 * @param name 				Login del usuario
	 * @param pwd 				Password introducida por el usuario al logarse
	 * @param cntsTriesNum 		Número de intentos de login que ha realizado el usuario 
	 * @return UasAuthToken       	Información completa del usuario
	 *
	 * @throws 		 			Exception if the application business logic throws an exception
	 * 
	 * @since V1.0
	 */
	public static UasAuthToken authenticateUser(DbConnectionConfig dbConConfig,
												           LdapConnCfg ldapCfg,
												           UasAuthConfig authConfig,
												           String name, String pwd,
												           int cntsTriesNum)
									   throws Exception
	{

		UasAuthToken   token    = null;		

		try
		{

			DbConnection.open(dbConConfig);
			
			token = UasMdoAuth.authenticateUser(ldapCfg, authConfig, name, pwd,
									                  cntsTriesNum);
			DbConnection.close();

			return token;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return token;

		}

	}
	
	public static UasAuthToken authenticateUser(Connection jdbcCnt, String name)
										 throws Exception
	{

		UasAuthToken   token    = null;		
		LdapConnCfg    ldapCfg  = null;
		UasAuthConfig  authCfg  = null;

		try
		{

			DbConnection.open(jdbcCnt);
			
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

	public static UasAuthToken authenticateUser(DbConnectionConfig dbConConfig,
												           LdapConnCfg ldapCfg,
												           UasAuthConfig authConfig,
												           String name)
									   throws Exception
	{

		UasAuthToken   token = null;	

		try
		{

			DbConnection.open(dbConConfig);
			
			token = UasMdoAuth.authenticateUser(ldapCfg, authConfig, name);
			
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
