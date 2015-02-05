package es.ieci.tecdoc.isicres.admin.base.dbex;

public final class DbDeleteFns
{

   private DbDeleteFns()
   {
   }

   public static void delete(DbConnection connection, String tblName, String qual) throws Exception
   {

      String stmtText;

      stmtText = getDeleteStmtText(tblName, qual);

      DbUtil.executeStatement(connection, stmtText);

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
