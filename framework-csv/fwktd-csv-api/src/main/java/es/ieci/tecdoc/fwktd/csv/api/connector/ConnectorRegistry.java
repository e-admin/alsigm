package es.ieci.tecdoc.fwktd.csv.api.connector;

import java.util.List;

/**
 * Interfaz de registro de conectores.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ConnectorRegistry<CustomConnector extends Connector> {

	/**
	 * Registra un conector.
	 *
	 * @param conector
	 *            Conector.
	 */
	public void registerConector(CustomConnector conector);

	/**
	 * Obtiene la lista de conectores registrados.
	 *
	 * @return Lista de conectores registrado.
	 */
	public List<CustomConnector> getConectores();

	/**
	 * Obtiene el conector.
	 *
	 * @param id
	 *            Identificador del conector,
	 * @return Conector.
	 */
	public CustomConnector getConector(String id);
}
