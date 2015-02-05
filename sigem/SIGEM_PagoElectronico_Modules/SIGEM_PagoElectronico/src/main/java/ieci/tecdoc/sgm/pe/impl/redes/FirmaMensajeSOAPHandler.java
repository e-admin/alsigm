package ieci.tecdoc.sgm.pe.impl.redes;
/*
 * $Id: FirmaMensajeSOAPHandler.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.soap.MessageFactory;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.utils.XMLUtils;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;

public class FirmaMensajeSOAPHandler implements Handler {

	/**
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger
			.getLogger(FirmaMensajeSOAPHandler.class);

	public void destroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("METODO DESTROY");
		}
	}

	public QName[] getHeaders() {
		if (logger.isDebugEnabled()) {
			logger.debug("METODO GETHEADERS");
		}
		return null;
	}

	public boolean handleFault(MessageContext arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("METODO HANDLEFAULT");
		}
		return false;
	}

	/**
	 * Método que se ejecuta justo antes del envío del mensaje SOAP al servidor.
	 * Se encarga de firmar el cuerpo del mensaje con la firma configurada.
	 * 
	 * @param MessageContext
	 *            Contexto del mensaje SOAP.
	 */
	public boolean handleRequest(MessageContext arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("Firmando mensaje soap...");
		}
		Message mensaje = ((org.apache.axis.MessageContext) arg0)
				.getRequestMessage();
		SOAPEnvelope sobre = null;
		Document doc = null;
		WSSecHeader cabecera = new WSSecHeader();
		cabecera.setMustUnderstand(false);
		WSSecSignature firma = new WSSecSignature();

		String alias = Configuracion.obtenerPropiedad(
				Configuracion.KEY_FIRMA_ALIAS);
		String password = Configuracion.obtenerPropiedad(
				Configuracion.KEY_FIRMA_PASS);
		if (logger.isDebugEnabled()) {
			StringBuffer sbMensaje = new StringBuffer(
					"Configuración de firma electrónica: ");
			sbMensaje.append("ALIAS: ").append(alias);
			sbMensaje.append("PASSWORD").append(password);
			logger.debug(sbMensaje.toString());
			logger.debug("Accediendo al almacén de certificados...");
		}
		firma.setUserInfo(alias, password);
		firma.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
		
		Crypto crypto = CryptoFactory.getInstance(Configuracion.getCrypto());
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Obteniendo mensaje SOAP...");
			}
			sobre = mensaje.getSOAPEnvelope();
			doc = sobre.getAsDocument();
			cabecera.insertSecurityHeader(doc);
			if (logger.isDebugEnabled()) {
				logger.debug("Firmando mansaje SOAP...");
			}
			Document signedDoc = firma.build(doc, crypto, cabecera);
			Canonicalizer c14n = Canonicalizer
					.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS);
			byte[] canonicalMessage = c14n.canonicalizeSubtree(signedDoc);
			ByteArrayInputStream in = new ByteArrayInputStream(canonicalMessage);
			//MessageFactory factory = MessageFactory.newInstance();	No support JDK6
			MessageFactory factory=new org.apache.axis.soap.MessageFactoryImpl();
			Message resultado = (org.apache.axis.Message) factory
					.createMessage(null, in);

			if (logger.isDebugEnabled()) {
				logger.debug("Mensaje SOAP firmado:");
				ByteArrayOutputStream obaos = new ByteArrayOutputStream();
				XMLUtils.ElementToWriter(
						resultado.getSOAPEnvelope().getAsDOM(),
						new PrintWriter(obaos));
				logger.debug(obaos.toString());
			}
			
			// Establecemos el mensaje firmado como el mensaje a enviar
			((org.apache.axis.MessageContext) arg0)
					.setCurrentMessage(resultado);
		} catch (AxisFault e) {
			logger.error("Error de Axis durante firma de mensaje", e);
			return false;
		} catch (Exception e) {
			logger.error("Error no esperado durante firma de mansaje", e);
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Fin firma mensaje SOAP.");
		}
		return true;
	}

	public boolean handleResponse(MessageContext arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("METODO HANDLERESPONSE");
		}
		return false;
	}

	public void init(HandlerInfo arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("METODO INIT");
		}
	}

}
