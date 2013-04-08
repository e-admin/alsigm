package deposito.db.sqlserver2000;

import ieci.core.db.DbConnection;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.db.DbDataSource;

import deposito.db.commonPostgreSQLServer.NoAsignablePostgreeSQLServerBaseDBEntity;

public class NoAsignableDBEntity extends
		NoAsignablePostgreeSQLServerBaseDBEntity {

	public NoAsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignableDBEntity(DbConnection conn) {
		super(conn);
	}

	public List getIdsDescendientes(String idNoAsignablePadre) {
		List ret = new ArrayList();
		try {
			String sql = "{call IdsDescendientesNoAsignable(?)}";
			CallableStatement cstmt = getConnection().getJdbcConnection()
					.prepareCall(sql);
			cstmt.setString(1, idNoAsignablePadre);
			ResultSet rs = cstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				ret.add(id);
			}

		} catch (SQLException e) {
			logger.error(e);
		}
		return ret;
	}

}
