
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoClfTbl
{
   
   // **************************************************************************
   
   private final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   private final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false); 
   
   private final DbColumnDef CD_PARENTID = new DbColumnDef
   ("PARENTID", DbDataType.LONG_INTEGER, false); 
   
   private final DbColumnDef CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_INTEGER, false); 
   
   private final DbColumnDef CD_STAT = new DbColumnDef
   ("STAT", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_REFCOUNT = new DbColumnDef
   ("REFCOUNT", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_REMS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true); 
   
   private static final DbColumnDef CD_ACSTYPE = new DbColumnDef
   ("ACCESSTYPE", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_ACSID = new DbColumnDef
   ("ACSID", DbDataType.LONG_INTEGER, false);    
   
   private final DbColumnDef CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);

   private final DbColumnDef CD_UPDUSRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   private final DbColumnDef CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);   
  
   private final DbColumnDef[] ACD = 
   {CD_ID, CD_FDRID, CD_NAME, CD_TYPE, CD_PARENTID, CD_INFO,
    CD_STAT, CD_REFCOUNT, CD_REMS, CD_ACSTYPE, CD_ACSID,CD_CRTUSRID, CD_CRTTS,
    CD_UPDUSRID, CD_UPDTS};   
  
   private final String ACN = DbUtil.getColumnNames(ACD); 
     
   private String m_tblName; 
   
   // **************************************************************************

   private String getDefaultQual(int id, int fdrId)
   {
      return "WHERE " + CD_ID.getName() + "= " + id + " AND " +
             CD_FDRID.getName() + "= " + fdrId;
   } 
   
   private String getQualByFdrId(int fdrId, boolean orderByName)
   {
      String qual;
      
      qual =  "WHERE " + CD_FDRID.getName() + "= " + fdrId;
      
      if (orderByName)
         qual = qual + " ORDER BY " + CD_NAME.getName();
      else
         qual = qual + " ORDER BY " + CD_ID.getName();
      
      return qual;
   }  
      
   // **************************************************************************
   
   public DaoClfTbl(String tblName)
   {
      m_tblName = tblName;
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
   
   public String getIdColName(boolean qualified)
   {
      return getColName(CD_ID, qualified);
   }
   
   public String getFdrIdColName(boolean qualified)
   {
      return getColName(CD_FDRID, qualified);
   }
   
   public String getNameColName(boolean qualified)
   {
      return getColName(CD_NAME, qualified);
   }
   
   public String getTypeColName(boolean qualified)
   {
      return getColName(CD_TYPE, qualified);
   }
   
   public String getParentIdColName(boolean qualified)
   {
      return getColName(CD_PARENTID, qualified);
   }
   
   public String getInfoColName(boolean qualified)
   {
      return getColName(CD_INFO, qualified);
   }
   
   public String getStatColName(boolean qualified)
   {
      return getColName(CD_STAT, qualified);
   }
   
   public String getRefCountColName(boolean qualified)
   {
      return getColName(CD_REFCOUNT, qualified);
   }
   
   public String getRemarksColName(boolean qualified)
   {
      return getColName(CD_REMS, qualified);
   }
   
   public String getAccessTypeColName(boolean qualified)
   {
      return getColName(CD_ACSTYPE, qualified);
   }
   
   public String getAcsIdColName(boolean qualified)
   {
      return getColName(CD_ACSID, qualified);
   }
   
   public String getCrtUserIdColName(boolean qualified)
   {
      return getColName(CD_CRTUSRID, qualified);
   }
   
   public String getCrtTSColName(boolean qualified)
   {
      return getColName(CD_CRTTS, qualified);
   }
   
   public String getUpdUserIdColName(boolean qualified)
   {
      return getColName(CD_UPDUSRID, qualified);
   }
   
   public String getUpdTSColName(boolean qualified)
   {
      return getColName(CD_UPDTS, qualified);
   }    
    
  // **************************************************************************
   
   public void insertRow(DaoClfRow row) throws Exception
   {  
     
      DbInsertFns.insert(m_tblName, ACN, row);      
      
   }
   
   // **************************************************************************
    
   public void updateRow(int id, int fdrId, DaoInputXRow row) throws Exception
   {  
      DbUpdateFns.update(m_tblName, row.getColumnNames(m_tblName), row, 
                         getDefaultQual(id, fdrId));
   }    
    
   // **************************************************************************

   public  void deleteRow(int id, int fdrId) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(id, fdrId));
   }
   
   public  void deleteFolderRows(int fdrId) throws Exception
   {
      String qual;
      
      qual =  "WHERE " + CD_FDRID.getName() + "= " + fdrId;
      
      DbDeleteFns.delete(m_tblName, qual);
   }
   
   // **************************************************************************
   
   public void selectFolderRows(int fdrId, boolean orderByName, 
                                DaoOutputXRows rows)
               throws Exception
   {  
      
      String qual;             
      
      qual = getQualByFdrId(fdrId, orderByName);
      
      DbSelectFns.select(m_tblName, rows.getColumnNames(m_tblName), qual, false, rows);
          
   }
   
   //**************************************************************************************
   
   public void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef idxDef1;
      DbIndexDef idxDef2;      
      
      indexName = m_tblName + "1";
      colNamesIndex = "FDRID,ID";
      idxDef1 = new DbIndexDef(indexName,colNamesIndex,true,false);
      indexName = m_tblName + "2";
      colNamesIndex = "FDRID";
      idxDef2 = new DbIndexDef(indexName,colNamesIndex,false,false);
            
      DbTableFns.createTable(m_tblName,ACD);   
      DbTableFns.createIndex(m_tblName,idxDef1);
      DbTableFns.createIndex(m_tblName,idxDef2);
      
   }
   
   
} // class
