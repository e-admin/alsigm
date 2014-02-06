
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoFdrStatTbl
{
   
   // **************************************************************************
   
   private static final String TN = "IDOCFDRSTAT";

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

   public static void insertRow(DaoFdrStatRow row)
                      throws Exception
   {
      DbInsertFns.insert(TN, ACN, row);
   }
   
   // **************************************************************************
   public static void deleteLockedRow(int archId, int fdrId, int userId)            
                                   throws Exception
   { 

      String qual;
      
      qual = "WHERE " + CD_FDRID.getName() + "=" + fdrId + 
             " AND " + CD_ARCHID.getName() + "=" + archId + 
             " AND " + CD_USERID.getName() + "=" + userId + 
             " AND " + CD_STAT.getName() + "=" + DaoDefs.FDR_STAT_LOCKED;  

      DbDeleteFns.delete(TN, qual);

   }
   
   // **************************************************************************
   public static void selectRow(int archId, int fdrId, DaoOutputRow row)            
                      throws Exception
   { 

      DbSelectFns.select(TN, row.getColumnNames(), getDefaultQual(archId, fdrId),
                         row);

   }
   
   // **************************************************************************
   public static void lockRow(int archId, int fdrId)  throws Exception
   { 

      DbUpdateFns.updateLongInteger(TN, CD_FDRID.getName(), fdrId,
                                    getDefaultQual(archId, fdrId));

   }
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef, indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ARCHID, FDRID";
      indexName2 = TN + "2";
      colNamesIndex2 = "STAT";
      
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
   
   
   
} // class
