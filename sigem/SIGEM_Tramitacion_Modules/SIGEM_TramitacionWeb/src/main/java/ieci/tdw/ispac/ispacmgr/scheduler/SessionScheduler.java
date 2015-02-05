package ieci.tdw.ispac.ispacmgr.scheduler;

import java.util.List;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.session.SessionMgr;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacweb.scheduler.SchedulerTask;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.tram.helpers.EntidadHelper;


import org.apache.log4j.Logger;

public class SessionScheduler extends SchedulerTask {
	
	/** 
	 * Logger de la clase. 
	 */
    private static Logger logger = Logger.getLogger(SessionScheduler.class);

    
    /**
     * Ejecuta la tarea del scheduler.
     */
	public void run() {

        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando SessionScheduler (SIGEM)");
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
        			
    				// Comprobar el estado de notificación
    				execute(entidad);

                    if (logger.isInfoEnabled()) {
                        logger.info("Fin de proceso de entidad #" + (i+1) + ": " 
                        		+ entidad.getIdentificador() + " - " + entidad.getNombre());
                    }
        		}
        	}
        }

	}

	public void execute(Entidad entidad) {
		
        try {
			SessionMgr mgr = new SessionMgr();
			mgr.deleteExpiredSessions();
		} catch (ISPACException e) {
			logger.error("Error al eliminar las sesiones " + e);
		}
	}
}