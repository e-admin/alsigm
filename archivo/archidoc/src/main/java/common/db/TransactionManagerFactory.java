/*
 * Created on 10-may-2005
 *
 */
package common.db;

public class TransactionManagerFactory {

	public static ITransactionManager getTransactionManager() {
		return new TransactionManagerImpl();
	}

	public static ITransactionManager getTransactionManager(
			DbDataSource dataSource) {
		return new TransactionManagerImpl(dataSource);
	}

}
