package es.ieci.tecdoc.fwktd.csv.api.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Clase base para los registros de conectores.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class BaseConnectorRegistry<CustomConnector extends Connector>
		implements ConnectorRegistry<CustomConnector> {

	/**
	 * Mapa de conectores.
	 */
	protected Map<String, CustomConnector> mapa = new HashMap<String, CustomConnector>();

	/**
	 * Constructor.
	 */
	public BaseConnectorRegistry() {
		super();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.ConnectorRegistry#registerConector(es.ieci.tecdoc.fwktd.csv.api.connector.Connector)
	 */
	public void registerConector(CustomConnector conector) {

		Assert.notNull(conector, "'conector' must not be null");

		mapa.put(conector.getUid(), conector);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.ConnectorRegistry#getConectores()
	 */
	public List<CustomConnector> getConectores() {
		return new ArrayList<CustomConnector>(mapa.values());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.ConnectorRegistry#getConector(java.lang.String)
	 */
	public CustomConnector getConector(String id) {
		return mapa.get(id);
	}
}
