
package ieci.tecdoc.sbo.config;

import java.net.URL;
import java.io.File;
import java.util.StringTokenizer;

public final class CfgMisc
{
   private static final String CONFIG_DIR_PROP = "IeciTd_Config_Dir";
   
   private CfgMisc()
   {
   }
   
    
   // **************************************************************************  
   
   
   public static String getConfigFilePath(String fileName) throws Exception
   {
      
      String dir = null;
      String loc = null;

      dir = getConfigDirectory();

      if (dir != null)
         loc = dir + File.separator + fileName;
      else
      {      
         if (loc == null)
            loc = getClassPathFileLocation(fileName);
      }

      return loc;
      
   }

   private static String getConfigDirectory()
                         throws Exception
   {
      
      String dir = null;

      dir = getConfigDirectoryUsingSystemProperty();      
           
      return dir;
      
   }   

   // **************************************************************************
   private static String getConfigDirectoryUsingSystemProperty()
                         throws Exception
   {
   
      String dir = null;   
      
      dir = System.getProperty(CONFIG_DIR_PROP);
      
      return dir;
   
   }
      
   private static String getResourceFileLocation(String fileName)
   {
      
      String loc = null;
      URL    url;
      
      url = CfgMisc.class.getResource("/" + fileName);
      if (url != null)
         loc = url.getPath();     
      
      return loc;
      
   }

   private static String getClassPathFileLocation(String fileName)
   {
      
      String          loc = null;
      String          cp, ps, dir, path;
      StringTokenizer strTkr;
      int             count, i;      
      File            file;
      
      cp = System.getProperty("java.class.path");
      if (cp != null)
      {
      
         ps     = File.pathSeparator;
         strTkr = new StringTokenizer(cp, ps);  
         count  = strTkr.countTokens();
               
         for (i = 0; i < count; i++)
         {
            
            path = strTkr.nextToken(); 
            
            file = new File(path);
            
            if (file.isDirectory())
               dir = path;
            else
               dir = file.getParent();
            
            file = new File(dir, fileName);
            if (file.exists())
            {
               loc = dir + File.separator + fileName;
               break;
            }
            
         }
      
      }
      
      return loc;
      
   }
   
} // class
