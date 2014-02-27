package ieci.tecdoc.sgm.migration.email;

import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;
import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.utils.Utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(EmailManager.class);
	
	/**
	 * Envía un correo a través de un sevidor SMTP
	 * @param from - Desde donde se envía el correo
	 * @param to - Destinatario al que se envía el correo
	 * @param cc - Destinatarios en copia
	 * @param bcc - Destinatarios en copia oculta
	 * @param subject - Asunto del correo
	 * @param content - Contenido del correo 
	 * @param attachments - Documentos adjuntos
	 * @throws MigrationException - Se lanza en el caso de que se produzca un error
	 */
	public void sendMail(String from, String[] to, String[] cc, String[] bcc,
	String subject, String content, Attachment[] attachments)
	throws MigrationException {
		
		String serverHost = Config.getInstance().getEmailServerHostSMTP();
		String user = Config.getInstance().getEmailUserSMTP();
		String serverPort = Config.getInstance().getEmailServerPortSMTP();
		
		try {
				Properties props = getProp(user, serverHost, serverPort);
				Authenticator auth = new autentificadorSMTP();            
				Session session = Session.getInstance(props, auth);            
				// session.setDebug(true);            
				MimeMessage msg = new MimeMessage(session);            
				
				// Email de origen
				if (StringUtils.isNotBlank(from)) { 
					InternetAddress addressFrom = new InternetAddress(from);
					msg.setFrom(addressFrom);
				}
				// Origen
				if (StringUtils.isNotBlank(from)) { 
					InternetAddress addressFrom = new InternetAddress(from);
					msg.setFrom(addressFrom);
				}

				// Destinatarios
				if (!ArrayUtils.isEmpty(to)) {
					msg.addRecipients(Message.RecipientType.TO, getAddresses(to));
				}
				if (!ArrayUtils.isEmpty(bcc)) {
					msg.addRecipients(Message.RecipientType.BCC, getAddresses(bcc));
				}
				if (!ArrayUtils.isEmpty(cc)) {
					msg.addRecipients(Message.RecipientType.CC, getAddresses(cc));
				}
				
				// Asunto
				if (StringUtils.isNotBlank(subject)) {
					msg.setSubject(subject);
				}

				// Contenido
				if (StringUtils.isNotBlank(content)) {
					msg.setText(content);
				}          

				// Adjuntos
				if (!ArrayUtils.isEmpty(attachments)) {
					
					/*Multipart mp = new MimeMultipart();

					MimeBodyPart adjunto =new MimeBodyPart();
					adjunto.setDataHandler(new DataHandler(new FileDataSource(files[i])));
					adjunto.setFileName(new FileDataSource(files[i]).getName());
					mp.addBodyPart(adjunto);*/
					
					MimeMessageHelper helper = new MimeMessageHelper(msg, true);
					
					for (int i = 0; i < attachments.length; i++) {
						helper.addAttachment(attachments[i].getFileName(),
								new ByteArrayResource(attachments[i].getContent()));
					}
					helper.setText(content);
				}
				
				Transport.send(msg);  
				
				Utils.trace("Email enviado con éxito");
				
		} catch (Exception e) {            
			logger.error("Error al enviar el correo electrónico", e);
			throw new MigrationException(e);           
		}
	}
	
	/**
	 * Establece las propiedades de inicio de session de envío de mensaje
	 * @param user - Usuario de conexión
	 * @param serverHost - Servidor SMTP
	 * @param serverPort - Puerto SMTP
	 * @return Properties - Propiedades de session de envio
	 */
	public Properties getProp(String user, String serverHost, String serverPort) {
		Properties props = new Properties();
		props.put("mail.smtp.user", user);        
		props.put("mail.smtp.host", serverHost);        
		props.put("mail.smtp.port", serverPort);        
		props.put("mail.smtp.starttls.enable", "true");        
		props.put("mail.smtp.auth", "true");        
		props.put("mail.smtp.socketFactory.port", serverPort);        
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");        
		props.put("mail.smtp.socketFactory.fallback", "false"); 
		return props;
	}
	
	/**
	 * Clase de autenticación con el servidor SMTP
	 * @author eacuna
	 *
	 */
	private class autentificadorSMTP extends javax.mail.Authenticator {
		/**
		 * Autenticación con usuario y password que se obtienen del fichero de configuración
		 */
		public PasswordAuthentication getPasswordAuthentication() {            
			try {
				return new PasswordAuthentication(Config.getInstance().getEmailUserSMTP(), 
						Config.getInstance().getEmailPasswordSMTP());
			} catch (MigrationException e) {
				e.printStackTrace();
			}
			return null;
		}    
	} 
	
	/**
	 * Recupera las cuentas de direcciones de correo
	 * @param dir - Direcciones de correo electrónico
	 * @return
	 * @throws AddressException - Excepción lanzada en caso de error
	 */
	protected static Address[] getAddresses(String[] dir) throws AddressException {

		Address[] dirA = null;
		
		if (dir != null) {
			dirA = new Address[dir.length];
			for (int i = 0; i < dir.length; i++) {
				dirA[i] = new InternetAddress(dir[i]);
			}
		}

		return dirA;
	}
}
