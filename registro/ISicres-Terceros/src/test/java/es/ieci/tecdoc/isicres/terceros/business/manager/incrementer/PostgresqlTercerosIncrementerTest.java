package es.ieci.tecdoc.isicres.terceros.business.manager.incrementer;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/incrementer-beans.xml" })
public class PostgresqlTercerosIncrementerTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getNextValue() {
		Assert.assertEquals(4, getTerceroIncrementer().nextIntValue());
		Assert.assertEquals(5, getTerceroIncrementer().nextIntValue());
	}

	public TercerosIncrementer getTerceroIncrementer() {
		return terceroIncrementer;
	}

	public void setTerceroIncrementer(
			TercerosIncrementer terceroIncrementer) {
		this.terceroIncrementer = terceroIncrementer;
	}

	@Autowired
	protected TercerosIncrementer terceroIncrementer;
}
