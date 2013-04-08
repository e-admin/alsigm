package es.ieci.tecdoc.fwktd.csv.wsclient.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.xml.ws.soap.SOAPFaultException;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;
import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

@ContextConfiguration({
    "/beans/fwktd-csv-wsclient-applicationContext.xml" })
public class ServicioDocumentosWSClientImplIntegrationTest extends AbstractJUnit4SpringContextTests {

	private static final String CODIGO_APLICACION_MOCK = "APP1";
	private static final String CODIGO_APLICACION_NO_EXISTENTE = "XXXXX";

	private static final String ID_DOCUMENTO_NO_EXISTENTE = "999999";

	private static final String CSV_DOCUMENTO_EXISTENTE = "CSV-00000001";
	private static final String CSV_DOCUMENTO_NO_EXISTENTE = "XXXXX";

    @Autowired
    private ServicioDocumentosWSClientImpl fwktd_csv_servicioDocumentosWSClientImpl;

	protected ServicioDocumentos getServicioDocumentos() {
		return fwktd_csv_servicioDocumentosWSClientImpl;
	}

    @Autowired
    private ServicioAplicaciones fwktd_csv_servicioAplicacionesWSClientImpl;

	protected ServicioAplicaciones getServicioAplicaciones() {
		return fwktd_csv_servicioAplicacionesWSClientImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull("El servicio es nulo", getServicioDocumentos());
	}

	@Test
	public void testGenerarCSV() {
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		deleteDocumento(infoDocumento.getId());
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
		infoDocumentoForm.setCodigoAplicacion(CODIGO_APLICACION_MOCK);
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
		infoDocumentoForm.setCodigoAplicacion(CODIGO_APLICACION_MOCK);
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
		infoDocumentoForm.setCodigoAplicacion(CODIGO_APLICACION_NO_EXISTENTE);
		infoDocumentoForm.setDisponible(true);

		try {
			getServicioDocumentos().generarCSV(infoDocumentoForm);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ninguna aplicación con el código: XXXXX", e.getMessage());
		}
	}

