package ieci.tecdoc.core.db;

public final class DbSelectStatement extends DbOutputStatement
{

   public DbSelectStatement()
   {
      super();
   }

   public void create(String tblNames, String colNames, String qual,
         boolean distinct) throws Exception
   {
      super.create(getSelectStmtText(tblNames, colNames, qual, distinct));
   }

   // **************************************************************************

   private static String getSelectStmtText(String tblNames, String colNames,
         String qual, boolean distinct)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append("SELECT ");

      if (distinct) tbdr.append("DISTINCT ");

      tbdr.append(colNames).append(" FROM ").append(tblNames);

      if (qual != null) tbdr.append(" ").append(qual);

      return tbdr.toString();

   }

} // class
