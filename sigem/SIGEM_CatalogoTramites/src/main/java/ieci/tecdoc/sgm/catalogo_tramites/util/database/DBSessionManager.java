package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.catalogo_tramites.Configuracion;
import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;

import java.sql.Connection;

public class DBSessionManager {

	public static Connection getSession(String entidad) throws Exception{
		return DataSourceManagerMultientidad.getInstance().getConnection(
				DataSourceManagerMultientidad.DEFAULT_DATASOURCE_NAME,
				Configuracion.getConfiguracion(), entidad);
	}
}
