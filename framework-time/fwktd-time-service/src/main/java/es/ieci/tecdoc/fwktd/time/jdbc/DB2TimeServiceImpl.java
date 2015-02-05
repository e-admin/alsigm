package es.ieci.tecdoc.fwktd.time.jdbc;

import javax.sql.DataSource;

public class DB2TimeServiceImpl extends JdbcTimeServiceImpl {

	/**
	 * Consulta especifica de DB2 para obtener la hora actual de la base de datos
	 */
	private final String DEFAULT_CURRENT_DATE_QUERY = "SELECT CURRENT_TIMESTAMP FROM sysibm.sysdummy1";

	/**
	 * @param dataSource Fuente de datos a utilizar
	 */
	public DB2TimeServiceImpl(DataSource dataSource) {
		super(dataSource);
		query = DEFAULT_CURRENT_DATE_QUERY;
	}

}
