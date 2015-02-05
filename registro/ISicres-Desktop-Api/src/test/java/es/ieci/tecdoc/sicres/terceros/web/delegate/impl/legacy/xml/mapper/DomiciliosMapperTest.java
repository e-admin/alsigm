package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Domicilios;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author IECISA
 *
 */
public class DomiciliosMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		DomiciliosMapper mapper = new DomiciliosMapper();
		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/domicilios.xml")
								.toURI()));

		Domicilios domicilios = mapper.unmarshall(xml);

		Assert.assertNotNull(domicilios);
		Assert.assertEquals(2, domicilios.getDomicilios().size());
	}
}
