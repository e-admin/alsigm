package common.bi;

import se.usuarios.ServiceClient;
import auditoria.logger.IArchivoLogger;

import common.db.ITransactionManager;
import common.security.SecurityManagerBase;

public interface IServiceBase {
	public ITransactionManager getTransactionManager();

	public ServiceClient getServiceClient();

	public void attachToSession(ServiceSession serviceSession);

	public void setLogger(IArchivoLogger logger);

	public IArchivoLogger getLogger();

	public void setSecurityManager(SecurityManagerBase securityManager);

	// abstract LoggingEvent getLogginEvent(int action);
	public ServiceSession getServiceSession();

}
