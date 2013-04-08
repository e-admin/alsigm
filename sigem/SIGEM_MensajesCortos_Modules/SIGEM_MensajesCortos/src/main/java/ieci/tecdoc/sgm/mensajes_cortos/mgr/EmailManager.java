package ieci.tecdoc.sgm.mensajes_cortos.mgr;

import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;

/**
 * Interfaz para los gestores de envío de correos electrónicos.
 *
 */
public interface EmailManager {

	
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
			String content, Attachment[] attachments) throws MensajesCortosException;

}
