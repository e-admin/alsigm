package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

public class DbPreparedStatement {
	
	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(DbPreparedStatement.class);

	// Constantes: son necesarias para esta
	public static final int DATE = Types.DATE;
	public static final int DOUBLE = Types.DOUBLE;
	public static final int FLOAT = Types.FLOAT;
	public static final int INTEGER = Types.INTEGER;
	public static final int NUMERIC = Types.NUMERIC;
	public static final int VARCHAR = Types.VARCHAR;

	// sentencia sql precompilada y parametrizada
	private PreparedStatement mps;

	/**
	 * Constructor
	 * @param ps sentencia sql precompilada y parametrizada
	 */
	public DbPreparedStatement(PreparedStatement ps) {
		mps = ps;
	}

	/**
	 * Cierra la sentencia sql precompilada y parametrizada
	 * @return true si la operacion tuvo exito; false en caso contrario
	 */
	public boolean close() {
		try {
			mps.close();
			return true;

		} catch (SQLException e) {
			logger.warn("DbPreparedStatement::close()" + e.toString());
			return false;
		}

	}

	/**
	 * Establece la fecha date como parametro i-esimo de la consulta
	 * 
	 * @param idx
	 *            indice iesimo de los parametros de la consulta
	 * @param date
	 *            fecha
	 */
	public void setDate(int idx, Date date) throws ISPACException {
		try {
			mps.setDate(idx, date);
		} catch (SQLException e) {
			throw new ISPACException("DbPreparedStatement::setDate, error", e);
		}
	}

	/**
	 * Establece el valor int 'value' como parametro i-esimo de la consulta
	 * 
	 * @param idx
	 *            indice iesimo de los parametros de la consulta
	 * @param value
	 *            valor
	 */
	public void setInt(int idx, int value) throws ISPACException {
		try {
			mps.setInt(idx, value);
		} catch (SQLException e) {
			throw new ISPACException("DbPreparedStatement::setInt, error", e);
		}
	}

	/**
	 * Establece el valor string 'value' como parametro i-esimo de la consulta
	 * 
	 * @param idx
	 *            indice iesimo de los parametros de la consulta
	 * @param value
	 *            valor
	 */
	public void setString(int idx, String value) throws ISPACException {
		try {
			mps.setString(idx, value);
		} catch (SQLException e) {
			throw new ISPACException("DbPreparedStatement::setString, error", e);
		}
	}

	/**
	 * Establece el valor 'value' como parametro i-esimo de la consulta
	 * 
	 * @param idx
	 *            indice iesimo de los parametros de la consulta
	 * @param value
	 *            valor
	 */
	public void setObject(int idx, Object value) throws ISPACException {
		try {
			mps.setObject(idx, value);
		} catch (SQLException e) {
			throw new ISPACException("DbPreparedStatement::setObject, error", e);
		}
	}

	/**
	 * Establece a null el parametro iesimo de la consulta
	 * 
	 * @param idx
	 *            indice iesimo de los parametros de la consulta
	 * @param type
	 *            tipo: definido segun la clase java.sql.Types
	 */
	public void setNull(int idx, int type) throws ISPACException {
		try {
			mps.setNull(idx, type);
		} catch (SQLException e) {
			throw new ISPACException("DbPreparedStatement::setNull, error", e);
		}
	}

	/**
	 * Establece el valor del stream 'is' como parametro i-esimo de la consulta
	 * 
	 * @param idx
	 *            indice iesimo de los parametros de la consulta
	 * @param is
	 *            Stream de lectura
     * @param length
	 *            Número de bytes del stream.
	 */
	public void setBinaryStream(int idx, InputStream is, int length) throws ISPACException {
		try {
			mps.setBinaryStream(idx, is, length);
		} catch (SQLException e) {
			throw new ISPACException("DbPreparedStatement::setBinaryStream, error", e);
		}
	}

	/**
	 * Ejecuta la query que representa el objeto @result Objeto de tipo DbQuery
	 * que encapsula los resultados de la query
	 */
	public DbQuery executeQuery() throws ISPACException {
		DbQuery dbq = null;
		try {
			ResultSet rs = mps.executeQuery();
			// El statement se pasa como null ya que queremos que éste sea cerrado
			// cuando se cierre el objeto DbPreparedStatement y no cuando se
			// cierre el objeto DbQuery
			dbq = new DbQuery(null, rs);
		} catch (SQLException e) {
			throw new ISPACException (
					"DbPreparedStatement::executeQuery(), error", e);
		}
		return dbq;
	}

	/**
	 * Ejecuta la operacion INSERT, UPDATE o DELETE que encapsula la sentencia
	 * sql @result numero de filas efectadas; -1 si hubo error
	 */
	public int executeUpdate() throws ISPACException {
		int nRows = -1;
		try {
			nRows = mps.executeUpdate();
	    } catch (SQLException e) {
			throw new ISPACException (
					"DbPreparedStatement::executeUpdate (), error", e);
		}
		return nRows;
	}

}