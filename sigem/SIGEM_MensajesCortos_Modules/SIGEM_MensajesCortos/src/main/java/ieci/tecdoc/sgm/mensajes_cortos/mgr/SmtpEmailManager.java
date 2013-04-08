package ieci.tecdoc.sgm.mensajes_cortos.mgr;

import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SmtpEmailManager implements EmailManager {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SmtpEmailManager.class);
	
	private String user = null;
	private String password = null;
	private String serverHost = null;
	private String serverPort = null;

	
	/**
	 * Constructor.
	 */
	public SmtpEmailManager() {
		super();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}


	/**
	 * Envía un correo electrónico.
	 * 
	 * @param from
	 *            Dirección de correo electrónico del remitente.
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
	public void sendMail(String from, String[] to, String[] cc, String[] bcc,
			String subject, String content, Attachment[] attachments)
			throws MensajesCortosException {

		try {

			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost(serverHost);

			if (StringUtils.isNotBlank(serverPort)) {
				sender.setPort(Integer.valueOf(serverPort));
			}
			
			if (StringUtils.isNotBlank(user)) {
				sender.setUsername(user);
			}
			
			if (StringUtils.isNotBlank(password)) {
				sender.setPassword(password);
			}
			
			MimeMessage msg = new MimeMessage(getSession(user, password, serverHost, serverPort));

			// Email de origen
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
				
				MimeMessageHelper helper = new MimeMessageHelper(msg, true);
				
				for (int i = 0; i < attachments.length; i++) {
					helper.addAttachment(attachments[i].getFileName(),
							new ByteArrayResource(attachments[i].getContent()));
				}
				
				helper.setText(content);
			}
			
			sender.send(msg);
			
		} catch (Throwable e) {
			logger.error("Error al enviar el correo electrónico", e);
			throw new MensajesCortosException(e);
		}
	}

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
	
	protected static Session getSession(String user, String password, String serverHost, String serverPort) {

		// Autenticación de usuario/password en el servidor de correo
		PasswordAuthentication authentication = new PasswordAuthentication(user, password);

		// Parámetros de configuración
		Properties properties = new Properties();

		// Autenticación
		properties.put("mail.smtp.submitter", authentication);

		properties.put("mail.smtp.user", user);

		properties.put("mail.smtp.auth", "true");

		// Servidor de correo
		properties.put("mail.smtp.host", serverHost);
		properties.put("mail.smtp.port", String.valueOf(serverPort));

		// Protocolos
		properties.put("mail.transport.protocol", "smtp");

		return Session.getInstance(properties);
	}

}
