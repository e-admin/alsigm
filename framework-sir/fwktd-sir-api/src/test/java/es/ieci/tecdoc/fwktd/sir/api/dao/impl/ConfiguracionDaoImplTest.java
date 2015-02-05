package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.api.dao.ConfiguracionDao;
import es.ieci.tecdoc.fwktd.sir.api.vo.ConfiguracionVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class ConfiguracionDaoImplTest extends BaseDaoTest {

	protected static final String ID_EXISTENTE = "10000001";
	protected static final String ID_NO_EXISTENTE = "99999999";

	@Autowired
	private ConfiguracionDao fwktd_sir_configuracionDao;

	public ConfiguracionDao getConfiguracionDao() {
		return fwktd_sir_configuracionDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDao<Entity, String> getDao() {
		return (BaseDao) getConfiguracionDao();
	}

	public String getIdExistente() {
		return ID_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_NO_EXISTENTE;
	}

	@Test
	public void testGet() {

		ConfiguracionVO configuracion = getConfiguracionDao().get(getIdExistente());

		Assert.assertNotNull(configuracion);
		Assert.assertEquals(getIdExistente(), configuracion.getId());
		Assert.assertEquals("parametro-pruebas", configuracion.getNombre());
		Assert.assertEquals("Descripción del parámetro de pruebas", configuracion.getDescripcion());
		Assert.assertEquals("Valor del parámetro de pruebas", configuracion.getValor());
	}

	@Test
	public void testGetValorConfiguracion() {

		Assert.assertEquals("Valor del parámetro de pruebas", getConfiguracionDao().getValorConfiguracion("parametro-pruebas"));
		Assert.assertNull(getConfiguracionDao().getValorConfiguracion("no-existe"));
	}

	@Test
	public void testSave() {

		ConfiguracionVO configuracion = new ConfiguracionVO();
		configuracion.setId(getIdNoExistente());
		configuracion.setNombre("parametro-pruebas-2");
		configuracion.setDescripcion("Descripción del parámetro de pruebas 2");
		configuracion.setValor("Valor del parámetro de pruebas 2");

		ConfiguracionVO configuracionCreada = (ConfiguracionVO) getConfiguracionDao().save(configuracion);

		Assert.assertNotNull("Configuración creada", configuracionCreada);
		Assert.assertEquals(configuracion.getId(), configuracionCreada.getId());
		Assert.assertEquals(configuracion.getNombre(), configuracionCreada.getNombre());
		Assert.assertEquals(configuracion.getDescripcion(), configuracionCreada.getDescripcion());
		Assert.assertEquals(configuracion.getValor(), configuracionCreada.getValor());
	}

	@Test
	public void testUpdate() {

		ConfiguracionVO configuracion = getConfiguracionDao().get(getIdExistente());
		configuracion.setDescripcion("Descripción del parámetro de pruebas modificada");

		ConfiguracionVO configuracionModificada = getConfiguracionDao().update(configuracion);

		Assert.assertNotNull("Configuración modificada", configuracionModificada);
		Assert.assertEquals(configuracion.getId(), configuracionModificada.getId());
		Assert.assertEquals(configuracion.getNombre(), configuracionModificada.getNombre());
		Assert.assertEquals(configuracion.getDescripcion(), configuracionModificada.getDescripcion());
		Assert.assertEquals(configuracion.getValor(), configuracionModificada.getValor());
	}

}
