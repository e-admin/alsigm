package es.ieci.tecdoc.fwktd.dm.bd.service.impl;

import java.io.InputStream;
import java.io.OutputStream;

import es.ieci.tecdoc.fwktd.dm.bd.manager.BaseDatosManager;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.impl.DefaultGestionDocumentalServiceImpl;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;

/**
 * Implementación del manager de gestión documental que
 * almacena los documentos en base de datos.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseDatosServiceImpl extends DefaultGestionDocumentalServiceImpl {

    private BaseDatosManager baseDatosManager = null;


    /**
     * Constructor.
     */
    public BaseDatosServiceImpl() {
        super();
    }

    /**
     * Constructor.
     */
    public BaseDatosServiceImpl(ContentType contentType) {
        super(contentType);
    }

	public BaseDatosManager getBaseDatosManager() {
		return baseDatosManager;
	}

	public void setBaseDatosManager(BaseDatosManager baseDatosManager) {
		this.baseDatosManager = baseDatosManager;
	}

	/**
     * Crea una sesión con el gestor documental.
     *
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void createSesion() throws GestionDocumentalException {
    }

    /**
     * Cierra la sesión con el gestor documental.
     *
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void releaseSesion() throws GestionDocumentalException {
    }

    /**
     * Comprueba si existe un documento.
     *
     * @param idDocumento
     *            Identificador del documento.
     * @return true si existe el documento, false en caso contrario.
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public boolean existeDocumento(String idDocumento)
            throws GestionDocumentalException {
        return getBaseDatosManager().existeDocumento(idDocumento);
    }

    /**
     * Obtiene la información de un documento.
     *
     * @param idDocumento
     *            Identificador del documento.
     * @return Información del documento.
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public InfoDocumentoVO getInfoDocumento(String idDocumento)
            throws GestionDocumentalException {
    	return getBaseDatosManager().getInfoDocumento(idDocumento);
    }

    /**
     * Obtiene el contenido de un documento.
     *
     * @param idDocumento
     *            Identificador del documento.
     * @param out
     *            Stream donde se guardará el contenido del documento.
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void retrieveDocumento(String idDocumento, OutputStream out)
            throws GestionDocumentalException {
    	getBaseDatosManager().retrieveDocumento(idDocumento, out);
    }

    /**
     * Crea un documento en el gestor documental.
     *
     * @param infoDocumento
     *            Información del documento.
     * @param in
     *            Contenido del documento.
     * @return Información del documento creado.
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public InfoDocumentoVO createDocumento(InfoDocumentoVO infoDocumento, InputStream in)
            throws GestionDocumentalException {
    	return getBaseDatosManager().createDocumento(infoDocumento, in);
    }

    /**
     * Modifica la información y contenido de un documento.
     *
     * @param documento
     *            Información del documento.
     * @param in
     *            Contenido del documento.
     * @return Información del documento actualizada.
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public InfoDocumentoVO updateDocumento(InfoDocumentoVO infoDocumento, InputStream in)
            throws GestionDocumentalException {
    	return getBaseDatosManager().updateDocumento(infoDocumento, in);
    }

    /**
     * Elimina un documento en el gestor documental.
     *
     * @param idDocumento
     *            Identificador del documento.
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void deleteDocumento(String idDocumento)
            throws GestionDocumentalException {
		getBaseDatosManager().deleteDocumento(idDocumento);
    }

//    /**
//     * Realiza una búsqueda de documentos en base a unos criterios.
//     *
//     * @param criteriosBusqueda
//     *            Criterios de búsqueda de documentos.
//     * @return Lista de documentos ({@link InfoDocumentoVO} ).
//     * @throws GestionDocumentalException
//     *             si ocurre algún error.
//     */
//    public List<InfoDocumentoVO> findDocumentos(CriteriosBusquedaVO criteriosBusqueda)
//            throws GestionDocumentalException {
//        return new ArrayList<InfoDocumentoVO>();
//    }

}
