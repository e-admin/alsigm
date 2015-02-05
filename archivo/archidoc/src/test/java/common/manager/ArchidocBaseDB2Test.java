package common.manager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import common.db.IDBEntity;
import common.manager.impl.ArchidocManagerConnectionImpl;
import common.util.DBFactoryConstants;


@ContextConfiguration( {
	"/beans/datasource-beans-test.xml",
	"/beans/transaction-beans-test.xml"

})
public abstract class ArchidocBaseDB2Test extends ArchidocDBBaseTest
	/*extends AbstractDbUnitTransactionalJUnit4SpringContextTests*/{

	protected abstract IDBEntity getDAO();


	@Autowired
	public DataSource dataSourceDB2;

	private Connection conn;

	private IArchidocManager manager;

	protected Connection getConn() throws SQLException{
		if(conn == null){
			conn = dataSourceDB2.getConnection();
		}
		return dataSourceDB2.getConnection();
	}

	protected IArchidocManager getManager(){
		if(manager == null){
			try {
				manager = new ArchidocManagerConnectionImpl(getConn());
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return manager;
	}

	@Test
	public void dbFactortyTest(){
		Assert.assertEquals("La base de datos no es DB2",DBFactoryConstants.DB2_FACTORY, getManager().getDbFactoryClass());
	}

}
