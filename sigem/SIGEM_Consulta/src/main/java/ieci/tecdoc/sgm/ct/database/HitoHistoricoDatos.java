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
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente;
import ieci.tecdoc.sgm.ct.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.ct.database.exception.DbException;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de hitos del historico de un Expediente
 *  
 */
public class HitoHistoricoDatos extends HitoExpedienteImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(HitoHistoricoDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Constructor de clase
   */
  public HitoHistoricoDatos() {
  }
  
  /**
   * Constructor de clase
   * 
   * @param hito Objeto del que toma las propiedades
   */

  public HitoHistoricoDatos(HitoExpedienteImpl hito) {
		
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
    
     int index = idx.intValue();
     
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
   * Recupera el número de expediente.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws DbException Si se produce algún error.
   */
  public Integer getNumeroExpediente(DbOutputStatement statement, Integer idx)
  throws DbException {
     if (isDebugeable)
       logger.debug("getNumeroExpediente >> statement: " + statement.toString() + " idx entrada: " + idx);
    
     int index = idx.intValue();
     
     try{
    	 
    	numeroExpediente = statement.getShortText(index ++);
       
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }
     
     if (isDebugeable)
       logger.debug("getNumeroExpediente << NumeroExpediente: " + numeroExpediente);
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un hito.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws Exception Si se produce algún error.
   */

  public Integer insertar(DbInputStatement statement, Integer idx) throws DbException {
	  if (isDebugeable)
		  logger.debug("insert << statement: " + statement.toString());

	  int index = 1;

	  try{
		  statement.setShortText(index++, numeroExpediente);
		  statement.setShortText(index++, guid);
		  statement.setShortText(index++, codigo);
		  statement.setDateTime(index++, fecha);
		  statement.setShortText(index++, descripcion);
		  statement.setShortText(index++, informacionAuxiliar);
	  }catch(Exception e){
		  throw new DbException(DbErrorCodes.CT_INSERTAR_TODOS_LOS_VALORES);
	  }

	  return new Integer(index);
  }
  
  
  /**
   * Añade un hito al historico para un expediente.
   *
   * @throws Exception Si se produce algún error.
   */
  public void add(String entidad) throws DbException {
	  
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     HitosHistoricoTabla table = new HitosHistoricoTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("add >> Guid: " + guid);
     
     try {
    	 dbConn.open(DBSessionManager.getSession(entidad));
        tableInfo.setTableObject(table);
        tableInfo.setClassName(HitosHistoricoTabla.class.getName());
        tableInfo.setTablesMethod("getNombreTabla");
        tableInfo.setColumnsMethod("getNombresColumnas");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(HitoHistoricoDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new DbException(DbErrorCodes.CT_ANIADIR_VALOR, e.getCause());
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
     }
  }
  
  
  /**
   * Borra los hitos asociadas a un expediente.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) 
    throws ConsultaExcepcion, DbException {
     HitosHistoricoTabla table = new HitosHistoricoTabla();
     
     logger.debug("delete >> Guid: " + guid);
     
     if (guid == null || guid.equals(""))
    	 throw new ConsultaExcepcion(ConsultaCodigosError.EC_GUID_HITO_INCORRECTO);
     
     DbConnection dbConn = new DbConnection();
     
     try {
    	 dbConn.open(DBSessionManager.getSession(entidad));
        DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorGuid(guid));
        
     } catch (Exception e) {
    	 throw new DbException(DbErrorCodes.CT_BORRAR_TODOS_LOS_VALORES);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){
         throw new DbException(DbErrorCodes.CT_CERRAR_CONEXION,ee.getCause());
       }
     }
  }
  /**
   * Devuelve los hitos asociados a un expediente
   * @return Coleccion de hitos
   * @throws ConsultaExcepcion
   */
  public HitosExpediente obtenerHitosExpediente(String entidad) 
  throws ConsultaExcepcion { 
	  if (isDebugeable)
		  logger.debug("obtenerHitosExpediente >> Numero Expediente: " + numeroExpediente);

	  HitosExpediente hitos = new HitosExpediente();
	  HitoExpedienteImpl hito = null;
	  HitosHistoricoTabla tablaHitos = new HitosHistoricoTabla(); 
	  DynamicTable tableInfo = new DynamicTable();
	  DynamicRows rowsInfo = new DynamicRows();
	  DynamicRow rowInfo = new DynamicRow();
	  DbConnection dbConn = new DbConnection();

	  try {
		  dbConn.open(DBSessionManager.getSession(entidad));
		  tableInfo.setTableObject(tablaHitos);
		  tableInfo.setClassName(HitosHistoricoTabla.class.getName());
		  tableInfo.setTablesMethod("getNombreTabla");
		  tableInfo.setColumnsMethod("getNombresColumnas");
		  rowInfo.setClassName(HitoHistoricoDatos.class.getName());
		  rowInfo.setValuesMethod("cargarColumnas");
		  rowInfo.addRow(this);
		  rowsInfo.add(rowInfo);
		  if (!DynamicFns.selectMultiple(dbConn, tablaHitos.getClausulaPorNumeroExpediente(numeroExpediente),false, tableInfo, rowsInfo)) {
			  //throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);
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
		HitosHistoricoTabla table = new HitosHistoricoTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("nuevo");

		try {
		    dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(HitosHistoricoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(HitoHistoricoDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_HISTORICO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	
	
	/**
	 * Borra un hito de la tabla de historico de hitos
	 * @param dbCon Conexion a base de datos
	 * @throws Exception
	 */
	public void eliminar(String entidad) throws Exception {

		HitosHistoricoTabla table = new HitosHistoricoTabla();
		DbConnection dbConn = new DbConnection();
		
		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			
			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table
					.getClausulaPorNumeroExpediente(getNumeroExpediente()));

		} catch (Exception exc) {

		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	
	 /**
	 * Comprueba si existe un registro.
	 *
	 * @param dbCon
	 *            Conexion a base de datos.
	 * @return boolean Si existe un regisrto o no
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public static boolean existeHitoHistorico(String numeroExpediente, String entidad) throws Exception {
		
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		HitosHistoricoTabla table = new HitosHistoricoTabla();
		DbConnection dbConn = new DbConnection(); 
		boolean existe = false;

		logger.debug("existeHitoHistorico");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(HitosHistoricoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNumeroExpediente");

			rowInfo.addRow(new HitoHistoricoDatos());
			rowInfo.setClassName(HitoHistoricoDatos.class.getName());
			rowInfo.setValuesMethod("cargar");
			rowsInfo.add(rowInfo);
			existe = DynamicFns.select(dbConn,table.getClausulaPorNumeroExpediente(numeroExpediente), tableInfo, rowsInfo);

		} catch (Exception exc) {
			
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
		return existe;
	}

}