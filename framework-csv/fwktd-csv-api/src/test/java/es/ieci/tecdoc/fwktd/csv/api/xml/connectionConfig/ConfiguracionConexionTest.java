package es.ieci.tecdoc.fwktd.csv.api.xml.connectionConfig;

import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class ConfiguracionConexionTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void testParseText() throws DocumentException {

		String xml = "<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector><parameters><parameter name='WSDL_LOCATION'>http://localhost:8080/ws</parameter></parameters></connection-config>";

		ConfiguracionConexion configuracionConexion = ConfiguracionConexion.parseText(xml);
		Assert.assertNotNull("No se ha parseado el XML", configuracionConexion);
		Assert.assertEquals("MOCK", configuracionConexion.getConector());
		Assert.assertEquals(1, configuracionConexion.getParametros().size());
		Assert.assertEquals("http://localhost:8080/ws", configuracionConexion.getParametros().get("WSDL_LOCATION"));
	}

}
