package ieci.tdw.ispac.ispacpublicador.business.scheduler;

import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacpublicador.business.engine.PublisherEngine;
import ieci.tdw.ispac.ispacweb.scheduler.SchedulerTask;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.tram.helpers.EntidadHelper;

import java.util.List;

import org.apache.log4j.Logger;


/**
 * Tarea para lanzar la gestión de hitos de SIGEM.
 * 
 */
public class SigemPublisherSchedulerTask extends SchedulerTask {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(PublisherSchedulerTask.class);

    
    /**
     * Ejecuta la tarea del scheduler.
     */
    public void run() {
    	
        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando MilestoneScheduler (SIGEM)");
        }
        
        // Obtener la lista de entidades
        List entidades = AdministracionHelper.obtenerListaEntidades();
        if (logger.isInfoEnabled()) {
            logger.info("Se han encontrado " + (entidades != null ? entidades.size() : 0) + " entidades");
        }
        
        if (!CollectionUtils.isEmpty(entidades)) {
        	for (int i = 0; i < entidades.size(); i++) {
        		
        		// Información de la entidad
        		Entidad entidad = (Entidad) entidades.get(i);
        		if (entidad != null) {

                    if (logger.isInfoEnabled()) {
                        logger.info("Inicio de proceso de entidad #" + (i+1) + ": " 
                        		+ entidad.getIdentificador() + " - " + entidad.getNombre());
                    }

        			// Establecer la entidad en el thread local
    				EntidadHelper.setEntidad(entidad);
        			
        			// Lanzar el publicador
        			PublisherEngine.execute();

                    if (logger.isInfoEnabled()) {
                        logger.info("Fin de proceso de entidad #" + (i+1) + ": " 
                        		+ entidad.getIdentificador() + " - " + entidad.getNombre());
                    }
        		}
        	}
        } else {
        	
        	// Lanzar el publicador
        	PublisherEngine.execute();
        }
    }
}