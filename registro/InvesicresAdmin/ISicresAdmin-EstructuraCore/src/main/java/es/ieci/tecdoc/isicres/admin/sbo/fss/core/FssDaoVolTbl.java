package es.ieci.tecdoc.isicres.admin.sbo.fss.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;



public final class FssDaoVolTbl
{

   // **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String        TN          = "IVOLVOLHDR";

   public static final DbColumnDef   CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);

   public static final DbColumnDef   CD_REPID = new DbColumnDef
   ("REPID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_TEXT, 4096, false);

   public static final DbColumnDef   CD_ITEMP = new DbColumnDef
   ("ITEMP", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_ASIZE = new DbColumnDef
   ("ACTSIZE", DbDataType.SHORT_TEXT, 32, false);

   public static final DbColumnDef   CD_NUMFILES = new DbColumnDef
   ("NUMFILES", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_STAT = new DbColumnDef
   ("STAT", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_REMS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true);

   public static final DbColumnDef   CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);

   public static final DbColumnDef   CD_UPDUSRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   public static final DbColumnDef   CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);

   public static final DbColumnDef[] ACD =
   {CD_ID, CD_NAME, CD_REPID, CD_INFO, CD_ITEMP, CD_ASIZE, CD_NUMFILES, CD_STAT,
    CD_REMS, CD_CRTUSRID, CD_CRTTS, CD_UPDUSRID, CD_UPDTS};

   public static final String   ACN  = DbUtil.getColumnNames(ACD);

   public static final String   RACN = DbUtil.getColumnNames
   (CD_ID, CD_REPID, CD_INFO, CD_ASIZE, CD_STAT);

   public static final String   UCN  = DbUtil.getColumnNames
   (CD_ASIZE, CD_STAT);

   // **************************************************************************

   public static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "=" + id;
   }

   // **************************************************************************

   private FssDaoVolTbl()
   {
   }

   // **************************************************************************

   public static void incrementNumFiles(DbConnection dbConn, int id)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_NUMFILES.getName() + "="
            + CD_NUMFILES.getName() + "+1 WHERE " + CD_ID.getName() + "= " + id;

      DbUtil.executeStatement(dbConn, stmtText);

   }

   public static void decrementNumFiles(DbConnection dbConn, int id)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_NUMFILES.getName() + "="
            + CD_NUMFILES.getName() + "-1 WHERE " + CD_ID.getName() + "= " + id;

      DbUtil.executeStatement(dbConn, stmtText);

   }

   public static void updateRow(DbConnection dbConn, FssDaoVolRecUc rec, int id)
      throws Exception
   {
      DbUpdateFns.update(dbConn, TN, UCN, rec, getDefaultQual(id));
   }

   // **************************************************************************

   public static FssDaoVolRecA selectRecA(DbConnection dbConn, int id)
      throws Exception
   {

      FssDaoVolRecA rec = new FssDaoVolRecA();

      DbSelectFns.select(dbConn, TN, RACN, getDefaultQual(id), rec);

      return rec;

   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "REPID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";
      dropIndex(dbConn, TN,indexName);
         
      DbTableFns.dropTable(dbConn, TN);     
      
   }
   
   private static void dropIndex(DbConnection dbConn, String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(dbConn, tblName,indexName);
      }
      catch(Exception e)
      {         
      }
   }

   // **************************************************************************

} // class
