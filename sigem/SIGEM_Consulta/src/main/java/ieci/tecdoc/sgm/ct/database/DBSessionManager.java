package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;
import ieci.tecdoc.sgm.ct.Configuracion;

import java.sql.Connection;

public class DBSessionManager {

	public static Connection getSession(String entidad) throws Exception{
		return DataSourceManagerMultientidad.getInstance().getConnection(
				DataSourceManagerMultientidad.DEFAULT_DATASOURCE_NAME,
				Configuracion.getConfiguracion(), entidad);
	}
}
