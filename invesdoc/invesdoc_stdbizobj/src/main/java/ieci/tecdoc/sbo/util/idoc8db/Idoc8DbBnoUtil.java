
package ieci.tecdoc.sbo.util.idoc8db;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

public final class Idoc8DbBnoUtil
{
   
   private Idoc8DbBnoUtil()
   {
   }

   // *** Access Token *********************************************************
   
   public static int getDbEngine() throws Exception
   {
      
      int engine = 0;      
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());		
         
         engine = DbConnection.getEngine();
         
         DbConnection.close();
         
         return engine;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return engine;
      }
            
   }   
   
}