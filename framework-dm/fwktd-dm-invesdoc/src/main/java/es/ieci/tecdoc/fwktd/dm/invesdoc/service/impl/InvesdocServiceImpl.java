package es.ieci.tecdoc.fwktd.dm.invesdoc.service.impl;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.db.DbError;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.api.Archive;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.api.Folder;
import ieci.tecdoc.sbo.idoc.api.FolderDocumentObject;
import ieci.tecdoc.sbo.idoc.api.FolderObject;
import ieci.tecdoc.sbo.idoc.folder.base.FolderBaseError;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.manager.MetadataManager;
import es.ieci.tecdoc.fwktd.dm.business.service.impl.DefaultGestionDocumentalServiceImpl;
import es.ieci.tecdoc.fwktd.dm.business.util.MimeTypeUtils;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.invesdoc.manager.impl.InvesdocMetadataManagerImpl;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;

public class InvesdocServiceImpl extends DefaultGestionDocumentalServiceImpl {

    /**
     * Logger de la clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(InvesdocServiceImpl.class);

    private DbConnectionConfig dbConnectionConfig = null;
	private int idArchivador = -1;
    private int idUsuario = 0;
	private String directorioTemporal = null;


    /**
     * Constructor.
     */
    public InvesdocServiceImpl() {
        super();
    }

    /**
     * Constructor.
     */
    public InvesdocServiceImpl(ContentType contentType) {
        super(contentType);
    }

	public DbConnectionConfig getDbConnectionConfig() {
		return dbConnectionConfig;
	}

	public void setDbConnectionConfig(DbConnectionConfig dbConnectionConfig) {
		this.dbConnectionConfig = dbConnectionConfig;
	}

	public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDirectorioTemporal() {
        return directorioTemporal;
    }

    public void setDirectorioTemporal(String directorioTemporal) {
        this.directorioTemporal = directorioTemporal;
    }

	public int getIdArchivador() {
		return idArchivador;
	}

	public void setIdArchivador(int idArchivador) {
		this.idArchivador = idArchivador;
	}

