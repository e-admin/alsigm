/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.jdbc.support.incrementer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractColumnMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

/**
 * {@link DataFieldMaxValueIncrementer} que incrementa un contador en una tabla.
 *
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GenericMaxValueIncrementer extends AbstractColumnMaxValueIncrementer {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(GenericMaxValueIncrementer.class);

	/** Nombre de la columna del tipo de contador. */
	private String typeColumnName;

	/** Tipo de contador */
	private int type;

	/**
	 * Constructor.
	 */
	public GenericMaxValueIncrementer() {
		super();
	}

	/**
	 * @return El valor de typeColumnName
	 */
	public String getTypeColumnName() {
		return typeColumnName;
	}

	/**
	 * @param typeColumnName
	 *            El valor de typeColumnName
	 */
	public void setTypeColumnName(String typeColumnName) {
		this.typeColumnName = typeColumnName;
	}

	/**
	 * @return El valor de type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            El valor de type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer#getNextKey()
	 */
	@Override
	protected synchronized long getNextKey() throws DataAccessException {

		/*
		 * Se utiliza JDBC por la necesidad de asegurar la transacción de las
		 * consultas.
		 */
		Connection con = DataSourceUtils.getConnection(getDataSource());
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());

			// Incrementar el contador
			int result = stmt.executeUpdate(getUpdateSQL());
			if (result == 0) {

				// Insertar el valor inicial del contador
				stmt.executeUpdate(getInsertSQL());

				return 1;

			} else {
				
				// Obtener el valor del contador
				ResultSet rs = stmt.executeQuery(getSelectSQL());
				try {
					if (rs.next()) {
						return rs.getLong(1) - 1;
					} else {
						throw new DataAccessResourceFailureException(
								"select failed after executing an update");
					}
				} finally {
					JdbcUtils.closeResultSet(rs);
				}
			}
			
		} catch (SQLException ex) {
			LOGGER.error("Error getting next key for "
					+ getIncrementerName(), ex);
			throw new DataAccessResourceFailureException(
					"Could not increment " + getColumnName(), ex);
		} finally {
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
	}
	
	protected String getUpdateSQL() {
		
		StringBuffer sql = new StringBuffer("UPDATE ")
				.append(getIncrementerName()).append(" SET ")
				.append(getColumnName()).append("=").append(getColumnName())
				.append("+1");

		if (StringUtils.isNotBlank(getTypeColumnName())) {
			sql.append(" WHERE ").append(getTypeColumnName()).append("=")
					.append(getType());
		}
		
		return sql.toString();
	}

	protected String getInsertSQL() {
		
		StringBuffer sql = new StringBuffer("INSERT INTO ")
				.append(getIncrementerName()).append(" (");
		
		if (StringUtils.isNotBlank(getTypeColumnName())) {
			sql.append("TYPE, ");
		}
		
		sql.append("ID) VALUES (");

		if (StringUtils.isNotBlank(getTypeColumnName())) {
			sql.append(getType()).append(",");
		}
		
		sql.append("2)");
		
		return sql.toString();
	}

	protected String getSelectSQL() {
		
		StringBuffer sql = new StringBuffer("SELECT ")
				.append(getColumnName()).append(" FROM ")
				.append(getIncrementerName());

		if (StringUtils.isNotBlank(getTypeColumnName())) {
			sql.append(" WHERE ").append(getTypeColumnName()).append("=")
					.append(getType());
		}
		
		return sql.toString();
	}
}
