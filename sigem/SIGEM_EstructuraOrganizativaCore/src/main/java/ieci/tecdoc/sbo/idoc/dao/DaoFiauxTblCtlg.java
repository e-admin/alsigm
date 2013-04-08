package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;


public class DaoFiauxTblCtlg
{
// **************************************************************************
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
     private static final String TN ="IDOCFIAUXTBLCTLG";
     
     private static final DbColumnDef CD_ID = new DbColumnDef
     ("ID", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CD_NAME = new DbColumnDef
     ("NAME", DbDataType.SHORT_TEXT,16, false);

     private static final DbColumnDef CD_USERID = new DbColumnDef
     ("USERID", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CD_TIMESTAMP = new DbColumnDef
     ("TIMESTAMP", DbDataType.DATE_TIME, false);
     
     private static final DbColumnDef[] ACD = 
     {CD_ID, CD_NAME, CD_USERID, CD_TIMESTAMP};  
     
//   **************************************************************************
     
     public static void createTable(DbConnection dbConn) throws Exception
     {
        String indexName,indexName2,indexName3;
        String colNamesIndex,colNamesIndex2,colNamesIndex3;
        DbIndexDef indexDef, indexDef2, indexDef3;
        
        indexName = TN + "1";
        colNamesIndex = "ID";
        indexName2 = TN + "2";
        colNamesIndex2 = "NAME";
        indexName3 = TN + "3";
        colNamesIndex3 = "USERID";
        
        indexDef= new DbIndexDef(indexName,colNamesIndex,true);
        indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
        indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false);
        
        DbTableFns.createTable(dbConn, TN,ACD);
        
        DbTableFns.createIndex(dbConn, TN,indexDef);
        DbTableFns.createIndex(dbConn, TN,indexDef2);
        DbTableFns.createIndex(dbConn, TN,indexDef3);
     }
     
     public static void dropTable(DbConnection dbConn) throws Exception
     {
        String indexName,indexName2,indexName3;      
        
        indexName = TN + "1";
        indexName2 = TN + "2";
        indexName3 = TN + "3";
        
        dropIndex(dbConn, TN,indexName);
        dropIndex(dbConn, TN,indexName2);
        dropIndex(dbConn, TN,indexName3);
           
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
