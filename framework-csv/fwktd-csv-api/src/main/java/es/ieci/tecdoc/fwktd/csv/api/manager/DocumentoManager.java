package es.ieci.tecdoc.fwktd.csv.api.manager;

import es.ieci.tecdoc.fwktd.csv.api.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.csv.api.xml.connectionConfig.ConfiguracionConexion;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;

/**
 * Interfaz para los managers de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DocumentoManager extends BaseManager<DocumentoVO, String> {

	/**
	 * Obtiene la información del documento a partir del CSV.
	 *
	 * @param csv
	 *            Código Seguro de Verificación del documento.
	 * @return Información del documento.
	 */
	public DocumentoVO getDocumentoByCSV(String csv);

	/**
	 * Elimina un documento a partir del CSV
	 *
	 * @param csv
	 *            Código Seguro de Verificación del documento.
	 */
	public void deleteDocumentoByCSV(String csv);

	/**
	 * Obtiene el la configuración de conexión a la aplicación externa asociada
	 * al documento.
	 *
	 * @param documento
	 *            Información del documento.
	 * @return Conector con la aplicación externa.
	 */
	public ConfiguracionConexion getConfiguracionConexion(DocumentoVO documento);

}
