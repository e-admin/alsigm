package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.PersonasMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Personas;

/**
 *
 * @author IECISA
 *
 */
public class PersonasMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/personas.xml")
								.toURI()));
		PersonasMapper mapper = new PersonasMapper();
		Personas personas = (Personas) mapper.unmarshall(xml);

		Assert.assertNotNull(personas);

		Assert.assertEquals(2, personas.getPersonas().size());
	}
}
