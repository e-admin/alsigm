/**
 * ServicioNotificacionesEndPointBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client;

public class ServicioNotificacionesEndPointBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint {
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
        oper.setName("calcularAnagrama");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaEstadoNotificacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Long_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "EstadoNotificacion"));
        oper.setReturnClass(ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaSuscripcion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "InfoSuscripcion"));
        oper.setReturnClass(ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaSuscripcionSimple");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearNotificacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "AltaNotificacion_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacion"), ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion"));
        oper.setReturnClass(ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearNotificacionMultidestinatario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "AltaNotificacionMultidestinatario_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacionMultidestinatario"), ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion.Array"));
        oper.setReturnClass(ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearNotificacionMultidestinatarioSimple");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arrayOfString_2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "String.Array"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_5"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_6"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_7"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_8"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_9"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_10"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_11"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "String.Array"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearNotificacionSimple");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_5"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_6"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_7"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_8"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_9"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_10"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "String_11"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearNotificaciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arrayOfAltaNotificacion_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacion.Array"), ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion.Array"));
        oper.setReturnClass(ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearNotificacionesMultidestinatario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arrayOfAltaNotificacionMultidestinatario_1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacionMultidestinatario.Array"), ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion.Array.Array"));
        oper.setReturnClass(ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    public ServicioNotificacionesEndPointBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ServicioNotificacionesEndPointBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ServicioNotificacionesEndPointBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "ConfirmacionAltaNotificacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "ConfirmacionAltaNotificacion.Array");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion");
            qName2 = new javax.xml.namespace.QName("", "value");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "InfoAltaNotificacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.InfoAltaNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacion.Array");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacion");
            qName2 = new javax.xml.namespace.QName("", "value");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacionMultidestinatario");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacionMultidestinatario.Array");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacionMultidestinatario");
            qName2 = new javax.xml.namespace.QName("", "value");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion.Array");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "ConfirmacionAltaNotificacion");
            qName2 = new javax.xml.namespace.QName("", "value");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "ConfirmacionAltaNotificacion.Array.Array");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "ConfirmacionAltaNotificacion.Array");
            qName2 = new javax.xml.namespace.QName("", "value");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "EstadoNotificacion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "InfoSuscripcion");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "String.Array");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "value");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

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

    public java.lang.String calcularAnagrama(java.lang.String string_1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "calcularAnagrama"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {string_1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion consultaEstadoNotificacion(long long_1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "consultaEstadoNotificacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(long_1)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion consultaSuscripcion(java.lang.String string_1, java.lang.String string_2) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "consultaSuscripcion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {string_1, string_2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String consultaSuscripcionSimple(java.lang.String string_1, java.lang.String string_2) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "consultaSuscripcionSimple"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {string_1, string_2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion crearNotificacion(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion altaNotificacion_1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "crearNotificacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {altaNotificacion_1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[] crearNotificacionMultidestinatario(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario altaNotificacionMultidestinatario_1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "crearNotificacionMultidestinatario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {altaNotificacionMultidestinatario_1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[]) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String[] crearNotificacionMultidestinatarioSimple(java.lang.String string_1, java.lang.String[] arrayOfString_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.lang.String string_6, java.lang.String string_7, java.lang.String string_8, java.lang.String string_9, java.lang.String string_10, java.lang.String string_11) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "crearNotificacionMultidestinatarioSimple"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {string_1, arrayOfString_2, string_3, string_4, string_5, string_6, string_7, string_8, string_9, string_10, string_11});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String crearNotificacionSimple(java.lang.String string_1, java.lang.String string_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.lang.String string_6, java.lang.String string_7, java.lang.String string_8, java.lang.String string_9, java.lang.String string_10, java.lang.String string_11) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "crearNotificacionSimple"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {string_1, string_2, string_3, string_4, string_5, string_6, string_7, string_8, string_9, string_10, string_11});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[] crearNotificaciones(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion[] arrayOfAltaNotificacion_1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "crearNotificaciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arrayOfAltaNotificacion_1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[]) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][] crearNotificacionesMultidestinatario(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario[] arrayOfAltaNotificacionMultidestinatario_1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "crearNotificacionesMultidestinatario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arrayOfAltaNotificacionMultidestinatario_1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][]) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][]) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
