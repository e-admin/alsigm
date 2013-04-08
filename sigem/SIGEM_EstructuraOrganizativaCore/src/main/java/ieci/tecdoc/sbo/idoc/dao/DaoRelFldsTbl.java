
package ieci.tecdoc.sbo.idoc.dao;


import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.dyndao.DynDaoTbl;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;

public class DaoRelFldsTbl extends DynDaoTbl
{  
   
   protected static final DbColumnDef CD_FDRID = new DbColumnDef
   (DaoUtil.getRelFldsTblFdrIdColName(), DbDataType.LONG_INTEGER, false);

   protected static final DbColumnDef CD_TIMESTAMP = new DbColumnDef
   (DaoUtil.getRelFldsTblTsColName(), DbDataType.DATE_TIME, false);  
   
     
// **************************************************************************

   protected static String getDefaultQual(int id)
   {
      return "WHERE " + CD_FDRID.getName() + "= " + id;
   }   
      
      
   // **************************************************************************
   
   public DaoRelFldsTbl(String tblName, DbColumnDef[] relFldCols)
          throws Exception
   { 
      super();
      
      DbColumnDef[] colDefs = getAllColsDef(relFldCols);
      
      super.init(tblName, colDefs);     
   }
   
   private DbColumnDef[] getAllColsDef(DbColumnDef[] relFldCols)
                         throws Exception
   {   
      DbColumnDef[]   colsDef    = null; 
      DbColumnDef     colDef     = null;  
      DbColumnDef     relFldCol  = null;       
      int             i, idx;
      int             numCols, numRelCols;      
      
      numRelCols = relFldCols.length;
          
      numCols = numRelCols + 2; 
      
      colsDef       = new DbColumnDef[numCols]; 
      
      colsDef[0] = CD_FDRID;        
      colsDef[1] = CD_TIMESTAMP;
      
      idx = 2;
            
      for(i = 0; i < numRelCols; i++)
      {
         relFldCol = relFldCols[i];
                
         colDef = new DbColumnDef(relFldCol.getName(), 
                                  relFldCol.getDataType(),
                                  relFldCol.getMaxLen(), relFldCol.isNullable());
                                  
         colsDef[idx] = colDef;
         
         idx = idx + 1;
      }
      
      return colsDef;
      
   } 
   
   public DbColumnDef[] getColsDef(DaoRelFlds relFlds, boolean addFdrIdCol,
                                   boolean addTSCol)
                        throws Exception
   {  
      DbColumnDef[]   colsDef = null; 
      DbColumnDef     colDef  = null; 
      DaoRelFld       fld   = null;       
      int             i, j;
      int             numCols;
      int             firstRelFldColIdx, idx;
      int             specialColIdx;
      int             numFlds = relFlds.count();
      int             numAllColumns = m_colsDef.length;      
      
      numCols           = numFlds;
      firstRelFldColIdx = 0;
            
      if (addFdrIdCol)
      {
         numCols           = numCols + 1;
         firstRelFldColIdx = firstRelFldColIdx + 1;
      }
      
      if (addTSCol)
      {
         numCols           = numCols + 1;
         firstRelFldColIdx = firstRelFldColIdx + 1;
      }
      
      colsDef       = new DbColumnDef[numCols]; 
      specialColIdx = 0;
      
      if (addFdrIdCol)
      {
         colsDef[specialColIdx] = CD_FDRID;
         specialColIdx = specialColIdx + 1;
      }
      
      if (addTSCol)
      {
         colsDef[specialColIdx] = CD_TIMESTAMP;
      }
      
      idx = firstRelFldColIdx;
      
      for(i = 0; i < numFlds; i++)
      {
         fld = relFlds.get(i);
         
         for (j = 1; j < numAllColumns; j++)
         {
            colDef = m_colsDef[j];
            
            if (colDef.getName().equals(fld.getColName()))
            {
               colsDef[idx] = colDef;
               idx = idx + 1;
               break;
            }
         }
      }
      
      return colsDef;
      
   }   
      
   // ************************************************************************
   
   public IeciTdLongIntegerArrayList getFdrIds(String qual, boolean distinct, String entidad)
                                            throws Exception
   {  
      
	   DbConnection dbConn=new DbConnection();
	   IeciTdLongIntegerArrayList fdrIds;
	   try{
		   dbConn.open(DBSessionManager.getSession(entidad));
	      fdrIds = new IeciTdLongIntegerArrayList();       
	                 
	      DbSelectFns.select(dbConn, super.m_tblName, CD_FDRID.getName(), qual,
	                         distinct, fdrIds);
	   }catch (Exception e) {
		   throw e;
	}finally{
		dbConn.close();
	}
      return fdrIds;
         
   }
   
   // **************************************************************************

   public  void deleteRow(int id, String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
		   dbConn.open(DBSessionManager.getSession(entidad));
		   DbDeleteFns.delete(dbConn, super.m_tblName, getDefaultQual(id));
	   }catch (Exception e) {
		   throw e;
	}finally{
		dbConn.close();
	}
   }
   
   public static void deleteRow(String tblName, int id, String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad)); 
	   	DbDeleteFns.delete(dbConn, tblName, getDefaultQual(id));
	   }catch (Exception e) {
		   throw e;
	}finally{
		dbConn.close();
	}
   }   
   
   // **************************************************************************

   public DaoRelFldsRow selectFolderValues(DaoRelFlds flds, int id, String entidad)
                        throws Exception
   { 
      DaoRelFldsRow row = null;
      DbColumnDef[] cols = getColsDef(flds, true, true);
      
      row = new DaoRelFldsRow(flds,cols);      
      
      super.selectRow(getDefaultQual(id), row, entidad);
      
      return row;
  }
  
  public void updateRow(int id, DaoRelFldsRow updateValues, String entidad)
              throws Exception
   {  
      super.updateRow(updateValues, getDefaultQual(id), entidad);
   }
  
  public void createTable(DbConnection connection, DbIndexDef[] idxs) throws Exception
  {
     String      indexName;    
     DbIndexDef  idxFdrId;
     
     try{

	     indexName = m_tblName + "0";
	     idxFdrId = new DbIndexDef (indexName, "FDRID", true);
	     
	     DbTableFns.createTable(connection, m_tblName,m_colsDef);   
	     DbTableFns.createIndex(connection, m_tblName,idxFdrId);
	     DbTableFns.createIndices(connection, m_tblName,idxs);
     }catch(Exception e){
    	 throw e;
     }
  }
   
  
   
   
   
} // class
