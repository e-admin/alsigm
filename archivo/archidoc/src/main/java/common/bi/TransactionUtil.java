/*
 * Created on 26-sep-2005
 *
 */
package common.bi;

import common.db.ITransactionManager;

/**
 * @author LUISANVE
 * 
 */
public class TransactionUtil {

	public static void setTxManager(ITransactionManager txManager,
			ServiceBase service) {
		service.setTransactionManager(txManager);
	}
}
