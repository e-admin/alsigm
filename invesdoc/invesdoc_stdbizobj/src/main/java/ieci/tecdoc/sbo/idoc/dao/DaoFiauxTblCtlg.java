package ieci.tecdoc.sbo.idoc.dao;

import java.util.Date;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbInsertFns;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUpdateFns;
import ieci.tecdoc.core.types.IeciTdType;
import ieci.tecdoc.sbo.idoc.report.std.FiAuxTblCtlgTokenRows;
import ieci.tecdoc.sbo.idoc.report.std.FiAuxTblCtlgRowUpd;
import ieci.tecdoc.sbo.idoc.report.std.FiAuxTblCtlgTokenRow;
import ieci.tecdoc.sbo.idoc.report.std.FiAuxTblCtlgUserRowUpd;
import ieci.tecdoc.sbo.util.nextid.DaoNextIdTbl;
import ieci.tecdoc.sbo.util.types.SboType;

public class DaoFiauxTblCtlg
{

  // **************************************************************************
  private static final String        TN           = "IDOCFIAUXTBLCTLG";
  private static final String        TN_NEXTID    = "IDOCNEXTID";

  private static final DbColumnDef   CD_ID        = new DbColumnDef("ID",DbDataType.LONG_INTEGER,false);

  private static final DbColumnDef   CD_NAME      = new DbColumnDef("NAME",DbDataType.SHORT_TEXT,16, false);

  private static final DbColumnDef   CD_USERID    = new DbColumnDef("USERID",DbDataType.LONG_INTEGER,false);

  private static final DbColumnDef   CD_TIMESTAMP = new DbColumnDef("TIMESTAMP",DbDataType.DATE_TIME,false);

  private static final DbColumnDef[] ACD          = { CD_ID, CD_NAME, CD_USERID, CD_TIMESTAMP};

  // **************************************************************************

  public static void createTable() throws Exception
  {
    String indexName, indexName2, indexName3;
    String colNamesIndex, colNamesIndex2, colNamesIndex3;
    DbIndexDef indexDef, indexDef2, indexDef3;

    indexName = TN + "1";
    colNamesIndex = "ID";
    indexName2 = TN + "2";
    colNamesIndex2 = "NAME";
    indexName3 = TN + "3";
    colNamesIndex3 = "USERID";

    indexDef = new DbIndexDef(indexName, colNamesIndex, true,false);
    indexDef2 = new DbIndexDef(indexName2, colNamesIndex2, true,false);
    indexDef3 = new DbIndexDef(indexName3, colNamesIndex3, false,false);

    DbTableFns.createTable(TN, ACD);

    DbTableFns.createIndex(TN, indexDef);
    DbTableFns.createIndex(TN, indexDef2);
    DbTableFns.createIndex(TN, indexDef3);
  }

  public static void dropTable() throws Exception
  {
    String indexName, indexName2, indexName3;

    indexName = TN + "1";
    indexName2 = TN + "2";
    indexName3 = TN + "3";

    dropIndex(TN, indexName);
    dropIndex(TN, indexName2);
    dropIndex(TN, indexName3);

    DbTableFns.dropTable(TN);

  }

  private static void dropIndex(String tblName, String indexName)
  {
    try
    {
      DbTableFns.dropIndex(tblName, indexName);
    }
    catch (Exception e)
    {

    }
  }

  protected static String getColName(DbColumnDef colDef, boolean qualified)
  {
    String colName = colDef.getName();

    if (qualified) colName = TN + "." + colName;

    return colName;
  }

  public static String getIdColName(boolean qualified)
  {
    return getColName(CD_ID, qualified);
  }

  public static String getNameColName(boolean qualified)
  {
    return getColName(CD_NAME, qualified);
  }

  public static String getUserIdColName(boolean qualified)
  {
    return getColName(CD_USERID, qualified);
  }

  public static String getTimestampColName(boolean qualified)
  {
    return getColName(CD_TIMESTAMP, qualified);
  }

  public static FiAuxTblCtlgTokenRows getReleasedFiAuxTblInfo ()
  throws Exception
  {
    FiAuxTblCtlgTokenRows rows = null;
    rows = new FiAuxTblCtlgTokenRows();
    DbSelectFns.select(TN, rows.getColumnNames(), getFirstReleasedQuery(), false, rows);
    return rows;
  }
  
