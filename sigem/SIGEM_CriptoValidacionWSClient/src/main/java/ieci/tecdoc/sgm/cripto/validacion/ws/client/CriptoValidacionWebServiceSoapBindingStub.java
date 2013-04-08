/**
 * CriptoValidacionWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.validacion.ws.client;

public class CriptoValidacionWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[6];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearHashByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poBase64Document"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Hash"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "crearHashByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearHash");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poBase64Document"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Hash"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "crearHashReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validaHashByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poBase64Document"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poB64Hash"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionHash"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validaHashByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarHash");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poBase64Document"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poB64Hash"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionHash"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validarHashReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarCertificadoByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poB64Certificate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionCertificado"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validarCertificadoByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarCertificado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "poB64Certificate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionCertificado"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validarCertificadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

    }

    public CriptoValidacionWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public CriptoValidacionWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public CriptoValidacionWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = ieci.tecdoc.sgm.cripto.validacion.ws.client.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Contenido");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "Hash");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "InfoCertificado");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.validacion.ws.client.InfoCertificado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionCertificado");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionHash");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash.class;
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

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash crearHashByEntidad(ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poBase64Document, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "crearHashByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poBase64Document, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash crearHash(ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poBase64Document) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "crearHash"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poBase64Document});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.validacion.ws.client.Hash.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash validaHashByEntidad(ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poBase64Document, ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poB64Hash, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validaHashByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poBase64Document, poB64Hash, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash validarHash(ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poBase64Document, ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poB64Hash) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validarHash"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poBase64Document, poB64Hash});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionHash.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado validarCertificadoByEntidad(ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poB64Certificate, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validarCertificadoByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poB64Certificate, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado validarCertificado(ieci.tecdoc.sgm.cripto.validacion.ws.client.Contenido poB64Certificate) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "validarCertificado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poB64Certificate});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.validacion.ws.client.ResultadoValidacionCertificado.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
