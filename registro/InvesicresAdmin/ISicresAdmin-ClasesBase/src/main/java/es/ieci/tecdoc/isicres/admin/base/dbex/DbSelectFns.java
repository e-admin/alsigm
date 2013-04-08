package es.ieci.tecdoc.isicres.admin.base.dbex;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdDateTimeArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongDecimalArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongTextArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdShortDecimalArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdShortIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.base.exception.IeciTdException;

import java.util.Date;


public class DbSelectFns
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(DbSelectFns.class);

   private DbSelectFns()
   {
   }

   public static void select(DbConnection connection, String tblNames, String colNames, String qual,
         DbOutputRecord rec) throws Exception
   {

      DbSelectStatement stmt = null;
      
      if(logger.isDebugEnabled())
         logger.debug("tblNames: "+ tblNames+ " colNames: "+colNames+ " qual: "+qual);
      
      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblNames, colNames, qual, false);
         stmt.execute();

         if (stmt.next())
            rec.getStatementValues(stmt);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static void select(DbConnection connection, String tblNames, String colNames, String qual,
         boolean distinct, DbOutputRecordSet rs) throws Exception
   {

      if(logger.isDebugEnabled())
         logger.debug("tblNames: "+ tblNames+ " colNames: "+colNames+ " qual: "+qual);
      
      DbSelectStatement stmt = null;
      DbOutputRecord rec;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblNames, colNames, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            rec = rs.newRecord();
            rec.getStatementValues(stmt);
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static String selectShortText(DbConnection connection, String tblName, String colName,
         String qual) throws Exception
   {

      String val = DbDataType.NULL_SHORT_TEXT;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         if (stmt.next())
            val = stmt.getShortText(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static String selectLongText(DbConnection connection, String tblName, String colName,
         String qual) throws Exception
   {

      String val = DbDataType.NULL_LONG_TEXT;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         if (stmt.next())
            val = stmt.getLongText(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static short selectShortInteger(DbConnection connection, String tblName, String colName,
         String qual) throws Exception
   {

      short val = DbDataType.NULL_SHORT_INTEGER;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         if (stmt.next())
            val = stmt.getShortInteger(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static int selectLongInteger(DbConnection connection, String tblName, String colName,
         String qual) throws Exception
   {

      int val = DbDataType.NULL_LONG_INTEGER;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();
         if(logger.isDebugEnabled())
            logger.debug("tblNames: "+ tblName+ " colNames: "+colName+ " qual: "+qual);

         if (stmt.next())
            val = stmt.getLongInteger(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static float selectShortDecimal(DbConnection connection, String tblName, String colName,
         String qual) throws Exception
   {

      float val = DbDataType.NULL_SHORT_DECIMAL;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         if (stmt.next())
            val = stmt.getShortDecimal(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static double selectLongDecimal(DbConnection connection, String tblName, String colName,
         String qual) throws Exception
   {

      double val = DbDataType.NULL_LONG_DECIMAL;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         if (stmt.next())
            val = stmt.getLongDecimal(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static Date selectDateTime(DbConnection connection, String tblName, String colName, String qual)
         throws Exception
   {

      Date val = DbDataType.NULL_DATE_TIME;
      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         if (stmt.next())
            val = stmt.getDateTime(1);
         else
         {
            throw new IeciTdException(DbError.EC_NOT_FOUND,
                  DbError.EM_NOT_FOUND);
         }

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static void select(DbConnection connection, String tblName, String colName, String qual,
         boolean distinct, IeciTdShortTextArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getShortText(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   // Con columnas de tipo LONG no se puede poner la clausula distinct en
   // una sentencia select
   public static void select(DbConnection connection, String tblName, String colName, String qual,
         IeciTdLongTextArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, false);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getLongText(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static void select(DbConnection connection, String tblName, String colName, String qual,
         boolean distinct, IeciTdShortIntegerArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getShortInteger(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static void select(DbConnection connection, String tblName, String colName, String qual,
         boolean distinct, IeciTdLongIntegerArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {
         if(logger.isDebugEnabled())
            logger.debug("tblNames: "+ tblName+ " colNames: "+colName+ " qual: "+qual+" distinct: "+distinct);

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getLongInteger(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static void select(DbConnection connection, String tblName, String colName, String qual,
         boolean distinct, IeciTdShortDecimalArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getShortDecimal(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static void select(DbConnection connection, String tblName, String colName, String qual,
         boolean distinct, IeciTdLongDecimalArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getLongDecimal(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static void select(DbConnection connection, String tblName, String colName, String qual,
         boolean distinct, IeciTdDateTimeArrayList vals) throws Exception
   {

      DbSelectStatement stmt = null;

      try
      {

         stmt = new DbSelectStatement(connection);
         stmt.create(tblName, colName, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            vals.add(stmt.getDateTime(1));
         }

         stmt.release();

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
      }

   }

   public static int selectCount(DbConnection connection, String tblName, String qual) throws Exception
   {

      int val = 0;
      DbSelectStatement stmt = null;
      String stmtText;

      try
      {

         stmt = new DbSelectStatement(connection);
         if (qual == null)
            stmtText = "SELECT COUNT(*) FROM " + tblName;
         else
            stmtText = "SELECT COUNT(*) FROM " + tblName + " " + qual;
         stmt.create(stmtText);
         stmt.execute();

         if (stmt.next())
            val = stmt.getLongInteger(1);
         else
            val = 0;

         stmt.release();

         return val;

      }
      catch (Exception e)
      {
         DbSelectStatement.ensureRelease(stmt, e);
         return val;
      }

   }

   public static boolean rowExists(DbConnection connection, String tblName, String qual)
         throws Exception
   {

      boolean exists = false;
      int count;

      count = selectCount(connection, tblName, qual);
      if (count > 0) exists = true;

      return exists;

   }

} // class
