package es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.impl.ws.AplicacionExternaConnectorWS;
import es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.impl.ws.AplicacionExternaConnectorWSService;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.ws.security.callback.SimplePasswordCallback;

/**
 * Implementación del conector con las aplicaciones externas que almacenan los
 * documentos, que hace llamadas a un servicio web definido en el producto.
 *
 * Parámetros de configuración:
 * <dl>
 * <dt>WSDL_LOCATION</dt><dd>Obligatorio. URL del WSDL del servicio web.</dd>
 * </dl>
 *
 * Parámetros para añadir seguridad mediante usuario y contraseña:
 * <dl>
 * <dt>AUTHENTICATION_MODE</dt><dd>Obligatorio. Modo de autenticación. Se debe configurar con el valor USERNAME.</dd>
 * <dt>USERNAME</dt><dd>Obligatorio. Usuario de acceso al servicio web.</dd>
 * <dt>PASSWORD</dt><dd>Obligatorio. Clave del usuario de acceso al servicio web.</dd>
 * <dt>PASSWORD_TYPE</dt><dd>Opcional. Tipo de password. Los valores pueden ser: PasswordDigest o PasswordText</dd>
 * </dl>
 *
 * Parámetros para añadir seguridad mediante certificado digital:
 * <dl>
 * <dt>AUTHENTICATION_MODE</dt><dd>Obligatorio. Modo de autenticación. Se debe configurar con el valor CERTIFICATE.</dd>
 * <dt>KEYSTORE_FILE</dt><dd>Obligatorio. Ruta del fichero que contiene el almacén de certificados.</dd>
 * <dt>KEYSTORE_TYPE</dt><dd>Obligatorio. Tipo de almacén de certificados: JKS, PKCS12, ...</dd>
 * <dt>KEYSTORE_CERT_ALIAS</dt><dd>Obligatorio. Alias del certificado en el almacén de certificados.</dd>
 * <dt>KEYSTORE_PASSWORD</dt><dd>Obligatorio. Clave del almacén de certificados.</dd>
 * <dt>KEY_ALIAS</dt><dd>Obligatorio. Alias de la clave privada.</dd>
 * <dt>KEY_PASSWORD</dt><dd>Obligatorio. Clave de acceso a la clave privada.</dd>
 * </dl>
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class WebServiceConnectorImpl extends
		AbstractAplicacionExternaConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(WebServiceConnectorImpl.class);

	private static final String DEFAULT_CRYPTO_PROVIDER = "org.apache.ws.security.components.crypto.Merlin";
	private static final String SIG_PROP_REF_ID_VALUE = "CRYPTO_PROPERTIES";

	private static final String AUTH_USERNAME = "USERNAME";
	private static final String AUTH_CERTIFICATE = "CERTIFICATE";

	private static final String WSDL_LOCATION_PARAM = "WSDL_LOCATION";
	private static final String AUTHENTICATION_MODE_PARAM = "AUTHENTICATION_MODE";

	private static final String USERNAME_PARAM = "USERNAME";
	private static final String PASSWORD_PARAM = "PASSWORD";
	private static final String PASSWORD_TYPE_PARAM = "PASSWORD_TYPE";

	private static final String CRYPTO_PROVIDER_PARAM = "CRYPTO_PROVIDER";
	private static final String KEYSTORE_FILE_PARAM = "KEYSTORE_FILE";
	private static final String KEYSTORE_TYPE_PARAM = "KEYSTORE_TYPE";
	private static final String KEYSTORE_CERT_ALIAS_PARAM = "KEYSTORE_CERT_ALIAS";
	private static final String KEYSTORE_PASSWORD_PARAM = "KEYSTORE_PASSWORD";
	private static final String KEY_ALIAS_PARAM = "KEY_ALIAS";
	private static final String KEY_PASSWORD_PARAM = "KEY_PASSWORD";

	/**
	 * Constructor.
	 */
	public WebServiceConnectorImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.AplicacionExternaConnector#existeDocumento(java.lang.String, java.util.Map)
	 */
	public boolean existeDocumento(String csv, Map<String, String> params) {

		logger.info("Llamada a existeDocumento: csv=[{}], params=[{}]", csv, params);

		AplicacionExternaConnectorWS aplicacionExternaConnectorWS = getAplicacionExternaConnectorWS(params);
		return aplicacionExternaConnectorWS.existeDocumento(csv);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.AplicacionExternaConnector#getContenidoDocumento(java.lang.String, java.util.Map)
	 */
	public byte[] getContenidoDocumento(String csv, Map<String, String> params) {

		logger.info("Llamada a getContenidoDocumento: csv=[{}], params=[{}]", csv, params);

		AplicacionExternaConnectorWS aplicacionExternaConnectorWS = getAplicacionExternaConnectorWS(params);
		return aplicacionExternaConnectorWS.getContenidoDocumento(csv);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.AplicacionExternaConnector#writeDocumento(java.lang.String, java.io.OutputStream, java.util.Map)
	 */
	public void writeDocumento(String csv, OutputStream outputStream,
			Map<String, String> params) throws IOException {

		logger.info("Llamada a writeDocumento: csv=[{}], params=[{}]", csv, params);

		AplicacionExternaConnectorWS aplicacionExternaConnectorWS = getAplicacionExternaConnectorWS(params);
		byte[] contenido = aplicacionExternaConnectorWS.getContenidoDocumento(csv);

		if ((contenido != null) && (contenido.length > 0)) {

			logger.info("Escribiendo {} bytes...", contenido.length);

			outputStream.write(contenido);
			outputStream.flush();

		} else {
			logger.info("El contenido está vacío");
		}
	}

	public AplicacionExternaConnectorWS getAplicacionExternaConnectorWS(Map<String, String> params) {

		AplicacionExternaConnectorWSService aplicacionExternaConnectorWSService = new AplicacionExternaConnectorWSService(getWSDLLocation(params));
		AplicacionExternaConnectorWS aplicacionExternaConnectorWS = aplicacionExternaConnectorWSService.getAplicacionExternaConnectorWS();

		// Modo de autenticación
		String authenticationMode = MapUtils.getString(params, AUTHENTICATION_MODE_PARAM);
		if (AUTH_USERNAME.equalsIgnoreCase(authenticationMode)) {
			addUsernameTokenSecurity(aplicacionExternaConnectorWS, params);
		} else if (AUTH_CERTIFICATE.equalsIgnoreCase(authenticationMode)) {
			addCertificateSecurity(aplicacionExternaConnectorWS, params);
		} else {

			// Sin autenticación
			logger.info(
					"Llamada al servicio web sin aplicar token de seguridad: {}=[{}]",
					AUTHENTICATION_MODE_PARAM, authenticationMode);
		}

		return aplicacionExternaConnectorWS;
	}

	protected URL getWSDLLocation(Map<String, String> params) {

		String url = MapUtils.getString(params, WSDL_LOCATION_PARAM);
		logger.info("{}: {}", WSDL_LOCATION_PARAM, url);
		if (StringUtils.isBlank(url)) {
			logger.error("No se ha especificado el parámetro {}", WSDL_LOCATION_PARAM);
			throw new CSVException(
					"error.csv.connector.aplicacionExterna.configError",
					new String[] { getUid(), WSDL_LOCATION_PARAM },
					"Error en la configuración del conector con aplicaciones externas: "
							+ WSDL_LOCATION_PARAM);
		}

		URL wsdlLocation = null;
		try {
			wsdlLocation = new URL(url);
			logger.info("URL [{}]: {}", WSDL_LOCATION_PARAM, wsdlLocation);
		} catch (MalformedURLException e) {
			logger.error("El parámetro " + WSDL_LOCATION_PARAM + " no contiene una URL válida: " + url, e);
			throw new CSVException(
					"error.csv.connector.aplicacionExterna.configError",
					new String[] { getUid(), WSDL_LOCATION_PARAM },
					"Error en la configuración del conector con aplicaciones externas: "
							+ WSDL_LOCATION_PARAM);
		}

		return wsdlLocation;
	}

	protected void addInterceptor(AplicacionExternaConnectorWS aplicacionExternaConnectorWS, WSS4JOutInterceptor interceptor) {

		logger.info("Añadiendo en interceptor de seguridad: {}", interceptor);

		Client client = ClientProxy.getClient(aplicacionExternaConnectorWS);
		client.getEndpoint().getOutInterceptors().add(interceptor);
	}

	protected void addUsernameTokenSecurity(AplicacionExternaConnectorWS aplicacionExternaConnectorWS, Map<String, String> params) {

		String username = MapUtils.getString(params, USERNAME_PARAM);
		String password = MapUtils.getString(params, PASSWORD_PARAM);
		
		String passwordType = MapUtils.getString(params, PASSWORD_TYPE_PARAM);
		if (StringUtils.isBlank(passwordType)) {
			passwordType = WSConstants.PW_DIGEST;
		}

		logger.info("Creando el token de seguridad usernameToken: usuario=[{}], passwordType[{}]", username, passwordType);

		// Propiedades del interceptor
		Map<String,Object> outProps = new HashMap<String,Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, username);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, passwordType);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new SimplePasswordCallback(password));

		addInterceptor(aplicacionExternaConnectorWS, new WSS4JOutInterceptor(outProps));
	}

	protected void addCertificateSecurity(AplicacionExternaConnectorWS aplicacionExternaConnectorWS, Map<String, String> params) {

		String keyAlias = MapUtils.getString(params, KEY_ALIAS_PARAM);
		String keyPassword = MapUtils.getString(params, KEY_PASSWORD_PARAM);

		String provider = MapUtils.getString(params,CRYPTO_PROVIDER_PARAM, DEFAULT_CRYPTO_PROVIDER);
		String keystoreFile = MapUtils.getString(params, KEYSTORE_FILE_PARAM);
		String keystoreType = MapUtils.getString(params, KEYSTORE_TYPE_PARAM);
		String keystoreCertAlias = MapUtils.getString(params, KEYSTORE_CERT_ALIAS_PARAM);
		String keystorePassword = MapUtils.getString(params, KEYSTORE_PASSWORD_PARAM);


		logger.info("Creando el token de seguridad con certificado: keyAlias=[{}], provider=[{}], keystoreFile=[{}], keystoreType=[{}], keystoreCertAlias=[{}], ",
				new Object[] { keyAlias, provider, keystoreFile, keystoreType, keystoreCertAlias });

		// Propiedades de seguridad internas
		Properties cryptoProperties = new Properties();
		cryptoProperties.setProperty("org.apache.ws.security.crypto.provider", provider);
		cryptoProperties.setProperty("org.apache.ws.security.crypto.merlin.file", keystoreFile);
		cryptoProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", keystoreType);
		cryptoProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", keystoreCertAlias);
		cryptoProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", keystorePassword);

		// Propiedades del interceptor
		Map<String,Object> outProps = new HashMap<String,Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
		outProps.put(WSHandlerConstants.USER, keyAlias);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new SimplePasswordCallback(keyPassword));
		outProps.put(WSHandlerConstants.SIG_PROP_REF_ID, SIG_PROP_REF_ID_VALUE);
		outProps.put(SIG_PROP_REF_ID_VALUE, cryptoProperties);

		addInterceptor(aplicacionExternaConnectorWS, new WSS4JOutInterceptor(outProps));
	}
}
