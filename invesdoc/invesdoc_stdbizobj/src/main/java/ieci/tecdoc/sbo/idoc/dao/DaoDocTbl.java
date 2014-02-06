
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoDocTbl
{
   
   // **************************************************************************

   public static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   public static final DbColumnDef CD_CLFID = new DbColumnDef
   ("CLFID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false); 
   
   public static final DbColumnDef CD_TITLE = new DbColumnDef
   ("TITLE", DbDataType.SHORT_TEXT, 128, true);
   
   public static final DbColumnDef CD_AUTHOR = new DbColumnDef
   ("AUTHOR", DbDataType.SHORT_TEXT, 64, true);
   
   public static final DbColumnDef CD_KEYWORDS = new DbColumnDef
   ("KEYWORDS", DbDataType.SHORT_TEXT, 254, true);
   
   public static final DbColumnDef CD_STAT = new DbColumnDef
   ("STAT", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_REFCOUNT = new DbColumnDef
   ("REFCOUNT", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_REMS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true); 
   
   public static final DbColumnDef CD_ACSTYPE = new DbColumnDef
   ("ACCESSTYPE", DbDataType.LONG_INTEGER, false); 
   
   public static final DbColumnDef CD_ACSID = new DbColumnDef
   ("ACSID", DbDataType.LONG_INTEGER, false);    
   
   public static final DbColumnDef CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);

   public static final DbColumnDef CD_UPDUSRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   public static final DbColumnDef CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
   
   public static final DbColumnDef CD_ACCRID = new DbColumnDef
   ("ACCRID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_ACCDATE = new DbColumnDef
   ("ACCDATE", DbDataType.DATE_TIME, false);
   
   public static final DbColumnDef CD_ACCCOUNT = new DbColumnDef
   ("ACCCOUNT", DbDataType.LONG_INTEGER, false); 
      
   public static final DbColumnDef CD_TS = new DbColumnDef
   ("TIMESTAMP", DbDataType.DATE_TIME, false); 
  
   public static final DbColumnDef[] ACD = 
   {CD_ID, CD_FDRID, CD_ARCHID, CD_NAME, CD_CLFID, CD_TYPE, CD_TITLE, CD_AUTHOR,
    CD_KEYWORDS, CD_STAT, CD_REFCOUNT, CD_REMS, CD_ACSTYPE, CD_ACSID,CD_CRTUSRID, CD_CRTTS,
    CD_UPDUSRID, CD_UPDTS, CD_ACCRID, CD_ACCDATE, CD_ACCCOUNT, CD_TS};   
  
   public static final String ACN = DbUtil.getColumnNames(ACD);
   
   private String m_tblName; 
   
   // **************************************************************************

   private String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   }
   
   private String getDefaultQual(int archId, int fdrId, 
                                 boolean orderById)
   {
      String qual;
      
      qual =  "WHERE " + CD_ARCHID.getName() + "= " + archId +
             " AND " + CD_FDRID.getName() + "= " + fdrId;
      
      if (orderById)
         qual = qual + " ORDER BY " + CD_ID.getName();
      
      return qual;
   }    
      
   // **************************************************************************
   
   public DaoDocTbl(String tblName)
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
   
   public String getArchIdColName(boolean qualified)
   {
      return getColName(CD_ARCHID, qualified);
   }
   
   public String getNameColName(boolean qualified)
   {
      return getColName(CD_NAME, qualified);
   }
   
   public String getClfIdColName(boolean qualified)
   {
      return getColName(CD_CLFID, qualified);
   }
   
   public String getTypeColName(boolean qualified)
   {
      return getColName(CD_TYPE, qualified);
   }
   
   public String getTitleColName(boolean qualified)
   {
      return getColName(CD_TITLE, qualified);
   }
   
   public String getAuthorColName(boolean qualified)
   {
      return getColName(CD_AUTHOR, qualified);
   }
   
   public String getKeywordsColName(boolean qualified)
   {
      return getColName(CD_KEYWORDS, qualified);
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
   
   public String getTsColName(boolean qualified)
   {
      return getColName(CD_TS, qualified);
   }  
      
   
   // **************************************************************************
   
   public void insertRow(DaoDocRow row) throws Exception
   {  
     
      DbInsertFns.insert(m_tblName, ACN, row);      
      
   }   
   // **************************************************************************

   public  void deleteRow(int id) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(id));
   }
   
   public  void deleteFolderRows(int archId, int fdrId) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(archId, fdrId, false));
   }
   
   // **************************************************************************
   public void selectFolderRows(int archId, int fdrId, DaoOutputXRows rows)
               throws Exception
   {  
      
      String qual;                
      
      qual = getDefaultQual(archId, fdrId, true);
      
      DbSelectFns.select(m_tblName, rows.getColumnNames(m_tblName), qual, false, rows);
           
   }
   
// **************************************************************************************
   
   public void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef idxDef1;
      DbIndexDef idxDef2;
      DbIndexDef idxDef3;
      
      indexName = m_tblName + "1";
      colNamesIndex = "ID";
      idxDef1 = new DbIndexDef(indexName,colNamesIndex,true,false);
      indexName = m_tblName + "2";
      colNamesIndex = "FDRID";
      idxDef2 = new DbIndexDef(indexName,colNamesIndex,false,false);
      indexName = m_tblName + "3";
      colNamesIndex = "FDRID,CLFID";
      idxDef3 = new DbIndexDef(indexName,colNamesIndex,false,false);
            
      DbTableFns.createTable(m_tblName,ACD);   
      DbTableFns.createIndex(m_tblName,idxDef1);
      DbTableFns.createIndex(m_tblName,idxDef2);
      DbTableFns.createIndex(m_tblName,idxDef3);
      
   }
   
} // class
