package es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class AplicacionExternaConnectorRegistryTest extends AbstractJUnit4SpringContextTests {

	private static final String ID_CONECTOR = "MOCK";

	@Autowired
	private AplicacionExternaConnectorRegistry fwktd_csv_aplicacionExternaConnectorRegistry;

	@Test
	public void testConnectorRegistry() {
		Assert.assertNotNull("El registro es nulo", fwktd_csv_aplicacionExternaConnectorRegistry);
	}

	@Test
	public void testRegisterConector() {

		List<AplicacionExternaConnector> conectores = fwktd_csv_aplicacionExternaConnectorRegistry.getConectores();
		int count = conectores.size();

		fwktd_csv_aplicacionExternaConnectorRegistry.registerConector(new AplicacionExternaConnector() {

			public String getUid() {
				return "MOCK2";
			}

			public boolean existeDocumento(String csv, Map<String, String> params) {
				return false;
			}

			public byte[] getContenidoDocumento(String csv, Map<String, String> params) {
				return null;
			}

			public void writeDocumento(String csv, OutputStream outputStream, Map<String, String> params)
					throws IOException {
			}
		});

		conectores = fwktd_csv_aplicacionExternaConnectorRegistry.getConectores();
		Assert.assertEquals(count + 1, conectores.size());
	}

	@Test
	public void testGetConectores() {

		List<AplicacionExternaConnector> conectores = fwktd_csv_aplicacionExternaConnectorRegistry.getConectores();
		Assert.assertNotNull("La lista de conectores es nula", conectores);
		Assert.assertFalse("La lista de conectores está vacía", conectores.isEmpty());

		for (AplicacionExternaConnector conector : conectores) {
			Assert.assertNotNull("El conector es nulo", conector);
		}
	}

	@Test
	public void testGetConector() {

		AplicacionExternaConnector conector = (AplicacionExternaConnector) fwktd_csv_aplicacionExternaConnectorRegistry.getConector(ID_CONECTOR);
		Assert.assertNotNull("El conector es nulo", conector);
		Assert.assertEquals(ID_CONECTOR, conector.getUid());
	}

}
