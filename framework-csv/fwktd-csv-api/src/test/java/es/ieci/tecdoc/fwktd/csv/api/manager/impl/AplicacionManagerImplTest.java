package es.ieci.tecdoc.fwktd.csv.api.manager.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager;
import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class AplicacionManagerImplTest extends BaseManagerTest {

	protected static final String ID_APLICACION_EXISTENTE = "20000001";
	protected static final String ID_APLICACION_BORRAR = "20000002";
	protected static final String ID_APLICACION_NO_EXISTENTE = "99999999";

	protected static final String CODIGO_APLICACION_EXISTENTE = "APP1";
	protected static final String CODIGO_APLICACION_BORRAR = "APP2";

	@Autowired
	private AplicacionManager fwktd_sir_aplicacionManager;

	public AplicacionManager getAplicacionManager() {
		return fwktd_sir_aplicacionManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseManager<Entity, String> getManager() {
		return (BaseManager) getAplicacionManager();
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
		AplicacionVO aplicacion = getAplicacionManager().get(getIdExistente());
		assertAplicacion(aplicacion);
	}

	@Test
	public void testGetAplicacionByCodigo() {
		AplicacionVO aplicacion = getAplicacionManager().getAplicacionByCodigo(CODIGO_APLICACION_EXISTENTE);
		assertAplicacion(aplicacion);
	}

	@Test
	public void testSave() {

		AplicacionVO aplicacion = createAplicacionVO();

		AplicacionVO aplicacionCreada = (AplicacionVO) getAplicacionManager().save(aplicacion);
		assertEquals(aplicacion, aplicacionCreada);
	}

	@Test
	public void testUpdate() {

		AplicacionVO aplicacion = getAplicacionManager().get(getIdExistente());
		aplicacion.setNombre(aplicacion.getNombre() + " MODIFICADO");

		AplicacionVO aplicacionModificada = getAplicacionManager().update(aplicacion);
		assertEquals(aplicacion, aplicacionModificada);
	}

	@Test
	public void testDeleteAplicacionByCodigo() {
		int count = getAplicacionManager().count();
		getAplicacionManager().deleteAplicacionByCodigo(CODIGO_APLICACION_BORRAR);
		Assert.assertEquals(getAplicacionManager().count(), count-1);
	}

	@Test
	public void testDeleteAll() {

		int count = getAplicacionManager().count();
		AplicacionVO aplicacion = getAplicacionManager().get(ID_APLICACION_BORRAR);
		getAplicacionManager().deleteAll(Arrays.asList(aplicacion));
		Assert.assertEquals(getAplicacionManager().count(), count-1);
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

	private AplicacionVO createAplicacionVO() {

		AplicacionVO aplicacion = new AplicacionVO();
		aplicacion.setCodigo("APPX");
		aplicacion.setNombre("Aplicación X");
		aplicacion.setInfoConexion("");

		return aplicacion;
	}

}
