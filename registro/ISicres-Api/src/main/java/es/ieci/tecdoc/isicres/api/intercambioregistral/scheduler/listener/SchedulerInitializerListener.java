package es.ieci.tecdoc.isicres.api.intercambioregistral.scheduler.listener;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;

public class SchedulerInitializerListener implements ServletContextListener {

	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger.getLogger(SchedulerInitializerListener.class);
	
	/**
	 * Clave del parámetro del contexto para almacenar la factoría del Scheduler.
	 */
    public static final String SCHEDULER_FACTORY_KEY = "es.ieci.tecdoc.isicres.intercambioregistral.scheduler.StdSchedulerFactory.KEY";
    
    /**
     * Scheduler.
     */
    private Scheduler scheduler = null;


    public void contextInitialized(ServletContextEvent sce) {

        try {
        	
        	// Configurar el scheduler
        	initScheduler(sce.getServletContext());
        	
        } catch (Throwable t) {
        	logger.error("Error al iniciar el Scheduler", t);
        }
    }
    
 
    protected void initScheduler(ServletContext servletContext) throws SchedulerException {
    	try{
	    	logger.info("Iniciando Scheduler...");
	    	ConfigFilePathResolverIsicres configFilePathResolver = ConfigFilePathResolverIsicres.getInstance();
	    	//Obtener el fichero de configuración
	    	String configFile = configFilePathResolver.getISicresQuartzPath();
	        FileInputStream configInputStream = new FileInputStream(new File(configFile));
	        Properties propertiesFile = new Properties();
	        propertiesFile.load(configInputStream);
	        // Iniciar el Scheduler
	        StdSchedulerFactory factory = new StdSchedulerFactory(propertiesFile);
	        scheduler = factory.getScheduler();
	        scheduler.start();
	        
	        logger.info("Scheduler iniciado");
	
	        // Guardar al Quartz Scheduler Factory en el contexto
	        servletContext.setAttribute(SCHEDULER_FACTORY_KEY, factory);
    	}
    	catch (Exception e) {
			logger.error("No se ha podido inicializar el Scheduler de actualización de estados del intercambio registral");
		}
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
