package es.ieci.tecdoc.fwktd.dm.bd.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.bd.dao.DaoFactory;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.dm.bd.manager.BaseDatosManager;
import es.ieci.tecdoc.fwktd.dm.bd.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.manager.MetadataManager;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;
import es.ieci.tecdoc.fwktd.util.uuid.UUIDGenerator;

/**
 * Implementación del manager de gestión documental que
 * almacena los documentos en base de datos.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseDatosManagerImpl implements BaseDatosManager {

    /**
     * Logger de la clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseDatosManagerImpl.class);

    /**
     * Factoría de DAOs
     */
    private DaoFactory daoFactory = null;

    /**
     * Información del tipo de contenido.
     */
    private ContentType contentType = null;


    /**
     * Constructor.
     */
    public BaseDatosManagerImpl() {
        super();
    }

    public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public DocumentoDao getDocumentoDao() {
		return getDaoFactory().getDocumentoDao(getContentType());
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
        boolean existe = false;
        if (StringUtils.isNotBlank(idDocumento)) {
            existe = getDocumentoDao().exists(idDocumento);
        }
        return existe;
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

        InfoDocumentoVO infoDocumento = null;

        if (StringUtils.isNotBlank(idDocumento)) {
            DocumentoVO documento = getDocumentoDao().get(idDocumento);
            infoDocumento = getInfoDocumentoVO(documento);
        }

        return infoDocumento;
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

        if (StringUtils.isNotBlank(idDocumento) && (out != null)) {
        	byte[] contenido = getDocumentoDao().getContenidoDocumento(idDocumento);
        	if (contenido != null) {
	            try {
	                out.write(contenido);
	                out.flush();
	            } catch (IOException e) {
	                logger.error("Error al obtener el contenido del documento [id=" + idDocumento + "]", e);
	                throw new GestionDocumentalException("Error al obtener el contenido del documento", e);
	            }
        	}
        }
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

        if ((infoDocumento != null) && (in != null)) {

            // Leer el contenido del documento
            byte[] contenido = null;

            try {
				contenido = FileUtils.retrieve(in);
			} catch (IOException e) {
				logger.error("Error al leer el contenido del documento", e);
				throw new GestionDocumentalException(
						"Error al leer el contenido del documento", e);
			}

			Date fecha = new Date();

            DocumentoVO documento = new DocumentoVO();
            documento.setId(UUIDGenerator.generateRandomBasedUUID().toString());
            documento.setNombre(infoDocumento.getNombre());
            documento.setTipoMime(infoDocumento.getTipoMime());
            documento.setTamano((contenido != null ? contenido.length : 0));
            documento.setFechaCreacion(fecha);
            documento.setFechaModificacion(fecha);
            documento.setContenido(contenido);

            BaseDatosMetadataManagerImpl metadataManager = new BaseDatosMetadataManagerImpl(getContentType());
            metadataManager.setMetadatos(infoDocumento.getMetadatos());
        	documento.setMetadatos(metadataManager.getDocumentAsXML());

            getDocumentoDao().save(documento);

            infoDocumento = getInfoDocumentoVO(documento);
        }

        return infoDocumento;
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

        if (infoDocumento != null) {

            // Leer el contenido del documento
            byte[] contenido = null;

            try {
				contenido = FileUtils.retrieve(in);
			} catch (IOException e) {
				logger.error("Error al leer el contenido del documento", e);
				throw new GestionDocumentalException(
						"Error al leer el contenido del documento", e);
			}

            DocumentoVO documento = new DocumentoVO();
            documento.setId(infoDocumento.getId());
            documento.setNombre(infoDocumento.getNombre());
            documento.setTipoMime(infoDocumento.getTipoMime());
            documento.setTamano((contenido != null ? contenido.length : 0));
            documento.setFechaModificacion(new Date());
            documento.setContenido(contenido);

            BaseDatosMetadataManagerImpl metadataManager = new BaseDatosMetadataManagerImpl(getContentType());
            metadataManager.setMetadatos(infoDocumento.getMetadatos());
        	documento.setMetadatos(metadataManager.getDocumentAsXML());

            getDocumentoDao().updateWithContent(documento);

            infoDocumento = getInfoDocumentoVO(documento);
        }

        return infoDocumento;
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
    	if (StringUtils.isNotBlank(idDocumento)) {
    		getDocumentoDao().delete(idDocumento);
    	}
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


	protected InfoDocumentoVO getInfoDocumentoVO(DocumentoVO documento)
			throws GestionDocumentalException {

		InfoDocumentoVO infoDocumento = null;

		if (documento != null) {

			Document docMetadatos = null;

			if (StringUtils.isNotBlank(documento.getMetadatos())) {
				try {
					docMetadatos = DocumentHelper.parseText(documento.getMetadatos());
				} catch (DocumentException e) {
					logger.error("Error al parsear el XML de los metadatos", e);
					throw new GestionDocumentalException(e);
				}
			}

			infoDocumento = new InfoDocumentoVO();
			infoDocumento.setId(documento.getId());
			infoDocumento.setNombre(documento.getNombre());
			infoDocumento.setTipoMime(documento.getTipoMime());
			infoDocumento.setTamano(documento.getTamano());
			infoDocumento.setFechaCreacion(documento.getFechaCreacion());
			infoDocumento.setFechaModificacion(documento.getFechaModificacion());
			MetadataManager metadataManager = new BaseDatosMetadataManagerImpl(
					getContentType(), docMetadatos);
			infoDocumento.setMetadatos(metadataManager.getMetadatos());
		}

		return infoDocumento;
	}

}
