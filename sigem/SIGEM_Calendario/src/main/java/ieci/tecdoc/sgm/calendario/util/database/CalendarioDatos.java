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
import ieci.tecdoc.sgm.calendario.util.Calendario;
import ieci.tecdoc.sgm.calendario.util.CalendarioImpl;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de calendario.
 * @see CalendarioTabla
 * @see Calendario
 * @see CalendarioImpl 
 */
public class CalendarioDatos extends CalendarioImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(CalendarioDatos.class);
  boolean isDebugeable = true;
  
  /**
   * Constructor de la clase CalendarioDatos
   */  
  public CalendarioDatos() {
  }
  
  
  /**
   * Constructor de la clase CalendarioDatos
   * @param calendar Datos relacionados con un calendario
   */
  public CalendarioDatos(Calendario calendar) {
     this.setDias(calendar.getDias());
     this.setHoraFin(calendar.getHoraFin());
     this.setHoraInicio(calendar.getHoraInicio());
     this.setMinutoFin(calendar.getMinutoFin());
     this.setMinutoInicio(calendar.getMinutoInicio());
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
     
     dias = statement.getShortText(index++);
     horaInicio = statement.getShortText(index++);
     minutoInicio = statement.getShortText(index++);
     horaFin = statement.getShortText(index++);
     minutoFin = statement.getShortText(index++);
     
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

     statement.setShortText(index++, dias);
     statement.setShortText(index++, horaInicio);
     statement.setShortText(index++, minutoInicio);
     statement.setShortText(index++, horaFin);
     statement.setShortText(index++, minutoFin);
     
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

     statement.setShortText(index++, dias);
     statement.setShortText(index++, horaInicio);
     statement.setShortText(index++, minutoInicio);
     statement.setShortText(index++, horaFin);
     statement.setShortText(index++, minutoFin);
     
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
     CalendarioTabla table = new CalendarioTabla();
     DbConnection dbConn = new DbConnection();
     boolean load = true;
  
     if (isDebugeable)
       logger.debug("Load Calendar <-- EntidadId: " + entidad);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        if (!DynamicFns.select(dbConn, null, tableInfo, rowsInfo)) {
          load = false;
        }
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
        if (!load)
          throw new CalendarioExcepcion(CalendarioCodigosError.EC_NO_CALENDAR);
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
     CalendarioTabla table = new CalendarioTabla();
     DbConnection dbConn = new DbConnection();
     
     if (isDebugeable)
       logger.debug("Insert Calendar <-- EntidadId: " + entidad);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
        
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ADD_CALENDAR);
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
     CalendarioTabla table = new CalendarioTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Calendar <-- EntidadId: " + entidad);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(CalendarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getUpdateColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(CalendarioDatos.class.getName());
        rowInfo.setValuesMethod("updateAll");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn, null, tableInfo, rowsInfo);
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_UPDATE_CALENDAR);
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
	  CalendarioTabla table = new CalendarioTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Delete Calendar <-- EntidadId: " + entidad);
     
     try {
        dbConn.open(DBSessionManager.getSession(entidad));
        
        DbDeleteFns.delete(dbConn, table.getTableName(), null);
        
     } catch (CalendarioExcepcion e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR);
     } catch (Exception e) {
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
}
