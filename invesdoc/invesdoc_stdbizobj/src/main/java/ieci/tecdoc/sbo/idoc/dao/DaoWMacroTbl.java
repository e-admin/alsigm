package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class DaoWMacroTbl
{
// **************************************************************************
   
   private static final String TN = "IDOCWMACRO";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   private static final DbColumnDef CD_TEXT = new DbColumnDef
   ("TEXT", DbDataType.LONG_TEXT, (32*1024), false);
   
   private static final DbColumnDef CD_REMARKS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true);
   
   private static final DbColumnDef CD_CRTRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_CRTNDATE = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);
   
   private static final DbColumnDef CD_UPDRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);
   
   private static final DbColumnDef CD_UPDDATE = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_NAME, CD_TEXT, CD_REMARKS, CD_CRTRID, CD_CRTNDATE, CD_UPDRID, CD_UPDDATE};  
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "NAME";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true,false);
      
      DbTableFns.createTable(TN,ACD);
      
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
            
   }
   
   public static void dropTable() throws Exception
   {
      String indexName,indexName2;      
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      
      dropIndex(TN,indexName);
      dropIndex(TN,indexName2);
         
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
