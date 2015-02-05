package es.ieci.tecdoc.fwktd.csv.api.dao.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.csv.api.dao.AplicacionDao;
import es.ieci.tecdoc.fwktd.csv.api.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.csv.api.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class DocumentoDaoImplTest extends BaseDaoTest {

	protected static final String ID_APLICACION_EXISTENTE = "20000001";

	protected static final String ID_DOCUMENTO_EXISTENTE = "10000001";
	protected static final String ID_DOCUMENTO_NO_EXISTENTE = "99999999";
	protected static final String ID_DOCUMENTO_BORRAR = "10000003";

	protected static final String CSV_DOCUMENTO_EXISTENTE = "00001";
	protected static final String CSV_DOCUMENTO_BORRAR = "00003";

	@Autowired
	private AplicacionDao fwktd_sir_aplicacionDao;

	@Autowired
	private DocumentoDao fwktd_sir_documentoDao;


	public AplicacionDao getAplicacionDao() {
		return fwktd_sir_aplicacionDao;
	}

	public DocumentoDao getDocumentoDao() {
		return fwktd_sir_documentoDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDao<Entity, String> getDao() {
		return (BaseDao) getDocumentoDao();
	}

	public String getIdExistente() {
		return ID_DOCUMENTO_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_DOCUMENTO_NO_EXISTENTE;
	}

	public String getIdBorrar() {
		return ID_DOCUMENTO_BORRAR;
	}

	@Test
	public void testGet() {
		DocumentoVO documento = getDocumentoDao().get(getIdExistente());
		assertDocumento(documento);
	}

	@Test
	public void testGetDocumentoByCSV() {
		DocumentoVO documento = getDocumentoDao().getDocumentoByCSV(CSV_DOCUMENTO_EXISTENTE);
		assertDocumento(documento);
	}

	@Test
	public void testSave() {

		DocumentoVO documento = createDocumentoVO(getIdNoExistente());

		DocumentoVO documentoCreado = (DocumentoVO) getDocumentoDao().save(documento);
		assertEquals(documento, documentoCreado);
	}

	@Test
	public void testUpdate() {

		DocumentoVO documento = getDocumentoDao().get(getIdExistente());
		documento.setDisponible(false);

		DocumentoVO documentoModificado = getDocumentoDao().update(documento);
		assertEquals(documento, documentoModificado);
	}

	@Test
	public void testDeleteDocumentoByCSV() {
		int count = getDocumentoDao().count();
		getDocumentoDao().deleteDocumentoByCSV(CSV_DOCUMENTO_BORRAR);
		Assert.assertEquals(getDocumentoDao().count(), count-1);
	}

	@Test
	public void testDeleteAll() {

		getDocumentoDao().deleteAll();
		Assert.assertEquals(getDocumentoDao().count(), 0);
	}

	private void assertDocumento(DocumentoVO documento) {

		Assert.assertNotNull("No se ha obtenido el documento", documento);
		Assert.assertEquals(getIdExistente(), documento.getId());
		Assert.assertEquals("Documento_1.pdf", documento.getNombre());
		Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?><labels><label locale='default'>[default] Descripción del documento 1</label><label locale='ca'>[ca] Descripción del documento 1</label><label locale='es'>[es] Descripción del documento 1</label><label locale='eu'>[eu] Descripción del documento 1</label><label locale='gl'>[gl] Descripción del documento 1</label></labels>", documento.getDescripcion());
		Assert.assertEquals("application/pdf", documento.getTipoMIME());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(documento.getFechaCreacion()));
		Assert.assertEquals("Thu Aug 06 12:07:31 CEST 2020", String.valueOf(documento.getFechaCaducidad()));
		Assert.assertEquals("00001", documento.getCsv());
		Assert.assertEquals("Sat Aug 06 12:07:31 CEST 2011", String.valueOf(documento.getFechaCSV()));
		Assert.assertTrue(documento.isDisponible());
		Assert.assertNotNull("No se ha obtenido la aplicación del documento", documento.getAplicacion());
		Assert.assertEquals("20000001", documento.getAplicacion().getId());
		Assert.assertEquals("APP1", documento.getAplicacion().getCodigo());
		Assert.assertEquals("Aplicación 1", documento.getAplicacion().getNombre());
		Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>", documento.getAplicacion().getInfoConexion());
	}

	private void assertEquals(DocumentoVO documento, DocumentoVO documento2) {

		Assert.assertNotNull("El documento 1 es nulo", documento);
		Assert.assertNotNull("El documento 2 es nulo", documento2);

		Assert.assertEquals(documento.getId(), documento2.getId());
		Assert.assertEquals(documento.getNombre(), documento2.getNombre());
		Assert.assertEquals(documento.getDescripcion(), documento2.getDescripcion());
		Assert.assertEquals(documento.getTipoMIME(), documento2.getTipoMIME());
		Assert.assertEquals(String.valueOf(documento.getFechaCreacion()), String.valueOf(documento2.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(documento.getFechaCaducidad()), String.valueOf(documento2.getFechaCaducidad()));
		Assert.assertEquals(documento.getCsv(), documento2.getCsv());
		Assert.assertEquals(String.valueOf(documento.getFechaCSV()), String.valueOf(documento2.getFechaCSV()));
		Assert.assertEquals(documento.isDisponible(), documento2.isDisponible());

		Assert.assertNotNull("El documento 1 no tiene aplicación asociada", documento.getAplicacion());
		Assert.assertNotNull("El documento 2 no tiene aplicación asociada", documento2.getAplicacion());

		Assert.assertEquals(documento.getAplicacion().getId(), documento2.getAplicacion().getId());
		Assert.assertEquals(documento.getAplicacion().getCodigo(), documento2.getAplicacion().getCodigo());
		Assert.assertEquals(documento.getAplicacion().getNombre(), documento2.getAplicacion().getNombre());
		Assert.assertEquals(documento.getAplicacion().getInfoConexion(), documento2.getAplicacion().getInfoConexion());
	}

	private DocumentoVO createDocumentoVO(String id) {

		DocumentoVO documento = new DocumentoVO();
		documento.setId(id);
		documento.setNombre("Documento_" + id + ".txt");
		documento.setDescripcion("Descripción del documento");
		documento.setTipoMIME("text/plain");
		documento.setFechaCreacion(new Date());
		documento.setFechaCaducidad(new Date());
		documento.setCsv("CSV_" + id);
		documento.setFechaCSV(new Date());
		documento.setDisponible(true);
		documento.setAplicacion(getAplicacionDao().get(ID_APLICACION_EXISTENTE));

		return documento;
	}
}
