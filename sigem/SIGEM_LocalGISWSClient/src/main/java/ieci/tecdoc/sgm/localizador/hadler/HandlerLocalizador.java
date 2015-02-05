package ieci.tecdoc.sgm.localizador.hadler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Properties;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.soap.MessageFactoryImpl;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecUsernameToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Clase encargada de securizar los mensajes SOAP de petición realizados desde un cliente.
 * @author SEPAOT
 *
 */

public class HandlerLocalizador extends BasicHandler {
	
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(HandlerLocalizador.class);
    
    // Opciones de seguridad
    // Seguridad UserNameToken
    public static final String USERNAMEOPTION = WSConstants.USERNAME_TOKEN_LN;
    // Seguridad BinarySecurityToken
    public static final String CERTIFICATEOPTION = WSConstants.BINARY_TOKEN_LN;
    // Sin seguridad
    public static final String NONEOPTION = "None";

    // Opción de seguridad del objeto actual
    private String securityOption = null;
    // Usuario para el token de seguridad UserNameToken.
    private String userTokenUserName = null;
    // Password para el token de seguridad UserNameToken
    private String userTokenUserPassword = null;
    // Tipo de password para el UserNameTokenPassword
    private String userTokenUserPasswordType = null;
    // Localización del keystore con certificado y clave privada de usuario
    private String keystoreLocation = null;
    // Tipo de keystore
    private String keystoreType = null;
    // Clave del keystore
    private String keystorePassword = null;
    // Alias del certificado usado para firmar el tag soapBody de la petición y que será alojado en el token BinarySecurityToken
    private String keystoreCertAlias = null;
    // Password del certificado usado para firmar el tag soapBody de la petición y que será alojado en el token BinarySecurityToken
    private String keystoreCertPassword = null;

    
    /**
     * Constructor que inicializa el atributo securityOption
     *
     * @param securityOption opción de seguridad.
     * @throws Exception
     */
	public HandlerLocalizador(String mode, String user, String pass,
			String passType) {
		
		securityOption = mode;
		userTokenUserName = user;
		userTokenUserPassword = pass;
		
		if ("PasswordText".equals(passType)) {
			userTokenUserPasswordType = WSConstants.PASSWORD_TEXT;
		} else if ("PasswordDigest".equals(passType)) {
			userTokenUserPasswordType = WSConstants.PASSWORD_DIGEST;
		}

		if (!securityOption.equals(USERNAMEOPTION)
				&& !securityOption.equals(CERTIFICATEOPTION)
				&& !securityOption.equals(NONEOPTION)) {
			logger.error("Opcion de seguridad no valida: " + securityOption);
		}
	}
    
	public void invoke(MessageContext msgContext) throws AxisFault {
		
		SOAPMessage msg, secMsg;
		Document doc = null;
		secMsg = null;
		
		try {
			
			// Obtención del documento XML que representa la petición SOAP
			msg = msgContext.getCurrentMessage();
			doc = ((org.apache.axis.message.SOAPEnvelope) msg.getSOAPPart()
					.getEnvelope()).getAsDocument();
			
			// Securización de la petición SOAP según la opcion de seguridad
			// configurada
			if (this.securityOption.equals(USERNAMEOPTION)) {
				secMsg = this.createUserNameToken(doc);
			} else if (this.securityOption.equals(CERTIFICATEOPTION)) {
				secMsg = this.createBinarySecurityToken(doc);
			} else {
				secMsg = msg;
			}
			
			// Modificación de la petición SOAP
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart())
					.setCurrentMessage(secMsg.getSOAPPart().getEnvelope(),
							SOAPPart.FORM_SOAPENVELOPE);
			
		} catch (AxisFault e) {
			logger.error("Error al invocar al servicio", e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al invocar al servicio", t);
			throw new AxisFault(t.getMessage(), t);
		}
	}

