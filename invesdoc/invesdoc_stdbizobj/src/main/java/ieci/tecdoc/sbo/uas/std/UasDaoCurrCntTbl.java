package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class UasDaoCurrCntTbl
{
// **************************************************************************
   
   public static final String TN = "IUSERCURRCNT";

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
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID,PRODID,APPID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,true,false);
      
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
}
