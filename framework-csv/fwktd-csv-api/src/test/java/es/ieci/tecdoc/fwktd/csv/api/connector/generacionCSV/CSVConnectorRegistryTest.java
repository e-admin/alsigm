package es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class CSVConnectorRegistryTest extends AbstractJUnit4SpringContextTests {

	private static final String ID_CONECTOR = "DEFAULT_CSV_CONNECTOR";

	@Autowired
	private CSVConnectorRegistry fwktd_csv_csvConnectorRegistry;

	@Test
	public void testConnectorRegistry() {
		Assert.assertNotNull("El registro es nulo", fwktd_csv_csvConnectorRegistry);
	}

	@Test
	public void testRegisterConector() {

		List<CSVConnector> conectores = fwktd_csv_csvConnectorRegistry.getConectores();
		int count = conectores.size();

		fwktd_csv_csvConnectorRegistry.registerConector(new CSVConnector() {

			public String getUid() {
				return "MOCK";
			}

			public String generarCSV(InfoDocumentoCSVForm infoDocumentoForm) {
				return null;
			}
		});

		conectores = fwktd_csv_csvConnectorRegistry.getConectores();
		Assert.assertEquals(count + 1, conectores.size());
	}

	@Test
	public void testGetConectores() {

		List<CSVConnector> conectores = fwktd_csv_csvConnectorRegistry.getConectores();
		Assert.assertNotNull("La lista de conectores es nula", conectores);
		Assert.assertFalse("La lista de conectores está vacía", conectores.isEmpty());

		for (CSVConnector conector : conectores) {
			Assert.assertNotNull("El conector es nulo", conector);
		}
	}

	@Test
	public void testGetConector() {

		CSVConnector conector = (CSVConnector) fwktd_csv_csvConnectorRegistry.getConector(ID_CONECTOR);
		Assert.assertNotNull("El conector es nulo", conector);
		Assert.assertEquals(ID_CONECTOR, conector.getUid());
	}

}

