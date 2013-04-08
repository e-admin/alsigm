package ieci.tecdoc.sgm.mensajes_cortos.mgr;

import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;

/**
 * Interfaz para los gestores de envíos de SMSs.
 *
 */
public interface SMSManager {

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
			String txt) throws MensajesCortosException;

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
			String txt) throws MensajesCortosException;


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
			throws MensajesCortosException;

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
			String txt, String lang) throws MensajesCortosException;
	
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
			throws MensajesCortosException;

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
			throws MensajesCortosException;

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
			throws MensajesCortosException;

}
