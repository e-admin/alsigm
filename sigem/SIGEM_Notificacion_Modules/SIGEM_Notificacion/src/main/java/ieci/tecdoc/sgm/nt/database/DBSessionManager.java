package ieci.tecdoc.sgm.nt.database;

import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;

import java.sql.Connection;

public class DBSessionManager {

	public static Connection getSession(String entidad) throws Exception{
		return DataSourceManagerMultientidad.getInstance().getConnection(
				DataSourceManagerMultientidad.DEFAULT_DATASOURCE_NAME,
				DefaultConfiguration.getConfiguration(), entidad);
	}
}