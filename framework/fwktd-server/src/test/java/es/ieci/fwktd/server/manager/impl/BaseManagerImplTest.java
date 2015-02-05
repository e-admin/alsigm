/*
 * 
 */
package es.ieci.fwktd.server.manager.impl;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

/**
 * 
 * @author X71636MU
 * 
 */
@RunWith(JUnit4.class)
public class BaseManagerImplTest {

	protected BaseManager<Entity, String> manager;

	protected DataFieldMaxValueIncrementer incrementer;

	protected BaseDao<Entity, String> dao;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		dao = EasyMock.createMock(BaseDao.class);

		incrementer = EasyMock.createMock(DataFieldMaxValueIncrementer.class);

		manager = new BaseManagerImpl<Entity, String>(dao);
		((BaseManagerImpl<Entity, String>) manager).setIncrementer(incrementer);
	}

	@Test
	public void createAnEntity() {

		Entity entity = new Entity();

		EasyMock.expect(incrementer.nextStringValue()).andReturn("1");
		Entity newEntity = new Entity();
		newEntity.setId("1");
		EasyMock.expect(dao.save(entity)).andReturn(newEntity).atLeastOnce();

		EasyMock.replay(incrementer, dao);

		Assert.assertEquals(newEntity, manager.save(entity));

		EasyMock.verify(incrementer, dao);

	}

	@Test(expected = IllegalArgumentException.class)
	public void createAnEntityWithoutIncrementer() {
		((BaseManagerImpl<Entity, String>) manager).setIncrementer(null);

		manager.save(new Entity());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAnEntityWithoutDao() {
		((BaseManagerImpl<Entity, String>) manager).setDao(null);

		EasyMock.expect(incrementer.nextStringValue()).andReturn("1");

		EasyMock.replay(incrementer);

		try {
			manager.save(new Entity());
		} finally {
			EasyMock.verify(incrementer);
		}
	}

}
