
package es.ieci.tecdoc.isicres.admin.sbo.idoc.util;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;

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
      	dbConn.open(DBSessionManager.getSession());
                 
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
	   	dbConn.open(DBSessionManager.getSession());
         
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
	   	dbConn.open(DBSessionManager.getSession());	
         
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
