package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;



public class DaoNextIdTbl
{
// **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String TN ="IDOCNEXTID";

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
   
   public static void initTblContentsNextId(DbConnection dbConn) throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_DIR + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_ARCH + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_FDR_LINK + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_FMT + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_MACRO + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_PICT + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_RPT + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_BTBL + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_VTBL + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_FIAUXTBL + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_DB + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_AUTONUM_TBL + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + DaoDefs.NEXT_ID_TYPE_WMACRO + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
   }
   
   // **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "TYPE,ID";
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
