package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import java.util.Date;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.HitoVO;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;

import org.apache.log4j.Logger;

/**
 * Acción para dar de alta un hito histórico de un expediente 
 * en la Consulta Telemática.
 * 
 */
public class EstablecerHitoHistoricoAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(EstablecerHitoHistoricoAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = 
    	Logger.getLogger("CONSULTA_TELEMATICA");


    /**
     * Constructor.
     * 
     */
    public EstablecerHitoHistoricoAction() {
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

        HitoVO hito = null;
        
        try {
	        
        	// Información del hito
	        hito = new HitoVO();
	        hito.setCnumexp(rctx.getIdObjeto());
	        hito.setCguid(String.valueOf(rctx.getIdHito()));
	        hito.setCcod(attContext.getAttribute("CODIGO"));
	        hito.setCfecha(new Date());
	        hito.setCdescr(attContext.getAttribute("DESCRIPCION"));
	        hito.setCinfoaux("");
	        
	        // Establece el hito histórico en Consulta Telemática
	        establecerHitoHistorico(hito);

	        // Log del hito
	        logOk(hito);

        } catch (Throwable e) {
        	setInfo("Error al establecer hito histórico: " + e.toString());
        	logError(hito, e);
            throw new ActionException(e);
        }
        
        return true;
    }

    /**
     * Muestra un log del hito.
     * @param hito Datos del hito.
     */
    private void logOk(HitoVO hito) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
	        // Información del hito
	        StringBuffer hitoInfo = new StringBuffer()
	        	.append("- HITO HISTÓRICO: ")
	        	.append(hito);
	        
	        // Log del resultado de la acción
	        CONSULTA_TELEMATICA.info("Alta de hito histórico:\n" 
	        		+ hitoInfo.toString());
    	}
    }

    /**
     * Muestra un log de error.
     * @param hito Datos del hito.
     */
    private static void logError(HitoVO hito, Throwable e) {
    	
        // Información del hito
        StringBuffer hitoInfo = new StringBuffer()
        	.append("- HITO HISTÓRICO: ")
        	.append(hito);
        
        // Log del error
        CONSULTA_TELEMATICA.error("Error en la acción " 
        		+ EstablecerHitoHistoricoAction.class.getName() + ":\n" 
        		+ hitoInfo.toString(), e);
    }

    /**
     * Establece el hito histórico en Consulta Telemática
     * @param hito Datos del hito.
     * @throws Exception si ocurre algún error.
     */
    private void establecerHitoHistorico(HitoVO hito) throws Exception {

    	HitoExpediente cthito = new HitoExpediente();

    	// Información del hito
		cthito.setNumeroExpediente(hito.getCnumexp());
		cthito.setCodigo(StringUtils.nullToEmpty(hito.getCcod()));
		cthito.setGuid(new Guid().toString());
		cthito.setFecha(TypeConverter.toString(hito.getCfecha(), ConstantesServicios.DATE_PATTERN));
		cthito.setDescripcion(hito.getCdescr());
		cthito.setInformacionAuxiliar(
				StringUtils.nullToEmpty(hito.getCinfoaux()));

		// Llamada al API de Consulta Telemática
		ServicioConsultaExpedientes consulta = 
			LocalizadorServicios.getServicioConsultaExpedientes();
		consulta.nuevoHitoHistorico(cthito, getEntidad());
    }

}