    /**
     * Securiza, mediante el tag userNameToken, una petición SOAP no securizada.
     *
     * @param soapRequest Documento xml que representa la petición SOAP sin securizar.
     * @return Un mensaje SOAP que contiene la petición SOAP de entrada securizada
     * mediante el tag userNameToken.
     * @throws Exception 
     */
	private SOAPMessage createUserNameToken(Document soapEnvelopeRequest)
			throws Exception {
		
		ByteArrayOutputStream baos;
		Document secSOAPReqDoc;
		DOMSource source;
		Element element;
		StreamResult streamResult;
		String secSOAPReq;
		WSSecUsernameToken wsSecUsernameToken;
		WSSecHeader wsSecHeader;
		
		try {
			// Inserción del tag wsse:Security y userNameToken
			wsSecHeader = new WSSecHeader(null, false);
			wsSecUsernameToken = new WSSecUsernameToken();
			wsSecUsernameToken.setPasswordType(this.userTokenUserPasswordType);
			wsSecUsernameToken.setUserInfo(this.userTokenUserName,
					this.userTokenUserPassword);
			wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
			wsSecUsernameToken.prepare(soapEnvelopeRequest);
			
			
			// Añadimos una marca de tiempo inidicando la fecha de creación del
			// tag
			wsSecUsernameToken.addCreated();
			wsSecUsernameToken.addNonce();
			
			// Modificación de la petición
			secSOAPReqDoc = wsSecUsernameToken.build(soapEnvelopeRequest,
					wsSecHeader);
			element = secSOAPReqDoc.getDocumentElement();
			
			// Transformación del elemento DOM a String
			source = new DOMSource(element);
			baos = new ByteArrayOutputStream();
			streamResult = new StreamResult(baos);
			TransformerFactory.newInstance().newTransformer()
					.transform(source, streamResult);
			secSOAPReq = new String(baos.toByteArray());
			
			// Creación de un nuevo mensaje SOAP a partir del mensaje SOAP
			// securizado formado
//			res = MessageFactory.newInstance().createMessage(null,
//					new ByteArrayInputStream(secSOAPReq.getBytes()));
			// Corrección para Java6 al no retornar la MessageFactoryImpl de
			// Axis 1.x, así que se crea directamente
			return new MessageFactoryImpl().createMessage(null,
					new ByteArrayInputStream(secSOAPReq.getBytes()));
			
		} catch (Exception e) {
		    logger.error("Error al crear el UserNameToken", e);
		    throw e;
		}
	}

    /**
     * Securiza, mediante el tag BinarySecurityToken y firma una petición SOAP no securizada.
     *
     * @param soapEnvelopeRequest Documento xml que representa la petición SOAP sin securizar.
     * @return Un mensaje SOAP que contiene la petición SOAP de entrada securizada
     * mediante el tag BinarySecurityToken.
     * @throws Exception 
     */
	private SOAPMessage createBinarySecurityToken(Document soapEnvelopeRequest)
			throws Exception {
		
		ByteArrayOutputStream baos;
		Crypto crypto;
		Document secSOAPReqDoc;
		DOMSource source;
		Element element;
		StreamResult streamResult;
		String secSOAPReq;
		WSSecSignature wsSecSignature;
		WSSecHeader wsSecHeader;
		
		try {
			// Inserción del tag wsse:Security y BinarySecurityToken
			wsSecHeader = new WSSecHeader(null, false);
			wsSecSignature = new WSSecSignature();
			crypto = CryptoFactory.getInstance(
					"org.apache.ws.security.components.crypto.Merlin",
					this.initializateCryptoProperties());
			
			// Indicación para que inserte el tag BinarySecurityToken
			wsSecSignature
					.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
			
			// wsSecSignature.setKeyIdentifierType(WSConstants.ISSUER_SERIAL);
			wsSecSignature.setUserInfo(this.keystoreCertAlias,
					this.keystoreCertPassword);
			wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
			wsSecSignature.prepare(soapEnvelopeRequest, crypto, wsSecHeader);
			
			// Modificación y firma de la petición
			secSOAPReqDoc = wsSecSignature.build(soapEnvelopeRequest, crypto,
					wsSecHeader);
			element = secSOAPReqDoc.getDocumentElement();
			
			// Transformación del elemento DOM a String
			source = new DOMSource(element);
			baos = new ByteArrayOutputStream();
			streamResult = new StreamResult(baos);
			TransformerFactory.newInstance().newTransformer()
					.transform(source, streamResult);
			secSOAPReq = new String(baos.toByteArray());
			
			// Creación de un nuevo mensaje SOAP a partir del mensaje SOAP
			// securizado formado
//			res = MessageFactory.newInstance().createMessage(null,
//					new ByteArrayInputStream(secSOAPReq.getBytes()));
			// Corrección para Java6 al no retornar la MessageFactoryImpl de
			// Axis 1.x, así que se crea directamente
			return new MessageFactoryImpl().createMessage(null,
					new ByteArrayInputStream(secSOAPReq.getBytes()));

		} catch (Exception e) {
		    logger.error("Error al crear el BinarySecurityToken", e);
		    throw e;
		}
	}

    /**
     * Establece el conjunto de propiedades con el que será inicializado el gestor criptográfico de WSS4J.
     * @return Devuelve el conjunto de propiedades con el que será inicializado el gestor criptográfico de
     WSS4J.
    */
	@SuppressWarnings("rawtypes")
	private Map initializateCryptoProperties() {
		Properties res = new Properties();
		res.setProperty("org.apache.ws.security.crypto.provider",
				"org.apache.ws.security.components.crypto.Merlin");
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.type",
				this.keystoreType);
		res.setProperty(
				"org.apache.ws.security.crypto.merlin.keystore.password",
				this.keystorePassword);
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias",
				this.keystoreCertAlias);
		res.setProperty("org.apache.ws.security.crypto.merlin.alias.password",
				this.keystoreCertPassword);
		res.setProperty("org.apache.ws.security.crypto.merlin.file",
				this.keystoreLocation);

		return res;
	}
}
