/*
 * 
 */
package es.ieci.tecdoc.fwktd.sampleapp.business.manager;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.core.model.NamedEntity;
import es.ieci.tecdoc.fwktd.sampleapp.business.vo.Person;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

/**
 * 
 * @author IECISA
 * 
 */
public class PersonManagerTests extends AbstractJUnit4SpringContextTests {

	protected BaseManager<Person, java.lang.String> manager;
	protected BaseDao<Person, String> dao;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		dao = (BaseDao<Person, String>) EasyMock.createMock(BaseDao.class);
		manager = new BaseManagerImpl<Person, String>(dao);
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void testGetPerson() {
		Person mockPerson = new Person();
		mockPerson.setId("1");
		mockPerson.setName("uno");
		mockPerson.setEmail("uno@ieci.es");

		EasyMock.expect(dao.get("1")).andReturn(mockPerson);
		EasyMock.expect(dao.get("2")).andThrow(
				new ObjectRetrievalFailureException(NamedEntity.class, "2"));
		EasyMock.replay(dao);

		Person person = manager.get("1");
		Assert.assertEquals(mockPerson.getId(), person.getId());
		Assert.assertEquals(mockPerson.getName(), person.getName());
		Assert.assertEquals(mockPerson.getEmail(), person.getEmail());

		try {
			manager.get("2");
		} finally {
			EasyMock.verify(dao);
		}
	}

}