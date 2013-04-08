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
import ieci.tecdoc.sgm.catalogo_tramites.util.TipoConector;
import ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.TiposConectores;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de tipo de conectores.
 * @see TipoConectorTable
 * @see TipoConector
 * @see TipoConectorImpl
 * @see TiposConectores
 */
public class TipoConectorDatos extends TipoConectorImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(TipoConectorDatos.class);
  boolean isDebugeable = true;
  
  /**
   * Constructor de la clase HookTypeData
   */  
  public TipoConectorDatos() {
  }
  
  
  /**
   * Constructor de la clase HookTypeData
   * @param addressee Datos relacionados con un tipo de conector
   */
  public TipoConectorDatos(TipoConector hookType) {
     this.setId(hookType.getId());
     this.setDescription(hookType.getDescription());
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
     
     id = statement.getLongInteger(index++);
     description = statement.getShortText(index++);
     
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
     
     statement.setLongInteger(index++, id);
     statement.setShortText(index++, description);
     
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
     
     return new Integer(index);
  }
  
  /**
   * Recoge el conector cuyo identificador se pasa como parámetro.
   * @param hookId Identificador del conector.
   * @throws Exception Si se produce algún error.
   */
  public void load(int typeId, String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     TipoConectorTabla table = new TipoConectorTabla();
     DbConnection dbConn = new DbConnection();
     boolean load = true;
  
     logger.debug("Load Hook Type <-- HookTypeId: " + typeId);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(TipoConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(TipoConectorDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        if (!DynamicFns.select(dbConn,table.getByIdQual(typeId), tableInfo, rowsInfo)) {
          load = false;
        }
        
        if(load)
        	logger.debug("Load Hook Type --> HookType: " + this.toString());
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKTYPE);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
        if (!load)
          throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_BAD_HOOKTYPEID);
     }
  }
  

  /**
   * Inserta un tipo de conector.
   * @throws Exception Si se produce algún error.
   */
  public void insert(String entidad) throws Exception {     
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     TipoConectorTabla table = new TipoConectorTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Insert Hook Type <-- HookTypeId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(TipoConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(TipoConectorDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_HOOKTYPE);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Actualiza el tipo de conector cuyo identificador se pasa como parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void update(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     TipoConectorTabla table = new TipoConectorTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Hook Type <-- HookTypeId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
          
        tableInfo.setTableObject(table);
        tableInfo.setClassName(TipoConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getUpdateColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(TipoConectorDatos.class.getName());
        rowInfo.setValuesMethod("updateAll");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn,table.getByIdQual(id), tableInfo, rowsInfo);
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_HOOKTYPE);
     } catch (Exception e) {
       throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
        if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Elimina el tipo de conector cuyo identificador se pasa como parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String  entidad) throws Exception {
     TipoConectorTabla table = new TipoConectorTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Delete Hook Type <-- HookTypeId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        DbDeleteFns.delete(dbConn, table.getTableName(), table.getByIdQual(id));
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_HOOKTYPE);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  /**
   * Recupera la lista de tipos de conectores.
   * @return Lista de conectores.
   * @throws Exception Si se produce algún error.
   */
  public static TiposConectores getHookTypes(String entidad) throws Exception {
     TiposConectores hookTypes = new TiposConectores();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoDocument = new DynamicRow();
     TipoConectorTabla table = new TipoConectorTabla();
     DbConnection dbConn = new DbConnection();
     TipoConectorDatos hookType;
     int counter, size;
     
     logger.debug("Get Hook Types");
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(TipoConectorTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoDocument.setClassName(TipoConectorDatos.class.getName());
        rowInfoDocument.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoDocument);
        
        DynamicFns.selectMultiple(dbConn, table.getOrderByDesc(), false, tableInfo, rowsInfo);
        size = rowInfoDocument.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           hookType = (TipoConectorDatos)rowInfoDocument.getRow(counter);
           hookTypes.add(hookType);
        }
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKTYPES);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
     
     return hookTypes;
  }
}
