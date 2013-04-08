package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.service.ConsultaTelematicaService;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.InteresadoVO;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.Interesado;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;

import org.apache.log4j.Logger;

/**
 * Acción para cambiar el interesado principal de un expediente 
 * en la Consulta Telemática.
 * 
 */
public class CambioInteresadoExpedienteAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(CambioInteresadoExpedienteAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = 
    	Logger.getLogger("CONSULTA_TELEMATICA");

    
    /**
     * Constructor.
     * 
     */
    public CambioInteresadoExpedienteAction() {
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
            logger.info("Acción [" + this.getClass().getName() + "] en ejecución");
        }

        InteresadoVO interesado = null;
        
        try {
	        
        	// Número de expediente
	        String numExp = rctx.getIdObjeto();
        
	        // API de acceso a Consulta Telemática
			ServicioConsultaExpedientes consulta = 
				LocalizadorServicios.getServicioConsultaExpedientes();

			// Eliminar los interesados del expediente
			consulta.eliminarInteresadoExpediente(rctx.getIdObjeto(), getEntidad());
			
        	// Información del interesado principal del expediente
			ConsultaTelematicaService service = new ConsultaTelematicaService();
        	interesado = service.getInteresadoExpediente(numExp);
	        if ( (interesado != null)
	        		&& StringUtils.isNotBlank(interesado.getCnif())) {

	        	// Creación del interesado en Consulta Telemática
		        consulta.nuevoInteresado(getCTInteresado(interesado), getEntidad());
	        }
	        
	        // Log del interesado
	        logOk(interesado);
	        
        } catch (ActionException e) {
        	setInfo("Error en el cambio de interesado del expediente: " + e.toString());
        	logError(interesado, e);
        	throw e;
        } catch (Throwable e) {
        	setInfo("Error en el cambio de interesado del expediente: " + e.toString());
        	logError(interesado, e);
            throw new ActionException(e);
        }
        
        return true;
    }

    /**
     * Muestra un log del interesado principal.
     * @param expediente Datos del expediente.
     * @param interesado Datos del interesado principal.
     */
    private static void logOk(InteresadoVO interesado) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
	        // Información completa del expediente
	        StringBuffer info = new StringBuffer();
	    
	        // Añadir información del interesado
	        info.append("- INTERESADO:\n");
        	info.append("\t").append(interesado);
	        
	        // Log del resultado de la acción
	        CONSULTA_TELEMATICA.info("Cambio de interesado del expediente:\n" 
	        		+ info.toString());
    	}
    }

    /**
     * Muestra un log de error del interesado principal.
     * @param interesado Datos del interesado principal.
     * @param e Excepción capturada.
     */
    private static void logError(InteresadoVO interesado, Throwable e) {
    	
        // Información completa
        StringBuffer info = new StringBuffer();
    
        // Añadir información de los interesados
        info.append("- INTERESADO:\n");
    	info.append("\t").append(interesado);
        
        // Log del error
        CONSULTA_TELEMATICA.error("Error en la acción " 
        		+ CambioInteresadoExpedienteAction.class.getName() + ":\n" 
        		+ info.toString(), e);
    }

    private static Interesado getCTInteresado(InteresadoVO interesado) {
    	Interesado ctinteresado = null;
    	
		if (interesado != null) {
			ctinteresado = new Interesado();
			ctinteresado.setNumeroExpediente(interesado.getCnumexp());
			ctinteresado.setNIF(interesado.getCnif());
			ctinteresado.setNombre(interesado.getCnom());
			ctinteresado.setPrincipal("S");
			ctinteresado.setInformacionAuxiliar(
					StringUtils.nullToEmpty(interesado.getCinfoaux()));
			//ctinteresado.setExpedientes();
		}

		return ctinteresado;
    }
}