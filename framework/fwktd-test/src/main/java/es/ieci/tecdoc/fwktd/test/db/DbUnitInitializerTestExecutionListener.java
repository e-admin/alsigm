/**
 * 
 */
package es.ieci.tecdoc.fwktd.test.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.util.StringUtils;

import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

/**
 * Especializaci�n de <code>TestExecutionListener</code> que permite cargar un
 * dataset de DbUnit antes de la ejecuci�n de cada uno de los m�todos de test de
 * una suite.
 * 
 * Para una correcta ejecuci�n en un entorno transaccional debe definirse un
 * bean de nombre <i>datasourceDbUnit</i>.
 * 
 * @author IECISA
 * @see TestExecutionListener
 * 
 */
public class DbUnitInitializerTestExecutionListener extends
		TransactionalTestExecutionListener {

	/**
	 * 
	 */
	public void beforeTestMethod(TestContext testContext) throws Exception {
		super.beforeTestMethod(testContext);

		DataSource dataSource = (DataSource) testContext
				.getApplicationContext().getBean("datasourceDbUnit");

		Resource dataSetResource = getDataSetLocation(testContext);
		if (dataSetResource == null) {
			dataSetResource = getDefaultDataSetLocation(testContext);
		}

		if (dataSetResource != null) {
			FlatXmlDataSetBuilder dsBuilder = new FlatXmlDataSetBuilder();
			IDataSet dataSet = dsBuilder
					.build(dataSetResource.getInputStream());

			dataSet = decorate(dataSet);

			IDatabaseConnection connection = getConnection(testContext,
					dataSource);
			

			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		}
	}

	/**
	 * 
	 * @param testContext
	 * @param dataSource
	 * @return
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 */
	private IDatabaseConnection getConnection(TestContext testContext,
			DataSource dataSource) throws DatabaseUnitException, SQLException {

		IDatabaseConnection connection = new DatabaseConnection(dataSource
				.getConnection());
		

		configureCustomConnection(connection);

		DatasetLocation dsLocation = testContext.getTestInstance().getClass()
				.getAnnotation(DatasetLocation.class);
		if (null != dsLocation) {
			connection.getConfig().setProperty(
					"http://www.dbunit.org/features/qualifiedTableNames",
					dsLocation.qualifyTables());
		}

		return connection;
	}
	
	/**
	 * Método para configurar particularidades de los tipos de datos de diferentes bases de datos.
	 * @param connection
	 * @throws SQLException
	 */
	void configureCustomConnection(IDatabaseConnection connection) throws SQLException{
		String metaDataConnection=connection.getConnection().getMetaData().toString();
		boolean isOracle=org.apache.commons.lang.StringUtils.containsIgnoreCase(metaDataConnection, "oracle");
		
		if (isOracle){
			connection.getConfig().setProperty(
					DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new OracleDataTypeFactory());
		}
		//TODO otras posibles factorias de datos que todavía no hemos detectado errores
		/* Oracle     –   org.dbunit.ext.oracle.Oracle10DataTypeFactory, org.dbunit.ext.oracle.OracleDataTypeFactory
		 MySql     -   org.dbunit.ext.mysql.MySqlDataTypeFactory
		 MsSql      –   org.dbunit.ext.mssql.MsSqlDataTypeFactory
		 Hsqldb   -    org.dbunit.ext.hsqldb.HsqldbDataTypeFactory
		 H2            –    org.dbunit.ext.h2.H2DataTypeFactory
		 Db2         -     org.dbunit.ext.db2.Db2DataTypeFactory
		 Others    -     org.dbunit.dataset.datatype.DefaultDataTypeFacto
		 */
		
	}
	

	/**
	 * 
	 * @param dataSet
	 * @return
	 */
	protected IDataSet decorate(IDataSet dataSet) {
		IDataSet rds = new ReplacementDataSet(dataSet);
		((ReplacementDataSet) rds).addReplacementObject("[NULL]", null);

		return rds;
	}

	/**
	 * 
	 * @param testContext
	 * @return
	 */
	private Resource getDefaultDataSetLocation(TestContext testContext) {
		String dataSetLocation = testContext.getTestInstance().getClass()
				.getName();
		dataSetLocation = StringUtils.replace(dataSetLocation, ".", "/");
		dataSetLocation = "/" + dataSetLocation + "-dataset.xml";
		if (getClass().getResourceAsStream(dataSetLocation) != null) {
			return new ClassPathResource(dataSetLocation);
		}
		return null;
	}

	/**
	 * 
	 * @param testContext
	 * @return
	 */
	private Resource getDataSetLocation(TestContext testContext) {
		DatasetLocation dsLocation = testContext.getTestInstance().getClass()
				.getAnnotation(DatasetLocation.class);
		if (dsLocation != null) {
			return new ClassPathResource(dsLocation.value());
		}
		return null;
	}
}
