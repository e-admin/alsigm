package es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV.impl;

import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV.CSVConnector;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class CSVConnectorImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CSVConnector fwktd_csv_csvConnectorImpl;

	public CSVConnector getCSVConnector() {
		return fwktd_csv_csvConnectorImpl;
	}

	@Test
	public void testConector() {
		Assert.assertNotNull("El conector es nulo", getCSVConnector());
	}

	@Test
	public void testGenerarCSV() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime("application/pdf");
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		String csv = getCSVConnector().generarCSV(infoDocumentoForm);
		Assert.assertTrue("El CSV no es válido", StringUtils.isNotBlank(csv));
	}
}
