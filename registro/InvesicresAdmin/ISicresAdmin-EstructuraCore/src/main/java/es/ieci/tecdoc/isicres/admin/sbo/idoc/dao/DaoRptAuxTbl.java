package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;


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
