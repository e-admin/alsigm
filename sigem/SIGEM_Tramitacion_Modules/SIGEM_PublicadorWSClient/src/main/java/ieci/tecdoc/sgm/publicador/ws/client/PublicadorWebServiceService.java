package ieci.tecdoc.sgm.publicador.ws.client;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface PublicadorWebServiceService extends Service {
	
    public String getPublicadorWebServiceAddress();

    public PublicadorWebService getPublicadorWebService() throws ServiceException;

    public PublicadorWebService getPublicadorWebService(URL portAddress) throws ServiceException;
    
}
