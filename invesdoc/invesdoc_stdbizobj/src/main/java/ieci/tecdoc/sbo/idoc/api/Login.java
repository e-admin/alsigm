
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.idoc.login.LoginManager;
import ieci.tecdoc.sbo.idoc.login.LoginMethod;

/**
 * Gestor de acceso. Esta clase proporciona la funcionalidad
 * básica para establecer una sesión. Esta sesión puede establecerse
 * contra diferentes sistemas:
 * <li> Estándar: los usuarios se encuentran registrados en el sistema invesdoc
 * <li> Ldap: los usuarios se encuentran registrados en un directorio
 * <li> SSOLdap: Single Sign On
 */

public final class Login
{
   /** 
    * Configuración de la base de datos donde se encuentra los usuarios 
    * invesdoc.
    */
   DbConnectionConfig m_dbConnConfig;
   
   /** 
    * Directorio donde se encuentra el fichero con la configuración de
    * la base de datos invesdoc
    */
   String             m_configDir;
   
   /**
    * Constructor
    * @throws Exception
    */
   public Login() throws Exception
   {
      m_dbConnConfig = null;
      m_configDir    = null;
   }
   
   /**
    * Construtor
    * @param configDir configuración de la base de datos invesdoc
    * @throws Exception
    */
   public Login(String configDir) throws Exception
   {      
      m_dbConnConfig = null;
      m_configDir    = configDir;          
   }

   /**
    * Devuelve la configuración de la base de datos donde se encuentran
    * usuarios invesdoc. 
    * @return
    * @throws Exception
    */
   private DbConnectionConfig getDbConfig() throws Exception
   {
      if (m_dbConnConfig == null)
      {
         if (m_configDir == null)
         {
            m_dbConnConfig = CfgMdoConfig.getDbConfig();
         }
         else
         {
            m_dbConnConfig = CfgMdoConfig.getDbConfig(m_configDir);
         }
      }
      
      return  m_dbConnConfig;
   }
   
   /**
    * Establece una configuración de base de datos. Esta configuración
    * corresponderá con la de la base de datos donde se encuentren los
    * usuarios invesdoc.
    * @param dbConnConfig Configuración de base de datos
    * @throws Exception
    */
   public void setConnectionConfig(DbConnectionConfig dbConnConfig)
               throws Exception
   {
      m_dbConnConfig = dbConnConfig;
   }
   
   /**
    * Devuelve el método bajo el cual se establece la sesión. 
    * @return Método bajo el cual se establece la sesión. Los métodos vienen
    * definidos en la clase LoginMethod:
    * <li> LoginMethod.STANDARD
    * <li> LoginMethod.LDAP
    * <li> LoginMethod.SSO_LDAP
    * @throws Exception
    * @see LoginMethod
    */
   public int getLoginMethod() throws Exception
   {
      int login = 0;
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         login = LoginManager.getLoginMethod();

         DbConnection.close();
         
         return login;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return login;
      }
            
   }   
   
   /**
    * Lleva a cabo el establecimiento de sesión estándar
    * @param name nombre del usuario
    * @param pwd contraseña del usuario
    * @param cntsTriesNum número de intentos en el establecimiento de sesión
    * @return referencia a un objeto de tipo AcsAccessObject que contiene información
    * básica del usuario
    * @throws Exception si se produce un error en el establecimiento de la sesión
    * @see AcsAccessObject
    */
   public AcsAccessObject doLoginStd(String name, String pwd,
												int cntsTriesNum) 
								 throws Exception
   {
      
      AcsAccessToken  accessToken = null;
      AcsAccessObject acs         = null; 
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         accessToken = LoginManager.doLoginStd(name, pwd, cntsTriesNum);

         DbConnection.close();

         acs = new AcsAccessObject(accessToken);
         
         return acs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Lleva a cabo el establecimiento de sesión contra directorio ldap. 
    * @param name - nombre del usuario
    * @param pwd - contraseña del usuario
    * @param cntsTriesNum - número de intentos en el establecimiento de sesión
    * @return referencia a un objeto de tipo AcsAccessObject que contiene información
    * básica del usuario
    * @throws Exception - si se produce un error en el establecimiento de la sesión
    * @see AcsAccessObject
    */
   public AcsAccessObject doLoginLdap(String name, String pwd,
												  int cntsTriesNum) throws Exception
   {
      
      AcsAccessToken  accessToken = null; 
      AcsAccessObject acs         = null; 
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         accessToken = LoginManager.doLoginLdap(name, pwd, cntsTriesNum);

         DbConnection.close();

         acs = new AcsAccessObject(accessToken);
         
         return acs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }     
            
   }
   
   /**
    * Lleva a cabo el establecimiento de sesión Single Sign On. 
    * @param name - nombre del usuario
    * @return referencia a un objeto de tipo AcsAccessObject que contiene información
    * básica del usuario
    * @throws Exception - si se produce un error en el establecimiento de la sesión
    * @see AcsAccessObject
    */

   public AcsAccessObject doSsoLoginLdap(String name)
								  throws Exception
   {
      
      AcsAccessToken  accessToken = null; 
      AcsAccessObject acs         = null; 
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         accessToken = LoginManager.doSsoLoginLdap(name);

         DbConnection.close();

         acs = new AcsAccessObject(accessToken);
         
         return acs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }       
            
   }
   
   /**
    * Lleva a cabo el establecimiento de sesión con pool de usuarios
    * de un grupo
    * @return referencia a un objeto de tipo AcsAccessObject que contiene información
    * básica del usuario
    * @throws Exception si se produce un error en el establecimiento de la sesión
    * @see AcsAccessObject
    */
   public AcsAccessObject doDirectLoginStd(int groupId) 
								 throws Exception
   {
      
      AcsAccessToken  accessToken = null;
      AcsAccessObject acs         = null; 
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         accessToken = LoginManager.doDirectLoginStd(groupId);

         DbConnection.close();

         acs = new AcsAccessObject(accessToken);
         
         return acs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Lleva a cabo el establecimiento de sesión con pool de usuarios
    * de un grupo
    * @return referencia a un objeto de tipo AcsAccessObject que contiene información
    * básica del usuario
    * @throws Exception si se produce un error en el establecimiento de la sesión
    * @see AcsAccessObject
    */
   public AcsAccessObject doDirectLoginStd() 
								 throws Exception
   {
      
      AcsAccessToken  accessToken = null;
      AcsAccessObject acs         = null; 
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         accessToken = LoginManager.doDirectLoginStd();

         DbConnection.close();

         acs = new AcsAccessObject(accessToken);
         
         return acs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Lleva a cabo la liberación del usario de un pool de usuarios
    * de un grupo
    *  
    * @throws Exception si se produce un error en el establecimiento de la sesión
    * 
    */
   public void doDirectLogoutStd(int userId,int groupId) 
					throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());		        

         LoginManager.doDirectLogoutStd(userId, groupId);

         DbConnection.close();

                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
         
      }
            
   }
   
   /**
    * Lleva a cabo la liberación del usario de un pool de usuarios
    * de un grupo
    *  
    * @throws Exception si se produce un error en el establecimiento de la sesión
    * 
    */
   public void doDirectLogoutStd(int userId) 
					throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());		        

         LoginManager.doDirectLogoutStd(userId);

         DbConnection.close();

                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
         
      }
            
   }
   
   
   
} // class
