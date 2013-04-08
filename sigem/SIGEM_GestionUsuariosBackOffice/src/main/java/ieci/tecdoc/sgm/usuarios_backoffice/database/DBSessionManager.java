package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;
import ieci.tecdoc.sgm.usuarios_backoffice.Configuracion;

import java.sql.Connection;

public class DBSessionManager {

	public static Connection getSession(String entidad) throws Exception{
		return DataSourceManagerMultientidad.getInstance().getConnection(
				DataSourceManagerMultientidad.BACKOFFICE_DATASOURCE_NAME,
				Configuracion.getConfiguracion(), entidad);
	}

	public static Connection getSession(String datasourceName, String entidad) throws Exception{
		return DataSourceManagerMultientidad.getInstance().getConnection(
				datasourceName, Configuracion.getConfiguracion(), entidad);
	}
}

