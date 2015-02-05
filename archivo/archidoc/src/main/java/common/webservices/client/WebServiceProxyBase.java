package common.webservices.client;

import java.util.List;
import java.util.Vector;

import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.apache.soap.Header;
import org.apache.soap.util.xml.QName;

import util.CollectionUtils;

import common.util.StringUtils;
import common.util.XmlFacade;

/**
 * Clase base para los proxys de servicios web.
 */
public class WebServiceProxyBase {

	/** Definición del servicio web. */
	private Definition m_definition = null;

	/** Información del puerto del servicio web. */
	private Port m_port = null;

	/** URL para el envío SOAP. */
	private String m_soapURL = null;

	/** Nombre del usuario. */
	private String m_username = null;

	/** Clave del usuario. */
	private String m_password = null;

	/**
	 * Constructor.
	 * 
	 * @param wsdlURL
	 *            URL del WSDL del servicio web.
	 * @param serviceName
	 *            Nombre del servicio.
	 * @param portName
	 *            Nombre del puerto.
	 * @throws WSDLException
	 *             si ocurre algún error.
	 */
	public WebServiceProxyBase(String wsdlURL, String serviceName,
			String portName) throws WSDLException {
		m_definition = getDefinition(wsdlURL);
		m_soapURL = getSoapURL(serviceName, portName);
	}

	/**
	 * Obtiene la definición de un servicio web.
	 * 
	 * @param wsdlURL
	 *            URL del WSDL del servicio web.
	 * @return Definición del servicio web.
	 * @throws WSDLException
	 *             si ocurre algún error.
	 */
	private Definition getDefinition(String wsdlURL) throws WSDLException {
		WSDLFactory factory = WSDLFactory.newInstance();
		WSDLReader reader = factory.newWSDLReader();
		return reader.readWSDL(wsdlURL);
	}

	/**
	 * Obtiene la URL para enviar el mensaje SOAP.
	 * 
	 * @param serviceName
	 *            Nombre del servicio.
	 * @param portName
	 *            Nombre del puerto.
	 * @return URL para el envío SOAP.
	 */
	private String getSoapURL(String serviceName, String portName) {
		String soapURL = null;

		Service service = m_definition
				.getService(new javax.xml.namespace.QName(m_definition
						.getTargetNamespace(), serviceName));
		if (service != null) {
			m_port = service.getPort(portName);
			if (m_port != null) {
				List extElements = m_port.getExtensibilityElements();
				if (!CollectionUtils.isEmpty(extElements)) {
					Object extElement;
					for (int i = 0; (soapURL == null)
							&& (i < extElements.size()); i++) {
						extElement = extElements.get(i);
						if (extElement instanceof SOAPAddress)
							soapURL = ((SOAPAddress) extElement)
									.getLocationURI();
					}
				}
			}
		}

		return soapURL;
	}

	/**
	 * Obtiene la información de la operación.
	 * 
	 * @param operationName
	 *            Nombre de la
	 * @return Información de la operación.
	 */
	protected BindingOperation getBindingOperation(String operationName) {
		BindingOperation operation = null;

		if (m_port != null)
			operation = m_port.getBinding().getBindingOperation(operationName,
					null, null);

		return operation;
	}

	/**
	 * Obtiene el valor de la acción SOAP.
	 * 
	 * @param operation
	 *            Información de la operación.
	 * @return Valor de la acción SOAP.
	 */
	protected String getSoapActionURI(BindingOperation operation) {
		String soapActionURI = null;

		if (operation != null) {
			List extElements = operation.getExtensibilityElements();
			if (!CollectionUtils.isEmpty(extElements)) {
				Object extElement;
				for (int i = 0; (soapActionURI == null)
						&& (i < extElements.size()); i++) {
					extElement = extElements.get(i);
					if (extElement instanceof SOAPOperation)
						soapActionURI = ((SOAPOperation) extElement)
								.getSoapActionURI();
				}
			}
		}

		return soapActionURI;
	}

	/**
	 * Obtiene la información del cuerpo SOAP de la operación.
	 * 
	 * @param operation
	 *            Información de la operación.
	 * @return Información del cuerpo SOAP de la operación.
	 */
	protected SOAPBody getSOAPBody(BindingOperation operation) {
		SOAPBody soapBody = null;

		if (operation != null) {
			List extElements = operation.getBindingInput()
					.getExtensibilityElements();
			if (!CollectionUtils.isEmpty(extElements)) {
				Object extElement;
				for (int i = 0; (soapBody == null) && (i < extElements.size()); i++) {
					extElement = extElements.get(i);
					if (extElement instanceof SOAPBody)
						soapBody = (SOAPBody) extElement;
				}
			}
		}

		return soapBody;
	}

