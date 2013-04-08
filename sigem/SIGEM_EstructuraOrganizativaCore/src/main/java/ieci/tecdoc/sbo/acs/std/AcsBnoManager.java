
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class AcsBnoManager
{
   
   private AcsBnoManager()
   {
   }

   // *** Access Token *********************************************************
   
   public static AcsAccessToken createiDocAccessToken(UasAuthToken authToken, String entidad)
                                throws Exception
   {
      
      AcsAccessToken token  = null;      
      
      DbConnection dbConn=new DbConnection();
      try
      {
         
    	  dbConn.open(DBSessionManager.getSession(entidad));	
         
         token = AcsMdoAccessToken.createiDocAccessToken(authToken, entidad);
         
         dbConn.close();
                                                   
      }
      catch (Exception e)
      {
         //DbConnection.ensureClose(e);  
      }finally{
    	  dbConn.close();
      }
      return token;
   }   
   
} // class
