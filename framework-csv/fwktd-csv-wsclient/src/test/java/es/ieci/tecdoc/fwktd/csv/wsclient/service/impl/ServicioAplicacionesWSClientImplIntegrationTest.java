package es.ieci.tecdoc.fwktd.csv.wsclient.service.impl;

import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;

@ContextConfiguration({
    "/beans/fwktd-csv-wsclient-applicationContext.xml" })
public class ServicioAplicacionesWSClientImplIntegrationTest extends AbstractJUnit4SpringContextTests {

	private static final String ID_APLICACION_NO_EXISTENTE = "99999999";
	private static final String CODIGO_APLICACION_NO_EXISTENTE = "XXXXX";

    @Autowired
    private ServicioAplicaciones fwktd_csv_servicioAplicacionesWSClientImpl;

	protected ServicioAplicaciones getServicioAplicaciones() {
		return fwktd_csv_servicioAplicacionesWSClientImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull("El servicio es nulo", getServicioAplicaciones());
	}

	@Test
	public void testGetAplicaciones() {

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				"APP" + ID_APLICACION_NO_EXISTENTE,
				"Aplicacion " + ID_APLICACION_NO_EXISTENTE,
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
		AplicacionCSV aplicacion = getServicioAplicaciones().saveAplicacion(aplicacionForm);

		try {
			List<AplicacionCSV> aplicaciones = getServicioAplicaciones().getAplicaciones();
			Assert.assertNotNull("No se han obtenido las aplicaciones", aplicaciones);
			Assert.assertFalse(aplicaciones.isEmpty());

			for (AplicacionCSV app : aplicaciones) {
				if (aplicacion.getId().equals(app.getId())) {
					assertEquals(aplicacion, app);
				}
			}

		} finally {
			getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
		}
	}

	@Test
	public void testGetAplicacion() {

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				"APP" + ID_APLICACION_NO_EXISTENTE,
				"Aplicacion " + ID_APLICACION_NO_EXISTENTE,
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
		AplicacionCSV aplicacion = getServicioAplicaciones().saveAplicacion(aplicacionForm);

		try {
			AplicacionCSV app = getServicioAplicaciones().getAplicacion(aplicacion.getId());
			Assert.assertNotNull("No se ha encontrado la aplicacion", app);

			assertEquals(aplicacion, app);

		} finally {
			getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
		}
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

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				"APP" + ID_APLICACION_NO_EXISTENTE,
				"Aplicacion " + ID_APLICACION_NO_EXISTENTE,
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
		AplicacionCSV aplicacion = getServicioAplicaciones().saveAplicacion(aplicacionForm);

		try {
			AplicacionCSV app = getServicioAplicaciones().getAplicacionByCodigo(aplicacion.getCodigo());
			Assert.assertNotNull("No se ha encontrado la aplicacion", app);

			assertEquals(aplicacion, app);

		} finally {
			getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
		}
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

		AplicacionCSV aplicacion = getServicioAplicaciones().saveAplicacion(aplicacionForm);

		try {
			Assert.assertNotNull("No se ha creado la aplicación", aplicacion);
			Assert.assertNotNull("No se ha creado el ID de la aplicación", aplicacion.getId());
			Assert.assertEquals(aplicacionForm.getCodigo(), aplicacion.getCodigo());
			Assert.assertEquals(aplicacionForm.getNombre(), aplicacion.getNombre());
			Assert.assertEquals(aplicacionForm.getInfoConexion(), aplicacion.getInfoConexion());

		} finally {
			getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
		}
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

		AplicacionCSV aplicacion = new AplicacionCSV();
		aplicacion.setCodigo(null);
		aplicacion.setNombre("Aplicación nueva");
		aplicacion.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		try {
			getServicioAplicaciones().saveAplicacion(aplicacion);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.codigo' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_NombreVacio() {

		AplicacionCSV aplicacion = new AplicacionCSV();
		aplicacion.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacion.setNombre(null);
		aplicacion.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		try {
			getServicioAplicaciones().saveAplicacion(aplicacion);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.nombre' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_InfoConexionVacio() {

		AplicacionCSV aplicacion = new AplicacionCSV();
		aplicacion.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacion.setNombre("Aplicación nueva");
		aplicacion.setInfoConexion(null);

		try {
			getServicioAplicaciones().saveAplicacion(aplicacion);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.infoConexion' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testUpdateAplicacion() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacionForm.setNombre("Aplicación nueva");
		aplicacionForm.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionCSV aplicacion = getServicioAplicaciones().saveAplicacion(aplicacionForm);

		try {

			// Modificar la información de la aplicación
			aplicacion.setNombre("Nombre modificado");

			// Guardar la información de la aplicación
			AplicacionCSV aplicacionModificada = getServicioAplicaciones().updateAplicacion(aplicacion);
			Assert.assertNotNull("No se ha modificado la aplicación", aplicacionModificada);
			assertEquals(aplicacion, aplicacionModificada);

			// Obtener la información de la aplicación
			aplicacion = getServicioAplicaciones().getAplicacion(aplicacion.getId());
			assertEquals(aplicacion, aplicacionModificada);

		} finally {
			getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
		}
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

//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.application.idNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ninguna aplicación con el identificador: 99999999", e.getMessage());
		}
	}

	@Test
	public void testDeleteAplicacion() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		aplicacionForm.setNombre("Aplicación nueva");
		aplicacionForm.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionCSV aplicacion = getServicioAplicaciones().saveAplicacion(aplicacionForm);

		// Borrar aplicación existente
		getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
		Assert.assertNull("No se ha borrado la aplicacion", getServicioAplicaciones().getAplicacion(aplicacion.getId()));

		// Borrar aplicación no existente
		getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
	}

	protected AplicacionCSVForm createAplicacionCSVForm(String codigo,
			String nombre, String infoConexion) {

		AplicacionCSVForm aplicacion = new AplicacionCSVForm();

		aplicacion.setCodigo(codigo);
		aplicacion.setNombre(nombre);
		aplicacion.setInfoConexion(infoConexion);

		return aplicacion;
	}

	protected AplicacionCSV createAplicacionCSV(String id, String codigo,
			String nombre, String infoConexion) {

		AplicacionCSV aplicacion = new AplicacionCSV();

		aplicacion.setId(id);
		aplicacion.setCodigo(codigo);
		aplicacion.setNombre(nombre);
		aplicacion.setInfoConexion(infoConexion);

		return aplicacion;
	}

	protected void assertEquals(AplicacionCSV aplicacion1,
			AplicacionCSV aplicacion2) {

		Assert.assertEquals(aplicacion1.getId(), aplicacion2.getId());
		Assert.assertEquals(aplicacion1.getCodigo(), aplicacion2.getCodigo());
		Assert.assertEquals(aplicacion1.getNombre(), aplicacion2.getNombre());
		Assert.assertEquals(aplicacion1.getInfoConexion(),
				aplicacion2.getInfoConexion());
	}
}
