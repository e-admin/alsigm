
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public class DaoDbCtlgTbl
{
   // **************************************************************************
   
   private static final String TN = "IDOCDBCTLG";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);    
   
   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
  
   private static final DbColumnDef CD_DBNAME = new DbColumnDef
   ("DBNAME", DbDataType.SHORT_TEXT, 32, false);
   
   private static final DbColumnDef CD_DBUSER = new DbColumnDef
   ("DBUSER", DbDataType.SHORT_TEXT, 32, false);
  
   private static final DbColumnDef CD_DBPASSWORD = new DbColumnDef
   ("DBPASSWORD", DbDataType.SHORT_TEXT, 34, false);
   
   private static final DbColumnDef CD_REMARKS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true);
  
  
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_NAME, CD_DBNAME, CD_DBUSER, CD_DBPASSWORD, CD_REMARKS};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);  
   
   
   // **************************************************************************

   private DaoDbCtlgTbl()
   {
   }
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef, indexDef2;
      
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
      String idxName, idxName2;     
      
      idxName = TN + "1";
      idxName2 = TN + "2";
      
      dropIndex(TN,idxName);      
      dropIndex(TN,idxName2);      
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
