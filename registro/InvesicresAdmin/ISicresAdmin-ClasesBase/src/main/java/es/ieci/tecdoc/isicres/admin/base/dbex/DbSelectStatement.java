package es.ieci.tecdoc.isicres.admin.base.dbex;

public final class DbSelectStatement extends DbOutputStatement
{

   public DbSelectStatement(DbConnection connection)
   {
      super(connection);
   }

   public void create(String tblNames, String colNames, String qual,
         boolean distinct) throws Exception
   {
      super.create(getSelectStmtText(tblNames, colNames, qual, distinct));
   }

   public void createBlockRows(String tblNames, String colNames, String qual) 
   		throws Exception
   {
      super.create(getSelectForUpdate(tblNames, colNames, qual));
   }
   
   public void createBlob(String tblName, String colName, String qual)
         throws Exception
   {
      super.createBlob(getUpdateStmtTextBlob(tblName, colName, qual));
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

   private static String getUpdateStmtTextBlob(String tblName, String colName,
         String qual)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append("SELECT ");

      tbdr.append(colName).append(" FROM ").append(tblName);

      if (qual != null) tbdr.append(" ").append(qual);
      
      tbdr.append(" FOR UPDATE");

      return tbdr.toString();

   }
   
   private static String getSelectForUpdate(String tblName, String colName,
           String qual)
     {

        StringBuffer tbdr;

        tbdr = new StringBuffer();

        tbdr.append("SELECT ");

        tbdr.append(colName).append(" FROM ").append(tblName);

        if (qual != null) tbdr.append(" ").append(qual);
        
        tbdr.append(" FOR UPDATE");

        return tbdr.toString();

     }
   
} // class
