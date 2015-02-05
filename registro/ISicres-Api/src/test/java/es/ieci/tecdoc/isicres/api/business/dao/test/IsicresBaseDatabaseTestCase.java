package es.ieci.tecdoc.isicres.api.business.dao.test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;;

public class IsicresBaseDatabaseTestCase extends TestCase {
	
	protected static final Logger logger = Logger.getLogger(IsicresBaseDatabaseTestCase.class);
	
	
	protected void setUp() throws Exception {
		
		MultiEntityContextHolder.setEntity(ISicresKeys.IS_INVESICRES);
		super.setUp();
		configureJndi();
		
	}
	
	/**
	 *  Metodo que configura los recursos jndir a traves del fichero de spring jndi.xml que debe econtrarse en el classpath
	 *  Ejemplo de contenido del fichero
	 *  
	 *  <beans>
	
		<bean id="jndi" class="org.apache.xbean.spring.jndi.DefaultContext">
			<property name="entries">
				<map>
				
					
					<entry key="java:comp/env/ISicres">
						<bean class="org.springframework.jdbc.datasource.DriverManagerDataSource"
							destroy-method="close" singleton="false">
							<property name="driverClassName" value="org.postgresql.Driver" />
							<property name="url" value="jdbc:postgresql://10.228.20.98:5432:5432/registro_000" />
							<property name="username" value="postgres" />
							<property name="password" value="postgres" />
							
						</bean>
						
					</entry>
									
				</map>
			</property>
		</bean>
	</beans>
	*
	*
	*
	* 
	*/
	protected void configureJndi() {
		try {
			// Create initial context
	
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.xbean.spring.jndi.SpringInitialContextFactory");
	
			// -- Loads initial context - and data found in jndi.xml
	
			new InitialContext();
	
		} catch (NamingException ex)
	
		{
			logger.error("Error configurando jndi para los test:"+ ex.getLocalizedMessage());
			
		}
	
	}
	
	

}
