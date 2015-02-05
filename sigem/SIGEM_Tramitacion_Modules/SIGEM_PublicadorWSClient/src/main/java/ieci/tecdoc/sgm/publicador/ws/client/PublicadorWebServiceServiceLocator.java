package ieci.tecdoc.sgm.publicador.ws.client;


import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;


public class PublicadorWebServiceServiceLocator extends Service implements PublicadorWebServiceService {

    private String PublicadorWebService_address = "http://localhost:8080/SIGEM_PublicadorWS/services/PublicadorWebService";
    private String PublicadorWebServiceWSDDServiceName = "PublicadorWebService";
    private HashSet ports = null;
    
    public PublicadorWebServiceServiceLocator() {
    	super();
    }

    public PublicadorWebServiceServiceLocator(EngineConfiguration config) {
        super(config);
    }

    public PublicadorWebServiceServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    public String getPublicadorWebServiceAddress() {
        return PublicadorWebService_address;
    }


    public String getPublicadorWebServiceWSDDServiceName() {
        return PublicadorWebServiceWSDDServiceName;
    }

    public void setPublicadorWebServiceWSDDServiceName(String name) {
        PublicadorWebServiceWSDDServiceName = name;
    }

    public PublicadorWebService getPublicadorWebService() throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(PublicadorWebService_address);
		} catch (MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getPublicadorWebService(endpoint);
	}

    public PublicadorWebService getPublicadorWebService(URL portAddress) throws ServiceException {
        try {
            PublicadorWebServiceSoapBindingStub _stub = new PublicadorWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getPublicadorWebServiceWSDDServiceName());
            return _stub;
        }
        catch (AxisFault e) {
            return null;
        }
    }

    public void setPublicadorWebServiceEndpointAddress(String address) {
        PublicadorWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (PublicadorWebService.class.isAssignableFrom(serviceEndpointInterface)) {
               PublicadorWebServiceSoapBindingStub _stub = new PublicadorWebServiceSoapBindingStub(new URL(PublicadorWebService_address), this);
                _stub.setPortName(getPublicadorWebServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("PublicadorWebService".equals(inputPortName)) {
            return getPublicadorWebService();
        }
        else  {
            Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://server.ws.publicador.sgm.tecdoc.ieci", "PublicadorWebServiceService");
    }


    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://server.ws.publicador.sgm.tecdoc.ieci", "PublicadorWebService"));
        }
        return ports.iterator();
    }

    public void setEndpointAddress(String portName, String address) throws ServiceException {
		if (PublicadorWebServiceWSDDServiceName.equals(portName)) {
			setPublicadorWebServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

    public void setEndpointAddress(QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
