package common.util;

/**
 * Constantes de las factorias de base de datos soportadas.
 * 
 * @author IECISA
 * 
 */
public class DBFactoryConstants {

	/**
	 * Factoría por defecto
	 */
	public static final String DEFAULT_FACTORY = "common.db.DbEntityFactoryBase";

	/**
	 * Factoría DB2
	 */
	public static final String DB2_FACTORY = "common.db.DBEntityFactoryDB2";

	/**
	 * Factoría Oracle 8i
	 */
	public static final String ORACLE8_FACTORY = "common.db.DBEntityFactoryOracle8i";

	/**
	 * Factoría Oracle 9i o posterior
	 */
	public static final String ORACLE9_FACTORY = "common.db.DBEntityFactoryOracle9i";

	/**
	 * Factoría PostgreSQL
	 */
	public static final String POSTGRESQL_FACTORY = "common.db.DBEntityFactoryPostgreSQL";

	/**
	 * Factoría SQLServer
	 */
	public static final String SQLSERVER_FACTORY = "common.db.DBEntityFactorySqlServer2000";
}
