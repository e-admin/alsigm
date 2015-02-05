package es.ieci.tecdoc.fwktd.dm.bd.manager.impl;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.config.Destination;
import es.ieci.tecdoc.fwktd.dm.business.config.Mapping;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.manager.MetadataManager;

/**
 * Implementación de la gestión de mapeos de metadatos en base de datos.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseDatosMetadataManagerImpl extends MetadataManager {

    private static final String DEFAULT_XML_ENCODING = "UTF-8";
    private static final String DEFAULT_XML_VERSION = "1.0";
    private static final String TAG_METADATOS = "metadatos";
    private static final String TAG_METADATO = "metadato";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_VALOR = "valor";
    private static final String ATT_VERSION = "version";


	/**
	 * XML con los metadatos
	 */
	private Document document = null;


	/**
	 * Constructor.
	 */
	public BaseDatosMetadataManagerImpl(ContentType contentType) {
		this(contentType, null);
	}

	/**
	 * Constructor.
	 * @param doc XML con los metadatos.
	 */
	public BaseDatosMetadataManagerImpl(ContentType contentType, Document doc) {
		super(contentType);

		if (doc == null) {

			doc = DocumentHelper.createDocument();
			doc.setXMLEncoding(DEFAULT_XML_ENCODING);

			Element root = doc.addElement(TAG_METADATOS);
			root.addAttribute(ATT_VERSION, DEFAULT_XML_VERSION);
		}

		setDocument(doc);
	}

	public String getDocumentAsXML() {
		String xml = null;
		if (document != null) {
			xml = document.asXML();
		}
		return xml;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Object getValorMetadato(Destination destination) throws GestionDocumentalException {

		String valorMetadato = null;

		if (getDocument() != null) {

			Node node = getDocument().selectSingleNode("/" + TAG_METADATOS
					+ "/" + TAG_METADATO + "[" + TAG_NOMBRE + "='"
					+ destination.getValue() + "']");
    		if (node != null) {
				valorMetadato = node.valueOf(TAG_VALOR);
    		}
		}

		return valorMetadato;
	}

	public void setMetadato(Mapping mapping, Object valor) throws GestionDocumentalException {

		Destination destination = mapping.getDestination();
		if (destination != null) {
			Element nodoMetadatos = (Element) document.selectSingleNode("/" + TAG_METADATOS);
			if (nodoMetadatos != null) {
				Element elem = nodoMetadatos.addElement(TAG_METADATO);
				elem.addElement(TAG_NOMBRE).addCDATA(destination.getValue());
				elem.addElement(TAG_VALOR).addCDATA((valor != null ? String.valueOf(valor) : ""));
			}
		}
	}

}