
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;



public final class DaoArchDetTbl
{
   
   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String TN =  "IDOCARCHDET";

   private static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_DETTYPE = new DbColumnDef
   ("DETTYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_DETVAL = new DbColumnDef
   ("DETVAL", DbDataType.LONG_TEXT, 51200, false); 
  
   private static final DbColumnDef[] ACD = 
   {CD_ARCHID, CD_DETTYPE, CD_DETVAL};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);    
   
   // **************************************************************************

   private static String getDefaultQual(int archId)
   {
      return "WHERE " + CD_ARCHID.getName() + "= " + archId;
   }   
      
   // **************************************************************************
   
   private DaoArchDetTbl()
   {
   }
   
   // **************************************************************************
      
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
   
   public static String getArchIdColName(boolean qualified)
   {
      return getColName(CD_ARCHID, qualified);
   }
   
   public static String getDetTypeColName(boolean qualified)
   {
      return getColName(CD_DETTYPE, qualified);
   }
   
   public static String getDetValColName(boolean qualified)
   {
      return getColName(CD_DETVAL, qualified);
   }   
      
   // **************************************************************************
   
   public static DaoArchDetRows selectAllColumnRows(DbConnection dbConn, int archId) throws Exception
   {  
      
      String qual;
      
      DaoArchDetRows rows = new DaoArchDetRows();       
      
      DbSelectFns.select(dbConn, TN, ACN, getDefaultQual(archId), false, rows);
      
      return rows;
      
   }
   
   public static String selectDetVal(DbConnection dbConn, int archId, int detType) throws Exception
   {  
      
      String qual;
      String detVal;
      
      qual = " WHERE " + CD_ARCHID.getName() + "= " + archId +
             " AND " + CD_DETVAL.getName() + "=" + detType;
      
      detVal = DbSelectFns.selectLongText(dbConn, TN, CD_DETVAL.getName(),
                                          qual);
      
      return detVal;
      
   }
   
// **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef, indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ARCHID,DETTYPE";
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
      String idxName, idxName2;
      
      idxName = "TN" + "1";
      idxName2 = "TN" + "2";
      
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
   
} // class
