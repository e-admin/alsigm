package ieci.tecdoc.sgm.tram.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;

public class TramitacionWebServiceServiceLocator extends Service 
		implements TramitacionWebServiceService {

	public TramitacionWebServiceServiceLocator() {
	}

	public TramitacionWebServiceServiceLocator(
			EngineConfiguration config) {
		super(config);
	}

	public TramitacionWebServiceServiceLocator(String wsdlLoc,
			QName sName) throws ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for TramitacionWebService
	private String TramitacionWebService_address = "http://localhost:8080/tramitadorWS/services/TramitacionWebService";

	public String getTramitacionWebServiceAddress() {
		return TramitacionWebService_address;
	}

	// The WSDD service name defaults to the port name.
	private String TramitacionWebServiceWSDDServiceName = "TramitacionWebService";

	public String getTramitacionWebServiceWSDDServiceName() {
		return TramitacionWebServiceWSDDServiceName;
	}

	public void setTramitacionWebServiceWSDDServiceName(String name) {
		TramitacionWebServiceWSDDServiceName = name;
	}

	public TramitacionWebService getTramitacionWebService()
			throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(TramitacionWebService_address);
		} catch (MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getTramitacionWebService(endpoint);
	}

	public TramitacionWebService getTramitacionWebService(
			URL portAddress) throws ServiceException {
		try {
			TramitacionWebServiceSoapBindingStub _stub = new TramitacionWebServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getTramitacionWebServiceWSDDServiceName());
			return _stub;
		} catch (AxisFault e) {
			return null;
		}
	}

	public void setTramitacionWebServiceEndpointAddress(String address) {
		TramitacionWebService_address = address;
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public Remote getPort(Class serviceEndpointInterface)
			throws ServiceException {
		try {
			if (TramitacionWebService.class
					.isAssignableFrom(serviceEndpointInterface)) {
				TramitacionWebServiceSoapBindingStub _stub = new TramitacionWebServiceSoapBindingStub(
						new URL(TramitacionWebService_address), this);
				_stub.setPortName(getTramitacionWebServiceWSDDServiceName());
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
	public Remote getPort(QName portName,
			Class serviceEndpointInterface) throws ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("TramitacionWebService".equals(inputPortName)) {
			return getTramitacionWebService();
		} else {
			Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName(
				"http://server.ws.tram.sgm.tecdoc.ieci",
				"TramitacionWebServiceService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new QName(
					"http://server.ws.tram.sgm.tecdoc.ieci",
					"TramitacionWebService"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName,
			String address) throws ServiceException {

		if ("TramitacionWebService".equals(portName)) {
			setTramitacionWebServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(QName portName,
			String address) throws ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
