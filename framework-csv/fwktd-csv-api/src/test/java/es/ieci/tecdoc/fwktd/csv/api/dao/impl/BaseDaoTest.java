package es.ieci.tecdoc.fwktd.csv.api.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;

public abstract class BaseDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	public abstract BaseDao<Entity, String> getDao();
	public abstract String getIdExistente();
	public abstract String getIdNoExistente();
	public abstract String getIdBorrar();

	@Test
	public void testDao() {
		Assert.assertNotNull(getDao());
	}

	@Test
	public void testCount() {
		int count = getDao().count();
		Assert.assertTrue(count > 0);
	}

	@Test
	public void testGetAll() {

		List<Entity> elementos = getDao().getAll();

		Assert.assertNotNull(elementos);
		Assert.assertTrue(elementos.size() > 0);
	}

	@Test
	public void testGetAllDistinct() {

		List<Entity> elementos = getDao().getAllDistinct();

		Assert.assertNotNull(elementos);
		Assert.assertTrue(elementos.size() > 0);
	}

	@Test
	public void testExists() {
		Assert.assertTrue(getDao().exists(getIdExistente()));
		Assert.assertFalse(getDao().exists(getIdNoExistente()));
	}

	@Test
	public void testDelete() {
		int count = getDao().count();
		getDao().delete(getIdBorrar());
		Assert.assertEquals(getDao().count(), count-1);
	}

	@Test
	public void testDeleteAll() {
		getDao().deleteAll();
		Assert.assertEquals(getDao().count(), 0);
	}

}
