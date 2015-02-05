package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.ContadorManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class ContadorManagerImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	protected static final String CODIGO_ENTIDAD_REGISTRAL = "ER0000000000000000001";
	protected static final String CODIGO_ENTIDAD_REGISTRAL_2 = "ER9999999999999999999";
	protected static final TipoContadorEnum TIPO_CONTADOR = TipoContadorEnum.IDENTIFICADOR_INTERCAMBIO;

	@Autowired
	private ContadorManager fwktd_sir_contadorManager;

	public ContadorManager getContadorManager() {
		return fwktd_sir_contadorManager;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getContadorManager());
	}

	@Test
	public void testUpdateContador() {

		String contador = getContadorManager().updateContador(
				CODIGO_ENTIDAD_REGISTRAL, TIPO_CONTADOR);
		Assert.assertNotNull(contador);
		Assert.assertEquals("00000051", contador);

		contador = getContadorManager().updateContador(
				CODIGO_ENTIDAD_REGISTRAL_2, TIPO_CONTADOR);
		Assert.assertNotNull(contador);
		Assert.assertEquals("00000000", contador);
	}

}
