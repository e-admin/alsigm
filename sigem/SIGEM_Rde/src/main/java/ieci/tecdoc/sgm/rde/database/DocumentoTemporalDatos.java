package ieci.tecdoc.sgm.rde.database;

import java.io.Serializable;


//import ieci.tecdoc.sgm.rde.Configuracion;
import ieci.tecdoc.sgm.rde.database.DBSessionManager;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.rde.datatypes.DocumentoTemporalImpl;
import ieci.tecdoc.sgm.rde.datatypes.DocumentosTemporales;
import ieci.tecdoc.sgm.rde.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.rde.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoCodigosError;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de documentos temporales asociados a un registro.
 *  
 */
public class DocumentoTemporalDatos extends DocumentoTemporalImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(DocumentoTemporalDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Recupera todos los valores de los parámetros de la sentencia
   * de consulta que se pasa como parámetro.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws DbExcepcion Si se produce algún error.
   */
  public Integer loadAllValues(DbOutputStatement statement, Integer idx)
  throws DbExcepcion {

     int index = idx.intValue();
     
     try{
       sessionId = statement.getShortText(index ++);
       guid = statement.getShortText(index ++);
       timestamp = statement.getTimestamp(index ++);
     }catch(Exception e){
       throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e.getCause());
     }
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un documento temporal.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer insert(DbInputStatement statement, Integer idx)
  throws DbExcepcion {

    int index = idx.intValue();
    
    try{
      statement.setShortText(index++, sessionId);
      statement.setShortText(index++, guid);
      statement.setTimestamp(index++, timestamp);
    }catch(Exception e){
      throw new DbExcepcion(DbCodigosError.EC_INSERT_ALL_VALUES);
    }

    return new Integer(index);
  }
  
  
  /**
   * Añade un documento temporal.
   *
   * @throws Exception Si se produce algún error.
   */
  public void add(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     DocumentoTemporalTabla table = new DocumentoTemporalTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Add Temporal Registry Document <-- Temporal Registry Document: " + this.toString());
     
     try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(DocumentoTemporalTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(DocumentoTemporalDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new DbExcepcion(DbCodigosError.EC_ADD_VALUE, e.getCause());
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
     }
  }
  
  
  /**
   * Borra los documentos temporales asociadas a un sessionId.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) 
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
     DocumentoTemporalTabla table = new DocumentoTemporalTabla();
     
     logger.debug("Delete Temporal Registry Documents <-- Registry ID: " + sessionId);
     
     if (sessionId == null || sessionId.equals(""))
       throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
     
     DbConnection dbConn = new DbConnection();
     
     try {
         dbConn.open(DBSessionManager.getSession(entidad));
        
        DbDeleteFns.delete(dbConn, table.getTableName(), table.getBySessionIdQual(sessionId));
        
     } catch (Exception e) {
        throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){
         throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_DELETE_DOCUMENT);
       }
     }
  }

  
  /**
   * Recupera la lista de docuemntos temporales.
   *
   * @return Lista de docuemnto temporales.
   * @throws Exception Si se produce algún error.
   */
  public DocumentosTemporales getDocumentTmps(String entidad) throws Exception {
     DocumentosTemporales documentTmps = new DocumentosTemporales();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoProcedure = new DynamicRow();
     DocumentoTemporalTabla table = new DocumentoTemporalTabla();
     DbConnection dbConn = new DbConnection();
     DocumentoTemporalDatos documentTmp;
     int counter, size;
  
     logger.debug("Get Temporal Registry Documents <-- Registry ID: " + sessionId);
     
     try {
         dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(DocumentoTemporalTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoProcedure.setClassName(DocumentoTemporalDatos.class.getName());
        rowInfoProcedure.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoProcedure);
        
        DynamicFns.selectMultiple(dbConn, table.getBySessionIdQual(sessionId), false, tableInfo, rowsInfo);
        size = rowInfoProcedure.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           documentTmp = (DocumentoTemporalDatos)rowInfoProcedure.getRow(counter);
           documentTmps.add(documentTmp);
        }
        
     } catch (GuidIncorrectoExcepcion e) {
       throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
     } catch (Exception e) {
       throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }
     return documentTmps;
  }
  
  
  /**
   * Recupera la lista de docuemntos temporales caducados.
   *
   * @return Lista de docuemnto temporales.
   * @throws Exception Si se produce algún error.
   */
  public DocumentosTemporales getDocumentTmps(int timeout, String entidad) throws Exception {
     DocumentosTemporales documentTmps = new DocumentosTemporales();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoProcedure = new DynamicRow();
     DocumentoTemporalTabla table = new DocumentoTemporalTabla();
     DbConnection dbConn = new DbConnection();
     DocumentoTemporalDatos documentTmp;
     int counter, size;
  
     logger.debug("Get Temporal Registry Documents <-- Timeout: " + timeout + " minutos");
     
     try {
         dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(DocumentoTemporalTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoProcedure.setClassName(DocumentoTemporalDatos.class.getName());
        rowInfoProcedure.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoProcedure);
        
        DynamicFns.selectMultiple(dbConn, table.getByTimeoutQualDBConn(timeout, dbConn), false, tableInfo, rowsInfo);
        size = rowInfoProcedure.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           documentTmp = (DocumentoTemporalDatos)rowInfoProcedure.getRow(counter);
           documentTmps.add(documentTmp);
        }
        
     } catch (GuidIncorrectoExcepcion e) {
       throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
     } catch (Exception e) {
       throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }
     return documentTmps;
  }
  
}
