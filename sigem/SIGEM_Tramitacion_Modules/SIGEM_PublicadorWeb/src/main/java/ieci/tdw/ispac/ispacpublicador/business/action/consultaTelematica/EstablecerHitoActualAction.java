package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import java.util.Date;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.service.ConsultaTelematicaService;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;

import org.apache.log4j.Logger;

/**
 * Acción para dar de alta un hito del estado de un expediente 
 * en la Consulta Telemática.
 * 
 */
public class EstablecerHitoActualAction extends SigemBaseAction {

	//========================================================================
	// Constantes de los nombres de los parámetros de la regla
	//========================================================================
	private static final String CODIGO 				= "CODIGO";
	private static final String DESCRIPCION 		= "DESCRIPCION";
	private static final String PASO_A_HISTORICO 	= "PASO_A_HISTORICO";
	private static final String ENVIAR_DOCUMENTOS 	= "ENVIAR_DOCUMENTOS";
	//========================================================================
	
	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(EstablecerHitoActualAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = 
    	Logger.getLogger("CONSULTA_TELEMATICA");

    
    /**
     * Constructor.
     * 
     */
    public EstablecerHitoActualAction() {
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

        HitoExpediente hito = new HitoExpediente();
        
        try {

        	// Establecer el hito actual
        	hito = establecerHitoActual(rctx, attContext);

	        // Comprobar si hay que enviar los documentos asociados al hito
	        if ("S".equalsIgnoreCase(
	        		attContext.getAttribute(ENVIAR_DOCUMENTOS))) {
	        	anexarFicheros(rctx, hito);
	        }

	        // Log del hito
	        logOk(hito);

        } catch (Throwable e) {
        	setInfo("Error al establecer hito actual: " + e.toString());
        	logError(hito, e);
            throw new ActionException(e);
        }
        
        return true;
    }
    
    private HitoExpediente establecerHitoActual(RuleContext rctx, 
    		AttributeContext attContext) throws SigemException {
    	
    	// Información del hito
    	HitoExpediente hito = new HitoExpediente();
		hito.setGuid(new Guid().toString());
		hito.setNumeroExpediente(rctx.getIdObjeto());
		hito.setCodigo(StringUtils.nullToEmpty(attContext.getAttribute(CODIGO)));
		hito.setFecha(TypeConverter.toString(new Date(), ConstantesServicios.DATE_PATTERN));
		hito.setDescripcion(attContext.getAttribute(DESCRIPCION));
		hito.setInformacionAuxiliar("");
		
		// Paso a histórico del hito anterior
        boolean pasoAHistorico = "S".equalsIgnoreCase(
        		attContext.getAttribute(PASO_A_HISTORICO));

        // Establece el hito actual en Consulta Telemática
		ServicioConsultaExpedientes consulta = 
			LocalizadorServicios.getServicioConsultaExpedientes();
		consulta.establecerHitoActual(hito, new FicherosHito(), pasoAHistorico, getEntidad());
		
		// Devolver la información del hito creado
		return consulta.obtenerHitoEstado(rctx.getIdObjeto(), getEntidad());
    }
    
    private void anexarFicheros(RuleContext rctx, HitoExpediente hito) 
    		throws Exception {
    	
        ConsultaTelematicaService service = new ConsultaTelematicaService();
        FicherosHito ficheros = null;

        // Obtener el identificador del trámite
    	int idTramite = TypeConverter.parseInt((String) rctx.getProperties().get("id_tramite"), -1);
    	int idFase = TypeConverter.parseInt((String) rctx.getProperties().get("id_fase"), -1);

        try {

			// Llamada al API de Consulta Telemática
			ServicioConsultaExpedientes consulta = 
				LocalizadorServicios.getServicioConsultaExpedientes();

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

        } catch (Exception e) {
        	logger.error("Error al anexar ficheros al hito: " 
        			+ hito.getGuid(), e);
        	
        	try {
	        	// Eliminar los ficheros anexados
	    		service.deleteFicherosHito(ficheros);
        	} catch (Throwable t) {
        		logger.warn("No se han podido eliminar los ficheros en RDE", e);
        	}
        	
            throw e;
        }
    }
    
    /**
     * Muestra un log del hito.
     * @param hito Datos del hito.
     */
    private static void logOk(HitoExpediente hito) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
	        // Información del hito
	        StringBuffer hitoInfo = new StringBuffer()
	        	.append("- HITO ESTADO: ")
	        	.append(toString(hito));
	        
	        // Log del resultado de la acción
	        CONSULTA_TELEMATICA.info("Alta de hito actual:\n" 
	        		+ hitoInfo.toString());
    	}
    }

    /**
     * Muestra un log de error.
     * @param hito Datos del hito.
     */
    private static void logError(HitoExpediente hito, Throwable e) {
    	
        // Información del hito
        StringBuffer hitoInfo = new StringBuffer()
        	.append("- HITO ESTADO: ")
        	.append(toString(hito));
        
        // Log del error
        CONSULTA_TELEMATICA.error("Error en la acción " 
        		+ EstablecerHitoActualAction.class.getName() + ":\n" 
        		+ hitoInfo.toString(), e);
    }
    
    /**
     * Obtiene una cadena con la información del hito.
     * @param hito Información del hito.
     * @return Cadena con la información del hito.
     */
    private static String toString(HitoExpediente hito) {
    	if (hito != null) {
	    	return new StringBuffer()
	    		.append("guid=[").append(hito.getGuid()).append("]")
	    		.append(", numeroExpediente=[").append(hito.getNumeroExpediente())
	    		.append("]")
	    		.append(", codigo=[").append(hito.getCodigo()).append("]")
	    		.append(", fecha=[").append(hito.getFecha()).append("]")
	    		.append(", descripcion=[").append(hito.getDescripcion()).append("]")
	    		.append(", informacionAuxiliar=[")
	    		.append(hito.getInformacionAuxiliar()).append("]")
	    		.toString();
    	} else {
    		return null;
    	}
    }
}