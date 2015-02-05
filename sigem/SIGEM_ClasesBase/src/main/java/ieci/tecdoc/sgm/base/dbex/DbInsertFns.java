package ieci.tecdoc.sgm.base.dbex;

public final class DbInsertFns
{

   private DbInsertFns()
   {
   }

   public static void insert(DbConnection connection, String tblName, String colNames,
         DbInputRecord colValues) throws Exception
   {

      DbInsertStatement stmt = null;

      try
      {

         stmt = new DbInsertStatement(connection);
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
