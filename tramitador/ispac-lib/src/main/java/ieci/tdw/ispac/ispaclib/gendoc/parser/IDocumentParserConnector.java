package ieci.tdw.ispac.ispaclib.gendoc.parser;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.entity.DocumentData;

/**
 * Conector de parseo de documentos.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IDocumentParserConnector {

	/**
	 * Indica si el tipo MIME está soportado para las plantillas.
	 * 
	 * @param mimeType
	 *            Tipo MIME.
	 * @return True si el tipo MIME está soportado, false en caso contrario.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public boolean isSupported(String mimeType) throws ISPACException;

	/**
	 * Genera un documento a partir de una plantilla.
	 * 
	 * @param templateURL
	 *            URL de la plantilla a combinar.
	 * @param documentURL
	 *            URL del documento generado.
	 * @param documentData
	 *            Información del documento.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void mergeDocument(String templateURL, String documentURL,
			DocumentData documentData) throws ISPACException;

}
