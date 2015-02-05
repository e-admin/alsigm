package ieci.tdw.ispac.api.rule.email;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class AvisoEmailHelper {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoEmailHelper.class);





	public static String getXMLInfoHito(List <Object>  destinatarios, List <IItem> documents, String from,String varSubject,String varContent, IClientContext ctx, String numexp) throws ISPACException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio getXMLInfoHito");
		}
		String documentos = "";
		IItem documento = null;
		String dest = "";
		int i = 0;
		Map <String, String>variables = null;


		// obtener el asunto el contenido del email y el asunto de las vbles del
		// sistema


		String subject = ConfigurationMgr.getVarGlobal(ctx, varSubject);
		String content = ConfigurationMgr.getVarGlobal(ctx, varContent);

		IItem document = null;
		if (documents != null && documents.size() > 0) {
			document = (IItem) documents.get(0);
		}
		variables = getVariables(numexp, document);

		if (StringUtils.isNotBlank(subject)) {
			subject = StringUtils.replaceVariables(subject, variables);
		}

		if (StringUtils.isNotBlank(content)) {
			content = StringUtils.replaceVariables(content, variables);
		}
		for (i = 0; i < documents.size(); i++) {
			documento = (IItem) documents.get(i);
			documentos += "<document><infopagRde>"
				+ documento.getString("INFOPAG_RDE") + "</infopagRde>";
			documentos += "<nameDocument>" + documento.getString("NOMBRE")
			+ "." + documento.getString("EXTENSION_RDE")
			+ "</nameDocument></document>";

		}

		for (i = 0; i < destinatarios.size(); i++) {

			String email="";
			if(destinatarios.get(i) instanceof IItem){
				IItem item=(IItem) destinatarios.get(i);
				email=item.getString("EMAIL");
			}
			else{
				email=(String) destinatarios.get(i);
			}
			dest += "<addressee>" + email
			+ "</addressee>";
			if (logger.isInfoEnabled()) {
				logger.info("Se añade el destinatario:"
						+ email
						+ " al envio por e-mail ");
			}
		}

		// Componemos el xml del hito
		String infoHito = new StringBuffer(
		"<?xml version='1.0' encoding='ISO-8859-1'?>").append(
		"<infoaux>").append("<documents><![CDATA[<documents>").append(
				documentos).append("</documents>]]></documents>").append(
				"<subject>").append(subject).append("</subject>").append(
				"<content>").append(content).append("</content>").append(
				"<sender>").append(from).append("</sender>").append(
				"<addressees><![CDATA[<addressees>").append(dest).append(
				"</addressees>]]></addressees>").append("</infoaux>")
				.toString();

		if (logger.isInfoEnabled()) {
			logger
			.info("getXMLInfoHito-> XML del hito a añadir:"
					+ infoHito);
		}


		if (logger.isDebugEnabled()) {
			logger.debug("Fin getXMLInfoHito");
		}
		return infoHito;
	}

	public static Map<String, String> getVariables(String numexp, IItem document) {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio AvisoEmailRule: getVariables");
		}
		Map <String, String> variables = new HashMap <String, String>();
		try {
			variables.put("NUMEXP", numexp);
			if (document != null) {
				variables.put("DOCUMENT_NAME", document.getString("NOMBRE"));
				variables.put("DOCUMENT_DESC", document
						.getString("DESCRIPCION"));
				variables.put("DOCUMENT_DATE", TypeConverter.toString(document
						.getDate("FDOC"), TypeConverter.TIMESTAMPFORMAT));
				variables.put("DOCUMENT_DATE_SIGN", TypeConverter.toString(
						document.getDate("FFIRMA"),
						TypeConverter.TIMESTAMPFORMAT));
			}

		} catch (Exception e) {
			logger.warn("Error al obtener la información del documento", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Fin AvisoEmailRule: getVariables");
		}
		return variables;
	}



}
