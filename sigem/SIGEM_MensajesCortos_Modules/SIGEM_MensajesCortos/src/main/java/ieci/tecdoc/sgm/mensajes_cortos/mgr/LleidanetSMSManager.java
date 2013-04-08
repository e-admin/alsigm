package ieci.tecdoc.sgm.mensajes_cortos.mgr;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.mensajes_cortos.helpers.HttpClientHelper;
import ieci.tecdoc.sgm.mensajes_cortos.helpers.XmlFacade;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LleidanetSMSManager implements SMSManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(LleidanetSMSManager.class);
	
	protected static final String SMS_RESPONSE_STATUS_OK	= "100";
	
	protected static final String EMAIL_NOREPLY = "no-reply@lleida.net";
	
	/**
	 * URL de envío de SMSs.
	 */
	private String smsUrl = null;
	
	/**
	 * Url de consulta de la firma
	 */
	private String signUrl =null;
	/**
	 * Nombre o dirección IP del proxy HTTP. 
	 */
	private String proxyHost = null;
	
	/**
	 * Puerto del proxy HTTP.
	 */
	private int proxyPort = 0;
	
	/**
	 * Usuario del proxy HTTP.
	 */
	private String proxyUser = null;
	
	/**
	 * Clave del usuario del proxy HTTP.
	 */
	private String proxyPassword = null;
	
	/**
	 * Prefijo por defecto
	 */
	private static String defaultPrefix=null;
	
	
	/**
	 * Número de digitos de los móviles
	 */
	private static String numDigits;
	/**
	 * Constructor.
	 */
	public LleidanetSMSManager() {
		super();
	}
	
	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}


	/**
	 * Envía un mensaje SMS.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param src
	 *            Remitente del mensaje
	 * @param dst
	 *            Destinatario del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * @return Identificador del mensaje enviado.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public String sendSMS(String user, String pwd, String src, String dst,
			String txt) throws MensajesCortosException {

		logger.info("Entrada en sendSMS");

		String mtid = new Guid().toString();
		if (logger.isDebugEnabled()) {
			logger.debug("mt_id generado: " + mtid);
		}
		
		// Componer el mensaje XML de petición
		String xml = composeXmlToSendSms(user, pwd, src, dst, txt, mtid, false);
		
		// Enviar el XML
		sendXML(xml,smsUrl);
		
		// Devolver el identificador único del mensaje
		return generateId(mtid, dst);
	}

	/**
	 * Envía un mensaje SMS.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param src
	 *            Remitente del mensaje
	 * @param dst
	 *            Destinatarios del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * @return Identificadores de los mensajes enviados.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public String[] sendSMS(String user, String pwd, String src, String[] dst,
			String txt) throws MensajesCortosException {
		String [] mtids= null;
		if(dst!=null){
			mtids=new String[dst.length];
			for(int i=0; i< dst.length; i++){
				mtids[i]=sendSMS(user, pwd, src, dst[i], txt);
				
			}
		}
		return mtids;
	}


	/**
	 * Obtiene el estado de un mensaje SMS enviado.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param id
	 *            Identificador del SMS enviado.
	 *            
	 * @return Estado del mensaje.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public int getSMSStatus(String user, String pwd, String id)
			throws MensajesCortosException {
		
		logger.info("Entrada en getSMSStatus");

		// Componer el mensaje XML de petición
		String xml = "<?xml version='1.0' encoding='ISO-8859-1' ?>"
			+ "<query_mt_status>"
			+ "<user>" + user + "</user>"
			+ "<password>" + pwd + "</password>"
			+ "<mt_id>" + id + "</mt_id>"
			+ "</query_mt_status>";
		
		// Enviar el XML
		XmlFacade response = sendXML(xml,smsUrl);
		
		int statusCode = parseInt(response.get("/result/mt_status/status_code/text()"), -1);
		switch (statusCode) {
			case 1:	
				return ServicioMensajesCortos.SMS_STATUS_NEW;			//1
			case 2:	
				return ServicioMensajesCortos.SMS_STATUS_PENDING;		//2
			case 3:	
				return ServicioMensajesCortos.SMS_STATUS_SENT;			//3
			case 4:	
				return ServicioMensajesCortos.SMS_STATUS_DELIVERED;		//4
			case 5:	
				return ServicioMensajesCortos.SMS_STATUS_BUFFERED;		//5
			case 6:	
				return ServicioMensajesCortos.SMS_STATUS_FAILED;		//6
			case 7:	
				return ServicioMensajesCortos.SMS_STATUS_INVALID;		//7
			case 8:	
				return ServicioMensajesCortos.SMS_STATUS_CANCELLED;		//8
			case 9:	
				return ServicioMensajesCortos.SMS_STATUS_SCHEDULED;		//9
			default:
				return ServicioMensajesCortos.SMS_STATUS_INVALID;		//7
		}
		
	}

	/**
	 * Envía un mensaje SMS certificado.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param src
	 *            Remitente del mensaje
	 * @param dst
	 *            Destinatarios del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * @param lang
	 *            Lenguaje del resguardo del mensaje.
	 * 
	 * @return Identificador del mensaje certificado.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public String sendCertSMS(String user, String pwd, String src, String dst,
			String txt, String lang) throws MensajesCortosException {
		
		logger.info("Entrada en sendCertSMS");

		String mtid = new Guid().toString();
		if (logger.isDebugEnabled()) {
			logger.debug("mt_id generado: " + mtid);
		}
		
		//Construimos el xml indicando que va a ser un  mensaje certificado
		String xml=composeXmlToSendSms(user, pwd, src, dst, txt, mtid, true);
		// Enviar el XML
		sendXML(xml,smsUrl);
		
		// Devolver el identificador único del mensaje
		return generateId(mtid, dst);
	}
	
	/**
	 * Obtiene el estado del proceso de firma del resguardo del envío de un SMS
	 * certificado.
	 * 
	 * @param user
	 *            Usuario del conector.
	 *            
	 * @param pwd
	 *            Clave del usuario del conector.
	 *            
	 * @param id
	 *            Identificador del SMS certificado.
	 *
	 *            
	 * @return Estado del proceso de firma.
	 * Los posibles valores que puede retornar y su significado son :
	 * 
	 * 1 Waiting for service    	--> El servidor de firma electr´onica se esta inicializando o el servicio del 
	 *                               	tercero no esta temporalmente disponible por algun motivo. Fichero pendiente de generación.
	 * 2 Query done 		   		--> Generando el contenido y cursando la petici´on de firma electr´onica del contenido.
	 * 							    	Fichero pendiente de generación.
	 * 3 Generating the content 	--> Incrustando la firma en el fichero PDF. Fichero pendiente de generación.
	 * 4 Sending mail notification 	--> Notificando al usuario a través del correo electrónico.Fichero no disponible todavía.
	 * 5 Finished pdf 				-->Proceso de firma finalizado.Fichero temporalmente disponible en el repositorio
	 *-1 Error						--> Se produjo error
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public int getCertSMSSignatureStatus(String user, String pwd, String id)
			throws MensajesCortosException {

		
		String [] parts= id.split(":");
	
		
		String xml = "<?xml version='1.0' encoding='ISO-8859-1' ?>"
			+ "<signature_status>"
			+ "<user>" + user + "</user>"
			+ "<password>" + pwd + "</password>"
			+ "<dst>"+ parts[1] + "</dst>"
			+ "<mt_id>" +parts[0] + "</mt_id>"
			+ "</signature_status>";
		
		// Enviar el XML
		XmlFacade response = sendXML(xml,signUrl);
		int statusCode=parseInt(response.get("/result/status_code/text()"), -1);
		switch(statusCode){
			case ServicioMensajesCortos.SIGNATURE_STATUS_WAITING_FOR_SERVICE: 		return ServicioMensajesCortos.SIGNATURE_STATUS_WAITING_FOR_SERVICE;
			case ServicioMensajesCortos.SIGNATURE_STATUS_QUERY_DONE: 		 		return ServicioMensajesCortos.SIGNATURE_STATUS_QUERY_DONE;
			case ServicioMensajesCortos.SIGNATURE_STATUS_GENERATING_CONTENT: 		return ServicioMensajesCortos.SIGNATURE_STATUS_GENERATING_CONTENT;
			case ServicioMensajesCortos.SIGNATURE_STATUS_SENDING_MAIL_NOTIFICATION: return ServicioMensajesCortos.SIGNATURE_STATUS_SENDING_MAIL_NOTIFICATION;
			case ServicioMensajesCortos.SIGNATURE_STATUS_FINISHED_PDF: 				return ServicioMensajesCortos.SIGNATURE_STATUS_FINISHED_PDF;
			default													 : 				return ServicioMensajesCortos.SIGNATURE_STATUS_ERROR;
			
		}
	}

	/**
	 * Obtiene el resguardo del envío de un SMS certificado en formato XML.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * 
	 * @param pwd
	 *            Clave del usuario del conector.
	 * 
	 * @param id
	 *            Identificador del SMS certificado.
	 *            
	 * @return XML con el resguardo.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public String getCertSMSSignatureXML(String user, String pwd, String id)
			throws MensajesCortosException {

		
		String [] parts= id.split(":");

		
		String xml = "<?xml version='1.0' encoding='ISO-8859-1' ?>"
			+ "<get_pdf_content>"
			+ "<user>" + user + "</user>"
			+ "<password>" + pwd + "</password>"
			+ "<dst>"+ parts[1] + "</dst>"
			+ "<mt_id>" +parts[0] + "</mt_id>"
			+ "</get_pdf_content>";
		
		// Enviar el XML
		XmlFacade response = sendXML(xml, signUrl);
		int file_status= parseInt(response.get("/result/ file_status/text()"), -1);
		if (ServicioMensajesCortos.SIGNATURE_STATUS_FINISHED_EXIST_PDF== file_status) {
		String SignatureXMLBase64=response.get("/result/file_content");
		return SignatureXMLBase64;
		}
		else{
			logger.error("Error en la llamada al servicio getCertSMSSignatureXML: " + response);
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION);
		}
	}

	/**
	 * Obtiene el resguardo del envío de un SMS certificado en formato binario.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * 
	 * @param pwd
	 *            Clave del usuario del conector.
	 * 
	 * @param id
	 *            Identificador del SMS certificado.
	 *            
	 * @return Binario con el resguardo.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public byte[] getCertSMSSignatureDocument(String user, String pwd, String id)
			throws MensajesCortosException {
	
		String [] parts= id.split(":");
	
		
		String xml = "<?xml version='1.0' encoding='ISO-8859-1' ?>"
			+ "<download_pdf>"
			+ "<user>" + user + "</user>"
			+ "<password>" + pwd + "</password>"
			+ "<dst>"+ parts[1] + "</dst>"
			+ "<mt_id>" +parts [0] + "</mt_id>"
			+ "</download_pdf>";
		
		// Enviar el XML y recoger los bytes del fichero resultante
		return sendXML(xml);
		
		
	}

	protected HttpClientHelper prepareHttpClientHelper(){
		
		HttpClientHelper helper = new HttpClientHelper();
		helper.setProxyHost(proxyHost);
		helper.setProxyPort(proxyPort);
		helper.setProxyUser(proxyUser);
		helper.setProxyPassword(proxyPassword);
		return helper;
	}
	protected byte [] sendXML(String xml)throws MensajesCortosException {
		
		byte[] res=null;
		try {
			HttpClientHelper helper = prepareHttpClientHelper();
			if (logger.isDebugEnabled()) {
				logger.debug("Envío de XML: " + xml);
			}
			
			res = helper.post(signUrl, new NameValuePair[] {
					new NameValuePair("xml", xml)
			});
			
		} catch (Throwable t) {
		logger.error("Error en el envío del XML: " + xml, t);
		throw new MensajesCortosException(t);
		}
	
		return res;
		
	}

	/**
	 * Envía un mensaje XML al servicio del proveedor.
	 * @param xml XML con el mensaje de envío.
	 * @return XML con el mensaje de respuesta.
	 * @throws MensajesCortosException si ocurre algún error.
	 */
	protected XmlFacade sendXML(String xml, String url) throws MensajesCortosException {

		try {
			HttpClientHelper helper = prepareHttpClientHelper();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Envío de XML: " + xml);
			}
			
			String response = helper.postAsString(url, new NameValuePair[] {
					new NameValuePair("xml", xml)
			});
		
	
			if (logger.isDebugEnabled()) {
				logger.debug("Respuesta: " + response);
			}
			
			// Comprobar el resultado
			return checkResult(response);
			
		} catch (MensajesCortosException e) {
			throw e;
		} catch (Throwable t) {
			logger.error("Error en el envío del XML: " + xml, t);
			throw new MensajesCortosException(t);
		}
	}
	
	/**
	 * Comprueba el resultado de la petición.
	 * @param response XML con el mensaje de respuesta.
	 * @return XML con el mensaje de respuesta.
	 * @throws MensajesCortosException si ocurre algún error.
	 */
	protected static XmlFacade checkResult(String response) throws MensajesCortosException {
		
		XmlFacade result = new XmlFacade(response);
		String status = result.get("/result/status/text()");
		
		if (!SMS_RESPONSE_STATUS_OK.equals(status)) {
			logger.error("Error en la llamada al servicio de envio de SMS: " + response);
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION);
		}
		
		return result;
	}
	
	protected static String generateId(String mtid, String dst) {
		return mtid + ":" + dst;
	}
	
	private static int parseInt(String str, int def) {
		int i = def;
		
		if (StringUtils.isNotBlank(str)) {
			try {
				i = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				i = def;
			}
		}
		return i;
	}
	
	

	
	private static String composeXmlToSendSms(String user, String pwd, String src, String dst, String txt, String mtid, boolean isCertificate){
		
		
		if(StringUtils.isNotEmpty(numDigits)){
			int numDigits= Integer.parseInt(LleidanetSMSManager.numDigits);
			//Comprobamos el formato de los número destino y origen
			if(StringUtils.isNotBlank(src) && src.length()==numDigits){
				src=defaultPrefix+src;
			}
			if(StringUtils.isNotBlank(dst) && dst.length()==numDigits){
				dst=defaultPrefix+dst;
			}
		}
		// Componer el mensaje XML de petición
		String xml = "<?xml version='1.0' encoding='ISO-8859-1' ?>"
			+ "<sms>"
			+ "<user>" + user + "</user>"
			+ "<password>" + pwd + "</password>"
			+ (StringUtils.isNotBlank(src) ? "<src>" + src + "</src>" : "")
			+ "<dst>"
			+ "<num>" + dst + "</num>"
			+ "</dst>"
			+ "<txt>" + txt + "</txt>"
			+ "<mt_id>" + mtid + "</mt_id>";
			if(isCertificate){
				xml+="<delivery_receipt cert_type='D'>"+EMAIL_NOREPLY+"</delivery_receipt>" ;
			}
			xml+= "</sms>";
			return xml;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	public void setDefaultPrefix(String defaultPrefix) {
		LleidanetSMSManager.defaultPrefix = defaultPrefix;
	}

	public String getNumDigits() {
			return LleidanetSMSManager.numDigits;
	}

	public void setNumDigits(String numDigits) {
		
			LleidanetSMSManager.numDigits =numDigits;
		
	}
}
