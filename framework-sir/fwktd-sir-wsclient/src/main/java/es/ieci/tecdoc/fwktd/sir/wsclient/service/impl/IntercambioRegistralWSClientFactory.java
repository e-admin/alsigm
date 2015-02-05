package es.ieci.tecdoc.fwktd.sir.wsclient.service.impl;

import es.ieci.tecdoc.fwktd.sir.ws.service.IntercambioRegistralWS;

/**
 * @author IECISA
 *
 */
public interface IntercambioRegistralWSClientFactory {

	/**
	 * Metodo para obtener cliente de servicio web de intercambio registral
	 * @return
	 */
	public abstract IntercambioRegistralWS getIntercambioRegistralWS();

}