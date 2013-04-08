package common.db;

import java.util.HashMap;
import java.util.Map;

import ieci.core.db.DbEngine;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import common.exceptions.ArchivoModelException;
import common.session.model.GestionSessionImpl;

/**
 * Clase para almacenar el datasource de la aplicación y el engine
 */
public class DataSourceEngine {

	private static Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
	private static Map<String, DbEngine> engines = new HashMap<String, DbEngine>();

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(DataSourceEngine.class);

	/**
	 * Permite obtener el datasource de la aplicación
	 *
	 * @param dataSourceName
	 *            nombre del datasource de la aplicación
	 * @return datasource de la aplicación
	 */
	public static DataSource getDataSource(String dataSourceName,
			String dbFactoryClass) {

		DataSource dataSource = dataSources.get(dataSourceName);

		if (dataSource == null) {
			try {
				// Buscar el datasource en el contexto
				if (dataSourceName != null) {
					Context context = new InitialContext();
					if (context != null) {
						dataSource = (DataSource) context
								.lookup(dataSourceName);

						logger.info("Creación de datasource " + dataSourceName);

						dataSources.put(dataSourceName, dataSource);
						engines.put(dataSourceName, new DbEngine(dataSource,
								dbFactoryClass));
					}
				}
			} catch (NamingException e) {
				throw new ArchivoModelException(DbEngine.class,
						"createWithEntity", e.getMessage());
			} catch (ArchivoModelException e) {
				throw e;
			}
		}

		return dataSource;
	}

	/**
	 * Permite obtener la engine de base de datos de la aplicación
	 *
	 * @param dataSourceName
	 *            Nombre del datasource de la aplicación
	 * @return engine de la aplicación
	 */
	public static DbEngine getDbEngine(String dataSourceName,
			String dbFactoryClass) {

		DataSource dataSource = dataSources.get(dataSourceName);
		DbEngine engine = engines.get(dataSourceName);

		if (dataSource == null) {
			getDataSource(dataSourceName, dbFactoryClass);
			engine = engines.get(dataSourceName);
		}

		return engine;
	}
}
