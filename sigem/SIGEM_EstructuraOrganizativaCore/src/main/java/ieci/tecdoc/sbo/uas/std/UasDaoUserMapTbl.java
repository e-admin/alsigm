package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;

public class UasDaoUserMapTbl
{
// **************************************************************************
/* 
 * @SF-SEVILLA 
 * 02-may-2006 / antmaria
 */   
   public static final String TN ="IUSERUSERMAP";

   public static final DbColumnDef CN_SRCDBID = new DbColumnDef
   ("SRCDBID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CN_SRCUSERID = new DbColumnDef
   ("SRCUSERID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CN_DSTDBID = new DbColumnDef
   ("DSTDBID", DbDataType.LONG_INTEGER, false);  
   
   public static final DbColumnDef CN_DSTUSERID = new DbColumnDef
   ("DSTUSERID", DbDataType.LONG_INTEGER, false);  
   
     
   public static final DbColumnDef[] ACD = 
   {CN_SRCDBID, CN_SRCUSERID, CN_DSTDBID, CN_DSTUSERID};  
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex3,colNamesIndex2;
      DbIndexDef indexDef,indexDef2,indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "SRCDBID,SRCUSERID";
      indexName2 = TN + "2";
      colNamesIndex2 = "DSTDBID,DSTUSERID";
      indexName3 = TN + "3";
      colNamesIndex3 = "SRCDBID,SRCUSERID,DSTDBID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,true);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      DbTableFns.createIndex(dbConn, TN,indexDef3);
      
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "3";      
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
