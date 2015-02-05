package es.ieci.tecdoc.fwktd.csv.core.service;

import java.io.IOException;
import java.io.OutputStream;

import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

/**
 * Interfaz del servicio de gestión de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ServicioDocumentos {

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
	 * Guarda el contenido del documento en el OutputStream.
	 *
	 * @param id
	 *            Identificador del documento.
	 * @param outputStream
	 *            OutputStream para escribir el documento.
	 * @throws IOException
	 *             si ocurre algún error en la escritura del documento.
	 */
	public void writeDocumento(String id, OutputStream outputStream)
			throws IOException;

	/**
	 * Revoca la disponibilidad del documento.
	 *
	 * @param csv
	 *          CSV del documento.
	 */
	public void revocarDocumento(String csv);

}