  //TODO: ESTARIA BIEN QUE LA FUNCION UPDATE DEVOLVIERA EL NUMERO DE REGISTROS ACTUALIZADOS
  // PARA NO REALIZAR LA SIGUIENTE QUERY CON LA CUAL SE COMPRUEBA SI REALMENTE LA TABLA HA SIDO
  // ASIGNADA AL USUARIO
  public static boolean lockFiAuxTbl (FiAuxTblCtlgTokenRow row, int userId)
  throws Exception
  {
    boolean rc = false;
    FiAuxTblCtlgUserRowUpd updRow = null;
    FiAuxTblCtlgTokenRows userRows = null;
    
    updRow = new FiAuxTblCtlgUserRowUpd (userId);
    DbUpdateFns.update(TN, updRow.getColumnNames(), updRow, getLockQual(row.m_id));

    userRows = new FiAuxTblCtlgTokenRows();
    DbSelectFns.select(TN, userRows.getColumnNames(), getBelongingQuery(
          row.m_id, userId), false, userRows);
    
    if (userRows.count() != 0)
      rc = true;
    
    return rc;
  }
  
  public static String createAndLockFiAuxTbl (int userId) throws Exception
  {
    String tableName = null;
    int nextId = -1;
    FiAuxTblCtlgRowUpd newRow = null;
    
    // CONVENDRIA SACAR LA GENERACION DEL INDICE A OTRA TRANSACCION (Y POR TANTO LA CREACION 
    // DE LA TABLA) A OTRA TRANSACCION PUESTO QUE SI NO AQUI PUEDE HABER UN CUELLO DE BOTELLA
    nextId = generateNextId ();
    tableName = "IDOCFIAUXTBL" + nextId;
    
    DaoFiAuxTbl fiAuxTbl = new DaoFiAuxTbl (nextId);
    fiAuxTbl.createTable();

    newRow = new FiAuxTblCtlgRowUpd ();
    newRow = initFiAuxTblCtlgRowUpd(nextId, tableName, userId);
    DbInsertFns.insert(TN, newRow.getColumnNames(), newRow);   
    
    return tableName;
  }
  

  public static void releaseFiAuxTbl(int userId, String tablename) throws Exception
  {
    FiAuxTblCtlgUserRowUpd row = null;    
    row = new FiAuxTblCtlgUserRowUpd (SboType.NULL_ID);    
    DbUpdateFns.update(TN, CD_USERID.getName(), row, "WHERE " + CD_USERID.getName() + "=" + userId + " and " + CD_NAME.getName() + "='" + tablename + "'");    
  }
  
  private static String getFirstReleasedQuery()
  {
    return "WHERE " + CD_USERID.getName() + "=" + SboType.NULL_ID;
  }

  private static String getLockQual(int auxTableId)
  {
    return " WHERE " + CD_ID.getName() + " = " + auxTableId + " and "
          + CD_USERID.getName() + " = " + SboType.NULL_ID;
  }

  private static String getBelongingQuery(int auxTableId, int userId)
  {
    return "WHERE " + CD_USERID.getName() + "=" + userId + " and "
          + CD_ID.getName() + "=" + auxTableId;
  }

  public static int generateNextId()
        throws Exception
  {
    DaoNextIdTbl nextIdTbl = new DaoNextIdTbl(TN_NEXTID);
    int nextId = IeciTdType.NULL_LONG_INTEGER;

    nextIdTbl.incrementNextId(DaoDefs.NEXT_ID_TYPE_FIAUXTBL);
    nextId = nextIdTbl.getNextId(DaoDefs.NEXT_ID_TYPE_FIAUXTBL);

    return nextId;
  }
  
  private static FiAuxTblCtlgRowUpd initFiAuxTblCtlgRowUpd(int auxTableId,
        String tablename, int userId)
  {
    FiAuxTblCtlgRowUpd row = new FiAuxTblCtlgRowUpd();
    row.m_id = auxTableId;
    row.m_name = tablename;
    row.m_userId = userId;
    row.m_timestamp = new Date(System.currentTimeMillis());
    return row;
  }

}
