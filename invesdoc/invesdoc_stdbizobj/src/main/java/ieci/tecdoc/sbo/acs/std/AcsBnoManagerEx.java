
package ieci.tecdoc.sbo.acs.std;

import java.sql.Connection;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;

public final class AcsBnoManagerEx
{
   
   private AcsBnoManagerEx()
   {
   }

   
   // *** Access Token *********************************************************
   
   public static AcsAccessToken createiDocAccessToken(Connection jdbcCnt, UasAuthToken authToken)
                                throws Exception
   {
      
      AcsAccessToken token  = null;      
      
      try
      {
         
         DbConnection.open(jdbcCnt);		
         
         token = AcsMdoAccessToken.createiDocAccessToken(authToken);
         
         DbConnection.close();
         
         return token;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return token;
      }
            
   }
   
   public static AcsAccessToken createiDocAccessToken(DbConnectionConfig dbConConfig,
                                                      UasAuthToken authToken)
                                throws Exception
   {
      
      AcsAccessToken token  = null;      
      
      try
      {
         
         DbConnection.open(dbConConfig);		
         
         token = AcsMdoAccessToken.createiDocAccessToken(authToken);
         
         DbConnection.close();
         
         return token;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return token;
      }
            
   }
   
} // class
