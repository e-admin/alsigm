package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import java.io.Serializable;

//import ieci.tecdoc.sgm.catalogo_tramites.Configuracion;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conector;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conectores;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de conectores.
 * @see ConectorTable
 * @see Conector
 * @see ConectorImpl
 * @see Conectores 
 */
public class ConectorDatos extends ConectorImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(ConectorDatos.class);
  boolean isDebugeable = true;
  
  /**
   * Constructor de la clase ConectorDatos
   */  
  public ConectorDatos() {
  }
  
  
  /**
   * Constructor de la clase ConectorDatos
   * @param addressee Datos relacionados con un conector
   */
  public ConectorDatos(Conector hook) {
     this.setId(hook.getId());
     this.setDescription(hook.getDescription());
     this.setType(hook.getType());
  }
  
  
  /**
   * Recupera todos los valores de los parámetros de la sentencia de consulta
   * que se pasa como parámetro.
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge de la
   *  consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer loadAllValues(DbOutputStatement statement, Integer idx)
  throws Exception {

     int index = idx.intValue();
     
     id = statement.getShortText(index++);
     description = statement.getShortText(index++);
     type = statement.getLongInteger(index++);
     
     return new Integer(index);
  }

  
  /**
   * Genera la sentencia de inserción de un registro.
   * @param statement Sentencia SQL precompilada.
   * @param idx Indice de posición del primer parámetro en la sentencia.
   * @return Indice de posición del último parámetro en la sentencia.
   * @throws Exception Si se produce algún error.
   */
  public Integer insert(DbInputStatement statement, Integer idx)
  throws Exception {
     int index = idx.intValue();

     statement.setShortText(index++, id);
     statement.setShortText(index++, description);
     statement.setLongInteger(index++, type);
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de actualización de un registro.
   * @param statement Sentencia SQL precompilada.
   * @param idx Indice de posición del primer parámetro en la sentencia.
   * @return Indice de posición del último parámetro en la sentencia.
   * @throws Exception Si se produce algún error.
   */
  public Integer updateAll(DbInputStatement statement, Integer idx)
  throws Exception {
     int index = idx.intValue();

     statement.setShortText(index++, description);
     statement.setLongInteger(index++, type);
     
     return new Integer(index);
  }
  
  /**
   * Recoge el conector cuyo identificador se pasa como parámetro.
   * @param hookId Identificador del conector.
   * @throws Exception Si se produce algún error.
   */
  public void load(String hookId, String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     ConectorTabla table = new ConectorTabla();
     DbConnection dbConn = new DbConnection();
     boolean load = true;
  
     if (isDebugeable)
       logger.debug("Load Hook <-- HookId: " + hookId);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(ConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(ConectorDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        if (!DynamicFns.select(dbConn,table.getByIdQual(hookId), tableInfo, rowsInfo)) {
          load = false;
        }
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOK);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
        if (!load)
          throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_BAD_HOOKID);
     }
  }
  

  /**
   * Inserta un conector.
   * @throws Exception Si se produce algún error.
   */
  public void insert(String entidad) throws Exception {     
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     ConectorTabla table = new ConectorTabla();
     DbConnection dbConn = new DbConnection();
     
     if (isDebugeable)
       logger.debug("Insert Hook <-- DocumentId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(ConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(ConectorDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_HOOK);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Actualiza el conector cuyo identificador se pasa como parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void update(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     ConectorTabla table = new ConectorTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Hook <-- HookId: " + id);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(ConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getUpdateColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(ConectorDatos.class.getName());
        rowInfo.setValuesMethod("updateAll");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn,table.getByIdQual(id), tableInfo, rowsInfo);
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_HOOK);
     } catch (Exception e) {
       throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
        if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Elimina el conector cuyo identificador se pasa como parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) throws Exception {
     ConectorTabla table = new ConectorTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Delete Hook <-- HookId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        DbDeleteFns.delete(dbConn, table.getTableName(), table.getByIdQual(id));
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_HOOK);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  /**
   * Recupera la lista de conectores.
   * @param hookType Tipo de conector (-1 si se quieren todos los tipos)
   * @return Lista de conectores.
   * @throws Exception Si se produce algún error.
   */
  public static Conectores getHooks(int hookType, String entidad) throws Exception {
     Conectores hooks = new Conectores();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoDocument = new DynamicRow();
     ConectorTabla table = new ConectorTabla();
     DbConnection dbConn = new DbConnection();
     ConectorDatos hook;
     int counter, size;
     
     logger.debug("getHooks");
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(ConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoDocument.setClassName(ConectorDatos.class.getName());
        rowInfoDocument.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoDocument);
        
        if (hookType < 0)
          DynamicFns.selectMultiple(dbConn, table.getOrderByDesc(), false, tableInfo, rowsInfo);
        else
          DynamicFns.selectMultiple(dbConn, table.getByHookTypeQual(hookType) + " " + table.getOrderByDesc(), false, tableInfo, rowsInfo);
        size = rowInfoDocument.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           hook = (ConectorDatos)rowInfoDocument.getRow(counter);
           hooks.add(hook);
        }
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKS);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
     
     return hooks;
  }
  
  
  /**
   * Recupera la lista de conectores a partir de un conjunto de identificadores.
   * @param hookIds Listado de identificadores de conectores a recuperar
   * @return Lista de conectores.
   * @throws Exception Si se produce algún error.
   */
  public static Conectores getMultipleHooks(String[] hookIds, String entidad) throws Exception {
     Conectores hooks = new Conectores();
     ConectorTabla table = new ConectorTabla();
     DbConnection dbConn = new DbConnection();
     ConectorDatos hook;
     int counter, size;
     
     logger.debug("getHooks");
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        for(int i=0; i<hookIds.length; i++){
        	DynamicTable tableInfo = new DynamicTable();
			DynamicRows rowsInfo = new DynamicRows();
			DynamicRow rowInfoDocument = new DynamicRow();
        	tableInfo.setTableObject(table);
        	tableInfo.setClassName(ConectorTabla.class.getName());
        	tableInfo.setTablesMethod("getTableName");
        	tableInfo.setColumnsMethod("getAllColumnNames");
        
        	rowInfoDocument.setClassName(ConectorDatos.class.getName());
        	rowInfoDocument.setValuesMethod("loadAllValues");
        	rowsInfo.add(rowInfoDocument);
        
        	DynamicFns.selectMultiple(dbConn, table.getByIdQual(hookIds[i]), false, tableInfo, rowsInfo);
        	size = rowInfoDocument.getRowCount();
     	
        	for (counter = 0; counter < size; counter++) {
        		hook = (ConectorDatos)rowInfoDocument.getRow(counter);
        		hooks.add(hook);
        	}
        }
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKS);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
     
     return hooks;
  }
}
