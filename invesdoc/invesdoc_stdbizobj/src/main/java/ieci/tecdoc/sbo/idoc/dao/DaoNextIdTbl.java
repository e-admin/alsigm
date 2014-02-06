package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public class DaoNextIdTbl
{
// **************************************************************************
   
   private static final String TN = "IDOCNEXTID";

   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);    
   
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
  
   private static final DbColumnDef[] ACD = 
   {CD_TYPE, CD_ID};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);  
   
   // **************************************************************************

   private DaoNextIdTbl()
   {
   }
   
   // ***************************************************************************
   
   public static void initTblContentsNextId() throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_DIR + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_ARCH + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_FDR_LINK + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_FMT + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_MACRO + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_PICT + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_RPT + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_BTBL + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_VTBL + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_FIAUXTBL + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_DB + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_AUTONUM_TBL + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_WMACRO + ", 1 )";            
      DbUtil.executeStatement(stmtText);
      
   }
   
   // **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "TYPE,ID";
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
