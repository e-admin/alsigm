package common.bi;

import se.usuarios.ServiceClient;

import common.db.ITransactionManager;

public interface ServiceSession {
	// Una sesion de servicio tiene que haber sido iniciada por alguien
	public ServiceClient getSessionOwner();

	public void setSessionOwner(ServiceClient serviceClient);

	public ITransactionManager getTransactionManager();

	public void setTransactionManager(ITransactionManager txManager);
}
