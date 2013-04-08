package es.ieci.tecdoc.fwktd.core.test.spring.configuration.jdbc.datasource;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iecisa
 * 
 *         Clase abstracta para el uso de test unitarios basados en datasources
 *         jndis que son simulados mediante spring con ayuda del artefacto
 *         <code>xbean-spring</code> se configura los recursos jndi a traves del
 *         fichero de spring jndi.xml que debe econtrarse en el classpath
 * 
 *         Ejemplo: <beans>
 * 
 *         <bean id="jndi" class="org.apache.xbean.spring.jndi.DefaultContext">
 *         <property name="entries"> <map> <entry
 *         key="java:comp/env/jdbc/person_01"> <bean
 *         class="org.springframework.jdbc.datasource.DriverManagerDataSource"
 *         destroy-method="close" singleton="false"> <property
 *         name="driverClassName" value="org.postgresql.Driver" /> <property
 *         name="url" value="jdbc:postgresql://localhost:5432/person_01" />
 *         <property name="username" value="postgres" /> <property
 *         name="password" value="postgres" />
 * 
 *         </bean> </entry>
 * 
 * 
 *         </map> </property> </bean> </beans>
 * 
 * 
 */
@RunWith(JUnit4.class)
public abstract class JndiSpringDatasourceTestCase {

	protected static final Logger logger = LoggerFactory
			.getLogger(JndiSpringDatasourceTestCase.class);

	protected InitialContext initialContext;

	@Before
	protected void setUp() throws Exception {

		configureJndi();

	}

	/**
	 * Metodo que configura los recursos jndir a traves del fichero de spring
	 * jndi.xml que debe econtrarse en el classpath
	 */
	protected void configureJndi() {
		try {
			// Create initial context

			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.xbean.spring.jndi.SpringInitialContextFactory");

			// -- Loads initial context - and data found in jndi.xml

			initialContext = new InitialContext();

		} catch (NamingException ex)

		{
			logger.error("Error configurando jndi para los test:"
					+ ex.getLocalizedMessage());

		}

	}

}
