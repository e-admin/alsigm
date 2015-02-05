package ieci.tecdoc.sbo.util.nextid;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.types.IeciTdType;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

/** Agrupa funciones varias. */

public final class NextId
{

   private NextId()
   {
   }

   public static int generateNextId(String tblName, int type, short incr, String entidad)
         throws Exception
   {
      DaoNextIdTbl nextIdTbl = new DaoNextIdTbl(tblName);
      int id;
      int nextId = IeciTdType.NULL_LONG_INTEGER;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();

         nextIdTbl.incrementNextId(type, incr, entidad);

         id = nextIdTbl.getNextId(type, entidad);

         nextId = id - incr;

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

   public static int generateNextId(String tblName, int type, String entidad) throws Exception
   {

      return generateNextId(tblName, type, (short) 1, entidad);

   }

} // class
