package common.bi;

import se.usuarios.ServiceClient;

import common.db.ITransactionManager;

public class DefaultServiceSession implements ServiceSession {

	protected ServiceClient serviceClient = null;
	protected ITransactionManager txManager = null;

	public DefaultServiceSession(ServiceClient serviceClient,
			ITransactionManager txManager) {
		this.serviceClient = serviceClient;
		this.txManager = txManager;
	}

	public ServiceClient getSessionOwner() {
		return serviceClient;
	}

	public ITransactionManager getTransactionManager() {
		return txManager;
	}

	public void setSessionOwner(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	public void setTransactionManager(ITransactionManager txManager) {
		this.txManager = txManager;
	}
}
