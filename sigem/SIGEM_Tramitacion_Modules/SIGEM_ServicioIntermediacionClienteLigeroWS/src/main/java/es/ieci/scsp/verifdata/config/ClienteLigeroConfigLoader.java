package es.ieci.scsp.verifdata.config;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.Properties;
public class ClienteLigeroConfigLoader {

	private static final String CONFIG_SUBDIR="SIGEM_ServicioIntermediacionClienteLigeroWS";
	private static final String CONFIG_FILE = "clienteLigeroServicioIntermediacion.properties";
	
	
	
	public final String CLIENTE_LIGERO_URL 					= "clienteLigero.hibernate.connection.url";
	public final String CLIENTE_LIGERO_USERNAME				= "clienteLigero.hibernate.connection.username";
	public final String CLIENTE_LIGERO_PASSWORD				= "clienteLigero.hibernate.connection.password";
	public final String CLIENTE_LIGERO_DRIVER_CLASS			= "clienteLigero.hibernate.connection.driver_class";
	public final String CLIENTE_LIGERO_HIBERNATE_DIALECT	= "clienteLigero.hibernate.dialect";
	public final String CLIENTE_LIGERO_NOMBRE				= "clienteLigero.hibernate.connection.nombre";
	public final String CLIENTE_LIGERO_PROVIDER_CLASS		= "clienteLigero.hibernate.connection.provider_class";
	public final String CLIENTE_LIGERO_MAX_SIZE				= "clienteLigero.hibernate.c3p0.max_size";
	public final String CLIENTE_LIGERO_MIN_SIZE				= "clienteLigero.hibernate.c3p0.min_size";
	public final String CLIENTE_LIGERO_ACQUIRE_INCREMENT	= "clienteLigero.hibernate.c3p0.acquire_increment";
	public final String CLIENTE_LIGERO_IDLE_TEST_PERIOD		= "clienteLigero.hibernate.c3p0.idle_test_period";
	public final String CLIENTE_LIGERO_MAX_STATEMENTS		= "clienteLigero.hibernate.c3p0.max_statements";
	public final String CLIENTE_LIGERO_TIMEOUT				= "clienteLigero.hibernate.c3p0.timeout";	

	
	
	public final String REQUIRENTE_URL					= "requirente.hibernate.connection.url";
	public final String REQUIRENTE_USERNAME				= "requirente.hibernate.connection.username";
	public final String REQUIRENTE_PASSWORD				= "requirente.hibernate.connection.password";
	public final String REQUIRENTE_DRIVER_CLASS			= "requirente.hibernate.connection.driver_class";
	public final String REQUIRENTE_HIBERNATE_DIALECT	= "requirente.hibernate.dialect";	
	public final String REQUIRENTE_NOMBRE				= "requirente.hibernate.connection.nombre";
	public final String REQUIRENTE_PROVIDER_CLASS		= "requirente.hibernate.connection.provider_class";
	public final String REQUIRENTE_MAX_SIZE				= "requirente.hibernate.c3p0.max_size";
	public final String REQUIRENTE_MIN_SIZE				= "requirente.hibernate.c3p0.min_size";
	public final String REQUIRENTE_ACQUIRE_INCREMENT	= "requirente.hibernate.c3p0.acquire_increment";
	public final String REQUIRENTE_IDLE_TEST_PERIOD		= "requirente.hibernate.c3p0.idle_test_period";
	public final String REQUIRENTE_MAX_STATEMENTS		= "requirente.hibernate.c3p0.max_statements";
	public final String REQUIRENTE_TIMEOUT				= "requirente.hibernate.c3p0.timeout";	

	
	
	protected Properties properties;

	private static ClienteLigeroConfigLoader mInstance = null;
	
	public static synchronized ClienteLigeroConfigLoader getInstance(){

		if (mInstance == null) {
			mInstance = new ClienteLigeroConfigLoader();
		}
		return mInstance;
	}
	
	
	protected ClienteLigeroConfigLoader(){
		properties= loadConfiguration();
	}

	public String getValue(String key){
		String result="";
		result= properties.getProperty(key);
		return result;
	}

	protected Properties loadConfiguration(){

		Properties result=null;
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		
		result= pathResolver.loadProperties(CONFIG_FILE, CONFIG_SUBDIR);

		return result;
	}

	
}
