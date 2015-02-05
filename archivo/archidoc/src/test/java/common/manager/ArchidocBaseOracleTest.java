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
public abstract class ArchidocBaseOracleTest extends ArchidocDBBaseTest{

	protected abstract IDBEntity getDAO();

	protected abstract String getDbFactoryClass();


	@Autowired
	public DataSource dataSourceOracle;

	private Connection conn;

	private IArchidocManager manager;

	protected Connection getConn() throws SQLException{
		if(conn == null){
			conn = dataSourceOracle.getConnection();
		}
		return dataSourceOracle.getConnection();
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
		Assert.assertEquals("La base de datos no es Oracle",getDbFactoryClass(), getManager().getDbFactoryClass());
	}
}
