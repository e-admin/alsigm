package es.ieci.tecdoc.fwktd.dm.alfresco.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.config.Destination;
import es.ieci.tecdoc.fwktd.dm.business.config.Mapping;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.manager.MetadataManager;

/**
 * Implementación de la gestión de mapeos de metadatos en invesDoc.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AlfrescoMetadataManagerImpl extends MetadataManager {

	private Map<String, Object> documentProperties = null;
	private List<NamedValue> metadata = new ArrayList<NamedValue>();


	/**
	 * Constructor.
	 */
	public AlfrescoMetadataManagerImpl(ContentType contentType) {
		super(contentType);
	}

	/**
	 * Constructor.
	 */
	public AlfrescoMetadataManagerImpl(ContentType contentType, Map<String, Object> props) {
		this(contentType);
		setDocumentProperties(props);
	}

	public Map<String, Object> getDocumentProperties() {
		return documentProperties;
	}

	public void setDocumentProperties(Map<String, Object> documentProperties) {
		this.documentProperties = documentProperties;
	}

	public List<NamedValue> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<NamedValue> metadata) {
		this.metadata = metadata;
	}

	public Object getValorMetadato(Destination destination) throws GestionDocumentalException {

		Object valorMetadato = null;

		if (getDocumentProperties() != null) {
			valorMetadato = getDocumentProperties().get(destination.getValue());
		}

		return valorMetadato;
	}

	public void setMetadato(Mapping mapping, Object valorMetadato) throws GestionDocumentalException {

		Destination destination = mapping.getDestination();

		metadata.add(Utils.createNamedValue(destination.getValue(),
				String.valueOf(valorMetadato)));
	}

}
