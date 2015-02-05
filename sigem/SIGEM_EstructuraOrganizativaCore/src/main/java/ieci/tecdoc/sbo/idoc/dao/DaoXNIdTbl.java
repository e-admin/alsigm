
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbInputRecord;
import ieci.tecdoc.sgm.base.dbex.DbInsertFns;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;



public final class DaoXNIdTbl
{
   
   // **************************************************************************
  
   private final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_PARENTID = new DbColumnDef
   ("PARENTID", DbDataType.LONG_INTEGER, false);   
   
   private final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
  
   private final DbColumnDef[] ACD = 
   {CD_TYPE, CD_PARENTID, CD_ID};  
      
   private final String ACN = DbUtil.getColumnNames(ACD); 
   
   private String m_tblName; 
   
   // **************************************************************************

   private String getDefaultQual(int type, int parentId)
   {
      return "WHERE " + CD_TYPE.getName() + "=" + type +
             " AND " + CD_PARENTID.getName() + "=" + parentId;
   }   
      
   // **************************************************************************
   
   public DaoXNIdTbl(String tblName)
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
   
   public String getTypeColName(boolean qualified)
   {
      return getColName(CD_TYPE, qualified);
   }
   
   public String getParentIdColName(boolean qualified)
   {
      return getColName(CD_PARENTID, qualified);
   }
   
   public String getIdColName(boolean qualified)
   {
      return getColName(CD_ID, qualified);
   }   
   
   // **************************************************************************
   
   public void insertRow(DbConnection connection, DaoXNIdRow row) throws Exception
   {  
      
      DbInsertFns.insert(connection, m_tblName, ACN, row);      
      
   }
  
   // **************************************************************************
   
   public void incrementNextId(DbConnection connection, int type, int parentId) throws Exception
   {

      incrementNextId(connection, type, parentId, 1);

   }

   public void incrementNextId(DbConnection connection, int type, int parentId, int incr) throws Exception
   {

      String stmtText;
      String qual = getDefaultQual(type, parentId);

      stmtText = "UPDATE " + m_tblName + " SET " + CD_ID.getName() + "="
            + CD_ID.getName() + "+" + incr + qual;

      DbUtil.executeStatement(connection, stmtText);

   }
   
   // **************************************************************************

   public int getNextId(DbConnection connection, int type, int parentId) throws Exception
   {

      int nextId;

      nextId = DbSelectFns.selectLongInteger(connection, m_tblName, CD_ID.getName(),
                                             getDefaultQual(type, parentId));

      return nextId;

   } 
   
   public boolean nextIdExists(DbConnection connection, int type, int parentId) throws Exception
   {
       
      return DbSelectFns.rowExists(connection, m_tblName, getDefaultQual(type, parentId)); 
           
   } 
   
   // **************************************************************************
   
   public void deleteChildrenRows(DbConnection connection, int parentId) throws Exception
   {
       
      String qual;
      
      qual = "WHERE " + CD_PARENTID.getName() + "=" + parentId;  

      DbDeleteFns.delete(connection, m_tblName, qual);
           
   } 
   //***********************************************************
   
   public void createTable(DbConnection connection) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = m_tblName + "1";
      colNamesIndex = "TYPE,PARENTID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      DbTableFns.createTable(connection, m_tblName,ACD);   
      DbTableFns.createIndex(connection, m_tblName,indexDef);
   }
   
} // class
