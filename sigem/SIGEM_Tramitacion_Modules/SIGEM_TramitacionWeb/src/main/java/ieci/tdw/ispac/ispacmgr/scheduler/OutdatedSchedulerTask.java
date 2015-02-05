package ieci.tdw.ispac.ispacmgr.scheduler;

import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacweb.session.ISPACScheduler;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.tram.helpers.EntidadHelper;

import java.util.List;

import org.apache.log4j.Logger;

public class OutdatedSchedulerTask extends ISPACScheduler {
	
	/** 
	 * Logger de la clase. 
	 */
    private static Logger logger = Logger.getLogger(OutdatedSchedulerTask.class);

    
    /**
     * Ejecuta la tarea del scheduler.
     */
	public void run() {

        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando OutdatedSchedulerTask (SIGEM)");
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
		
        ClientContext ctx = new ClientContext();
        ctx.setAPI(new InvesflowAPI(ctx));

        runAll(ctx);
	}
}