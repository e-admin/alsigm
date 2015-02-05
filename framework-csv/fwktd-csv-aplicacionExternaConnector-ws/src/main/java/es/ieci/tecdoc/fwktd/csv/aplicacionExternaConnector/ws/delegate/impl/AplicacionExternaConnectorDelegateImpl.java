package es.ieci.tecdoc.fwktd.csv.aplicacionExternaConnector.ws.delegate.impl;

import es.ieci.tecdoc.fwktd.csv.aplicacionExternaConnector.ws.delegate.AplicacionExternaConnectorDelegate;

/**
 * Implementación del delegate de conexión con aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionExternaConnectorDelegateImpl implements
		AplicacionExternaConnectorDelegate {

	/**
	 * Constructor.
	 */
	public AplicacionExternaConnectorDelegateImpl() {
		super();
	}

	/**
	 * {@inheritDo
	 * @see es.ieci.tecdoc.fwktd.csv.aplicacionExternaConnector.ws.delegate.AplicacionExternaConnectorDelegate#existeDocumento(java.lang.String)
	 */
	public boolean existeDocumento(String csv) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.csv.aplicacionExternaConnector.ws.delegate.AplicacionExternaConnectorDelegate#getContenidoDocumento(java.lang.String)
	 */
	public byte[] getContenidoDocumento(String csv) {
		return null;
	}
}
