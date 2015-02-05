/** 
 * CalendarioWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CalendarioWebService;

public class CalendarioWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[10];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "Calendario"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.Calendario.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "obtenerCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nuevoCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "calendario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "Calendario"), es.ieci.tecdoc.sigem.CalendarioWebService.Calendario.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "nuevoCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("actualizarCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "calendario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "Calendario"), es.ieci.tecdoc.sigem.CalendarioWebService.Calendario.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "actualizarCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "borrarDias"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "eliminarCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerDiaCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDia"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "obtenerDiaCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nuevoDiaCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "dia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDia"), es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDia"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "nuevoDiaCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("actualizarDiaCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "dia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDia"), es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "actualizarDiaCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarDiaCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "eliminarDiaCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerDiasCalendario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "tipo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDias"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "obtenerDiasCalendarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("proximoLaborable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "fecha"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "RetornoCalendario"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "proximoLaborableReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    public CalendarioWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public CalendarioWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public CalendarioWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.Entidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "ArrayOf_xsd_string");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.ArrayOf_xsd_string.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "ArrayOfCalendarioDia");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.ArrayOfCalendarioDia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "Calendario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.Calendario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDia");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDias");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "RetornoCalendario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario.class;
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

    public es.ieci.tecdoc.sigem.CalendarioWebService.Calendario obtenerCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "obtenerCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.Calendario) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.Calendario) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.Calendario.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio nuevoCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.Calendario calendario, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "nuevoCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {calendario, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio actualizarCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.Calendario calendario, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "actualizarCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {calendario, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio eliminarCalendario(boolean borrarDias, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "eliminarCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Boolean(borrarDias), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia obtenerDiaCalendario(java.lang.String id, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "obtenerDiaCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {id, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia nuevoDiaCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia dia, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "nuevoDiaCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dia, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio actualizarDiaCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia dia, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "actualizarDiaCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dia, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio eliminarDiaCalendario(java.lang.String id, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "eliminarDiaCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {id, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias obtenerDiasCalendario(int tipo, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "obtenerDiasCalendario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(tipo), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario proximoLaborable(java.lang.String fecha, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "proximoLaborable"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {fecha, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
