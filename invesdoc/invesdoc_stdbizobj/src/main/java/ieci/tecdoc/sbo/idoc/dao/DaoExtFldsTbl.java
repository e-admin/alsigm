
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoExtFldsTbl
{
   
   // **************************************************************************   

   private final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_FDRID = new DbColumnDef
   ("FDRID", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_FLDID = new DbColumnDef
   ("FLDID", DbDataType.LONG_INTEGER, false); 
   
   private final DbColumnDef CD_TEXT = new DbColumnDef
   ("TEXT", DbDataType.LONG_TEXT, 64*1024, false);

   private final DbColumnDef CD_TS = new DbColumnDef
   ("TIMESTAMP", DbDataType.DATE_TIME, false);
   
   private final DbColumnDef[] ACD = 
   {CD_ID, CD_FDRID, CD_FLDID, CD_TEXT, CD_TS};   
  
   private final String ACN = DbUtil.getColumnNames(ACD); 
   
   private String m_tblName; 
   
   // **************************************************************************

   private String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   } 
   
   private String getDefaultQual(int fdrId, int fldId)
   {
      return "WHERE " + CD_FDRID.getName() + "= " + fdrId +
             " AND " + CD_FLDID.getName() + "= " + fldId;
   }
   
   private String getQualByFdrId(int fdrId)
   {
      String qual;
      
      qual =  "WHERE " + CD_FDRID.getName() + "= " + fdrId;
      
      
      return qual;
   }  
   
   // **************************************************************************

   private String getFdrQualOrderByFldId(int fdrId)
   {
      String qual;
      
      qual = "WHERE " + CD_FDRID.getName() + "= " + fdrId +
             " ORDER BY " + CD_FLDID.getName();
      
      return qual;
   }   
      
   // **************************************************************************
   
   public DaoExtFldsTbl(String tblName)
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
   
   public String getFldIdColName(boolean qualified)
   {
      return getColName(CD_FLDID, qualified);
   }
   
   public String getTextColName(boolean qualified)
   {
      return getColName(CD_TEXT, qualified);
   }
     
   public String getTSColName(boolean qualified)
   {
      return getColName(CD_TS, qualified);
   }
   
   // **************************************************************************
   
   public void insertRow(DaoExtFldsRow row) throws Exception
   { 
       
      DbInsertFns.insert(m_tblName, ACN, row);      
      
   }
   
   // **************************************************************************

   public  void deleteRow(int fdrId, int fldId) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getDefaultQual(fdrId, fldId));
   }
   
   public  void deleteFolderRows(int fdrId) throws Exception
   {
      DbDeleteFns.delete(m_tblName, getQualByFdrId(fdrId));
   }
   
   // ************************************************************************
   
   public void updateRow(int fdrId, int fldId, DaoInputXRow row)
               throws Exception
   {  
          
      DbUpdateFns.update(m_tblName, row.getColumnNames(m_tblName), 
                         row, getDefaultQual(fdrId, fldId));
         
   }
   
   // **************************************************************************
   
   public void selectRows(int fdrId, DaoOutputXRows rows)
               throws Exception
   { 
         
      DbSelectFns.select(m_tblName, rows.getColumnNames(m_tblName),
                         getFdrQualOrderByFldId(fdrId), false, rows);

   }
   
   // **************************************************************************
   
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
      colNamesIndex = "FDRID,FLDID";
      idxDef2 = new DbIndexDef(indexName,colNamesIndex,true,false);
      indexName = m_tblName + "3";
      colNamesIndex = "FDRID";
      idxDef3 = new DbIndexDef(indexName,colNamesIndex,false,false);
      
      DbTableFns.createTable(m_tblName,ACD);   
      DbTableFns.createIndex(m_tblName,idxDef1);
      DbTableFns.createIndex(m_tblName,idxDef2);
      DbTableFns.createIndex(m_tblName,idxDef3);
   
   }
   
} // class
