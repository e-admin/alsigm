package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;

/**
 *
 * @author IECISA
 *
 */
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
@DatasetLocation("data/dataset.xml")
public class DireccionFisicaDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void testGetAllDireccionesFisicas() {
		List<DireccionFisicaVO> all = getDireccionFisicaDao().getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(2, all.size());
	}

	public DireccionFisicaDao getDireccionFisicaDao() {
		return direccionFisicaDao;
	}

	public void setDireccionFisicaDao(DireccionFisicaDao direccionFisicaDao) {
		this.direccionFisicaDao = direccionFisicaDao;
	}

	// Members
	@Autowired
	protected DireccionFisicaDao direccionFisicaDao;
}
