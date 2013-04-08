package ieci.tecdoc.core.db;

import java.util.Date;

public final class DbUpdateFns
{

   private DbUpdateFns()
   {
   }

   public static void update(String tblName, String colNames,
         DbInputRecord colValues, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colNames, qual);

         colValues.setStatementValues(stmt);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateShortText(String tblName, String colName,
         String colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setShortText(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateLongText(String tblName, String colName,
         String colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setLongText(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateShortInteger(String tblName, String colName,
         short colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setShortInteger(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateLongInteger(String tblName, String colName,
         int colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setLongInteger(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateShortDecimal(String tblName, String colName,
         float colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setShortDecimal(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateLongDecimal(String tblName, String colName,
         double colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setLongDecimal(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static void updateDateTime(String tblName, String colName,
         Date colValue, String qual) throws Exception
   {

      DbUpdateStatement stmt = null;

      try
      {

         stmt = new DbUpdateStatement();
         stmt.create(tblName, colName, qual);

         stmt.setDateTime(1, colValue);

         stmt.execute();
         stmt.release();

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

} // class
