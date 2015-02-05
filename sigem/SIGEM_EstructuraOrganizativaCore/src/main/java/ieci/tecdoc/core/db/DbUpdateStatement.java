package ieci.tecdoc.core.db;

import java.util.StringTokenizer;

public final class DbUpdateStatement extends DbInputStatement
{

   public DbUpdateStatement()
   {
      super();
   }

   public void create(String tblName, String colNames, String qual)
         throws Exception
   {
      super.create(getUpdateStmtText(tblName, colNames, qual));
   }

   // **************************************************************************

   private static String getUpdateStmtText(String tblName, String colNames,
         String qual)
   {
      if (colNames.indexOf(',', 0) != -1)
         return getMCUpdateStmtText(tblName, colNames, qual);
      else
         return get1CUpdateStmtText(tblName, colNames, qual);
   }

   private static String getMCUpdateStmtText(String tblName, String colNames,
         String qual)
   {

      StringBuffer tbdr;
      StringTokenizer strTkr;
      int count, i;

      tbdr = new StringBuffer();

      tbdr.append("UPDATE ").append(tblName).append(" SET ");

      strTkr = new StringTokenizer(colNames, ",");

      count = strTkr.countTokens();

      for (i = 1; i < count; i++)
         tbdr.append(strTkr.nextToken()).append(" = ?, ");

      tbdr.append(strTkr.nextToken()).append(" = ?");

      if (qual != null) tbdr.append(" ").append(qual);

      return tbdr.toString();

   }

   private static String get1CUpdateStmtText(String tblName, String colName,
         String qual)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append("UPDATE ").append(tblName).append(" SET ");

      tbdr.append(colName).append(" = ?");

      if (qual != null) tbdr.append(" ").append(qual);

      return tbdr.toString();

   }

} // class
