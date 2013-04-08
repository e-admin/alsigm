package ieci.tdw.ispac.ispaclib.gendoc.parser.openoffice;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.entity.DocumentData;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class OpenOfficeDocumentParserConnectorImplTest extends TestCase {

	public void testIsSupported() throws ISPACException {
		
		OpenOfficeDocumentParserConnectorImpl connector = new OpenOfficeDocumentParserConnectorImpl(getClientContext());
		
		Assert.assertTrue(connector.isSupported("application/msword"));
		Assert.assertTrue(connector.isSupported("application/vnd.oasis.opendocument.text"));
		
		Assert.assertFalse(connector.isSupported("application/pdf"));
	}

	public void testMergeDocumentDOC() throws ISPACException, IOException {

		URL plantillaURL = this.getClass().getClassLoader().getResource("plantilla.doc");
		
		DocumentData documentData = new DocumentData();
		documentData.setNumExp("EXP2011/000001");
		documentData.setMimeType("application/msword");
		
		OpenOfficeDocumentParserConnectorImpl connector = new OpenOfficeDocumentParserConnectorImpl(getClientContext());

		File doc = File.createTempFile("tmp_", ".doc");

		String templateURL = StringUtils.replace(plantillaURL.toString(), "file:/", "file:///"); 
		String documentURL = StringUtils.replace(doc.toURL().toExternalForm(), "file:/", "file:///");
		
		try {
			connector.mergeDocument(templateURL, documentURL, documentData);
			Assert.assertTrue("El fichero no existe", doc.isFile());
		} finally {
			doc.delete();	
		}
	}

	public void testMergeDocumentODT() throws ISPACException, IOException {

		URL plantillaURL = this.getClass().getClassLoader().getResource("plantilla.odt");
		
		DocumentData documentData = new DocumentData();
		documentData.setNumExp("EXP2011/000001");
		documentData.setMimeType("application/vnd.oasis.opendocument.text");
		
		OpenOfficeDocumentParserConnectorImpl connector = new OpenOfficeDocumentParserConnectorImpl(getClientContext());

		File doc = File.createTempFile("tmp_", ".odt");

		String templateURL = StringUtils.replace(plantillaURL.toString(), "file:/", "file:///"); 
		String documentURL = StringUtils.replace(doc.toURL().toExternalForm(), "file:/", "file:///");
		
		try {
			connector.mergeDocument(templateURL, documentURL, documentData);
			Assert.assertTrue("El fichero no existe", doc.isFile());
		} finally {
			doc.delete();	
		}
	}
	
	private ClientContext getClientContext() {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		
		return ctx;
	}
}
