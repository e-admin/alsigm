package es.ieci.tecdoc.isicres.admin.sbo.fss.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;



public class FssDaoNextIdTbl
{
// **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String TN ="IVOLNEXTID";

   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
  
   private static final DbColumnDef[] ACD = 
   {CD_TYPE, CD_ID}; 
   
   
   //  **********************************************************************
   /** Debe ejecutarse dentro de una transacción*/
   public static void initTblContentsNextId(DbConnection dbConn) throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_REP + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_VOL + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_LIST + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
      stmtText = "INSERT INTO " + TN + " VALUES( " + FssMdoUtil.NEXT_ID_TYPE_FILE + ", 1 )";            
      DbUtil.executeStatement(dbConn, stmtText);
   }
      
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "TYPE";
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
