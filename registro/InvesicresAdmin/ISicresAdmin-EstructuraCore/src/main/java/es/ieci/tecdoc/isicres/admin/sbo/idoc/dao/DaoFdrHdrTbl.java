
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.sbo.util.idoc8db.Idoc8DbAcsRec;


public class DaoFdrHdrTbl
{
   
   // **************************************************************************
   
   private final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private final DbColumnDef CD_VERSTAT = new DbColumnDef
   ("VERSTAT", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_REFCOUNT = new DbColumnDef
   ("REFCOUNT", DbDataType.LONG_INTEGER, false); 
   
   private final DbColumnDef CD_ACSTYPE = new DbColumnDef
   ("ACCESSTYPE", DbDataType.LONG_INTEGER, false);
   
   private final DbColumnDef CD_ACSID = new DbColumnDef
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
   {CD_ID, CD_VERSTAT, CD_REFCOUNT, CD_ACSTYPE, CD_ACSID,CD_CRTUSRID, CD_CRTTS,
    CD_UPDUSRID, CD_UPDTS, CD_ACCRID, CD_ACCDATE, CD_ACCCOUNT};   
  
   private final String ACN = DbUtil.getColumnNames(ACD); 
   
   private final String ACSCN = DbUtil.getColumnNames
  (CD_ACSTYPE, CD_ACSID);  
   
   private String m_tblName; 
   
   // **************************************************************************

   private String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   }   
      
   // **************************************************************************
   
   public DaoFdrHdrTbl(String tblName)
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
   
   public String getVerStatColName(boolean qualified)
   {
      return getColName(CD_VERSTAT, qualified);
   }
   
   public String getRefCountColName(boolean qualified)
   {
      return getColName(CD_REFCOUNT, qualified);
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
   
   public void insertRow(DbConnection connection, DaoFdrHdrRow row) throws Exception
   {  
     
      DbInsertFns.insert(connection, m_tblName, ACN, row);      
      
   }
   
   // **************************************************************************

   public  void deleteRow(DbConnection connection, int id) throws Exception
   {
      DbDeleteFns.delete(connection, m_tblName, getDefaultQual(id));
   }
   
   
   // ************************************************************************
   
   public void updateRow(DbConnection connection, int id, DaoInputXRow row)
               throws Exception
   {  
          
      DbUpdateFns.update(connection, m_tblName, row.getColumnNames(m_tblName), row, 
                         getDefaultQual(id));
         
   }   
   
   // **************************************************************************
   
   public void selectRow(DbConnection connection, int id, DaoOutputXRow row) throws Exception
   {  
      
      DbSelectFns.select(connection, m_tblName, ACN, getDefaultQual(id), row); 
            
   }   
   
   public int selectRefCount(DbConnection connection, int id) throws Exception
   { 
      int count;
      
      count = DbSelectFns.selectLongInteger(connection, m_tblName, getRefCountColName(false),
                                            getDefaultQual(id));

      return count;
   }
   
   public int selectAccCount(DbConnection connection, int id) throws Exception
   { 
      int count;
      
      count = DbSelectFns.selectLongInteger(connection, m_tblName, getAccessCountColName(false),
                                            getDefaultQual(id));

      return count;
   }
   
   public Idoc8DbAcsRec selectAcsInfo(DbConnection connection, int id)
                        throws Exception
   {
      
	   Idoc8DbAcsRec rec = new Idoc8DbAcsRec();  

      DbSelectFns.select(connection, m_tblName, ACSCN, getDefaultQual(id), rec);

      return rec;
      
   } 
   
   public void createTable(DbConnection connection) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = m_tblName + "1";
      colNamesIndex = "ID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      
      DbTableFns.createTable(connection, m_tblName,ACD);   
      DbTableFns.createIndex(connection, m_tblName,indexDef);
   
   }
   
} // class
