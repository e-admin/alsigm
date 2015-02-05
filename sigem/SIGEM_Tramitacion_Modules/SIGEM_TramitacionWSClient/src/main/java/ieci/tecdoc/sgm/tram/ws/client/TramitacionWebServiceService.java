package ieci.tecdoc.sgm.tram.ws.client;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface TramitacionWebServiceService extends Service {
	
	public String getTramitacionWebServiceAddress();

	public TramitacionWebService getTramitacionWebService()
			throws ServiceException;

	public TramitacionWebService getTramitacionWebService(
			java.net.URL portAddress) throws ServiceException;
}
