package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;


public class DaoRptTbl
{
// **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
     private static final String TN ="IDOCRPT";
     
     private static final DbColumnDef CD_ID = new DbColumnDef
     ("ID", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CD_NAME = new DbColumnDef
     ("NAME", DbDataType.SHORT_TEXT,32, false);

     private static final DbColumnDef CD_ARCHID = new DbColumnDef
     ("ARCHID", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CD_DATA = new DbColumnDef
     ("DATA", DbDataType.LONG_TEXT, (300*1024), false);
     
     private static final DbColumnDef CD_REMARKS = new DbColumnDef
     ("REMARKS", DbDataType.SHORT_TEXT, 254, true);
     
     private static final DbColumnDef CD_ACCESSTYPE = new DbColumnDef
     ("ACCESSTYPE", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CD_ACSID = new DbColumnDef
     ("ACSID", DbDataType.LONG_INTEGER, false); 
     
     private static final DbColumnDef CN_CRTRID = new DbColumnDef
     ("CRTRID", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CN_CRTNDATE = new DbColumnDef
     ("CRTNDATE", DbDataType.DATE_TIME, false);
     
     private static final DbColumnDef CN_UPDRID = new DbColumnDef
     ("UPDRID", DbDataType.LONG_INTEGER, true);
     
     private static final DbColumnDef CD_UPDDATE = new DbColumnDef
     ("UPDDATE", DbDataType.DATE_TIME, true);
     
     private static final DbColumnDef[] ACD = 
     {CD_ID, CD_NAME, CD_ARCHID, CD_DATA, CD_REMARKS, CD_ACCESSTYPE, CD_ACSID ,
           CN_CRTRID, CN_CRTNDATE, CN_UPDRID, CD_UPDDATE};  
     
//   **************************************************************************
     
     public static void createTable(DbConnection dbConn) throws Exception
     {
        String indexName,indexName2;
        String colNamesIndex,colNamesIndex2;
        DbIndexDef indexDef, indexDef2;
        
        indexName = TN + "1";
        colNamesIndex = "ID";
        indexName2 = TN + "2";
        colNamesIndex2 = "ARCHID";
        
        indexDef= new DbIndexDef(indexName,colNamesIndex,true);
        indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false);
        
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

