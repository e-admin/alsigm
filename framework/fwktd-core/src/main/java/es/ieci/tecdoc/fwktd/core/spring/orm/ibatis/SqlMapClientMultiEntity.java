package es.ieci.tecdoc.fwktd.core.spring.orm.ibatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.execution.BatchException;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 *          Clase para integración de spring con ibatis teniendo en cuenta
 *          multientidad
 * 
 *          Ejemplo de configuración completa en ibatis multientidad
 * 
 * @see MultiEntityContextHolder
 * 
 *      <!--sqlMapClient multEntidad --> <bean id="sqlMapClientMultiEntity"
 *      class
 *      ="es.ieci.tecdoc.fwktd.core.spring.orm.ibatis.SqlMapClientMultiEntity">
 *      <property name="configLocation"
 *      value="classpath:/sqlmaps/sqlmap-config.xml" /> <property
 *      name="dataSource" ref="dataSource" /> </bean>
 * 
 *      <bean name="personDao"
 *      class="es.ieci.tecdoc.fwktd.sampleWeb.business.dao.IbatisPersonDAOImpl">
 *      <!-- <property name="sqlMapClient" ref="sqlMapClient"/> --> <property
 *      name="sqlMapClient" ref="sqlMapClientMultiEntity"/>
 * 
 *      </bean>
 * 
 *      <!--datasource multEntidad --> <bean id="dataSource" class="es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityJndiDatasource"
 *      > <property name="jndiBaseName">
 *      <value>java:comp/env/jdbc/person</value> </property> </bean>
 * 
 * 
 *      <!-- Configuracion de las transacciones -->
 * 
 *      <bean id="transactionManager"
 *      class="org.springframework.jdbc.datasource.DataSourceTransactionManager"
 *      > <property name="dataSource"><ref bean="dataSource"/></property>
 *      </bean>
 * 
 * 
 *      <bean id="transactionTemplate" abstract="true" class=
 *      "org.springframework.transaction.interceptor.TransactionProxyFactoryBean"
 *      > <property name="transactionManager"><ref
 *      bean="transactionManager"/></property> <property
 *      name="transactionAttributes"> <props> <!-- Los metodos que comiencen por
 *      get en los Manager seran readOnly --> <prop
 *      key="get*">PROPAGATION_REQUIRED,readOnly</prop> <prop
 *      key="find*">PROPAGATION_REQUIRED,readOnly</prop> <prop
 *      key="*">PROPAGATION_REQUIRED</prop> </props> </property> </bean>
 * 
 *      <!-- manager transaccional --> <bean name="personManager"
 *      parent="transactionTemplate"> <property name="target"> <bean
 *      class="es.ieci.tecdoc.fwktd.sampleWeb.business.manager.PersonaManagerImpl"
 *      > <property name="personDao"><ref bean="personDao"/></property> </bean>
 *      </property>
 * 
 *      </bean>
 * 
 */
public class SqlMapClientMultiEntity implements SqlMapClient {

	private static Logger log = LoggerFactory
			.getLogger(SqlMapClientMultiEntity.class);

	protected DataSource dataSource;

	protected Resource configLocation;

	private Hashtable<String, SqlMapClient> targetSqlMapClients = new Hashtable<String, SqlMapClient>();

	/**
	 * 
	 * @return
	 */
	public Resource getConfigLocation() {
		return configLocation;
	}

	/**
	 * 
	 * @param configLocation
	 */
	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	/**
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 
	 * @return
	 */
	public SqlMapClient generateSqlMapClient() {
		SqlMapClient result = null;

		SqlMapClientFactoryBean sqlMapClientFactoryBean = new SqlMapClientFactoryBean();
		sqlMapClientFactoryBean.setConfigLocation(configLocation);
		sqlMapClientFactoryBean.setDataSource(dataSource);
		try {
			sqlMapClientFactoryBean.afterPropertiesSet();
		} catch (Exception e) {
			String message = "No se ha podido generar el slmapClient";
			log.error(message, e);
			throw new RuntimeException(message, e);

		}
		result = (SqlMapClientImpl) sqlMapClientFactoryBean.getObject();

		return result;

	}

	/**
	 * 
	 * @return
	 */
	protected String getCurrentSqlMapClientKey() {
		String result = null;
		result = MultiEntityContextHolder.getEntity();
		if (result==null){
			result="";
		}

		return result;
	}

	/**
	 * 
	 * @return
	 */
	protected SqlMapClient getCurrentSqlMapClient() {

		String currentSqlMapClientKey = this.getCurrentSqlMapClientKey();

		SqlMapClient result = ((SqlMapClient) targetSqlMapClients
				.get(currentSqlMapClientKey));

		if (result == null) {
			result = generateSqlMapClient();
			if (result != null) {
				putSqlMapClient(this.getCurrentSqlMapClientKey(), result);
			} else {
				String message = "No se ha podido generar el slmapClient para: "
						+ currentSqlMapClientKey;
				log.error(message);
				throw new RuntimeException(message);

			}
		}
		return result;
	}

	/**
	 * 
	 * @param key
	 * @param sqlMapClient
	 */
	private synchronized void putSqlMapClient(String key,
			SqlMapClient sqlMapClient) {

		targetSqlMapClients.put(key, sqlMapClient);

	}

	/**
	 * 
	 */
	public void flushDataCache() {
		getCurrentSqlMapClient().flushDataCache();
	}

