package ieci.tecdoc.sgm.mensajes_cortos;

import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;
import ieci.tecdoc.sgm.mensajes_cortos.mgr.EmailManager;
import ieci.tecdoc.sgm.mensajes_cortos.mgr.SMSManager;

import org.apache.log4j.Logger;

/**
 * Implementación del servicio de mensajes cortos de SIGEM.
 *
 */
public class SigemMensajesCortosServiceAdapter implements ServicioMensajesCortos {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SigemMensajesCortosServiceAdapter.class);

	/**
	 * Gestor de SMSs.
	 */
	private SMSManager smsManager = null;
	
	/**
	 * Gestor de correos electrónicos.
	 */
	private EmailManager emailManager = null;

	
	public SMSManager getSmsManager() {
		return smsManager;
	}

	public void setSmsManager(SMSManager smsManager) {
		this.smsManager = smsManager;
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
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
		try {
			return smsManager.sendSMS(user, pwd, src, dst, txt);
		} catch (MensajesCortosException e) {
			logger.error("Error en el envío del SMS", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error en el envío del SMS", e);
			throw new MensajesCortosException(e);
		}
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
		try {
			return smsManager.sendSMS(user, pwd, src, dst, txt);
		} catch (MensajesCortosException e) {
			logger.error("Error en el envío del SMS", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error en el envío del SMS", e);
			throw new MensajesCortosException(e);
		}
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
		try {
			return smsManager.getSMSStatus(user, pwd, id);
		} catch (MensajesCortosException e) {
			logger.error("Error al obtener el estado del SMS", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al obtener el estado del SMS", e);
			throw new MensajesCortosException(e);
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
		try {
			return smsManager.sendCertSMS(user, pwd, src, dst, txt, lang);
		} catch (MensajesCortosException e) {
			logger.error("Error en el envío del SMS certificado", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error en el envío del SMS certificado", e);
			throw new MensajesCortosException(e);
		}
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
	 * @return Estado del proceso de firma.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public int getCertSMSSignatureStatus(String user, String pwd, String id)
			throws MensajesCortosException {
		try {
			return smsManager.getCertSMSSignatureStatus(user, pwd, id );
		} catch (MensajesCortosException e) {
			logger.error("Error al obtener el estado de la firma del SMS certificado", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al obtener el estado de la firma del SMS certificado", e);
			throw new MensajesCortosException(e);
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
		try {
			return smsManager.getCertSMSSignatureXML(user, pwd, id );
		} catch (MensajesCortosException e) {
			logger.error("Error al obtener el XML del resguardo del SMS certificado", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al obtener el XML del resguardo del SMS certificado", e);
			throw new MensajesCortosException(e);
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
	 *            
	 * @return Binario con el resguardo.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public byte[] getCertSMSSignatureDocument(String user, String pwd, String id)
			throws MensajesCortosException {
		try {
			return smsManager.getCertSMSSignatureDocument(user, pwd, id);
		} catch (MensajesCortosException e) {
			logger.error("Error al obtener el resguardo del SMS certificado", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al obtener el resguardo del SMS certificado", e);
			throw new MensajesCortosException(e);
		}
	}
	
	/**
	 * Envía un correo electrónico.
	 * 
	 * @param from
	 *            Dirección de correo elentrónico del remitente.
	 * @param to
	 *            Dirección de correo electrónico del destinatario.
	 * @param cc
	 *            Dirección de correo electrónico de destinatarios en copia.
	 * @param bcc
	 *            Dirección de correo electrónico de destinatarios en copia
	 *            oculta.
	 * @param subject
	 *            Asunto del mensaje.
	 * @param content
	 *            Contenido del mensaje.
	 * @param attachments
	 *            Array de objetos Attachment con el nombre del fichero adjunto
	 *            y el contenido en binario.
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public void sendMail(String from, String[] to, String[] cc, String[] bcc, String subject,
			String content, Attachment[] attachments) throws MensajesCortosException {
		try {
			emailManager.sendMail(from, to, cc, bcc, subject, content, attachments);
		} catch (MensajesCortosException e) {
			logger.error("Error al enviar el correo electrónico", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al enviar el correo electrónico", e);
			throw new MensajesCortosException(e);
		}
	}
	
}
