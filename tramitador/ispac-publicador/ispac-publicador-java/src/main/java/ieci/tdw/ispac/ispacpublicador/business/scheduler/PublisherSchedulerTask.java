package ieci.tdw.ispac.ispacpublicador.business.scheduler;

import ieci.tdw.ispac.ispacpublicador.business.engine.PublisherEngine;
import ieci.tdw.ispac.ispacweb.scheduler.SchedulerTask;

import org.apache.log4j.Logger;


/**
 * Tarea para lanzar la gestión de hitos.
 * 
 */
public class PublisherSchedulerTask extends SchedulerTask {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(PublisherSchedulerTask.class);

    
    /**
     * Ejecuta la tarea del scheduler.
     */
    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando MilestoneScheduler");
        }
        
        PublisherEngine.execute();
    }
}