package ieci.tecdoc.sgm.terceros.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class TercerosWebServiceServiceLocator extends Service implements
		TercerosWebServiceService {

	public TercerosWebServiceServiceLocator() {
		super();
	}

	public TercerosWebServiceServiceLocator(EngineConfiguration config) {
		super(config);
	}

	public TercerosWebServiceServiceLocator(String wsdlLoc, QName sName)
			throws ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for TercerosWebService
	private String TercerosWebService_address = "http://localhost:8080/SIGEM_TercerosWS/services/TercerosWebService";

	public String getTercerosWebServiceAddress() {
		return TercerosWebService_address;
	}

	// The WSDD service name defaults to the port name.
	private String TercerosWebServiceWSDDServiceName = "TercerosWebService";

	public String getTercerosWebServiceWSDDServiceName() {
		return TercerosWebServiceWSDDServiceName;
	}

	public void setTercerosWebServiceWSDDServiceName(String name) {
		TercerosWebServiceWSDDServiceName = name;
	}

	public TercerosWebService getTercerosWebService() throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(TercerosWebService_address);
		} catch (MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getTercerosWebService(endpoint);
	}

	public TercerosWebService getTercerosWebService(URL portAddress)
			throws ServiceException {
		try {
			TercerosWebServiceSoapBindingStub _stub = new TercerosWebServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getTercerosWebServiceWSDDServiceName());
			return _stub;
		} catch (AxisFault e) {
			return null;
		}
	}

	public void setTercerosWebServiceEndpointAddress(String address) {
		TercerosWebService_address = address;
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public Remote getPort(Class serviceEndpointInterface)
			throws ServiceException {
		try {
			if (TercerosWebService.class
					.isAssignableFrom(serviceEndpointInterface)) {
				TercerosWebServiceSoapBindingStub _stub = new TercerosWebServiceSoapBindingStub(
						new URL(TercerosWebService_address), this);
				_stub.setPortName(getTercerosWebServiceWSDDServiceName());
				return _stub;
			}
		} catch (Throwable t) {
			throw new ServiceException(t);
		}
		throw new ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public Remote getPort(QName portName, Class serviceEndpointInterface)
			throws ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("TercerosWebService".equals(inputPortName)) {
			return getTercerosWebService();
		} else {
			Remote _stub = getPort(serviceEndpointInterface);
			((Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"TercerosWebServiceService");
	}

	private HashSet ports = null;

	public Iterator getPorts() {
		if (ports == null) {
			ports = new HashSet();
			ports.add(new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
					"TercerosWebService"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address)
			throws ServiceException {

		if ("TercerosWebService".equals(portName)) {
			setTercerosWebServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(QName portName, String address)
			throws ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
