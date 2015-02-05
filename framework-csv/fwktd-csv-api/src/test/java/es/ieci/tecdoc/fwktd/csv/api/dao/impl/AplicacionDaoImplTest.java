package es.ieci.tecdoc.fwktd.csv.api.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.csv.api.dao.AplicacionDao;
import es.ieci.tecdoc.fwktd.csv.api.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class AplicacionDaoImplTest extends BaseDaoTest {

	protected static final String ID_APLICACION_EXISTENTE = "20000001";
	protected static final String ID_APLICACION_BORRAR = "20000002";
	protected static final String ID_APLICACION_NO_EXISTENTE = "99999999";

	protected static final String CODIGO_APLICACION_EXISTENTE = "APP1";
	protected static final String CODIGO_APLICACION_BORRAR = "APP2";

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
		return (BaseDao) getAplicacionDao();
	}

	public String getIdExistente() {
		return ID_APLICACION_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_APLICACION_NO_EXISTENTE;
	}

	public String getIdBorrar() {
		return ID_APLICACION_BORRAR;
	}

	@Test
	public void testGet() {
		AplicacionVO aplicacion = getAplicacionDao().get(getIdExistente());
		assertAplicacion(aplicacion);
	}

	@Test
	public void testGetAplicacionByCodigo() {
		AplicacionVO aplicacion = getAplicacionDao().getAplicacionByCodigo(CODIGO_APLICACION_EXISTENTE);
		assertAplicacion(aplicacion);
	}

	@Test
	public void testSave() {

		AplicacionVO aplicacion = createAplicacionVO(getIdNoExistente());

		AplicacionVO aplicacionCreada = (AplicacionVO) getAplicacionDao().save(aplicacion);
		assertEquals(aplicacion, aplicacionCreada);
	}

	@Test
	public void testUpdate() {

		AplicacionVO aplicacion = getAplicacionDao().get(getIdExistente());
		aplicacion.setNombre(aplicacion.getNombre() + " MODIFICADO");

		AplicacionVO aplicacionModificada = getAplicacionDao().update(aplicacion);
		assertEquals(aplicacion, aplicacionModificada);
	}

	@Test
	public void testDeleteAplicacionByCodigo() {
		int count = getDao().count();
		getAplicacionDao().deleteAplicacionByCodigo(CODIGO_APLICACION_BORRAR);
		Assert.assertEquals(getDao().count(), count-1);
	}

	@Test
	public void testDeleteAll() {

		// Eliminar antes todos los documentos
		getDocumentoDao().deleteAll();
		Assert.assertEquals(getDocumentoDao().count(), 0);

		// Eliminar las aplicaciones
		getAplicacionDao().deleteAll();
		Assert.assertEquals(getDao().count(), 0);
	}


	private void assertAplicacion(AplicacionVO aplicacion) {

		Assert.assertNotNull("No se ha obtenido la aplicacion", aplicacion);
		Assert.assertEquals(getIdExistente(), aplicacion.getId());
		Assert.assertEquals("APP1", aplicacion.getCodigo());
		Assert.assertEquals("Aplicación 1", aplicacion.getNombre());
		Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>", aplicacion.getInfoConexion());
	}

	private void assertEquals(AplicacionVO aplicacion, AplicacionVO aplicacion2) {

		Assert.assertNotNull("La aplicación 1 es nula", aplicacion);
		Assert.assertNotNull("La aplicación 2 es nula", aplicacion2);

		Assert.assertEquals(aplicacion.getId(), aplicacion2.getId());
		Assert.assertEquals(aplicacion.getCodigo(), aplicacion2.getCodigo());
		Assert.assertEquals(aplicacion.getNombre(), aplicacion2.getNombre());
		Assert.assertEquals(aplicacion.getInfoConexion(), aplicacion2.getInfoConexion());
	}

	private AplicacionVO createAplicacionVO(String id) {

		AplicacionVO aplicacion = new AplicacionVO();
		aplicacion.setId(id);
		aplicacion.setCodigo("APP" + id);
		aplicacion.setNombre("Aplicación " + id);
		aplicacion.setInfoConexion("<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		return aplicacion;
	}
}
