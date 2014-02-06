
package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

public final class SmsBnoManager
{
   
   private SmsBnoManager()
   {
   }
   
   public static String beginSession(String appId, int userId)
                        throws Exception
   {
      
      DbConnection dbConn = null;
      String       sessId;
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());     

         sessId = SmsMdoSession.beginSession(appId, userId);

         DbConnection.close();
         
         return sessId;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
         return null;
      }
            
   }
   
   public static String beginGenericSession(int userId)
                        throws Exception
   {
   
      DbConnection dbConn = null;
      String       sessId;
      
      try
      {
      
      DbConnection.open(CfgMdoConfig.getDbConfig());     
      
      sessId = SmsMdoSession.beginGenericSession(userId);
      
      DbConnection.close();
      
      return sessId;
                        
   }
   catch (Exception e)
   {
      DbConnection.ensureClose(e); 
      return null;
   }

}

   public static void endSession(String sessId)
                      throws Exception
   {
        
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());     

         SmsMdoSession.endSession(sessId);

         DbConnection.close();
         
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
      }
            
   }

   public static boolean isValidSession(String sessId)
                         throws Exception
   {
      
      boolean      valid;
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());            

         valid = SmsMdoSession.isValidSession(sessId);
            
         DbConnection.close();
         
         return valid;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
         return false;
      }
            
   }
   
} // class
