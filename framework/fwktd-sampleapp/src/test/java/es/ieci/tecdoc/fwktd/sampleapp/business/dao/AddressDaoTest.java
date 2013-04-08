package es.ieci.tecdoc.fwktd.sampleapp.business.dao;

import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sampleapp.business.vo.Address;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration( { "/beans/business/transaction-beans.xml",
		"/beans/business/datasource-beans.xml", "/beans/business/dao-beans.xml" })
public class AddressDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	public IbatisGenericDaoImpl<Address, String> getAddressDao() {
		return addressDao;
	}

	public void setPersonDao(IbatisGenericDaoImpl<Address, String> dao) {
		this.addressDao = dao;
	}

	@Autowired
	protected IbatisGenericDaoImpl<Address, String> addressDao;

	@Autowired
	protected DataSource dataSource;

	@Test
	public void testGetAddress() {
		Address address = getAddressDao().get("1");

		Assert.assertNotNull(address);
		Assert.assertEquals("Plaza de la Iglesia, s/n", address.getStreet());
	}

	@Test
	public void testGetAddresses() throws Exception {
		List<Address> addresses = getAddressDao().getAll();

		Assert.assertNotNull(addresses);
		Assert.assertEquals(3, addresses.size());
	}

	@Test
	public void testInsertAddress() throws Exception {
		Address address = new Address();
		address.setStreet("Nueva calle, 5");

		getAddressDao().save(address);

		Assert.assertEquals(4, getAddressDao().getAll().size());
	}

	@Test
	public void testUpdateAddress() {
		Address address = getAddressDao().get("1");

		address.setStreet("Plaza de la lonja, 1");
		getAddressDao().update(address);

		Address updatedAddress = getAddressDao().get("1");

		Assert.assertEquals(address.getStreet(), updatedAddress.getStreet());
	}

	@Test
	public void testDeleteAddress() {
		getAddressDao().delete("2");

		Assert.assertEquals(2, getAddressDao().count());
	}

	@Test
	public void testCounts() {
		Assert.assertEquals(3, getAddressDao().count());
	}

	@Test
	public void testDeleteAddresses() {
		getAddressDao().deleteAll();

		Assert.assertEquals(0, getAddressDao().getAll().size());
	}

	@Test
	public void testExists() {
		Assert.assertTrue(getAddressDao().exists("1"));
	}

	@Test
	public void testNotExists() {
		Assert.assertFalse(getAddressDao().exists("30"));
	}

}
