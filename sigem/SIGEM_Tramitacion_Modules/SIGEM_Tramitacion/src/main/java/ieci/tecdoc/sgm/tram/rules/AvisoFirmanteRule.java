package ieci.tecdoc.sgm.tram.rules;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Regla para el aviso a un firmante de circuito de firmas en el momento
 * que le llegue el turno. Esta regla soporta los siguientes avisos:
 * 
 * <ul>
 * <li>Correo electrónico: cuando el tipo de notificación es EM.</li> 
 * <li>SMS: cuando el tipo de notificación es SM.</li>
 * </ul>
 * 
 * <p>Para el envío de correos electrónicos, es necesario definir las
 * siguientes variables del sistema en el Catálogo de Procedimientos:</p>
 * 
 * <ul>
 * <li>AVISO_FIRMANTE_EMAIL_FROM: Dirección de correo electrónico remitente.</li>
 * <li>AVISO_FIRMANTE_EMAIL_SUBJECT: Asunto del correo electrónico.</li>
 * <li>AVISO_FIRMANTE_EMAIL_CONTENT: Contenido del correo electrónico.</li>
 * </ul>
 *
 * <p>Para el envío de SMS, es necesario definir las siguientes variables 
 * del sistema:</p>
 * 
 * <ul>
 * <li>AVISO_FIRMANTE_SMS_USER: Usuario del módulo de envío de SMS.</li>
 * <li>AVISO_FIRMANTE_SMS_PASSWORD: Contraseña del usuario del módulo de envío de SMS.</li>
 * <li>AVISO_FIRMANTE_SMS_SRC: Número de teléfono remitente.</li>
 * <li>AVISO_FIRMANTE_SMS_TXT: Contenido del mensaje.</li>
 * </ul>
 * 
 * <p>Los mensajes definidos para el asunto y contenido de los correos electrónicos
 * y el texto de los SMS pueden utilizar variables. El formato de las variables es
 * ${NOMBRE_VARIABLE}. Las variables definidas son:</p>
 * 
 * <ul>
 * <li>NUMEXP: Número de expediente al que pertenece el documento.</li>
 * <li>DOCUMENT_NAME: Nombre del documento.</li>
 * <li>DOCUMENT_DESC: Descripción del documento.</li>
 * <li>DOCUMENT_DATE: Fecha del documento.</li>
 * </ul>
 * 
 */
