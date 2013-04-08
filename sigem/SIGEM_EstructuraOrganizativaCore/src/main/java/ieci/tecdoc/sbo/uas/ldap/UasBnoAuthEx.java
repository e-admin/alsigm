package ieci.tecdoc.sbo.uas.ldap;

import java.sql.Connection;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sgm.base.dbex.DbConnection;


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
                                               String pwd, int cntsTriesNum, String entidad)
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
												           int cntsTriesNum, String entidad)
									   throws Exception
	{

		UasAuthToken   token    = null;		

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));	
			
			token = UasMdoAuth.authenticateUser(dbConn, ldapCfg, authConfig, name, pwd,
									                  cntsTriesNum, entidad);

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

	public static UasAuthToken authenticateUser(DbConnectionConfig dbConConfig,
												           LdapConnCfg ldapCfg,
												           UasAuthConfig authConfig,
												           String name, String entidad)
									   throws Exception
	{

		UasAuthToken   token = null;	

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			
			token = UasMdoAuth.authenticateUser(dbConn, ldapCfg, authConfig, name, entidad);

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
