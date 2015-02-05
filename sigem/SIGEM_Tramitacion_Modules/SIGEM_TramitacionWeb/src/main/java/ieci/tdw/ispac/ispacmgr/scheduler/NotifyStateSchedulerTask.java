package ieci.tdw.ispac.ispacmgr.scheduler;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.common.constants.NotifyStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.scheduler.SchedulerTask;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.tram.helpers.EntidadHelper;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Tarea para lanzar la gestión que comprueba si el estado de notificación de los documentos
 * en proceso ha cambiado para actualizarlo.
 * 
 */
public class NotifyStateSchedulerTask extends SchedulerTask {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(NotifyStateSchedulerTask.class);
    
    public static final String VALOR_ESTADO_FALLO = String.valueOf(-1);
    public static final String VALOR_ESTADO_CADUCADA = String.valueOf(4);
    public static final String VALOR_ESTADO_RECHAZADA = String.valueOf(5);
    public static final String VALOR_ESTADO_OK = String.valueOf(3);
    public static final String VALOR_ESTADO_NO_USER = String.valueOf(7);

    /**
     * Ejecuta la tarea del scheduler.
     */
    public void run() {
    	
        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando NotifyStateScheduler (SIGEM)");
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
    	
    	ClientContext context = new ClientContext();
    	IInvesflowAPI invesFlowAPI = new InvesflowAPI(context);
    	
    	try {
    		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
    		ServicioNotificaciones servicioNotificaciones = LocalizadorServicios.getServicioNotificaciones();
    		
    		// Obtener los documentos con estado de notificación en proceso
    		String query = " WHERE ESTADONOTIFICACION = '" + NotifyStatesConstants.IN_PROCESS + "'";
    		IItemCollection collection = entitiesAPI.queryEntities(ISPACEntities.DT_ID_DOCUMENTOS, query);
    		while (collection.next()) {
    			
    			IItem document = collection.value();
    			String idNotificacion = document.getString("ID_NOTIFICACION");
    			if (StringUtils.isNotEmpty(idNotificacion)) {
    				
    				// Obtener el estado de la notificaciónc
    				EstadoNotificacion estadoNotificacion = servicioNotificaciones.obtenerEstado(idNotificacion, entidad);
    				if (estadoNotificacion != null) {
    					
    					String estado = estadoNotificacion.getEstado();
    					if (StringUtils.isNotEmpty(estado)) {
    						
    						String newState = null;
    						
    						if (estado.equals(VALOR_ESTADO_OK)) {
    							newState = NotifyStatesConstants.OK;
    							// Si la notificación está finalizada se establece la fecha de notificación
    							document.set("FNOTIFICACION", estadoNotificacion.getFecha().getTime());
    						}
    						else if (estado.equals(VALOR_ESTADO_RECHAZADA)) {
    							newState = NotifyStatesConstants.REJECTED;
    						}
    						else if (estado.equals(VALOR_ESTADO_CADUCADA)) {
    							newState = NotifyStatesConstants.EXPIRED;
    						}
    						else if (estado.equals(VALOR_ESTADO_FALLO) || estado.equals(VALOR_ESTADO_NO_USER)) {
    							newState = NotifyStatesConstants.ERROR;
    						}
    						
    						if (newState != null) {
    						
    							// Actualizar el estado de notificación
    							document.set("ESTADONOTIFICACION", newState);
    							document.store(context);
    						}
    					}
    				}
    			}
    		}
    	}
    	catch (Exception e){
        	logger.error(e.getMessage(), e);
    	}
    }
    
}