/*
 * 
 */
package es.ieci.tecdoc.fwktd.sampleapp.business.dao;

import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sampleapp.business.vo.Person;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration( { "/beans/business/transaction-beans.xml",
		"/beans/business/datasource-beans.xml", "/beans/business/dao-beans.xml" })
/**
 * 		
 */
public class PersonDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	public IbatisGenericDaoImpl<Person, String> getPersonDao() {
		return personDao;
	}

	public void setPersonDao(IbatisGenericDaoImpl<Person, String> dao) {
		this.personDao = dao;
	}

	@Autowired
	protected IbatisGenericDaoImpl<Person, String> personDao;

	@Autowired
	protected DataSource dataSource;

	@Test
	public void testGetPerson() {
		Person person = getPersonDao().get("1");

		Assert.assertNotNull(person);
		Assert.assertEquals("Pablo Muniz Garcia", person.getName());
		Assert.assertEquals("pablo_muniz@ieci.es", person.getEmail());

		Assert.assertNotNull(person.getAddresses());
		Assert.assertEquals(2, person.getAddresses().size());
	}

	@Test
	public void testGetPeople() throws Exception {
		List<Person> people = getPersonDao().getAll();

		Assert.assertNotNull(people);
		Assert.assertEquals(2, people.size());
	}

	@Test
	public void testInsertPerson() throws Exception {
		Person person = new Person();
		person.setName("nuevo");
		person.setEmail("nuevo@ieci.es");

		getPersonDao().save(person);

		Assert.assertEquals(3, getPersonDao().getAll().size());
	}

	@Test
	public void testUpdatePerson() {
		Person person = getPersonDao().get("1");

		person.setEmail("otro@ieci.es");
		getPersonDao().update(person);

		Person updatedPerson = getPersonDao().get("1");

		Assert.assertEquals(person.getName(), updatedPerson.getName());
		Assert.assertEquals(person.getEmail(), updatedPerson.getEmail());
		Assert.assertEquals(person.getAddresses().size(), updatedPerson
				.getAddresses().size());
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void testDeletePerson() {
		getPersonDao().delete("2");
	}

	@Test
	public void testCounts() {
		Assert.assertEquals(2, getPersonDao().count());
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void testDeletePeople() {
		getPersonDao().deleteAll();
	}

	@Test
	public void testExists() {
		Assert.assertTrue(getPersonDao().exists("1"));
	}

	@Test
	public void testNotExists() {
		Assert.assertFalse(getPersonDao().exists("3"));
	}
}
