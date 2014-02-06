
package ieci.tecdoc.sbo.uas.ldap;

import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapProvider;
import ieci.tecdoc.core.ldap.LdapScope;
import ieci.tecdoc.core.xml.lite.XmlTextParser;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.sbo.config.CfgMisc;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.config.CfgLdapConfig;
import org.apache.log4j.Logger;
import java.io.File;

public final class UasConfigUtil
{
   private static final String EN_ROOT                 = "Config";

   private static final String EN_CONNECTION           = "Connection";
   private static final String EN_CONN_PROVIDER        = "Provider";
   private static final String EN_CONN_POOLING         = "Pooling";
   private static final String EN_CONN_POOLING_TIMEOUT = "Pooling_TimeOut";

   private static final String EN_AUTH_CONFIG                    = "Auth_Config";
   private static final String EN_AUTH_CONFIG_MAXNUMTRIES        = "MaxNumTries";
   private static final String EN_AUTH_CONFIG_USER_SEARCH_BY_DN  = "User_Search_By_Dn";
   private static final String EN_AUTH_CONFIG_USER_START         = "User_Start";
   private static final String EN_AUTH_CONFIG_USER_SCOPE         = "User_Scope";
   private static final String EN_AUTH_CONFIG_USER_ATTRN         = "User_Attribute";
   private static final String EN_AUTH_CONFIG_GROUP_START        = "Group_Start";
   private static final String EN_AUTH_CONFIG_GROUP_SCOPE        = "Group_Scope";

    private static final String IECI_LDAP_CFG_FILE_NAME = "IeciTd_LdapConn_Cfg.xml";


   private static final Logger log = Logger.getLogger(UasConfigUtil.class);

      
   private UasConfigUtil()
   {
   } 

   
   public static LdapConnCfg createLdapConnConfig()
                             throws Exception
   {
      
      LdapConnCfg   connCfg = null; 
      CfgLdapConfig ldapCfg = null; 
      String        fileLoc;
      String        fileText = null;     
      String        fileName;
      
      ldapCfg  = CfgMdoConfig.loadDbLdapCfg();

      fileName = IECI_LDAP_CFG_FILE_NAME;
      
      fileLoc = CfgMisc.getConfigFilePath(fileName);

      try
      {      
         if (fileLoc != null)
            fileText =  FileManager.readStringFromFile(fileLoc);
         else
            fileText =  FileManager.readStringFromResourceFile(fileName);
         
      }
      catch(Exception e)
      {
          if (log.isDebugEnabled())
            log.debug("Leyendo fichero de configuración: "
                       + e.getMessage());

          fileText = null;
      }   

      connCfg =  createLdapConnConfig(ldapCfg, fileText);   
         
      return connCfg;
      
   }    

   public static LdapConnCfg createLdapConnConfig(String configDir)
                             throws Exception
   {
      
      LdapConnCfg   connCfg = null; 
      CfgLdapConfig ldapCfg = null; 
      String        fileText = null;    
     
      String        fileLoc;

      fileLoc = configDir + File.separator + IECI_LDAP_CFG_FILE_NAME;
      
      ldapCfg  = CfgMdoConfig.loadDbLdapCfg();

      try
      {      
         fileText =  FileManager.readStringFromFile(fileLoc);  
      }
      catch(Exception e)
      {
          if (log.isDebugEnabled())
            log.debug("Leyendo fichero de configuración: "
                       + e.getMessage());

          fileText = null;
      }   

      connCfg =  createLdapConnConfig(ldapCfg, fileText);   
         
      return connCfg;
      
   }     
 
   private static LdapConnCfg createLdapConnConfig(CfgLdapConfig ldapCfg,
                                                   String configFileText)
                              throws Exception
   {
      
      LdapConnCfg   connCfg = null; 
      int           engine, provider;     
      String        url, user, pwd, value;
      int           poolTO;
      boolean       usePool; 
      XmlTextParser xtp;
           
      ldapCfg  = CfgMdoConfig.loadDbLdapCfg();
      
      provider = LdapProvider.SUN;      
      usePool  = false;
      poolTO   = 0;  

      if (configFileText != null)
      {

         try
         {      
            xtp = new XmlTextParser();
         
            xtp.createFromStringText(configFileText);
   
            xtp.selectElement(EN_ROOT);
         
            xtp.selectElement(EN_CONNECTION);
   
            value = readElementValue(xtp, EN_CONN_PROVIDER);
            if (value != null)
               provider = Integer.parseInt(value);
   
            value = readElementValue(xtp, EN_CONN_POOLING);
            if (value != null)
            {
               if(value.equals("N"))
                  usePool = false;
               else
                  usePool = true;
            }
     
            value = readElementValue(xtp, EN_CONN_POOLING_TIMEOUT);
            if (value != null)
               poolTO = Integer.parseInt(value);         
         }
         catch(Exception e)
         {
            if (log.isDebugEnabled())
               log.debug("UasConfigUtil.createLdapConnConfig: " + e.getMessage());
         }

      } 

      engine   = ldapCfg.getEngine();
      url      = formatUrl(ldapCfg);
      user     = ldapCfg.getUser();
      pwd      = ldapCfg.getPwd();
      
      connCfg = new LdapConnCfg(engine, provider, url, user,
                                pwd, usePool, poolTO);
      
         
      return connCfg;
      
   }     

