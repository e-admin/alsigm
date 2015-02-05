package ieci.tecdoc.sgm.terceros.ws.client;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.terceros.ws.client.dto.DireccionElectronica;
import ieci.tecdoc.sgm.terceros.ws.client.dto.DireccionPostal;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoDireccionElectronica;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoDireccionPostal;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoTercero;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaDireccionesElectronicas;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaDireccionesPostales;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaTerceros;
import ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

public class TercerosWebServiceSoapBindingStub extends Stub implements
		TercerosWebService {

	private Vector cachedSerClasses = new Vector();

	private Vector cachedSerQNames = new Vector();

	private Vector cachedSerFactories = new Vector();

	private Vector cachedDeserFactories = new Vector();

	static OperationDesc[] _operations;

	static {
		_operations = new OperationDesc[13];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		OperationDesc oper;
		ParameterDesc param;

		oper = new OperationDesc();
		oper.setName("lookupByCode");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "codigo"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaTerceros"));
		oper.setReturnClass(ListaTerceros.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupByCodeReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[0] = oper;

		oper = new OperationDesc();
		oper.setName("lookupByName");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "nombre"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "apellido1"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "apellido2"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaTerceros"));
		oper.setReturnClass(ListaTerceros.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupByNameReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[1] = oper;

		oper = new OperationDesc();
		oper.setName("lookupById");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoTercero"));
		oper.setReturnClass(InfoTercero.class);
		oper
				.setReturnQName(new QName(
						"http://server.ws.terceros.sgm.tecdoc.ieci",
						"lookupByIdReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[2] = oper;

		oper = new OperationDesc();
		oper.setName("lookupByCodeValues");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "codigo"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "onlyDefaultValues"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"boolean"), boolean.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaTerceros"));
		oper.setReturnClass(ListaTerceros.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupByCodeValuesReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[3] = oper;

		oper = new OperationDesc();
		oper.setName("lookupByNameValues");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "nombre"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "apellido1"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "apellido2"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "onlyDefaultValues"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"boolean"), boolean.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaTerceros"));
		oper.setReturnClass(ListaTerceros.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupByNameValuesReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[4] = oper;

		oper = new OperationDesc();
		oper.setName("lookupByIdAddresses");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "postalAddressId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "electronicAddressId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoTercero"));
		oper.setReturnClass(InfoTercero.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupByIdAddressesReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[5] = oper;

		oper = new OperationDesc();
		oper.setName("lookupByIdValues");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "onlyDefaultValues"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"boolean"), boolean.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoTercero"));
		oper.setReturnClass(InfoTercero.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupByIdValuesReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[6] = oper;
		
		oper = new OperationDesc();
		oper.setName("lookupPostalAddresses");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaDireccionesPostales"));
		oper.setReturnClass(ListaDireccionesPostales.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupPostalAddressesReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[7] = oper;

		oper = new OperationDesc();
		oper.setName("lookupDefaultPostalAddress");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoDireccionPostal"));
		oper.setReturnClass(InfoDireccionPostal.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupDefaultPostalAddressReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[8] = oper;

		oper = new OperationDesc();
		oper.setName("lookupElectronicAddresses");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaDireccionesElectronicas"));
		oper.setReturnClass(ListaDireccionesElectronicas.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupElectronicAddressesReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[9] = oper;

		oper = new OperationDesc();
		oper.setName("lookupDefaultElectronicAddress");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoDireccionElectronica"));
		oper.setReturnClass(InfoDireccionElectronica.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"lookupDefaultElectronicAddressReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[10] = oper;

		oper = new OperationDesc();
		oper.setName("getPostalAddress");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoDireccionPostal"));
		oper.setReturnClass(InfoDireccionPostal.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"getPostalAddressReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[11] = oper;

		oper = new OperationDesc();
		oper.setName("getElectronicAddress");
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "entityId"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		param = new ParameterDesc(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "id"),
				ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema",
						"string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoDireccionElectronica"));
		oper.setReturnClass(InfoDireccionElectronica.class);
		oper.setReturnQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"getElectronicAddressReturn"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[12] = oper;

	}

	public TercerosWebServiceSoapBindingStub() throws AxisFault {
		this(null);
	}

	public TercerosWebServiceSoapBindingStub(java.net.URL endpointURL,
			javax.xml.rpc.Service service) throws AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public TercerosWebServiceSoapBindingStub(javax.xml.rpc.Service service)
			throws AxisFault {
		if (service == null) {
			super.service = new Service();
		} else {
			super.service = service;
		}
		((Service) super.service).setTypeMappingVersion("1.2");
		Class cls;
		QName qName;
		QName qName2;
		Class beansf = BeanSerializerFactory.class;
		Class beandf = BeanDeserializerFactory.class;

		qName = new QName("http://dto.services.core.sgm.tecdoc.ieci",
				"RetornoServicio");
		cachedSerQNames.add(qName);
		cls = RetornoServicio.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"ArrayOfTerceros");
		cachedSerQNames.add(qName);
		cls = Tercero[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"Tercero");
		qName2 = new QName("http://server.ws.terceros.sgm.tecdoc.ieci", "item");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"InfoTercero");
		cachedSerQNames.add(qName);
		cls = InfoTercero.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"ListaTerceros");
		cachedSerQNames.add(qName);
		cls = ListaTerceros.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"Tercero");
		cachedSerQNames.add(qName);
		cls = Tercero.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"DireccionElectronica");
		cachedSerQNames.add(qName);
		cls = DireccionElectronica.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"ListaDireccionesElectronicas");
		cachedSerQNames.add(qName);
		cls = ListaDireccionesElectronicas.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"ArrayOfDireccionesElectronicas");
		cachedSerQNames.add(qName);
		cls = DireccionElectronica[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionElectronica");
		qName2 = new QName("http://server.ws.terceros.sgm.tecdoc.ieci", "item");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"DireccionPostal");
		cachedSerQNames.add(qName);
		cls = DireccionPostal.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"ListaDireccionesPostales");
		cachedSerQNames.add(qName);
		cls = ListaDireccionesPostales.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci",
				"ArrayOfDireccionesPostales");
		cachedSerQNames.add(qName);
		cls = DireccionPostal[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionPostal");
		qName2 = new QName("http://server.ws.terceros.sgm.tecdoc.ieci", "item");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());
	}

	protected Call createCall() throws RemoteException {
		try {
			Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						Class cls = (Class) cachedSerClasses.get(i);
						QName qName = (QName) cachedSerQNames.get(i);
						Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							Class sf = (Class) cachedSerFactories.get(i);
							Class df = (Class) cachedDeserFactories.get(i);
							_call
									.registerTypeMapping(cls, qName, sf, df,
											false);
						} else if (x instanceof SerializerFactory) {
							SerializerFactory sf = (SerializerFactory) cachedSerFactories
									.get(i);
							DeserializerFactory df = (DeserializerFactory) cachedDeserFactories
									.get(i);
							_call
									.registerTypeMapping(cls, qName, sf, df,
											false);
						}
					}
				}
			}
			return _call;
		} catch (Throwable _t) {
			throw new AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public ListaTerceros lookupByCode(String entityId, String codigo) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "lookupByCode"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { entityId, codigo });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ListaTerceros) _resp;
				} catch (Exception _exception) {
					return (ListaTerceros) JavaUtils.convert(_resp,
							ListaTerceros.class);
				}
			}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ListaTerceros lookupByName(String entityId, String nombre, String apellido1,
			String apellido2) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "lookupByName"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { entityId, nombre, apellido1,
					apellido2 });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ListaTerceros) _resp;
				} catch (Exception _exception) {
					return (ListaTerceros) JavaUtils.convert(_resp,
							ListaTerceros.class);
				}
			}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public InfoTercero lookupById(String entityId, String id) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "lookupById"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { entityId, id });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (InfoTercero) _resp;
				} catch (Exception _exception) {
					return (InfoTercero) JavaUtils.convert(_resp,
							InfoTercero.class);
				}
			}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ListaTerceros lookupByCodeValues(String entityId, String codigo,
			boolean onlyDefaultValues) throws RemoteException {
		return (ListaTerceros) makeCall(_operations[3], 
				new Object[] { entityId, codigo, new Boolean(onlyDefaultValues) },
				ListaTerceros.class);
	}

	public ListaTerceros lookupByNameValues(String entityId, String nombre,
				String apellido1, String apellido2, boolean onlyDefaultValues) 
			throws RemoteException {
		return (ListaTerceros) makeCall(_operations[4], 
				new Object[] { entityId, nombre, apellido1, apellido2, 
					new Boolean(onlyDefaultValues) },
				ListaTerceros.class);
	}

	public InfoTercero lookupByIdAddresses(String entityId, String id,
			String postalAddressId, String electronicAddressId)
			throws RemoteException {
		return (InfoTercero) makeCall(_operations[5], 
				new Object[] { entityId, id, postalAddressId, electronicAddressId }, 
				InfoTercero.class);
	}

	public InfoTercero lookupByIdValues(String entityId, String id,
			boolean onlyDefaultValues) throws RemoteException {
		return (InfoTercero) makeCall(_operations[6], 
				new Object[] { entityId, id, new Boolean(onlyDefaultValues) },
				InfoTercero.class);
	}

	public ListaDireccionesPostales lookupPostalAddresses(String entityId, String id) 
			throws RemoteException {
		return (ListaDireccionesPostales) makeCall(_operations[7], 
				new Object[] { entityId, id },
				ListaDireccionesPostales.class);
	}

	public InfoDireccionPostal lookupDefaultPostalAddress(String entityId, String id) 
			throws RemoteException {
		return (InfoDireccionPostal) makeCall(_operations[8],
				new Object[] { entityId, id }, 
				InfoDireccionPostal.class);
	}

	public ListaDireccionesElectronicas lookupElectronicAddresses(String entityId, String id) 
			throws RemoteException {
		return (ListaDireccionesElectronicas) makeCall(_operations[9],
				new Object[] { entityId, id }, 
				ListaDireccionesElectronicas.class);
	}

	public InfoDireccionElectronica lookupDefaultElectronicAddress(String entityId, String id) 
			throws RemoteException {
		return (InfoDireccionElectronica) makeCall(_operations[10], 
				new Object[] { entityId, id }, 
				InfoDireccionElectronica.class);
	}

	public InfoDireccionPostal getPostalAddress(String entityId, String id) 
			throws RemoteException {
		return (InfoDireccionPostal) makeCall(_operations[11], new Object[] {
				entityId, id }, InfoDireccionPostal.class);
	}

	public InfoDireccionElectronica getElectronicAddress( String entityId, String id) 
			throws RemoteException {
		return (InfoDireccionElectronica) makeCall(_operations[12],
				new Object[] { entityId, id }, 
				InfoDireccionElectronica.class);
	}

	private Object makeCall(OperationDesc operation, Object[] parameters, Class returnClass) 
			throws RemoteException {
		
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(operation);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				operation.getName()));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(parameters);

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return _resp;
				} catch (Exception _exception) {
					return JavaUtils.convert(_resp, returnClass);
				}
			}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
