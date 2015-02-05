package ieci.tecdoc.sgm.scheduler.listener;

import java.io.File;

import ieci.tecdoc.sgm.scheduler.config.SchedulerConfigFilePathResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerInitializerListener implements ServletContextListener {

	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger.getLogger(SchedulerInitializerListener.class);
	
	/**
	 * Clave del parámetro del contexto para almacenar la factoría del Scheduler.
	 */
    public static final String SCHEDULER_FACTORY_KEY = "ieci.tecdoc.sgm.scheduler.StdSchedulerFactory.KEY";

    /**
     * Scheduler.
     */
    private Scheduler scheduler = null;


    public void contextInitialized(ServletContextEvent sce) {

        try {

        	// Configurar los logs
        	configureLogging();
        	
        	// Configurar el scheduler
        	initScheduler(sce.getServletContext());
        	
        } catch (Throwable t) {
        	logger.error("Error al iniciar el Scheduler", t);
        }
    }
    
    protected void configureLogging() {

    	// Obtener la ruta del fichero de configuración de logs.
    	String file = SchedulerConfigFilePathResolver.getConfigFilePath("log4j.xml");

    	// Inicializar la configuración de logs
    	if (file != null) {

			if (new File(file).exists()) {
				
				String extension = FilenameUtils.getExtension(file);
				
				if ("properties".equalsIgnoreCase(extension)){
					PropertyConfigurator.configure(file);
				}

				if ("xml".equalsIgnoreCase(extension)) {
					DOMConfigurator.configure(file);
				}

				logger = Logger.getLogger(SchedulerInitializerListener.class); 
				logger.info("Log4j cargado [" + file + "]");
			} else {
				logger.info("Log4j no encontrado [" + file + "]");
			}
    	}
    }
    
    protected void initScheduler(ServletContext servletContext) throws SchedulerException {
    	
    	logger.info("Iniciando Scheduler...");
    	
    	// Obtener el fichero de configuración
    	String configFile = SchedulerConfigFilePathResolver.getConfigFilePath("quartz.properties");
        
        // Iniciar el Scheduler
        StdSchedulerFactory factory = new StdSchedulerFactory(configFile);
        scheduler = factory.getScheduler();
        scheduler.start();
        
        logger.info("Scheduler iniciado");

        // Guardar al Quartz Scheduler Factory en el contexto
        servletContext.setAttribute(SCHEDULER_FACTORY_KEY, factory);

    }

    public void contextDestroyed(ServletContextEvent sce) {

        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
            
            logger.info("Scheduler detenido correctamente.");
            
        } catch (Exception e) {
        	logger.error("Error al detener el Scheduler: ", e);
        }
    }

}
