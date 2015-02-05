package ieci.tecdoc.sgm.entidades.database;

import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManager;

import java.sql.Connection;

public class DBSessionManager {

	public static Connection getSession() throws Exception{
		return DataSourceManager.getInstance().getConnection(DefaultConfiguration.getConfiguration());
	}
	
	public static Connection getSession(String datasourceName) throws Exception{
		return DataSourceManager.getInstance().getConnection(datasourceName, DefaultConfiguration.getConfiguration());
	}
}
