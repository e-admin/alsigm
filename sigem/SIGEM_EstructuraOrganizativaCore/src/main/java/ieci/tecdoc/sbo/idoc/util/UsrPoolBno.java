
package ieci.tecdoc.sbo.idoc.util;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class UsrPoolBno
{
   
   private UsrPoolBno()
   {
   }
   
   public static int getFreeUser(String entidad) throws Exception
   {
      
      int userId  = -1;      
      
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));
                 
         userId = UsrPoolMgr.getFreeUser(entidad);
         
         return userId;
                                          
      }
      catch (Exception e)
      {
         return userId;
      }finally{
    	  dbConn.close();
      }
            
   } 

   public static void freeUser(int userId, String entidad) throws Exception
   {
      
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));
         
         UsrPoolMgr.freeUser(userId, entidad);
                                          
      }
      catch (Exception e){
    	  throw e;
      }finally{
    	  dbConn.close();
      }
            
   } 

   //TimedOut en minutos
   public static void freeTimedOutUsers(int toim, String entidad) throws Exception
   {
      
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));	
         
         UsrPoolMgr.freeTimedOutUsers(dbConn, toim);
         
                                          
      }
      catch (Exception e)
      {
    	  throw e;
      }finally{
    	  dbConn.close();
      }
            
   }         
   
} // class
