package es.ieci.tecdoc.fwktd.csv.ws.delegate.impl;

import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate;
import es.ieci.tecdoc.fwktd.csv.ws.service.DescripcionI18N;
import es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-csv-core-applicationContext.xml",
		"/beans/fwktd-csv-api-applicationContext.xml",
		"/beans/fwktd-csv-ws-applicationContext.xml",
		"/beans/fwktd-csv-test-beans.xml" })
public class DocumentosDelegateImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String ID_EXISTENTE = "10000001";
	private static final String ID_NO_EXISTENTE = "99999";

	private static final String CSV_EXISTENTE = "00001";
	private static final String CSV_NO_EXISTENTE = "XXXXX";

	@Autowired
	private DocumentosDelegateImpl fwktd_csv_ws_documentosDelegateImpl;

	public DocumentosDelegate getDocumentosDelegate() {
		return fwktd_csv_ws_documentosDelegateImpl;
	}

	@Test
	public void testDelegate() {
		Assert.assertNotNull("El delegate es nulo", getDocumentosDelegate());
	}

	@Test
	public void testGenerarCSV() {

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime("applitacion/pdf");
		infoDocumentoForm.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		infoDocumentoForm.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		infoDocumentoForm.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		infoDocumentoForm.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		infoDocumentoForm.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		infoDocumentoForm.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().generarCSV(infoDocumentoForm);
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);
	}

	@Test
	public void testGenerarCSV_FormVacio() {

		try {
			getDocumentosDelegate().generarCSV(null);
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
		infoDocumentoForm.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		try {
			getDocumentosDelegate().generarCSV(infoDocumentoForm);
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
		infoDocumentoForm.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setCodigoAplicacion("APP1");
		infoDocumentoForm.setDisponible(true);

		try {
			getDocumentosDelegate().generarCSV(infoDocumentoForm);
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
		infoDocumentoForm.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setCodigoAplicacion(null);
		infoDocumentoForm.setDisponible(true);

		try {
			getDocumentosDelegate().generarCSV(infoDocumentoForm);
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
		infoDocumentoForm.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoForm.setCodigoAplicacion("XXX");
		infoDocumentoForm.setDisponible(true);

		try {
			getDocumentosDelegate().generarCSV(infoDocumentoForm);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		}
	}

	@Test
	public void testGetInfoDocumento() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);
		Assert.assertEquals(ID_EXISTENTE, infoDocumento.getId());
		Assert.assertEquals("Documento_1.pdf", infoDocumento.getNombre());
		Assert.assertEquals(5, infoDocumento.getDescripcionesI18N().size());
		Assert.assertEquals("application/pdf", infoDocumento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(infoDocumento.getFechaCreacion())));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(DateUtils.toDate(infoDocumento.getFechaCaducidad())));
		Assert.assertEquals("00001", infoDocumento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(infoDocumento.getFechaCSV())));
		Assert.assertEquals(Boolean.TRUE, infoDocumento.isDisponible());
		Assert.assertEquals("APP1", infoDocumento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", infoDocumento.getNombreAplicacion());
	}

	@Test
	public void testGetInfoDocumento_IdNulo() {

		try {
			getDocumentosDelegate().getInfoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetInfoDocumento_IDNoExistente() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", infoDocumento);
	}

	@Test
	public void testGetInfoDocumentoByCSV() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumentoByCSV(CSV_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);
		Assert.assertEquals(ID_EXISTENTE, infoDocumento.getId());
		Assert.assertEquals("Documento_1.pdf", infoDocumento.getNombre());
		Assert.assertEquals(5, infoDocumento.getDescripcionesI18N().size());
		Assert.assertEquals("application/pdf", infoDocumento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(infoDocumento.getFechaCreacion())));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(DateUtils.toDate(infoDocumento.getFechaCaducidad())));
		Assert.assertEquals("00001", infoDocumento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(infoDocumento.getFechaCSV())));
		Assert.assertEquals(Boolean.TRUE, infoDocumento.isDisponible());
		Assert.assertEquals("APP1", infoDocumento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", infoDocumento.getNombreAplicacion());
	}

	@Test
	public void testGetInfoDocumentoByCSV_CSVNulo() {

		try {
			getDocumentosDelegate().getInfoDocumentoByCSV(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'csv' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetInfoDocumentoByCSV_CSVNoExistente() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumentoByCSV(CSV_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", infoDocumento);
	}

	@Test
	public void testGetDocumento() {

		DocumentoCSV documento = getDocumentosDelegate().getDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", documento);
		Assert.assertEquals(ID_EXISTENTE, documento.getId());
		Assert.assertEquals("Documento_1.pdf", documento.getNombre());
		Assert.assertEquals(5, documento.getDescripcionesI18N().size());
		Assert.assertEquals("application/pdf", documento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(documento.getFechaCreacion())));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(DateUtils.toDate(documento.getFechaCaducidad())));
		Assert.assertEquals("00001", documento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(documento.getFechaCSV())));
		Assert.assertEquals(Boolean.TRUE, documento.isDisponible());
		Assert.assertEquals("APP1", documento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", documento.getNombreAplicacion());
		Assert.assertTrue("No se ha encontrado el contenido", ArrayUtils.isNotEmpty(documento.getContenido()));
	}

	@Test
	public void testGetDocumento_IdNulo() {

		try {
			getDocumentosDelegate().getDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetDocumento_IdNoExistente() {

		DocumentoCSV documento = getDocumentosDelegate().getDocumento(ID_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", documento);
	}

	@Test
	public void testGetDocumentoByCSV() {

		DocumentoCSV documento = getDocumentosDelegate().getDocumentoByCSV(CSV_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", documento);
		Assert.assertEquals(ID_EXISTENTE, documento.getId());
		Assert.assertEquals("Documento_1.pdf", documento.getNombre());
		Assert.assertEquals(5, documento.getDescripcionesI18N().size());
		Assert.assertEquals("application/pdf", documento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(documento.getFechaCreacion())));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(DateUtils.toDate(documento.getFechaCaducidad())));
		Assert.assertEquals("00001", documento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(documento.getFechaCSV())));
		Assert.assertEquals(Boolean.TRUE, documento.isDisponible());
		Assert.assertEquals("APP1", documento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", documento.getNombreAplicacion());
		Assert.assertTrue("No se ha encontrado el contenido", ArrayUtils.isNotEmpty(documento.getContenido()));
	}

	@Test
	public void testGetDocumentoByCSV_CSVNulo() {

		try {
			getDocumentosDelegate().getDocumentoByCSV(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'csv' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetDocumentoByCSV_CSVNoExistente() {

		DocumentoCSV documento = getDocumentosDelegate().getDocumentoByCSV(CSV_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", documento);
	}

	@Test
	public void testSaveInfoDocumento() {

		InfoDocumentoCSV infoDocumento = new InfoDocumentoCSV();
		infoDocumento.setNombre("documento.pdf");
		infoDocumento.setTipoMime("applitacion/pdf");
		infoDocumento.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumento.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumento.setCodigoAplicacion("APP1");
		infoDocumento.setNombreAplicacion("Aplicación 1");
		infoDocumento.setDisponible(true);
		infoDocumento.setCsv(CSV_NO_EXISTENTE);
		infoDocumento.setFechaCSV(DateUtils.toXMLGregorianCalendar(new Date()));

		infoDocumento.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		infoDocumento.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		infoDocumento.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		infoDocumento.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		infoDocumento.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		// Guardar la información del documento
		InfoDocumentoCSV infoDocumentoCreado = getDocumentosDelegate().saveInfoDocumento(infoDocumento);
		Assert.assertNotNull("No se ha creado el documento", infoDocumentoCreado);

		Assert.assertNotNull(infoDocumentoCreado.getId());
		Assert.assertEquals(infoDocumento.getNombre(), infoDocumentoCreado.getNombre());
		Assert.assertEquals(infoDocumento.getDescripcionesI18N().size(), infoDocumentoCreado.getDescripcionesI18N().size());
		Assert.assertEquals(infoDocumento.getTipoMime(), infoDocumento.getTipoMime());
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumento.getFechaCreacion())), String.valueOf(DateUtils.toDate(infoDocumentoCreado.getFechaCreacion())));
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumento.getFechaCaducidad())), String.valueOf(DateUtils.toDate(infoDocumentoCreado.getFechaCaducidad())));
		Assert.assertEquals(infoDocumento.getCsv(), infoDocumentoCreado.getCsv());
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumento.getFechaCSV())), String.valueOf(DateUtils.toDate(infoDocumentoCreado.getFechaCSV())));
		Assert.assertEquals(infoDocumento.isDisponible(), infoDocumentoCreado.isDisponible());
		Assert.assertEquals(infoDocumento.getCodigoAplicacion(), infoDocumentoCreado.getCodigoAplicacion());
		Assert.assertEquals(infoDocumento.getNombreAplicacion(), infoDocumentoCreado.getNombreAplicacion());

		// Leer la información del documento
		infoDocumento = getDocumentosDelegate().getInfoDocumento(infoDocumentoCreado.getId());
		Assert.assertNotNull("No se ha modificado el documento", infoDocumento);
		Assert.assertEquals(infoDocumentoCreado.getId(), infoDocumento.getId());
		Assert.assertEquals(infoDocumentoCreado.getNombre(), infoDocumento.getNombre());
		Assert.assertEquals(infoDocumentoCreado.getDescripcionesI18N().size(), infoDocumento.getDescripcionesI18N().size());
		Assert.assertEquals(infoDocumentoCreado.getTipoMime(), infoDocumento.getTipoMime());
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumentoCreado.getFechaCreacion())), String.valueOf(DateUtils.toDate(infoDocumento.getFechaCreacion())));
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumentoCreado.getFechaCaducidad())), String.valueOf(DateUtils.toDate(infoDocumento.getFechaCaducidad())));
		Assert.assertEquals(infoDocumentoCreado.getCsv(), infoDocumento.getCsv());
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumentoCreado.getFechaCSV())), String.valueOf(DateUtils.toDate(infoDocumento.getFechaCSV())));
		Assert.assertEquals(infoDocumentoCreado.isDisponible(), infoDocumento.isDisponible());
		Assert.assertEquals(infoDocumentoCreado.getCodigoAplicacion(), infoDocumento.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoCreado.getNombreAplicacion(), infoDocumento.getNombreAplicacion());
	}

	@Test
	public void testSaveInfoDocumento_InfoVacia() {

		try {
			getDocumentosDelegate().saveInfoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumento' must not be null", e.getMessage());
		}
	}

	@Test
	public void testSaveInfoDocumento_CodigoAplicacionNoExistente() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		infoDocumento.setCodigoAplicacion("XXX");

		try {
			getDocumentosDelegate().saveInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		}
	}

	@Test
	public void testUpdateInfoDocumento() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		Date fecha = new Date();

		// Modificar la información del documento
		infoDocumento.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(fecha));
		infoDocumento.setDisponible(false);
		infoDocumento.setCodigoAplicacion("APP2");

		// Guardar la información del documento
		InfoDocumentoCSV infoDocumentoModificado = getDocumentosDelegate().updateInfoDocumento(infoDocumento);
		Assert.assertNotNull("No se ha modificado el documento", infoDocumentoModificado);

		Assert.assertEquals(ID_EXISTENTE, infoDocumento.getId());
		Assert.assertEquals("Documento_1.pdf", infoDocumento.getNombre());
		Assert.assertEquals("application/pdf", infoDocumento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(infoDocumentoModificado.getFechaCreacion())));
		Assert.assertEquals(fecha.toString(), String.valueOf(DateUtils.toDate(infoDocumentoModificado.getFechaCaducidad())));
		Assert.assertEquals(CSV_EXISTENTE, infoDocumentoModificado.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(DateUtils.toDate(infoDocumentoModificado.getFechaCSV())));
		Assert.assertEquals(Boolean.FALSE, infoDocumentoModificado.isDisponible());
		Assert.assertEquals("APP2", infoDocumentoModificado.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 2", infoDocumentoModificado.getNombreAplicacion());

		// Leer la información del documento
		infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha modificado el documento", infoDocumento);
		Assert.assertEquals(infoDocumentoModificado.getId(), infoDocumento.getId());
		Assert.assertEquals(infoDocumentoModificado.getNombre(), infoDocumento.getNombre());
		Assert.assertEquals(infoDocumentoModificado.getTipoMime(), infoDocumento.getTipoMime());
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumentoModificado.getFechaCreacion())), String.valueOf(DateUtils.toDate(infoDocumento.getFechaCreacion())));
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumentoModificado.getFechaCaducidad())), String.valueOf(DateUtils.toDate(infoDocumento.getFechaCaducidad())));
		Assert.assertEquals(infoDocumentoModificado.getCsv(), infoDocumento.getCsv());
		Assert.assertEquals(String.valueOf(DateUtils.toDate(infoDocumentoModificado.getFechaCSV())), String.valueOf(DateUtils.toDate(infoDocumento.getFechaCSV())));
		Assert.assertEquals(infoDocumentoModificado.isDisponible(), infoDocumento.isDisponible());
		Assert.assertEquals(infoDocumentoModificado.getCodigoAplicacion(), infoDocumento.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoModificado.getNombreAplicacion(), infoDocumento.getNombreAplicacion());
	}

	@Test
	public void testUpdateInfoDocumento_InfoVacia() {

		try {
			getDocumentosDelegate().updateInfoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'infoDocumento' must not be null", e.getMessage());
		}
	}

	@Test
	public void testUpdateInfoDocumento_IdNoExistente() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		infoDocumento.setId(ID_NO_EXISTENTE);

		try {
			getDocumentosDelegate().updateInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testUpdateInfoDocumento_CodigoAplicacionNoExistente() {

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);

		infoDocumento.setCodigoAplicacion("XXX");

		try {
			getDocumentosDelegate().updateInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		}
	}

	@Test
	public void testDeleteInfoDocumento() {

		getDocumentosDelegate().deleteInfoDocumento(ID_EXISTENTE);
		Assert.assertNull("No se ha borrado el documento", getDocumentosDelegate().getInfoDocumento(ID_EXISTENTE));

		getDocumentosDelegate().deleteInfoDocumento(ID_NO_EXISTENTE);
	}

	@Test
	public void testExisteContenidoDocumento() {

		boolean existe = getDocumentosDelegate().existeContenidoDocumento(ID_EXISTENTE);
		Assert.assertTrue("No se ha encontrado el contenido del documento", existe);

		try {
			existe = getDocumentosDelegate().existeContenidoDocumento(ID_NO_EXISTENTE);
			Assert.fail("Debería lanzarse la excepción");
		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testExisteContenidoDocumento_IdNulo() {

		try {
			getDocumentosDelegate().existeContenidoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testGetContenidoDocumento() {

		byte[] contenido = getDocumentosDelegate().getContenidoDocumento(ID_EXISTENTE);
		Assert.assertTrue("No se ha encontrado el contenido del documento", ArrayUtils.isNotEmpty(contenido));

		try {
			contenido = getDocumentosDelegate().getContenidoDocumento(ID_NO_EXISTENTE);
			Assert.fail("Debería lanzarse la excepción");
		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		}
	}

	@Test
	public void testGetContenidoDocumento_IdNulo() {

		try {
			getDocumentosDelegate().getContenidoDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'id' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testRevocarDocumento() {

		getDocumentosDelegate().revocarDocumento(CSV_EXISTENTE);

		InfoDocumentoCSV infoDocumento = getDocumentosDelegate().getInfoDocumentoByCSV(CSV_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);
		Assert.assertEquals(Boolean.FALSE, infoDocumento.isDisponible());
	}

	@Test
	public void testRevocarDocumento_CSVNulo() {

		try {
			getDocumentosDelegate().revocarDocumento(null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'csv' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testRevocarDocumento_CsvNoExistente() {

		try {
			getDocumentosDelegate().revocarDocumento(CSV_NO_EXISTENTE);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

		} catch (CSVException e) {
			Assert.assertEquals("error.csv.document.csvNotFound", e.getMessageId());
		}
	}

	protected DescripcionI18N createDescripcionI18N(String locale, String descripcion) {

		DescripcionI18N descripcionI18N = new DescripcionI18N();
		descripcionI18N.setLocale(locale);
		descripcionI18N.setDescripcion(descripcion);
		return descripcionI18N;
	}

}
