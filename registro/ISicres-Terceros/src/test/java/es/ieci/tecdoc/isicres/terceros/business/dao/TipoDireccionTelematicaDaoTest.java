package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;

/**
 *
 * @author IECISA
 *
 */
@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
public class TipoDireccionTelematicaDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void testGetAllTipos() {
		List<TipoDireccionTelematicaVO> all = getTipoDireccionTelematicaDao()
				.getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(5, all.size());
	}

	@Test
	public void testGetTipoById() {
		TipoDireccionTelematicaVO tipoDireccionTelematicaVO = getTipoDireccionTelematicaDao()
				.get("1");

		Assert.assertNotNull(tipoDireccionTelematicaVO);
		Assert.assertEquals("1", tipoDireccionTelematicaVO.getId());
		Assert.assertEquals("TF", tipoDireccionTelematicaVO.getCodigo());
		Assert.assertEquals("Tel\u00E9fono (fijo)",
				tipoDireccionTelematicaVO.getDescripcion());
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void testUnknownTipo() {
		getTipoDireccionTelematicaDao().get("6");
	}

	public TipoDireccionTelematicaDao getTipoDireccionTelematicaDao() {
		return tipoDireccionTelematicaDao;
	}

	public void setTipoDireccionTelematicaDao(
			TipoDireccionTelematicaDao tipoDireccionTelematicaDao) {
		this.tipoDireccionTelematicaDao = tipoDireccionTelematicaDao;
	}

	@Autowired
	protected TipoDireccionTelematicaDao tipoDireccionTelematicaDao;
}
