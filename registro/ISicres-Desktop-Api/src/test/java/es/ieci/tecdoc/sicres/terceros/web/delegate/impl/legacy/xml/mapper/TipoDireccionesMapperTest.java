package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDirecciones;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author IECISA
 *
 */
public class TipoDireccionesMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		TipoDireccionesMapper mapper = new TipoDireccionesMapper();

		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/tipoDirecciones.xml")
								.toURI()));
		TipoDirecciones tipoDirecciones = mapper.unmarshall(xml);

		Assert.assertNotNull(tipoDirecciones);
		Assert.assertEquals(2, tipoDirecciones.getTipoDirecciones().size());
	}

}
