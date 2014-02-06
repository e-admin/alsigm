package ieci.tecdoc.sbo.util.nextid;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.types.IeciTdType;

/** Agrupa funciones varias. */

public final class NextId
{

   private NextId()
   {
   }

   public static int generateNextId(String tblName, int type, short incr)
         throws Exception
   {
      DaoNextIdTbl nextIdTbl = new DaoNextIdTbl(tblName);
      int id;
      int nextId = IeciTdType.NULL_LONG_INTEGER;

      try
      {

         DbConnection.beginTransaction();

         nextIdTbl.incrementNextId(type, incr);

         id = nextIdTbl.getNextId(type);

         nextId = id - incr;

         DbConnection.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         DbConnection.ensureEndTransaction(e);
         return nextId;
      }

   }

   public static int generateNextId(String tblName, int type) throws Exception
   {

      return generateNextId(tblName, type, (short) 1);

   }

} // class
