package es.ieci.tecdoc.fwktd.csv.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-csv-api-applicationContext.xml",
		"/beans/fwktd-csv-test-beans.xml" })
public class ServicioDocumentosImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String ID_EXISTENTE = "10000001";
	private static final String ID_NO_EXISTENTE = "99999";

	private static final String CSV_EXISTENTE = "00001";
	private static final String CSV_NO_EXISTENTE = "XXXXX";

	@Autowired
	private ServicioDocumentosImpl fwktd_csv_api_servicioDocumentosImpl;

	public ServicioDocumentos getServicioDocumentos() {
		return fwktd_csv_api_servicioDocumentosImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull("El servicio es nulo", getServicioDocumentos());
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

		infoDocumentoForm.addDescripcion(null, "Descripción por defecto");
		infoDocumentoForm.addDescripcion(new Locale("es"), "[es] Descripción del documento");

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().generarCSV(infoDocumentoForm);
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);
		Assert.assertEquals("Descripción por defecto", infoDocumento.getDescripcion(null));
		Assert.assertEquals("Descripción por defecto", infoDocumento.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento", infoDocumento.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento", infoDocumento.getDescripcion(new Locale("es", "ES")));
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
		Assert.assertEquals(ID_EXISTENTE, infoDocumento.getId());
		Assert.assertEquals("Documento_1.pdf", infoDocumento.getNombre());
		Assert.assertEquals("application/pdf", infoDocumento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(infoDocumento.getFechaCreacion()));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(infoDocumento.getFechaCaducidad()));
		Assert.assertEquals("00001", infoDocumento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(infoDocumento.getFechaCSV()));
		Assert.assertEquals(true, infoDocumento.isDisponible());
		Assert.assertEquals("APP1", infoDocumento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", infoDocumento.getNombreAplicacion());
		Assert.assertEquals("[default] Descripción del documento 1", infoDocumento.getDescripcion(null));
		Assert.assertEquals("[default] Descripción del documento 1", infoDocumento.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento 1", infoDocumento.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento 1", infoDocumento.getDescripcion(new Locale("es", "ES")));

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
		Assert.assertEquals(ID_EXISTENTE, infoDocumento.getId());
		Assert.assertEquals("Documento_1.pdf", infoDocumento.getNombre());
		Assert.assertEquals("application/pdf", infoDocumento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(infoDocumento.getFechaCreacion()));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(infoDocumento.getFechaCaducidad()));
		Assert.assertEquals("00001", infoDocumento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(infoDocumento.getFechaCSV()));
		Assert.assertEquals(true, infoDocumento.isDisponible());
		Assert.assertEquals("APP1", infoDocumento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", infoDocumento.getNombreAplicacion());
		Assert.assertEquals("[default] Descripción del documento 1", infoDocumento.getDescripcion(null));
		Assert.assertEquals("[default] Descripción del documento 1", infoDocumento.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento 1", infoDocumento.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento 1", infoDocumento.getDescripcion(new Locale("es", "ES")));
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
		Assert.assertEquals(ID_EXISTENTE, documento.getId());
		Assert.assertEquals("Documento_1.pdf", documento.getNombre());
		Assert.assertEquals("application/pdf", documento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(documento.getFechaCreacion()));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(documento.getFechaCaducidad()));
		Assert.assertEquals("00001", documento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(documento.getFechaCSV()));
		Assert.assertEquals(true, documento.isDisponible());
		Assert.assertEquals("APP1", documento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", documento.getNombreAplicacion());
		Assert.assertTrue("No se ha encontrado el contenido", ArrayUtils.isNotEmpty(documento.getContenido()));
		Assert.assertEquals("[default] Descripción del documento 1", documento.getDescripcion(null));
		Assert.assertEquals("[default] Descripción del documento 1", documento.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento 1", documento.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento 1", documento.getDescripcion(new Locale("es", "ES")));
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
		Assert.assertEquals(ID_EXISTENTE, documento.getId());
		Assert.assertEquals("Documento_1.pdf", documento.getNombre());
		Assert.assertEquals("application/pdf", documento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(documento.getFechaCreacion()));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(documento.getFechaCaducidad()));
		Assert.assertEquals("00001", documento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(documento.getFechaCSV()));
		Assert.assertEquals(true, documento.isDisponible());
		Assert.assertEquals("APP1", documento.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 1", documento.getNombreAplicacion());
		Assert.assertTrue("No se ha encontrado el contenido", ArrayUtils.isNotEmpty(documento.getContenido()));
		Assert.assertEquals("[default] Descripción del documento 1", documento.getDescripcion(null));
		Assert.assertEquals("[default] Descripción del documento 1", documento.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento 1", documento.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento 1", documento.getDescripcion(new Locale("es", "ES")));
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

		infoDocumento.addDescripcion(null, "Descripción por defecto");
		infoDocumento.addDescripcion(new Locale("es"), "[es] Descripción del documento");

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
		Assert.assertEquals("Descripción por defecto", infoDocumentoCreado.getDescripcion(null));
		Assert.assertEquals("Descripción por defecto", infoDocumentoCreado.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento", infoDocumentoCreado.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento", infoDocumentoCreado.getDescripcion(new Locale("es", "ES")));

		// Leer la información del documento
		infoDocumento = getServicioDocumentos().getInfoDocumento(infoDocumentoCreado.getId());
		Assert.assertNotNull("No se ha modificado el documento", infoDocumento);
		Assert.assertEquals(infoDocumentoCreado.getId(), infoDocumento.getId());
		Assert.assertEquals(infoDocumentoCreado.getNombre(), infoDocumento.getNombre());
		Assert.assertEquals(infoDocumentoCreado.getTipoMime(), infoDocumento.getTipoMime());
		Assert.assertEquals(String.valueOf(infoDocumentoCreado.getFechaCreacion()), String.valueOf(infoDocumento.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(infoDocumentoCreado.getFechaCaducidad()), String.valueOf(infoDocumento.getFechaCaducidad()));
		Assert.assertEquals(infoDocumentoCreado.getCsv(), infoDocumento.getCsv());
		Assert.assertEquals(String.valueOf(infoDocumentoCreado.getFechaCSV()), String.valueOf(infoDocumento.getFechaCSV()));
		Assert.assertEquals(infoDocumentoCreado.isDisponible(), infoDocumento.isDisponible());
		Assert.assertEquals(infoDocumentoCreado.getCodigoAplicacion(), infoDocumento.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoCreado.getNombreAplicacion(), infoDocumento.getNombreAplicacion());
		Assert.assertEquals("Descripción por defecto", infoDocumento.getDescripcion(null));
		Assert.assertEquals("Descripción por defecto", infoDocumento.getDescripcion(Locale.CANADA));
		Assert.assertEquals("[es] Descripción del documento", infoDocumento.getDescripcion(new Locale("es")));
		Assert.assertEquals("[es] Descripción del documento", infoDocumento.getDescripcion(new Locale("es", "ES")));
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

		Assert.assertEquals(ID_EXISTENTE, infoDocumento.getId());
		Assert.assertEquals("Documento_1.pdf", infoDocumento.getNombre());
		Assert.assertEquals("application/pdf", infoDocumento.getTipoMime());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(infoDocumentoModificado.getFechaCreacion()));
		Assert.assertEquals(fecha.toString(), String.valueOf(infoDocumentoModificado.getFechaCaducidad()));
		Assert.assertEquals(CSV_EXISTENTE, infoDocumentoModificado.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(infoDocumentoModificado.getFechaCSV()));
		Assert.assertEquals(false, infoDocumentoModificado.isDisponible());
		Assert.assertEquals("APP2", infoDocumentoModificado.getCodigoAplicacion());
		Assert.assertEquals("Aplicación 2", infoDocumentoModificado.getNombreAplicacion());

		// Leer la información del documento
		infoDocumento = getServicioDocumentos().getInfoDocumento(ID_EXISTENTE);
		Assert.assertNotNull("No se ha modificado el documento", infoDocumento);
		Assert.assertEquals(infoDocumentoModificado.getId(), infoDocumento.getId());
		Assert.assertEquals(infoDocumentoModificado.getNombre(), infoDocumento.getNombre());
		Assert.assertEquals(infoDocumentoModificado.getTipoMime(), infoDocumento.getTipoMime());
		Assert.assertEquals(String.valueOf(infoDocumentoModificado.getFechaCreacion()), String.valueOf(infoDocumento.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(infoDocumentoModificado.getFechaCaducidad()), String.valueOf(infoDocumento.getFechaCaducidad()));
		Assert.assertEquals(infoDocumentoModificado.getCsv(), infoDocumento.getCsv());
		Assert.assertEquals(String.valueOf(infoDocumentoModificado.getFechaCSV()), String.valueOf(infoDocumento.getFechaCSV()));
		Assert.assertEquals(infoDocumentoModificado.isDisponible(), infoDocumento.isDisponible());
		Assert.assertEquals(infoDocumentoModificado.getCodigoAplicacion(), infoDocumento.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoModificado.getNombreAplicacion(), infoDocumento.getNombreAplicacion());
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
		Assert.assertNull("No se ha borrado el documento", getServicioDocumentos().getInfoDocumento(ID_EXISTENTE));

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

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumentoByCSV(CSV_EXISTENTE);
		Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);
		Assert.assertEquals(false, infoDocumento.isDisponible());
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

}
