package common.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import solicitudes.consultas.ConsultasConstants;

import common.db.IDBEntity;
import common.manager.impl.ArchidocManagerConnectionImpl;
import common.util.DBFactoryConstants;
import common.util.DateUtils;


@ContextConfiguration( {
	"/beans/datasource-beans-test.xml",
	"/beans/transaction-beans-test.xml"

})
public abstract class ArchidocBaseSQLServerTest extends ArchidocDBBaseTest{

	protected abstract IDBEntity getDAO();


	@Autowired
	public DataSource dataSourceSQLServer;

	private Connection conn;

	private IArchidocManager manager;

	protected Connection getConn() throws SQLException{
		if(conn == null){
			conn = dataSourceSQLServer.getConnection();
		}
		return dataSourceSQLServer.getConnection();
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
		Assert.assertEquals("La base de datos no es SQLServer",DBFactoryConstants.SQLSERVER_FACTORY, getManager().getDbFactoryClass());
	}


}
