
package ieci.tecdoc.sbo.util.idoc8db;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class Idoc8DbBnoUtil
{
   
   private Idoc8DbBnoUtil()
   {
   }

   // *** Access Token *********************************************************
   
   public static int getDbEngine(String entidad) throws Exception
   {
      
      int engine = 0;      
      
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));	
         
         engine = dbConn.getEngine();
         
         return engine;
                                          
      }
      catch (Exception e)
      {
         return engine;
      }finally{
    	  dbConn.close();
      }
            
   }   
   
}