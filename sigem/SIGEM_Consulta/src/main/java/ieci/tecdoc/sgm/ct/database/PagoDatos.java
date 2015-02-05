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
import ieci.tecdoc.sgm.ct.database.datatypes.PagoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Pagos;
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
public class PagoDatos extends PagoImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(PagoDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Método constructor de la clase. Que devuelve un objeto sin propiedades
   *  
   */
  public PagoDatos() {

  }
  
/**
 * Método constructor de la clase.
 * 
 * @param fichero Objeto del que tomará las propiedades para trabajar con la base de datos. 
 */
  public PagoDatos(PagoImpl pago) {
	  this.setIdentificador(pago.getIdentificador());
	  this.setIdDocumento(pago.getIdDocumento());
	  this.setMensajeParaElCiudadano(pago.getMensajeParaElCiudadano());
	  this.setIdentificadorHito(pago.getIdentificadorHito());
	  this.setFecha(pago.getFecha());
	  this.setNumeroExpediente(pago.getNumeroExpediente());
	  this.setAutoliquidacionId(pago.getAutoliquidacionId());
	  this.setEntidadEmisoraId(pago.getEntidadEmisoraId());
	  this.setImporte(pago.getImporte());
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
     	setIdentificador(statement.getShortText(index ++));
 		setIdDocumento(statement.getShortText(index ++));
 		setMensajeParaElCiudadano(statement.getShortText(index ++));
 		setIdentificadorHito(statement.getShortText(index ++));
 		setFecha(statement.getTimestamp(index ++));
 		setNumeroExpediente(statement.getShortText(index ++));
    	entidadEmisoraId = statement.getShortText(index ++);
 		autoliquidacionId = statement.getShortText(index ++);
 		importe = statement.getShortText(index ++);
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }
     
     if (isDebugeable)
       logger.debug("cargarColumnas << PagoId: " + getIdentificador());
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un pago.
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
		  statement.setShortText(index++, getIdentificador());
		  statement.setShortText(index++, getIdDocumento());
		  statement.setShortText(index++, getMensajeParaElCiudadano());
		  statement.setShortText(index++, getIdentificadorHito());
		  statement.setDateTime(index++, getFecha());
		  statement.setShortText(index++, getNumeroExpediente());
		  statement.setShortText(index++, entidadEmisoraId);
		  statement.setShortText(index++, autoliquidacionId);
		  statement.setShortText(index++, importe);
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
		PagoTabla table = new PagoTabla();

		logger.debug("nuevo");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(PagoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(PagoDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_PAGO, exc.getCause());
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
		PagoTabla table = new PagoTabla();
		
		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorPago(getIdentificador()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_PAGO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	
	
	public Pagos getPagosPorIdPago(String entidad) throws ConsultaExcepcion{
		try {
			return getPagos(BUSQUEDA_POR_SUBSANACION, entidad);
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, exc.getCause());
		} 
	}
	
	
	public Pagos getPagosPorIdHito(String entidad) throws ConsultaExcepcion{
		try {
			return getPagos(BUSQUEDA_POR_HITO, entidad);
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, exc.getCause());
		} 
	}
	
	
	public Pagos getPagosPorExpediente(String entidad) throws ConsultaExcepcion{
		try {
			return getPagos(BUSQUEDA_POR_EXPEDIENTE, entidad);
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, exc.getCause());
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
   	private Pagos getPagos(int tipo, String entidad) throws Exception {
   		DbConnection dbConn = new DbConnection();
   		DynamicTable tableInfo = new DynamicTable();
   		DynamicRows rowsInfo = new DynamicRows();
   		DynamicRow rowInfo = new DynamicRow();
   		PagoTabla table = new PagoTabla();
   		PagoDatos pagoTmp;
   		Pagos pagos = new Pagos();
   		int counter, size;
   		
		logger.debug("getPagos");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
	        tableInfo.setClassName(PagoTabla.class.getName());
	        rowInfo.addRow(this);
	        rowInfo.setClassName(PagoDatos.class.getName());
            tableInfo.setTablesMethod("getNombreTabla");
            tableInfo.setColumnsMethod("getNombresColumnas");
            rowInfo.setValuesMethod("cargarColumnas");
	        rowsInfo.add(rowInfo);
	        
	        switch(tipo){
	        	case BUSQUEDA_POR_SUBSANACION:
	        		DynamicFns.selectMultiple(dbConn,table.getClausulaPorPago(getIdentificador()), false, tableInfo, rowsInfo);
	        		break;
	        	case BUSQUEDA_POR_HITO:
	        		DynamicFns.selectMultiple(dbConn,table.getClausulaPorHito(getIdentificadorHito()), false, tableInfo, rowsInfo);
	        		break;
	        	case BUSQUEDA_POR_EXPEDIENTE:
	        		DynamicFns.selectMultiple(dbConn,table.getClausulaPorExpediente(getNumeroExpediente()), false, tableInfo, rowsInfo);
	        		break;
	        }
	        
	        size = rowInfo.getRowCount();
	        
	        for (counter = 0; counter < size; counter++) {
	        	pagoTmp = (PagoDatos)rowInfo.getRow(counter);
	        	if (pagoTmp.getIdentificador() != null)
	        		pagos.add(pagoTmp);
	        }
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_PAGO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
		return pagos;
	}
   	
   	
   	public static final int BUSQUEDA_POR_SUBSANACION = 1;
	public static final int BUSQUEDA_POR_HITO = 2;
	public static final int BUSQUEDA_POR_EXPEDIENTE = 3;
}