package es.ieci.tecdoc.fwktd.dm.bd.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityJndiDatasource;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DataSourceFactory;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

/**
 * Factoría para obtener el origen de datos a partir de la configuración de un
 * tipo de contenido.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DataSourceFactoryImpl implements DataSourceFactory {

    protected static final String JDBC_DATASOURCE = "JDBC_DATASOURCE";
    protected static final String JDBC_DRIVER_CLASS_NAME = "JDBC_DRIVER_CLASS_NAME";
    protected static final String JDBC_URL = "JDBC_URL";
    protected static final String JDBC_USERNAME = "JDBC_USERNAME";
    protected static final String JDBC_PASSWORD = "JDBC_PASSWORD";

    private Map<ContentType, DataSource> dataSourcesCache = new HashMap<ContentType, DataSource>();


    /**
     * Constructor.
     */
	public DataSourceFactoryImpl() {}

	/**
	 * Obtiene el origen de datos a partir de la configuración del
	 * tipo de contenido.
	 * @param contentType Tipo de contenido.
	 * @return Origen de datos.
	 */
	public DataSource getDataSource(ContentType contentType) {

		DataSource dataSource = null;

    	if (contentType != null) {

    		// Obtener el dataSource de la caché
    		dataSource = dataSourcesCache.get(contentType);

    		if (dataSource == null) {

	    		// Configuración del origen de datos
	    		String dsName = contentType.getProperty(JDBC_DATASOURCE);
	    		if (StringUtils.isNotBlank(dsName)) {
	    			dataSource = new MultiEntityJndiDatasource();
	    			((MultiEntityJndiDatasource)dataSource).setJndiBaseName(dsName);
	    		} else {
	    			String jdbcUrl = contentType.getProperty(JDBC_URL);
	    			if (StringUtils.isNotBlank(jdbcUrl)) {
	    				dataSource = new DriverManagerDataSource();
	    				((DriverManagerDataSource)dataSource).setDriverClassName(contentType.getProperty(JDBC_DRIVER_CLASS_NAME));
	    				((DriverManagerDataSource)dataSource).setUrl(jdbcUrl);
	    				((DriverManagerDataSource)dataSource).setUsername(contentType.getProperty(JDBC_USERNAME));
	    				((DriverManagerDataSource)dataSource).setPassword(contentType.getProperty(JDBC_PASSWORD));
	    			}
	    		}

	    		// Guardar el dataSource en la caché
	    		dataSourcesCache.put(contentType, dataSource);
    		}
    	}

    	return dataSource;
	}
}
