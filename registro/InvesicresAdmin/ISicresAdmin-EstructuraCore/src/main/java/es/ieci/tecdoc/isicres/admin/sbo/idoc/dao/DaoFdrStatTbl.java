
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;

public final class DaoFdrStatTbl
{
   
   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String TN ="IDOCFDRSTAT";

   private static final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);
    
   private static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_STAT = new DbColumnDef
   ("STAT", DbDataType.LONG_INTEGER, false); 
      
   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_TS = new DbColumnDef
   ("TIMESTAMP", DbDataType.DATE_TIME, false);    

   private static final DbColumnDef CD_FLAGS = new DbColumnDef
   ("FLAGS", DbDataType.LONG_INTEGER, false);
  
   private static final DbColumnDef[] ACD = 
   {CD_FDRID, CD_ARCHID, CD_STAT, CD_USERID, CD_TS, CD_FLAGS};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);     
   
   // **************************************************************************

   private static String getDefaultQual(int archId, int fdrId)
   {
      return "WHERE " + CD_FDRID.getName() + "= " + fdrId +
             " AND " + CD_ARCHID.getName() + "= " + archId;
   }   
      
   // **************************************************************************
   
   private DaoFdrStatTbl()
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
   
   public static String getFdrIdColName(boolean qualified)
   {
      return getColName(CD_FDRID, qualified);
   }
   
   public static String getArchIdColName(boolean qualified)
   {
      return getColName(CD_ARCHID, qualified);
   }
   
   public static String getStatColName(boolean qualified)
   {
      return getColName(CD_STAT, qualified);
   }
   
   public static String getUserIdColName(boolean qualified)
   {
      return getColName(CD_USERID, qualified);
   }
   
   public static String getTSColName(boolean qualified)
   {
      return getColName(CD_TS, qualified);
   }
   
   public static String getFlagsColName(boolean qualified)
   {
      return getColName(CD_FLAGS, qualified);
   }
   
   
   // **************************************************************************

   public static void insertRow(DbConnection dbConn, DaoFdrStatRow row)
                      throws Exception
   {
      DbInsertFns.insert(dbConn, TN, ACN, row);
   }
   
   // **************************************************************************
   public static void deleteLockedRow(DbConnection dbConn, int archId, int fdrId, int userId)            
                                   throws Exception
   { 

      String qual;
      
      qual = "WHERE " + CD_FDRID.getName() + "=" + fdrId + 
             " AND " + CD_ARCHID.getName() + "=" + archId + 
             " AND " + CD_USERID.getName() + "=" + userId + 
             " AND " + CD_STAT.getName() + "=" + DaoDefs.FDR_STAT_LOCKED;  

      DbDeleteFns.delete(dbConn, TN, qual);

   }
   
   // **************************************************************************
   public static void selectRow(DbConnection dbConn, int archId, int fdrId, DaoOutputRow row)            
                      throws Exception
   { 

      DbSelectFns.select(dbConn, TN, row.getColumnNames(), getDefaultQual(archId, fdrId),
                         row);

   }
   
   // **************************************************************************
   public static void lockRow(DbConnection dbConn, int archId, int fdrId)  throws Exception
   { 

      DbUpdateFns.updateLongInteger(dbConn, TN, CD_FDRID.getName(), fdrId,
                                    getDefaultQual(archId, fdrId));

   }
   
// **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef, indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ARCHID, FDRID";
      indexName2 = TN + "2";
      colNamesIndex2 = "STAT";
      
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
   
   
   
} // class
