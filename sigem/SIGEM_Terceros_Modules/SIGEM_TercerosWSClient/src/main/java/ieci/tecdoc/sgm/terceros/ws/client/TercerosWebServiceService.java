package ieci.tecdoc.sgm.terceros.ws.client;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface TercerosWebServiceService extends Service {

	public String getTercerosWebServiceAddress();

	public TercerosWebService getTercerosWebService() throws ServiceException;

	public TercerosWebService getTercerosWebService(java.net.URL portAddress)
			throws ServiceException;
}
