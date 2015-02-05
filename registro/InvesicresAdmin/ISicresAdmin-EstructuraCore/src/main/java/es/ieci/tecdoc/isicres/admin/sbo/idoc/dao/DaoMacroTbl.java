
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;



public class DaoMacroTbl
{
// **************************************************************************
  /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String TN ="IDOCMACRO";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT,32, false);

   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_LANGUAGE = new DbColumnDef
   ("LANGUAGE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_TEXT = new DbColumnDef
   ("TEXT", DbDataType.LONG_TEXT, (32*1024), false);
   
   private static final DbColumnDef CD_REMARKS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true);
   
   private static final DbColumnDef CN_CRTRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CN_CRTNDATE = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);
   
   private static final DbColumnDef CN_UPDRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);
   
   private static final DbColumnDef CD_UPDDATE = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
        
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_NAME, CD_TYPE, CD_LANGUAGE, CD_TEXT, CD_REMARKS, 
         CN_CRTRID, CN_CRTNDATE, CN_UPDRID, CD_UPDDATE};  
   
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
      String indexName,indexName2;      
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      
      dropIndex(dbConn, TN,indexName);
      dropIndex(dbConn, TN,indexName2);
         
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
