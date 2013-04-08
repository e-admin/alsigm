/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 02-jun-2004
 */
package ieci.tecdoc.sgm.rde.database;

import java.io.Serializable;

//import ieci.tecdoc.core.db.DbConnection;

//import ieci.tecdoc.sgm.rde.Configuracion;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.rde.database.DBSessionManager;
import ieci.tecdoc.sgm.rde.datatypes.ContenedorDocumentoImpl;
import ieci.tecdoc.sgm.rde.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.rde.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoCodigosError;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de documentos del RDE.
 *  
 */
public class ContenedorDocumentoDatos extends ContenedorDocumentoImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(ContenedorDocumentoDatos.class);
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
       guid = statement.getShortText(index ++);
       content = statement.getBytes(index++);//BinaryStream(index ++);
       hash = statement.getShortText(index ++);
       extension = statement.getShortText(index ++);
       timestamp = statement.getDateTime(index ++);
     }catch(Exception e){
       throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e.getCause());
     }

     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un documento.
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
      statement.setShortText(index++, guid);
      statement.setBytes(index++, content);
      statement.setShortText(index++, hash);
      statement.setShortText(index++, extension);
      statement.setDateTime(index++, timestamp);
    }catch(Exception e){
      throw new DbExcepcion(DbCodigosError.EC_INSERT_ALL_VALUES);
    }

    return new Integer(index);
  }
  
  
  /**
   * Realiza la consulta por guid.
   *
   * @param guid GUID del documento.
   * @throws DbExcepcion Si se produce algún error.
   */
  public void load(String guid, String entidad)
     throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
     
    if (guid == null || guid.equals(""))
      throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    DynamicTable tableInfo = new DynamicTable();
    DynamicRows rowsInfo = new DynamicRows();
    DynamicRow rowInfo = new DynamicRow();
    ContenedorDocumentoTabla table = new ContenedorDocumentoTabla();
    DbConnection dbConn = new DbConnection();
     
    logger.debug("Load Document Content <-- Guid: " + guid);
    
    boolean incorrectGuid = false;
    
    try {
       dbConn.open(DBSessionManager.getSession(entidad));
       tableInfo.setTableObject(table);
       tableInfo.setClassName(ContenedorDocumentoTabla.class.getName());
       tableInfo.setTablesMethod("getTableName");
       tableInfo.setColumnsMethod("getAllColumnNames");
        
       rowInfo.addRow(this);
       rowInfo.setClassName(ContenedorDocumentoDatos.class.getName());
       rowInfo.setValuesMethod("loadAllValues");
       rowsInfo.add(rowInfo);
        
       if (!DynamicFns.select(dbConn, table.getByGuidQual(guid), tableInfo, rowsInfo, false)) {
          incorrectGuid = true;
       }
       logger.debug("Load Document Content --> Document Content: " + this.toString());
    } catch (Exception e) {
       throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
    } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
       
       if (incorrectGuid)
         throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }
  }
  
  
  /**
   * Añade un documento.
   *
   * @throws Exception Si se produce algún error.
   */
  public void add(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     ContenedorDocumentoTabla table = new ContenedorDocumentoTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Add Document Content <-- Document Content: " + this.toString());
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        tableInfo.setTableObject(table);
        tableInfo.setClassName(ContenedorDocumentoTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(ContenedorDocumentoDatos.class.getName());
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
   * Borra los documentos asociadas a un guid.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) 
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
     ContenedorDocumentoTabla table = new ContenedorDocumentoTabla();
     
     logger.debug("Delete Document Content <-- Guid: " + guid);
     
     if (guid == null || guid.equals(""))
       throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
     
     DbConnection dbConn = new DbConnection();
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        DbDeleteFns.delete(dbConn, table.getTableName(), table.getByGuidQual(guid));
        
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

}