/**
 * PagoTelematicoWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;

public class PagoTelematicoWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("altaLiquidacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poLiquidacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion"), ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "altaLiquidacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("bajaLiquidacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poLiquidacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion"), ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "bajaLiquidacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscarLiquidaciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poCriterio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "CriterioBusquedaLiquidacion"), ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ListaLiquidaciones"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "buscarLiquidacionesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscarTasas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poCriterio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "CriterioBusquedaTasa"), ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ListaTasas"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.ListaTasas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "buscarTasasReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("detallePago");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poPago"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Pago"), ieci.tecdoc.sgm.pe.ws.client.Pago.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Pago"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.Pago.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "detallePagoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("modificarLiquidacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poLiquidacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion"), ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "modificarLiquidacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerDatosTasa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poTasa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Tasa"), ieci.tecdoc.sgm.pe.ws.client.Tasa.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Tasa"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.Tasa.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "obtenerDatosTasaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerDocumentoPago");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poPago"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Pago"), ieci.tecdoc.sgm.pe.ws.client.Pago.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "DocumentoPago"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.DocumentoPago.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "obtenerDocumentoPagoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("realizarPago");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "poPago"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Pago"), ieci.tecdoc.sgm.pe.ws.client.Pago.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.pe.ws.client.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Pago"));
        oper.setReturnClass(ieci.tecdoc.sgm.pe.ws.client.Pago.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "realizarPagoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public PagoTelematicoWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public PagoTelematicoWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public PagoTelematicoWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = ieci.tecdoc.sgm.pe.ws.client.Entidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ArrayOfLiquidacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.Liquidacion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion");
            qName2 = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ArrayOfTasa");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.Tasa[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Tasa");
            qName2 = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "CriterioBusquedaLiquidacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "CriterioBusquedaTasa");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "DocumentoPago");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.DocumentoPago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ListaLiquidaciones");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ListaTasas");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.ListaTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Pago");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.Pago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Tasa");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.pe.ws.client.Tasa.class;
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

    public ieci.tecdoc.sgm.pe.ws.client.Liquidacion altaLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "altaLiquidacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poLiquidacion, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.Liquidacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.Liquidacion) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.RetornoServicio bajaLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "bajaLiquidacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poLiquidacion, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones buscarLiquidaciones(ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion poCriterio, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "buscarLiquidaciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poCriterio, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.ListaTasas buscarTasas(ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa poCriterio, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "buscarTasas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poCriterio, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.ListaTasas) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.ListaTasas) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.ListaTasas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.Pago detallePago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "detallePago"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poPago, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.Pago) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.Pago) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.Pago.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.Liquidacion modificarLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "modificarLiquidacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poLiquidacion, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.Liquidacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.Liquidacion) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.Liquidacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.Tasa obtenerDatosTasa(ieci.tecdoc.sgm.pe.ws.client.Tasa poTasa, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "obtenerDatosTasa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poTasa, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.Tasa) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.Tasa) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.Tasa.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.DocumentoPago obtenerDocumentoPago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "obtenerDocumentoPago"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poPago, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.DocumentoPago) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.DocumentoPago) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.DocumentoPago.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.pe.ws.client.Pago realizarPago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "realizarPago"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poPago, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.pe.ws.client.Pago) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.pe.ws.client.Pago) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.pe.ws.client.Pago.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
