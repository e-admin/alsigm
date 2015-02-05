package ieci.tecdoc.core.db;

public final class DbInsertStatement extends DbInputStatement
{

   public DbInsertStatement()
   {
      super();
   }

   public void create(String tblName, String colNames) throws Exception
   {
      super.create(getInsertStmtText(tblName, colNames));
   }

   // **************************************************************************

   private static String getInsertStmtText(String tblName, String colNames)
   {

      StringBuffer tbdr;
      int 		   index, count, i;

      index = 0;
      count = 0;
      while (index != -1)
      {
         count++;
         index = colNames.indexOf(',', index + 1);
      }

      tbdr = new StringBuffer();

      tbdr.append("INSERT INTO ").append(tblName);
      tbdr.append(" (").append(colNames).append(") VALUES (?");

      for (i = 1; i < count; i++)
         tbdr.append(",?");

      tbdr.append(")");

      return tbdr.toString();

   }

} // class
