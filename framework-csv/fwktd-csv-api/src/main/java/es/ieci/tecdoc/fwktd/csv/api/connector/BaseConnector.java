package es.ieci.tecdoc.fwktd.csv.api.connector;

/**
 * Clase base para los conectores.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class BaseConnector implements Connector {

	/**
	 * Registro de conectores.
	 */
	private ConnectorRegistry<BaseConnector> connectorRegistry = null;

	/**
	 * UID del conector
	 */
	private String uid = null;

	/**
	 * Constructor.
	 */
	public BaseConnector() {
		super();
	}

	public ConnectorRegistry<BaseConnector> getConnectorRegistry() {
		return connectorRegistry;
	}

	public void setConnectorRegistry(ConnectorRegistry<BaseConnector> connectorRegistry) {
		this.connectorRegistry = connectorRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.Connector#getUid()
	 */
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Registra este conector en el registro de conectores.
	 */
	public void register() {
		if (connectorRegistry != null) {
			connectorRegistry.registerConector(this);
		}
    }

}
