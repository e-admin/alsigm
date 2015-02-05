package es.ieci.tecdoc.fwktd.csv.aplicacionExternaConnector.ws.delegate;

/**
 * Interfaz para el delegate de conexión con aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AplicacionExternaConnectorDelegate {

	/**
	 * Comprueba si existe el documento.
	 *
	 * @param csv
	 *            CSV del documento.
	 * @return true si existe el documento, false en caso contrario.
	 */
	public boolean existeDocumento(String csv);

	/**
	 * Obtiene el contenido del documento.
	 *
	 * @param csv
	 *            CSV del documento.
	 * @return Contenido del documento.
	 */
	public byte[] getContenidoDocumento(String csv);

}
