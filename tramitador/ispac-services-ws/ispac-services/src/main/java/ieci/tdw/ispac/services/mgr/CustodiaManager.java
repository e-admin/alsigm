package ieci.tdw.ispac.services.mgr;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.services.dto.InfoFichero;
import ieci.tdw.ispac.services.dto.InfoOcupacion;
import ieci.tdw.ispac.services.helpers.DocumentsHelper;
import ieci.tdw.ispac.services.vo.DocumentoVO;

import org.apache.log4j.Logger;

public class CustodiaManager extends ServiceManager {

	/** Logger de la clase. */
	protected static final Logger logger = 
		Logger.getLogger(CustodiaManager.class);

	/**
	 * Constructor.
	 */
	private CustodiaManager() {
		super();
	}
	
	/**
	 * Obtiene una instancia del manager.
	 * @return Instancia del manager.
	 */
	public static CustodiaManager getInstance() {
		return new CustodiaManager();
	}

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     * @throws ISPACException si ocurre algún error.
     */
    public byte [] getFichero(String guid) throws ISPACException {
	    return DocumentsHelper.getContenidoDocumento(getContext(), guid);
    }

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InfoFichero getInfoFichero(String guid) 
			throws ISPACException {
    	InfoFichero info = null;
    	
        // Obtener la información del documento
		DocumentoVO doc = DocumentsHelper.getInfoDocumento(getContext(), guid);
	    if (doc != null) {
	        info = new InfoFichero();
	        info.setNombre(doc.getNombreCompleto());
			info.setFechaAlta(doc.getFechaAlta());
			info.setFirmas(DocumentsHelper.getFirmas(getContext(), doc));
	    }

		return info;
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InfoOcupacion getInfoOcupacion() throws ISPACException {
	    // Identificador del repositorio de custodia
        String repId = ISPACConfiguration.getInstance().get("RDE_ARCHIVE_ID");
        
        // Obtener la información de ocupación
        return DocumentsHelper.getInfoOcupacion(getContext(), repId);
	}

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void eliminaFicheros(String[] guids) 
			throws ISPACException {
        DocumentsHelper.removeFicheros(getContext(), guids);
	}

}
