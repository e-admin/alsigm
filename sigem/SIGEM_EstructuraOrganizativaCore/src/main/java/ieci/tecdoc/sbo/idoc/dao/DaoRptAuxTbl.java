package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;


public class DaoRptAuxTbl
{
// **************************************************************************
   
     private static final String TN ="IDOCRPTAUXTBL";
     
     private static final DbColumnDef CD_FDRID = new DbColumnDef
     ("FDRID", DbDataType.LONG_INTEGER, false);
     
     
     
     private static final DbColumnDef[] ACD = {CD_FDRID};  
     
//   **************************************************************************
     
     public static void createTable(DbConnection dbConn) throws Exception
     {
        String indexName;
        String colNamesIndex;
        DbIndexDef indexDef;
        
        indexName = TN + "1";
        colNamesIndex = "FDRID";        
        
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
