package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbInsertFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.sbo.idoc.report.std.FiAuxTblRow;


/**
 * @author egonzalez
 */
public class DaoFiAuxTbl
{
  private static final String        TN           = "IDOCFIAUXTBL";
  
  private static final DbColumnDef CD_FDRID = new DbColumnDef
  ("FDRID", DbDataType.LONG_INTEGER, false);
  
  private static final DbColumnDef[] ACD = {CD_FDRID};  
  
  private String tablename = null;
  
  public DaoFiAuxTbl (int nextId)
  {
    this.tablename = TN + nextId; 
  }
  
  public DaoFiAuxTbl (String tableName)
  {
    this.tablename = tableName; 
  }
 
  public void createTable() throws Exception
  {
     String colNamesIndex;
     DbIndexDef indexDef;

     colNamesIndex = "FDRID";        
     indexDef= new DbIndexDef(tablename + "_1",colNamesIndex,true,false);         
     DbTableFns.createTable(tablename,ACD);
     DbTableFns.createIndex(tablename,indexDef);
  }
  
  public void dropTable() throws Exception
  {
     dropIndex();   
     DbTableFns.dropTable(tablename);      
  }
  
  
  private void dropIndex()
  {
     try
     {
        DbTableFns.dropIndex(tablename,tablename+ "_1");
     }
     catch(Exception e)
     {        
     }
  }
  
  protected String getColName(DbColumnDef colDef, boolean qualified)
  {
    String colName = colDef.getName();

    if (qualified) colName = tablename + "." + colName;

    return colName;
  }

  public String getFdrIdColName(boolean qualified)
  {
    return getColName(CD_FDRID, qualified);
  }
  
  public static String getColumnNames ()
  {
    return DbUtil.getColumnNames(ACD);
  }
  
  public void insertRow (int fldId) throws Exception
  {
    // NO ESTAMOS APROVECHANDO VENTAJAS DEL PREPARED STATEMENT A LA HORA DE INSERTAR PUESTO
    // QUE CREAMOS DISTINTOS PREPARED STATEMENT PARA CADA INSERT
    FiAuxTblRow row = new FiAuxTblRow (fldId);
    DbInsertFns.insert(tablename, getColumnNames (), row);
  }
  
  public void clear () throws Exception
  {
    DbDeleteFns.delete(tablename, "");
  }
}
