
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import java.sql.Connection;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.uas.base.UasAuthToken;



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
         
         dbConn.open(DBSessionManager.getSession());		
         
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
