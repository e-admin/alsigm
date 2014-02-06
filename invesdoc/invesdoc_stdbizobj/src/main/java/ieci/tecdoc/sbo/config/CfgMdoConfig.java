
package ieci.tecdoc.sbo.config;

import ieci.tecdoc.core.db.DbConnectionConfig;

public final class CfgMdoConfig
{	

   private static final String IECI_DB_CFG_FILE_NAME = "IeciTd_DbConn_Cfg.xml";

   private CfgMdoConfig()
   {
   }

   //Debe existir una conexión a base de datos abierta
   public static CfgLdapConfig loadDbLdapCfg() throws Exception
   {
      
      String        misc;
      CfgDbInfoMisc infoMisc = null;
      
      misc = CfgDaoDbInfoTbl.selectMisc();
      
      infoMisc = new CfgDbInfoMisc();
     
      infoMisc.restore(misc);
     
      return infoMisc.getLdapConfig();          
            
   } 
   
   public static DbConnectionConfig getDbConfig()throws Exception
   {
      DbConnectionConfig cfg = null;

      cfg =  CfgMdoDbConn.createConfigFromFile(IECI_DB_CFG_FILE_NAME);
      
      return cfg;   

   }

   public static DbConnectionConfig getDbConfig(String configDir)throws Exception
   {
      DbConnectionConfig cfg = null;

      cfg =  CfgMdoDbConn.createConfigFromFile(configDir, IECI_DB_CFG_FILE_NAME);
      
      return cfg;   

   }
   
   //Debe existir una conexión a base de datos abierta
   public static String getDbInfoName()throws Exception
   {

      String  name;

      name =  CfgDaoDbInfoTbl.selectName();

      return name;       

   }
   
   //Debe existir una conexión a base de datos abierta
   public static CfgDbInfoMisc loadDbInfoMisc() throws Exception
   {
      String misc;
      CfgDbInfoMisc infoMisc = null;
      
      misc = CfgDaoDbInfoTbl.selectMisc();
      
      infoMisc = new CfgDbInfoMisc();
      
      infoMisc.restore(misc);
      
      return infoMisc;
   }

   //Debe existir una conexión a base de datos abierta
   public static CfgFtsConfig loadDbFtsCfg() throws Exception
   {
      
      String        misc;
      CfgDbInfoMisc infoMisc = null;
      
      misc = CfgDaoDbInfoTbl.selectMisc();
      
      infoMisc = new CfgDbInfoMisc(); 
     
      return infoMisc.restoreFtsInfo(misc);          
            
   } 
   
   public static void storeDbInfoMisc(CfgDbInfoMisc InfoMisc) throws Exception
   {
      String misc;
      
      misc = InfoMisc.persist();
      
      CfgDaoDbInfoTbl.updateMisc(misc);
   }
   
} // class
