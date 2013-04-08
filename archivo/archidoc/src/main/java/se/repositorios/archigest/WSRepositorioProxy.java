package se.repositorios.archigest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.wsdl.BindingOperation;
import javax.wsdl.Part;
import javax.wsdl.extensions.soap.SOAPBody;

import oracle.soap.encoding.soapenc.EncUtils;
import oracle.soap.transport.http.OracleSOAPHTTPConnection;

import org.apache.log4j.Logger;
import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.QName;

import common.webservices.client.WebServiceProxyBase;

/**
 * Proxy de acceso a los servicios web de los repositorios de los depósitos
 * electrónicos.
 */
public class WSRepositorioProxy extends WebServiceProxyBase {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(WSRepositorioProxy.class);

	private OracleSOAPHTTPConnection m_httpConnection = null;

	private SOAPMappingRegistry m_soapMappingRegistry = null;

	private HashMap m_soapMappingRegistries = new HashMap();

	/** Nombre del servicio por defecto. */
	private static final String DEFAULT_SERVICE_NAME = "WSRepositorio";

	/** Nombre del puerto por defecto. */
	private static final String DEFAULT_PORT_NAME = "WSRepositorioPort";

	/**
	 * Constructor.
	 * 
	 * @param wsdlURL
	 *            URL del WSDL del servicio web.
	 */
	public WSRepositorioProxy(String wsdlURL) throws Exception {
		super(wsdlURL, DEFAULT_SERVICE_NAME, DEFAULT_PORT_NAME);

		m_httpConnection = new OracleSOAPHTTPConnection();
		_setMaintainSession(true);
		m_soapMappingRegistry = new SOAPMappingRegistry();

		QName InfoOcupacionQName = getOperationReturnQName("getInfoOcupacion");
		QName InfoFicheroQName = getOperationReturnQName("getInfoFichero");
		BeanSerializer beanSer = new BeanSerializer();

		m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC,
				InfoOcupacionQName, InfoOcupacionVO.class, beanSer, beanSer);
		m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC,
				InfoFicheroQName, InfoFicheroVO.class, beanSer, beanSer);
		m_soapMappingRegistry
				.mapTypes(
						Constants.NS_URI_SOAP_ENC,
						new QName(
								"http://es.archigest.custodiaSpigaWS.web.services/WSRepositorio.xsd",
								"es_archigest_custodiaSpigaWS_business_vo_FirmaVO"),
						FirmaVO.class, beanSer, beanSer);

		Object untypedParams[] = {
				new String("getInfoOcupacion"),
				new String("return"),
				InfoOcupacionQName,
				new String("getInfoOcupacion"),
				new String("espacioOcupado"),
				new QName("http://www.w3.org/2001/XMLSchema", "long"),
				new String("getInfoOcupacion"),
				new String("espacioTotal"),
				new QName("http://www.w3.org/2001/XMLSchema", "long"),
				new String("getInfoOcupacion"),
				new String("numeroFicheros"),
				new QName("http://www.w3.org/2001/XMLSchema", "long"),
				new String("getInfoFichero"),
				new String("return"),
				InfoFicheroQName,
				new String("getInfoFichero"),
				new String("fechaAlta"),
				new QName("http://www.w3.org/2001/XMLSchema", "dateTime"),
				new String("getInfoFichero"),
				new String("firmas"),
				new QName(
						"http://es.archigest.custodiaSpigaWS.web.services/WSRepositorio.xsd",
						"ArrayOfEs_archigest_custodiaSpigaWS_business_vo_FirmaVO"),
				new String("getInfoFichero"), new String("nombre"),
				new QName("http://www.w3.org/2001/XMLSchema", "string"),
				new String("getFichero"), new String("return"),
				new QName("http://www.w3.org/2001/XMLSchema", "base64Binary") };

		String operationName;
		String paramName;
		QName returnType;
		SOAPMappingRegistry registry;
		Deserializer deserializer;
		int x;
		for (x = 0; x < untypedParams.length; x += 3) {
			operationName = (String) untypedParams[x];
			paramName = (String) untypedParams[x + 1];
			returnType = (QName) untypedParams[x + 2];

			registry = (SOAPMappingRegistry) m_soapMappingRegistries
					.get(operationName);
			if (registry == null) {
				if (m_soapMappingRegistry != null)
					registry = new SOAPMappingRegistry(m_soapMappingRegistry);
				else
					registry = new SOAPMappingRegistry();

				m_soapMappingRegistries.put(operationName, registry);
			}

			try {
				deserializer = registry.queryDeserializer(returnType,
						Constants.NS_URI_SOAP_ENC);
				registry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("",
						paramName), null, null, deserializer);
			} catch (IllegalArgumentException e) {
			}
		}
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * 
	 * @return Información de ocupación.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public InfoOcupacionVO getInfoOcupacion() throws Exception {
		BindingOperation operation = getBindingOperation("getInfoOcupacion");
		String soapActionURI = getSoapActionURI(operation);
		SOAPBody soapBody = getSOAPBody(operation);
		String encodingStyleURI = getEncodingStyleURI(soapBody);

		Vector params = new Vector();
		Response response = makeSOAPCallRPC(operation.getName(), params,
				encodingStyleURI, soapBody.getNamespaceURI(), soapActionURI);
		Parameter returnValue = response.getReturnValue();
		return (InfoOcupacionVO) returnValue.getValue();

	}

	public InfoFicheroVO getInfoFichero(String id) throws Exception {
		BindingOperation operation = getBindingOperation("getInfoFichero");
		String soapActionURI = getSoapActionURI(operation);
		SOAPBody soapBody = getSOAPBody(operation);
		String encodingStyleURI = getEncodingStyleURI(soapBody);

		Vector params = new Vector();
		params.add(new Parameter("param0", String.class, id, null));
		Response response = makeSOAPCallRPC(operation.getName(), params,
				encodingStyleURI, soapBody.getNamespaceURI(), soapActionURI);
		Parameter returnValue = response.getReturnValue();
		return (InfoFicheroVO) returnValue.getValue();
	}

	/**
	 * Obtiene el contenido de un fichero.
	 * 
	 * @param localizador
	 *            Localizador del fichero.
	 * @return Contenido del fichero.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public Byte[] getFichero(String localizador) throws Exception {
		BindingOperation operation = getBindingOperation("getFichero");
		String soapActionURI = getSoapActionURI(operation);
		SOAPBody soapBody = getSOAPBody(operation);
		String encodingStyleURI = getEncodingStyleURI(soapBody);

		String paramName = null;
		Map parts = operation.getOperation().getInput().getMessage().getParts();
		if (!parts.isEmpty()) {
			Part part = (Part) parts.values().iterator().next();
			paramName = part.getName();
		}

		Vector params = new Vector();
		params.add(new Parameter(paramName, String.class, localizador, null));
		Response response = makeSOAPCallRPC(operation.getName(), params,
				encodingStyleURI, soapBody.getNamespaceURI(), soapActionURI);
		Parameter returnValue = response.getReturnValue();
		return (Byte[]) EncUtils.mapArrayInbuiltToWrapper(Byte[].class,
				returnValue.getValue());

	}

	public void eliminaFicheros(String[] localizadores) throws Exception {
		BindingOperation operation = getBindingOperation("eliminaFicheros");
		String soapActionURI = getSoapActionURI(operation);
		SOAPBody soapBody = getSOAPBody(operation);
		String encodingStyleURI = getEncodingStyleURI(soapBody);

		Vector params = new Vector();
		params.add(new Parameter("param0", String[].class, localizadores, null));
		makeSOAPCallRPC(operation.getName(), params, encodingStyleURI,
				soapBody.getNamespaceURI(), soapActionURI);
	}

	/**
	 * Realiza la llamada SOAP.
	 * 
	 * @param methodName
	 *            Nombre del método a ejecutar.
	 * @param params
	 *            Parámetros de la llamada.
	 * @param encodingStyleURI
	 *            Estilo de codificación.
	 * @param targetObjectURI
	 *            Espacio de nombres del mensaje
	 * @param soapActionURI
	 *            Acción SOAP.
	 * @return Respuesta del servicio web.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	private Response makeSOAPCallRPC(String methodName, Vector params,
			String encodingStyleURI, String targetObjectURI,
			String soapActionURI) throws Exception {
		Call call = new Call();
		call.setSOAPTransport(m_httpConnection);
		SOAPMappingRegistry registry;
		if ((registry = (SOAPMappingRegistry) m_soapMappingRegistries
				.get(methodName)) != null)
			call.setSOAPMappingRegistry(registry);
		else if (m_soapMappingRegistry != null)
			call.setSOAPMappingRegistry(m_soapMappingRegistry);
		call.setTargetObjectURI(targetObjectURI);
		call.setMethodName(methodName);
		call.setEncodingStyleURI(encodingStyleURI);
		call.setParams(params);
		call.setHeader(getHeader());

		Response response = call.invoke(new URL(getSoapURL()), soapActionURI);
		if (response.generatedFault()) {
			Fault fault = response.getFault();
			throw new SOAPException(fault.getFaultCode(),
					fault.getFaultString());
		}
		return response;
	}

	public SOAPMappingRegistry _getSOAPMappingRegistry() {
		return m_soapMappingRegistry;
	}

	public void _setSOAPMappingRegistry(SOAPMappingRegistry soapMappingRegistry) {
		m_soapMappingRegistry = soapMappingRegistry;
	}

	public boolean _getMaintainSession() {
		return m_httpConnection.getMaintainSession();
	}

	public void _setMaintainSession(boolean maintainSession) {
		m_httpConnection.setMaintainSession(maintainSession);
	}

	public Properties _getTransportProperties() {
		return m_httpConnection.getProperties();
	}

	public void _setTransportProperties(Properties properties) {
		m_httpConnection.setProperties(properties);
	}

}
