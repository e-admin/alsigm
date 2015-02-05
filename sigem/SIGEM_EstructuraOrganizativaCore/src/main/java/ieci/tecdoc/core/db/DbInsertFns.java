package ieci.tecdoc.core.db;

public final class DbInsertFns
{

   private DbInsertFns()
   {
   }

   public static void insert(String tblName, String colNames,
         DbInputRecord colValues) throws Exception
   {

      DbInsertStatement stmt = null;

      try
      {

         stmt = new DbInsertStatement();
         stmt.create(tblName, colNames);

         colValues.setStatementValues(stmt);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbInsertStatement.ensureRelease(stmt, e);
      }

   }

} // class
