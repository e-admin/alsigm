package es.ieci.tecdoc.isicres.admin.sbo.fss.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;



public final class FssDaoFileTbl
{

   // **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String         TN       = "IVOLFILEHDR";

   public static final DbColumnDef   CD_ID     = new DbColumnDef
  ("ID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_VOLID  = new DbColumnDef
  ("VOLID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_LOC    = new DbColumnDef
  ("LOC", DbDataType.SHORT_TEXT, 254, false);

   public static final DbColumnDef   CD_EXTID1 = new DbColumnDef
  ("EXTID1", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_EXTID2 = new DbColumnDef
  ("EXTID2", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_EXTID3 = new DbColumnDef
  ("EXTID3", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_FLAGS  = new DbColumnDef
  ("FLAGS", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_STAT   = new DbColumnDef
  ("STAT", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_TS     = new DbColumnDef
  ("TIMESTAMP", DbDataType.DATE_TIME, true);
   
   public static final DbColumnDef   CD_FILESIZE = new DbColumnDef
   ("FILESIZE", DbDataType.LONG_DECIMAL, false);

   public static final DbColumnDef[] ACD =
   {CD_ID, CD_VOLID, CD_LOC, CD_EXTID1, CD_EXTID2, CD_EXTID3, CD_FLAGS, CD_STAT, CD_TS, CD_FILESIZE};

   public static final String        ACN = DbUtil.getColumnNames(ACD);

   private static final String        RACN  = DbUtil.getColumnNames
   (CD_ID, CD_VOLID, CD_LOC, CD_FLAGS, CD_FILESIZE);

   private static final String        UCN   = DbUtil.getColumnNames
   (CD_STAT, CD_TS);

   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "=" + id;
   }

   // **************************************************************************

   private FssDaoFileTbl()
   {
   }

   // **************************************************************************

   public static void insertRow(DbConnection dbConn, FssDaoFileRecAc rec)
      throws Exception
   {
      DbInsertFns.insert(dbConn, TN, ACN, rec);
   }

   // **************************************************************************

   public static void updateRow(DbConnection dbConn, FssDaoFileRecUc rec, int id)
      throws Exception
   {
      DbUpdateFns.update(dbConn, TN, UCN, rec, getDefaultQual(id));
   }

   // **************************************************************************

   public static void deleteRow(DbConnection dbConn, int id)
      throws Exception
   {
      DbDeleteFns.delete(dbConn, TN, getDefaultQual(id));
   }

   // **************************************************************************

   public static FssDaoFileRecA selectRecA(DbConnection dbConn, int id)
      throws Exception
   {

      FssDaoFileRecA rec = new FssDaoFileRecA();

      DbSelectFns.select(dbConn, TN, RACN, getDefaultQual(id), rec);
      return rec;

   }

   public static boolean rowExists(DbConnection dbConn, int id)
      throws Exception
   {
      return DbSelectFns.rowExists(dbConn, TN, getDefaultQual(id));
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
      colNamesIndex2 = "VOLID";
      
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

} // class
