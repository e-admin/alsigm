package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminLoginKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessTokenProducts;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.login.LoginManager;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.login.LoginMethod;

/**
 * Implementación de la clase Login. <br>Maneja la funcionalidad de acceso al
 * sistema de administración de invesDoc.
 */

public class LoginImpl
{

   public static AcsAccessToken doLogin(String name, String password,
                                        int productId, int numTries, String entidad)
                                        throws Exception
   {
      AcsAccessToken token = null;
      int method;

      if (_logger.isDebugEnabled())
         _logger.debug("doLogin");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());

    	  LoginManager loginMetod=new LoginManager();
         method = loginMetod.getLoginMethod(dbConn);

         switch (method)
         {
            case LoginMethod.STANDARD:
            {
               token = doLoginStd(name, password, productId, numTries, entidad);
               break;
            }
            case LoginMethod.LDAP:
            {
               token = doLoginLdap(name, password, productId, numTries, entidad);
               break;
            }
            case LoginMethod.SSO_LDAP:
            {
               token = doLoginSsoLdap(name, productId, entidad);
               break;
            }
            default:
            {
               ISicresAdminBasicException.throwException(ISicresAdminLoginKeys.EC_INVALID_PRODUCT_ID);
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
         dbConn.close();
      }

      return token;
   }

   public static AcsAccessTokenProducts doAdmLogin(String name, String password,
         													  int numTries, String entidad)
   												throws Exception
  	{

      AcsAccessTokenProducts token = null;
      int method;

      if (_logger.isDebugEnabled())
         _logger.debug("doAdmLogin");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());

    	  LoginManager loginMetod=new LoginManager();
         method = loginMetod.getLoginMethod(dbConn);

         switch (method)
         {
            case LoginMethod.STANDARD:
            {
               token = LoginManager.doAdmLoginStd(name, password, numTries, entidad);
               break;
            }
            case LoginMethod.LDAP:
            {
               token = LoginManager.doAdminLoginLdap(name, password, numTries, entidad);
               break;
            }
            case LoginMethod.SSO_LDAP:
            {
               token = LoginManager.doAdmSsoLoginLdap(name, entidad);
               break;
            }
            default:
            {
               ISicresAdminBasicException.throwException(ISicresAdminLoginKeys.EC_INVALID_PRODUCT_ID);
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
         dbConn.close();
      }

      return token;

   }

   private static AcsAccessToken doLoginStd(String name, String password,
                                            int productId, int numTries, String entidad)
                                            throws Exception
   {
      AcsAccessToken token = null;

      if (_logger.isDebugEnabled())
         _logger.debug("doLoginStd");

      switch (productId)
      {
         case ISicresAdminUserDefsKeys.PRODUCT_SYSTEM:
         {
            token = LoginManager.iDocAdmAppDoLoginStd(name, password, numTries, entidad);
            break;
         }
         case ISicresAdminUserDefsKeys.PRODUCT_USER:
         {
            token = LoginManager.iUserAdmAppDoLoginStd(name, password, numTries, entidad);
            break;
         }
         case ISicresAdminUserDefsKeys.PRODUCT_VOLUME:
         {
            token = LoginManager.iVolAdmAppDoLoginStd(name, password, numTries, entidad);
            break;
         }
         default:
         {
            ISicresAdminBasicException.throwException(ISicresAdminLoginKeys.EC_INVALID_PRODUCT_ID);
         }
      }

      return token;
   }

   private static AcsAccessToken doLoginLdap(String name, String password,
                                             int productId, int numTries, String entidad)
                                             throws Exception
   {
      AcsAccessToken token = null;

      if (_logger.isDebugEnabled())
         _logger.debug("doLoginLdap");

      switch (productId)
      {
         case ISicresAdminUserDefsKeys.PRODUCT_SYSTEM:
         {
            token = LoginManager.iDocAdmAppDoLoginLdap(name, password, numTries, entidad);
            break;
         }
         case ISicresAdminUserDefsKeys.PRODUCT_USER:
         {
            token = LoginManager.iUserAdmAppDoLoginLdap(name, password, numTries, entidad);
            break;
         }
         case ISicresAdminUserDefsKeys.PRODUCT_VOLUME:
         {
            token = LoginManager.iVolAdmAppDoLoginLdap(name, password, numTries, entidad);
            break;
         }
         default:
         {
            ISicresAdminBasicException.throwException(ISicresAdminLoginKeys.EC_INVALID_PRODUCT_ID);
         }
      }

      return token;
   }

   private static AcsAccessToken doLoginSsoLdap(String name, int productId, String entidad)
                                                throws Exception
   {
      AcsAccessToken token = null;

      if (_logger.isDebugEnabled())
         _logger.debug("doLoginSsoLdap");

      switch (productId)
      {
         case ISicresAdminUserDefsKeys.PRODUCT_SYSTEM:
         {
            token = LoginManager.iDocAdmAppDoSsoLoginLdap(name, entidad);
            break;
         }
         case ISicresAdminUserDefsKeys.PRODUCT_USER:
         {
            token = LoginManager.iUserAdmAppDoSsoLoginLdap(name, entidad);
            break;
         }
         case ISicresAdminUserDefsKeys.PRODUCT_VOLUME:
         {
            token = LoginManager.iVolAdmAppDoSsoLoginLdap(name, entidad);
            break;
         }
         default:
         {
            ISicresAdminBasicException.throwException(ISicresAdminLoginKeys.EC_INVALID_PRODUCT_ID);
         }
      }

      return token;
   }

   private static final Logger _logger = Logger.getLogger(LoginImpl.class);

}