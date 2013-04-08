package common.util;

import ieci.core.db.DbEngine;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import common.exceptions.ArchivoModelException;
import common.vos.EngineInfoVO;

public class DBEngineUtils {

	private static final Logger logger = Logger.getLogger(DBEngineUtils.class);

	public static EngineInfoVO getEngineInfo(Connection conn)
			throws ArchivoModelException {
		return getEngineInfo(conn, null);
	}

	public static EngineInfoVO getEngineInfo(Connection conn,
			String dbFactoryClass) throws ArchivoModelException {

		EngineInfoVO engineInfoVO = new EngineInfoVO();

		String databaseProductName;
		try {
			databaseProductName = conn.getMetaData().getDatabaseProductName();
			logger.debug("DatabaseProductName: " + databaseProductName);
		} catch (SQLException e) {
			logger.error("Error al obtener el dataBaseProductName", e);

			throw new ArchivoModelException(DBEngineUtils.class,
					"getEngineInfo", e.getMessage());
		}

		engineInfoVO.setDatabaseProductName(databaseProductName);

		if (databaseProductName.toLowerCase().indexOf(
				DbEngine.oracleDatabaseName.toLowerCase()) != -1) {
			engineInfoVO.setIdEngine(DbEngine.ORACLE);

			if (StringUtils.isNotEmpty(dbFactoryClass)) {
				engineInfoVO.setDbFactoryClass(dbFactoryClass);
			} else {
				engineInfoVO
						.setDbFactoryClass(DBFactoryConstants.ORACLE9_FACTORY);
			}

		} else if (databaseProductName.toLowerCase().indexOf(
				DbEngine.sqlserverDatabaseName.toLowerCase()) != -1) {
			engineInfoVO.setIdEngine(DbEngine.SQLSERVER);
			engineInfoVO
					.setDbFactoryClass(DBFactoryConstants.SQLSERVER_FACTORY);

		} else if (databaseProductName.toLowerCase().indexOf(
				DbEngine.postgresSQlDatabaseName.toLowerCase()) != -1) {

			engineInfoVO.setIdEngine(DbEngine.POSTGRES);
			engineInfoVO
					.setDbFactoryClass(DBFactoryConstants.POSTGRESQL_FACTORY);

		} else if (databaseProductName.toLowerCase().indexOf(
				DbEngine.db2DatabaseName.toLowerCase()) != -1) {

			engineInfoVO.setIdEngine(DbEngine.DB2);
			engineInfoVO.setDbFactoryClass(DBFactoryConstants.DB2_FACTORY);
			engineInfoVO.setAllowHint(false);
		} else {
			throw new ArchivoModelException(DBEngineUtils.class,
					"getEngineInfo",
					"Base de datos no soportada por la aplicacion");
		}

		if (dbFactoryClass != null) {
			logger.debug("dbFactoryClass Configurada: " + dbFactoryClass);
		}
		logger.debug("dbFactoryClass Establecida: "
				+ engineInfoVO.getDbFactoryClass());

		return engineInfoVO;

	}

}
