/**
 * @author Javier Septién Arceredillo
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
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente;
import ieci.tecdoc.sgm.ct.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.ct.database.exception.DbException;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de hitos del estado de un Expediente
 *  
 */
public class HitoEstadoDatos extends HitoExpedienteImpl implements Serializable{
	
  private static final Logger logger = Logger.getLogger(HitoEstadoDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Método constructor de la clase. Que devuelve un objeto sin propiedades
   *  
   */
  public HitoEstadoDatos() {
  }

  
  /**
   * Método constructor de la clase.
   * 
   * @param hito Objeto del que tomará las propiedades para trabajar con la base de datos. 
   */
   
  public HitoEstadoDatos(HitoExpedienteImpl hito) {
		
		this.setNumeroExpediente(hito.getNumeroExpediente());
		this.setGuid(hito.getGuid());
		this.setCodigo(hito.getCodigo());
		this.setFecha(hito.getFecha());
		this.setDescripcion(hito.getDescripcion());
		this.setInformacionAuxiliar(hito.getInformacionAuxiliar());
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
    
     int index = 1;
     
     try{
    	 
    	numeroExpediente = statement.getShortText(index ++);
 		guid = statement.getShortText(index ++);
 		codigo = statement.getShortText(index ++);
 		fecha = statement.getDateTime(index ++);
 		descripcion = statement.getShortText(index ++);
  		informacionAuxiliar = statement.getShortText(index ++);
       
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }
     
     if (isDebugeable)
       logger.debug("cargarColumnas << NumeroExpediente: " + numeroExpediente + " guid: " + guid + 
                   " Codigo: " + codigo + " Fecha: " + fecha + " Descripcion: " + descripcion  + " InformacionAuxiliar: " + informacionAuxiliar);
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un hito.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws ConsultaExcepcion Si se produce algún error.
   */
 
  public Integer insertar(DbInputStatement statement, Integer idx) throws ConsultaExcepcion {
    if (isDebugeable)
    logger.debug("insertar >> statement: " + statement.toString() + " idx entrada: " + idx);
    
    int index = 1;
    
    try{
    	statement.setShortText(index++, numeroExpediente);
    	//
    	guid = new Guid().toString();
 		statement.setShortText(index++, guid);
 		statement.setShortText(index++, codigo);
 		statement.setDateTime(index++, fecha);
 		statement.setShortText(index++, descripcion);
 		statement.setShortText(index++, informacionAuxiliar);
    }catch(Exception e){
      throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_ESTADO, e.getCause());
    }

    if (isDebugeable)
      logger.debug("insert << statement: " + statement.toString());
    
    return new Integer(index);
  }
  
  /**
   * Obtiene los hitos asociados a un expediente
   * @return HitosExpediente Objeto tipo colección de hitos
   */
  public HitosExpediente obtenerHitosExpediente(String entidad) 
  throws ConsultaExcepcion { 
	  if (isDebugeable)
		  logger.debug("obtenerHitosExpediente >> Numero Expediente: " + numeroExpediente);

	  HitosExpediente hitos = new HitosExpediente();
	  HitoExpedienteImpl hito = null;
	  HitosEstadoTabla tablaHitos = new HitosEstadoTabla(); 
	  DynamicTable tableInfo = new DynamicTable();
	  DynamicRows rowsInfo = new DynamicRows();
	  DynamicRow rowInfo = new DynamicRow();
	  DbConnection dbConn = new DbConnection();

	  try {
		  dbConn.open(DBSessionManager.getSession(entidad));
		  tableInfo.setTableObject(tablaHitos);
		  tableInfo.setClassName(HitosEstadoTabla.class.getName());
		  tableInfo.setTablesMethod("getNombreTabla");
		  tableInfo.setColumnsMethod("getNombresColumnas");
		  rowInfo.setClassName(HitoEstadoDatos.class.getName());
		  rowInfo.setValuesMethod("cargarColumnas");
		  rowInfo.addRow(this);
		  rowsInfo.add(rowInfo);
		  if (!DynamicFns.selectMultiple(dbConn, tablaHitos.getClausulaPorNumeroExpediente(numeroExpediente),false, tableInfo, rowsInfo)) {
			  throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);
		  }
		  int size = rowInfo.getRowCount();

		  for (int counter = 1; counter < size; counter++) {
			  hito = (HitoExpedienteImpl)rowInfo.getRow(counter);
			  hitos.add(hito);
		  }

	  } catch (Exception e) {
		  throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_HITOS_EXPEDIENTE, e.getCause());
	  } finally {
		  try{
			  if (dbConn.existConnection())
				  dbConn.close();
		  }catch(Exception ee){
			  DbException DbEx = new DbException(DbErrorCodes.CT_CERRAR_CONEXION);
			  logger.warn(this, DbEx.getCause());
		  }
	  }
	  return hitos;  
  }
  
  /**
   * Carga los valores del hito de estado de un expediente
   *
   * @throws ConsultaExcepcion Si se produce algún error.
   */
  public void cargar(String entidad) throws ConsultaExcepcion {

	  if (numeroExpediente == null || numeroExpediente.equals(""))
		  throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);
	  DynamicTable tableInfo = new DynamicTable();
	  DynamicRows rowsInfo = new DynamicRows();
	  DynamicRow rowInfo = new DynamicRow();
	  HitosEstadoTabla tablaHitos = new HitosEstadoTabla();
	  DbConnection dbConn = new DbConnection();

	  if (isDebugeable)
		  logger.debug("load >> numeroExpediente: " + numeroExpediente);

	  boolean codigoIncorrecto = false;

	  try {
		  dbConn.open(DBSessionManager.getSession(entidad));
		  tableInfo.setTableObject(tablaHitos);
		  tableInfo.setClassName(HitosEstadoTabla.class.getName());
		  tableInfo.setTablesMethod("getNombreTabla");
		  tableInfo.setColumnsMethod("getNombresColumnas");
		  rowInfo.setClassName(HitoEstadoDatos.class.getName());
		  rowInfo.setValuesMethod("cargarColumnas");
		  rowInfo.addRow(this);
		  rowsInfo.add(rowInfo);

		  if (!DynamicFns.select(dbConn, tablaHitos.getClausulaPorNumeroExpediente(numeroExpediente), tableInfo, rowsInfo, false)) {
			  codigoIncorrecto = true;
		  }
	  } catch (Exception e) {
		  throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTE, e.getCause());
	  } finally {
		  try{
			  if (dbConn.existConnection())
				  dbConn.close();
		  }catch(Exception ee){
			  DbException DbEx = new DbException(DbErrorCodes.CT_CERRAR_CONEXION);
			  logger.warn(this, DbEx.getCause());
		  }

		  if (codigoIncorrecto)
			  throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_HITO_ESTADO_EXPEDIENTE);
	  }
  }
  
  /**
   * Borra los hitos asociadas a un expediente.
   * @param dbCon
   *             Conexion a base de datos.
   * @throws Exception Si se produce algún error.
   */
  public void eliminar(String entidad) throws Exception {
		
		HitosEstadoTabla table = new HitosEstadoTabla();
		DbConnection dbConn = new DbConnection(); 

		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table
					.getClausulaPorNumeroExpediente(getNumeroExpediente()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_HITO_ESTADO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
  /**
	 * Recupera la información del hito de un expediente.
	 * 
	 * @param numeroExpediente
	 *            Número de expediente para cargar el hito.
	 * @param dbCon 
	 *             Conexión a base de datos
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void cargar(String numeroExpediente, String entidad) throws Exception {
		
		 if (numeroExpediente == null || numeroExpediente.equals(""))
			  throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		HitosEstadoTabla table = new HitosEstadoTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("load");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			
			tableInfo.setTableObject(table);
			tableInfo.setClassName(HitosEstadoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(HitoEstadoDatos.class.getName());
			rowInfo.setValuesMethod("cargarColumnas");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(dbConn, table.getClausulaPorNumeroExpediente(numeroExpediente),
					tableInfo, rowsInfo)) {
				throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTE);
			}
		} catch (ConsultaExcepcion e) {
			throw e;
		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTE);
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
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
	
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		HitosEstadoTabla table = new HitosEstadoTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("nuevo");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(HitosEstadoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(HitoEstadoDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_ESTADO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	 /**
	 * Comprueba si existe un Hito actual para un Expediente.
	 * @param numeroExpediente
	 *            Numero del Expediente 
	 * @param dbCon
	 *            Conexión a base de datos.
	 * @return boolean
	 *            Si existe un registro o no
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public static boolean existeHitoActual(String numeroExpediente, String entidad) throws Exception {
		
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		HitosEstadoTabla table = new HitosEstadoTabla();
		DbConnection dbConn = new DbConnection();

		boolean existe = false;

		logger.debug("existeHitoActual");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(HitosEstadoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(new HitoEstadoDatos());
			rowInfo.setClassName(HitoEstadoDatos.class.getName());
			rowInfo.setValuesMethod("cargarColumnas");
			rowsInfo.add(rowInfo);
			existe = DynamicFns.select(dbConn,table.getClausulaPorNumeroExpediente(numeroExpediente), tableInfo, rowsInfo, false);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_HITO_ESTADO_EXPEDIENTE, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
		return existe;
	}
}
