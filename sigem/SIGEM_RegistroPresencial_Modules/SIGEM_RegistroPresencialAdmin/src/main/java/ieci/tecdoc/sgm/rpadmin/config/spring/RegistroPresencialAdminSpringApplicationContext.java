package ieci.tecdoc.sgm.rpadmin.config.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Clase que carga el contexto de la aplicación
 *
 * @author BLIMEA
 *
 */
public class RegistroPresencialAdminSpringApplicationContext {

	private static final String DEFAULT_CONFIG_FILE 	= "RegistroPresencialAdminContext.xml";

	private static final Logger logger = Logger.getLogger(RegistroPresencialAdminSpringApplicationContext.class);

	private ApplicationContext contenedor;

	protected static RegistroPresencialAdminSpringApplicationContext _instance = null;

	public RegistroPresencialAdminSpringApplicationContext(){
		try {
			// Instanciamos el contenedor de Spring
			contenedor = new ClassPathXmlApplicationContext(DEFAULT_CONFIG_FILE);
		} catch (Throwable e) {
			logger.error("Error cargando propiedades de configuración iniciales.", e);
		}
	}

	/**
	 * Patron singleton
	 */
	public synchronized static RegistroPresencialAdminSpringApplicationContext getInstance() {
		if (_instance == null) {
			_instance = new RegistroPresencialAdminSpringApplicationContext();
		}

		return _instance;
	}

	public ApplicationContext getApplicationContext(){
		return contenedor;
	}

	public void setApplicationContext(ApplicationContext appContext){
		contenedor=appContext;
	}
}
