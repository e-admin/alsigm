package es.ieci.tecdoc.fwktd.test.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase para cargar el contexto JNDI en los test unitarios que son simulados
 * mediante spring con ayuda del artefacto <code>xbean-spring</code> se
 * configura los recursos jndi a traves del fichero de spring jndi.xml que debe
 * econtrarse en el classpath.
 *
 * Ejemplo:
 * <beans>
 *   <bean id="jndi" class="org.apache.xbean.spring.jndi.DefaultContext">
 *     <property name="entries">
 *       <map>
 *         <entry key="java:comp/env/jdbc/person_01">
 *           <bean class="org.springframework.jdbc.datasource.DriverManagerDataSource"
 *             destroy-method="close" singleton="false">
 *             <property name="driverClassName" value="org.postgresql.Driver" />
 *             <property name="url" value="jdbc:postgresql://localhost:5432/person_01" />
 *             <property name="username" value="postgres" />
 *             <property name="password" value="postgres" />
 *           </bean>
 *         </entry>
 *       </map>
 *     </property>
 *   </bean>
 * </beans>
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class JndiContextLoader {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(JndiContextLoader.class);

	/**
	 * Constructor.
	 */
	public JndiContextLoader() {
		configureJndi();
	}

	/**
	 * Configura el contexto JNDI.
	 */
	protected void configureJndi() {
		try {
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.xbean.spring.jndi.SpringInitialContextFactory");
			new InitialContext();
		} catch (NamingException ex) {
			logger.error("Error configurando jndi para los test", ex);
		}
	}
}

