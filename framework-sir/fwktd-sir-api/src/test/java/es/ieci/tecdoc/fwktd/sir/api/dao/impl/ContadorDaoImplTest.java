package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.dao.ContadorDao;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.ContadorVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class ContadorDaoImplTest extends AbstractDbUnitTransactionalJUnit4SpringContextTests {

	protected static final String CODIGO_ENTIDAD_REGISTRAL = "ER0000000000000000001";
	protected static final TipoContadorEnum TIPO_CONTADOR = TipoContadorEnum.IDENTIFICADOR_INTERCAMBIO;


	@Autowired
	private ContadorDao fwktd_sir_contadorDao;

	public ContadorDao getContadorDao() {
		return fwktd_sir_contadorDao;
	}

	@Test
	public void testDao() {
		Assert.assertNotNull(getContadorDao());

	}

	@Test
	public void testCount() {
		Assert.assertEquals(1, getContadorDao().count());
	}

	@Test
	public void testGet() {

		ContadorVO contador = getContadorDao().get(CODIGO_ENTIDAD_REGISTRAL, TIPO_CONTADOR);

		Assert.assertNotNull(contador);
		Assert.assertEquals(CODIGO_ENTIDAD_REGISTRAL, contador.getCodigoEntidadRegistral());
		Assert.assertEquals(TIPO_CONTADOR, contador.getTipoContador());
		Assert.assertEquals(50, contador.getContador());

		contador = getContadorDao().get(CODIGO_ENTIDAD_REGISTRAL, TipoContadorEnum.MENSAJE);
		Assert.assertNull(contador);
	}

	@Test
	public void testSave() {

		ContadorVO contador = new ContadorVO();
		contador.setCodigoEntidadRegistral(CODIGO_ENTIDAD_REGISTRAL);
		contador.setTipoContador(TipoContadorEnum.MENSAJE);
		contador.setContador(100);

		ContadorVO contadorCreado = (ContadorVO) getContadorDao().save(contador);

		Assert.assertNotNull("Contador creado", contadorCreado);
		Assert.assertEquals(contador.getCodigoEntidadRegistral(), contadorCreado.getCodigoEntidadRegistral());
		Assert.assertEquals(contador.getTipoContador(), contadorCreado.getTipoContador());
		Assert.assertEquals(contador.getContador(), contadorCreado.getContador());
	}

	@Test
	public void testUpdate() {

		ContadorVO contador = new ContadorVO();
		contador.setCodigoEntidadRegistral(CODIGO_ENTIDAD_REGISTRAL);
		contador.setTipoContador(TIPO_CONTADOR);
		contador.setContador(500);

		ContadorVO contadorModificado = getContadorDao().update(contador);

		Assert.assertNotNull("Contador modificado", contadorModificado);
		Assert.assertEquals(contador.getCodigoEntidadRegistral(), contadorModificado.getCodigoEntidadRegistral());
		Assert.assertEquals(contador.getTipoContador(), contadorModificado.getTipoContador());
		Assert.assertEquals(contador.getContador(), contadorModificado.getContador());
	}

	@Test
	public void testDelete() {
		int count = getContadorDao().count();
		getContadorDao().delete(CODIGO_ENTIDAD_REGISTRAL, TIPO_CONTADOR);
		Assert.assertEquals(count - 1, getContadorDao().count());
	}

	@Test
	public void testDeleteAll() {
		getContadorDao().deleteAll();
		Assert.assertEquals(0, getContadorDao().count());
	}

}
