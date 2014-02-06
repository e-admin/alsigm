package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class DaoSrvStateDetTbl
{
   private static final String TN = "IDOCSRVSTATEDET";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_SESSIONID = new DbColumnDef
   ("SESSIONID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_PARENTID = new DbColumnDef
   ("PARENTID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_TEXT, (300*1024), false); 
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_SESSIONID, CD_PARENTID, CD_INFO};  
   
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2, colNamesIndex3;
      DbIndexDef indexDef, indexDef2, indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "SESSIONID";
      indexName3 = TN + "3";
      colNamesIndex3 = "PARENTID";
      
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false,false);
      indexDef3 = new DbIndexDef(indexName3, colNamesIndex3, false,false);
      
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
