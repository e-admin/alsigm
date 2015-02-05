package es.ieci.tecdoc.fwktd.csv.web.delegate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import es.ieci.tecdoc.fwktd.csv.web.vo.InfoDocumentoVO;

/**
 * Interfaz para el delegate de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DocumentoDelegate {

	/**
	 * Obtiene la información almacenada del documento.
	 *
	 * @param csv
	 *            CSV del documento.
	 * @param locale
	 *            Locale del usuario.
	 * @return Información del documento.
	 */
	public InfoDocumentoVO getInfoDocumento(String csv, Locale locale);

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

}