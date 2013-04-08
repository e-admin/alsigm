package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.service.ConsultaTelematicaService;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.ExpedienteVO;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.InteresadoVO;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.Expediente;
import ieci.tecdoc.sgm.core.services.consulta.Interesado;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;

import org.apache.log4j.Logger;

/**
 * Acción para dar de alta un expediente en la Consulta Telemática.
 * 
 */
public class AltaExpedienteAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(AltaExpedienteAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = 
    	Logger.getLogger("CONSULTA_TELEMATICA");

    
    /**
     * Constructor.
     * 
     */
    public AltaExpedienteAction() {
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

        ExpedienteVO expediente = null;
        InteresadoVO interesado = null;
        
        try {
	        
        	// Número de expediente
	        String numExp = rctx.getIdObjeto();
        
	        // Servicio de acceso a la información de la Consulta Telemática
	        ConsultaTelematicaService service = new ConsultaTelematicaService();
	        
	        // Información del expediente
	        expediente = service.getExpediente(numExp);
	        if (expediente != null) {
	        	expediente.setCaportacion(attContext.getAttribute("APORTACION"));
	        	expediente.setCcodpres(attContext.getAttribute("CODPRES"));
	        	expediente.setCinfoaux("");
	        } else {
	        	throw new ActionException("No se ha encontrado el expediente: " 
	        			+ numExp);
	        }
	        	
        	// Información del interesado principal del expediente
        	interesado = service.getInteresadoExpediente(numExp);
	        
	        // Creación del expediente en la Consulta Telemática
	        creaExpediente(expediente, interesado);

	        // Log del expediente e interesados
	        logOk(expediente, interesado);
	        
        } catch (ActionException e) {
        	setInfo("Error en el alta de expediente: " + e.toString());
        	logError(expediente, interesado, e);
        	throw e;
        } catch (Throwable e) {
        	setInfo("Error en el alta de expediente: " + e.toString());
        	logError(expediente, interesado, e);
            throw new ActionException(e);
        }
        
        return true;
    }

    /**
     * Muestra un log del expediente e interesado principal.
     * @param expediente Datos del expediente.
     * @param interesado Datos del interesado principal.
     */
    private static void logOk(ExpedienteVO expediente, 
    		InteresadoVO interesado) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
	        // Información completa del expediente
	        StringBuffer expInfo = new StringBuffer();
	    
	        // Añadir información del expediente
	        expInfo.append("- EXPEDIENTE:\n");
	        expInfo.append("\t").append(expediente).append("\n");
	        
	        // Añadir información de los interesados
	        expInfo.append("- INTERESADO:\n");
        	expInfo.append("\t").append(interesado);
	        
	        // Log del resultado de la acción
	        CONSULTA_TELEMATICA.info("Alta de expediente:\n" 
	        		+ expInfo.toString());
    	}
    }

    /**
     * Muestra un log de error del expediente e interesado principal.
     * @param expediente Datos del expediente.
     * @param interesado Datos del interesado principal.
     * @param e Excepción capturada.
     */
    private static void logError(ExpedienteVO expediente, 
    		InteresadoVO interesado, Throwable e) {
    	
        // Información completa del expediente
        StringBuffer expInfo = new StringBuffer();
    
        // Añadir información del expediente
        expInfo.append("- EXPEDIENTE:\n");
        expInfo.append("\t").append(expediente).append("\n");
        
        // Añadir información de los interesados
        expInfo.append("- INTERESADO:\n");
    	expInfo.append("\t").append(interesado);
        
        // Log del error
        CONSULTA_TELEMATICA.error("Error en la acción " 
        		+ AltaExpedienteAction.class.getName() + ":\n" 
        		+ expInfo.toString(), e);
    }

    /**
     * Crea un nuevo expediente en la Consulta Telemática.
     * @param expediente Datos del expediente.
     * @param interesado Datos del interesado principal.
     * @throws Exception si ocurre algún error.
     */
    private void creaExpediente(ExpedienteVO expediente, 
    		InteresadoVO interesado) throws Exception {

    	// Información del expediente
    	Expediente ctexp = getCTExpediente(expediente);
    	
    	// Información del interesado principal
    	Interesado ctinteresado = getCTInteresado(interesado);
		
		// Llamada al API de Consulta Telemática
		ServicioConsultaExpedientes consulta = 
			LocalizadorServicios.getServicioConsultaExpedientes();
		consulta.nuevoExpediente(ctexp, ctinteresado, getEntidad());
    }
    
    private static Expediente getCTExpediente(ExpedienteVO exp) {
    	Expediente ctexp = null;
    	
    	if (exp != null) {
    		ctexp = new Expediente();
        	ctexp.setNumero(exp.getCnum());
    		ctexp.setProcedimiento(exp.getCproc());
    		ctexp.setFechaInicio(exp.getCfhinicio());
    		ctexp.setNumeroRegistro(exp.getCnumregini());
    		ctexp.setFechaRegistro(exp.getCfhregini());
    		ctexp.setInformacionAuxiliar(
    				StringUtils.nullToEmpty(exp.getCinfoaux()));
    		ctexp.setAportacion(exp.getCaportacion());
    		ctexp.setCodigoPresentacion(StringUtils.defaultString(
    				exp.getCcodpres(), "N"));
    		ctexp.setEstado(Expediente.COD_ESTADO_EXPEDIENTE_INICIADO);
    		//ctexp.setNotificacion();
    	}
    	
    	return ctexp;
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