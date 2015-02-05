package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.consulta.Expediente;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.ct.GestorConsulta;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import org.apache.log4j.Logger;

/**
 * Acción para cerrar un expediente en la Consulta Telemática.
 * 
 */
public class CerrarExpedienteAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = Logger.getLogger(CerrarExpedienteAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = Logger.getLogger("CONSULTA_TELEMATICA");

    
    /**
     * Constructor.
     * 
     */
    public CerrarExpedienteAction() {
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

    	// Número de expediente
        String numExp = rctx.getIdObjeto();

        try {
	        
	        // Cierre del expediente en la Consulta Telemática
//			ServicioConsultaExpedientes consulta = 
//				LocalizadorServicios.getServicioConsultaExpedientes();
//			consulta.cerrarExpediente(numExp, getEntidad());
	        cerrarExpediente(numExp, getEntidad());

	        // Log del expediente
	        logOk(numExp);
	        
//        } catch (ActionException e) {
//        	setInfo("Error en el alta de expediente: " + e.toString());
//        	logError(numExp, e);
//        	throw e;
        } catch (Throwable e) {
        	setInfo("Error en el alta de expediente: " + e.toString());
        	logError(numExp, e);
            throw new ActionException(e);
        }
        
        return true;
    }

    /**
     * Muestra un log del expediente.
     * @param numExp Número del expediente.
     */
    private static void logOk(String numExp) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
	        // Log del resultado de la acción
	        CONSULTA_TELEMATICA.info("Cierre de expediente: " + numExp);
    	}
    }

    /**
     * Muestra un log de error del expediente.
     * @param numExp Número del expediente.
     * @param e Excepción capturada.
     */
    private static void logError(String numExp, Throwable e) {
    	
        // Log del error
        CONSULTA_TELEMATICA.error("Error en la acción " 
        		+ CerrarExpedienteAction.class.getName() + ": " 
        		+ numExp, e);
    }

    
    /*
     * ========================================================================
     * Métodos a incorporar en el servicio de consulta de expedientes
     * en futuras versiones.
     * ========================================================================
     */
    
	public void cerrarExpediente(String numeroExpediente, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.actualizarEstado(numeroExpediente, 
					Expediente.COD_ESTADO_EXPEDIENTE_FINALIZADO, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error cerrando expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e) {
			logger.error("Error cerrando expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private ConsultaExpedientesException getConsultaExpedientesException(
			ConsultaExcepcion poException) {
		if (poException == null) {
			return new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_QUERY_EXPS_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new ConsultaExpedientesException(Long.valueOf(cCodigo.toString()).longValue(), poException);

	}
	
    // ========================================================================

}