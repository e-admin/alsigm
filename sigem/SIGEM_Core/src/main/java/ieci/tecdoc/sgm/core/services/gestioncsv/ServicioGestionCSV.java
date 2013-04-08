/**
 * 
 */
package ieci.tecdoc.sgm.core.services.gestioncsv;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

import java.io.OutputStream;

/**
 * @author IECISA
 * 
 *         Interfaz del servicio para la generación y gestión de CSV (Código
 *         Seguro de Verificación) de SIGEM.
 * 
 */
public interface ServicioGestionCSV {

	/**
	 * Genera el CSV de un documento y almacena la información en el modelo de
	 * datos.
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param infoDocumentoForm
	 *            Información del documento
	 * @return Información del documento con el CSV
	 */
	public InfoDocumentoCSV generarCSV(Entidad entidad, InfoDocumentoCSVForm infoDocumentoForm) throws CSVException;

	/**
	 * Obtiene la información de un documento a partir de su CSV.
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param csv
	 *            Código Seguro de Verificación asociado a dicho documento
	 * @return Información del documento con el CSV
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(Entidad entidad, String csv) throws CSVException;

	/**
	 * Obtiene la información de un documento, incluido su contenido, a partir
	 * de su CSV.
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param csv
	 *            Código Seguro de Verificación asociado a dicho documento
	 * @return Información del documento con el CSV y su contenido
	 */
	public DocumentoCSV getDocumentoByCSV(Entidad entidad, String csv) throws CSVException;

	/**
	 * Elimina la información de un documento
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 */
	public void deleteInfoDocumento(Entidad entidad, String id) throws CSVException;

	/**
	 * Comprueba si el contenido del documento se puede descargar de la
	 * aplicación externa
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 * @return true: Si existe el contenido del documento. false: Si no existe.
	 */
	public boolean existeContenidoDocumento(Entidad entidad, String id) throws CSVException;

	/**
	 * Obtiene el contenido de un documento a partir de su identificador
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 * @return Contenido del documento.
	 */
	public byte[] getContenidoDocumento(Entidad entidad, String id) throws CSVException;

	/**
	 * Escribe el contenido de un documento a partir del identificador en el
	 * OutputStrem
	 * 
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 * @param outputStream
	 *            OutputStream sobre el que escribiremos el contenido del
	 *            documento
	 * 
	 */
	public void writeDocumento(Entidad entidad, String id, OutputStream outputStream) throws CSVException;
}
