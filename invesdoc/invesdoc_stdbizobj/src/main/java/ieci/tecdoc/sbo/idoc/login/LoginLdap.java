
package ieci.tecdoc.sbo.idoc.login;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProduct;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProducts;
import ieci.tecdoc.sbo.acs.base.AcsProduct;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.uas.ldap.UasMdoAuth;
import ieci.tecdoc.sbo.acs.std.AcsMdoAccessToken;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;
import ieci.tecdoc.sbo.uas.ldap.UasAuthConfig;

public final class LoginLdap
{

   private LoginLdap()
   {
   }
   
   public static AcsAccessToken doLogin(String name, String pwd,
										          int cntsTriesNum)
							           throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name,
                                              pwd, cntsTriesNum);
         
      accessToken = AcsMdoAccessToken.createiDocAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriDocApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }
   
   public static AcsAccessToken doSsoLogin(String name)
								        throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name);
         
      accessToken = AcsMdoAccessToken.createiDocAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriDocApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }  

   public static AcsAccessToken iDocAdmAppDoLogin(String name, String pwd,
										                    int cntsTriesNum)
							           throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name,
                                              pwd, cntsTriesNum);
         
      accessToken = AcsMdoAccessToken.createiDocAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriDocAdmApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }
   
   public static AcsAccessToken iDocAdmAppDoSsoLogin(String name)
								        throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name);
         
      accessToken = AcsMdoAccessToken.createiDocAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriDocAdmApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }      

   public static AcsAccessToken iUserAdmAppDoLogin(String name, String pwd,
										                     int cntsTriesNum)
							           throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name,
                                              pwd, cntsTriesNum);
         
      accessToken = AcsMdoAccessToken.createiUserAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriUserAdmApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }
   
   public static AcsAccessToken iUserAdmAppDoSsoLogin(String name)
								        throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name);
         
      accessToken = AcsMdoAccessToken.createiUserAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriUserAdmApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }      

   public static AcsAccessToken iVolAdmAppDoLogin(String name, String pwd,
										                    int cntsTriesNum)
							           throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name,
                                              pwd, cntsTriesNum);
         
      accessToken = AcsMdoAccessToken.createiVolAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriVolAdmApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }
   
   public static AcsAccessToken iVolAdmAppDoSsoLogin(String name)
								        throws Exception
   {
      
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name);
         
      accessToken = AcsMdoAccessToken.createiVolAccessToken(authToken);
         
      hasAccess = LoginMdoAuth.hasAuthEnteriVolAdmApp(accessToken);
         
      if (!hasAccess)    
      {
      
         throw new IeciTdException(LoginError.EC_NOT_AUTH_ENTER_APP,
                                   LoginError.EM_NOT_AUTH_ENTER_APP);          
      }               
      
      return accessToken;
      
   }  
   
   public static AcsAccessTokenProducts doAdmLogin(String name, String pwd,
         														int cntsTriesNum)
   												throws Exception
   {
      AcsAccessTokenProducts acsTokenProducts = new AcsAccessTokenProducts();
      AcsAccessTokenProduct acsTokenProduct = null;
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name,
                                              pwd, cntsTriesNum);
      
      accessToken = AcsMdoAccessToken.createiDocAccessToken(authToken);      
      hasAccess = LoginMdoAuth.hasAuthEnteriDocAdmApp(accessToken);
      
      acsTokenProduct = new AcsAccessTokenProduct();
      acsTokenProduct.setAcsAccessToken(accessToken);
      acsTokenProduct.setProductId(AcsProduct.ISYS);
      acsTokenProduct.setHasAccess(hasAccess);
      
      acsTokenProducts.add(acsTokenProduct);
      
      accessToken = AcsMdoAccessToken.createiUserAccessToken(authToken);      
      hasAccess = LoginMdoAuth.hasAuthEnteriUserAdmApp(accessToken);
      
      acsTokenProduct = new AcsAccessTokenProduct();
      acsTokenProduct.setAcsAccessToken(accessToken);
      acsTokenProduct.setProductId(AcsProduct.IUSER);
      acsTokenProduct.setHasAccess(hasAccess);
      
      acsTokenProducts.add(acsTokenProduct);
         
      accessToken = AcsMdoAccessToken.createiVolAccessToken(authToken);         
      hasAccess = LoginMdoAuth.hasAuthEnteriVolAdmApp(accessToken);
      
      acsTokenProduct = new AcsAccessTokenProduct();
      acsTokenProduct.setAcsAccessToken(accessToken);
      acsTokenProduct.setProductId(AcsProduct.IVOL);
      acsTokenProduct.setHasAccess(hasAccess);
      
      acsTokenProducts.add(acsTokenProduct);
      
      return acsTokenProducts;
   }
   
   public static AcsAccessTokenProducts DoAdmSsoLogin(String name)
                                        throws Exception
   {
      AcsAccessTokenProducts acsTokenProducts = new AcsAccessTokenProducts();
      AcsAccessTokenProduct acsTokenProduct = null;
      UasAuthToken   authToken   = null;
      AcsAccessToken accessToken = null;  
      boolean        hasAccess; 
      LdapConnCfg    ldapCfg     = null;
	   UasAuthConfig  authCfg     = null;
        
      ldapCfg = UasConfigUtil.createLdapConnConfig();
	   authCfg = UasConfigUtil.createUasAuthConfig();				   
                                
      authToken = UasMdoAuth.authenticateUser(ldapCfg, authCfg, name);
      
      accessToken = AcsMdoAccessToken.createiUserAccessToken(authToken);      
      hasAccess = LoginMdoAuth.hasAuthEnteriUserAdmApp(accessToken);
      
      acsTokenProduct = new AcsAccessTokenProduct();
      acsTokenProduct.setAcsAccessToken(accessToken);
      acsTokenProduct.setProductId(AcsProduct.IDOC);
      acsTokenProduct.setHasAccess(hasAccess);
      
      acsTokenProducts.add(acsTokenProduct);
      
      accessToken = AcsMdoAccessToken.createiUserAccessToken(authToken);      
      hasAccess = LoginMdoAuth.hasAuthEnteriUserAdmApp(accessToken);
      
      acsTokenProduct = new AcsAccessTokenProduct();
      acsTokenProduct.setAcsAccessToken(accessToken);
      acsTokenProduct.setProductId(AcsProduct.IUSER);
      acsTokenProduct.setHasAccess(hasAccess);
      
      acsTokenProducts.add(acsTokenProduct);
         
      accessToken = AcsMdoAccessToken.createiVolAccessToken(authToken);         
      hasAccess = LoginMdoAuth.hasAuthEnteriVolAdmApp(accessToken);
      
      acsTokenProduct = new AcsAccessTokenProduct();
      acsTokenProduct.setAcsAccessToken(accessToken);
      acsTokenProduct.setProductId(AcsProduct.IVOL);
      acsTokenProduct.setHasAccess(hasAccess);
      
      acsTokenProducts.add(acsTokenProduct);
      
      return acsTokenProducts;
   }
} // class
