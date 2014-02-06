
package ieci.tecdoc.sbo.idoc.util;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

public final class UsrPoolBno
{
   
   private UsrPoolBno()
   {
   }
   
   public static int getFreeUser() throws Exception
   {
   
      int groupId = 0;
      	
      return getFreeUser(groupId);
      	
   }
   
   public static int getFreeUser(int groupId) throws Exception
   {
     int userId  = -1;      
     boolean bOpenedHere = false;
     
     try
     {
        
         try
         {
           DbConnection.getJdbcConnection ();
         }
         catch (IeciTdException e)
         {
           DbConnection.open(CfgMdoConfig.getDbConfig());
           bOpenedHere = true;
         }
       
        
        
        userId = UsrPoolMgr.getFreeUser(groupId);
        
        if (bOpenedHere)
          DbConnection.close();
        
        return userId;
                                         
     }
     catch (Exception e)
     {
        if (bOpenedHere)
          DbConnection.ensureClose(e);  
        
        return userId;
     }           
   } 
   
   public static void freeUser(int userId) throws Exception
   {
      
      int groupId = 0;
      
      freeUser(userId,groupId);
      
   }

   public static void freeUser(int userId, int groupId) throws Exception
   {
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());		
         
         UsrPoolMgr.freeUser(userId, groupId);
         
         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
      }
            
   } 

   //TimedOut en minutos
   public static void freeTimedOutUsers(int toim) throws Exception
   {
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());		
         
         UsrPoolMgr.freeTimedOutUsers(toim);
         
         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
      }
            
   }         
   
} // class
