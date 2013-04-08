package ieci.tecdoc.idoc.admin.database;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;


import java.sql.Connection;

public class DBSessionManager {

	private static Config config = new Config();
	
	public static Connection getSession(String entidad) throws Exception{
		return DataSourceManagerMultientidad.getInstance().getConnection(
				DataSourceManagerMultientidad.BACKOFFICE_DATASOURCE_NAME,
				config, entidad);
	}
}
