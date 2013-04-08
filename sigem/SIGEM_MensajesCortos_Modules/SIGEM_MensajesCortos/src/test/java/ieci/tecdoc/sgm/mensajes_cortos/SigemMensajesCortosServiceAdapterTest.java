package ieci.tecdoc.sgm.mensajes_cortos;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;

import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class SigemMensajesCortosServiceAdapterTest extends TestCase {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SigemMensajesCortosServiceAdapterTest.class);
	
	
	public void testServicio(){
		
		logger.info("--- Entrada en testServicio ---");
		
		try {
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			logger.info("Servicio de mensajes cortos: " + servicio);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testServicio", e);
		}
		
		logger.info("--- Salida de testServicio ---");
	}
	
	
	public void testSendCertSMS(){
		logger.info("--- Entrada en testSendCertificateSMS ---");
		
		try {
			
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			String mtid=servicio.sendCertSMS("ieci1@madrid", "ieci1", "", "+34600000000", "+34600000000", "ES");
			System.out.println(mtid);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de testSendCertificateSMS ---");
		
	}
	
	public void testGetCertSMSSignatureDocument(){
		logger.info("--- Entrada en testGetCertSMSSignatureDocument ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			byte[] sign=servicio.getCertSMSSignatureDocument("ieci1@madrid", "ieci1", "N9694A300EC311DEB958A6F0CEA2E86A:+34600000000");
			System.out.println("bytes"+sign);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de getCertSMSSignatureDocument ---");
		
	}
	
	public void testGetCertSMSSignatureStatus(){
		
		logger.info("--- Entrada en testGgetCertSMSSignatureStatus ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
				int res= servicio.getCertSMSSignatureStatus("ieci1@madrid", "ieci1", "N9694A300EC311DEB958A6F0CEA2E86A:+34600000000");
			System.out.println("Estado"+res);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de getCertSMSSignatureStatus ---");
		
	}
	
	
	public void testGetCertSMSSignatureXML(){
		
		logger.info("--- Entrada en testGgetCertSMSSignatureXMl ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			String b64=servicio.getCertSMSSignatureXML("ieci1@madrid", "ieci1", "N9694A300EC311DEB958A6F0CEA2E86A:++34600000000");
			System.out.println("Base64"+b64);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de getCertSMSSignatureXML ---");
		
	}

//ok
	public void testSendSms(){
		
		logger.info("--- Entrada en testSendSms ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			
			servicio.sendSMS("ieci1@madrid", "ieci1", "", "+34600000000", "probando");
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de sendSMS ---");
		
	}


public void getSendSMSMultiple(){
		
		logger.info("--- Entrada en getSendSMSMultiple ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			
			String dst [] = new String [2];
			dst[0]= "+34600000000";
			dst[1]="+34600000001";
			servicio.sendSMS("ieci1@madrid","ieci1", "+34600000000",dst , "multiple");
		
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de getSendSMSMultiple ---");
		
	}

	public void getSMSStatus(){
		
		logger.info("--- Entrada en getSMSStatus ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			
			int estado=servicio.getSMSStatus("ieci1@madrid", "ieci1", "N9694A300EC311DEB958A6F0CEA2E86A:+34600000000");
			System.out.println("estado"+estado);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendSMS", e);
		}
		
		logger.info("--- Salida de getSmsStatus ---");
		
	}
//ok
	public void testSendMail(){
		
		logger.info("--- Entrada en testSendMail ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			String from = "sigem@localhost";
			String [] to = new String[] { "sigem@localhost" };
			String [] cc = new String[] { "sigem@localhost" };
			String [] bcc = new String[] { "sigem@localhost" };
			
			servicio.sendMail(from, to, cc,bcc, "hola", "probando mensaje sin adjuntos", null);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendMail", e);
		}
		
		logger.info("--- Salida de testSendMail ---");
		
	}

	public void testSendMailAttachments(){
		
		logger.info("--- Entrada en testSendMailAttachments ---");
		
		try {
			
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			
			String from = "sigem@localhost";
			String [] to = new String[] { "sigem@localhost" };
			String [] cc = new String[] { "sigem@localhost" };
			String [] bcc = new String[] { "sigem@localhost" };
			
			Attachment [] attachs = new Attachment[] {
					new Attachment("ejemplo.pdf", getFileBytes("C:\\temp\\ejemplo.pdf")),
					new Attachment("ejemplo.txt", getFileBytes("C:\\temp\\ejemplo.txt"))
					
			};
			
			servicio.sendMail(from, to, cc,bcc, "hola", "probando mensaje con adjuntos", attachs);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			logger.info("Error en testSendMailAttachments", e);
		}
		
		logger.info("--- Salida de testSendMailAttachments ---");
		
	}

	private static byte[] getFileBytes(String fileName) {
		
		byte[] data = null;
		
		try {
			InputStream input = new FileInputStream(fileName);
			data = new byte[input.available()];
			input.read(data);
			input.close();
		} catch (Exception e) {
			logger.error("Error al obtener los bytes del fichero: " + fileName, e);
		}
		
		return data;
	}
}
