package ieci.tdw.ispac.ispaclib.gendoc.parser.openoffice;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.entity.DocumentData;
import ieci.tdw.ispac.ispaclib.gendoc.openoffice.OpenOfficeHelper;
import ieci.tdw.ispac.ispaclib.gendoc.parser.DocumentParser;
import ieci.tdw.ispac.ispaclib.gendoc.parser.DocumentParserPool;
import ieci.tdw.ispac.ispaclib.gendoc.parser.IDocumentParserConnector;
import ieci.tdw.ispac.ispaclib.tageval.TagTranslator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Implementación del conector de parseo de documentos con OpenOffice.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class OpenOfficeDocumentParserConnectorImpl implements
		IDocumentParserConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(OpenOfficeDocumentParserConnectorImpl.class);

	/**
	 * Contexto de cliente.
	 */
	private ClientContext clientContext = null;

	/**
	 * Constructor.
	 */
	public OpenOfficeDocumentParserConnectorImpl(ClientContext ctx) {
		super();
		setClientContext(ctx);
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	/**
	 * @see ieci.tdw.ispac.ispaclib.templates.IDocumentParserConnector#isSupported(java.lang.String)
	 */
	public boolean isSupported(String mimeType) throws ISPACException {

		if (logger.isInfoEnabled()) {
			logger.info("Comprobando el soporte del tipo MIME: " + mimeType);
		}

		// Comprobar el tipo MIME en la lista de tipos MIME soportados
		return OpenOfficeHelper.isMimeTypeSupportedForMerging(mimeType);
	}

	/**
	 * @see ieci.tdw.ispac.ispaclib.templates.IDocumentParserConnector#mergeDocument(java.lang.String,
	 *      java.lang.String, ieci.tdw.ispac.ispaclib.entity.DocumentData)
	 */
	public void mergeDocument(String templateURL, String documentURL,
			DocumentData documentData) throws ISPACException {

		if (logger.isInfoEnabled()) {
			logger.info("Generando el documento [" + documentURL
					+ "] a partir de la plantilla [" + templateURL + "]");
		}

		TagTranslator tagtranslate = new TagTranslator(getClientContext(),
				documentData);

		DocumentParserPool factory = DocumentParserPool.getInstance();
		DocumentParser document = factory.get();

		try {

			document.mergeDocument(templateURL, documentURL, tagtranslate,
					documentData.getMimeType());
		} finally {
			factory.release(document);
		}
	}

}
