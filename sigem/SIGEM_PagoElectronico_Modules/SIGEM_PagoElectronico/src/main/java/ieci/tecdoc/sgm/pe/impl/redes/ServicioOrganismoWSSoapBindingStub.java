/**
 * ServicioOrganismoWSSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;
public class ServicioOrganismoWSSoapBindingStub extends org.apache.axis.client.Stub implements ServicioOrganismoWS {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[8];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consulta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "busquedaSop"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:BusquedaSop", "BusquedaSop"), BusquedaSop.class, false, false);
        oper.addParameter(param);
        	//oper.setReturnType(new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_xsd_anyType"));
			oper.setReturnType(new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_xsd_anyType"));
        oper.setReturnClass(java.lang.Object[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "consultaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaEeff");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "busquedaEeff"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "BusquedaEeff"), BusquedaEeff.class, false, false);
        oper.addParameter(param);
        	//oper.setReturnType(new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_xsd_anyType"));
			oper.setReturnType(new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_xsd_anyType"));
        oper.setReturnClass(java.lang.Object[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "consultaEeffReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cargaMasiva");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "altaMasiva"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "AltaMasiva"), AltaMasiva.class, false, false);
        oper.addParameter(param);
        	//oper.setReturnType(new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_xsd_anyType"));
        	oper.setReturnType(new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_xsd_anyType"));
        oper.setReturnClass(java.lang.Object[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "cargaMasivaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pago60_1_2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cuaderno60_1_2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno60_1_2"), Cuaderno60_1_2.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta60_1_2"));
        oper.setReturnClass(CuadernoRespuesta60_1_2.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "pago60_1_2Return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pago60_3");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cuaderno60_3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno60_3"), Cuaderno60_3.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta60_3"));
        oper.setReturnClass(CuadernoRespuesta60_3.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "pago60_3Return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pago65");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cuaderno65"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno65"), Cuaderno65.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta65"));
        oper.setReturnClass(CuadernoRespuesta65.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "pago65Return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pago57");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cuaderno57"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno57"), Cuaderno57.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta57"));
        oper.setReturnClass(CuadernoRespuesta57.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "pago57Return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pagoLotes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cuadernos"), org.apache.axis.description.ParameterDesc.IN, 
        		//new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        		new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "ResultadoPagoLotesWS"));
        oper.setReturnClass(ResultadoPagoLotesWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "pagoLotesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

    }

    public ServicioOrganismoWSSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ServicioOrganismoWSSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ServicioOrganismoWSSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            	//qName = new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_soapenc_string");
            	qName = new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_soapenc_string");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            	//qName = new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_xsd_anyType");
            	qName = new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_xsd_anyType");
            cachedSerQNames.add(qName);
            cls = java.lang.Object[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            	//qName = new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ArrayOf_xsd_int");
            	qName = new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ArrayOf_xsd_int");
            cachedSerQNames.add(qName);
            cls = int[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:BusquedaSop", "BusquedaSop");
            cachedSerQNames.add(qName);
            cls = BusquedaSop.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "AltaMasiva");
            cachedSerQNames.add(qName);
            cls = AltaMasiva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "BusquedaEeff");
            cachedSerQNames.add(qName);
            cls = BusquedaEeff.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno57");
            cachedSerQNames.add(qName);
            cls = Cuaderno57.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno60_1_2");
            cachedSerQNames.add(qName);
            cls = Cuaderno60_1_2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno60_3");
            cachedSerQNames.add(qName);
            cls = Cuaderno60_3.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "Cuaderno65");
            cachedSerQNames.add(qName);
            cls = Cuaderno65.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta57");
            cachedSerQNames.add(qName);
            cls = CuadernoRespuesta57.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta60_1_2");
            cachedSerQNames.add(qName);
            cls = CuadernoRespuesta60_1_2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta60_3");
            cachedSerQNames.add(qName);
            cls = CuadernoRespuesta60_3.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "CuadernoRespuesta65");
            cachedSerQNames.add(qName);
            cls = CuadernoRespuesta65.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "InfoAccessor");
            cachedSerQNames.add(qName);
            cls = InfoAccessor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "ResultadoPagoLotesWS");
            cachedSerQNames.add(qName);
            cls = ResultadoPagoLotesWS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
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
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
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
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public java.lang.Object[] consulta(BusquedaSop busquedaSop) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
        	String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
        	Class oHanlerClass = null; 
        	try {
        		oHanlerClass = Class.forName(cHandlerClass);
        	} catch (ClassNotFoundException e) {
        		throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
        	}
        	List handlerList = new ArrayList();
        	handlerList.add(new HandlerInfo(oHanlerClass, null, null));
        	HandlerRegistry registry = service.getHandlerRegistry();
        	
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "consulta"));

        	//añadimos el handler
        	_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {busquedaSop});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Object[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Object[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Object[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Object[] consultaEeff(BusquedaEeff busquedaEeff) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
	    	String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
	    	Class oHanlerClass = null; 
	    	try {
	    		oHanlerClass = Class.forName(cHandlerClass);
	    	} catch (ClassNotFoundException e) {
	    		throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
	    	}
	    	List handlerList = new ArrayList();
	    	handlerList.add(new HandlerInfo(oHanlerClass, null, null));
	    	HandlerRegistry registry = service.getHandlerRegistry();
        
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "consultaEeff"));

        	//añadimos el handler
    		_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {busquedaEeff});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Object[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Object[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Object[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Object[] cargaMasiva(AltaMasiva altaMasiva) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
        	String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
        	Class oHanlerClass = null;
        	try
        	{
        		oHanlerClass = Class.forName(cHandlerClass);
        	}
        	catch (ClassNotFoundException e)
        	{
        		throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
        	}	
        	List handlerList = new ArrayList();
        	handlerList.add(new HandlerInfo(oHanlerClass, null, null));
        	org.apache.axis.client.Call _call = createCall();
        	
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "cargaMasiva"));

        	//añadimos el handler
        	_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);
        	
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {altaMasiva});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Object[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Object[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Object[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CuadernoRespuesta60_1_2 pago60_1_2(Cuaderno60_1_2 cuaderno60_1_2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
        	String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
        	Class oHanlerClass = null; 
        	try {
        		oHanlerClass = Class.forName(cHandlerClass);
        	} catch (ClassNotFoundException e) {
        		throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
        	}	
        	List handlerList = new ArrayList();
        	handlerList.add(new HandlerInfo(oHanlerClass, null, null));
        	HandlerRegistry registry = service.getHandlerRegistry();
        	
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "pago60_1_2"));     

    		//añadimos el handler
    		_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cuaderno60_1_2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CuadernoRespuesta60_1_2) _resp;
            } catch (java.lang.Exception _exception) {
                return (CuadernoRespuesta60_1_2) org.apache.axis.utils.JavaUtils.convert(_resp, CuadernoRespuesta60_1_2.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CuadernoRespuesta60_3 pago60_3(Cuaderno60_3 cuaderno60_3) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
        	String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
        	Class oHanlerClass = null; 
        	try {
        		oHanlerClass = Class.forName(cHandlerClass);
        	} catch (ClassNotFoundException e) {
        		throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
        	}
        	List handlerList = new ArrayList();
        	handlerList.add(new HandlerInfo(oHanlerClass, null, null));
        	HandlerRegistry registry = service.getHandlerRegistry();
        	
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "pago60_3"));

			//añadimos el handler
			_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cuaderno60_3});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CuadernoRespuesta60_3) _resp;
            } catch (java.lang.Exception _exception) {
                return (CuadernoRespuesta60_3) org.apache.axis.utils.JavaUtils.convert(_resp, CuadernoRespuesta60_3.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CuadernoRespuesta65 pago65(Cuaderno65 cuaderno65) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
        	String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
        	Class oHanlerClass = null; 
        	try {
        		oHanlerClass = Class.forName(cHandlerClass);
        	} catch (ClassNotFoundException e) {
        		throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
        	}
        	List handlerList = new ArrayList();
        	handlerList.add(new HandlerInfo(oHanlerClass, null, null));
        	HandlerRegistry registry = service.getHandlerRegistry();
        	
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "pago65"));
        
        	//añadimos el handler
        	_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cuaderno65});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CuadernoRespuesta65) _resp;
            } catch (java.lang.Exception _exception) {
                return (CuadernoRespuesta65) org.apache.axis.utils.JavaUtils.convert(_resp, CuadernoRespuesta65.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CuadernoRespuesta57 pago57(Cuaderno57 cuaderno57) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
	        String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
	        Class oHanlerClass = null; 
	        try {
				oHanlerClass = Class.forName(cHandlerClass);
			} catch (ClassNotFoundException e) {
				throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
			}
	        List handlerList = new ArrayList();
	        handlerList.add(new HandlerInfo(oHanlerClass, null, null));
	        HandlerRegistry registry = service.getHandlerRegistry();
        
	    org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "pago57"));

        	//añadimos el handler
        	_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cuaderno57});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CuadernoRespuesta57) _resp;
            } catch (java.lang.Exception _exception) {
                return (CuadernoRespuesta57) org.apache.axis.utils.JavaUtils.convert(_resp, CuadernoRespuesta57.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ResultadoPagoLotesWS pagoLotes(java.lang.String[] cuadernos) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        	//Añadimos el handler que realizará la firma electrónica
	        String cHandlerClass = Configuracion.obtenerPropiedad(Configuracion.KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS);
	        Class oHanlerClass = null; 
	        try {
				oHanlerClass = Class.forName(cHandlerClass);
			} catch (ClassNotFoundException e) {
				throw new java.rmi.RemoteException("Error obteniendo clase handler de firma.", e);
			}
	        List handlerList = new ArrayList();
	        handlerList.add(new HandlerInfo(oHanlerClass, null, null));
	        HandlerRegistry registry = service.getHandlerRegistry();
        
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://op.ifc.spt.red.es", "pagoLotes"));

    	//añadimos el handler
    	_call.getService().getHandlerRegistry().setHandlerChain(new javax.xml.namespace.QName("", "ServicioOrganismoWS"), handlerList);        
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cuadernos});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ResultadoPagoLotesWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (ResultadoPagoLotesWS) org.apache.axis.utils.JavaUtils.convert(_resp, ResultadoPagoLotesWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
