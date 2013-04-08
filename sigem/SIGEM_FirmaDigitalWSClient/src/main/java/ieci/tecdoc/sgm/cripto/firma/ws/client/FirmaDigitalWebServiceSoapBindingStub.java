/**
 * FirmaDigitalWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class FirmaDigitalWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService {
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
        oper.setName("registrarFirma");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "certificate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "hash"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "RegistroFirma"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "registrarFirmaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registrarFirmaByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "certificate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "hash"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "RegistroFirma"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "registrarFirmaByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("firmar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "poContenido"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firma"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.Firma.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmarReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("firmarByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "poContenido"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firma"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.Firma.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmarByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarFirma");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "contenido"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ResultadoValidacionFirma"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "validarFirmaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarFirmaByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "contenido"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ResultadoValidacionFirma"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "validarFirmaByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCertInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "X509CertificadoInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "getCertInfoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCertInfoByEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"), ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idEntidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "X509CertificadoInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "getCertInfoByEntidadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

    }

    public FirmaDigitalWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public FirmaDigitalWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public FirmaDigitalWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmar");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Firmar.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmarByEntidad");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.FirmarByEntidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmarByEntidadResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.FirmarByEntidadResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmarResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.FirmarResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">getCertInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.GetCertInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">getCertInfoByEntidad");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.GetCertInfoByEntidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">getCertInfoByEntidadResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.GetCertInfoByEntidadResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">getCertInfoResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.GetCertInfoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">registrarFirmaByEntidad");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.RegistrarFirmaByEntidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">registrarFirmaByEntidadResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.RegistrarFirmaByEntidadResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirma");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.ValidarFirma.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirmaByEntidad");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.ValidarFirmaByEntidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirmaByEntidadResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.ValidarFirmaByEntidadResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirmaResponse");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.ValidarFirmaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ArrayOf_tns2_Certificate");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido");
            qName2 = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ArrayOfFirmante");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Firmante[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firmante");
            qName2 = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Certificado");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Certificado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firma");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Firma.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firmante");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.Firmante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "RegistroFirma");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ResultadoValidacionFirma");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "X509CertificadoInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo.class;
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

    public ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirma(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "registrarFirma"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {signature, certificate, hash});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirmaByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "registrarFirmaByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {signature, certificate, hash, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmar(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmar"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poContenido});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.Firma) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.Firma) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.Firma.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmarByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poContenido, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.Firma) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.Firma) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.Firma.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirma(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido contenido) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "validarFirma"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {signature, contenido});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido contenido, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "validarFirmaByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {signature, contenido, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfo(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "getCertInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {signature});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature, java.lang.String idEntidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "getCertInfoByEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {signature, idEntidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
