
package ieci.tecdoc.core.dyndao;


import ieci.tecdoc.core.db.*;
import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;

public class DynDaoTbl
{
   
   // **************************************************************************
   
   protected String        m_tblName;
   protected DbColumnDef[] m_colsDef;      
   
      
   // **************************************************************************
   protected DynDaoTbl()
   {
      m_tblName = null;
      m_colsDef = null;
   }
   
   protected void init(String tblName, DbColumnDef[] colsDef)
   {
      m_tblName = tblName;
      m_colsDef = colsDef;
   }
   
   public DynDaoTbl(String tblName, DbColumnDef[] colsDef)
   {
      m_tblName = tblName;
      m_colsDef = colsDef;
   }
   
   public DbColumnDef[] getAllColumns()
   {
      return m_colsDef;
   }
   
  // ************************************************************************
   
   public void insertRow(DynDaoRec rec) throws Exception
   {  
     
      DbInsertFns.insert(m_tblName, DbUtil.getColumnNames(m_colsDef), rec);      
      
   }
   
   // ************************************************************************
   
   public void updateRow(DynDaoRec row, String qual)
               throws Exception
   {  
      DbColumnDef[] updateColsDef  = row.getColumnsDef();
      
      DbUpdateFns.update(m_tblName, DbUtil.getColumnNames(updateColsDef), 
                         row, qual);
         
   }
   
   // ************************************************************************
      
   public DynDaoRec selectRow(IeciTdShortTextArrayList colNames, String qual)
                      throws Exception
   {  
      DbColumnDef[] colsDef = getColsDef(colNames);
      DynDaoRec    rec      = new DynDaoRec(colsDef);
        
      DbSelectFns.select(m_tblName, DbUtil.getColumnNames(colsDef),
                         qual, rec);
      
      return rec;
      
   }
   
   public void selectRow(String qual, DynDaoRec rec)
                  throws Exception
   {  
      DbColumnDef[] selectColsDef  = rec.getColumnsDef();
      
      DbSelectFns.select(m_tblName, DbUtil.getColumnNames(selectColsDef), 
                         qual, rec);
   }
   
   public DynDaoRs selectRows(IeciTdShortTextArrayList colNames, String qual)
                   throws Exception
   { 

      DbColumnDef[] colsDef = getColsDef(colNames);

      return selectRows(colsDef, qual);

   }

   public DynDaoRs selectRows(DbColumnDef[] selectColsDef, String qual)
                   throws Exception
   { 

      DynDaoRs  rs  = new DynDaoRs(selectColsDef);

      DbSelectFns.select(m_tblName, 
                         DbUtil.getColumnNames(selectColsDef),
                         qual, false, rs);

      return rs;

   }
   
   private DbColumnDef[] getColsDef(IeciTdShortTextArrayList colNames)
                         throws Exception
   {  
      int           i, j, numCols;
      DbColumnDef[] colDefs = new DbColumnDef[colNames.count()];
      DbColumnDef   colDef;
      String        colName;
      int           colIdx = 0;
      
      numCols = m_colsDef.length;
      
      for(i = 0; i < numCols; i++)
      {
         
         colName = colNames.get(i);
                  
         for(j = 0; j < numCols; j++)
         {
            colDef = m_colsDef[j];
            
            if (colDef.getName().equals(colName))
            {
               colDefs[colIdx] = new DbColumnDef(colName, colDef.getDataType(),
                                                 colDef.getMaxLen(), colDef.isNullable());
               colIdx = colIdx + 1;
               break;
            }
         }
         
      }
      
      return colDefs;
      
   }
      
} // class
