package ieci.tecdoc.sgm.calendario.util.database;

import java.io.Serializable;

//import ieci.tecdoc.sgm.calendario.Configuracion;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.calendario.exception.CalendarioCodigosError;
import ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion;
import ieci.tecdoc.sgm.calendario.util.CalendarioDia;
import ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl;
import ieci.tecdoc.sgm.calendario.util.CalendarioDias;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de calendario.
 * @see CalendarioTabla
 * @see Calendario
 * @see CalendarioImpl 
 */
public class CalendarioDiaDatos extends CalendarioDiaImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(CalendarioDiaDatos.class);
  boolean isDebugeable = true;
  
  /**
   * Constructor de la clase CalendarioDiaDatos
   */  
  public CalendarioDiaDatos() {
  }
  
  
  /**
   * Constructor de la clase CalendarioDatos
   * @param calendar Datos relacionados con un calendario
   */
  public CalendarioDiaDatos(CalendarioDia day) {
	 this.setId(day.getId());
     this.setFecha(day.getFecha());
     this.setDescripcion(day.getDescripcion());
     this.setFijo(day.getFijo());
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
     fecha = statement.getShortText(index++);
     descripcion = statement.getShortText(index++);
     fijo = statement.getLongInteger(index++);
     
     return new Integer(index);
  }
  
  
  /**
   * Recupera el valor del identificador con los parámetros de la sentencia de consulta
   * que se pasa como parámetro.
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge de la
   *  consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer loadIdValue(DbOutputStatement statement, Integer idx)
  throws Exception {

     int index = idx.intValue();
     
     id = statement.getLongInteger(index++);
     
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
     statement.setShortText(index++, fecha);
     statement.setShortText(index++, descripcion);
     statement.setLongInteger(index++, fijo);
     
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

     statement.setShortText(index++, fecha);
     statement.setShortText(index++, descripcion);
     statement.setLongInteger(index++, fijo);
     
     return new Integer(index);
  }
  
  /**
   * Recoge el conector cuyo identificador se pasa como parámetro.
   * @param hookId Identificador del conector.
   * @throws Exception Si se produce algún error.
   */
  public void load(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     CalendarioDiaTabla table = new CalendarioDiaTabla();
     DbConnection dbConn = new DbConnection();
     boolean load = true;
  
     if (isDebugeable)
       logger.debug("Load CalendarDay <-- Date: " + fecha);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioDiaTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDiaDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        if (!DynamicFns.select(dbConn, table.getByIdQual(id), tableInfo, rowsInfo)) {
          load = false;
        }
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAY);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
        if (!load)
          throw new CalendarioExcepcion(CalendarioCodigosError.EC_NO_CALENDAR_DAY);
     }
  }
  
  /**
   * Recoge el identificador para los dias del calendario.
   * @param entidad Identificador de entidad.
   * @throws Exception Si se produce algún error.
   */
  public void get(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     CalendarioDiaTabla table = new CalendarioDiaTabla();
     DbConnection dbConn = new DbConnection();
     boolean load = true;
  
     if (isDebugeable)
       logger.debug("Get CalendarDay Id <-- entidad: " + entidad);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioDiaTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getMaxIdColumnName");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDiaDatos.class.getName());
        rowInfo.setValuesMethod("loadIdValue");
        rowsInfo.add(rowInfo);
        
        if (!DynamicFns.select(dbConn, null, tableInfo, rowsInfo)) {
          load = false;
        }
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAY_ID);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
        if (!load)
          throw new CalendarioExcepcion(CalendarioCodigosError.EC_NO_CALENDAR_DAY);
     }
  }

  /**
   * Inserta un calendario.
   * @throws Exception Si se produce algún error.
   */
  public void insert(String entidad) throws Exception {     
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     CalendarioDiaTabla table = new CalendarioDiaTabla();
     DbConnection dbConn = new DbConnection();
     
     if (isDebugeable)
       logger.debug("Insert Calendar Day <-- fecha: " + fecha);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioDiaTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDiaDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
        
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ADD_CALENDAR_DAY);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Actualiza el calendario.
   * @throws Exception Si se produce algún error.
   */
  public void update(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     CalendarioDiaTabla table = new CalendarioDiaTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Calendar Day <-- fecha: " + fecha);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioDiaTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getUpdateColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDiaDatos.class.getName());
        rowInfo.setValuesMethod("updateAll");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn, table.getByIdQual(id), tableInfo, rowsInfo);
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_UPDATE_CALENDAR_DAY);
     } catch (Exception e) {
       throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
        if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Elimina el calendario cuyo identificador se pasa como parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) throws Exception {
	 CalendarioDiaTabla table = new CalendarioDiaTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Delete Calendar Day <-- id: " + entidad);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        if (id != -1)
        	DbDeleteFns.delete(dbConn, table.getTableName(), table.getByIdQual(id));
        else
        	DbDeleteFns.delete(dbConn, table.getTableName(), null);
        
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR_DAY);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Recupera la lista de dias del calendario.
   * @param type Tipo de día (-1 todos, 0 fijos, 1 variables)
   * @param order Tipo de ordenacion (0 descripcion, 1 fecha)
   * @return Lista de días.
   * @throws Exception Si se produce algún error.
   */
  public static CalendarioDias getCalendarDays(int type, int order, String entidad) throws Exception {
	 CalendarioDias calendarDays = new CalendarioDias();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoDocument = new DynamicRow();
     CalendarioDiaTabla table = new CalendarioDiaTabla();
     DbConnection dbConn = new DbConnection();
     CalendarioDiaDatos day;
     int counter, size;
     
     logger.debug("getCalendarDays <-- tipo: " + type);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioDiaTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoDocument.setClassName(CalendarioDiaDatos.class.getName());
        rowInfoDocument.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoDocument);
        
        if (type < 0)
          DynamicFns.selectMultiple(dbConn, table.getOrder(order), false, tableInfo, rowsInfo);
        else
          DynamicFns.selectMultiple(dbConn, table.getByTypeAndOrderQual(type, order), false, tableInfo, rowsInfo);
        
        size = rowInfoDocument.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           day = (CalendarioDiaDatos)rowInfoDocument.getRow(counter);
           calendarDays.add(day);
        }
        
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAYS);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
     
     return calendarDays;
  }
}
