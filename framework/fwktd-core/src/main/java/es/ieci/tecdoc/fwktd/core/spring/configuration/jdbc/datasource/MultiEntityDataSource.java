package es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.AbstractDataSource;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

/**
 * Ibatis:
 * 
 * 
 * 
 * 
 * 
 * <!-- sqlMapclient --> <bean id="sqlMapClient"
 * class="org.springframework.orm.ibatis.SqlMapClientFactoryBean"> <property
 * name="configLocation" value="classpath:/sqlMapConfig.xml"/> <property
 * name="dataSource" ref="multiEntityDataSource"/> </bean>
 * 
 * <!-- datasource <!-- org.springframework.jndi.JndiObjectFactoryBean --> -->
 * <bean id="multiEntityDataSource" class="MultiEntityDataSource" > <property
 * name="multiEntityDatasourceHelper"><ref
 * bean="multiEntityDatasourceHelper"/></property> </bean>
 * 
 * 
 * 
 * 
 * <!-- daos --> <!-- iBatis DAOs --> <bean id="ejemploDAO" class="ejemploDAO">
 * <property name="sqlMapClient"><ref bean="sqlMapClient"/></property> </bean>
 * 
 * <!-- Configuracion de las transacciones -->
 * 
 * <bean id="transactionManager"
 * class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
 * <property name="dataSource"><ref bean="dataSource"/></property> </bean>
 * 
 * 
 * <bean id="transactionTemplate" abstract="true" class=
 * "org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
 * <property name="transactionManager"><ref
 * bean="transactionManager"/></property> <property
 * name="transactionAttributes"> <props> <!-- Los metodos que comiencen por get
 * en los Manager seran readOnly --> <prop
 * key="get*">PROPAGATION_REQUIRED,readOnly</prop> <prop
 * key="find*">PROPAGATION_REQUIRED,readOnly</prop> <prop
 * key="*">PROPAGATION_REQUIRED</prop> </props> </property> </bean>
 * 
 * 
 * <!--manager transaccionales --> <bean id="ejemploManager"
 * parent="transactionTemplate"> <property name="target"> <bean
 * class="ejemploManager"> <property name="dao"><ref
 * bean="ejemploDAO"/></property> </bean> </property> </bean>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * </bean>
 * 
 * 
 * 
 */
public class MultiEntityDataSource extends AbstractDataSource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MultiEntityDataSource.class);

	/**
	 * propiedad que se encargará de obener realmente el datasource
	 */
	protected MultiEntityDatasourceHelper multiEntityDatasourceHelper;

	public MultiEntityDatasourceHelper getMultiEntityDatasourceHelper() {
		return multiEntityDatasourceHelper;
	}

	public void setMultiEntityDatasourceHelper(
			MultiEntityDatasourceHelper multiEntityDatasourceHelper) {
		this.multiEntityDatasourceHelper = multiEntityDatasourceHelper;
	}

	/**
	 * 
	 */
	public Connection getConnection() throws SQLException {

		Connection result = multiEntityDatasourceHelper.getDatasource()
				.getConnection();
		return result;
	}

	/**
	 * 
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {

		Connection result = multiEntityDatasourceHelper.getDatasource()
				.getConnection(username, password);
		return result;
	}

}
