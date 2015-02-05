package es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Iecisa
 * @version $Revision$
 *
 *          Clase para el uso de datasources multiEntidad
 *
 *          Configuracion de Spring <bean id="dataSource" class="es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityJndiDatasource"
 *          > <property name="jndiBaseName">
 *          <value>java:comp/env/jdbc/person</value> </property> </bean>
 *
 *
 *
 */
public class MultiEntityJndiDatasource extends MultiEntityDataSource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MultiEntityJndiDatasource.class);

	protected String jndiBaseName;

	public MultiEntityJndiDatasource() {
		super();
		this.multiEntityDatasourceHelper = new MultiEntityJndiDatasourceHelper();
	}

	public String getJndiBaseName() {
		return jndiBaseName;
	}

	public void setJndiBaseName(String jndiBaseName) {
		this.jndiBaseName = jndiBaseName;
		((MultiEntityJndiDatasourceHelper) this.multiEntityDatasourceHelper).jndiBaseName = jndiBaseName;
	}

	public String getJndiName() {
		return multiEntityDatasourceHelper.getJndiName();
	}
}
