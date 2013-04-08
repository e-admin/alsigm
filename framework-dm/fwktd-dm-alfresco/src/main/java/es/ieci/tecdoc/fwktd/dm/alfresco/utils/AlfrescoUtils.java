package es.ieci.tecdoc.fwktd.dm.alfresco.utils;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.CMLDelete;
import org.alfresco.webservice.types.CMLUpdate;
import org.alfresco.webservice.types.CMLWriteContent;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.alfresco.manager.impl.AlfrescoMetadataManagerImpl;
import es.ieci.tecdoc.fwktd.dm.alfresco.service.impl.AlfrescoServiceImpl;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.util.ConvertUtils;
import es.ieci.tecdoc.fwktd.dm.business.util.MimeTypeUtils;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;


/**
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AlfrescoUtils {

	private static final Logger logger = LoggerFactory.getLogger(AlfrescoUtils.class);

	public static Content getContentObject(AlfrescoServiceImpl service,
			String idDocumento) throws ContentFault, RemoteException {

		Content content = null;

		if (StringUtils.isNotBlank(idDocumento)) {
			try {
				Store STORE = new Store(Constants.WORKSPACE_STORE, service.getStoreNamespace());
				Reference reference = new Reference(STORE, idDocumento, null);
				Content[] readResult = service.getContentService().read(
						new Predicate(new Reference[] { reference }, STORE, null), Constants.PROP_CONTENT);
				if (readResult != null) {
					content = (Content) readResult[0];
				}
			} catch (ContentFault fault) {
				// FIXME
				if (!fault.getMessage1().startsWith("Node does not exist")) {
					throw fault;
				}
			}
		}

		return content;
	}

	public static InfoDocumentoVO getInfoDocumento(AlfrescoServiceImpl service,
			Content content) throws RepositoryFault, RemoteException,
			GestionDocumentalException {

		InfoDocumentoVO infoDocumento = null;

		if (content != null) {

			infoDocumento = new InfoDocumentoVO();
			infoDocumento.setId(content.getNode().getUuid());
			infoDocumento.setTipoMime(content.getFormat().getMimetype());
			infoDocumento.setTamano(content.getLength());

			Map<String, Object> props = getDocumentProperties(service, content);

			infoDocumento.setNombre(String.valueOf(props
					.get(Constants.PROP_TITLE)));

			// TODO Mejorar esto
			infoDocumento.setFechaCreacion(ConvertUtils.toDate(
					String.valueOf(props.get(Constants.PROP_CREATED)),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZZ", null));
			infoDocumento.setFechaModificacion(ConvertUtils.toDate(
					String.valueOf(props.get("{http://www.alfresco.org/model/content/1.0}modified")),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZZ", null));

			AlfrescoMetadataManagerImpl metadataManager = new AlfrescoMetadataManagerImpl(
					service.getContentType(), props);
			infoDocumento.setMetadatos(metadataManager.getMetadatos());
		}

		return infoDocumento;
	}

	public static Map<String, Object> getDocumentProperties(
			AlfrescoServiceImpl service, Content content)
			throws RepositoryFault, RemoteException {

		Map<String, Object> metadata = new HashMap<String, Object>();

		if (content != null) {
			Predicate predicate = new Predicate(new Reference[] { content.getNode() }, null, null);
			Node[] nodes = service.getRepositoryService().get(predicate);
			if (nodes != null) {
				for (Node node : nodes) {
					NamedValue[] properties = node.getProperties();
					if (properties != null) {
						for (NamedValue property : properties) {
							if (property.getIsMultiValue()) {
								metadata.put(property.getName(), property.getValues());
								logger.debug("propiedad: {} = {}",
										property.getName(), property.getValue());
							} else {
								metadata.put(property.getName(), property.getValue());
								logger.debug("propiedad: {} = {}",
										property.getName(), property.getValues());
							}
						}
					}
				}
			}
		}

		return metadata;
	}

	public static CML getCMLCreate(AlfrescoServiceImpl service,
			InfoDocumentoVO infoDocumento, InputStream in) throws Exception {

		// Metadatos
		NamedValue[] metadata = getMetadataNamedValues(service, infoDocumento);

		// Carga del objeto CML
		Store STORE = new Store(Constants.WORKSPACE_STORE, service.getStoreNamespace());
		ParentReference parentReference = new ParentReference(STORE, null,
				service.getPath(), Constants.ASSOC_CONTAINS,
				Constants.ASSOC_CONTAINS);
		CMLCreate cmlCreate = new CMLCreate("1", parentReference,
				parentReference.getUuid(), Constants.ASSOC_CONTAINS, null,
				service.getType(), metadata);

		CML cml = new CML();
		cml.setCreate(new CMLCreate[] { cmlCreate });
		cml.setAddAspect(getCMLAddAspect(service.getAspects()));

		// Contenido del documento
		if (in != null) {
			String mimeType = MimeTypeUtils.getMimeType(infoDocumento.getNombre());
			ContentFormat format = new ContentFormat(mimeType, service.getEncoding());

			CMLWriteContent cmlWriteContent = new CMLWriteContent(
					Constants.PROP_CONTENT,
					ContentUtils.convertToByteArray(in), format, null, "1");

			cml.setWriteContent(new CMLWriteContent[] { cmlWriteContent });
		}

		return cml;
	}

	public static CML getCMLUpdate(AlfrescoServiceImpl service,
			InfoDocumentoVO infoDocumento, InputStream in) throws Exception {

        // Metadatos
        NamedValue[] metadata = getMetadataNamedValues(service, infoDocumento);

        // Carga del objeto CML
        Store STORE = new Store(Constants.WORKSPACE_STORE, service.getStoreNamespace());
        Reference reference = new Reference(STORE, infoDocumento.getId(), null);
        Predicate predicate = new Predicate(new Reference[] { reference },
                null, null);
		CMLUpdate cmlUpdate = new CMLUpdate(metadata, predicate, null);

        CML cml = new CML();
        cml.setUpdate(new CMLUpdate[] { cmlUpdate });

		// Contenido del documento
		if (in != null) {
			String mimeType = MimeTypeUtils.getMimeType(infoDocumento.getNombre());
			ContentFormat format = new ContentFormat(mimeType, service.getEncoding());

			CMLWriteContent cmlWriteContent = new CMLWriteContent(
					Constants.PROP_CONTENT,
					ContentUtils.convertToByteArray(in), format, predicate, null);

			cml.setWriteContent(new CMLWriteContent[] { cmlWriteContent });
		}

         return cml;
    }

    public static CML getCMLDelete(AlfrescoServiceImpl service, String id) {

    	Store STORE = new Store(Constants.WORKSPACE_STORE, service.getStoreNamespace());
        Reference reference = new Reference(STORE, id, null);
        Predicate predicate = new Predicate(new Reference[] { reference },
                null, null);
        CMLDelete delete = new CMLDelete(predicate);

        CML cml = new CML();
        cml.setDelete(new CMLDelete[] { delete });

        return cml;
    }

	public static NamedValue[] getMetadataNamedValues(AlfrescoServiceImpl service, InfoDocumentoVO infoDocumento) {

		List<NamedValue> metadata = new ArrayList<NamedValue>();

		if (infoDocumento != null) {

			// Nombre del documento
			metadata.add(Utils.createNamedValue(Constants.PROP_NAME, System.currentTimeMillis() + "_" + infoDocumento.getNombre()));
			metadata.add(Utils.createNamedValue(Constants.PROP_TITLE, infoDocumento.getNombre()));

			try {

				AlfrescoMetadataManagerImpl metadataManager = new AlfrescoMetadataManagerImpl(service.getContentType());
				metadataManager.setMetadatos(infoDocumento.getMetadatos());
				metadata.addAll(metadataManager.getMetadata());

			} catch (GestionDocumentalException e) {
				logger.warn("Error al componer los metadatos", e);
			}
		}

		return (NamedValue[]) metadata.toArray(new NamedValue[metadata.size()]);
	}

    public static CMLAddAspect[] getCMLAddAspect(String aspectos) {

        List<CMLAddAspect> addAspects = new ArrayList<CMLAddAspect>();

        // Añadir el aspecto genérico de Alfresco: nombre, descripcion, fecha
		addAspects.add(new CMLAddAspect(Constants.ASPECT_TITLED, null, null, "1"));

        // Añadir el aspecto versionable
		addAspects.add(new CMLAddAspect(Constants.ASPECT_VERSIONABLE, null, null, "1"));

		// Añadir los aspectos específicos del tipo de contenido
		if (StringUtils.isNotBlank(aspectos)) {
			String[] aspectosArray = StringUtils.split(aspectos, ",");
			for (String aspecto : aspectosArray) {
				if (aspecto != null) {
					addAspects.add(new CMLAddAspect(aspecto.trim(), null, null, "1"));
				}
			}
		}

        return (CMLAddAspect[]) addAspects.toArray(new CMLAddAspect[addAspects.size()]);
    }

	public static void checkFolderPath(AlfrescoServiceImpl service,
			String folderPath) throws RepositoryFault, RemoteException {

    	if (StringUtils.isNotBlank(folderPath)) {

    		try {

    			// Comprobar si existe el espacio
    			Store STORE = new Store(Constants.WORKSPACE_STORE, service.getStoreNamespace());
				Reference reference = new Reference(STORE, null, folderPath);
				service.getRepositoryService().get(new Predicate(new Reference[] { reference }, STORE, null));

				logger.info("folderPath existente [{}]", folderPath);

    		} catch (Exception e) {

				logger.info("creando folderPath [{}]...", folderPath);

    			String parentFolderPath = getParentFolderPath(folderPath);
    			logger.info("parentFolderPath: {}", parentFolderPath);

    			// Comprobar que exista el espacio padre
    			checkFolderPath(service, parentFolderPath);

    			String spaceName = getSpaceName(folderPath);
    			logger.info("spaceName: {}", spaceName);

    			// Crear el espacio
    			Store STORE = new Store(Constants.WORKSPACE_STORE, service.getStoreNamespace());
    			ParentReference parentReference = new ParentReference(STORE, null,
    					parentFolderPath, Constants.ASSOC_CONTAINS,
    					Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, spaceName));

    			CMLCreate create = new CMLCreate("1", parentReference, null, null, null,
    					Constants.TYPE_FOLDER, new NamedValue[] {
    							Utils.createNamedValue(Constants.PROP_NAME, spaceName),
    							Utils.createNamedValue(Constants.PROP_TITLE, spaceName),
    							Utils.createNamedValue(Constants.PROP_DESCRIPTION, spaceName) });

    			CML cml = new CML();
    			cml.setCreate(new CMLCreate[] { create });
    			service.getRepositoryService().update(cml);

				logger.info("folderPath creado [{}]", folderPath);
    		}
    	}
    }

	private static String getParentFolderPath(String folderPath) {
		return folderPath.substring(0, folderPath.lastIndexOf("/"));
	}

	private static String getSpaceName(String folderPath) {
		return folderPath.substring(folderPath.lastIndexOf(":") + 1);
	}

}
