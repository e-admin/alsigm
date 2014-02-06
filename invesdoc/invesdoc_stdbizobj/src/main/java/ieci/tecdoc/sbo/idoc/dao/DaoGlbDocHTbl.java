package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class DaoGlbDocHTbl
{
// **************************************************************************
   private static final String TN = "IDOCGLBDOCH";
        
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   private static final DbColumnDef CD_CLFID = new DbColumnDef
   ("CLFID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_TITLE = new DbColumnDef
   ("TITLE", DbDataType.SHORT_TEXT, 128, true);
   
   private static final DbColumnDef CD_AUTHOR = new DbColumnDef
   ("AUTHOR", DbDataType.SHORT_TEXT, 64, true);
   
   private static final DbColumnDef CD_KEYWORDS = new DbColumnDef
   ("KEYWORDS", DbDataType.SHORT_TEXT, 254, true);
   
   private static final DbColumnDef CD_STAT = new DbColumnDef
   ("STAT", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_REFCOUNT = new DbColumnDef
   ("REFCOUNT", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_REMARKS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true);

   private static final DbColumnDef CD_ACCESSTYPE = new DbColumnDef
   ("ACCESSTYPE", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_ACSID = new DbColumnDef
   ("ACSID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CN_CRTRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CN_CRTNDATE = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);
   
   private static final DbColumnDef CN_UPDRID = new DbColumnDef 
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   private static final DbColumnDef CN_UPDDATE = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
   
   private static final DbColumnDef CN_ACCRID = new DbColumnDef 
   ("ACCRID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CN_ACCDATE = new DbColumnDef
   ("ACCDATE", DbDataType.DATE_TIME, false);
   
   private static final DbColumnDef CN_ACCCOUNT = new DbColumnDef 
   ("ACCCOUNT", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CN_TIMESTAMP = new DbColumnDef
   ("TIMESTAMP", DbDataType.DATE_TIME, false);   
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_FDRID, CD_ARCHID, CD_NAME, CD_CLFID, CD_TYPE, CD_TITLE, CD_AUTHOR,
         CD_KEYWORDS, CD_STAT, CD_REFCOUNT, CD_REMARKS, CD_ACCESSTYPE, CD_ACSID,
         CN_CRTRID, CN_CRTNDATE, CN_UPDRID, CN_UPDDATE, CN_ACCRID, CN_ACCDATE,
         CN_ACCCOUNT, CN_TIMESTAMP};  
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2,colNamesIndex3;
      DbIndexDef indexDef, indexDef2, indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "ARCHID,FDRID";
      indexName3 = TN + "3";
      colNamesIndex3 = "ARCHID,FDRID,CLFID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false,false);
      
      DbTableFns.createTable(TN,ACD);
      
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
      DbTableFns.createIndex(TN,indexDef3);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName,indexName2,indexName3;      
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      indexName3 = TN + "3";
      
      dropIndex(TN,indexName);
      dropIndex(TN,indexName2);
      dropIndex(TN,indexName3);
         
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
}
