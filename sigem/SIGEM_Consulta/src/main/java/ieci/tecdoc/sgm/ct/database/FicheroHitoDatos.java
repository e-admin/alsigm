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
import ieci.tecdoc.sgm.ct.database.datatypes.FicheroHitoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito;
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
public class FicheroHitoDatos extends FicheroHitoImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(FicheroHitoDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Método constructor de la clase. Que devuelve un objeto sin propiedades
   *  
   */
  public FicheroHitoDatos() {
  }
  
/**
 * Método constructor de la clase.
 * 
 * @param fichero Objeto del que tomará las propiedades para trabajar con la base de datos. 
 */
  public FicheroHitoDatos(FicheroHitoImpl fichero) {
	  this.setGuidHito(fichero.getGuidHito());
	  this.setGuid(fichero.getGuid());
	  this.setTitulo(fichero.getTitulo());
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
    	 
    	guidHito = statement.getShortText(index ++);
 		guid = statement.getShortText(index ++);
 		titulo = statement.getShortText(index ++);
     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }
     
     if (isDebugeable)
       logger.debug("cargarColumnas << Guid del Hito: " + guidHito + ", Guid: " + guid + 
                   ", Titulo: " + titulo);
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un hito.
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
		  statement.setShortText(index++, guidHito);
		  statement.setShortText(index++, guid);
		  statement.setShortText(index++, titulo);
	  }catch(Exception e){
		  throw new DbException(DbErrorCodes.CT_INSERTAR_TODOS_LOS_VALORES);
	  }

	  return new Integer(index);
  }
	  
 /**
 * Recupera de la base de datos los Ficheros adjuntos a un Hito.
 * 
 * @param guidHito
 *            identificador del Hito.
 * @return FicherosHito Objeto de tipo ArrayList con los Ficheros
 * @throws ConsultaExcepcion
 *             Si se produce algún error.
 */
  public FicherosHito obtenerFicherosHito(String guidHito, String entidad)
  throws ConsultaExcepcion { 
	  if (isDebugeable)
		  logger.debug("obtenerFicherosHito >> Guid Hito: " + guidHito);

	  FicherosHito ficheros = new FicherosHito();
	  FicheroHitoImpl fichero = null;
	  FicheroHitoTabla tablaFicheros = new FicheroHitoTabla(); 
	  DynamicTable tableInfo = new DynamicTable();
	  DynamicRows rowsInfo = new DynamicRows();
	  DynamicRow rowInfo = new DynamicRow();
	  DbConnection dbConn = new DbConnection();

	  try {
		  dbConn.open(DBSessionManager.getSession(entidad));
		  tableInfo.setTableObject(tablaFicheros);
		  tableInfo.setClassName(FicheroHitoTabla.class.getName());
		  tableInfo.setTablesMethod("getNombreTabla");
		  tableInfo.setColumnsMethod("getNombresColumnas");
		  rowInfo.setClassName(FicheroHitoDatos.class.getName());
		  rowInfo.setValuesMethod("cargarColumnas");
		  rowInfo.addRow(this);
		  rowsInfo.add(rowInfo);
		  if (DynamicFns.selectMultiple(dbConn, tablaFicheros.getClausulaPorGuidHito(guidHito),false, tableInfo, rowsInfo)) {
			  int size = rowInfo.getRowCount();

			  for (int counter = 1; counter < size; counter++) {
				  fichero = (FicheroHitoImpl)rowInfo.getRow(counter);
				  ficheros.add(fichero);
			  }

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
	  return ficheros;  
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
		FicheroHitoTabla table = new FicheroHitoTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("nuevo");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			
			tableInfo.setTableObject(table);
			tableInfo.setClassName(FicheroHitoTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(FicheroHitoDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_FICHERO, exc.getCause());
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

		FicheroHitoTabla table = new FicheroHitoTabla();
		DbConnection dbConn = new DbConnection(); 
			
		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table
					.getClausulaPorGuid(getGuid()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_FICHERO, exc.getCause());
		} finally {
			 if (dbConn.existConnection())
				  dbConn.close();
		}
	}
}