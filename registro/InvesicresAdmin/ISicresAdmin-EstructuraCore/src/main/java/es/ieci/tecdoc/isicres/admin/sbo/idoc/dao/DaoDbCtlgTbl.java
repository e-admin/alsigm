
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;

public class DaoDbCtlgTbl
{
   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
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
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef, indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "NAME";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
      
      DbTableFns.createTable(dbConn, TN,ACD);
      
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String idxName, idxName2;     
      
      idxName = TN + "1";
      idxName2 = TN + "2";
      
      dropIndex(dbConn, TN,idxName);      
      dropIndex(dbConn, TN,idxName2);      
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
