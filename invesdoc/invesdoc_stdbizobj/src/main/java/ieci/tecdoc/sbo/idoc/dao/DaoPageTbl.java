
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;

public final class DaoPageTbl
{
   
   // **************************************************************************
  
   private final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 254, false);
   
   private final DbColumnDef CD_SORTORDER = new DbColumnDef
   ("SORTORDER", DbDataType.LONG_INTEGER, false); 
   
   private final DbColumnDef CD_DOCID = new DbColumnDef
   ("DOCID", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_FILEID = new DbColumnDef
   ("FILEID", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_VOLID = new DbColumnDef
   ("VOLID", DbDataType.LONG_INTEGER, false);
   
    private static final DbColumnDef CD_LOC = new DbColumnDef
   ("LOC", DbDataType.SHORT_TEXT, 254, false);
   
   private final DbColumnDef CD_ANNID = new DbColumnDef
   ("ANNID", DbDataType.LONG_INTEGER, true);
   
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
   
   private final DbColumnDef CD_ACCRID = new DbColumnDef
   ("ACCRID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_ACCDATE = new DbColumnDef
   ("ACCDATE", DbDataType.DATE_TIME, false);
   
   private final DbColumnDef CD_ACCCOUNT = new DbColumnDef
   ("ACCCOUNT", DbDataType.LONG_INTEGER, false); 
  
   private final DbColumnDef[] ACD = 
   {CD_ID, CD_FDRID, CD_NAME, CD_SORTORDER, CD_DOCID, CD_FILEID, CD_VOLID, CD_LOC,
    CD_ANNID, CD_STAT, CD_REFCOUNT, CD_REMS, CD_ACSTYPE, CD_ACSID,CD_CRTUSRID, CD_CRTTS,
    CD_UPDUSRID, CD_UPDTS, CD_ACCRID, CD_ACCDATE, CD_ACCCOUNT};   
  
   private final String ACN = DbUtil.getColumnNames(ACD); 
   
   private String m_tblName; 
   
   // **************************************************************************

   private String getDefaultQual(int id, int fdrId)
   {
      return "WHERE " + CD_ID.getName() + "= " + id + " AND " +
             CD_FDRID.getName() + "= " + fdrId;
   }  
   
   private String getQualByFdrId(int fdrId, boolean orderBySortOrder)
   {
      String qual;
      
      qual =  "WHERE " + CD_FDRID.getName() + "= " + fdrId;             
      
      if (orderBySortOrder)
         qual = qual + " ORDER BY " + CD_SORTORDER.getName();
      
      return qual;
   } 
      
   // **************************************************************************
   
   public DaoPageTbl(String tblName)
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
   
   public String getSortOrderColName(boolean qualified)
   {
      return getColName(CD_SORTORDER, qualified);
   }
   
   public String getDocIdColName(boolean qualified)
   {
      return getColName(CD_DOCID, qualified);
   }
   
   public String getFileIdColName(boolean qualified)
   {
      return getColName(CD_FILEID, qualified);
   }
   
   public String getVolIdColName(boolean qualified)
   {
      return getColName(CD_VOLID, qualified);
   }
   
   public String getLocColName(boolean qualified)
   {
      return getColName(CD_LOC, qualified);
   }
   
   public String getAnnIdColName(boolean qualified)
   {
      return getColName(CD_ANNID, qualified);
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
   
   public String getAccessUserIdColName(boolean qualified)
   {
      return getColName(CD_ACCRID, qualified);
   }
   
   public String getAccessTSColName(boolean qualified)
   {
      return getColName(CD_ACCDATE, qualified);
   }
   
   public String getAccessCountColName(boolean qualified)
   {
      return getColName(CD_ACCCOUNT, qualified);
   } 
   
   // **************************************************************************
   
   public void insertRow(DaoPageRow row) throws Exception
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
      DbDeleteFns.delete(m_tblName, getQualByFdrId(fdrId, false));
   }
   
   // **************************************************************************
   
   public void selectFolderRows(int fdrId, DaoOutputXRows rows)
                               throws Exception
   {  
      
      String qual;              
      
      qual = getQualByFdrId(fdrId, true);
      
      DbSelectFns.select(m_tblName, rows.getColumnNames(m_tblName),
                         qual, false, rows);
            
   }
   
    public IeciTdLongIntegerArrayList selectFolderFileIds(int fdrId)
                                      throws Exception
   {  
      
      IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();   
            
      DbSelectFns.select(m_tblName, CD_FILEID.getName(),
                         getQualByFdrId(fdrId, false), false, vals);

      return vals;
         
   }
    
    //  **************************************************************************************
    
    public void createTable() throws Exception
    {
       String indexName;
       String colNamesIndex;
       DbIndexDef idxDef1,idxDef2,idxDef3,idxDef4;
       
       
       indexName = m_tblName + "1";
       colNamesIndex = "FDRID,ID";
       idxDef1 = new DbIndexDef(indexName,colNamesIndex,true,false);
       indexName = m_tblName + "2";
       colNamesIndex = "FDRID";
       idxDef2 = new DbIndexDef(indexName,colNamesIndex,false,false);
       indexName = m_tblName + "3";
       colNamesIndex = "FDRID,DOCID";
       idxDef3 = new DbIndexDef(indexName,colNamesIndex,false,false);
       indexName = m_tblName + "4";
       colNamesIndex = "FILEID";
       idxDef4 = new DbIndexDef(indexName,colNamesIndex,false,false);
             
       DbTableFns.createTable(m_tblName,ACD);   
       DbTableFns.createIndex(m_tblName,idxDef1);
       DbTableFns.createIndex(m_tblName,idxDef2);
       DbTableFns.createIndex(m_tblName,idxDef3);
       DbTableFns.createIndex(m_tblName,idxDef4);
       
    } 
   
} // class
