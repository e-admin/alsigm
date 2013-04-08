
package ieci.tecdoc.sbo.config;

import java.io.File;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.core.xml.lite.XmlTextParser;
import ieci.tecdoc.sbo.fss.core.CfgMdoFile;


public final class CfgFssMdoFile
{
   
   private static final String EN_ROOT        = "Config";
   private static final String EN_REPOSITORY	= "Repository";
   private static final String EN_ID  				= "Id";
   private static final String EN_PATH        = "Path";
   private static final String EN_OPERATING_SYSTEM = "OS";
   
   private CfgFssMdoFile()
   {
   }
   
   public static CfgMdoFile createConfigFromFile(String dir, String file)
                                    throws Exception
   {
      
      CfgMdoFile cfg = null;
      String             loc;
      
      loc = dir + File.separator + file;
      
      cfg =  loadConfigFromFile(loc);
      
      return cfg;
      
   }

   public static CfgMdoFile createConfigFromFile(String file)
                                    throws Exception
   {
      
      CfgMdoFile cfg = null;
      String             loc;
      
      loc = CfgMisc.getConfigFilePath(file);
      
      if (loc != null)
         cfg =  loadConfigFromFile(loc);
      else
         cfg =  loadConfigFromResourceFile(file);
      
      return cfg;
      
   }
                           
   public static CfgMdoFile loadConfigFromFile(String fileLoc)
                                    throws Exception
   {    
      String             text;
      XmlTextParser      xtp;
      CfgMdoFile   cfg = null;
      
      text = FileManager.readStringFromFile(fileLoc);
            
      xtp = new XmlTextParser();
      
      xtp.createFromStringText(text);

      cfg = loadConfigFromText(xtp, EN_ROOT);
      
      return cfg;

   }
   
   public static CfgMdoFile loadConfigFromResourceFile(String fileName)
                                    throws Exception
   {    
      String             text;
      XmlTextParser      xtp;
      CfgMdoFile cfg = null;
      
      text = FileManager.readStringFromResourceFile(fileName);
            
      xtp = new XmlTextParser();
      
      xtp.createFromStringText(text);

      cfg = loadConfigFromText(xtp, EN_ROOT);
      
      return cfg;

   }
   
   public static CfgMdoFile loadConfigFromText(XmlTextParser xtp,String ren) 
                                    throws Exception
   {
      
      String sid, spath, sso;
      int id, so;

      CfgMdoFile cfg = new CfgMdoFile ();
      CfgMdoFile.RepositoryConfig repositoryConfig;
      
      xtp.selectElement(ren);     
      
      while (true) {
      
         // los selectElement lanzan una excepción si no encuentran/seleccionan el elemento
         try {
            xtp.selectElement(EN_REPOSITORY);
         } catch (IeciTdException e) {
            break;
         }
         
         xtp.selectElement (EN_ID);
         sid = xtp.getElementValue ();
         
         xtp.selectElement (EN_PATH);
         spath= xtp.getElementValue ();
         
         xtp.selectElement (EN_OPERATING_SYSTEM);
         sso = xtp.getElementValue ();
         
         id = Integer.parseInt (sid);
         so = Integer.parseInt (sso);
         
         repositoryConfig = new CfgMdoFile.RepositoryConfig (id, spath, so);
         
         cfg.addRepositoryConfig (repositoryConfig);
      }
            
      return cfg;
   }  
   
} // class
