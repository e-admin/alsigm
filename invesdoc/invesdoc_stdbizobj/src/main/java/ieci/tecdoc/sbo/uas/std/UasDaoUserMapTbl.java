package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class UasDaoUserMapTbl
{
// **************************************************************************
   
   public static final String TN = "IUSERUSERMAP";

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
   public static void createTable() throws Exception
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
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,true,false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
      DbTableFns.createIndex(TN,indexDef3);
      
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
      indexName = TN + "2";      
      dropIndex(TN,indexName);
      indexName = TN + "3";      
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
