package es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.AplicacionExternaConnector;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;

@ContextConfiguration({
    "/beans/fwktd-csv-api-applicationContext.xml",
    "/beans/fwktd-csv-test-beans.xml" })
public class WebServiceConnectorImplIntegrationTest extends AbstractJUnit4SpringContextTests {

    private static final String CSV_EXISTENTE = "CSV_001";
    private static final String CSV_NO_EXISTENTE = "XXX";

    @Autowired
    private AplicacionExternaConnector fwktd_csv_webServiceAplicacionExternaConnectorImpl;

    public AplicacionExternaConnector getAplicacionExternaConnector() {
        return fwktd_csv_webServiceAplicacionExternaConnectorImpl;
    }

    @Test
    public void testConector() {
        Assert.assertNotNull("El conector es nulo", getAplicacionExternaConnector());
    }

    @Test
    public void testExisteDocumento() {

    	boolean res;

    	try {
    		res = getAplicacionExternaConnector().existeDocumento(CSV_EXISTENTE, null);
    		Assert.fail("Debería lanzar la excepción");
    	} catch (CSVException e) {
    		Assert.assertEquals("error.csv.connector.aplicacionExterna.configError", e.getMessageId());
    	}

    	try {
    		res = getAplicacionExternaConnector().existeDocumento(CSV_EXISTENTE, new HashMap<String, String>());
    		Assert.fail("Debería lanzar la excepción");
    	} catch (CSVException e) {
    		Assert.assertEquals("error.csv.connector.aplicacionExterna.configError", e.getMessageId());
    	}

        res = getAplicacionExternaConnector().existeDocumento(CSV_EXISTENTE, getWebServiceParameters());
        Assert.assertEquals(false, res);

        res = getAplicacionExternaConnector().existeDocumento(CSV_NO_EXISTENTE, getWebServiceParameters());
        Assert.assertEquals(false, res);
    }

    @Test
    public void testGetContenidoDocumento() {

        byte[] contenido = getAplicacionExternaConnector().getContenidoDocumento(CSV_EXISTENTE, getWebServiceParameters());
        Assert.assertNull("El contenido debería ser nulo", contenido);

        contenido = getAplicacionExternaConnector().getContenidoDocumento(CSV_NO_EXISTENTE, getWebServiceParameters());
        Assert.assertNull("El contenido debería ser nulo", contenido);
    }

    @Test
    public void testWriteDocumento() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            getAplicacionExternaConnector().writeDocumento(CSV_EXISTENTE, baos, getWebServiceParameters());
            Assert.assertTrue("El contenido debería ser nulo", baos.size() == 0);

        } catch (IOException e) {
            Assert.fail("Error al obtener el documento: " + e.toString());
            e.printStackTrace();
        }

        try {
            getAplicacionExternaConnector().writeDocumento(CSV_NO_EXISTENTE, baos, getWebServiceParameters());
            Assert.assertTrue("El contenido debería ser nulo", baos.size() == 0);

        } catch (IOException e) {
            Assert.fail("Error al obtener el documento: " + e.toString());
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map<String, String> getWebServiceParameters() {

    	Properties props = new Properties();
    	try {
			props.load(getClass().getClassLoader().getResourceAsStream("WebServiceConnectorImplTest.properties"));
		} catch (IOException e) {
			Assert.fail("Error al cargar los parámetros del servicio web: " + e.toString());
			e.printStackTrace();
		}

    	return new HashMap(props);
    }
}
