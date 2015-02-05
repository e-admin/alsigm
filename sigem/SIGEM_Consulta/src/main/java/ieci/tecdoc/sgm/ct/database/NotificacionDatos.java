/**
 * @author Jose Antonio Nogales
 * 
 * Fecha de Creación: 11-may-2007
 */
package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones;
import ieci.tecdoc.sgm.ct.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.ct.database.exception.DbException;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de ficheros de un Hito
 *  
 */
public class NotificacionDatos extends NotificacionImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(NotificacionDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Método constructor de la clase. Que devuelve un objeto sin propiedades
   *  
   */
  public NotificacionDatos() {
  }
  
/**
 * Método constructor de la clase.
 * 
 * @param fichero Objeto del que tomará las propiedades para trabajar con la base de datos. 
 */
  public NotificacionDatos(NotificacionImpl notificacion) {
	  this.setDescripcion(notificacion.getDescripcion());
	  this.setDEU(notificacion.getDEU());
	  this.setExpediente(notificacion.getExpediente());
	  this.setFechaNotificacion(notificacion.getFechaNotificacion());
	  this.setHitoId(notificacion.getHitoId());
	  this.setNotificacionId(notificacion.getNotificacionId());
	  this.setServicioNotificacionesId(notificacion.getServicioNotificacionesId());
	  
  }
  
  /**
   * Recupera todos los valores de los parámetros de la sentencia
   * de consulta que se pasa como parámetro.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws DbException Si se produce algún error.
   */
  public Integer cargarColumnas(DbOutputStatement statement, Integer idx)
  throws DbException {
     if (isDebugeable)
       logger.debug("cargarColumnas >> statement: " + statement.toString() + " idx entrada: " + idx);
    
     int index = idx.intValue();
     
     try{
    	 
    	notificacionId = statement.getShortText(index ++);
 		fechaNotificacion = statement.getTimestamp(index ++);
 		deu = statement.getShortText(index ++);
 		servicioNotificacionesId = statement.getShortText(index ++);
 		expediente = statement.getShortText(index ++);
 		descripcion = statement.getShortText(index ++);
 		hitoId= statement.getShortText(index ++);
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }
     
     if (isDebugeable)
       logger.debug("cargarColumnas << Guid de la notificacion: " + notificacionId);
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de una notificacion.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws DbException Si se produce algún error.
   */

  public Integer insertar(DbInputStatement statement, Integer idx) throws DbException {
	  if (isDebugeable)
		  logger.debug("insert << statement: " + statement.toString());

	  int index = 1;

	  try{
		  statement.setShortText(index++, notificacionId);
		  statement.setDateTime(index++, fechaNotificacion);
		  statement.setShortText(index++, deu);
		  statement.setShortText(index++, servicioNotificacionesId);
		  statement.setShortText(index++, expediente);
		  statement.setShortText(index++, descripcion);
		  statement.setShortText(index++, hitoId);
	  }catch(Exception e){
		  throw new DbException(DbErrorCodes.CT_INSERTAR_TODOS_LOS_VALORES);
	  }

	  return new Integer(index);
  }
  
  /**
	 * Añade un registro.
	 *
	 * @param dbCon
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void nuevo(String entidad) throws Exception {
		DbConnection dbConn = new DbConnection();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		NotificacionTabla table = new NotificacionTabla();

		logger.debug("nuevo");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(NotificacionTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(NotificacionDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_NOTIFICACION, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	
	 /**
	 * Elimina un registro.
	 *
	 * @param dbCon
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void eliminar(String entidad) throws Exception {
		DbConnection dbConn = new DbConnection();
		NotificacionTabla table = new NotificacionTabla();

		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorNotificacion(getNotificacionId()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_NOTIFICACION, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	
	public Notificaciones getNotificacionesHitoActual(String entidad) throws Exception {
		return getNotificacionesHito(hitoId, entidad);
	}
	
	
	public Notificaciones getNotificacionesHito(String hitoId, String entidad) throws Exception {
		DbConnection dbConn = new DbConnection();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		DynamicRow rowInfo = new DynamicRow();
		NotificacionTabla table = new NotificacionTabla();
		NotificacionDatos notifcacionTmp;
		Notificaciones notificaciones = new Notificaciones();
		int counter, size;

		logger.debug("getNotificacionesHitoActual");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
	        tableInfo.setClassName(NotificacionTabla.class.getName());
	        rowInfo.addRow(this);
	        rowInfo.setClassName(NotificacionDatos.class.getName());
            tableInfo.setTablesMethod("getNombreTabla");
            tableInfo.setColumnsMethod("getNombresColumnas");
            rowInfo.setValuesMethod("cargarColumnas");
	        rowsInfo.add(rowInfo);
	        
			DynamicFns.select(dbConn,table.getClausulaPorHito(hitoId), tableInfo, rowsInfo);

			size = rowInfo.getRowCount();
	        
	        for (counter = 0; counter < size; counter++) {
	        	notifcacionTmp = (NotificacionDatos)rowInfo.getRow(counter);
	        	if (notifcacionTmp.getNotificacionId() != null)
	        		notificaciones.add(notifcacionTmp);
	        }
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
		return notificaciones;
	}
	
	public Notificaciones getNotificacionesPorIdNotificacion(String entidad) throws ConsultaExcepcion{
		try {
			return getNotificaciones(BUSQUEDA_POR_NOTIFICACION, entidad);
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, exc.getCause());
		} 
	}
	
	
	public Notificaciones getNotificacionesPorIdHito(String entidad) throws ConsultaExcepcion{
		try {
			return getNotificaciones(BUSQUEDA_POR_HITO, entidad);
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, exc.getCause());
		} 
	}
	
	
	public Notificaciones getNotificacionesPorExpediente(String entidad) throws ConsultaExcepcion{
		try {
			return getNotificaciones(BUSQUEDA_POR_EXPEDIENTE, entidad);
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, exc.getCause());
		} 
	}
	
	
	 /**
	 * Obtiene registros.
	 *
	 * @param dbCon
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
   	private Notificaciones getNotificaciones(int tipo, String entidad) throws Exception {
   		DbConnection dbConn = new DbConnection();
   		DynamicTable tableInfo = new DynamicTable();
   		DynamicRows rowsInfo = new DynamicRows();
   		DynamicRow rowInfo = new DynamicRow();
   		NotificacionTabla table = new NotificacionTabla();
   		NotificacionDatos notificacionTmp;
   		Notificaciones notificaciones = new Notificaciones();
   		int counter, size;
   		
		logger.debug("getNotificaciones");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
	        tableInfo.setClassName(NotificacionTabla.class.getName());
	        rowInfo.addRow(this);
	        rowInfo.setClassName(NotificacionDatos.class.getName());
            tableInfo.setTablesMethod("getNombreTabla");
            tableInfo.setColumnsMethod("getNombresColumnas");
            rowInfo.setValuesMethod("cargarColumnas");
	        rowsInfo.add(rowInfo);
	        
	        switch(tipo){
	        	case BUSQUEDA_POR_NOTIFICACION:
	        		DynamicFns.selectMultiple(dbConn,table.getClausulaPorNotificacion(getNotificacionId()), false, tableInfo, rowsInfo);
	        		break;
	        	case BUSQUEDA_POR_HITO:
	        		DynamicFns.selectMultiple(dbConn,table.getClausulaPorHito(getHitoId()), false, tableInfo, rowsInfo);
	        		break;
	        	case BUSQUEDA_POR_EXPEDIENTE:
	        		DynamicFns.selectMultiple(dbConn,table.getClausulaPorExpediente(getExpediente()), false, tableInfo, rowsInfo);
	        		break;
	        }
	        
	        size = rowInfo.getRowCount();
	        
	        for (counter = 0; counter < size; counter++) {
	        	notificacionTmp = (NotificacionDatos)rowInfo.getRow(counter);
	        	if (notificacionTmp.getNotificacionId() != null)
	        		notificaciones.add(notificacionTmp);
	        }
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_NOTIFICACION, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
		return notificaciones;
	}
	
	public static final int BUSQUEDA_POR_NOTIFICACION = 1;
	public static final int BUSQUEDA_POR_HITO = 2;
	public static final int BUSQUEDA_POR_EXPEDIENTE = 3;
}