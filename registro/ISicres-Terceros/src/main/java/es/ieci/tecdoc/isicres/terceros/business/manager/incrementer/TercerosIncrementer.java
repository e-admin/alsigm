package es.ieci.tecdoc.isicres.terceros.business.manager.incrementer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

/**
 *
 * @author IECISA
 *
 */
public class TercerosIncrementer extends AbstractDataFieldMaxValueIncrementer {

	public TercerosIncrementer(String incrementerName) {
		setIncrementerName(incrementerName);
	}

	@Override
	protected long getNextKey() {
		Connection conn = DataSourceUtils.getConnection(getDataSource());
		PreparedStatement stmt = null;
		long key;
		try {
			stmt = conn
					.prepareStatement("update scr_contador set contador=contador+1 where tablaid=?");
			stmt.setString(1, getIncrementerName());

			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());

			stmt.executeUpdate();

			JdbcUtils.closeStatement(stmt);

			stmt = conn
					.prepareStatement("select contador from scr_contador where tablaid=?");
			stmt.setString(1, getIncrementerName());

			ResultSet rs = stmt.executeQuery();
			try {
				if (!rs.next()) {
					throw new DataAccessResourceFailureException(
							"No se ha recuperado del valor del contador para la entidad "
									+ getIncrementerName());
				}
				key = rs.getLong(1);
			} finally {
				JdbcUtils.closeResultSet(rs);
			}

		} catch (SQLException ex) {
			throw new DataAccessResourceFailureException(
					"No se ha podido incrementar el contador para la entidad "
							+ getIncrementerName(), ex);
		} finally {
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
		return key;
	}

}
