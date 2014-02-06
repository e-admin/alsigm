package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public class UasDaoNextIdTbl
{
// **************************************************************************
   
   private static final String TN = "IUSERNEXTID";

   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
  
   private static final DbColumnDef[] ACD = 
   {CD_TYPE, CD_ID}; 
   
   public static final int NEXT_ID_TYPE_USER  = 1;
   public static final int NEXT_ID_TYPE_DEPT  = 2;
   public static final int NEXT_ID_TYPE_GROUP = 3;
   public static final int NEXT_ID_TYPE_OBJ   = 4;
   public static final int NEXT_ID_TYPE_CNT   = 5;
   
   // ***********************************************************************
   
   public static void initTblContentsNextId() throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( " + NEXT_ID_TYPE_USER + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + NEXT_ID_TYPE_DEPT + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + NEXT_ID_TYPE_GROUP + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + NEXT_ID_TYPE_OBJ + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + NEXT_ID_TYPE_CNT + ", 1 )";            
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
