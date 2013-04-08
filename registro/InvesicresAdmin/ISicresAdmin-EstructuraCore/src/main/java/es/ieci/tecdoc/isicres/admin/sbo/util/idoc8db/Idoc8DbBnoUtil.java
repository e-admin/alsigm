
package es.ieci.tecdoc.isicres.admin.sbo.util.idoc8db;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;

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
      	dbConn.open(DBSessionManager.getSession());	
         
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