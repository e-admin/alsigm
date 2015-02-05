package es.ieci.tecdoc.fwktd.csv.ws.service.impl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.ServicioAplicacionesPortType;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-csv-core-applicationContext.xml",
		"/beans/fwktd-csv-api-applicationContext.xml",
		"/beans/fwktd-csv-ws-applicationContext.xml",
		"/beans/fwktd-csv-test-beans.xml" })
public class ServicioAplicacionesWSImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String ID_APLICACION_EXISTENTE = "20000001";
	private static final String ID_APLICACION_BORRAR = "20000002";
	private static final String ID_APLICACION_NO_EXISTENTE = "99999999";

	private static final String CODIGO_APLICACION_EXISTENTE = "APP1";
	private static final String CODIGO_APLICACION_NO_EXISTENTE = "XXXXX";

	@Autowired
	private ServicioAplicacionesWSImpl fwktd_csv_ws_servicioAplicacionesImpl;

	public ServicioAplicacionesPortType getServicioAplicaciones() {
		return fwktd_csv_ws_servicioAplicacionesImpl;
	}

	@Test
	public void testServiceDelegate() {
		Assert.assertNotNull("El servicio es nulo", getServicioAplicaciones());
	}

	@Test
	public void testGetAplicaciones() {

		List<AplicacionCSV> aplicaciones = getServicioAplicaciones().getAplicaciones();
		Assert.assertNotNull("No se han obtenido las aplicaciones", aplicaciones);
		Assert.assertFalse(aplicaciones.isEmpty());
	}

	@Test
	public void testGetAplicacion() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacion(ID_APLICACION_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado la aplicacion", aplicacion);
		Assert.assertEquals(ID_APLICACION_EXISTENTE, aplicacion.getId());
		Assert.assertEquals(CODIGO_APLICACION_EXISTENTE, aplicacion.getCodigo());
		Assert.assertEquals("Aplicación 1", aplicacion.getNombre());
		Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>", aplicacion.getInfoConexion());
	}

	@Test
	public void testGetAplicacion_IdNulo() {

		try {
			getServicioAplicaciones().getAplicacion(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetAplicacion_IdNoExistente() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacion(ID_APLICACION_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado la aplicación", aplicacion);
	}

	@Test
	public void testGetAplicacionByCodigo() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacionByCodigo(CODIGO_APLICACION_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado la aplicacion", aplicacion);
		Assert.assertEquals(ID_APLICACION_EXISTENTE, aplicacion.getId());
		Assert.assertEquals(CODIGO_APLICACION_EXISTENTE, aplicacion.getCodigo());
		Assert.assertEquals("Aplicación 1", aplicacion.getNombre());
		Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>", aplicacion.getInfoConexion());
	}

	@Test
	public void testGetAplicacionByCodigo_CodigoNulo() {

		try {
			getServicioAplicaciones().getAplicacionByCodigo(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'codigo' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetAplicacionByCodigo_CodigoNoExistente() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacionByCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado la aplicación", aplicacion);
	}

	@Test
	public void testSaveAplicacion() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacionForm.setNombre("Aplicación nueva");
		aplicacionForm.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionCSV aplicacionCreada = getServicioAplicaciones().saveAplicacion(aplicacionForm);
		Assert.assertNotNull("No se ha creado la aplicación", aplicacionCreada);
		Assert.assertNotNull("No se ha creado el ID de la aplicación", aplicacionCreada.getId());
		Assert.assertEquals(aplicacionForm.getCodigo(), aplicacionCreada.getCodigo());
		Assert.assertEquals(aplicacionForm.getNombre(), aplicacionCreada.getNombre());
		Assert.assertEquals(aplicacionForm.getInfoConexion(), aplicacionCreada.getInfoConexion());
	}

	@Test
	public void testSaveAplicacion_FormVacio() {

		try {
			getServicioAplicaciones().saveAplicacion(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion' must not be null", e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_CodigoVacio() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(null);
		aplicacionForm.setNombre("Aplicación nueva");
		aplicacionForm.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		try {
			getServicioAplicaciones().saveAplicacion(aplicacionForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.codigo' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_NombreVacio() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacionForm.setNombre(null);
		aplicacionForm.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		try {
			getServicioAplicaciones().saveAplicacion(aplicacionForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.nombre' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_InfoConexionVacio() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacionForm.setNombre("Aplicación nueva");
		aplicacionForm.setInfoConexion(null);

		try {
			getServicioAplicaciones().saveAplicacion(aplicacionForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.infoConexion' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testUpdateAplicacion() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacion(ID_APLICACION_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado la aplicación", aplicacion);

		// Modificar la información de la aplicación
		aplicacion.setNombre("Nombre modificado");

		// Guardar la información de la aplicación
		AplicacionCSV aplicacionModificada = getServicioAplicaciones().updateAplicacion(aplicacion);
		Assert.assertNotNull("No se ha modificado la aplicación", aplicacionModificada);

		Assert.assertEquals(aplicacion.getId(), aplicacionModificada.getId());
		Assert.assertEquals(aplicacion.getCodigo(), aplicacionModificada.getCodigo());
		Assert.assertEquals(aplicacion.getNombre(), aplicacionModificada.getNombre());
		Assert.assertEquals(aplicacion.getInfoConexion(), aplicacionModificada.getInfoConexion());

		aplicacion = getServicioAplicaciones().getAplicacion(ID_APLICACION_EXISTENTE);
		Assert.assertEquals(aplicacion.getId(), aplicacionModificada.getId());
		Assert.assertEquals(aplicacion.getCodigo(), aplicacionModificada.getCodigo());
		Assert.assertEquals(aplicacion.getNombre(), aplicacionModificada.getNombre());
		Assert.assertEquals(aplicacion.getInfoConexion(), aplicacionModificada.getInfoConexion());
	}

	@Test
	public void testUpdateAplicacion_InfoVacia() {

		try {
			getServicioAplicaciones().updateAplicacion(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion' must not be null", e.getMessage());
		}
	}

	@Test
	public void testUpdateAplicacion_IdNoExistente() {

		try {

			AplicacionCSV aplicacion = new AplicacionCSV();
			aplicacion.setId(ID_APLICACION_NO_EXISTENTE);
			aplicacion.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
			aplicacion.setNombre("Aplicación nueva");
			aplicacion.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

			getServicioAplicaciones().updateAplicacion(aplicacion);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testDeleteAplicacion() {

		getServicioAplicaciones().deleteAplicacion(ID_APLICACION_BORRAR);
		Assert.assertNull("No se ha borrado la aplicacion", getServicioAplicaciones().getAplicacion(ID_APLICACION_BORRAR));

		getServicioAplicaciones().deleteAplicacion(ID_APLICACION_NO_EXISTENTE);
	}
}