	/**
	 * Obtiene el valor de codificación SOAP.
	 * 
	 * @param soapBody
	 *            Información del cuerpo SOAP de la operación.
	 * @return Valor de codificación SOAP.
	 */
	protected String getEncodingStyleURI(SOAPBody soapBody) {
		String encodingStyleURI = null;

		if (soapBody != null) {
			List encodingStyles = soapBody.getEncodingStyles();
			if (!CollectionUtils.isEmpty(encodingStyles))
				encodingStyleURI = (String) encodingStyles.toArray()[0];
		}

		return encodingStyleURI;
	}

	/**
	 * Obtiene el contenido de la cabecera SOAP.
	 * 
	 * @return Cabecera SOAP.
	 */
	protected Header getHeader() {
		Header header = null;

		if (StringUtils.isNotBlank(m_username)
				|| StringUtils.isNotBlank(m_password)) {
			XmlFacade xml = new XmlFacade(getWSSecurityXML(m_username,
					m_password));
			Vector headerEntries = new Vector();
			headerEntries.add(xml.getDocument().getDocumentElement());

			header = new Header();
			header.setHeaderEntries(headerEntries);
		}

		return header;
	}

	/**
	 * Obtiene el XML del WS-Security.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @param password
	 *            Clave del usuario.
	 * @return XML WS-Security.
	 */
	protected String getWSSecurityXML(String username, String password) {
		return new StringBuffer()
				.append("<wsse:Security")
				.append(" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"")
				.append(" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"")
				.append(" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"")
				.append(" env:mustUnderstand=\"1\">")
				.append("<wsse:UsernameToken")
				.append(" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"")
				.append(" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"")
				.append(" wsu:Id=\"5sRcKP03F1qLikSW1CVmTg22\"")
				.append(" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">")
				.append("<wsse:Username>")
				.append(StringUtils.isNotBlank(username) ? username : "")
				.append("</wsse:Username>").append("<wsse:Password>")
				.append(StringUtils.isNotBlank(password) ? password : "")
				.append("</wsse:Password>").append("</wsse:UsernameToken>")
				.append("</wsse:Security>").toString();
	}

	/**
	 * Obtiene el nombre cualificado del objeto de retorno.
	 * 
	 * @return Nombre cualificado.
	 */
	protected QName getOperationReturnQName(String operationName) {
		QName qname = null;

		BindingOperation operation = getBindingOperation(operationName);
		if (operation != null) {
			Part returnPart = operation.getOperation().getOutput().getMessage()
					.getPart("return");
			if (returnPart != null) {
				javax.xml.namespace.QName typeName = returnPart.getTypeName();
				qname = new QName(typeName.getNamespaceURI(),
						typeName.getLocalPart());
			}
		}

		return qname;
	}

	/**
	 * Obtiene la clave del usuario.
	 * 
	 * @return Clave del usuario.
	 */
	public String getPassword() {
		return m_password;
	}

	/**
	 * Establece la clave del usuario.
	 * 
	 * @param password
	 *            Clave del usuario.
	 */
	public void setPassword(String password) {
		m_password = password;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return Nombre del usuario.
	 */
	public String getUsername() {
		return m_username;
	}

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 */
	public void setUsername(String username) {
		m_username = username;
	}

	/**
	 * Obtiene la definición del servicio web.
	 * 
	 * @return Definición del servicio web.
	 */
	public Definition getDefinition() {
		return m_definition;
	}

	/**
	 * Establece la definición del servicio web.
	 * 
	 * @param definition
	 *            Definición del servicio web.
	 */
	public void setDefinition(Definition definition) {
		m_definition = definition;
	}

	/**
	 * Obtiene la URL para el envío SOAP.
	 * 
	 * @return URL para el envío SOAP.
	 */
	public String getSoapURL() {
		return m_soapURL;
	}

	/**
	 * Establece la URL para el envío SOAP.
	 * 
	 * @param soapurl
	 *            URL para el envío SOAP.
	 */
	public void setSoapURL(String soapurl) {
		m_soapURL = soapurl;
	}
}
