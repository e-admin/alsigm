package es.ieci.tecdoc.fwktd.csv.api.manager.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;

public abstract class BaseManagerTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	public abstract BaseManager<Entity, String> getManager();
	public abstract String getIdExistente();
	public abstract String getIdNoExistente();
	public abstract String getIdBorrar();

	@Test
	public void testManager() {
		Assert.assertNotNull(getManager());
	}

	@Test
	public void testCount() {

		int count = getManager().count();

		Assert.assertTrue(count > 0);
	}

	@Test
	public void testGetAll() {

		List<Entity> elementos = getManager().getAll();

		Assert.assertNotNull(elementos);
		Assert.assertTrue(elementos.size() > 0);
	}

	@Test
	public void testGetAllDistinct() {

		List<Entity> elementos = getManager().getAllDistinct();

		Assert.assertNotNull(elementos);
		Assert.assertTrue(elementos.size() > 0);
	}

	@Test
	public void testExists() {
		Assert.assertTrue(getManager().exists(getIdExistente()));
		Assert.assertFalse(getManager().exists(getIdNoExistente()));
	}

	@Test
	public void testDelete() {
		int count = getManager().count();
		getManager().delete(getIdBorrar());
		Assert.assertEquals(getManager().count(), count-1);
	}

	@Test
	public void testDeleteAll() {
		getManager().deleteAll(getManager().getAll());
		Assert.assertEquals(getManager().count(), 0);
	}

}
