package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class DaoAppEventTbl
{
// **************************************************************************
   
   private static final String TN = "IDOCAPPEVENT";

   private static final DbColumnDef CD_APPID = new DbColumnDef
   ("APPID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_EVENTID = new DbColumnDef
   ("EVENTID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_MACROID = new DbColumnDef
   ("MACROID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = 
   {CD_APPID, CD_EVENTID, CD_MACROID};  
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "APPID,EVENTID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);      
      
      DbTableFns.createTable(TN,ACD);
      
      DbTableFns.createIndex(TN,indexDef);
            
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";
      
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
