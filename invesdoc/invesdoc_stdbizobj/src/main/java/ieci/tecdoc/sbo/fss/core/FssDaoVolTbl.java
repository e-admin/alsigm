package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUpdateFns;
import ieci.tecdoc.core.db.DbUtil;

public final class FssDaoVolTbl
{

   // **************************************************************************

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

   public static void incrementNumFiles(int id)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_NUMFILES.getName() + "="
            + CD_NUMFILES.getName() + "+1 WHERE " + CD_ID.getName() + "= " + id;

      DbUtil.executeStatement(stmtText);

   }

   public static void decrementNumFiles(int id)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_NUMFILES.getName() + "="
            + CD_NUMFILES.getName() + "-1 WHERE " + CD_ID.getName() + "= " + id;

      DbUtil.executeStatement(stmtText);

   }

   public static void updateRow(FssDaoVolRecUc rec, int id)
      throws Exception
   {
      DbUpdateFns.update(TN, UCN, rec, getDefaultQual(id));
   }

   // **************************************************************************

   public static FssDaoVolRecA selectRecA(int id)
      throws Exception
   {

      FssDaoVolRecA rec = new FssDaoVolRecA();

      DbSelectFns.select(TN, RACN, getDefaultQual(id), rec);

      return rec;

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
      colNamesIndex2 = "REPID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,true);
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

   // **************************************************************************

} // class
