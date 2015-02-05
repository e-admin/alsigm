package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Provincias;

/**
 *
 * @author IECISA
 *
 */
public class ProvinciasMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		ProvinciasMapper mapper = new ProvinciasMapper();
		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/provincias.xml")
								.toURI()));

		Provincias provincias = (Provincias) mapper.unmarshall(xml);

		Assert.assertNotNull(provincias);
		Assert.assertEquals(2, provincias.getProvincias().size());
	}
}
