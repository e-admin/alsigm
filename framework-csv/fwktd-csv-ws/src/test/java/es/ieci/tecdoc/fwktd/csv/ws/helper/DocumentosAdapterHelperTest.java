package es.ieci.tecdoc.fwktd.csv.ws.helper;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.ws.service.DescripcionI18N;
import es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;


/**
 * Test de la clase de utilidad para la adaptación de objetos con la información
 * del documento desde el fwktd-csv-api.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentosAdapterHelperTest {

	@Test
	public void testGetCoreInfoDocumentoCSVForm() {

		InfoDocumentoCSVForm infoDocumentoCSVForm = null;
		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm coreInfoDocumentoCSVForm = DocumentoAdapterHelper.getCoreInfoDocumentoCSVForm(infoDocumentoCSVForm);
		Assert.assertNull("El objeto debería ser nulo", coreInfoDocumentoCSVForm);

		infoDocumentoCSVForm = new InfoDocumentoCSVForm();
		infoDocumentoCSVForm.setNombre("documento.txt");
		infoDocumentoCSVForm.setTipoMime("text/plain");
		infoDocumentoCSVForm.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoCSVForm.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoCSVForm.setCodigoAplicacion("COD_APP");
		infoDocumentoCSVForm.setDisponible(true);

		infoDocumentoCSVForm.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		infoDocumentoCSVForm.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		infoDocumentoCSVForm.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		infoDocumentoCSVForm.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		infoDocumentoCSVForm.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		coreInfoDocumentoCSVForm = DocumentoAdapterHelper.getCoreInfoDocumentoCSVForm(infoDocumentoCSVForm);
		assertEquals(infoDocumentoCSVForm, coreInfoDocumentoCSVForm);
	}

	@Test
	public void testGetCoreInfoDocumentoCSV() {

		InfoDocumentoCSV infoDocumentoCSV = null;
		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV coreInfoDocumentoCSV = DocumentoAdapterHelper.getCoreInfoDocumentoCSV(infoDocumentoCSV);
		Assert.assertNull("El objeto debería ser nulo", coreInfoDocumentoCSV);

		infoDocumentoCSV = new InfoDocumentoCSV();
		infoDocumentoCSV.setId("1");
		infoDocumentoCSV.setNombre("documento.txt");
		infoDocumentoCSV.setTipoMime("text/plain");
		infoDocumentoCSV.setFechaCreacion(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoCSV.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(new Date()));
		infoDocumentoCSV.setCodigoAplicacion("COD_APP");
		infoDocumentoCSV.setNombreAplicacion("Aplicación");
		infoDocumentoCSV.setDisponible(true);
		infoDocumentoCSV.setCsv("CSV000");
		infoDocumentoCSV.setFechaCSV(DateUtils.toXMLGregorianCalendar(new Date()));

		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("default", "[default] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("ca", "[ca] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("es", "[es] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("eu", "[eu] Descripción del documento"));
		infoDocumentoCSV.getDescripcionesI18N().add(createDescripcionI18N("gl", "[gl] Descripción del documento"));

		coreInfoDocumentoCSV = DocumentoAdapterHelper.getCoreInfoDocumentoCSV(infoDocumentoCSV);
		assertEquals(infoDocumentoCSV, coreInfoDocumentoCSV);
	}

	@Test
	public void testGetInfoDocumentoCSV() {

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV coreInfoDocumentoCSV = null;
		InfoDocumentoCSV infoDocumentoCSV = DocumentoAdapterHelper.getInfoDocumentoCSV(coreInfoDocumentoCSV);
		Assert.assertNull("El objeto debería ser nulo", infoDocumentoCSV);

		coreInfoDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV();
		coreInfoDocumentoCSV.setId("1");
		coreInfoDocumentoCSV.setNombre("documento.txt");
		coreInfoDocumentoCSV.setTipoMime("text/plain");
		coreInfoDocumentoCSV.setFechaCreacion(new Date());
		coreInfoDocumentoCSV.setFechaCaducidad(new Date());
		coreInfoDocumentoCSV.setCodigoAplicacion("COD_APP");
		coreInfoDocumentoCSV.setNombreAplicacion("Aplicación");
		coreInfoDocumentoCSV.setDisponible(true);
		coreInfoDocumentoCSV.setCsv("CSV000");
		coreInfoDocumentoCSV.setFechaCSV(new Date());

		coreInfoDocumentoCSV.addDescripcion(null, "[default] Descripción del documento");
		coreInfoDocumentoCSV.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		coreInfoDocumentoCSV.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		coreInfoDocumentoCSV.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		coreInfoDocumentoCSV.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		infoDocumentoCSV = DocumentoAdapterHelper.getInfoDocumentoCSV(coreInfoDocumentoCSV);
		assertEquals(infoDocumentoCSV, coreInfoDocumentoCSV);
	}

	@Test
	public void testGetDocumentoCSV() {

		es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV coreDocumentoCSV = null;
		DocumentoCSV documentoCSV = DocumentoAdapterHelper.getDocumentoCSV(coreDocumentoCSV);
		Assert.assertNull("El objeto debería ser nulo", documentoCSV);

		coreDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV();
		coreDocumentoCSV.setId("1");
		coreDocumentoCSV.setNombre("documento.txt");
		coreDocumentoCSV.setTipoMime("text/plain");
		coreDocumentoCSV.setFechaCreacion(new Date());
		coreDocumentoCSV.setFechaCaducidad(new Date());
		coreDocumentoCSV.setCodigoAplicacion("COD_APP");
		coreDocumentoCSV.setNombreAplicacion("Aplicación");
		coreDocumentoCSV.setDisponible(true);
		coreDocumentoCSV.setCsv("CSV000");
		coreDocumentoCSV.setFechaCSV(new Date());
		coreDocumentoCSV.setContenido("Contenido del documento".getBytes());

		coreDocumentoCSV.addDescripcion(null, "[default] Descripción del documento");
		coreDocumentoCSV.addDescripcion(new Locale("ca"), "[ca] Descripción del documento");
		coreDocumentoCSV.addDescripcion(new Locale("es"), "[es] Descripción del documento");
		coreDocumentoCSV.addDescripcion(new Locale("eu"), "[eu] Descripción del documento");
		coreDocumentoCSV.addDescripcion(new Locale("gl"), "[gl] Descripción del documento");

		documentoCSV = DocumentoAdapterHelper.getDocumentoCSV(coreDocumentoCSV);
		assertEquals(documentoCSV, coreDocumentoCSV);
	}

	protected void assertEquals(InfoDocumentoCSVForm infoDocumentoCSVForm,
			es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm coreInfoDocumentoCSVForm) {

		Assert.assertEquals(infoDocumentoCSVForm.getNombre(),
				coreInfoDocumentoCSVForm.getNombre());
		Assert.assertEquals(infoDocumentoCSVForm.getTipoMime(),
				coreInfoDocumentoCSVForm.getTipoMime());
		Assert.assertEquals(DateUtils.toDate(infoDocumentoCSVForm.getFechaCreacion()).toString(),
				coreInfoDocumentoCSVForm.getFechaCreacion().toString());
		Assert.assertEquals(DateUtils.toDate(infoDocumentoCSVForm.getFechaCaducidad()).toString(),
				coreInfoDocumentoCSVForm.getFechaCaducidad().toString());
		Assert.assertEquals(infoDocumentoCSVForm.getCodigoAplicacion(),
				coreInfoDocumentoCSVForm.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoCSVForm.isDisponible(),
				coreInfoDocumentoCSVForm.isDisponible());

		// Descripciones
		Assert.assertEquals(infoDocumentoCSVForm.getDescripcionesI18N().size(), coreInfoDocumentoCSVForm.getDescripciones().size());
		for (DescripcionI18N descripcionI18N : infoDocumentoCSVForm.getDescripcionesI18N()) {
			Assert.assertEquals(descripcionI18N.getDescripcion(), coreInfoDocumentoCSVForm.getDescripciones().get(descripcionI18N.getLocale()));
		}

		if (infoDocumentoCSVForm instanceof InfoDocumentoCSV && coreInfoDocumentoCSVForm instanceof es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV) {
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getId(),
					((es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)coreInfoDocumentoCSVForm).getId());
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getNombreAplicacion(),
					((es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)coreInfoDocumentoCSVForm).getNombreAplicacion());
			Assert.assertEquals(((InfoDocumentoCSV)infoDocumentoCSVForm).getCsv(),
					((es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)coreInfoDocumentoCSVForm).getCsv());
			Assert.assertEquals(DateUtils.toDate(((InfoDocumentoCSV)infoDocumentoCSVForm).getFechaCSV()).toString(),
					((es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)coreInfoDocumentoCSVForm).getFechaCSV().toString());
		}


		if (infoDocumentoCSVForm instanceof DocumentoCSV && coreInfoDocumentoCSVForm instanceof es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV) {
			Assert.assertEquals(((DocumentoCSV)infoDocumentoCSVForm).getContenido(),
					((es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV)coreInfoDocumentoCSVForm).getContenido());
		}
	}

	protected DescripcionI18N createDescripcionI18N(String locale, String descripcion) {

		DescripcionI18N descripcionI18N = new DescripcionI18N();
		descripcionI18N.setLocale(locale);
		descripcionI18N.setDescripcion(descripcion);
		return descripcionI18N;
	}
}