   public static UasAuthConfig createUasAuthConfig(String configDir)
                               throws Exception
   {
      
      UasAuthConfig cfg = null;
      String        fileLoc, fileText;
      XmlTextParser xtp = null;

      fileLoc = configDir + File.separator + IECI_LDAP_CFG_FILE_NAME;
      
      try
      {      
         fileText = FileManager.readStringFromFile(fileLoc);
         
         xtp = new XmlTextParser();
      
         xtp.createFromStringText(fileText);

         cfg = createUasAuthConfigFromText(xtp);
      }
      catch(Exception e)
      {
          if (log.isDebugEnabled())
            log.debug("UasConfigUtil.createUasAuthConfig: " + e.getMessage());

          cfg = createDefaultUasAuthConfig();
      }
      
      return cfg;
          
   }

   public static UasAuthConfig createUasAuthConfig()
                               throws Exception
   {
      
      UasAuthConfig cfg = null;
      String        fileLoc;
      String        fileText;
      XmlTextParser xtp;
      String        fileName;

      fileName = IECI_LDAP_CFG_FILE_NAME;
      
      fileLoc = CfgMisc.getConfigFilePath(fileName);

      try
      {      
         if (fileLoc != null)
            fileText =  FileManager.readStringFromFile(fileLoc);
         else
            fileText =  FileManager.readStringFromResourceFile(fileName);

         xtp = new XmlTextParser();
      
         xtp.createFromStringText(fileText);

         cfg = createUasAuthConfigFromText(xtp);
      }
      catch(Exception e)
      {
          if (log.isDebugEnabled())
            log.debug("UasConfigUtil.createUasAuthConfig: " + e.getMessage());

          cfg = createDefaultUasAuthConfig();
      }
      
      return cfg;
      
   }  
   
   private static UasAuthConfig createUasAuthConfigFromText(XmlTextParser xtp)
                               throws Exception
   {
      
      UasAuthConfig uasAuthCfg; 
      boolean       byDn;
      String        userAttr, userStart, groupStart;
      int           maxNumTries, userScope, groupScope;
      boolean       find;
      String        value;
      
      uasAuthCfg  = createDefaultUasAuthConfig();

      xtp.selectElement(EN_ROOT);
      
      xtp.selectElement(EN_AUTH_CONFIG);            

      value = readElementValue(xtp, EN_AUTH_CONFIG_MAXNUMTRIES);
      if (value != null)
      {
         maxNumTries = Integer.parseInt(value);
         uasAuthCfg.setMaxNumTries(maxNumTries); 
      }

      value = readElementValue(xtp, EN_AUTH_CONFIG_USER_SEARCH_BY_DN);
      if (value != null)
      {
         if(value.equals(UasDefs.SEARCH_MUST_NOT_BE_BY_DN))
            byDn = false;
         else
            byDn = true;
         
         uasAuthCfg.setUserSearchByDn(byDn);    
      }

      value = readElementValue(xtp, EN_AUTH_CONFIG_USER_START);
      if (value != null)
      {
         userStart = value;
         uasAuthCfg.setUserStart(userStart);   
      }

      value = readElementValue(xtp, EN_AUTH_CONFIG_USER_SCOPE);
      if (value != null)
      {
         userScope = Integer.parseInt(value);
         uasAuthCfg.setUserScope(userScope);          
      }

      value = readElementValue(xtp, EN_AUTH_CONFIG_USER_ATTRN);
      if (value != null)
      {
         userAttr = value;
         uasAuthCfg.setUserAttrName(userAttr);          
      }

      value = readElementValue(xtp, EN_AUTH_CONFIG_GROUP_START);
      if (value != null)
      {
         groupStart = value;
         uasAuthCfg.setGroupStart(groupStart);   
      }

      value = readElementValue(xtp, EN_AUTH_CONFIG_GROUP_SCOPE);
      if (value != null)
      {
         groupScope = Integer.parseInt(value); 
         uasAuthCfg.setGroupScope(groupScope);  
      }

      
      return uasAuthCfg;
      
   }   

   private static String readElementValue(XmlTextParser xtp, String elementName)
   {
      String value = null;

      try
      {
         xtp.selectElement(elementName);
         value = xtp.getElementValue(); 
      }
      catch (Exception e)
      {
         if (log.isDebugEnabled())
            log.debug("UasConfigUtil.readElementValue: " + e.getMessage());

      }
         
      return value;
      
   }   

   private static UasAuthConfig createDefaultUasAuthConfig()
                               throws Exception
   {
      
      UasAuthConfig uasAuthCfg; 
      boolean       byDn;
      String        userAttr, userStart, groupStart;
      int           maxNumTries, userScope, groupScope;
      
      maxNumTries = 3;
      byDn        = true;
      userAttr    = "cn";
      userStart   = "";
      userScope   = LdapScope.SUBTREE;
      groupStart  = "";
      groupScope  = LdapScope.SUBTREE;
      
      uasAuthCfg  = new UasAuthConfig();      
      
      uasAuthCfg.setMaxNumTries(maxNumTries);      
      uasAuthCfg.setUserSearchByDn(byDn);         
      uasAuthCfg.setUserStart(userStart);      
      uasAuthCfg.setUserScope(userScope);
      uasAuthCfg.setUserAttrName(userAttr); 
      uasAuthCfg.setGroupStart(groupStart);      
      uasAuthCfg.setGroupScope(groupScope);      
         
      return uasAuthCfg;
      
   } 

   
   private static String formatUrl(CfgLdapConfig ldapCfg)
                         throws Exception
   {

      String       url;
	  StringBuffer buf = new StringBuffer();
	  
	  buf.append("ldap://");
	  buf.append(ldapCfg.getServer());
	  buf.append(":");
	  buf.append(ldapCfg.getPort());
	  buf.append("/");
	  buf.append(ldapCfg.getRoot());
	
	  url = buf.toString();

      return url;

   }

   
     
} // class
