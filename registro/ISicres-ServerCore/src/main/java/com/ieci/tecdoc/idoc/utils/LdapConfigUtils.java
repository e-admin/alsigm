package com.ieci.tecdoc.idoc.utils;

import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.core.xml.lite.XmlTextParser;
import ieci.tecdoc.sbo.config.CfgMisc;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.idoc.authentication.ldap.LdapConnCfg;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapProvider;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;
import com.ieci.tecdoc.idoc.repository.RepositoryLDAP;

public final class LdapConfigUtils{
   private static final String EN_ROOT                 = "Config";

   private static final String EN_CONNECTION           = "Connection";
   private static final String EN_CONN_PROVIDER        = "Provider";
   private static final String EN_CONN_POOLING         = "Pooling";
   private static final String EN_CONN_POOLING_TIMEOUT = "Pooling_TimeOut";
   private static final String EN_CONNECT_USER_ADMIN_BY_DN = "Connect_User_Admin_By_DN";
   private static final String EN_CONNECT_USER_ADMIN_DN = "Connect_User_Admin_DN";

   

   private static final String EN_AUTH_CONFIG                    = "Auth_Config";
   private static final String EN_AUTH_CONFIG_MAXNUMTRIES        = "MaxNumTries";
   private static final String EN_AUTH_CONFIG_USER_SEARCH_BY_DN  = "User_Search_By_Dn";
   private static final String EN_AUTH_CONFIG_USER_START         = "User_Start";
   private static final String EN_AUTH_CONFIG_USER_SCOPE         = "User_Scope";
   private static final String EN_AUTH_CONFIG_USER_ATTRN         = "User_Attribute";
   private static final String EN_AUTH_CONFIG_GROUP_START        = "Group_Start";
   private static final String EN_AUTH_CONFIG_GROUP_SCOPE        = "Group_Scope";

    private static final String IECI_LDAP_CFG_FILE_NAME = "IeciTd_LdapConn_Cfg.xml";


   private static final Logger log = Logger.getLogger(LdapConfigUtils.class);

      
   private LdapConfigUtils(){  } 

   
   public static LdapConnCfg createLdapConnConfig(LDAPDef ldapDef) throws Exception {
      LdapConnCfg   connCfg = null; 
      
      String        fileLoc;
      String        fileText = null;     
      String        fileName;
      fileName = IECI_LDAP_CFG_FILE_NAME;
      
      fileLoc = CfgMisc.getConfigFilePath(fileName);

      try {      
         if (fileLoc != null)
            fileText =  FileManager.readStringFromFile(fileLoc);
         else
            fileText =  FileManager.readStringFromResourceFile(fileName);
         
      } catch(Exception e) {
          if (log.isDebugEnabled())
            log.debug("Leyendo fichero de configuración: "
                       + e.getMessage());

          fileText = null;
      }   

      connCfg =  createLdapConnConfig(ldapDef, fileText);   
         
      return connCfg;
      
   }    

   /*
   public static LdapConnCfg createLdapConnConfig(String configDir,String entidad)
                             throws Exception
   {
      
      LdapConnCfg   connCfg = null; 
      LDAPDef ldapDef = RepositoryLDAP.getInstance(entidad).getLDAPInfo();
      String        fileText = null;    
     
      String        fileLoc;

      fileLoc = configDir + File.separator + IECI_LDAP_CFG_FILE_NAME;

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

      connCfg =  createLdapConnConfig(ldapDef, fileText);   
         
      return connCfg;
      
   } */    
 
   private static LdapConnCfg createLdapConnConfig(LDAPDef ldapCfg,
                                                   String configFileText)
                              throws Exception
   {
      
      LdapConnCfg   connCfg = null; 
      int           engine, provider;     
      String        user = null;
      String        url, pwd, value;
      int           poolTO;
      String 		connectByDN = null;
      String 		connectUserDN = null;

      boolean       usePool; 
      XmlTextParser xtp;
      
      provider = LdapProvider.SUN;      
      usePool  = false;
      poolTO   = 0;  

      if (configFileText != null) {
         try {      
            xtp = new XmlTextParser();
         
            xtp.createFromStringText(configFileText);
   
            xtp.selectElement(EN_ROOT);
         
            xtp.selectElement(EN_CONNECTION);
   
            value = readElementValue(xtp, EN_CONN_PROVIDER);
            if (value != null)
               provider = Integer.parseInt(value);
   
            value = readElementValue(xtp, EN_CONN_POOLING);
            if (value != null) {
               if(value.equals("N"))
                  usePool = false;
               else
                  usePool = true;
            }
     
            value = readElementValue(xtp, EN_CONN_POOLING_TIMEOUT);
            if (value != null)
               poolTO = Integer.parseInt(value);         

            value = readElementValue(xtp, EN_CONNECT_USER_ADMIN_BY_DN);
            if (value != null)
            	connectByDN = value;         
         
            value = readElementValue(xtp, EN_CONNECT_USER_ADMIN_DN);
            if (value != null)
            	connectUserDN = value;         
         } catch(Exception e) {
            if (log.isDebugEnabled())
               log.debug("UasConfigUtil.createLdapConnConfig: " + e.getMessage());
         }

      } 

      engine   = ldapCfg.getLdapEngine();
      url      = formatUrl(ldapCfg);
      //Dependiendo de connectByDN se construye el user
      if(connectByDN!=null){
          if(connectByDN.equalsIgnoreCase("Y")){
        	  user     = connectUserDN;
          }else if(connectByDN.equalsIgnoreCase("N")){
        	  user = ldapCfg.getLdapUser();
          }else{
        	  user = ldapCfg.getLdapUser();
          }
    	  
      }else{
          log.warn("El fichero de configuracion IeciTd_LdapConn_Cfg.xml está mal configurado, el valor de ConnectBy es null");
          user     = ldapCfg.getLdapUser();
      }
      pwd      = ldapCfg.getLdapPassword();
      
      connCfg = new LdapConnCfg(engine, provider, url, user,
                                pwd, usePool, poolTO);
      
         
      return connCfg;
      
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

   private static String formatUrl(LDAPDef ldapCfg)
                         throws Exception
   {

      String       url;
	  StringBuffer buf = new StringBuffer();
	  
	  buf.append("ldap://");
	  buf.append(ldapCfg.getLdapServer());
	  buf.append(":");
	  buf.append(ldapCfg.getLdapPort());
	
	  url = buf.toString();

      return url;

   }

   
     
} // class


