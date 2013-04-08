package es.ieci.tecdoc.isicres.admin.base.dbex;

public final class DbInsertStatement extends DbInputStatement
{

   public DbInsertStatement(DbConnection connection)
   {
      super(connection);
   }

   public void create(String tblName, String colNames) throws Exception
   {
      super.create(getInsertStmtText(tblName, colNames));
   }

   public void createBlob(String tblName, String colNames, int[] blobIndexArr) throws Exception
   {
      super.create(getInsertStmtTextBlob(tblName, colNames, blobIndexArr));
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

   private static String getInsertStmtTextBlob(String tblName, String colNames, int[] blobIndexArr)
   {

      StringBuffer tbdr;
      int j, index, count, i;
      boolean found = false;

      index = 0;
      count = 0;
      while (index != -1)
      {
         count++;
         index = colNames.indexOf(',', index + 1);
      }

      tbdr = new StringBuffer();

      tbdr.append("INSERT INTO ").append(tblName);
      found = false;
      for (i = 0; i < blobIndexArr.length; i++)
      {
         if (blobIndexArr[i] == 0)
         {
            found = true;
            break;
         }
      }
      if (found)
         tbdr.append(" (").append(colNames).append(") VALUES (empty_blob()");
      else
         tbdr.append(" (").append(colNames).append(") VALUES (?");

      for (i = 1; i < count; i++)
      {
         found = false;
         for (j = 0; j < blobIndexArr.length; j++)
         {
            if (blobIndexArr[j] == i)
            {
               found = true;
               break;
            }
         }

         if (found)
            tbdr.append(",empty_blob()");
         else
            tbdr.append(",?");
      }

      tbdr.append(")");

      return tbdr.toString();

   }

} // class
