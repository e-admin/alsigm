package ieci.tdw.ispac.api.rule.email;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.helper.RuleHelper;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispactx.TXConstants;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
*
* se crea el hito para que se proceda a su envio desde el publicador.
*
*
* <p>Para el envío de correos electrónicos, es necesario definir las
*  variables del sistema en el Catálogo de Procedimientos.
*  Estas variables de sistema se definen en la clase que hereda de ésta a través
*  de los métodos:
*  	public abstract String getEmailFrom();
*
*	public abstract String getEmailSubject();
*
*	public abstract String getEmailContent();
*	Ejemplo:
*		public String getEmailContent() {
*		return "EMAIL_CONTENT";
*	}
*
*
*	public String getEmailFrom() {
*		return  "EMAIL_FROM";
*	}
*
*	public String getEmailSubject() {
*
*		return  "EMAIL_SUBJECT";
*	}
*	Las variables de sistema definidas han de ser:
* <ul>
* <li>EMAIL_FROM: Dirección de correo electrónico remitente.</li>
* <li>EMAIL_SUBJECT: Asunto del correo electrónico.</li>
* <li>EMAIL_CONTENT: Contenido del correo electrónico.</li>
* </ul>
*</p>

*
*
*
* <p>Los mensajes definidos para el asunto y contenido de los correos electrónicos
*  El formato de las variables es
* ${NOMBRE_VARIABLE}. Las variables definidas son:</p>
*
* <ul>
* <li>NUMEXP: Número de expediente al que pertenece el documento.</li>
* <li>DOCUMENT_NAME: Nombre del documento.</li>
* <li>DOCUMENT_DESC: Descripción documento.</li>
* <li>DOCUMENT_DATE: Fecha del Documento.</li>
* <li>DOCUMENT_DATE_SIGN: Fecha del Documento.</li>
* </ul>
*
* <ul><li>Los destinatarios se obtienen de la entidad de participantes  </li>
* </ul>
*
*/

public abstract class AvisoEmailRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoEmailRule.class);

	protected List <IItem> documents = new ArrayList <IItem>();
	protected List <Object> destinatarios = null;
	private List <String> info_envios = null;
	protected int idTramite = -1;

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio AvisoEmailRule: execute");
		}
		try {

			int i = 0;
			if(info_envios.size()>0 ){
				// Insertamos Hitos
				for (i = 0; i < info_envios.size(); i++) {
					// Se genera el hito informativo
					rulectx.getClientContext().getAPI().getTransactionAPI()
					.newMilestone(rulectx.getProcessId(),
							rulectx.getStageProcedureId(),
							rulectx.getTaskProcedureId(),
							TXConstants.MILESTONE_INFORMATIVO_EMAIL,
							(String) info_envios.get(i), "Envío e-mail");
				}
			}

		} catch (Exception e) {
			logger.warn("Error en la regla AvisoEmailDocumentoFirmadoRule", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin AvisoEmailRule: execute");
		}
		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		info_envios = new ArrayList <String>();

		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio AvisoEmailRule: validate");
		}
		idTramite = rulectx.getTaskId();
		boolean noDoc = false;

		if (logger.isInfoEnabled()) {
			logger.info("Validamos la regla AvisoEmailRule");
		}
		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		try {
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			IItemCollection dest = entitiesAPI.getEntities(
					SpacEntities.SPAC_DT_INTERVINIENTES, rulectx.getNumExp(),
					getQueryDestinario());
			IItem destinatario = null;
			if (oneEmailByEachDest()) {
				while (dest.next() && (!isDocument() || !noDoc)) {
					destinatario = dest.value();
					documents = new ArrayList <IItem>();
					IItemCollection docs = entitiesAPI.getDocuments(rulectx
							.getNumExp(), getQueryDocumento(ctx, destinatario),
					" ID DESC ");

					if (docs.next()) {

						documents = docs.toList();

					} else {
						noDoc = true;
					}
					if((docs.next() || !isDocument()) && destinatario!=null &&  getCondition(rulectx)){
						destinatarios = new ArrayList <Object>();
						destinatarios.add(destinatario);
						addXMLInfoHito(rulectx);

					}
				}

			} else {
				IItemCollection docs = entitiesAPI.getEntities(
						SpacEntities.SPAC_DT_DOCUMENTOS, rulectx.getNumExp(),
						getQueryDocumento(ctx, null));
				if (docs.next()) {

					documents = docs.toList();

				} else {
					noDoc = true;
				}
				if((docs.next() || !isDocument())&& dest.next() &&  getCondition(rulectx)) {
					destinatarios = dest.toList();
					addXMLInfoHito(rulectx);
				}

			}
			if (noDoc) {

				if (logger.isDebugEnabled()) {
					logger.debug("No hay ningún documento a enviar por email");
				}
				if (isDocument()) {
					rulectx.setInfoMessage(RuleHelper.getMessage(rulectx
							.getClientContext().getLocale(), "email.noDoc"));
					return false;

				}
			}
		} catch (ISPACException e) {
			logger.warn("Error al ejecutar la regla AvisoEmailRule", e);
			throw new ISPACRuleException(e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin AvisoEmailRule: validate");
		}
		return true;

	}

	protected void addXMLInfoHito(IRuleContext rulectx) throws ISPACException{

		String from="";
		IClientContext ctx = rulectx.getClientContext();
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio AvisoEmailRule: addXMLInfoHito");
		}

		if (isStaticFrom()) {
			from = ConfigurationMgr.getVarGlobal(ctx, getEmailFrom());
		} else {
			from = getEmailFrom();
		}

		String infoHito=AvisoEmailHelper.getXMLInfoHito(destinatarios, documents, from, getEmailSubject(), getEmailContent(), ctx, rulectx.getNumExp());

		info_envios.add(infoHito);
		if (logger.isInfoEnabled()) {
			logger
			.info("AvisoEmailRule: addXMLInfoHito-> XML del hito a añadir:"
					+ infoHito);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin AvisoEmailRule: addXMLInfoHito");
		}
	}



	/**
	 *
	 * Indica si el remitente del email es simpre el mismo o depende de los
	 * datos del expediente.
	 *
	 * @return
	 */
	public boolean isStaticFrom() {
		return true;
	}

	public abstract String getQueryDestinario() throws ISPACException;

	public abstract String getQueryDocumento(IClientContext ctx, IItem item)
	throws ISPACException;

	public abstract String getEmailFrom();

	public abstract String getEmailSubject();

	public abstract String getEmailContent();

	/**
	 * Indica si es obligatorio o no que haya documentos a enviar
	 *
	 * @return
	 */
	public abstract boolean isDocument();

	/**
	 *
	 * @return true: Si el email es distinto para cada destinatario false: En
	 *         caso contrario
	 */
	public abstract boolean oneEmailByEachDest();

	/**
	 * Aquellas reglas que necesiten cumplir una condición adicional
	 * @return
	 * @throws ISPACRuleException
	 */
	public boolean  getCondition(IRuleContext rulectx) throws ISPACRuleException{return true;}

}
