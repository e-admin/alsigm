
package ieci.tecdoc.sbo.config;

import java.io.File;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.core.xml.lite.XmlTextParser;
import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.core.db.DbConnectionConfig;

public final class CfgMdoDbConn
{
   
   private static final String EN_ROOT        = "Config";
   private static final String EN_POOL        = "Pooling";
   private static final String EN_DATASOURCE  = "DataSource";
   private static final String EN_DRV         = "Driver";
   private static final String EN_URL         = "Url";
   private static final String EN_USER        = "User";
   private static final String EN_PWD         = "Password";
   
   private CfgMdoDbConn()
   {
   }
   
   public static DbConnectionConfig createConfigFromFile(String dir, String file)
                                    throws Exception
   {
      
      DbConnectionConfig cfg = null;
      String             loc;
      
      loc = dir + File.separator + file;
      
      cfg =  loadConfigFromFile(loc);
      
      return cfg;
      
   }

   public static DbConnectionConfig createConfigFromFile(String file)
                                    throws Exception
   {
      
      DbConnectionConfig cfg = null;
      String             loc;
      
      loc = CfgMisc.getConfigFilePath(file);
      
      if (loc != null)
         cfg =  loadConfigFromFile(loc);
      else
         cfg =  loadConfigFromResourceFile(file);
      
      return cfg;
      
   }
                           
   public static DbConnectionConfig loadConfigFromFile(String fileLoc)
                                    throws Exception
   {    
      String             text;
      XmlTextParser      xtp;
      DbConnectionConfig cfg = null;
      
      text = FileManager.readStringFromFile(fileLoc);
            
      xtp = new XmlTextParser();
      
      xtp.createFromStringText(text);

      cfg = loadConfigFromText(xtp, EN_ROOT);
      
      return cfg;

   }
   
   public static DbConnectionConfig loadConfigFromResourceFile(String fileName)
                                    throws Exception
   {    
      String             text;
      XmlTextParser      xtp;
      DbConnectionConfig cfg = null;
      
      text = FileManager.readStringFromResourceFile(fileName);
            
      xtp = new XmlTextParser();
      
      xtp.createFromStringText(text);

      cfg = loadConfigFromText(xtp, EN_ROOT);
      
      return cfg;

   }
   
   public static DbConnectionConfig loadConfigFromText(XmlTextParser xtp,String ren) 
                                    throws Exception
   {
      
      String             pooling, encPwd, dataSource;
      String             ctx = null;
      String             drv = null;
      String             url = null;
      String             user, pwd;
      DbConnectionConfig cfg = null;
      
      xtp.selectElement(ren);     
      
      xtp.selectElement(EN_POOL);
      pooling = xtp.getElementValue(); 

      if (pooling.equals("Y"))
      {      
         xtp.selectElement(EN_DATASOURCE);
         dataSource = xtp.getElementValue();
         ctx = dataSource;
      }
      else 
      {     
         xtp.selectElement(EN_DRV);
         drv = xtp.getElementValue();

         xtp.selectElement(EN_URL);
         url = xtp.getElementValue();
      }
      
      xtp.selectElement(EN_USER);
      user = xtp.getElementValue();
      
      if (user.equals(""))
         user = null;
            
      xtp.selectElement(EN_PWD);
      encPwd = xtp.getElementValue();

      if ((encPwd != null) && (!encPwd.equals("")))
         pwd = Base64Util.decodeToString(encPwd);
      else
         pwd = null;

      if (pooling.equals("Y"))
      {
          cfg = new DbConnectionConfig(ctx, user, pwd);
      }
      else
      {
          cfg = new DbConnectionConfig(drv, url, user, pwd);
      }
      
      return cfg;
            
   }  
   
} // class