	@Test
	public void testGetInfoDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			// Obtener la información del documento
			InfoDocumentoCSV infoDocumentoObtenido = getServicioDocumentos().getInfoDocumento(infoDocumento.getId());
			Assert.assertNotNull("No se ha encontrado el documento", infoDocumentoObtenido);
			assertEquals(infoDocumento, infoDocumentoObtenido);

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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
	public void testGetInfoDocumento_IdNoExistente() {

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumento(ID_DOCUMENTO_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", infoDocumento);
	}

	@Test
	public void testGetInfoDocumentoByCSV() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			// Obtener la información del documento
			InfoDocumentoCSV infoDocumentoObtenido = getServicioDocumentos().getInfoDocumentoByCSV(infoDocumento.getCsv());
			Assert.assertNotNull("No se ha encontrado el documento", infoDocumentoObtenido);
			assertEquals(infoDocumento, infoDocumentoObtenido);

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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

		InfoDocumentoCSV infoDocumento = getServicioDocumentos().getInfoDocumentoByCSV(CSV_DOCUMENTO_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", infoDocumento);
	}

	@Test
	public void testGetDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {
			DocumentoCSV documento = getServicioDocumentos().getDocumento(infoDocumento.getId());
			Assert.assertNotNull("No se ha encontrado el documento", documento);
			assertEquals(infoDocumento, documento);
			Assert.assertTrue("No se ha encontrado el contenido del documento", ArrayUtils.isNotEmpty(documento.getContenido()));

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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

		DocumentoCSV documento = getServicioDocumentos().getDocumento(ID_DOCUMENTO_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", documento);
	}

	@Test
	public void testGetDocumentoByCSV() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {
			DocumentoCSV documento = getServicioDocumentos().getDocumentoByCSV(infoDocumento.getCsv());
			Assert.assertNotNull("No se ha encontrado el documento", documento);
			assertEquals(infoDocumento, documento);
			Assert.assertTrue("No se ha encontrado el contenido del documento", ArrayUtils.isNotEmpty(documento.getContenido()));

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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

		DocumentoCSV documento = getServicioDocumentos().getDocumentoByCSV(CSV_DOCUMENTO_NO_EXISTENTE);
		Assert.assertNull("No se debería haber encontrado el documento", documento);
	}

	@Test
	public void testSaveInfoDocumento() {

		AplicacionCSV aplicacion = saveAplicacionCSV();

		InfoDocumentoCSV infoDocumento = new InfoDocumentoCSV();
		infoDocumento.setNombre("documento.pdf");
		infoDocumento.setTipoMime("applitacion/pdf");
		infoDocumento.setFechaCreacion(new Date());
		infoDocumento.setFechaCaducidad(new Date());
		infoDocumento.setCodigoAplicacion(aplicacion.getCodigo());
		infoDocumento.setNombreAplicacion(aplicacion.getNombre());
		infoDocumento.setCsv(CSV_DOCUMENTO_NO_EXISTENTE);
		infoDocumento.setFechaCSV(new Date());
		infoDocumento.setDisponible(true);

		infoDocumento.addDescripcion(null, "[default] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		infoDocumento.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		InfoDocumentoCSV infoDocumentoGuardado = getServicioDocumentos().saveInfoDocumento(infoDocumento);
		Assert.assertNotNull("No se ha guardado el documento", infoDocumentoGuardado);

		try {

			infoDocumento.setId(infoDocumentoGuardado.getId());
			assertEquals(infoDocumento, infoDocumentoGuardado);

			// Leer la información del documento
			infoDocumento = getServicioDocumentos().getInfoDocumento(infoDocumento.getId());
			Assert.assertNotNull("No se ha modificado el documento", infoDocumento);
			assertEquals(infoDocumentoGuardado, infoDocumento);

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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
	public void testUpdateInfoDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			Date fecha = new Date();

			// Modificar la información del documento
			infoDocumento.setFechaCaducidad(fecha);
			infoDocumento.setDisponible(false);

			// Guardar la información del documento
			InfoDocumentoCSV infoDocumentoModificado = getServicioDocumentos().updateInfoDocumento(infoDocumento);
			Assert.assertNotNull("No se ha modificado el documento", infoDocumentoModificado);
			assertEquals(infoDocumento, infoDocumentoModificado);

			// Leer la información del documento
			infoDocumento = getServicioDocumentos().getInfoDocumento(infoDocumento.getId());
			Assert.assertNotNull("No se ha modificado el documento", infoDocumento);
			assertEquals(infoDocumentoModificado, infoDocumento);

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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

		InfoDocumentoCSV infoDocumento = new InfoDocumentoCSV();
		infoDocumento.setId(ID_DOCUMENTO_NO_EXISTENTE);
		infoDocumento.setCsv(CSV_DOCUMENTO_EXISTENTE);
		infoDocumento.setNombre("documento.pdf");
		infoDocumento.setTipoMime("applitacion/pdf");
		infoDocumento.setFechaCreacion(new Date());
		infoDocumento.setFechaCaducidad(new Date());
		infoDocumento.setCodigoAplicacion(CODIGO_APLICACION_MOCK);
		infoDocumento.setDisponible(true);

		try {
			getServicioDocumentos().updateInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ningún documento con el identificador: 999999", e.getMessage());
		}
	}

	@Test
	public void testUpdateInfoDocumento_CodigoAplicacionNoExistente() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		infoDocumento.setCodigoAplicacion(CODIGO_APLICACION_NO_EXISTENTE);

		try {
			getServicioDocumentos().updateInfoDocumento(infoDocumento);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.application.codeNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ninguna aplicación con el código: XXXXX", e.getMessage());
		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
	}

	@Test
	public void testdeleteInfoDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		deleteDocumento(infoDocumento.getId());
		Assert.assertNull("No se ha borrado el documento", getServicioDocumentos().getInfoDocumento(infoDocumento.getId()));

		getServicioDocumentos().deleteInfoDocumento(infoDocumento.getId());
	}

	@Test
	public void testRevocarDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			getServicioDocumentos().revocarDocumento(infoDocumento.getCsv());

			InfoDocumentoCSV infoDocumentoRevocado = getServicioDocumentos().getInfoDocumento(infoDocumento.getId());
			Assert.assertNotNull("No se ha encontrado el documento", infoDocumentoRevocado);
			Assert.assertEquals(false, infoDocumentoRevocado.isDisponible());

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
		}
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
			getServicioDocumentos().revocarDocumento(CSV_DOCUMENTO_NO_EXISTENTE);
			Assert.fail("Debería producirse una ServicioGestionCSVException");

//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.document.csvNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ningún documento con el CSV: XXXXX", e.getMessage());
		}
	}

	@Test
	public void testExisteContenidoDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			boolean existe = getServicioDocumentos().existeContenidoDocumento(infoDocumento.getId());
			Assert.assertTrue("No se ha encontrado el contenido del documento", existe);

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
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
	public void testExisteContenidoDocumento_IdNoExistente() {

		try {
			getServicioDocumentos().existeContenidoDocumento(ID_DOCUMENTO_NO_EXISTENTE);
			Assert.fail("Debería lanzarse la excepción");
//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ningún documento con el identificador: 999999", e.getMessage());
		}
	}

