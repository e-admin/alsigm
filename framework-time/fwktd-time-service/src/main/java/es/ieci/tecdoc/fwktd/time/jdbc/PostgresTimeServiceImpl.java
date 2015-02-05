package es.ieci.tecdoc.fwktd.time.jdbc;

import javax.sql.DataSource;

public class PostgresTimeServiceImpl extends JdbcTimeServiceImpl{

	/**
	 * Consulta especifica de Postgres para obtener la hora actual de la base de datos
	 */
	private final String DEFAULT_CURRENT_DATE_QUERY = "SELECT NOW()";

	/**
	 * @param dataSource Fuente de datos a utilizar
	 */
	public PostgresTimeServiceImpl(DataSource dataSource) {
		super(dataSource);
		query = DEFAULT_CURRENT_DATE_QUERY;
	}
}
