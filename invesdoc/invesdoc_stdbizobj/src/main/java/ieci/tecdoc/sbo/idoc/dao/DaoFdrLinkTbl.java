
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoFdrLinkTbl
{
   
   // **************************************************************************
   
   private static final String TN = "IDOCFDRLINK";
   private static final String TNX = "IDOCFDRLINKX";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, true);   
   
   private static final DbColumnDef CD_CNTRDBID = new DbColumnDef
   ("CNTRDBID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_CNTRARCHID = new DbColumnDef
   ("CNTRARCHID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_CNTRFDRID = new DbColumnDef
   ("CNTRFDRID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_CNTRCLFID = new DbColumnDef
   ("CNTRCLFID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_SRVRDBID = new DbColumnDef
   ("SRVRDBID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_SRVRARCHID = new DbColumnDef
   ("SRVRARCHID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_SRVRFDRID = new DbColumnDef
   ("SRVRFDRID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_FLAGS = new DbColumnDef
   ("FLAGS", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_STAT = new DbColumnDef
   ("STAT", DbDataType.LONG_INTEGER, false);    
   
   private static final DbColumnDef CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);   
  
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_NAME, CD_CNTRDBID, CD_CNTRARCHID, CD_CNTRFDRID, CD_CNTRCLFID, 
    CD_SRVRDBID, CD_SRVRARCHID, CD_SRVRFDRID, CD_FLAGS, CD_STAT,
    CD_CRTUSRID, CD_CRTTS};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);    
   
   private static final int DEFAULT_DB_ID = 1;
   
   // **************************************************************************

   private static String getQualByCntr(int archId, int fdrId)
   {
      return "WHERE " + CD_CNTRDBID.getName() + "= " + DEFAULT_DB_ID +
             " AND " + CD_CNTRARCHID.getName() + "= " + archId +
             " AND " + CD_CNTRFDRID.getName() + "= " + fdrId;
   }   
      
   // **************************************************************************
   
   private DaoFdrLinkTbl()
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
   
   public static String getIdColName(boolean qualified)
   {
      return getColName(CD_ID, qualified);
   }
   
   public static String getNameColName(boolean qualified)
   {
      return getColName(CD_NAME, qualified);
   }
   
   public static String getContainerDbIdColName(boolean qualified)
   {
      return getColName(CD_CNTRDBID, qualified);
   }
   
   public static String getContainerArchIdColName(boolean qualified)
   {
      return getColName(CD_CNTRARCHID, qualified);
   }  
   
   public static String getContainerFdrIdColName(boolean qualified)
   {
      return getColName(CD_CNTRFDRID, qualified);
   } 
   
   public static String getContainerClfIdColName(boolean qualified)
   {
      return getColName(CD_CNTRCLFID, qualified);
   } 
   
   public static String getServerDbIdColName(boolean qualified)
   {
      return getColName(CD_SRVRDBID, qualified);
   }
   
   public static String getServerArchIdColName(boolean qualified)
   {
      return getColName(CD_SRVRARCHID, qualified);
   }  
   
   public static String getServerFdrIdColName(boolean qualified)
   {
      return getColName(CD_SRVRFDRID, qualified);
   } 
   
   public static String getFlagsColName(boolean qualified)
   {
      return getColName(CD_FLAGS, qualified);
   } 
   
   public static String getStatColName(boolean qualified)
   {
      return getColName(CD_STAT, qualified);
   } 
   
   public static String getCrtUserIdColName(boolean qualified)
   {
      return getColName(CD_CRTUSRID, qualified);
   } 
   
   public static String getCrtTSColName(boolean qualified)
   {
      return getColName(CD_CRTTS, qualified);
   }       
   
   // **************************************************************************
   
   public static void selectRowsByCntr(int archId, int fdrId, DaoOutputRows rows)                                                            
                                          throws Exception
   {       
            
      DbSelectFns.select(TN, rows.getColumnNames(), getQualByCntr(archId, fdrId),
                         false, rows);     
      
   }
   
   // **************************************************************************
      
   public static void deleteCntrRows(int archId,int fdrId) throws Exception
   {       
      DbDeleteFns.delete(TN, getQualByCntr(archId, fdrId));
   }  
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      createTable(TN);
      //SE CREA TABLA COPIA
      createTable(TNX);     
   }
   
   private static void createTable(String tblName) throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2,colNamesIndex3;
      DbIndexDef indexDef,indexDef2,indexDef3;
      
      indexName = tblName + "1";
      colNamesIndex = "ID";        
      indexName2 = tblName + "2";
      colNamesIndex2 = "CNTRDBID,CNTRARCHID,CNTRFDRID,CNTRCLFID";
      indexName3 = tblName + "3";
      colNamesIndex3 = "SRVRDBID,SRVRARCHID,SRVRFDRID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);  
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false,false);
      
      DbTableFns.createTable(tblName,ACD);
      
      DbTableFns.createIndex(tblName,indexDef);
      DbTableFns.createIndex(tblName,indexDef2);
      DbTableFns.createIndex(tblName,indexDef3);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName,indexName2,indexName3;      
      
      indexName = TN + "1";              
      indexName2 = TN + "2";      
      indexName3 = TN + "3";      
      
      dropIndex(TN,indexName);
      dropIndex(TN,indexName2);
      dropIndex(TN,indexName3);
         
      DbTableFns.dropTable(TN);
      
      indexName = TNX + "1";              
      indexName2 = TNX + "2";      
      indexName3 = TNX + "3";      
      
      dropIndex(TNX,indexName);
      dropIndex(TNX,indexName2);
      dropIndex(TNX,indexName3);
         
      DbTableFns.dropTable(TNX);
      
      
   }
   
   private static void droptable(String tblName)
   {
      try
      {
         DbTableFns.dropTable(tblName);
      }
      catch(Exception e)
      {         
      }
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
