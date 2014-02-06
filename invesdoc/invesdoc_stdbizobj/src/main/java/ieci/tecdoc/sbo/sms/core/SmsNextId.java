package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.types.IeciTdType;

/** Agrupa funciones varias. */

public final class SmsNextId
{

   private SmsNextId()
   {
   }

   public static int generateNextId()
                     throws Exception
   {
      int nextId = IeciTdType.NULL_LONG_INTEGER;
      int id;

      try
      {

         DbConnection.beginTransaction();

         SmsDaoSessNextIdTbl.incrementNextId((short)1);

         id = SmsDaoSessNextIdTbl.getNextId();

         nextId = id - 1;

         DbConnection.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         DbConnection.ensureEndTransaction(e);
         return nextId;
      }

   }

} // class
