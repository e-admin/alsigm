package es.ieci.tecdoc.fwktd.time.jdbc;

import javax.sql.DataSource;

public class SqlServerTimeServiceImpl extends JdbcTimeServiceImpl {

	/**
	 * Consulta especifica de Sql Server para obtener la hora actual de la base de datos
	 */
	private final String DEFAULT_CURRENT_DATE_QUERY = "SELECT getdate()";

	/**
	 * @param dataSource Fuente de datos a utilizar
	 */
	public SqlServerTimeServiceImpl(DataSource dataSource) {
		super(dataSource);
		query = DEFAULT_CURRENT_DATE_QUERY;
	}

}