public class AvisoFirmanteRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoFirmanteRule.class);
	
    //--------------------------------------------------------------------------------------------------
    // Códigos de los tipos de notificación.
    //--------------------------------------------------------------------------------------------------
	private static final String NOTIF_TYPE_EMAIL 			= "EM";
	private static final String NOTIF_TYPE_SMS 				= "SM";
    //--------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------
    // Variables Globales
    //--------------------------------------------------------------------------------------------------
	private static final String EMAIL_FROM_VAR_NAME = 		"AVISO_FIRMANTE_EMAIL_FROM";
	private static final String EMAIL_SUBJECT_VAR_NAME = 	"AVISO_FIRMANTE_EMAIL_SUBJECT";
	private static final String EMAIL_CONTENT_VAR_NAME = 	"AVISO_FIRMANTE_EMAIL_CONTENT";

	private static final String SMS_USER_VAR_NAME = 		"AVISO_FIRMANTE_SMS_USER";
	private static final String SMS_PASSWORD_VAR_NAME = 	"AVISO_FIRMANTE_SMS_PASSWORD";
	private static final String SMS_SRC_VAR_NAME = 			"AVISO_FIRMANTE_SMS_SRC";
	private static final String SMS_TXT_VAR_NAME = 			"AVISO_FIRMANTE_SMS_TXT";
    //--------------------------------------------------------------------------------------------------

	
	/**
	 * Constructor.
	 */
	public AvisoFirmanteRule() {
		super();
	}

	/**
	 * Inicializa la regla.
	 * 
	 * @param rulectx
	 *            Contexto de la regla.
	 * @return true si la inicialización se ha ejecutado correctamente.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	/**
	 * Valida la regla.
	 * 
	 * @param rulectx
	 *            Contexto de la regla.
	 * @return true si la regla se ha validado.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	/**
	 * Ejecuta la regla.
	 * 
	 * @param rulectx
	 *            Contexto de la regla.
	 * @return Objeto de respuesta de la regla.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		try {
			
			IClientContext ctx = rulectx.getClientContext();
			IInvesflowAPI invesflowAPI = ctx.getAPI();
			ISignAPI signAPI = invesflowAPI.getSignAPI();

			int circuitId = rulectx.getInt("ID_CIRCUITO");
			int stepId = rulectx.getInt("ID_PASO");
			
			// Información del paso
			IItem step = signAPI.getCircuitStep(circuitId, stepId);
			String tipoNotif = step.getString("TIPO_NOTIF");
			
			if (StringUtils.isBlank(tipoNotif)) {
				// nada
			} else if (NOTIF_TYPE_EMAIL.equals(tipoNotif)) {
				sendEmail(rulectx, step);
			} else if (NOTIF_TYPE_SMS.equals(tipoNotif)) {
				sendSms(rulectx, step);
			} else {
				logger.warn("Tipo de notificación no soportado por la regla: " + tipoNotif);
			}
			
		} catch (Exception e) {
			logger.warn("Error en la regla AvisoFirmateRule", e);
		}
		
		return null;
	}

	/**
	 * El sistema invoca esta función si se encuentra con algún problema durante
	 * la ejecución del evento. Permitiría deshacer operaciones realizadas en el
	 * método execute().
	 * 
	 * @param rulectx
	 *            Contexto de la regla.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	
	protected void sendEmail(IRuleContext rulectx, IItem step) throws ISPACException, SigemException {
		String dirNotif = step.getString("DIR_NOTIF");
		if (StringUtils.isNotBlank(dirNotif)) {

			IClientContext ctx = rulectx.getClientContext();
			
			String from = ConfigurationMgr.getVarGlobal(ctx, EMAIL_FROM_VAR_NAME);
			String subject = ConfigurationMgr.getVarGlobal(ctx, EMAIL_SUBJECT_VAR_NAME);
			String content = ConfigurationMgr.getVarGlobal(ctx, EMAIL_CONTENT_VAR_NAME);
			
			Map variables = getVariables(rulectx);

			if (StringUtils.isNotBlank(subject)) {
				subject = StringUtils.replaceVariables(subject, variables);
			}

			if (StringUtils.isNotBlank(content)) {
				content = StringUtils.replaceVariables(content, variables);
			}

			ServicioMensajesCortos svc = LocalizadorServicios.getServicioMensajesCortos();
			svc.sendMail(from, new String[] { dirNotif }, null, null, subject, content, null);
		}
	}

	protected void sendSms(IRuleContext rulectx, IItem step) throws ISPACException, SigemException {
		String dirNotif = step.getString("DIR_NOTIF");
		if (StringUtils.isNotBlank(dirNotif)) {

			IClientContext ctx = rulectx.getClientContext();
			
			String user = ConfigurationMgr.getVarGlobal(ctx, SMS_USER_VAR_NAME);
			String pwd = ConfigurationMgr.getVarGlobal(ctx, SMS_PASSWORD_VAR_NAME);
			String src = ConfigurationMgr.getVarGlobal(ctx, SMS_SRC_VAR_NAME);
			String txt = ConfigurationMgr.getVarGlobal(ctx, SMS_TXT_VAR_NAME);

			if (StringUtils.isNotBlank(txt)) {
				txt = StringUtils.replaceVariables(txt, getVariables(rulectx));
			}

			ServicioMensajesCortos svc = LocalizadorServicios.getServicioMensajesCortos();
			svc.sendSMS(user, pwd, src, dirNotif, txt);
		}
	}
	
	protected Map getVariables(IRuleContext rulectx) {
		
		Map variables = new HashMap();

		try {
			
			IClientContext ctx = rulectx.getClientContext();
			IInvesflowAPI invesflowAPI = ctx.getAPI();
			ISignAPI signAPI = invesflowAPI.getSignAPI();
	
			int instancedStepId = rulectx.getInt("ID_INSTANCIA_PASO");
			IItem instancedStep = signAPI.getStepInstancedCircuit(instancedStepId);
			
			int documentId = instancedStep.getInt("ID_DOCUMENTO");
			IItem document = invesflowAPI.getEntitiesAPI().getDocument(documentId);
			
			variables.put("NUMEXP", document.getString("NUMEXP"));
			variables.put("DOCUMENT_NAME", document.getString("NOMBRE"));
			variables.put("DOCUMENT_DESC", document.getString("DESCRIPCION"));
			variables.put("DOCUMENT_DATE", TypeConverter.toString(document.getDate("FDOC"), TypeConverter.TIMESTAMPFORMAT));
			
		} catch (Exception e) {
			logger.warn("Error al obtener la información del documento", e);
		}
		
		return variables;
	}
	
}
