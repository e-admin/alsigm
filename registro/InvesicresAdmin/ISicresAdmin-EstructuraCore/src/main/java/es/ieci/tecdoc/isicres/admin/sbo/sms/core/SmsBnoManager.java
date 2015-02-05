
package es.ieci.tecdoc.isicres.admin.sbo.sms.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;

public final class SmsBnoManager
{
   
   private SmsBnoManager()
   {
   }
   
   public static String beginSession(String appId, int userId, String entidad)
                        throws Exception
   {
      
      String       sessId;
      
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession()); 

         sessId = SmsMdoSession.beginSession(dbConn, appId, userId, entidad);
         
         return sessId;
                                          
      }
      catch (Exception e)
      {
         return null;
      }finally{
    	  dbConn.close();
      }
            
   }
   
   public static String beginGenericSession(int userId, String entidad)
                        throws Exception
   {
   
      String       sessId;
      
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());  
      
      sessId = SmsMdoSession.beginGenericSession(dbConn, userId, entidad);
      
      return sessId;
                        
   }
   catch (Exception e)
   {
      return null;
   }finally{
	   dbConn.close();
   }

}

   public static void endSession(String sessId, String entidad)
                      throws Exception
   {
        
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

         SmsMdoSession.endSession(dbConn, sessId);
         
                                          
      }
      catch (Exception e)
      {
    	  throw e;
      }finally{
    	  dbConn.close();
      }
            
   }

   public static boolean isValidSession(String sessId, String entidad)
                         throws Exception
   {
      
      boolean      valid;
      
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());         

         valid = SmsMdoSession.isValidSession(dbConn, sessId);
         
         return valid;
                                          
      }
      catch (Exception e)
      {
         return false;
      }finally{
    	  dbConn.close();
      }
            
   }
   
} // class
