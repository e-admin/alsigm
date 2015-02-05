package es.ieci.tecdoc.isicres.admin.sbo.sms.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.types.IeciTdType;

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
      	dbConn.open(DBSessionManager.getSession());

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
