package ieci.tecdoc.core.db;

public final class DbDeleteFns
{

   private DbDeleteFns()
   {
   }

   public static void delete(String tblName, String qual) throws Exception
   {

      String stmtText;

      stmtText = getDeleteStmtText(tblName, qual);

      DbUtil.executeStatement(stmtText);

   }

   // **************************************************************************

   private static String getDeleteStmtText(String tblName, String qual)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append("DELETE FROM ").append(tblName);

      if (qual != null) tbdr.append(" ").append(qual);

      return tbdr.toString();

   }

} // class
