
package es.ieci.tecdoc.isicres.admin.sbo.uas.ldap;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.ConfigFilePathResolverIsicresAdmin;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.file.FileManager;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapProvider;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapScope;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextParser;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMisc;

import xtom.parser.Element;
import xtom.parser.Parser;
import xtom.parser.XMLTree;

import java.io.File;

public final class UasConfigUtilLdap
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

    private static final String IECI_LDAP_CFG_FILE_NAME = "ISicresAdmin-EstructuraCore/IeciTd_LdapConn_Cfg.xml";


   private static final Logger log = Logger.getLogger(UasConfigUtilLdap.class);


   private UasConfigUtilLdap()
   {
	   System.out.println("=> La entidad está configurada para LDAP");
   }


	public static LdapConnCfg createLdapConnConfig(String entidad)
			throws Exception {

		LdapConnCfg connCfg = null;
		CfgLdapConfig ldapCfg = null;
		String fileLoc;
		String fileText = null;
		String fileName;

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession());
			CfgMdoConfig cfgMdoConfig = new CfgMdoConfig();
			ldapCfg = cfgMdoConfig.loadDbLdapCfg(dbConn);

			fileName = IECI_LDAP_CFG_FILE_NAME;

			// fileLoc = CfgMisc.getConfigFilePath(fileName);
			ConfigFilePathResolverIsicresAdmin pathResolver = ConfigFilePathResolverIsicresAdmin
					.getInstance();
			fileLoc = pathResolver.resolveFullPath(fileName);

			// si el path del fichero es distinto de nulo y ademas no esta
			// dentro de un jar
			if (fileLoc != null && !StringUtils.contains(fileLoc, ".jar!")) {
				fileText = FileManager.readStringFromFile(fileLoc);
			} else {
				fileText = FileManager.readStringFromResourceFile(fileName);
			}

		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("Leyendo fichero de configuración: " + e.getMessage());

			fileText = null;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		connCfg = createLdapConnConfig(ldapCfg, fileText, entidad);

		return connCfg;

	}

   public static LdapConnCfg createLdapConnConfig(String configDir, String entidad)
                             throws Exception
   {

      LdapConnCfg   connCfg = null;
      CfgLdapConfig ldapCfg = null;
      String        fileText = null;

      String        fileLoc;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());

        fileLoc = configDir + File.separator + IECI_LDAP_CFG_FILE_NAME;
        CfgMdoConfig cfgMdoConfig=new CfgMdoConfig();
        ldapCfg  = cfgMdoConfig.loadDbLdapCfg(dbConn);

         fileText =  FileManager.readStringFromFile(fileLoc);
      }
      catch(Exception e)
      {
          if (log.isDebugEnabled())
            log.debug("Leyendo fichero de configuración: "
                       + e.getMessage());

          fileText = null;
      }
      finally
      {
     	 if(dbConn != null) {
     		 dbConn.close();
     	 }
      }
      connCfg =  createLdapConnConfig(ldapCfg, fileText, entidad);

      return connCfg;

   }

   private static LdapConnCfg createLdapConnConfig(CfgLdapConfig ldapCfg,
                                                   String configFileText, String entidad)
                              throws Exception
   {

      LdapConnCfg   connCfg = null;
      int           engine, provider;
      String        url, user, pwd, value;
      int           poolTO;
      boolean       usePool;
      XmlTextParser xtp;

      provider = LdapProvider.SUN;
      usePool  = false;
      poolTO   = 0;

      if (configFileText != null)
      {

    	  DbConnection dbConn=new DbConnection();
    	  try{
    	  	dbConn.open(DBSessionManager.getSession());
            CfgMdoConfig cfgMdoConfig=new CfgMdoConfig();
            ldapCfg  = cfgMdoConfig.loadDbLdapCfg(dbConn);
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
         finally
         {
        	 if(dbConn != null) {
        		 dbConn.close();
        	 }
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

   /**
    * Construye un objeto LdapConnCfg a partir de los datos de conexión con LDAP configurados en fichero y en el objeto ldapCfg pasado como parámetro
    * @param ldapCfg - Configuración LDAP
    * @return - Objeto LdapConnCfg de configuración LDAP
    * @throws Exception
    */
   public static LdapConnCfg createLdapConnConfig(CfgLdapConfig ldapCfg) throws Exception {

	   	LdapConnCfg connCfg = null;

		int           engine, provider;
		String        url, user, pwd;
		int           poolTO;
		boolean       usePool;

		// Se ha omitido  la lectura del fichero IeciTd_LdapConn_Cfg.xml de los datos de pool y timeout debido a que existe un error existente previamente
		// en el parseo del fichero a la espera de que se pueda solucionar, por tanto, al igual que en el caso de que de error, se utilizan
		// los valores por defecto para estos campos y se omite el código que produce el error
		provider = LdapProvider.SUN;
		usePool  = false;
		poolTO   = 0;
		engine   = ldapCfg.getEngine();
		url      = formatUrl(ldapCfg);
		user     = ldapCfg.getUser();
		pwd      = ldapCfg.getPwd();

		connCfg = new LdapConnCfg(engine, provider, url, user,	pwd, usePool, poolTO);

		return connCfg;
   }

   public static UasAuthConfig createUasAuthConfig(String configDir, String entidad)
                               throws Exception
   {

      UasAuthConfig cfg = null;
      String        fileLoc, fileText;

      fileLoc = configDir + File.separator + IECI_LDAP_CFG_FILE_NAME;

      try
      {
         fileText = FileManager.readStringFromFile(fileLoc);

         cfg = createUasAuthConfigFromText(fileText, entidad);
      }
      catch(Exception e)
      {
          if (log.isDebugEnabled())
            log.debug("UasConfigUtil.createUasAuthConfig: " + e.getMessage());

          cfg = createDefaultUasAuthConfig();
      }

      return cfg;

   }

	public static UasAuthConfig createUasAuthConfig(String entidad)
			throws Exception {

		UasAuthConfig cfg = null;
		String fileLoc;
		String fileText;
		String fileName;

		fileName = IECI_LDAP_CFG_FILE_NAME;

		// fileLoc = CfgMisc.getConfigFilePath(fileName);
		ConfigFilePathResolverIsicresAdmin pathResolver = ConfigFilePathResolverIsicresAdmin
				.getInstance();
		fileLoc = pathResolver.resolveFullPath(fileName);

		try {
			// si el path del fichero es distinto de nulo y ademas no esta
			// dentro de un jar
			if (fileLoc != null && !StringUtils.contains(fileLoc, ".jar!")) {
				fileText = FileManager.readStringFromFile(fileLoc);
			} else {
				fileText = FileManager.readStringFromResourceFile(fileName);
			}

			cfg = createUasAuthConfigFromText(fileText, entidad);
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("UasConfigUtil.createUasAuthConfig: "
						+ e.getMessage());

			cfg = createDefaultUasAuthConfig();
		}

		return cfg;

	}

   private static UasAuthConfig createUasAuthConfigFromText(String configFileText, String entidad)
                               throws Exception
   {

      UasAuthConfig uasAuthCfg;
      boolean       byDn;
      String        userAttr, userStart, groupStart;
      int           maxNumTries, userScope, groupScope;
      boolean       find;
      String        value;

      uasAuthCfg  = createDefaultUasAuthConfig();

      Parser parser = new Parser(configFileText);
      XMLTree tree = parser.parse();
      Element root = tree.getRootElement();
      Element authConfig = null;
      Element[] elements = root.getChildren();
      for (int i=0; i<elements.length; i++) {
     	 //Si la entidad es vacia
     	 if(StringUtils.isEmpty(entidad)){
     		 //obtenemos el primer elemento de configuracion
     		authConfig = elements[i].getElementByPath("Auth_Config");
      		break;
     	 }else{
     		 //comprobamos la configuracion para la entidad indicada
         	 if (elements[i].getAttribute("Entidad").getValue().equals(entidad)) {
         		authConfig = elements[i].getElementByPath("Auth_Config");
         		break;
         	 }
     	 }
      }

      if (authConfig == null) {
     	 throw new Exception ("No encontrada configuración de autenticación LDAP para entidad " + entidad);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_MAXNUMTRIES).getValue();
      if (value != null)
      {
         maxNumTries = Integer.parseInt(value);
         uasAuthCfg.setMaxNumTries(maxNumTries);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_USER_SEARCH_BY_DN).getValue();
      if (value != null)
      {
         if(value.equals(UasDefs.SEARCH_MUST_NOT_BE_BY_DN))
            byDn = false;
         else
            byDn = true;

         uasAuthCfg.setUserSearchByDn(byDn);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_USER_START).getValue();
      if (value != null)
      {
         userStart = value;
         uasAuthCfg.setUserStart(userStart);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_USER_SCOPE).getValue();
      if (value != null)
      {
         userScope = Integer.parseInt(value);
         uasAuthCfg.setUserScope(userScope);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_USER_ATTRN).getValue();
      if (value != null)
      {
         userAttr = value;
         uasAuthCfg.setUserAttrName(userAttr);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_GROUP_START).getValue();
      if (value != null)
      {
         groupStart = value;
         uasAuthCfg.setGroupStart(groupStart);
      }

      value = authConfig.getElementByPath(EN_AUTH_CONFIG_GROUP_SCOPE).getValue();
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
