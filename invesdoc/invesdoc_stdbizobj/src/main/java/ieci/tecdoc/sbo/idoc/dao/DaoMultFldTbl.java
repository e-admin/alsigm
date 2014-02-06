
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoMultFldTbl
{
   
   // **************************************************************************
   
   private final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_FLDID = new DbColumnDef
   ("FLDID", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_SORTORDER = new DbColumnDef
   ("SORTORDER", DbDataType.LONG_INTEGER, false); 
   
   private final String COL_VALUE_NAME = "VALUE";
   private final int    COL_VALUE_LEN  = 254;
   
   private final int    NUM_COLS = 4;
   
   private DbColumnDef[] m_colsDef;
   
   private String m_tblName; 
   private int    m_fldDbType;
   
// **************************************************************************
   
   private String getDefaultQual(int fdrId)
   {
      return "WHERE " + CD_FDRID.getName() + "= " + fdrId;
   }
   
// **************************************************************************
   
   private String getDefaultQualOrderBy(int fdrId)
   {
      return "WHERE " + CD_FDRID.getName() + "= " + fdrId +
             " ORDER BY " + CD_FLDID.getName()+ "," +
             CD_SORTORDER.getName();
   }
   
// **************************************************************************
   
   private String getDefaultQual(int fdrId, int fldId, boolean orderBySortOrder)
   {
      String qual;
      
      qual =  " WHERE " + CD_FDRID.getName() + "= " + fdrId +
             " AND " + CD_FLDID.getName() + "= " + fldId;
      
      if (orderBySortOrder)
         qual = qual + " ORDER BY " + CD_SORTORDER.getName();
      
      return qual;
   }  
   
   private String getDefaultQual(int fdrId, int fldId, int sortOrder)
   {
      return "WHERE " + CD_FDRID.getName() + "= " + fdrId +
             " AND " + CD_FLDID.getName() + "= " + fldId +
             " AND " + CD_SORTORDER.getName() + "= " + sortOrder;
   } 
      
   // **************************************************************************
   
   public DaoMultFldTbl(String tblName,int fldDbType) throws Exception
   {
      DbColumnDef colValue = null;
      int         dbType   = fldDbType; 
   
      m_tblName   = tblName;
      m_fldDbType = fldDbType;
      
      if (dbType == DbDataType.SHORT_TEXT)     
         colValue = new DbColumnDef(COL_VALUE_NAME, dbType, COL_VALUE_LEN, false);
      else
         colValue = new DbColumnDef(COL_VALUE_NAME, dbType, false);
      
      m_colsDef = new DbColumnDef[NUM_COLS];
      
      m_colsDef[0] = CD_FDRID;
      m_colsDef[1] = CD_FLDID;
      m_colsDef[2] = colValue;
      m_colsDef[3] = CD_SORTORDER;  
      
   }
   
   // **************************************************************************   
   
   protected String getColName(DbColumnDef colDef, boolean qualified)
   {
      String colName = colDef.getName();
      
      if (qualified)
         colName =  m_tblName + "." + colName;
      
      return colName;
   }
   
   public String getTblName()
   {      
      return m_tblName;
   }
   
   public String getFdrIdColName(boolean qualified)
   {
      return getColName(CD_FDRID, qualified);
   }
   
    
   public String getFldIdColName(boolean qualified)
   {  
      return getColName(CD_FLDID, qualified);     
   }
   
   public String getValueColName(boolean qualified)
   {  
      String colName = COL_VALUE_NAME;
      
      if (qualified)
         colName =  m_tblName + "." + colName;
      
      return colName;
   }
   
   public String getSortOrderColName(boolean qualified)
   {  
      return getColName(CD_SORTORDER, qualified);          
   }
   
   protected String getAllColumnNames()
   {
      StringBuffer tbdr;
            
      tbdr = new StringBuffer();

      tbdr.append(getFdrIdColName(false));      
      tbdr.append(",").append(getFldIdColName(false)); 
      tbdr.append(",").append(getValueColName(false));
      tbdr.append(",").append(getSortOrderColName(false));    

      return tbdr.toString();
   }
   
   
   // **************************************************************************
   
   public void insertRow(DaoMultFldRow row) throws Exception
   { 
       
      DbInsertFns.insert(m_tblName, DbUtil.getColumnNames(m_colsDef), row);      
      
   }
   
  // **************************************************************************

   public  void deleteRow(int fdrId, int fldId, int sortOrder) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(fdrId, fldId, sortOrder));
   }
   
   public  void deleteRows(int fdrId, int fldId) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(fdrId, fldId, false));
   } 
   
   public  void deleteFolderRows(int fdrId) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(fdrId));
   }   
   
 // **************************************************************************
   
   public DaoMultFldRows selectAllColumnRows(int fdrId)
                         throws Exception
   {
      DaoMultFldRows rows = new DaoMultFldRows(m_fldDbType);
      
      DbSelectFns.select(m_tblName, getAllColumnNames(),
                         getDefaultQualOrderBy(fdrId), false, rows);
                         
      return rows;
   }
   
   public void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef idxDef1,idxDef2;
      
      indexName = m_tblName + "1";
      colNamesIndex = "FDRID,FLDID,SORTORDER";
      idxDef1 = new DbIndexDef(indexName,colNamesIndex,true,false);
      indexName = m_tblName + "2";
      colNamesIndex = "FDRID,FLDID";
      idxDef2 = new DbIndexDef(indexName,colNamesIndex,false,false);
      
      DbTableFns.createTable(m_tblName,m_colsDef);   
      DbTableFns.createIndex(m_tblName,idxDef1);
      DbTableFns.createIndex(m_tblName,idxDef2);
   
   }

   
} // class
