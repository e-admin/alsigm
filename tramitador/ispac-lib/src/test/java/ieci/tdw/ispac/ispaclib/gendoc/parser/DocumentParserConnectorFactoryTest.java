package ieci.tdw.ispac.ispaclib.gendoc.parser;

import ieci.tdw.ispac.api.errors.ISPACException;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class DocumentParserConnectorFactoryTest extends TestCase {

	public void testGetTemplateConnector() throws ISPACException {
		IDocumentParserConnector connector = DocumentParserConnectorFactory.getTemplateConnector(null);
		Assert.assertNotNull("No se ha obtenido el conector", connector);
	}
}
