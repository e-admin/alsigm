
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;

public final class AcsBnoManager
{
   
   private AcsBnoManager()
   {
   }

   // *** Access Token *********************************************************
   
   public static AcsAccessToken createiDocAccessToken(UasAuthToken authToken)
                                throws Exception
   {
      
      AcsAccessToken token  = null;      
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());		
         
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
