package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.service.ConsultaTelematicaService;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;

import org.apache.log4j.Logger;

/**
 * Acción para anexar ficheros al hito actual de un expediente 
 * en la Consulta Telemática.
 * 
 */
public class AnexarFicherosHitoActualAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(AnexarFicherosHitoActualAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = 
    	Logger.getLogger("CONSULTA_TELEMATICA");

    
    /**
     * Constructor.
     * 
     */
    public AnexarFicherosHitoActualAction() {
    	super();
    }
    
    /**
     * Ejecuta la acción.
     * @param rctx Contexto de ejecución de la regla
     * @param attContext Atributos con información extra, utilizados dentro de 
     * la ejecución de la regla.
     * @return true si la ejecución termina correctamente, false en caso 
     * contrario.
     * @throws ActionException si ocurre algún error.
     */
    public boolean execute(RuleContext rctx, AttributeContext attContext) 
    		throws ActionException {
    	
        if (logger.isInfoEnabled()) {
            logger.info("Acción [" + this.getClass().getName() 
            		+ "] en ejecución");
        }

    	int idTramite = TypeConverter.parseInt((String) rctx.getProperties().get("id_tramite"), -1);
    	int idFase = TypeConverter.parseInt((String) rctx.getProperties().get("id_fase"), -1);

        ConsultaTelematicaService service = new ConsultaTelematicaService();
        FicherosHito ficheros = null;
        
        try {

			// Llamada al API de Consulta Telemática
			ServicioConsultaExpedientes consulta = 
				LocalizadorServicios.getServicioConsultaExpedientes();

			// Información del hito actual del expediente en CT
	        HitoExpediente hito = consulta.obtenerHitoEstado(rctx.getIdObjeto(), getEntidad());
	        if (hito == null) {
	        	throw new ActionException("No se ha encontrado el hito actual del expediente " 
	        			+ rctx.getIdObjeto());
	        }
	        
	        // Obtener los ficheros asociados al hito en tramitación
	        if (idTramite > 0) {
		        ficheros = service.getFicherosTramite(hito.getGuid(), rctx.getIdObjeto(), idTramite);
	        } else if (idFase > 0) {
		        ficheros = service.getFicherosFase(hito.getGuid(), rctx.getIdObjeto(), idFase);
	        } else {
	        	logger.warn("No se ha encontrado ningún identificador de fase o trámite");
	        }

	        // Anexar los ficheros al hito actual en CT
	        if ((ficheros != null) && ficheros.count() > 0) {
	        	consulta.anexarFicherosHitoActual(ficheros, getEntidad());
	        }

	        // Log del resultado
	        logOk(ficheros);

        } catch (ActionException e) {
        	setInfo(e.getLocalizedMessage());
        	logError(e);
        	
        	try {
	        	// Eliminar los ficheros anexados
	    		service.deleteFicherosHito(ficheros);
        	} catch (Throwable t) {
        		logger.warn("No se han podido eliminar los ficheros en RDE", e);
        	}
        	
            throw e;
        } catch (Throwable e) {
        	setInfo("Error al anexar ficheros al hito actual: " + e.toString());
        	logError(e);
        	
        	try {
	        	// Eliminar los ficheros anexados
	    		service.deleteFicherosHito(ficheros);
        	} catch (Throwable t) {
        		logger.warn("No se han podido eliminar los ficheros en RDE", e);
        	}
        	
            throw new ActionException(e);
        }
        
        return true;
    }
    
    /**
     * Muestra un log del resultado.
     * @param ficheros Ficheros anexados.
     */
    private static void logOk(FicherosHito ficheros) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
	        // Log del resultado de la acción
	        CONSULTA_TELEMATICA.info("Anexión de ficheros al hito actual:\n" 
	        		+ ficheros.count());
    	}
    }

    /**
     * Muestra un log de error.
     * @param e Excepción provocada por el error.
     */
    private static void logError(Throwable e) {
    	
        // Log del error
        CONSULTA_TELEMATICA.error("Error en la acción " 
        		+ AnexarFicherosHitoActualAction.class.getName(), e);
    }
}