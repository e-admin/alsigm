package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.LoginErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProducts;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.login.LoginManager;
import ieci.tecdoc.sbo.idoc.login.LoginMethod;
import org.apache.log4j.Logger;

/**
 * Implementación de la clase Login. <br>Maneja la funcionalidad de acceso al 
 * sistema de administración de invesDoc.
 */
 
public class LoginImpl 
{
   
   public static AcsAccessToken doLogin(String name, String password, 
                                        int productId, int numTries)
                                        throws Exception
   {
      AcsAccessToken token = null;
      int method;

      if (_logger.isDebugEnabled())
         _logger.debug("doLogin");
      
      try
      {
			DbConnection.open(CfgMdoConfig.getDbConfig());

         method = LoginManager.getLoginMethod();
         
         switch (method)
         {
            case LoginMethod.STANDARD:
            {
               token = doLoginStd(name, password, productId, numTries);
               break;
            }
            case LoginMethod.LDAP:
            {
               token = doLoginLdap(name, password, productId, numTries);
               break;
            }
            case LoginMethod.SSO_LDAP:
            {
               token = doLoginSsoLdap(name, productId);
               break;
            }
            default:
            {
               AdminException.throwException(LoginErrorCodes.EC_INVALID_PRODUCT_ID);
            }
         }
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;         
      }
      finally
      {
         DbConnection.close();
      }
      
      return token;
   }
   
   public static AcsAccessTokenProducts doAdmLogin(String name, String password,
         													  int numTries)
   												throws Exception
  	{
      
      AcsAccessTokenProducts token = null;
      int method;

      if (_logger.isDebugEnabled())
         _logger.debug("doAdmLogin");
      
      try
      {
			DbConnection.open(CfgMdoConfig.getDbConfig());

         method = LoginManager.getLoginMethod();
         
         switch (method)
         {
            case LoginMethod.STANDARD:
            {
               token = LoginManager.doAdmLoginStd(name, password, numTries);
               break;
            }
            case LoginMethod.LDAP:
            {
               token = LoginManager.doAdminLoginLdap(name, password, numTries);
               break;
            }
            case LoginMethod.SSO_LDAP:
            {
               token = LoginManager.doAdmSsoLoginLdap(name);
               break;
            }
            default:
            {
               AdminException.throwException(LoginErrorCodes.EC_INVALID_PRODUCT_ID);
            }
         }
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;         
      }
      finally
      {
         DbConnection.close();
      }
      
      return token;
      
   }

   private static AcsAccessToken doLoginStd(String name, String password, 
                                            int productId, int numTries)
                                            throws Exception
   {
      AcsAccessToken token = null;

      if (_logger.isDebugEnabled())
         _logger.debug("doLoginStd");
         
      switch (productId)
      {
         case UserDefs.PRODUCT_SYSTEM:
         {
            token = LoginManager.iDocAdmAppDoLoginStd(name, password, numTries);
            break;
         }
         case UserDefs.PRODUCT_USER:
         {
            token = LoginManager.iUserAdmAppDoLoginStd(name, password, numTries);
            break;
         }
         case UserDefs.PRODUCT_VOLUME:
         {
            token = LoginManager.iVolAdmAppDoLoginStd(name, password, numTries);
            break;
         }
         default:
         {
            AdminException.throwException(LoginErrorCodes.EC_INVALID_PRODUCT_ID);
         }
      }
         
      return token;
   }
 
   private static AcsAccessToken doLoginLdap(String name, String password, 
                                             int productId, int numTries)
                                             throws Exception
   {
      AcsAccessToken token = null;

      if (_logger.isDebugEnabled())
         _logger.debug("doLoginLdap");
         
      switch (productId)
      {
         case UserDefs.PRODUCT_SYSTEM:
         {
            token = LoginManager.iDocAdmAppDoLoginLdap(name, password, numTries);
            break;
         }
         case UserDefs.PRODUCT_USER:
         {
            token = LoginManager.iUserAdmAppDoLoginLdap(name, password, numTries);
            break;
         }
         case UserDefs.PRODUCT_VOLUME:
         {
            token = LoginManager.iVolAdmAppDoLoginLdap(name, password, numTries);
            break;
         }
         default:
         {
            AdminException.throwException(LoginErrorCodes.EC_INVALID_PRODUCT_ID);
         }
      }
         
      return token;
   }
 
   private static AcsAccessToken doLoginSsoLdap(String name, int productId)
                                                throws Exception
   {
      AcsAccessToken token = null;

      if (_logger.isDebugEnabled())
         _logger.debug("doLoginSsoLdap");
         
      switch (productId)
      {
         case UserDefs.PRODUCT_SYSTEM:
         {
            token = LoginManager.iDocAdmAppDoSsoLoginLdap(name);
            break;
         }
         case UserDefs.PRODUCT_USER:
         {
            token = LoginManager.iUserAdmAppDoSsoLoginLdap(name);
            break;
         }
         case UserDefs.PRODUCT_VOLUME:
         {
            token = LoginManager.iVolAdmAppDoSsoLoginLdap(name);
            break;
         }
         default:
         {
            AdminException.throwException(LoginErrorCodes.EC_INVALID_PRODUCT_ID);
         }
      }
         
      return token;
   }
 
   private static final Logger _logger = Logger.getLogger(LoginImpl.class);

}