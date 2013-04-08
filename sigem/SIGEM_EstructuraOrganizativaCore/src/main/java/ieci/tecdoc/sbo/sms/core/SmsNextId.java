package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.types.IeciTdType;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

/** Agrupa funciones varias. */

public final class SmsNextId
{

   private SmsNextId()
   {
   }

   public static int generateNextId(String entidad)
                     throws Exception
   {
      int nextId = IeciTdType.NULL_LONG_INTEGER;
      int id;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();

         SmsDaoSessNextIdTbl.incrementNextId(dbConn, (short)1);

         id = SmsDaoSessNextIdTbl.getNextId(dbConn);

         nextId = id - 1;

         dbConn.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         return nextId;
      }finally{
    	  dbConn.close();
      }

   }

} // class
