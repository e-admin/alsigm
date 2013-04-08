
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.uas.base.UasAuthToken;

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
         
    	  dbConn.open(DBSessionManager.getSession());	
         
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
