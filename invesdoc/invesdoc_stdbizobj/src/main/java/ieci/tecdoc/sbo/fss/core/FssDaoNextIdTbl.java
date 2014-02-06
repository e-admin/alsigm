package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public class FssDaoNextIdTbl
{
// **************************************************************************
   
   private static final String TN = "IVOLNEXTID";

   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
  
   private static final DbColumnDef[] ACD = 
   {CD_TYPE, CD_ID}; 
   
   
   //  **********************************************************************
   /** Debe ejecutarse dentro de una transacción*/
   public static void initTblContentsNextId() throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_REP + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_VOL + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_LIST + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_FILE + ", 1 )";            
      DbUtil.executeStatement(stmtText);
   }
      
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "TYPE";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,true);
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
