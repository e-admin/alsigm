package ieci.tecdoc.sgm.registropresencial.config.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Clase que carga el contexto de la aplicación
 *
 * @author BLIMEA
 *
 */
public class RegistroPresencialSpringApplicationContext {

	private static final String DEFAULT_CONFIG_FILE 	= "RegistroPresencialContext.xml";

	private static final Logger logger = Logger.getLogger(RegistroPresencialSpringApplicationContext.class);

	private ApplicationContext contenedor;

	protected static RegistroPresencialSpringApplicationContext _instance = null;

	public RegistroPresencialSpringApplicationContext(){
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
	public synchronized static RegistroPresencialSpringApplicationContext getInstance() {
		if (_instance == null) {
			_instance = new RegistroPresencialSpringApplicationContext();
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
