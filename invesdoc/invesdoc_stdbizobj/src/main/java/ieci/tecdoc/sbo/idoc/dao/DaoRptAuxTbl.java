package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;

public class DaoRptAuxTbl
{
// **************************************************************************
   
     private static final String TN = "IDOCRPTAUXTBL";
     
     private static final DbColumnDef CD_FDRID = new DbColumnDef
     ("FDRID", DbDataType.LONG_INTEGER, false);
     
     
     
     private static final DbColumnDef[] ACD = {CD_FDRID};  
     
//   **************************************************************************
     
     public static void createTable() throws Exception
     {
        String indexName;
        String colNamesIndex;
        DbIndexDef indexDef;
        
        indexName = TN + "1";
        colNamesIndex = "FDRID";        
        
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
