package es.ieci.tecdoc.fwktd.csv.wsclient.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.DescripcionI18N;
import es.ieci.tecdoc.fwktd.csv.ws.service.ServicioDocumentosPortType;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioDocumentosWSClientImplTest {

	private static final String ID_EXISTENTE = "10000001";
	private static final String ID_NO_EXISTENTE = "99999";

	private static final String CSV_EXISTENTE = "00001";
	private static final String CSV_NO_EXISTENTE = "XXXXX";

	private static final Date FECHA_CREACION = new Date();
	private static final Date FECHA_CADUCIDAD = new Date();
	private static final Date FECHA_CSV = new Date();

	private static final XMLGregorianCalendar FECHA_CREACION_GC = DateUtils.toXMLGregorianCalendar(FECHA_CREACION);
	private static final XMLGregorianCalendar FECHA_CADUCIDAD_GC = DateUtils.toXMLGregorianCalendar(FECHA_CADUCIDAD);
	private static final XMLGregorianCalendar FECHA_CSV_GC = DateUtils.toXMLGregorianCalendar(FECHA_CSV);

	protected ServicioDocumentos getServicioDocumentos() {

		ServicioDocumentosWSClientImpl service = new ServicioDocumentosWSClientImpl();
		service.setServicioDocumentos(getServicioDocumentosPortType());

		return service;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ServicioDocumentosPortType getServicioDocumentosPortType() {

		ServicioDocumentosPortType servicioDocumentosPortType = EasyMock.createMock(ServicioDocumentosPortType.class);

		EasyMock.expect(servicioDocumentosPortType.generarCSV((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV answer() throws Throwable {

						es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm infoDocumentoCSVForm = (es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm) EasyMock.getCurrentArguments()[0];
						if (infoDocumentoCSVForm == null) {
							throw new IllegalArgumentException("'infoDocumentoForm' must not be null");
						}

						if ("XXX".equals(infoDocumentoCSVForm.getCodigoAplicacion())) {
							throw new CSVException("error.csv.application.codeNotFound", null, null);
						}

						es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV infoDocumentoCSV = createInfoDocumentoCSVWS(
								"1",
								infoDocumentoCSVForm.getNombre(),
								infoDocumentoCSVForm.getTipoMime(),
								infoDocumentoCSVForm.getFechaCreacion(),
								infoDocumentoCSVForm.getFechaCaducidad(),
								infoDocumentoCSVForm.getCodigoAplicacion(),
								"Aplicación " + infoDocumentoCSVForm.getCodigoAplicacion(),
								infoDocumentoCSVForm.isDisponible(),
								"00001",
								FECHA_CSV_GC);

						return infoDocumentoCSV;
					}
				});

		EasyMock.expect(servicioDocumentosPortType.getInfoDocumento((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV answer() throws Throwable {

						String id = (String) EasyMock.getCurrentArguments()[0];

						if (StringUtils.isBlank(id)) {
							throw new IllegalArgumentException("'id' must not be empty");
						}

						es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV infoDocumentoCSV = null;

						if (ID_EXISTENTE.equals(id)) {
							infoDocumentoCSV = createInfoDocumentoCSVWS(
									id,
									"Documento_" + id + ".pdf",
									"application/pdf",
									FECHA_CREACION_GC,
									FECHA_CADUCIDAD_GC,
									"APP1",
									"Aplicación 1",
									true,
									"00001",
									FECHA_CSV_GC);
						}

						return infoDocumentoCSV;
					}
				});

		EasyMock.expect(servicioDocumentosPortType.getInfoDocumentoByCSV((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV answer() throws Throwable {

						String csv = (String) EasyMock.getCurrentArguments()[0];

						if (StringUtils.isBlank(csv)) {
							throw new IllegalArgumentException("'csv' must not be empty");
						}

						es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV infoDocumentoCSV = null;

						if (CSV_EXISTENTE.equals(csv)) {
							infoDocumentoCSV = createInfoDocumentoCSVWS(
									ID_EXISTENTE,
									"Documento_" + ID_EXISTENTE + ".pdf",
									"application/pdf",
									FECHA_CREACION_GC,
									FECHA_CADUCIDAD_GC,
									"APP1",
									"Aplicación 1",
									true,
									csv,
									FECHA_CSV_GC);
						}

						return infoDocumentoCSV;
					}
				});

		EasyMock.expect(servicioDocumentosPortType.getDocumento((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV answer() throws Throwable {

						String id = (String) EasyMock.getCurrentArguments()[0];

						if (StringUtils.isBlank(id)) {
							throw new IllegalArgumentException("'id' must not be empty");
						}

						es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV documentoCSV = null;

						if (ID_EXISTENTE.equals(id)) {

							documentoCSV = createDocumentoCSVWS(
									id,
									"Documento_" + id + ".pdf",
									"application/pdf",
									FECHA_CREACION_GC,
									FECHA_CADUCIDAD_GC,
									"APP1",
									"Aplicación 1",
									true,
									"00001",
									FECHA_CSV_GC,
									"contenido del documento".getBytes());
						}

						return documentoCSV;
					}
				});

		EasyMock.expect(servicioDocumentosPortType.getDocumentoByCSV((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV answer() throws Throwable {

						String csv = (String) EasyMock.getCurrentArguments()[0];

						if (StringUtils.isBlank(csv)) {
							throw new IllegalArgumentException("'csv' must not be empty");
						}

						es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV documentoCSV = null;

						if (CSV_EXISTENTE.equals((String) EasyMock.getCurrentArguments()[0])) {

							documentoCSV = createDocumentoCSVWS(
									ID_EXISTENTE,
									"Documento_" + ID_EXISTENTE + ".pdf",
									"application/pdf",
									FECHA_CREACION_GC,
									FECHA_CADUCIDAD_GC,
									"APP1",
									"Aplicación 1",
									true,
									csv,
									FECHA_CSV_GC,
									"contenido del documento".getBytes());
						}

						return documentoCSV;
					}
				});

		EasyMock.expect(servicioDocumentosPortType.saveInfoDocumento((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV answer() throws Throwable {

						es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV oldInfoDocumentoCSV = (es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV) EasyMock.getCurrentArguments()[0];
						if (oldInfoDocumentoCSV == null) {
							throw new IllegalArgumentException("'infoDocumento' must not be null");
						}

						if ("XXX".equals(oldInfoDocumentoCSV.getCodigoAplicacion())) {
							throw new CSVException("error.csv.application.codeNotFound", null, null);
						}

						oldInfoDocumentoCSV.setId(ID_EXISTENTE);
						return oldInfoDocumentoCSV;
					}
				});

		EasyMock.expect(servicioDocumentosPortType.updateInfoDocumento((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV) EasyMock.anyObject())).andAnswer(
				new IAnswer<es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV>() {
					public es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV answer() throws Throwable {

						es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV oldInfoDocumentoCSV = (es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV) EasyMock.getCurrentArguments()[0];
						if (oldInfoDocumentoCSV == null) {
							throw new IllegalArgumentException("'infoDocumento' must not be null");
						}

						if (ID_EXISTENTE.equals(oldInfoDocumentoCSV.getId())) {

							if ("XXX".equals(oldInfoDocumentoCSV.getCodigoAplicacion())) {
								throw new CSVException("error.csv.application.codeNotFound", null, null);
							}

							return oldInfoDocumentoCSV;
						} else {
							throw new CSVException("error.csv.document.idNotFound", null, null);
						}
					}
				});

		EasyMock.expect(servicioDocumentosPortType.existeContenidoDocumento((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<Boolean>() {
					public Boolean answer() throws Throwable {
						if (ID_EXISTENTE.equals((String) EasyMock.getCurrentArguments()[0])) {
							return Boolean.TRUE;
						} else {
							throw new CSVException("error.csv.document.idNotFound", null, null);
						}
					}
				});

		EasyMock.expect(servicioDocumentosPortType.getContenidoDocumento((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<byte[]>() {
					public byte[] answer() throws Throwable {
						if (ID_EXISTENTE.equals((String) EasyMock
								.getCurrentArguments()[0])) {
							return "contenido".getBytes();
						} else {
							throw new CSVException("error.csv.document.idNotFound", null, null);
						}
					}
				});

		servicioDocumentosPortType.deleteInfoDocumento((String) EasyMock.anyObject());
		servicioDocumentosPortType.revocarDocumento((String) EasyMock.anyObject());
		EasyMock.expectLastCall().andAnswer(
				new IAnswer() {
					public Object answer() throws Throwable {
						if (!CSV_EXISTENTE.equals((String) EasyMock.getCurrentArguments()[0])) {
							throw new CSVException("error.csv.document.csvNotFound", null, null);
						}

						return null;
					}
				});

		EasyMock.replay(servicioDocumentosPortType);

		return servicioDocumentosPortType;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getServicioDocumentos());
	}

	@Test
	public void testGenerarCSV() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime("applitacion/pdf");
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		infoDocumentoForm.addDescripcion(null, "[default] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().generarCSV(infoDocumentoForm);
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);
	}

	@Test
	public void testGenerarCSV_FormVacio() {

		try {
			getServicioDocumentos().generarCSV(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumentoForm' must not be null", e.getMessage());
		}
	}

	@Test
	public void testGenerarCSV_NombreVacio() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre(null);
		infoDocumentoForm.setTipoMime("applitacion/pdf");
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		try {
			getServicioDocumentos().generarCSV(infoDocumentoForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumentoForm.nombre' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGenerarCSV_TipoMIMEVacio() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime(null);
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		try {
			getServicioDocumentos().generarCSV(infoDocumentoForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumentoForm.tipoMime' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGenerarCSV_CodigoAplicacionVacio() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime("applitacion/pdf");
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion(null);
		infoDocumentoForm.setDisponible(true);

		try {
			getServicioDocumentos().generarCSV(infoDocumentoForm);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumentoForm.codigoAplicacion' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGenerarCSV_CodigoAplicacionErroneo() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime("applitacion/pdf");
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion("XXX");
		infoDocumentoForm.setDisponible(true);

		try {
			getServicioDocumentos().generarCSV(infoDocumentoForm);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		}
	}

	@Test
	public void testGetInfoDocumento() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		assertEquals(
				createInfoDocumentoCSV(ID_EXISTENTE, "Documento_"
						+ ID_EXISTENTE + ".pdf", "application/pdf",
						FECHA_CREACION, FECHA_CADUCIDAD, "APP1",
						"Aplicación 1", true, "00001", FECHA_CSV),
				infoDocumento);
	}

	@Test
	public void testGetInfoDocumento_IdNulo() {

		try {
			getServicioDocumentos().getInfoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetInfoDocumento_IDNoExistente() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", infoDocumento);
	}

	@Test
	public void testGetInfoDocumentoByCSV() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumentoByCSV(CSV_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);
		assertEquals(
				createInfoDocumentoCSV(ID_EXISTENTE, "Documento_"
						+ ID_EXISTENTE + ".pdf", "application/pdf",
						FECHA_CREACION, FECHA_CADUCIDAD, "APP1",
						"Aplicación 1", true, CSV_EXISTENTE, FECHA_CSV),
				infoDocumento);
	}

	@Test
	public void testGetInfoDocumentoByCSV_CSVNulo() {

		try {
			getServicioDocumentos().getInfoDocumentoByCSV(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'csv' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetInfoDocumentoByCSV_CSVNoExistente() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumentoByCSV(CSV_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", infoDocumento);
	}

	@Test
	public void testGetDocumento() {

		DocumentoCSV documento = getServicioDocumentos().getDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", documento);

		assertEquals(
				createDocumentoCSV(ID_EXISTENTE, "Documento_"
						+ ID_EXISTENTE + ".pdf", "application/pdf",
						FECHA_CREACION, FECHA_CADUCIDAD, "APP1",
						"Aplicación 1", true, CSV_EXISTENTE, FECHA_CSV, "contenido del documento".getBytes()),
				documento);
	}

	@Test
	public void testGetDocumento_IdNulo() {

		try {
			getServicioDocumentos().getDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetDocumento_IdNoExistente() {

		DocumentoCSV documento = getServicioDocumentos().getDocumento(ID_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", documento);
	}

	@Test
	public void testGetDocumentoByCSV() {

		DocumentoCSV documento = getServicioDocumentos().getDocumentoByCSV(CSV_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", documento);

		assertEquals(
				createDocumentoCSV(ID_EXISTENTE, "Documento_"
						+ ID_EXISTENTE + ".pdf", "application/pdf",
						FECHA_CREACION, FECHA_CADUCIDAD, "APP1",
						"Aplicación 1", true, CSV_EXISTENTE, FECHA_CSV, "contenido del documento".getBytes()),
				documento);
	}

	@Test
	public void testGetDocumentoByCSV_CSVNulo() {

		try {
			getServicioDocumentos().getDocumentoByCSV(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'csv' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetDocumentoByCSV_CSVNoExistente() {

		DocumentoCSV documento = getServicioDocumentos().getDocumentoByCSV(CSV_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", documento);
	}

	@Test
	public void testSaveInfoDocumento() {

		InfoDocumentoCSV infoDocumento = new InfoDocumentoCSV();
		infoDocumento.setNombre("documento.pdf");
		infoDocumento.setTipoMime("applitacion/pdf");
		infoDocumento.setFechaCreacion(new Date());
		infoDocumento.setFechaCaducidad(new Date());
		infoDocumento.setCodigoAplicacion("APP1");
		infoDocumento.setNombreAplicacion("Aplicación 1");
		infoDocumento.setDisponible(true);
		infoDocumento.setCsv(CSV_NO_EXISTENTE);
		infoDocumento.setFechaCSV(new Date());

		// Guardar la información del documento
		InfoDocumentoCSV infoDocumentoCreado = getServicioDocumentos().saveInfoDocumento(infoDocumento);
		Assert.assertNotNull("No se ha creado el documento", infoDocumentoCreado);

		Assert.assertNotNull(infoDocumentoCreado.getId());
		Assert.assertEquals(infoDocumento.getNombre(), infoDocumento.getNombre());
		Assert.assertEquals(infoDocumento.getTipoMime(), infoDocumento.getTipoMime());
		Assert.assertEquals(String.valueOf(infoDocumento.getFechaCreacion()), String.valueOf(infoDocumentoCreado.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(infoDocumento.getFechaCaducidad()), String.valueOf(infoDocumentoCreado.getFechaCaducidad()));
		Assert.assertEquals(infoDocumento.getCsv(), infoDocumentoCreado.getCsv());
		Assert.assertEquals(String.valueOf(infoDocumento.getFechaCSV()), String.valueOf(infoDocumentoCreado.getFechaCSV()));
		Assert.assertEquals(infoDocumento.isDisponible(), infoDocumentoCreado.isDisponible());
		Assert.assertEquals(infoDocumento.getCodigoAplicacion(), infoDocumentoCreado.getCodigoAplicacion());
		Assert.assertEquals(infoDocumento.getNombreAplicacion(), infoDocumentoCreado.getNombreAplicacion());
	}

	@Test
	public void testSaveInfoDocumento_InfoVacia() {

		try {
			getServicioDocumentos().saveInfoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumento' must not be null", e.getMessage());
		}
	}

	@Test
	public void testSaveInfoDocumento_CodigoAplicacionNoExistente() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		infoDocumento.setCodigoAplicacion("XXX");

		try {
			getServicioDocumentos().saveInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		}
	}

	@Test
	public void testUpdateInfoDocumento() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		Date fecha = new Date();

		// Modificar la información del documento
		infoDocumento.setFechaCaducidad(fecha);
		infoDocumento.setDisponible(false);
		infoDocumento.setCodigoAplicacion("APP2");

		// Guardar la información del documento
		InfoDocumentoCSV infoDocumentoModificado = getServicioDocumentos().updateInfoDocumento(infoDocumento);
		Assert.assertNotNull("No se ha modificado el documento", infoDocumentoModificado);

		assertEquals(infoDocumento, infoDocumentoModificado);
	}

	@Test
	public void testUpdateInfoDocumento_InfoVacia() {

		try {
			getServicioDocumentos().updateInfoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumento' must not be null", e.getMessage());
		}
	}

	@Test
	public void testUpdateInfoDocumento_IdNoExistente() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		infoDocumento.setId(ID_NO_EXISTENTE);

		try {
			getServicioDocumentos().updateInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testUpdateInfoDocumento_CodigoAplicacionNoExistente() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		infoDocumento.setCodigoAplicacion("XXX");

		try {
			getServicioDocumentos().updateInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		}
	}

	@Test
	public void testDeleteInfoDocumento() {
		getServicioDocumentos().deleteInfoDocumento(ID_EXISTENTE);
		getServicioDocumentos().deleteInfoDocumento(ID_NO_EXISTENTE);
	}

	@Test
	public void testExisteContenidoDocumento() {

		boolean existe = getServicioDocumentos().existeContenidoDocumento(ID_EXISTENTE);
		Assert.assertTrue("No se ha encontrado el contenido del documento", existe);

		try {
			existe = getServicioDocumentos().existeContenidoDocumento(ID_NO_EXISTENTE);
			Assert.fail("Debería lanzarse la excepción");
		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testExisteContenidoDocumento_IdNulo() {

		try {
			getServicioDocumentos().existeContenidoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetContenidoDocumento() {

		byte[] contenido = getServicioDocumentos().getContenidoDocumento(ID_EXISTENTE);
		Assert.assertTrue("No se ha encontrado el contenido del documento", ArrayUtils.isNotEmpty(contenido));

		try {
			contenido = getServicioDocumentos().getContenidoDocumento(ID_NO_EXISTENTE);
			Assert.fail("Debería lanzarse la excepción");
		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testGetContenidoDocumento_IdNulo() {

		try {
			getServicioDocumentos().getContenidoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void writeDocumento() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		getServicioDocumentos().writeDocumento(ID_EXISTENTE, baos);
		Assert.assertTrue("No se ha encontrado el contenido del documento", baos.size() > 0);

		baos.reset();

		try {
			getServicioDocumentos().writeDocumento(ID_NO_EXISTENTE, baos);
			Assert.fail("Debería lanzarse la excepción");
		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}

	}

	@Test
	public void testWriteDocumento_IdNulo() throws IOException {

		try {
			getServicioDocumentos().writeDocumento(null, null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testWriteDocumento_OutputStreamNulo() throws IOException {

		try {
			getServicioDocumentos().writeDocumento(ID_EXISTENTE, null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'outputStream' must not be null", e.getMessage());
		}
	}

	@Test
	public void testRevocarDocumento() {
		getServicioDocumentos().revocarDocumento(CSV_EXISTENTE);
	}

	@Test
	public void testRevocarDocumento_CSVNulo() {

		try {
			getServicioDocumentos().revocarDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'csv' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testRevocarDocumento_CsvNoExistente() {

		try {
			getServicioDocumentos().revocarDocumento(CSV_NO_EXISTENTE);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.csvNotFound", e.getMessageId());
		}
	}

	protected es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV createInfoDocumentoCSVWS(
			String id, String nombre, String tipoMime,
			XMLGregorianCalendar fechaCreacion,
			XMLGregorianCalendar fechaCaducidad, String codigoAplicacion,
			String nombreAplicacion, boolean disponible, String csv,
			XMLGregorianCalendar fechaCsv) {

		es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV infoDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV();
		infoDocumentoCSV.setId(id);
		infoDocumentoCSV.setNombre(nombre);
		infoDocumentoCSV.setTipoMime(tipoMime);
		infoDocumentoCSV.setFechaCreacion(fechaCreacion);
		infoDocumentoCSV.setFechaCaducidad(fechaCaducidad);
		infoDocumentoCSV.setCodigoAplicacion(codigoAplicacion);
		infoDocumentoCSV.setNombreAplicacion(nombreAplicacion);
		infoDocumentoCSV.setDisponible(disponible);
		infoDocumentoCSV.setCsv(csv);
		infoDocumentoCSV.setFechaCSV(fechaCsv);

		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		return infoDocumentoCSV;
	}

	protected InfoDocumentoCSV createInfoDocumentoCSV(
			String id, String nombre, String tipoMime,
			Date fechaCreacion,
			Date fechaCaducidad, String codigoAplicacion,
			String nombreAplicacion, boolean disponible, String csv,
			Date fechaCsv) {

		InfoDocumentoCSV infoDocumento = new InfoDocumentoCSV();
		infoDocumento.setId(id);
		infoDocumento.setNombre(nombre);
		infoDocumento.setTipoMime(tipoMime);
		infoDocumento.setFechaCreacion(fechaCreacion);
		infoDocumento.setFechaCaducidad(fechaCaducidad);
		infoDocumento.setCodigoAplicacion(codigoAplicacion);
		infoDocumento.setNombreAplicacion(nombreAplicacion);
		infoDocumento.setDisponible(disponible);
		infoDocumento.setCsv(csv);
		infoDocumento.setFechaCSV(fechaCsv);

		infoDocumento.addDescripcion(null, "[default] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		return infoDocumento;
	}

	protected es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV createDocumentoCSVWS(
			String id, String nombre, String tipoMime,
			XMLGregorianCalendar fechaCreacion,
			XMLGregorianCalendar fechaCaducidad, String codigoAplicacion,
			String nombreAplicacion, boolean disponible, String csv,
			XMLGregorianCalendar fechaCsv, byte[] contenido) {

		es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV documentoCSV = new es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV();
		documentoCSV.setId(id);
		documentoCSV.setNombre(nombre);
		documentoCSV.setTipoMime(tipoMime);
		documentoCSV.setFechaCreacion(fechaCreacion);
		documentoCSV.setFechaCaducidad(fechaCaducidad);
		documentoCSV.setCodigoAplicacion(codigoAplicacion);
		documentoCSV.setNombreAplicacion(nombreAplicacion);
		documentoCSV.setDisponible(disponible);
		documentoCSV.setCsv(csv);
		documentoCSV.setFechaCSV(fechaCsv);
		documentoCSV.setContenido(contenido);

		documentoCSV.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		documentoCSV.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		documentoCSV.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		documentoCSV.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		documentoCSV.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		return documentoCSV;
	}

	protected DocumentoCSV createDocumentoCSV(
			String id, String nombre, String tipoMime,
			Date fechaCreacion,
			Date fechaCaducidad, String codigoAplicacion,
			String nombreAplicacion, boolean disponible, String csv,
			Date fechaCsv, byte[] contenido) {

		DocumentoCSV documento = new DocumentoCSV();
		documento.setId(id);
		documento.setNombre(nombre);
		documento.setTipoMime(tipoMime);
		documento.setFechaCreacion(fechaCreacion);
		documento.setFechaCaducidad(fechaCaducidad);
		documento.setCodigoAplicacion(codigoAplicacion);
		documento.setNombreAplicacion(nombreAplicacion);
		documento.setDisponible(disponible);
		documento.setCsv(csv);
		documento.setFechaCSV(fechaCsv);
		documento.setContenido(contenido);

		documento.addDescripcion(null, "[default] Descripción del documento");
		documento.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		documento.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		documento.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		documento.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		return documento;
	}

	protected void assertEquals(InfoDocumentoCSV doc1, InfoDocumentoCSV doc2) {

		Assert.assertEquals(doc1.getId(), doc2.getId());
		Assert.assertEquals(doc1.getNombre(), doc2.getNombre());
		Assert.assertEquals(doc1.getTipoMime(), doc2.getTipoMime());
		Assert.assertEquals(String.valueOf(doc1.getFechaCreacion()), String.valueOf(doc2.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(doc1.getFechaCaducidad()), String.valueOf(doc2.getFechaCaducidad()));
		Assert.assertEquals(doc1.getCsv(), doc2.getCsv());
		Assert.assertEquals(String.valueOf(doc1.getFechaCSV()), String.valueOf(doc2.getFechaCSV()));
		Assert.assertEquals(doc1.isDisponible(), doc2.isDisponible());
		Assert.assertEquals(doc1.getCodigoAplicacion(), doc2.getCodigoAplicacion());
		Assert.assertEquals(doc1.getNombreAplicacion(), doc2.getNombreAplicacion());

		// Descripciones
		Assert.assertEquals(doc1.getDescripciones().size(), doc2.getDescripciones().size());
		for (String key : doc1.getDescripciones().keySet()) {
			Assert.assertEquals(doc1.getDescripciones().get(key), doc2.getDescripciones().get(key));
		}

		if (doc1 instanceof DocumentoCSV) {
			Assert.assertEquals(new String(((DocumentoCSV)doc1).getContenido()), new String(((DocumentoCSV)doc2).getContenido()));
		}
	}

	protected DescripcionI18N createDescripcionI18N(String locale, String descripcion) {

		DescripcionI18N descripcionI18N = new DescripcionI18N();
		descripcionI18N.setLocale(locale);
		descripcionI18N.setDescripcion(descripcion);
		return descripcionI18N;
	}
}