	@Test
	public void testGetContenidoDocumento() {

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			byte[] contenido = getServicioDocumentos().getContenidoDocumento(infoDocumento.getId());
			Assert.assertTrue("No se ha encontrado el contenido del documento", ArrayUtils.isNotEmpty(contenido));
		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
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
	public void testGetContenidoDocumento_IdNoExistente() {

		try {
			getServicioDocumentos().getContenidoDocumento(ID_DOCUMENTO_NO_EXISTENTE);
			Assert.fail("Debería lanzarse la excepción");
//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ningún documento con el identificador: 999999", e.getMessage());
		}
	}

	@Test
	public void writeDocumento() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Crear la información del documento
		InfoDocumentoCSV infoDocumento = generarCSV();
		Assert.assertNotNull("No se ha generado el CSV", infoDocumento);

		try {

			getServicioDocumentos().writeDocumento(infoDocumento.getId(), baos);
			Assert.assertTrue("No se ha encontrado el contenido del documento", baos.size() > 0);

		} finally {

			// Eliminar la información del documento
			deleteDocumento(infoDocumento.getId());
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
	public void testWriteDocumento_IdNoExistente() throws IOException {

		try {
			getServicioDocumentos().writeDocumento(ID_DOCUMENTO_NO_EXISTENTE, new ByteArrayOutputStream());
			Assert.fail("Debería lanzarse la excepción");
//		} catch (CSVException e) {
//			Assert.assertEquals("error.csv.document.idNotFound", e.getMessageId());
		} catch (SOAPFaultException e) {
			Assert.assertEquals("No existe ningún documento con el identificador: 999999", e.getMessage());
		}
	}

	@Test
	public void testWriteDocumento_OutputStreamNulo() throws IOException {

		try {
			getServicioDocumentos().writeDocumento(ID_DOCUMENTO_NO_EXISTENTE, null);
			Assert.fail("Debería producirse una IllegalArgumentException");

		} catch (IllegalArgumentException e) {
			Assert.assertEquals("'outputStream' must not be null", e.getMessage());
		}
	}

	protected InfoDocumentoCSV generarCSV() {

		AplicacionCSV aplicacion = saveAplicacionCSV();

		InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
		infoDocumentoForm.setNombre("documento.pdf");
		infoDocumentoForm.setTipoMime("applitacion/pdf");
		infoDocumentoForm.setFechaCreacion(new Date());
		infoDocumentoForm.setFechaCaducidad(new Date());
		infoDocumentoForm.setCodigoAplicacion(aplicacion.getCodigo());
		infoDocumentoForm.setDisponible(true);

		infoDocumentoForm.addDescripcion(null, "[default] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		infoDocumentoForm.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		return getServicioDocumentos().generarCSV(infoDocumentoForm);
	}

	protected AplicacionCSV saveAplicacionCSV() {

		AplicacionCSVForm aplicacionForm = new AplicacionCSVForm();
		aplicacionForm.setCodigo(CODIGO_APLICACION_MOCK);
		aplicacionForm.setNombre("Aplicación MOCK");
		aplicacionForm.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		return getServicioAplicaciones().saveAplicacion(aplicacionForm);
	}

	protected void deleteDocumento(String id) {

		getServicioDocumentos().deleteInfoDocumento(id);

		AplicacionCSV aplicacion = getServicioAplicaciones().getAplicacionByCodigo(CODIGO_APLICACION_MOCK);
		getServicioAplicaciones().deleteAplicacion(aplicacion.getId());
	}

	protected void assertEquals(InfoDocumentoCSV infoDocumento1, InfoDocumentoCSV infoDocumento2) {

		Assert.assertNotNull(infoDocumento1);
		Assert.assertNotNull(infoDocumento2);

		Assert.assertEquals(infoDocumento1.getId(), infoDocumento2.getId());
		Assert.assertEquals(infoDocumento1.getNombre(), infoDocumento2.getNombre());
		Assert.assertEquals(infoDocumento1.getTipoMime(), infoDocumento2.getTipoMime());
		Assert.assertEquals(String.valueOf(infoDocumento1.getFechaCreacion()), String.valueOf(infoDocumento2.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(infoDocumento1.getFechaCaducidad()), String.valueOf(infoDocumento2.getFechaCaducidad()));
		Assert.assertEquals(infoDocumento1.getCsv(), infoDocumento2.getCsv());
		Assert.assertEquals(String.valueOf(infoDocumento1.getFechaCSV()), String.valueOf(infoDocumento2.getFechaCSV()));
		Assert.assertEquals(infoDocumento1.isDisponible(), infoDocumento2.isDisponible());
		Assert.assertEquals(infoDocumento1.getCodigoAplicacion(), infoDocumento2.getCodigoAplicacion());
		Assert.assertEquals(infoDocumento1.getNombreAplicacion(), infoDocumento2.getNombreAplicacion());

		// Descripciones
		Assert.assertEquals(infoDocumento1.getDescripciones().size(), infoDocumento2.getDescripciones().size());
		for (String key : infoDocumento1.getDescripciones().keySet()) {
			Assert.assertEquals(infoDocumento1.getDescripciones().get(key), infoDocumento2.getDescripciones().get(key));
		}
	}
}
