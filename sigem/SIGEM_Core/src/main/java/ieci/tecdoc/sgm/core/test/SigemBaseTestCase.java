package ieci.tecdoc.sgm.core.test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public  class SigemBaseTestCase extends TestCase {

	protected static final Logger logger = Logger.getLogger(SigemBaseTestCase.class);
	
	
	
	protected void setUp() throws Exception {
		
		super.setUp();
		configureJndi();
		
	}
	
	/**
	 * Metodo que configura los recursos jndir a traves del fichero de spring jndi.xml que debe econtrarse en el classpath
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

