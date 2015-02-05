package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.ConfiguracionVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class ConfiguracionManagerImplTest extends BaseManagerTest {

	protected static final String ID_EXISTENTE = "10000001";
	protected static final String ID_NO_EXISTENTE = "99999999";

	@Autowired
	private ConfiguracionManager fwktd_sir_configuracionManager;

	public ConfiguracionManager getConfiguracionManager() {
		return fwktd_sir_configuracionManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseManager<Entity, String> getManager() {
		return (BaseManager) getConfiguracionManager();
	}

	public String getIdExistente() {
		return ID_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_NO_EXISTENTE;
	}

	@Test
	public void testGet() {

		ConfiguracionVO configuracion = getConfiguracionManager().get(getIdExistente());

		Assert.assertNotNull(configuracion);
		Assert.assertEquals(getIdExistente(), configuracion.getId());
		Assert.assertEquals("parametro-pruebas", configuracion.getNombre());
		Assert.assertEquals("Descripción del parámetro de pruebas", configuracion.getDescripcion());
		Assert.assertEquals("Valor del parámetro de pruebas", configuracion.getValor());
	}

	@Test
	public void testGetValorConfiguracion() {

		Assert.assertEquals("Valor del parámetro de pruebas", getConfiguracionManager().getValorConfiguracion("parametro-pruebas"));
		Assert.assertNull(getConfiguracionManager().getValorConfiguracion("no-existe"));
	}

	@Test
	public void testSave() {

		ConfiguracionVO configuracion = new ConfiguracionVO();
		configuracion.setNombre("parametro-pruebas-2");
		configuracion.setDescripcion("Descripción del parámetro de pruebas 2");
		configuracion.setValor("Valor del parámetro de pruebas 2");

		ConfiguracionVO configuracionCreada = (ConfiguracionVO) getConfiguracionManager().save(configuracion);

		Assert.assertNotNull("Configuración creada", configuracionCreada);
		Assert.assertNotNull(configuracionCreada.getId());
		Assert.assertEquals(configuracion.getNombre(), configuracionCreada.getNombre());
		Assert.assertEquals(configuracion.getDescripcion(), configuracionCreada.getDescripcion());
		Assert.assertEquals(configuracion.getValor(), configuracionCreada.getValor());
	}

	@Test
	public void testUpdate() {

		ConfiguracionVO configuracion = getConfiguracionManager().get(getIdExistente());
		configuracion.setDescripcion("Descripción del parámetro de pruebas modificada");

		ConfiguracionVO configuracionModificada = getConfiguracionManager().update(configuracion);

		Assert.assertNotNull("Configuración modificada", configuracionModificada);
		Assert.assertEquals(configuracion.getId(), configuracionModificada.getId());
		Assert.assertEquals(configuracion.getNombre(), configuracionModificada.getNombre());
		Assert.assertEquals(configuracion.getDescripcion(), configuracionModificada.getDescripcion());
		Assert.assertEquals(configuracion.getValor(), configuracionModificada.getValor());
	}

}
