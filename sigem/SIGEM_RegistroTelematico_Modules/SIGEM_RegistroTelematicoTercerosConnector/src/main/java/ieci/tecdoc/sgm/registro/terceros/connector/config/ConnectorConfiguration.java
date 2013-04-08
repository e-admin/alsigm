package ieci.tecdoc.sgm.registro.terceros.connector.config;

import java.util.Properties;

/**
 * Almacena la configuración para cargar el conector correspondiente
 * @author iecisa
 *
 */
public class ConnectorConfiguration {


	private static final String WS_ENDPOINT_KEY = "WS_ENDPOINT";

	private static final String AUTH_USERNAME = "USERNAME";
	private static final String AUTH_CERTIFICATE = "CERTIFICATE";


	private static final String AUTHENTICATION_MODE_PARAM = "AUTHENTICATION_MODE";

	private static final String USERNAME_PARAM = "USERNAME";
	private static final String PASSWORD_PARAM = "PASSWORD";

	private static final String CRYPTO_PROVIDER_PARAM = "CRYPTO_PROVIDER";
	private static final String KEYSTORE_FILE_PARAM = "KEYSTORE_FILE";
	private static final String KEYSTORE_TYPE_PARAM = "KEYSTORE_TYPE";
	private static final String KEYSTORE_CERT_ALIAS_PARAM = "KEYSTORE_CERT_ALIAS";
	private static final String KEYSTORE_PASSWORD_PARAM = "KEYSTORE_PASSWORD";
	private static final String KEY_ALIAS_PARAM = "KEY_ALIAS";
	private static final String KEY_PASSWORD_PARAM = "KEY_PASSWORD";
	private static final String DEFAULT_CRYPTO_PROVIDER = "org.apache.ws.security.components.crypto.Merlin";
	private static final String SIG_PROP_REF_ID_VALUE = "CRYPTO_PROPERTIES";

	private static final String CONNECTOR_KEY = "TERCEROS_CONNECTOR_IMPL";

	protected Properties params=null;

	public ConnectorConfiguration(String idEntidad) {

		params = TercerosConnectorConfigLoader.getProperties(idEntidad);
	}

	public String getConnector() {
		String tercerosConnectorImpl = null;
		if(params!=null)
		{
			tercerosConnectorImpl = params.getProperty(CONNECTOR_KEY);
		}
		return tercerosConnectorImpl;
	}


	public String getWSEndpoint(){
		String endpoint = null;
		if(params!=null)
		{
			endpoint = params.getProperty(WS_ENDPOINT_KEY);
		}
		return endpoint;
	}

}
