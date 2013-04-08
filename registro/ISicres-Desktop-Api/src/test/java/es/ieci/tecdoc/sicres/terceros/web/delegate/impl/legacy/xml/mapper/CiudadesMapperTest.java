package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Ciudades;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author IECISA
 *
 */
public class CiudadesMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		CiudadesMapper mapper = new CiudadesMapper();
		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/ciudades.xml")
								.toURI()));

		Ciudades ciudades = mapper.unmarshall(xml);

		Assert.assertNotNull(ciudades);
		Assert.assertEquals(2, ciudades.getCiudades().size());
	}
}
