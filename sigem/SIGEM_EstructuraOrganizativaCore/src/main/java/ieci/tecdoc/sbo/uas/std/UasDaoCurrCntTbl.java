package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;

public class UasDaoCurrCntTbl
{
// **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */   
   public static final String TN ="IUSERCURRCNT";

   public static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);  
   
   public static final DbColumnDef CD_APPID = new DbColumnDef
   ("APPID", DbDataType.LONG_INTEGER, false);  
   
   public static final DbColumnDef CD_STATDT = new DbColumnDef
   ("TIMESTAMP", DbDataType.DATE_TIME, false);   
   
   
   public static final DbColumnDef[] ACD = 
   {CD_ID, CD_USERID, CD_PRODID, CD_APPID,CD_STATDT};  
   
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID,PRODID,APPID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,true);
      
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
}
