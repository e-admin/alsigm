package ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos;

import ieci.tdw.ispac.api.expedients.adapter.XMLFacade;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos.vo.DestinatarioVO;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;

import java.util.List;

import org.apache.log4j.Logger;


/**
 * 
 * Clase que ejecuta la accion de enviar email. La regla que tenga asociada esta acción en el campo xml de attributos deberá tener un xml
 * semejante al que se indica a continuación
 * 
 * <?xml version='1.0' encoding='ISO-8859-1'?>
	<attributes>
		<attribute name='DESTINATARIOS'>
			<addressees>
				<addressee>sigem@localhost.com</addressee>
					<roles>	
						<!-- para indicar todos los roles <role>*</role>
						<role>INT</role>
						<role>LIC</role>
					</roles>
			</addressees>
		</attribute>
		<attribute name="MENSAJE">
			<labels default='es'>
				<label locale='es'>
				[ES]Estimado ${var:NOMBRE} Se ha comprobado la documentación del procedimiento  ${var:NOMBRE_PROCEDIMIENTO} aportada en el expediente Nº ${var: NUM_EXP} siendo la fecha de inicio de plazo: ${xpath:/EXPEDIENTE/SPAC_EXPEDIENTES/REGISTROS/FINICIOPLAZO } 
				</label>
				<label locale='eu'>
				[EU] Estimado ${var:NOMBRE} Se ha comprobado la documentación del procedimiento  ${var:NOMBRE_PROCEDIMIENTO} aportada en el expediente Nº ${var: NUM_EXP} siendo la fecha de inicio de plazo: ${xpath:/EXPEDIENTE/SPAC_EXPEDIENTES/REGISTROS/FINICIOPLAZO } 
				</label>
				
				<label locale='ca' Estimado ${var:NOMBRE} Se ha comprobado la documentación del procedimiento  ${var:NOMBRE_PROCEDIMIENTO} aportada en el expediente Nº ${var: NUM_EXP} siendo la fecha de inicio de plazo: ${xpath:/EXPEDIENTE/SPAC_EXPEDIENTES/REGISTROS/FINICIOPLAZO } 
				</label>
				<label locale='gl'>[GL] Estimado ${var:NOMBRE} Se ha comprobado la documentación del procedimiento  ${var:NOMBRE_PROCEDIMIENTO} aportada en el expediente Nº ${var: NUM_EXP} siendo la fecha de inicio de plazo: ${xpath:/EXPEDIENTE/SPAC_EXPEDIENTES/REGISTROS/FINICIOPLAZO } 
				</label>
			
			</labels>
		</attribute>
		<attribute name="ASUNTO">
			<labels default='es'>
				<label locale='es'>
				[ES]Documentación del expediente Nº ${var:NUM_EXP}
				</label>
				<label locale='eu'>
				[EU] Documentación del expediente Nº ${var:NUM_EXP}
				</label>
				<label locale='ca'>
				[CA] Documentación del expediente Nº ${var:NUM_EXP}
				</label>
				<label locale='gl'>
				[GL] Documentación del expediente Nº ${var:NUM_EXP}
				</label>
			</labels>
		</attribute>
		<attribute name="EMISOR">nombre@servidor.com</attribute>
</attributes>

 *
 */
public class EnvioMailAction extends MensajesCortosAction{

	
	
	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(EnvioMailAction.class);
    
    private static final String ATR_ASUNTO="ASUNTO";
   
    
    /**
     * Constructor
     */
    public EnvioMailAction() {
    	super();
    	clientContext = new ClientContext();
    	clientContext.setAPI(new InvesflowAPI(clientContext));
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
	public boolean execute(RuleContext rctx, AttributeContext actx)
			throws ActionException {

		  if (logger.isInfoEnabled()) {
	            logger.info("Acción [" + this.getClass().getName() + "] en ejecución");
	        }
		  
		  
		try {
			String xmlExpediente=getXmlExpediente(rctx.getIdObjeto());
			componerEnviarSms(xmlExpediente, actx, ENVIO_EMAIL);
			
		 } catch (ActionException e) {
	        	setInfo("Error en el envio de sms sin certificado para el expediente "
	        			+ rctx.getIdObjeto() + ": " + e.toString());
	            throw e;
	        } catch (Throwable e) {
	        	setInfo("Error en el envio de sms sin certificado para el expediente "
	        			+ rctx.getIdObjeto() + ": " + e.toString());
	            throw new ActionException(e);
	        }
	        
	        
	        return true;
	}

	

	
	 /**
	  * Realiza el envio de los emails
	  */
	public void realizarEnvio(AttributeContext actx, List destinatariosVo)
			throws SigemException {
		
		
		String asunto=actx.getAttribute(ATR_ASUNTO);
		asunto=StringUtils.unescapeXml(asunto);
		XMLFacade xmlAsunto= new XMLFacade(asunto);
		String emisor = actx.getAttribute(ATR_EMISOR);
		fillTexto(xmlAsunto, destinatariosVo, TEXTO_ASUNTO);
		
		ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();	
		for(int i=0; i< destinatariosVo.size(); i++){
			
			DestinatarioVO dest=(DestinatarioVO) destinatariosVo.get(i);
			String [] str= new String[1];
			str[0]=dest.getMail();
			servicio.sendMail(emisor, str, null, null, dest.getAsunto(), dest.getTexto(),null);
			
		}
		
		
		
	}
	    
}
