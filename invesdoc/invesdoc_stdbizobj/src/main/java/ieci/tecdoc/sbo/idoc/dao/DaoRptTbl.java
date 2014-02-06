package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;

public class DaoRptTbl
{
// **************************************************************************
   
     private static final String TN = "IDOCRPT";
     
     private static final DbColumnDef CD_ID = new DbColumnDef
     ("ID", DbDataType.LONG_INTEGER, false);
     
     private static final DbColumnDef CD_NAME = new DbColumnDef
     ("NAME", DbDataType.SHORT_TEXT,32, false);

     private static final DbColumnDef CD_EDITTYPE = new DbColumnDef
     ("EDITTYPE", DbDataType.LONG_INTEGER, false);
     
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
     {CD_ID, CD_NAME, CD_EDITTYPE, CD_ARCHID, CD_DATA, CD_REMARKS, CD_ACCESSTYPE, CD_ACSID ,
           CN_CRTRID, CN_CRTNDATE, CN_UPDRID, CD_UPDDATE};  
     
//   **************************************************************************
     
     public static void createTable() throws Exception
     {
        String indexName,indexName2;
        String colNamesIndex,colNamesIndex2;
        DbIndexDef indexDef, indexDef2;
        
        indexName = TN + "1";
        colNamesIndex = "ID";
        indexName2 = TN + "2";
        colNamesIndex2 = "ARCHID";
        
        indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
        indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false,false);
        
        DbTableFns.createTable(TN,ACD);
        
        DbTableFns.createIndex(TN,indexDef);
        DbTableFns.createIndex(TN,indexDef2);      
     }
     
     public static void dropTable() throws Exception
     {
        String indexName,indexName2;      
        
        indexName = TN + "1";
        indexName2 = TN + "2";
        
        dropIndex(TN,indexName);
        dropIndex(TN,indexName2);
           
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
     
     protected static String getColName(DbColumnDef colDef, boolean qualified)
     {
        String colName = colDef.getName();
        
        if (qualified)
           colName =  TN + "." + colName;
        
        return colName;
     }
     
     public static String getTblName()
     {      
        return TN;
     }
     
     public static String getIdColName(boolean qualified)
     {
        return getColName(CD_ID, qualified);
     }
     
     public static String getNameColName(boolean qualified)
     {
        return getColName(CD_NAME, qualified);
     }
     
     public static String getEditTypeColName(boolean qualified) {
        return getColName(CD_EDITTYPE, qualified);
     }
     
     public static String getArchIdColName(boolean qualified)
     {
        return getColName(CD_ARCHID, qualified);
     }
     
     public static String getDataColName(boolean qualified)
     {
        return getColName(CD_DATA, qualified);
     }
     
     public static String getRemarksColName(boolean qualified)
     {
        return getColName(CD_REMARKS, qualified);
     }
     
     public static String getAccessTypeColName(boolean qualified)
     {
        return getColName(CD_ACCESSTYPE, qualified);
     }
     
     public static String getAcsIdColName(boolean qualified)
     {
        return getColName(CD_ACSID, qualified);
     }
     
     public static String getCrtIdColName(boolean qualified)
     {
        return getColName(CN_CRTRID, qualified);
     }
     
     public static String getCrtnDateColName(boolean qualified)
     {
        return getColName(CN_CRTNDATE, qualified);
     }
     
     public static String getUpdrIdColName(boolean qualified)
     {
        return getColName(CN_UPDRID, qualified);
     }
     
     public static String getUpdDateColName(boolean qualified)
     {
        return getColName(CD_UPDDATE, qualified);
     }
     
     public static void selectRow(int id, DaoOutputRow row) throws Exception 
     { 
        DbSelectFns.select(TN, row.getColumnNames(), getDefaultQual(id), row);
     }
     
     private static String getDefaultQual(int id)
     {
        return "WHERE " + CD_ID.getName() + "= " + id;
     }  
}

