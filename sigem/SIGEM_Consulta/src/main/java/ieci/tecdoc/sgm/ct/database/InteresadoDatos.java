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
import ieci.tecdoc.sgm.ct.database.datatypes.Expediente;
import ieci.tecdoc.sgm.ct.database.datatypes.Expedientes;
import ieci.tecdoc.sgm.ct.database.datatypes.InteresadoImpl;
import ieci.tecdoc.sgm.ct.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.ct.database.exception.DbException;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de expedientes del CT.
 *  
 */
public class InteresadoDatos extends InteresadoImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(InteresadoDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Constructor de clase
   */
  
	public InteresadoDatos() {
	}

	/**
	   * Constructor de clase
	   * 
	   * @param interesado del que toma las propiedades
	   */
	public InteresadoDatos(InteresadoImpl interesado) {
		
		this.setNumeroExpediente(interesado.getNumeroExpediente());
		this.setNIF(interesado.getNIF());
		this.setNombre(interesado.getNombre());
		this.setPrincipal(interesado.getPrincipal());
		this.setInformacionAuxiliar(interesado.getInformacionAuxiliar());
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
 		NIF = statement.getShortText(index ++);
 		nombre = statement.getShortText(index ++);
 		principal = statement.getShortText(index ++);
  		informacionAuxiliar = statement.getShortText(index ++);
       
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }
     
     if (isDebugeable)
       logger.debug("cargarColumnas << NumeroExpediente: " + numeroExpediente + " NIF: " + NIF + 
                   " Nombre: " + nombre + " Principal: " + principal + " InformacionAuxiliar: " + informacionAuxiliar);
     
     return new Integer(index);
  }
  
  /**
   * De valor al interesado por el NIF que es su identificador unico.
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del campo a cargar.
   * @throws DbException Si se produce algún error.
   */
  public Integer cargarExpedientes(DbOutputStatement statement, Integer idx)
  throws DbException {
     if (isDebugeable)
       logger.debug("cargarExpedientes >> statement: " + statement.toString());
     int index = idx.intValue();
     try{ 
        if(expedientes == null){
        	expedientes = new Expedientes();
        }
        do  {
        	numeroExpediente = statement.getShortText(index);
        	ExpedienteDatos expDatos = new ExpedienteDatos();
        	expDatos.setNumero(numeroExpediente);
  		  	//expDatos.load();
  		  	expedientes.add(expDatos);
        } while (statement.next());
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_CARGAR_EXPEDIENTES_INTERESADO, e.getCause());
     }
     return new Integer(index);
  }
  
  /**
   * Realiza la consulta por NIF.
   *
   * @param dbCon Objeto conexión a base de datos.
   * @throws DbException Si se produce algún error.
   */
  public void cargarExpedientesPorNIF(boolean detalle, String entidad)
  throws ConsultaExcepcion {

	  if (isDebugeable)
		  logger.debug("cargarExpedientesPorNIF >> NIF: " + NIF);

	  DbConnection dbConn = new DbConnection();

	  try {
		  dbConn.open(DBSessionManager.getSession(entidad));
		  
		  boolean NIFIncorrecto = false;
		  if (NIF == null || NIF.equals(""))
			  NIFIncorrecto = true;
		  DynamicTable tableInfo = new DynamicTable();
		  DynamicRows rowsInfo = new DynamicRows();
		  DynamicRow rowInfo = new DynamicRow();
		  InteresadosTabla tablaInteresados = new InteresadosTabla();

		  tableInfo.setTableObject(tablaInteresados);
		  tableInfo.setClassName(InteresadosTabla.class.getName());
		  tableInfo.setTablesMethod("getNombreTabla");
		  tableInfo.setColumnsMethod("getNombreColumnaNumeroExpediente");
		  rowInfo.setClassName(InteresadoDatos.class.getName());
		  rowInfo.setValuesMethod("cargarExpedientes");
		  rowInfo.addRow(this);
		  rowsInfo.add(rowInfo);
		  DynamicFns.select(dbConn, tablaInteresados.getClausulaPorNIF(NIF), tableInfo, rowsInfo);

		  if (detalle) {
			  if (this.expedientes != null) {
				  for(int i=0; i<this.expedientes.count(); i++){
					  ExpedienteDatos expDatos = new ExpedienteDatos();
			          expDatos.setNumero(((Expediente)this.expedientes.get(i)).getNumero());
			  		  expDatos.load(entidad);
			  		  this.expedientes.set(i, expDatos);
				  }
			  }
		  }
	  } catch (Exception e) {
		  throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_INTERESADO_EXPEDIENTE);
	  } finally {
		  try{
			  if (dbConn.existConnection())
				  dbConn.close();
		  }catch(Exception ee){
			  DbException DbEx = new DbException(DbErrorCodes.CT_CERRAR_CONEXION);
			  logger.warn(this, DbEx.getCause());
		  }
	  }
  }

  
  
  /**
   * Genera la sentencia de inserción de un expediente.
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
    	statement.setShortText(index ++, numeroExpediente);
 		statement.setShortText(index ++, NIF);
 		statement.setShortText(index ++, nombre);
 		statement.setShortText(index ++, principal);
 		statement.setShortText(index ++, informacionAuxiliar);
    } catch(Exception e){
      throw new DbException(DbErrorCodes.CT_INSERTAR_TODOS_LOS_VALORES);
    }
    
    return new Integer(index);
  }
  
  
  /**
	 * Borra el Interesado.
	 * 
	 @param dbCon
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void eliminar(String entidad) throws Exception {
		InteresadosTabla table = new InteresadosTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorNIF(getNIF()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_INTERESADO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}

	
	 /**
	 * Borra el Interesado.
	 * 
	 @param dbCon
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void eliminarPorExpediente(String entidad) throws Exception {
		InteresadosTabla table = new InteresadosTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("eliminar por expediente");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorNumeroExpediente(getNumeroExpediente()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_INTERESADO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
	
  
  /**
	 * Añade un registro.
	 * 
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void nuevo(String entidad) throws Exception {
	
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		InteresadosTabla table = new InteresadosTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("nuevo");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(InteresadosTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(InteresadoDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_INTERESADO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}

}