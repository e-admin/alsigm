package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

public final class FssDaoRepTbl
{

   // **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String        TN          = "IVOLREPHDR";

   public static final DbColumnDef   CD_ID       = new DbColumnDef(
                                                        "ID",
                                                        DbDataType.LONG_INTEGER,
                                                        false);

   public static final DbColumnDef   CD_NAME     = new DbColumnDef("NAME",
                                                        DbDataType.SHORT_TEXT,
                                                        32, false);

   public static final DbColumnDef   CD_TYPE     = new DbColumnDef(
                                                        "TYPE",
                                                        DbDataType.LONG_INTEGER,
                                                        false);

   public static final DbColumnDef   CD_INFO     = new DbColumnDef("INFO",
                                                        DbDataType.LONG_TEXT,
                                                        4096, false);

   public static final DbColumnDef   CD_STAT     = new DbColumnDef(
                                                        "STAT",
                                                        DbDataType.LONG_INTEGER,
                                                        false);

   public static final DbColumnDef   CD_REMS     = new DbColumnDef("REMARKS",
                                                        DbDataType.SHORT_TEXT,
                                                        254, true);

   public static final DbColumnDef   CD_CRTUSRID = new DbColumnDef(
                                                        "CRTRID",
                                                        DbDataType.LONG_INTEGER,
                                                        false);

   public static final DbColumnDef   CD_CRTTS    = new DbColumnDef("CRTNDATE",
                                                        DbDataType.DATE_TIME,
                                                        false);

   public static final DbColumnDef   CD_UPDUSRID = new DbColumnDef(
                                                        "UPDRID",
                                                        DbDataType.LONG_INTEGER,
                                                        true);

   public static final DbColumnDef   CD_UPDTS    = new DbColumnDef("UPDDATE",
                                                        DbDataType.DATE_TIME,
                                                        true);

   public static final DbColumnDef[] ACD         =
   {CD_ID, CD_NAME, CD_TYPE, CD_INFO, CD_STAT, CD_REMS, CD_CRTUSRID,
    CD_CRTTS, CD_UPDUSRID, CD_UPDTS};

   public static final String        ACN         = DbUtil.getColumnNames(ACD);

   private static final String        RACN        = DbUtil.getColumnNames(
                                                        CD_ID, CD_TYPE,
                                                        CD_INFO, CD_STAT);

   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "=" + id;
   }

   // **************************************************************************

   private FssDaoRepTbl()
   {
   }   
   
   // **************************************************************************

   public static FssDaoRepRecA selectRecA(DbConnection dbConn, int id)
      throws Exception
   {

      FssDaoRepRecA rec = new FssDaoRepRecA();

      DbSelectFns.select(dbConn, TN, RACN, getDefaultQual(id), rec);

      return rec;

   }
   
   public static boolean rowExists(DbConnection dbConn, int type)
                 throws Exception
   {
      String qual;
      
      qual = "WHERE " + CD_TYPE.getName() + "=" + type;
      
      return DbSelectFns.rowExists(dbConn, TN, qual);
   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
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