	/**
	 * 
	 */
	public void flushDataCache(String s) {
		getCurrentSqlMapClient().flushDataCache(s);

	}

	/**
	 * 
	 */
	public SqlMapSession getSession() {

		return getCurrentSqlMapClient().openSession();
	}

	/**
	 * 
	 */
	public SqlMapSession openSession() {
		return getCurrentSqlMapClient().openSession();
	}

	/**
	 * 
	 */
	public SqlMapSession openSession(Connection connection) {
		return getCurrentSqlMapClient().openSession(connection);
	}

	/**
	 * 
	 */
	public int delete(String s) throws SQLException {
		return getCurrentSqlMapClient().delete(s);
	}

	/**
	 * 
	 */
	public int delete(String s, Object obj) throws SQLException {
		return getCurrentSqlMapClient().delete(s, obj);
	}

	/**
	 * 
	 */
	public int executeBatch() throws SQLException {
		return getCurrentSqlMapClient().executeBatch();
	}

	public List executeBatchDetailed() throws SQLException, BatchException {
		return getCurrentSqlMapClient().executeBatchDetailed();
	}

	/**
	 * 
	 */
	public Object insert(String s) throws SQLException {
		return getCurrentSqlMapClient().insert(s);
	}

	/**
	 * 
	 */
	public Object insert(String s, Object obj) throws SQLException {
		return getCurrentSqlMapClient().insert(s, obj);
	}

	/**
	 * 
	 */
	public List queryForList(String s) throws SQLException {
		return getCurrentSqlMapClient().queryForList(s);
	}

	/**
	 * 
	 */
	public List queryForList(String s, Object obj) throws SQLException {
		return getCurrentSqlMapClient().queryForList(s, obj);
	}

	/**
	 * 
	 */
	public List queryForList(String s, int i, int j) throws SQLException {
		return getCurrentSqlMapClient().queryForList(s, i, j);
	}

	/**
	 * 
	 */
	public List queryForList(String s, Object obj, int i, int j)
			throws SQLException {
		return getCurrentSqlMapClient().queryForList(s, obj, i, j);
	}

	/**
	 * 
	 */
	public Map queryForMap(String s, Object obj, String s1) throws SQLException {
		return getCurrentSqlMapClient().queryForMap(s, obj, s1);
	}

	/**
	 * 
	 */
	public Map queryForMap(String s, Object obj, String s1, String s2)
			throws SQLException {
		return getCurrentSqlMapClient().queryForMap(s, obj, s1, s2);
	}

	/**
	 * 
	 */
	public Object queryForObject(String s) throws SQLException {
		return getCurrentSqlMapClient().queryForObject(s);
	}

	/**
	 * 
	 */
	public Object queryForObject(String s, Object obj) throws SQLException {

		return getCurrentSqlMapClient().queryForObject(s, obj);
	}

	/**
	 * 
	 */
	public Object queryForObject(String s, Object obj, Object obj1)
			throws SQLException {
		return getCurrentSqlMapClient().queryForObject(s, obj, obj1);
	}

	/**
	 * 
	 */
	public PaginatedList queryForPaginatedList(String s, int i)
			throws SQLException {

		return getCurrentSqlMapClient().queryForPaginatedList(s, i);
	}

	/**
	 * 
	 */
	public PaginatedList queryForPaginatedList(String s, Object obj, int i)
			throws SQLException {
		return getCurrentSqlMapClient().queryForPaginatedList(s, obj, i);
	}

	/**
	 * 
	 */
	public void queryWithRowHandler(String s, RowHandler rowhandler)
			throws SQLException {
		getCurrentSqlMapClient().queryWithRowHandler(s, rowhandler);

	}

	/**
	 * 
	 */
	public void queryWithRowHandler(String s, Object obj, RowHandler rowhandler)
			throws SQLException {
		getCurrentSqlMapClient().queryWithRowHandler(s, obj, rowhandler);

	}

	/**
	 * 
	 */
	public void startBatch() throws SQLException {
		getCurrentSqlMapClient().startBatch();
	}

	/**
	 * 
	 */
	public int update(String s) throws SQLException {
		return getCurrentSqlMapClient().update(s);
	}

	/**
	 * 
	 */
	public int update(String s, Object obj) throws SQLException {
		return getCurrentSqlMapClient().update(s, obj);
	}

	/**
	 * 
	 */
	public void commitTransaction() throws SQLException {
		getCurrentSqlMapClient().commitTransaction();
	}

	/**
	 * 
	 */
	public void endTransaction() throws SQLException {
		getCurrentSqlMapClient().endTransaction();
	}

	/**
	 * 
	 */
	public Connection getCurrentConnection() throws SQLException {
		return getCurrentSqlMapClient().getCurrentConnection();
	}

	/**
	 * 
	 */
	public DataSource getDataSource() {
		return getCurrentSqlMapClient().getDataSource();
	}

	/**
	 * 
	 */
	public Connection getUserConnection() throws SQLException {
		return getCurrentSqlMapClient().getUserConnection();
	}

	/**
	 * 
	 */
	public void setUserConnection(Connection connection) throws SQLException {
		getCurrentSqlMapClient().setUserConnection(connection);
	}

	/**
	 * 
	 */
	public void startTransaction() throws SQLException {
		getCurrentSqlMapClient().startTransaction();
	}

	/**
	 * 
	 */
	public void startTransaction(int i) throws SQLException {
		getCurrentSqlMapClient().startTransaction(i);
	}

}
