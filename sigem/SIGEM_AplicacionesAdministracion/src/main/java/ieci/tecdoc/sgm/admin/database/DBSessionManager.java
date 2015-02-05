package ieci.tecdoc.sgm.admin.database;

import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManager;

import java.sql.Connection;

/**
 * $Id: DBSessionManager.java,v 1.1.2.2 2008/04/19 16:06:18 afernandez Exp $
 */

public class DBSessionManager {

	
	public static Connection getSession(String datasourceName) throws Exception{
		return DataSourceManager.getInstance().getConnection(datasourceName, DefaultConfiguration.getConfiguration());
	}

}
