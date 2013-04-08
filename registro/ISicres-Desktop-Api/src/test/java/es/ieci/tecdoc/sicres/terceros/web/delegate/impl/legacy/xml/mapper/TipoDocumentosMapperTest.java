package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDocumentos;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author IECISA
 *
 */
public class TipoDocumentosMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		TipoDocumentosMapper mapper = new TipoDocumentosMapper();
		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/tipoDocumentos.xml")
								.toURI()));
		TipoDocumentos tipoDocumentos = mapper.unmarshall(xml);

		Assert.assertNotNull(tipoDocumentos);
		Assert.assertEquals(2, tipoDocumentos.getTipoDocumentos().size());
	}
}
