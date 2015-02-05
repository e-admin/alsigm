package ieci.tecdoc.sgm.admsistema.proceso.exportacion;

import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class Exportar implements Runnable {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Exportar.class);

	/**
	 * Parámetros de exportación
	 */
	private Map parameters = null;

	/**
	 * Gestor de exportación de entidades
	 */
	private IProcessManager exportProcess = null;
	
	
	/**
	 * Constructor
	 * @param params Parámetros de exportación
	 */
	public Exportar(Map params) {
		super();
		setParameters(params);
		
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("admon-entidades-context.xml"));
		exportProcess = (IProcessManager) beanFactory.getBean("exportProcess");
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public void run() {

		try {
			
			if (exportProcess != null) {
				exportProcess.execute(parameters);
			} else {
				logger.error("No se ha especificado el gestor de exportación de entidades");
			}
	
		} catch (Throwable t) {
			logger.error("Error en el proceso de exportación", t);
		}
	}

	public static boolean exportar(Map params) {
		
		Thread process = new Thread(new Exportar(params));
		process.start();

	    return true;
	}

}
