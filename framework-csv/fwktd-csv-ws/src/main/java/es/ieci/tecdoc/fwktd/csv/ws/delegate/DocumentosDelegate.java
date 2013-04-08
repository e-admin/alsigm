package es.ieci.tecdoc.fwktd.csv.ws.delegate;

import es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm;

/**
 * Interfaz del delegate para gestionar documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DocumentosDelegate {

	/**
	 * Genera el CSV para un documento.
	 *
	 * @param infoDocumentoForm
	 *            Información del documento.
	 * @return Información del documento
	 */
	public InfoDocumentoCSV generarCSV(InfoDocumentoCSVForm infoDocumentoForm);

	/**
	 * Obtiene la información almacenada del documento.
	 *
	 * @param id
	 *            Identificador del documento.
	 * @return Información del documento.
	 */
	public InfoDocumentoCSV getInfoDocumento(String id);

	/**
	 * Obtiene la información almacenada del documento.
	 *
	 * @param csv
	 *            CSV del documento.
	 * @return Información del documento.
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(String csv);

	/**
	 * Obtiene la información almacenada del documento junto con el contenido.
	 *
	 * @param id
	 *            Identificador del documento.
	 * @return Información del documento con el contenido.
	 */
	public DocumentoCSV getDocumento(String id);

	/**
	 * Obtiene la información almacenada del documento junto con el contenido.
	 *
	 * @param csv
	 *            CSV del documento.
	 * @return Información del documento con el contenido.
	 */
	public DocumentoCSV getDocumentoByCSV(String csv);

	/**
	 * Guarda la información de un documento.
	 *
	 * @param infoDocumento
	 *            Información del documento.
	 * @return Información del documento.
	 */
	public InfoDocumentoCSV saveInfoDocumento(InfoDocumentoCSV infoDocumento);

	/**
	 * Actualiza la información almacenada del documento.
	 *
	 * @param infoDocumento
	 *            Información del documento.
	 * @return Información del documento.
	 */
	public InfoDocumentoCSV updateInfoDocumento(InfoDocumentoCSV infoDocumento);

	/**
	 * Elimina la información almacenada del documento.
	 *
	 * @param id
	 *            Identificador del documento.
	 */
	public void deleteInfoDocumento(String id);

	/**
	 * Comprueba si el contenido del documento se puede descargar de la
	 * aplicación externa.
	 *
	 * @param id
	 *            Identificador del documento.
	 * @return true si el contenido del documento existe, false en caso
	 *         contrario.
	 */
	public boolean existeContenidoDocumento(String id);

	/**
	 * Obtiene el contenido del documento.
	 *
	 * @param id
	 *            Identificador del documento.
	 * @return Contenido del documento.
	 */
	public byte[] getContenidoDocumento(String id);

	/**
	 * Revoca la disponibilidad del documento.
	 *
	 * @param csv
	 *          CSV del documento.
	 */
	public void revocarDocumento(String csv);

}
