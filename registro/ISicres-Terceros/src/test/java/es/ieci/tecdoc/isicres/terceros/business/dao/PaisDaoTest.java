package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
public class PaisDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void testGetAll() {
		List<PaisVO> all = getPaisDao().getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(240, all.size());
	}

	@Test
	public void testGetPais() {
		PaisVO paisVO = getPaisDao().get("180");

		Assert.assertNotNull(paisVO);
		Assert.assertEquals("180", paisVO.getId());
		Assert.assertEquals("GB", paisVO.getCodigo());
		Assert.assertEquals("Reino Unido", paisVO.getNombre());
	}

	@Test
	public void testFindByCodigo() {
		PaisVO pais = getPaisDao().findByCodigo("FO");

		Assert.assertNotNull(pais);
		Assert.assertEquals("78", pais.getId());
		Assert.assertEquals("FO", pais.getCodigo());
		Assert.assertEquals("Islas Feroe", pais.getNombre());
	}

	@Test
	public void testFindByNombre() {
		PaisVO pais = getPaisDao().findByNombre("Austria");

		Assert.assertNotNull(pais);
		Assert.assertEquals("17", pais.getId());
		Assert.assertEquals("AT", pais.getCodigo());
		Assert.assertEquals("Austria", pais.getNombre());
	}

	public PaisDao getPaisDao() {
		return paisDao;
	}

	public void setPaisDao(PaisDao paisDao) {
		this.paisDao = paisDao;
	}

	@Autowired
	protected PaisDao paisDao;
}
