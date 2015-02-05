package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;



public class UasDaoRemUserTbl
{
// **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */   
   public static final String TN ="IUSERREMUSER";

   public static final DbColumnDef CN_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CN_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   public static final DbColumnDef CN_REMARKS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true);  
   
   public static final DbColumnDef CN_REMDATE = new DbColumnDef
   ("REMDATE", DbDataType.DATE_TIME, false);  
   
    
   public static final DbColumnDef[] ACD = 
   {CN_ID, CN_NAME, CN_REMARKS, CN_REMDATE};  
   
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
   
}
