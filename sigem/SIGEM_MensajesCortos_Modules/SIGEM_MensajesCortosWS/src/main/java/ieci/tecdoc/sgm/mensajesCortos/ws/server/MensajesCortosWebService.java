package ieci.tecdoc.sgm.mensajesCortos.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import javax.xml.soap.SOAPException;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;

public class MensajesCortosWebService {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(MensajesCortosWebService.class);

	/** Nombre del servicio. */
	private static final java.lang.String SERVICE_NAME = ConstantesServicios.SERVICE_SHORT_MESSAGES;

	/**
	 * 
	 * @return El servicio de los mensajes Cortos
	 * @throws SOAPException
	 * @throws SigemException
	 */
	private ServicioMensajesCortos getServicioMensajesCortos() throws SOAPException,
			SigemException {

		java.lang.String cImpl = UtilAxis.getImplementacion(MessageContext
				.getCurrentContext());

		if ((cImpl == null) || ("".equals(cImpl))) {
			return LocalizadorServicios.getServicioMensajesCortos();
		} else {
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".")
					.append(cImpl);
			return LocalizadorServicios
					.getServicioMensajesCortos(sbImpl.toString());
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
	 *            Destinatario del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * @return Identificador del mensaje enviado.
	 * 
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String sendCertSMS(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String dst,
			java.lang.String txt, java.lang.String lang) {
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String mtid =  new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String(); 
		try {
		
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			mtid.setValor(servicio.sendCertSMS(user, pwd, src, dst, txt, lang));
			
		} catch (SOAPException e) {
			logger.error("Error al enviar un mensaje certificado.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnError(mtid);
		} catch (Exception e) {
			logger.error("Error al enviar un mensaje certificado.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnError(mtid);
		}
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnOK(mtid);
	}
	
	

	/**
	 * Envía un mensaje SMS a varios destinatarios
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
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaString sendSMS_Multiple(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String[] dst,
			java.lang.String txt) {
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaString lista = new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaString();
		try {
			
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			lista.setArray(servicio.sendSMS(user, pwd, src, dst, txt));
			
		} catch (SOAPException e) {
			logger.error("Error al enviar un mensaje múltiple.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaString) ServiciosUtils.completeReturnError(lista);
		} catch (Exception e) {
			logger.error("Error al enviar un mensaje múltiple", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaString) ServiciosUtils.completeReturnError(lista);
		}
		
		
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaString) ServiciosUtils.completeReturnOK(lista);
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
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero getSMSStatus(java.lang.String user, java.lang.String pwd, java.lang.String id){
		
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero estado =  new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero();
		try {
		
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			estado.setValor(servicio.getSMSStatus(user, pwd, id));
			
		} catch (SOAPException e) {
			logger.error("Error al obtener el estado del mensaje", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero) ServiciosUtils.completeReturnError(estado);
		} catch (Exception e) {
			logger.error("Error al obtener el estado del mensaje.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero) ServiciosUtils.completeReturnError(estado);
		}
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero) ServiciosUtils.completeReturnOK(estado);
		
	}
	

	/**
	 * Envía un mensaje SMS no certificado.
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
	 * 
	 * @return Identificador del mensaje certificado.
	 * 
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String sendSMS(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String dst,
			java.lang.String txt) {
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String mtid = new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String(); 
		try {
		
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			mtid.setValor(servicio.sendSMS(user, pwd, src, dst, txt));
			
		} catch (SOAPException e) {
			logger.error("Error al enviar un mensaje .", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnError(mtid);
		} catch (Exception e) {
			logger.error("Error al enviar un mensaje .", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnError(mtid);
		}
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnOK(mtid);
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
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero getCertSMSSignatureStatus(java.lang.String user, java.lang.String pwd, java.lang.String id){
		
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero estado =  new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero();
		try {
		
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			estado.setValor(servicio.getCertSMSSignatureStatus(user, pwd, id));
			
		} catch (SOAPException e) {
			logger.error("Error al obtener el estado de la firma", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero) ServiciosUtils.completeReturnError(estado);
		} catch (Exception e) {
			logger.error("Error al obtener el estado de la firma.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero) ServiciosUtils.completeReturnError(estado);
		}
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.Entero) ServiciosUtils.completeReturnOK(estado);
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
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String getCertSMSSignatureXML(java.lang.String user, java.lang.String pwd, java.lang.String id) {
		
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String xmlB64 =  new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String(); 
		try {
		
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			xmlB64.setValor(servicio.getCertSMSSignatureXML(user, pwd, id));
			
		} catch (SOAPException e) {
			logger.error("Error al obtener el xml de la firma.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnError(xmlB64);
		} catch (Exception e) {
			logger.error("Error al obtener el xml de la firma.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnError(xmlB64);
		}
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.String) ServiciosUtils.completeReturnOK(xmlB64);
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
	 */
	public ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaBytes getCertSMSSignatureDocument(java.lang.String user, java.lang.String pwd, java.lang.String id){
		
		ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaBytes lista = new ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaBytes();
		try {
			
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			lista.setBytes(servicio.getCertSMSSignatureDocument(user, pwd, id));
			
		} catch (SOAPException e) {
			logger.error("Error al obtener el documento firmado.", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaBytes) ServiciosUtils.completeReturnError(lista);
		} catch (Exception e) {
			logger.error("Error al obtener el documento firmado", e);
			return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaBytes) ServiciosUtils.completeReturnError(lista);
		}
		
		
		return (ieci.tecdoc.sgm.mensajesCortos.ws.server.dto.ListaBytes) ServiciosUtils.completeReturnOK(lista);	
		
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
	 */
	public RetornoServicio sendMail(java.lang.String from, java.lang.String[] to, java.lang.String[] cc, java.lang.String[] bcc, java.lang.String subject,
			java.lang.String content, Attachment[] attachments) {
		
		RetornoServicio ret = new ieci.tecdoc.sgm.core.services.dto.RetornoServicio();
		try {
			
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			servicio.sendMail(from, to, cc, bcc, subject, content, attachments);
			
		} catch (SOAPException e) {
			logger.error("Error al enviar el mail.", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		} catch (Exception e) {
			logger.error("Error al enviar el mail", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		}
		
		return (RetornoServicio) ServiciosUtils.completeReturnOK(ret);	
		
	}
	



	

}
