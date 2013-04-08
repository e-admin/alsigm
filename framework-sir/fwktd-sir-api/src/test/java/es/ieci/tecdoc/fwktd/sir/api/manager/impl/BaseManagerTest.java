package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

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

	@Test
	public void testManager() {
		Assert.assertNotNull(getManager());
	}

	@Test
	public void testCount() {

		int numAnexos = getManager().count();

		Assert.assertTrue(numAnexos > 0);
	}

	@Test
	public void testGetAll() {

		List<Entity> anexos = getManager().getAll();

		Assert.assertNotNull(anexos);
		Assert.assertTrue(anexos.size() > 0);
	}

	@Test
	public void testGetAllDistinct() {

		List<Entity> anexos = getManager().getAllDistinct();

		Assert.assertNotNull(anexos);
		Assert.assertTrue(anexos.size() > 0);
	}

	@Test
	public void testExists() {
		Assert.assertTrue(getManager().exists(getIdExistente()));
		Assert.assertFalse(getManager().exists(getIdNoExistente()));
	}

	@Test
	public void testDelete() {
		int count = getManager().count();
		getManager().delete(getIdExistente());
		Assert.assertEquals(getManager().count(), count-1);
	}

}
