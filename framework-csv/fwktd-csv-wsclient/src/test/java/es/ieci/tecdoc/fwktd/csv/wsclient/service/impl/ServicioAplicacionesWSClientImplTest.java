package es.ieci.tecdoc.fwktd.csv.wsclient.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.ServicioAplicacionesPortType;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioAplicacionesWSClientImplTest {

	private static final String ID_APLICACION_EXISTENTE = "1";
	private static final String ID_APLICACION_NO_EXISTENTE = "99999999";

	private static final String CODIGO_APLICACION_EXISTENTE = "APP1";
	private static final String CODIGO_APLICACION_NO_EXISTENTE = "XXXXX";

	protected ServicioAplicaciones getServicioAplicaciones() {

		ServicioAplicacionesWSClientImpl service = new ServicioAplicacionesWSClientImpl();
		service.setServicioAplicaciones(getServicioAplicacionesPortType());

		return service;
	}

	protected ServicioAplicacionesPortType getServicioAplicacionesPortType() {

		ServicioAplicacionesPortType servicioAplicacionesPortType = EasyMock
				.createMock(ServicioAplicacionesPortType.class);

		EasyMock.expect(servicioAplicacionesPortType.getAplicaciones())
				.andAnswer(
						new IAnswer<List<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>>() {
							public List<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV> answer()
									throws Throwable {

								List<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV> aplicacionesWS = new ArrayList<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>();
								aplicacionesWS
										.add(createAplicacionCSVWS(
												"1",
												"APP1",
												"Aplicación 1",
												"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));
								aplicacionesWS
										.add(createAplicacionCSVWS(
												"2",
												"APP2",
												"Aplicación 2",
												"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));

								return aplicacionesWS;
							}
						});

		EasyMock.expect(
				servicioAplicacionesPortType.getAplicacion((String) EasyMock
						.anyObject()))
				.andAnswer(
						new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>() {
							public es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV answer()
									throws Throwable {

								String id = (String) EasyMock
										.getCurrentArguments()[0];

								if (StringUtils.isBlank(id)) {
									throw new IllegalArgumentException(
											"'id' must not be empty");
								}

								es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacion = null;

								if (!ID_APLICACION_NO_EXISTENTE.equals(id)) {
									aplicacion = createAplicacionCSVWS(
											id,
											"APP" + id,
											"Aplicación " + id,
											"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
								}

								return aplicacion;
							}
						});

		EasyMock.expect(
				servicioAplicacionesPortType
						.getAplicacionByCodigo((String) EasyMock.anyObject()))
				.andAnswer(
						new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>() {
							public es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV answer()
									throws Throwable {

								String codigo = (String) EasyMock
										.getCurrentArguments()[0];

								if (StringUtils.isBlank(codigo)) {
									throw new IllegalArgumentException(
											"'codigo' must not be empty");
								}

								es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacion = null;

								if (CODIGO_APLICACION_EXISTENTE.equals(codigo)) {
									aplicacion = createAplicacionCSVWS(
											ID_APLICACION_EXISTENTE,
											codigo,
											"Aplicación 1",
											"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
								}

								return aplicacion;
							}
						});

		EasyMock.expect(
				servicioAplicacionesPortType
						.saveAplicacion((es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm) EasyMock
								.anyObject()))
				.andAnswer(
						new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>() {
							public es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV answer()
									throws Throwable {

								es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm aplicacionForm = (es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm) EasyMock
										.getCurrentArguments()[0];
								if (aplicacionForm == null) {
									throw new IllegalArgumentException(
											"'aplicacion' must not be null");
								}

								return createAplicacionCSVWS("1",
										aplicacionForm.getCodigo(),
										aplicacionForm.getNombre(),
										aplicacionForm.getInfoConexion());
							}
						});

		EasyMock.expect(
				servicioAplicacionesPortType
						.updateAplicacion((es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV) EasyMock
								.anyObject()))
				.andAnswer(
						new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>() {
							public es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV answer()
									throws Throwable {

								es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacion = (es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV) EasyMock
										.getCurrentArguments()[0];
								if (aplicacion == null) {
									throw new IllegalArgumentException(
											"'aplicacion' must not be null");
								}

								if (ID_APLICACION_NO_EXISTENTE.equals(aplicacion.getId())) {
									throw new CSVException("error.csv.application.idNotFound", null, null);
								}

								return aplicacion;
							}
						});

		servicioAplicacionesPortType.deleteAplicacion((String) EasyMock
				.anyObject());
		// EasyMock.expectLastCall().andAnswer(
		// new IAnswer() {
		// public Object answer() throws Throwable {
		// if (!CSV_EXISTENTE.equals((String)
		// EasyMock.getCurrentArguments()[0])) {
		// throw new
		// ServicioGestionCSVException("error.csv.document.csvNotFound", null,
		// null);
		// }
		//
		// return null;
		// }
		// });

		EasyMock.replay(servicioAplicacionesPortType);

		return servicioAplicacionesPortType;
	}

	@Test
	public void testService() {
		Assert.assertNotNull("El servicio es nulo", getServicioAplicaciones());
	}

	@Test
	public void testGetAplicaciones() {

		List<AplicacionCSV> aplicaciones = getServicioAplicaciones()
				.getAplicaciones();
		Assert.assertNotNull("No se han obtenido las aplicaciones",
				aplicaciones);
		Assert.assertFalse(aplicaciones.isEmpty());
		Assert.assertEquals(2, aplicaciones.size());
		assertEquals(
				createAplicacionCSV(
						"1",
						"APP1",
						"Aplicación 1",
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"),
				aplicaciones.get(0));
		assertEquals(
				createAplicacionCSV(
						"2",
						"APP2",
						"Aplicación 2",
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"),
				aplicaciones.get(1));
	}

	@Test
	public void testGetAplicacion() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacion(
				ID_APLICACION_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado la aplicacion", aplicacion);

		assertEquals(
				createAplicacionCSV(
						ID_APLICACION_EXISTENTE,
						"APP" + ID_APLICACION_EXISTENTE,
						"Aplicación " + ID_APLICACION_EXISTENTE,
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"),
				aplicacion);
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

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacion(
				ID_APLICACION_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado la aplicación",
				aplicacion);
	}

	@Test
	public void testGetAplicacionByCodigo() {

		AplicacionCSV aplicacion = getServicioAplicaciones()
				.getAplicacionByCodigo(CODIGO_APLICACION_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado la aplicacion", aplicacion);

		assertEquals(
				createAplicacionCSV(
						ID_APLICACION_EXISTENTE,
						"APP" + ID_APLICACION_EXISTENTE,
						"Aplicación " + ID_APLICACION_EXISTENTE,
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"),
				aplicacion);
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

		AplicacionCSV aplicacion = getServicioAplicaciones()
				.getAplicacionByCodigo(CODIGO_APLICACION_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado la aplicación",
				aplicacion);
	}

	@Test
	public void testSaveAplicacion() {

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				CODIGO_APLICACION_NO_EXISTENTE,
				"Aplicación nueva",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionCSV aplicacionCreada = getServicioAplicaciones()
				.saveAplicacion(aplicacionForm);
		Assert.assertNotNull("No se ha creado la aplicación", aplicacionCreada);
		Assert.assertNotNull("No se ha creado el ID de la aplicación",
				aplicacionCreada.getId());
		Assert.assertEquals(aplicacionForm.getCodigo(),
				aplicacionCreada.getCodigo());
		Assert.assertEquals(aplicacionForm.getNombre(),
				aplicacionCreada.getNombre());
		Assert.assertEquals(aplicacionForm.getInfoConexion(),
				aplicacionCreada.getInfoConexion());
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

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				null,
				"Aplicación nueva",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		try {
			getServicioAplicaciones().saveAplicacion(aplicacionForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.codigo' must not be empty",
					e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_NombreVacio() {

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				CODIGO_APLICACION_NO_EXISTENTE,
				null,
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		try {
			getServicioAplicaciones().saveAplicacion(aplicacionForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.nombre' must not be empty",
					e.getMessage());
		}
	}

	@Test
	public void testSaveAplicacion_InfoConexionVacio() {

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				CODIGO_APLICACION_NO_EXISTENTE, "Aplicación nueva", null);

		try {
			getServicioAplicaciones().saveAplicacion(aplicacionForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'aplicacion.infoConexion' must not be empty",
					e.getMessage());
		}
	}

	@Test
	public void testUpdateAplicacion() {

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacion(
				ID_APLICACION_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado la aplicación", aplicacion);

		// Modificar la información de la aplicación
		aplicacion.setNombre("Nombre modificado");

		// Guardar la información de la aplicación
		AplicacionCSV aplicacionModificada = getServicioAplicaciones()
				.updateAplicacion(aplicacion);
		Assert.assertNotNull("No se ha modificado la aplicación",
				aplicacionModificada);

		Assert.assertEquals(aplicacion.getId(), aplicacionModificada.getId());
		Assert.assertEquals(aplicacion.getCodigo(),
				aplicacionModificada.getCodigo());
		Assert.assertEquals(aplicacion.getNombre(),
				aplicacionModificada.getNombre());
		Assert.assertEquals(aplicacion.getInfoConexion(),
				aplicacionModificada.getInfoConexion());
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

			AplicacionCSV aplicacion = createAplicacionCSV(
					ID_APLICACION_NO_EXISTENTE,
					CODIGO_APLICACION_NO_EXISTENTE,
					"Aplicación nueva",
					"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

			getServicioAplicaciones().updateAplicacion(aplicacion);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.idNotFound",
					e.getMessageId());
		}
	}

	@Test
	public void testDeleteAplicacion() {

		getServicioAplicaciones().deleteAplicacion(ID_APLICACION_EXISTENTE);
		getServicioAplicaciones().deleteAplicacion(ID_APLICACION_NO_EXISTENTE);
	}

	protected es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV createAplicacionCSVWS(
			String id, String codigo, String nombre, String infoConexion) {

		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionWS = new es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV();

		aplicacionWS.setId(id);
		aplicacionWS.setCodigo(codigo);
		aplicacionWS.setNombre(nombre);
		aplicacionWS.setInfoConexion(infoConexion);

		return aplicacionWS;
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

	protected AplicacionCSVForm createAplicacionCSVForm(String codigo,
			String nombre, String infoConexion) {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();

		aplicacionForm.setCodigo(codigo);
		aplicacionForm.setNombre(nombre);
		aplicacionForm.setInfoConexion(infoConexion);

		return aplicacionForm;
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
