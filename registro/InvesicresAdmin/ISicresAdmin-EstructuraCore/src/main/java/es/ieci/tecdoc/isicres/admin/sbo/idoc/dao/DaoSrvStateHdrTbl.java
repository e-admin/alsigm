package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;


public class DaoSrvStateHdrTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN ="IDOCSRVSTATEHDR";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_APPID = new DbColumnDef
   ("APPID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_ALIASID = new DbColumnDef
   ("ALIASID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_TIMESTAMP = new DbColumnDef
   ("TIMESTAMP", DbDataType.DATE_TIME, false);
   
   private static final DbColumnDef CD_RANDOM = new DbColumnDef
   ("RANDOM", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_TEXT, (300*1024), false);    
   
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_PRODID, CD_APPID, CD_USERID, CD_ALIASID, CD_TIMESTAMP, CD_RANDOM, CD_INFO};  
   
   
// **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2, colNamesIndex3;
      DbIndexDef indexDef, indexDef2, indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID,PRODID";
      indexName3 = TN + "3";
      colNamesIndex3 = "TIMESTAMP";
      
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
      indexDef3 = new DbIndexDef(indexName3, colNamesIndex3, false);
      
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
