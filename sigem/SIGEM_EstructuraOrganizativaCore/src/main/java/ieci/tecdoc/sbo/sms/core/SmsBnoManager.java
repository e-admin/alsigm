
package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

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
      	dbConn.open(DBSessionManager.getSession(entidad)); 

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
      	dbConn.open(DBSessionManager.getSession(entidad));  
      
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
	   	dbConn.open(DBSessionManager.getSession(entidad));

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
      	dbConn.open(DBSessionManager.getSession(entidad));         

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
