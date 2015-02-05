/**
 * EntidadesWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.entidades.ws.client;

public class EntidadesWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[7];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nuevaEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "poEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.entidades.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.Entidad.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "nuevaEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "poEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.entidades.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "eliminarEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("actualizarEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "poEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.entidades.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.Entidad.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "actualizarEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "identificador"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.Entidad.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "obtenerEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscarEntidades");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "poCriterio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "CriterioBusquedaEntidades"), ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidades"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.Entidades.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "buscarEntidadesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerEntidades");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidades"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.Entidades.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "obtenerEntidadesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerIdentificadorEntidad");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "RetornoCadena"));
        oper.setReturnClass(ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "obtenerIdentificadorEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

    }

    public EntidadesWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public EntidadesWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public EntidadesWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "ArrayOfEntidad");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.entidades.ws.client.Entidad[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad");
            qName2 = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "CriterioBusquedaEntidades");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidad");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.entidades.ws.client.Entidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "Entidades");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.entidades.ws.client.Entidades.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "RetornoCadena");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena.class;
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
                    _call.setEncodingStyle(null);
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

    public ieci.tecdoc.sgm.entidades.ws.client.Entidad nuevaEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "nuevaEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidad) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidad) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.Entidad.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio eliminarEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "eliminarEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.entidades.ws.client.Entidad actualizarEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "actualizarEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidad) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidad) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.Entidad.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.entidades.ws.client.Entidad obtenerEntidad(java.lang.String identificador) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "obtenerEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {identificador});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidad) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidad) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.Entidad.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.entidades.ws.client.Entidades buscarEntidades(ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades poCriterio) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "buscarEntidades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poCriterio});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidades) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidades) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.Entidades.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.entidades.ws.client.Entidades obtenerEntidades() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "obtenerEntidades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidades) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.Entidades) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.Entidades.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena obtenerIdentificadorEntidad() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "obtenerIdentificadorEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
