package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Interfaz para los managers de envío de ficheros de datos de intercambio en
 * formato SICRES 3.0 generado por la aplicación de registro.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface FicheroIntercambioManager {

	/**
	 * Envía el fichero de datos de intercambio al nodo distribuido asociado.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 */
	public void enviarFicheroIntercambio(AsientoRegistralVO asiento);

	/**
	 * Reenvía el fichero de datos de intercambio al nodo distribuido asociado.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 */
	public void reenviarFicheroIntercambio(AsientoRegistralVO asiento);

	/**
	 * Rechaza el fichero de datos de intercambio.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 */
	public void rechazarFicheroIntercambio(AsientoRegistralVO asiento);

}
