package es.ieci.tecdoc.fwktd.time.jdbc;

import javax.sql.DataSource;

public class OracleTimeServiceImpl extends JdbcTimeServiceImpl{

	/**
	 * Consulta especifica de ORACLE para obtener la hora actual de la base de datos
	 */
	private final String DEFAULT_CURRENT_DATE_QUERY = "SELECT SYSDATE FROM DUAL";

	/**
	 * @param dataSource Fuente de datos a utilizar
	 */
	public OracleTimeServiceImpl(DataSource dataSource) {
		super(dataSource);
		query = DEFAULT_CURRENT_DATE_QUERY;
	}
}
