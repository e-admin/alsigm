package es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiEntityJndiDatasourceHelper implements
		MultiEntityDatasourceHelper {
	
	private final static String NON_MULTIENTITY_DATASOURCE_KEY="NON_MULTIENTITY_KEY";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MultiEntityJndiDatasourceHelper.class);

	private Hashtable<String, DataSource> dataSources = new Hashtable<String, DataSource>();

	protected String jndiBaseName = "";

	public String getJndiBaseName() {
		return jndiBaseName;
	}

	public void setJndiBaseName(String jndiBaseName) {
		this.jndiBaseName = jndiBaseName;
	}

	public DataSource getDatasource() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDatasource() - start");
		}

		DataSource result = null;

		String currentEntity = MultiEntityContextHolder.getEntity();
		String datasourceKey=currentEntity;

		//si está sin setear la multientidad se usa una clave ficticia para que no rompa el put de la hashtable
		if (StringUtils.isEmpty(currentEntity)){
			datasourceKey=NON_MULTIENTITY_DATASOURCE_KEY;
		}
		
		result = (DataSource) dataSources.get(datasourceKey); 
		
		//si no se obtiene de la cache de datasources se genera uno y se introduce en la cache
		if (result == null) {
			result = getJndiDatasourceFromInitialContext(currentEntity);
			putDatasource(datasourceKey, result);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDatasource() - end");
		}
		return result;

	}

	private synchronized void putDatasource(String key, DataSource dataSource) {
		if (dataSource != null) {
			this.dataSources.put(key, dataSource);
		}
	}

	protected DataSource getJndiDatasourceFromInitialContext(String entity) {
		DataSource result = null;
		InitialContext ctx;
		String jndiName = getJndiName(entity);
		try {
			ctx = new InitialContext();
			result = (DataSource) ctx.lookup(jndiName);

		} catch (NamingException e) {
			String message = "No se ha podido obtener el datasource jndi"
					+ jndiName;
			logger.error(message);
			throw new RuntimeException(message, e);
		}

		return result;
	}


	public String getJndiName(){
		return getJndiName(MultiEntityContextHolder.getEntity());
	}

	protected String getJndiName(String entity) {
		if (StringUtils.isNotBlank(entity)){
			return jndiBaseName + "_" + entity;
		}
		return jndiBaseName;
	}
}
