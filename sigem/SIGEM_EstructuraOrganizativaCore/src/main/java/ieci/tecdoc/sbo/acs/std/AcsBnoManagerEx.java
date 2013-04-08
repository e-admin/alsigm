
package ieci.tecdoc.sbo.acs.std;

import java.sql.Connection;

import ieci.tecdoc.core.db.DBSessionManager;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class AcsBnoManagerEx
{
   
   private AcsBnoManagerEx()
   {
   }

   
   // *** Access Token *********************************************************
   
   public static AcsAccessToken createiDocAccessToken(Connection jdbcCnt, UasAuthToken authToken, String entidad)
                                throws Exception
   {
      
      AcsAccessToken token  = null;      
      
      DbConnection dbConn=new DbConnection();
      try
      {
         
         dbConn.open(jdbcCnt);		
         
         token = AcsMdoAccessToken.createiDocAccessToken(authToken, entidad);
         
         dbConn.close();
                                                   
      }
      catch (Exception e)
      {
    	  throw (e);
      }finally{
    	  dbConn.close();
      }
      return token;    
   }
   
   public static AcsAccessToken createiDocAccessToken(DbConnectionConfig dbConConfig,
                                                      UasAuthToken authToken, String entidad)
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
    	  throw (e);
      }finally{
          dbConn.close();
      }
      return token;     
   }
   
} // class