    /**
     * Crea una sesión con el gestor documental.
     *
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void createSesion() throws GestionDocumentalException {
		if (dbConnectionConfig != null) {
			this.setDbConnectionConfig(dbConnectionConfig);
		}
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

        boolean existe = false;

        logger.info("Comprobando si existe el documento: {}", idDocumento);

        if (StringUtils.isNotBlank(idDocumento)) {

            try {

                InvesdocUid uid = new InvesdocUid(idDocumento);

                logger.debug("InvesdocUid: {}", uid);

                Archive archiveAPI = new Archive();
                ArchiveObject archive = archiveAPI.loadArchive(null, uid.getArchive());

                if (logger.isDebugEnabled()) {
	                logger.debug("ArchiveObject: {}", archive);
	                if (archive != null) {
	                	logger.debug("ArchiveToken: {}", archive.getArchiveToken());
	                }
                }

                Folder folderAPI = new Folder();
                FolderObject folder = folderAPI.loadFolder(null,
                        getIdUsuario(), archive, uid.getFolder());

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderObject: {}", folder);
	                if (folder != null) {
	                	logger.debug("FolderToken: {}", folder.getFolderToken());
	                }
                }

                FolderDocumentObject document = folder.getDocument(uid.getDocument());

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderDocumentObject: {}", document);
                }

                if (document != null) {
                    existe = true;
                }

            } catch (IeciTdException e) {

                // Documento no encontrado
                if (DbError.EC_NOT_FOUND.equals(e.getErrorCode())
                        || FolderBaseError.EC_NOT_FOUND.equals(e.getErrorCode())) {
                    existe = false;
                } else {
                    logger.error("Error al comprobar la existencia del documento ["
                                    + idDocumento + "]", e);
                    throw new GestionDocumentalException(
                            "Error al comprobar la existencia del documento", e);
                }

            } catch (Exception e) {
                logger.error("Error al comprobar la existencia del documento ["
                        + idDocumento + "]", e);
                throw new GestionDocumentalException(
                        "Error al comprobar la existencia del documento", e);
            }
        }

        logger.debug("Existe el documento {}? {}", idDocumento, existe);

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

        logger.info("Obteniendo la información del documento: {}", idDocumento);

        if (StringUtils.isNotBlank(idDocumento)) {

            try {

                InvesdocUid uid = new InvesdocUid(idDocumento);

                logger.debug("InvesdocUid: {}", uid);

                Archive archiveAPI = new Archive();
                ArchiveObject archive = archiveAPI.loadArchive(null, uid.getArchive());

                if (logger.isDebugEnabled()) {
	                logger.debug("ArchiveObject: {}", archive);
	                if (archive != null) {
	                	logger.debug("ArchiveToken: {}", archive.getArchiveToken());
	                }
                }

                Folder folderAPI = new Folder();
                FolderObject folder = folderAPI.loadFolder(null,
                        getIdUsuario(), archive, uid.getFolder());

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderObject: {}", folder);
	                if (folder != null) {
	                	logger.debug("FolderToken: {}", folder.getFolderToken());
	                }
                }

                FolderDocumentObject document = folder.getDocument(uid.getDocument());

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderDocumentObject: {}", document);
                }

                infoDocumento = new InfoDocumentoVO();
                infoDocumento.setId(idDocumento);
                infoDocumento.setNombre(document.getName());
                infoDocumento.setTipoMime(MimeTypeUtils.getExtensionMimeType(document.getFileExt()));

                long fileSize = folderAPI.getFileSize(null, archive, folder, document.getId());
                infoDocumento.setTamano(fileSize);

                // TODO Obtener fechas de creación y modificación
                infoDocumento.setFechaCreacion(null);
                infoDocumento.setFechaModificacion(null);

                // Mapeo de metadatos
				MetadataManager metadataManager = new InvesdocMetadataManagerImpl(
						getContentType(), archive, folder);
				infoDocumento.setMetadatos(metadataManager.getMetadatos());

            } catch (GestionDocumentalException e) {
        		throw e;
            } catch (IeciTdException e) {

                // Documento no encontrado
                if (DbError.EC_NOT_FOUND.equals(e.getErrorCode())
                        || FolderBaseError.EC_NOT_FOUND.equals(e.getErrorCode())) {
                    logger.info("No existe el documento [{}]", idDocumento);
                } else {
                    logger.error("Error al comprobar la existencia del documento ["
                    		+ idDocumento + "]", e);
                    throw new GestionDocumentalException(
                            "Error al comprobar la existencia del documento", e);
                }

            } catch (Exception e) {
                logger.error("Error al obtener la información del documento ["
                        + idDocumento + "]", e);
                throw new GestionDocumentalException(
                        "Error al obtener la información del documento", e);
            }
        }

		logger.info("Información del documento {}: {}", idDocumento, infoDocumento);

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

    	logger.info("Obteniendo el contenido del documento: {}", idDocumento);

        if (StringUtils.isNotBlank(idDocumento)) {

            File tempFile = null;

            try {

                InvesdocUid uid = new InvesdocUid(idDocumento);

                logger.debug("InvesdocUid: {}", uid);

                Archive archiveAPI = new Archive();
                ArchiveObject archive = archiveAPI.loadArchive(null, uid.getArchive());

                if (logger.isDebugEnabled()) {
	                logger.debug("ArchiveObject: {}", archive);
	                if (archive != null) {
	                	logger.debug("ArchiveToken: {}", archive.getArchiveToken());
	                }
                }

                Folder folderAPI = new Folder();
                FolderObject folder = folderAPI.loadFolder(null, getIdUsuario(),
                		archive, uid.getFolder());

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderObject: {}", folder);
	                if (folder != null) {
	                	logger.debug("FolderToken: {}", folder.getFolderToken());
	                }
                }

                tempFile = folderAPI.retrieveFolderDocumentFile(null, archive,
                        folder, new File(getDirectorioTemporal()), uid.getDocument());

                logger.debug("Fichero temporal: {}", tempFile);

                FileUtils.copy(new FileInputStream(tempFile), out);

                logger.debug("Fichero temporal copiado al OutputStream");

            } catch (Exception e) {
                logger.error("Error al obtener el contenido del documento [id="
                        + idDocumento + "]", e);
                throw new GestionDocumentalException(
                        "Error al obtener el contenido del documento", e);
            } finally {

                // Elimina el fichero intermedio
                if (tempFile != null) {
                    tempFile.delete();
                    logger.debug("Fichero temporal eliminado");
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
    public InfoDocumentoVO createDocumento(InfoDocumentoVO infoDocumento,
            InputStream in) throws GestionDocumentalException {

    	logger.info("Creando el documento: {}", infoDocumento);

        if (infoDocumento != null) {

            try {

                Archive archiveAPI = new Archive();
                ArchiveObject archive = archiveAPI.loadArchive(null, getIdArchivador());

                if (logger.isDebugEnabled()) {
	                logger.debug("ArchiveObject: {}", archive);
	                if (archive != null) {
	                	logger.debug("ArchiveToken: {}", archive.getArchiveToken());
	                }
                }

                Folder folderAPI = new Folder();
                FolderObject folder = folderAPI.newFolder(null, archive);

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderObject: {}", folder);
	                if (folder != null) {
	                	logger.debug("FolderToken: {}", folder.getFolderToken());
	                }
                }

                // Añade el fichero como raíz a la carpeta
                folder.addRootDocument(infoDocumento.getNombre(),
                        FileUtils.getExtension(infoDocumento.getNombre()), in);

                logger.debug("Fichero añadido como raíz a la carpeta");

                // Añade los metadatos
				MetadataManager metadataManager = new InvesdocMetadataManagerImpl(
						getContentType(), archive, folder);
				metadataManager.setMetadatos(infoDocumento.getMetadatos());

				logger.debug("Metadatos establecidos");

                // Crea la carpeta
                folderAPI.createFolder(getIdUsuario(), archive, folder);

                logger.debug("Carpeta creada");

                // Obtiene el identificador el documento
                int documentId = folder.getAllDocuments().get(0).getId();
                InvesdocUid uid = new InvesdocUid(archive.getId(),
                		folder.getId(), documentId);

                logger.debug("InvesdocUid: {}", uid);

                // Obtener la información del documento
                infoDocumento = getInfoDocumento(uid.toUid());

            } catch (Exception e) {
                logger.error("Error al crear el documento", e);
                throw new GestionDocumentalException(
                        "Error al crear el documento", e);
            }
        }

        logger.debug("Información del documento creado: {}", infoDocumento);

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
    public InfoDocumentoVO updateDocumento(InfoDocumentoVO infoDocumento,
            InputStream in) throws GestionDocumentalException {

    	logger.info("Modificando el documento: {}", infoDocumento);

        if (infoDocumento != null) {

            ArchiveObject archive = null;
            Folder folderAPI = null;
            FolderObject folder = null;

            try {

                InvesdocUid uid = new InvesdocUid(infoDocumento.getId());

                logger.debug("InvesdocUid: {}", uid);

                Archive archiveAPI = new Archive();
                archive = archiveAPI.loadArchive(null, uid.getArchive());

                if (logger.isDebugEnabled()) {
	                logger.debug("ArchiveObject: {}", archive);
	                if (archive != null) {
	                	logger.debug("ArchiveToken: {}", archive.getArchiveToken());
	                }
                }

                folderAPI = new Folder();

                // Bloquea la carpeta
                folderAPI.editFolder(null, getIdUsuario(), archive, uid.getFolder());

                logger.debug("Carpeta bloqueada");

                // Obtiene la información de la carpeta
                folder = folderAPI.loadFolder(null, getIdUsuario(), archive, uid.getFolder());

                if (logger.isDebugEnabled()) {
	                logger.debug("FolderObject: {}", folder);
	                if (folder != null) {
	                	logger.debug("FolderToken: {}", folder.getFolderToken());
	                }
                }

                // Información del documento
                FolderDocumentObject document = folder.getDocument(uid.getDocument());
                logger.debug("FolderDocumentObject: {}", document);
                if (!StringUtils.equals(document.getName(), infoDocumento.getNombre())) {
                    folder.renameDocument(document.getId(), infoDocumento.getNombre());
                    logger.debug("Se ha renombrado el documento {} con el nombre {}",
                    		document.getId(), infoDocumento.getNombre());
                }

                // Actualizar el contenido del fichero
                if (in != null) {
                    document.replaceFile(in, FileUtils.getExtension(infoDocumento.getNombre()));
                    logger.debug("Contenido del documento {} actualizado", infoDocumento.getNombre());
                }

                // Actualiza los metadatos
				MetadataManager metadataManager = new InvesdocMetadataManagerImpl(
						getContentType(), archive, folder);
				metadataManager.setMetadatos(infoDocumento.getMetadatos());

				logger.debug("Metadatos establecidos");

                // Actualiza la carpeta
                folderAPI.storeFolder(getIdUsuario(), archive, folder);

                logger.debug("Carpeta actualizada");

                // Obtener la información del documento
                infoDocumento = getInfoDocumento(infoDocumento.getId());

            } catch (Exception e) {
                logger.error("Error al modificar el documento", e);
                throw new GestionDocumentalException("Error al modificar el documento", e);
            } finally {
                try {
                    // Desbloquea la carpeta
                    if (folder != null) {
                        folderAPI.terminateEditFolder(getIdUsuario(), archive, folder.getId());
                    }
                } catch (Exception e) {
                    logger.warn("Error al desbloquear la carpeta", e);
                }
            }
        }

        logger.debug("Información del documento atualizado: {}", infoDocumento);

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

    	logger.info("Eliminando el documento: {}", idDocumento);

        if (StringUtils.isNotBlank(idDocumento)) {

            try {

                InvesdocUid uid = new InvesdocUid(idDocumento);

                logger.debug("InvesdocUid: {}", uid);

                Archive archiveAPI = new Archive();
                ArchiveObject archive = archiveAPI.loadArchive(null, uid.getArchive());

                if (logger.isDebugEnabled()) {
	                logger.debug("ArchiveObject: {}", archive);
	                if (archive != null) {
	                	logger.debug("ArchiveToken: {}", archive.getArchiveToken());
	                }
                }

                Folder folderAPI = new Folder();
                folderAPI.removeFolder(null, getIdUsuario(), archive, uid.getFolder());

                logger.debug("Carpeta eliminada");

            } catch (IeciTdException e) {

                // Documento no encontrado (ya eliminado)
                if (!DbError.EC_NOT_FOUND.equals(e.getErrorCode())) {
                    logger.error("Error al eliminar el documento ["
                            + idDocumento + "]", e);
                    throw new GestionDocumentalException(
                            "Error al eliminar el documento", e);
                }

            } catch (Exception e) {
                logger.error("Error al eliminar el documento [" + idDocumento
                        + "]", e);
                throw new GestionDocumentalException(
                        "Error al eliminar el documento", e);
            }
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
//    public List<InfoDocumentoVO> findDocumentos(
//            CriteriosBusquedaVO criteriosBusqueda)
//            throws GestionDocumentalException {
//        return new ArrayList<InfoDocumentoVO>();
//    }

}
