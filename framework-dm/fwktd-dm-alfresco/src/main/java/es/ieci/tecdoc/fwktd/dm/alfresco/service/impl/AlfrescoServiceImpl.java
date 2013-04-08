package es.ieci.tecdoc.fwktd.dm.alfresco.service.impl;

import java.io.InputStream;
import java.io.OutputStream;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.authoring.AuthoringServiceSoapPort;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapPort;
import org.alfresco.webservice.repository.RepositoryServiceSoapPort;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.alfresco.utils.AlfrescoUtils;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.config.TokenEvaluator;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.impl.DefaultGestionDocumentalServiceImpl;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;

public class AlfrescoServiceImpl extends DefaultGestionDocumentalServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(AlfrescoServiceImpl.class);

    private static final String DEFAULT_MODEL_TYPE = Constants.TYPE_CONTENT;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_STORE_NAME_SPACE = "SpacesStore";

    private RepositoryServiceSoapPort repositoryService = null;
    private ContentServiceSoapPort  contentService = null;
    private AuthoringServiceSoapPort authoringService = null;

    private String endPointAddress = null;
    private String user = null;
    private String password = null;
    private String type = DEFAULT_MODEL_TYPE;
    private String aspects = null;
    private String path = null;
    private String encoding = DEFAULT_ENCODING;
    private String storeNamespace = DEFAULT_STORE_NAME_SPACE;


    /**
     * Constructor.
     */
    public AlfrescoServiceImpl() {
        super();
    }

    /**
     * Constructor.
     */
    public AlfrescoServiceImpl(ContentType contentType) {
        super(contentType);
    }

    public RepositoryServiceSoapPort getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryServiceSoapPort repositoryService) {
		this.repositoryService = repositoryService;
	}

	public ContentServiceSoapPort getContentService() {
		return contentService;
	}

	public void setContentService(ContentServiceSoapPort contentService) {
		this.contentService = contentService;
	}

	public AuthoringServiceSoapPort getAuthoringService() {
		return authoringService;
	}

	public void setAuthoringService(AuthoringServiceSoapPort authoringService) {
		this.authoringService = authoringService;
	}

	public String getEndPointAddress() {
        return endPointAddress;
    }

    public void setEndPointAddress(String endPointAddress) {
        this.endPointAddress = endPointAddress;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAspects() {
		return aspects;
	}

	public void setAspects(String aspects) {
		this.aspects = aspects;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getStoreNamespace() {
		return storeNamespace;
	}

	public void setStoreNamespace(String storeNamespace) {
		this.storeNamespace = storeNamespace;
	}

	/**
     * Crea una sesión con el gestor documental.
     *
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void createSesion() throws GestionDocumentalException {

        // Establecer la ruta del API de Alfresco
        WebServiceFactory.setEndpointAddress(getEndPointAddress());

        // Iniciar sesión en Alfresco
        try {
            AuthenticationUtils.startSession(getUser(), getPassword());
        } catch (AuthenticationFault e) {
            logger.error("Error al establecer la conexion", e);
            throw new GestionDocumentalException(e.toString());
        }

        // Obtener los servicios para acceder al repositorio
        setRepositoryService(WebServiceFactory.getRepositoryService());
        setContentService(WebServiceFactory.getContentService());
        setAuthoringService(WebServiceFactory.getAuthoringService());
    }

    /**
     * Cierra la sesión con el gestor documental.
     *
     * @throws GestionDocumentalException
     *             si ocurre algún error.
     */
    public void releaseSesion() throws GestionDocumentalException {

    	// Finalizar sesión con Alfresco
        AuthenticationUtils.endSession();
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
                if (AlfrescoUtils.getContentObject(this, idDocumento) != null) {
                    existe = true;
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

            	// Obtener la información del documento
            	Content content = AlfrescoUtils.getContentObject(this, idDocumento);
            	infoDocumento = AlfrescoUtils.getInfoDocumento(this, content);

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

            try {

				Content content = AlfrescoUtils.getContentObject(this, idDocumento);
				if (content != null) {
					ContentUtils.copy(
							ContentUtils.getContentAsInputStream(content), out);
					logger.info("Fichero copiado al OutputStream");
				} else {
					logger.warn("No se ha encontrado el contenido del documento [{}]", idDocumento);
				}

            } catch (Exception e) {
                logger.error("Error al obtener el contenido del documento [id="
                        + idDocumento + "]", e);
                throw new GestionDocumentalException(
                        "Error al obtener el contenido del documento", e);
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

				// Evaluar el path, por si contiene variables
				String folderPath = TokenEvaluator.evaluateTokenExpression(getContentType(), getPath(), infoDocumento.getMetadatos());

				// Comprobar que exita el path del documento
				AlfrescoUtils.checkFolderPath(this, folderPath);

				// Carga del objeto CML de Alfresco
				CML cml = AlfrescoUtils.getCMLCreate(this, infoDocumento, in);

				// Se inserta el objeto en el repositorio
				UpdateResult[] result = repositoryService.update(cml);
				String uuid = result[0].getDestination().getUuid();
				logger.debug("uuid: {}", uuid);

				// Se asigna el uuid al documento
				infoDocumento.setId(uuid);

				// Obtener la información del documento
				infoDocumento = getInfoDocumento(uuid);

			} catch (Exception e) {
				logger.error("Error al crear el documento", e);
				throw new GestionDocumentalException(
						"Error al crear el documento", e);
			}
		}

        logger.info("Información del documento creado: {}", infoDocumento);

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

//        	Reference workingCopyReference = null;

            try {

				// Carga del objeto CML de Alfresco
				CML cml = AlfrescoUtils.getCMLUpdate(this, infoDocumento, in);

				// Actualiza el objeto en el repositorio
				getRepositoryService().update(cml);
				logger.debug("Información del documento {} actualizada",
						infoDocumento.getNombre());

//				// Actualizar el contenido del documento
//				if (in != null) {
//
//					// Check-out del documento
//					Reference reference = new Reference(AlfrescoUtils.STORE,
//							infoDocumento.getId(), null);
//					Predicate predicate = new Predicate(
//							new Reference[] { reference }, null, null);
//					CheckoutResult checkOutResult = getAuthoringService()
//							.checkout(predicate, null);
//
//					// Get a reference to the working copy
//					workingCopyReference = checkOutResult.getWorkingCopies()[0];
//
//					// Obtener el formato del documento
//					String mimeType = MimeTypeUtils.getMimeType(infoDocumento
//							.getNombre());
//					ContentFormat format = new ContentFormat(mimeType,
//							getEncoding());
//					logger.debug("Tipo MIME: {}", mimeType);
//
//					// Actualizar el contenido
//					getContentService().write(workingCopyReference,
//							Constants.PROP_CONTENT,
//							ContentUtils.convertToByteArray(in), format);
//
//					logger.debug("Contenido del documento {} actualizado",
//							infoDocumento.getNombre());
//
//					// Check-in del documento
//					Predicate workingCopyPredicate = new Predicate(
//							new Reference[] { workingCopyReference }, null, null);
//					NamedValue[] comments = new NamedValue[]{Utils.createNamedValue("description", "The content has been updated")};
//					getAuthoringService()
//							.checkin(workingCopyPredicate, comments, false);
//				}

//				Reference reference = new Reference(AlfrescoUtils.STORE, infoDocumento.getId(), null);
//				VersionHistory versionHistory = getAuthoringService().getVersionHistory(reference);
//				for (Version version : versionHistory.getVersions()) {
//					logger.info("Versión: id=[{}], label=[{}], creator=[{}], created=[{}]", new Object[] {
//							version.getId(),
//							version.getLabel(),
//							version.getCreator(),
//							version.getCreated()});
//					for (NamedValue commentary : version.getCommentaries()) {
//						logger.info("Comentario [{}]: {} => {}", new Object[] {
//								version.getLabel(),
//								commentary.getName(),
//								commentary.getValue()
//						});
//					}
//				}


                // Obtener la información del documento
                infoDocumento = getInfoDocumento(infoDocumento.getId());

            } catch (Exception e) {
                logger.error("Error al actualizar el documento", e);

//				// Cancelar el check-out del documento
//                if (workingCopyReference != null) {
//					try {
//						Predicate workingCopyPredicate = new Predicate(
//								new Reference[] { workingCopyReference }, null,
//								null);
//						getAuthoringService().cancelCheckout(
//								workingCopyPredicate);
//					} catch (Exception e1) {
//						logger.error("Error al cancelar el check-out", e);
//					}
//                }

                throw new GestionDocumentalException("Error al actualizar el documento", e);
            }
        }

        logger.info("Información del documento actualizado: {}", infoDocumento);

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

				// Carga del objeto CML de Alfresco
				CML cml = AlfrescoUtils.getCMLDelete(this, idDocumento);

				// Elimina el objeto en el repositorio
				getRepositoryService().update(cml);

				logger.info("Documento eliminado [{}]", idDocumento);

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
