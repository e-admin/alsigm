package ieci.tecdoc.sbo.idoc.login;

import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.config.CfgLdapConfig;
import ieci.tecdoc.sbo.config.CfgDefs;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProducts;

public final class LoginManager
{

   private LoginManager()
   {
   }

   public static int getLoginMethod() throws Exception
   {
      int           loginMethod = 0;
      CfgLdapConfig ldapCfg     = null;

      ldapCfg = CfgMdoConfig.loadDbLdapCfg();

      if (ldapCfg.getEngine() != CfgDefs.LDAP_ENGINE_NONE)
      {
         if (ldapCfg.isUseOSAuth())
            loginMethod = LoginMethod.SSO_LDAP;
         else
            loginMethod = LoginMethod.LDAP;
      }
      else
         loginMethod = LoginMethod.STANDARD;

      return loginMethod;
   }

   // **************************************************************************  
  
   // Aplicación cliente invesDoc

   public static AcsAccessToken doLoginStd(String name, String pwd,
                                           int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginStd.doLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken doLoginLdap(String name, String pwd,
                                            int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.doLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken doSsoLoginLdap(String name)
                                throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.doSsoLogin(name);

      return accessToken;

   }
   
   public static AcsAccessToken doDirectLoginStd(int groupId) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginStd.doDirectLogin(groupId);
      
      return accessToken;

   }
   
   public static AcsAccessToken doDirectLoginStd() throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginStd.doDirectLogin();
      
      return accessToken;

   }
   
   public static void doDirectLogoutStd(int userId, int groupId) throws Exception
   {
     
      LoginStd.doDirectLogout(userId, groupId);

   }
   
   public static void doDirectLogoutStd(int userId) throws Exception
   {
     
      LoginStd.doDirectLogout(userId);

   }

   // **************************************************************************  
  
   // Aplicación administrador invesDoc


   public static AcsAccessToken iDocAdmAppDoLoginStd(String name, String pwd,
                                                       int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginStd.iDocAdmAppDoLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken iDocAdmAppDoLoginLdap(String name, String pwd,
                                                        int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.iDocAdmAppDoLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken iDocAdmAppDoSsoLoginLdap(String name)
                                throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.iDocAdmAppDoSsoLogin(name);

      return accessToken;

   }

   // **************************************************************************  
  
   // Aplicación Admistrador de usuarios


   public static AcsAccessToken iUserAdmAppDoLoginStd(String name, String pwd,
                                                       int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginStd.iUserAdmAppDoLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken iUserAdmAppDoLoginLdap(String name, String pwd,
                                                        int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.iUserAdmAppDoLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken iUserAdmAppDoSsoLoginLdap(String name)
                                throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.iUserAdmAppDoSsoLogin(name);

      return accessToken;

   }

    // **************************************************************************  
  
   // Aplicación Admistrador de volúmenes


   public static AcsAccessToken iVolAdmAppDoLoginStd(String name, String pwd,
                                                       int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginStd.iVolAdmAppDoLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken iVolAdmAppDoLoginLdap(String name, String pwd,
                                                        int cntsTriesNum) throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.iVolAdmAppDoLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }

   public static AcsAccessToken iVolAdmAppDoSsoLoginLdap(String name)
                                throws Exception
   {

      AcsAccessToken accessToken;

      accessToken = LoginLdap.iVolAdmAppDoSsoLogin(name);

      return accessToken;

   }
   
   // **********************************************************************
   
   public static AcsAccessTokenProducts doAdmLoginStd(String name, String pwd,
         															int cntsTriesNum) throws Exception
   {

      AcsAccessTokenProducts accessToken;

      accessToken = LoginStd.doAdmLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }
   
   public static AcsAccessTokenProducts doAdminLoginLdap(String name, String pwd,
         																int cntsTriesNum) throws Exception
   {

      AcsAccessTokenProducts accessToken;

      accessToken = LoginLdap.doAdmLogin(name, pwd, cntsTriesNum);

      return accessToken;

   }
   
   public static AcsAccessTokenProducts doAdmSsoLoginLdap(String name)
   												 throws Exception
   {

      AcsAccessTokenProducts accessToken;

      accessToken = LoginLdap.DoAdmSsoLogin(name);

      return accessToken;

   }

} // class
