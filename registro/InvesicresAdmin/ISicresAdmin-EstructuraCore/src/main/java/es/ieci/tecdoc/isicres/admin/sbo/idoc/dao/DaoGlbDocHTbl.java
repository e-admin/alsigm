package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;

public class DaoGlbDocHTbl
{
// **************************************************************************
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN ="IDOCGLBDOCH";
        
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
   
   public static void createTable(DbConnection dbConn) throws Exception
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
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false);
      
      DbTableFns.createTable(dbConn, TN,ACD);
      
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      DbTableFns.createIndex(dbConn, TN,indexDef3);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3;      
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      indexName3 = TN + "3";
      
      dropIndex(dbConn, TN,indexName);
      dropIndex(dbConn, TN,indexName2);
      dropIndex(dbConn, TN,indexName3);
         
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
}
