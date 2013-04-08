/**
 * 
 */
package ieci.tecdoc.sgm.registro.util.database;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.rde.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.rde.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoCSV;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoCSVImpl;

/**
 * @author IECISA
 * 
 *         Modela una fila de la tabla de SGMRTREGISTRO_DOCS_CSV
 * 
 */
public class RegistroDocumentoCSVDatos extends RegistroDocumentoCSVImpl {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegistroDocumentoCSVDatos.class);

	public RegistroDocumentoCSVDatos() {
		super();
	}

	public RegistroDocumentoCSVDatos(RegistroDocumentoCSV registroDocumentoCSV) {
		this.csv = registroDocumentoCSV.getCsv();
		this.guid = registroDocumentoCSV.getGuid();
	}

	/**
	 * Realiza la consulta a partir del código CSV
	 * 
	 * @param csv
	 *            Código Seguro de Verificación del documento
	 * @param entidad
	 *            Entidad
	 * @throws RegistroExcepcion
	 *             Si se produce algún error.
	 */
	public void load(String csv, String entidad) throws RegistroExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("load(String, String) - start");
		}

		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		RegistroDocumentoCSVTabla table = new RegistroDocumentoCSVTabla();
		DbConnection dbConn = new DbConnection();

		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(RegistroDocumentoCSVTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);

			rowInfo.setClassName(RegistroDocumentoCSVDatos.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			DynamicFns.select(dbConn, table.getByCsvQual(csv), tableInfo, rowsInfo);

		} catch (Exception e) {
			logger.error("load(String, String)", e);

			throw new RegistroExcepcion(RegistroCodigosError.EC_RETRIEVE_DOCUMENTO_CSV);
		} finally {
			try {
				if (dbConn.existConnection())
					dbConn.close();
			} catch (Exception ee) {
				logger.error("load(String, String)", ee);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("load(String, String) - end");
		}
	}

	/**
	 * Genera la sentencia de inserción de una asociacón entre el CSV y el GUID
	 * de un documento
	 * 
	 * @param statement
	 *            Sentencia sql precompilada.
	 * @param idx
	 *            Indice de posición del primer parámetro que se recoge de la
	 *            consulta.
	 * @return Indice de posición del último parámtro recogido
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public Integer insert(DbInputStatement statement, Integer idx) throws DbExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(DbInputStatement, Integer) - start");
		}

		int index = idx.intValue();

		try {
			statement.setShortText(index++, guid);
			statement.setShortText(index++, csv);
		} catch (Exception e) {
			logger.error("insert(DbInputStatement, Integer)", e);

			throw new DbExcepcion(DbCodigosError.EC_INSERT_ALL_VALUES);
		}

		Integer returnInteger = new Integer(index);
		if (logger.isDebugEnabled()) {
			logger.debug("insert(DbInputStatement, Integer) - end");
		}
		return returnInteger;
	}

	/**
	 * Añade una asociacón entre el CSV y el GUID de un documento
	 * 
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void add(String entidad) throws RegistroExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("add(String) - start");
		}

		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		RegistroDocumentoCSVTabla table = new RegistroDocumentoCSVTabla();
		DbConnection dbConn = new DbConnection();

		try {
			// dbConn.open(Configuracion.getDatabaseConnection());
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(RegistroDocumentoCSVTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(RegistroDocumentoCSVDatos.class.getName());
			rowInfo.setValuesMethod("insert");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
		} catch (Exception e) {
			logger.error("add(String)", e);

			throw new RegistroExcepcion(RegistroCodigosError.EC_ADD_DOCUMENTO_CSV);
		} finally {
			try {
				if (dbConn.existConnection())
					dbConn.close();
			} catch (Exception ee) {
				logger.error("add(String)", ee);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("add(String) - end");
		}
	}

	/**
	 * Borra una asociacón entre el CSV y el GUID de un documento
	 * 
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void delete(String entidad) throws RegistroExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("delete(String) - start");
		}

		RegistroDocumentoCSVTabla table = new RegistroDocumentoCSVTabla();

		DbConnection dbConn = new DbConnection();

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getTableName(), table.getByCsvQual(csv));

		} catch (Exception e) {
			logger.error("delete(String)", e);

			throw new RegistroExcepcion(RegistroCodigosError.EC_DELETE_DOCUMENTO_CSV);
		} finally {
			try {
				if (dbConn.existConnection())
					dbConn.close();
			} catch (Exception ee) {
				logger.error("delete(String)", ee);

				throw new RegistroExcepcion(RegistroCodigosError.EC_DELETE_DOCUMENTO_CSV);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("delete(String) - end");
		}
	}
	
	/**
	   * Recupera todos los valores de los parámetros de la sentencia
	   * de consulta que se pasa como parámetro.
	   *
	   * @param statement Sentencia sql precompilada.
	   * @param idx Indice de posición del primer parámetro que se recoge
	   * de la consulta.
	   * @return Indice de posición del último parámetro recogido
	   * @throws DbExcepcion Si se produce algún error.
	   */
	  public Integer loadAllValues(DbOutputStatement statement, Integer idx)
	  throws DbExcepcion {
	     
	     int index = idx.intValue();
	     
	     try{	       	       
	       guid = statement.getShortText(index ++);
	       csv = statement.getShortText(index ++);
	     }catch(Exception e){
	       throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e.getCause());
	     }

	     return new Integer(index);
	  }

}
