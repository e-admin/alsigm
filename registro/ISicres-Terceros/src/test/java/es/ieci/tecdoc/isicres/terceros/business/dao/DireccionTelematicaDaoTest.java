package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;

/**
 *
 * @author IECISa
 *
 */
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
@DatasetLocation("data/dataset.xml")
public class DireccionTelematicaDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void testGetAllDireccionesTelematicas() {
		List<DireccionTelematicaVO> all = getDireccionTelematicaDao().getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(1, all.size());
	}

	public DireccionTelematicaDao getDireccionTelematicaDao() {
		return direccionTelematicaDao;
	}

	public void setDireccionTelematicaDao(
			DireccionTelematicaDao direccionTelematicaDao) {
		this.direccionTelematicaDao = direccionTelematicaDao;
	}

	// Members
	@Autowired
	protected DireccionTelematicaDao direccionTelematicaDao;
}
