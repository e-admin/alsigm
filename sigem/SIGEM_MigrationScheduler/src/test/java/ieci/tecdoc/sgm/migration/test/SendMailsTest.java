package ieci.tecdoc.sgm.migration.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Assert;

import junit.framework.TestCase;
import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.email.EmailManager;
import ieci.tecdoc.sgm.migration.exception.MigrationException;

public class SendMailsTest extends TestCase{

	private static boolean ENTORNO_LOCAL = false;
	
	/**
	 * Constructor que establece la variable ENTORNO_LOCAL para ejecutar los test
	 * en función del fichero de configuración. Esto tiene sentido al ejecutar mvn
	 * puesto que para generar el war ejecuta estos TestCase
	 */
	
	public SendMailsTest() {
		try {
			ENTORNO_LOCAL = new Boolean(Config.getInstance().getEntornoLocal()).booleanValue();
		} catch (MigrationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test que ejecuta el envío de correos a través del servidor SMTP
	 * declarado en el fichero de configuración de la aplicación 
	 */
	public void testSendEmail() {
		try {
			EmailManager mgr = new EmailManager();
			String[] emailDestinoArray = {Config.getInstance().getEmailDestino()};
			String asuntoTest = "Asunto de prueba";
			StringBuffer contentTest = new StringBuffer();
			contentTest.append("Contenido de prueba");
			
			if(ENTORNO_LOCAL) {
				mgr.sendMail(Config.getInstance().getEmailOrigen(), 
					emailDestinoArray, null, null,
					asuntoTest, contentTest.toString(), null);
				Assert.assertTrue(true);
			}
		} catch (MigrationException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	// Hotmail:
	/*final String miCorreo = "emilioacunamorgado@hotmail.com";    
	final String miContraseña = "xxxxxx";
	final String servidorSMTP = "smtp.live.com";
	final String puertoEnvio = "25";*/

	// Yahoo:
	/*final String miCorreo = "eamrevi@yahoo.com";    
	final String miContraseña = "xxxxx";
	final String servidorSMTP = "smtp.mail.yahoo.com";
	final String puertoEnvio = "25";*/


	// Gmail
//	final String miCorreo = "eamrevi@gmail.com";    
//	final String miContraseña = "xxxxx";    
//	final String servidorSMTP = "smtp.gmail.com";    
//	final String puertoEnvio = "465";  
//	
//	String mailReceptor = null;    
//	String asunto = null;    
//	String cuerpo = null;    
//	
//	public SendMailsTest(String mailReceptor, String asunto, String cuerpo) {        
//		this.mailReceptor = mailReceptor;        
//		this.asunto = asunto;        
//		this.cuerpo = cuerpo;        
//		Properties props = new Properties();        
//		props.put("mail.smtp.user", miCorreo);        
//		props.put("mail.smtp.host", servidorSMTP);        
//		props.put("mail.smtp.port", puertoEnvio);        
//		props.put("mail.smtp.starttls.enable", "true");        
//		props.put("mail.smtp.auth", "true");        
//		props.put("mail.smtp.socketFactory.port", puertoEnvio);        
//		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");        
//		props.put("mail.smtp.socketFactory.fallback", "false");        
//		//SecurityManager security = System.getSecurityManager();        
//		try {            
//			Authenticator auth = new autentificadorSMTP();            
//			Session session = Session.getInstance(props, auth);            
//			// session.setDebug(true);            
//			MimeMessage msg = new MimeMessage(session);            
//			msg.setText(cuerpo);            
//			msg.setSubject(asunto);            
//			msg.setFrom(new InternetAddress(miCorreo));            
//			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailReceptor));            
//			Transport.send(msg);        
//		} catch (Exception mex) {            
//			mex.printStackTrace();        
//		}    
//	}   
//	
//	public static void main(String[] args) {        
//		// TODO Auto-generated method stub     
//		SendMailsTest test = new SendMailsTest();
//		new SendMailsTest(test.miCorreo, "Este es el asunto de mi correo", "Este es el cuerpo de mi correo");
//		System.out.println("===> OK");
//	}
//	
//	private class autentificadorSMTP extends javax.mail.Authenticator {        
//		public PasswordAuthentication getPasswordAuthentication() {            
//			return new PasswordAuthentication(miCorreo, miContraseña);        
//		}    
//	}
}
