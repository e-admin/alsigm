package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public final class FssDaoRepTbl
{

   // **************************************************************************

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

   public static FssDaoRepRecA selectRecA(int id)
      throws Exception
   {

      FssDaoRepRecA rec = new FssDaoRepRecA();

      DbSelectFns.select(TN, RACN, getDefaultQual(id), rec);

      return rec;

   }
   
   public static boolean rowExists(int type)
                 throws Exception
   {
      String qual;
      
      qual = "WHERE " + CD_TYPE.getName() + "=" + type;
      
      return DbSelectFns.rowExists(TN, qual);
   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
         
      DbTableFns.dropTable(TN);      
      
   }
   
   private static void dropIndex(String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(tblName,indexName);
      }
      catch(Exception e)
      {
         
      }
   }

} // class
