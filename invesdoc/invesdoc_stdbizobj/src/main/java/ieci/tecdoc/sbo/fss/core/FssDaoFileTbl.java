package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbInsertFns;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUpdateFns;
import ieci.tecdoc.core.db.DbUtil;

public final class FssDaoFileTbl
{

   // **************************************************************************

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

   public static void insertRow(FssDaoFileRecAc rec)
      throws Exception
   {
      DbInsertFns.insert(TN, ACN, rec);
   }

   // **************************************************************************

   public static void updateRow(FssDaoFileRecUc rec, int id)
      throws Exception
   {
      DbUpdateFns.update(TN, UCN, rec, getDefaultQual(id));
   }

   // **************************************************************************

   public static void deleteRow(int id)
      throws Exception
   {
      DbDeleteFns.delete(TN, getDefaultQual(id));
   }

   // **************************************************************************

   public static FssDaoFileRecA selectRecA(int id)
      throws Exception
   {

      FssDaoFileRecA rec = new FssDaoFileRecA();

      DbSelectFns.select(TN, RACN, getDefaultQual(id), rec);
      return rec;

   }

   public static boolean rowExists(int id)
      throws Exception
   {
      return DbSelectFns.rowExists(TN, getDefaultQual(id));
   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "VOLID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false,false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
      indexName = TN + "2";
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
