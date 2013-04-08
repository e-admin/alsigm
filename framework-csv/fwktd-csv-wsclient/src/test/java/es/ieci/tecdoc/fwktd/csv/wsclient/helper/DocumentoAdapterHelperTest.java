package es.ieci.tecdoc.fwktd.csv.wsclient.helper;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.DescripcionI18N;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * Test de la clase de utilidad para la adaptación de objetos con la información
 * del documento desde el fwktd-csv-ws.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoAdapterHelperTest {

	@Test
	public void testGetWSInfoDocumentoCSVForm() {

		InfoDocumentoCSVForm infoDocumentoCSVForm = null;
		es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm wsInfoDocumentoCSVForm = DocumentoAdapterHelper.getWSInfoDocumentoCSVForm(infoDocumentoCSVForm);
		Assert.assertNull("El objeto debería ser nulo", wsInfoDocumentoCSVForm);

		infoDocumentoCSVForm = new InfoDocumentoCSVForm();
		infoDocumentoCSVForm.setNombre("documento.txt");
		infoDocumentoCSVForm.setTipoMime("text/plain");
		infoDocumentoCSVForm.setFechaCreacion(new Date());
		infoDocumentoCSVForm.setFechaCaducidad(new Date());
		infoDocumentoCSVForm.setCodigoAplicacion("COD_APP");
		infoDocumentoCSVForm.setDisponible(true);

		infoDocumentoCSVForm.addDescripcion(null, "[default] Descripción del documento");
		infoDocumentoCSVForm.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		infoDocumentoCSVForm.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		infoDocumentoCSVForm.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		infoDocumentoCSVForm.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		wsInfoDocumentoCSVForm = DocumentoAdapterHelper.getWSInfoDocumentoCSVForm(infoDocumentoCSVForm);
		assertEquals(infoDocumentoCSVForm, wsInfoDocumentoCSVForm);
	}

	@Test
	public void testGetWSInfoDocumentoCSV() {

		InfoDocumentoCSV infoDocumentoCSV = null;
		es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV wsInfoDocumentoCSV = DocumentoAdapterHelper.getWSInfoDocumentoCSV(infoDocumentoCSV);
		Assert.assertNull("El objeto debería ser nulo", wsInfoDocumentoCSV);

		infoDocumentoCSV = new InfoDocumentoCSV();
		infoDocumentoCSV.setId("1");
		infoDocumentoCSV.setNombre("documento.txt");
		infoDocumentoCSV.setTipoMime("text/plain");
		infoDocumentoCSV.setFechaCreacion(new Date());
		infoDocumentoCSV.setFechaCaducidad(new Date());
		infoDocumentoCSV.setCodigoAplicacion("COD_APP");
		infoDocumentoCSV.setNombreAplicacion("Aplicación");
		infoDocumentoCSV.setDisponible(true);
		infoDocumentoCSV.setCsv("CSV000");
		infoDocumentoCSV.setFechaCSV(new Date());

		infoDocumentoCSV.addDescripcion(null, "[default] Descripción del documento");
		infoDocumentoCSV.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		infoDocumentoCSV.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		infoDocumentoCSV.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		infoDocumentoCSV.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		wsInfoDocumentoCSV = DocumentoAdapterHelper.getWSInfoDocumentoCSV(infoDocumentoCSV);
		assertEquals(infoDocumentoCSV, wsInfoDocumentoCSV);
	}


	@Test
	public void testGetInfoDocumentoCSV() {

		es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV wsInfoDocumentoCSV = null;
		InfoDocumentoCSV infoDocumentoCSV = DocumentoAdapterHelper.getInfoDocumentoCSV(wsInfoDocumentoCSV);
		Assert.assertNull("El objeto debería ser nulo", infoDocumentoCSV);

		wsInfoDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV();
		wsInfoDocumentoCSV.setId("1");
		wsInfoDocumentoCSV.setNombre("documento.txt");
		wsInfoDocumentoCSV.setTipoMime("text/plain");
		wsInfoDocumentoCSV.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		wsInfoDocumentoCSV.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		wsInfoDocumentoCSV.setCodigoAplicacion("COD_APP");
		wsInfoDocumentoCSV.setNombreAplicacion("Aplicación");
		wsInfoDocumentoCSV.setDisponible(true);
		wsInfoDocumentoCSV.setCsv("CSV000");
		wsInfoDocumentoCSV.setFechaCSV(DateUtils.toXMLGregorianCalendar(new Date()));

		wsInfoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		wsInfoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		wsInfoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		wsInfoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		wsInfoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		infoDocumentoCSV = DocumentoAdapterHelper.getInfoDocumentoCSV(wsInfoDocumentoCSV);
		assertEquals(infoDocumentoCSV, wsInfoDocumentoCSV);
	}

	@Test
	public void testGetDocumentoCSV() {

		es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV wsDocumentoCSV = null;
		DocumentoCSV documentoCSV = DocumentoAdapterHelper.getDocumentoCSV(wsDocumentoCSV);
		Assert.assertNull("El objeto debería ser nulo", documentoCSV);

		wsDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV();
		wsDocumentoCSV.setId("1");
		wsDocumentoCSV.setNombre("documento.txt");
		wsDocumentoCSV.setTipoMime("text/plain");
		wsDocumentoCSV.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		wsDocumentoCSV.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		wsDocumentoCSV.setCodigoAplicacion("COD_APP");
		wsDocumentoCSV.setNombreAplicacion("Aplicación");
		wsDocumentoCSV.setDisponible(true);
		wsDocumentoCSV.setCsv("CSV000");
		wsDocumentoCSV.setFechaCSV(DateUtils.toXMLGregorianCalendar(new Date()));
		wsDocumentoCSV.setContenido("contenido del documento".getBytes());

		wsDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		wsDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		wsDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		wsDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		wsDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		documentoCSV = DocumentoAdapterHelper.getDocumentoCSV(wsDocumentoCSV);
		assertEquals(documentoCSV, wsDocumentoCSV);
	}

	protected void assertEquals(InfoDocumentoCSVForm infoDocumentoCSVForm,
			es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm wsInfoDocumentoCSVForm) {

		Assert.assertEquals(infoDocumentoCSVForm.getNombre(),
				wsInfoDocumentoCSVForm.getNombre());
		Assert.assertEquals(infoDocumentoCSVForm.getTipoMime(),
				wsInfoDocumentoCSVForm.getTipoMime());
		Assert.assertEquals(infoDocumentoCSVForm.getFechaCreacion().toString(),
				DateUtils.toDate(wsInfoDocumentoCSVForm.getFechaCreacion()).toString());
		Assert.assertEquals(infoDocumentoCSVForm.getFechaCaducidad().toString(),
				DateUtils.toDate(wsInfoDocumentoCSVForm.getFechaCaducidad()).toString());
		Assert.assertEquals(infoDocumentoCSVForm.getCodigoAplicacion(),
				wsInfoDocumentoCSVForm.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoCSVForm.isDisponible(),
				wsInfoDocumentoCSVForm.isDisponible());

		// Descripciones
		Assert.assertEquals(wsInfoDocumentoCSVForm.getDescripcionesI18N().size(), infoDocumentoCSVForm.getDescripciones().size());
		for (DescripcionI18N descripcionI18N : wsInfoDocumentoCSVForm.getDescripcionesI18N()) {
			Assert.assertEquals(descripcionI18N.getDescripcion(), infoDocumentoCSVForm.getDescripciones().get(descripcionI18N.getLocale()));
		}

		if (infoDocumentoCSVForm instanceof InfoDocumentoCSV && wsInfoDocumentoCSVForm instanceof es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV) {
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getId(),
					((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV)wsInfoDocumentoCSVForm).getId());
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getNombreAplicacion(),
					((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV)wsInfoDocumentoCSVForm).getNombreAplicacion());
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getCsv(),
					((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV)wsInfoDocumentoCSVForm).getCsv());
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getFechaCSV().toString(),
					DateUtils.toDate(((es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV)wsInfoDocumentoCSVForm).getFechaCSV()).toString());
		}


		if (infoDocumentoCSVForm instanceof DocumentoCSV && wsInfoDocumentoCSVForm instanceof es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV) {
			Assert.assertEquals(((DocumentoCSV)infoDocumentoCSVForm).getContenido(),
					((es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV)wsInfoDocumentoCSVForm).getContenido());
		}
	}

	protected DescripcionI18N createDescripcionI18N(String locale, String descripcion) {

		DescripcionI18N descripcionI18N = new DescripcionI18N();
		descripcionI18N.setLocale(locale);
		descripcionI18N.setDescripcion(descripcion);
		return descripcionI18N;
	}
}
