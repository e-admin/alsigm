/**
 * GeoLocalizacionWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class GeoLocalizacionWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.geolocalizacion.ws.client.GeoLocalizacionWebService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[13];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verPlanoPorCoordenadas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "datosLocalizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoCoordenadas"), ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoCoordenadas.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "URLsPlano"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorCoordenadasReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verPlanoPorReferenciaCatastral");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "datosLocalizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoReferenciaCatastral"), ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoReferenciaCatastral.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "URLsPlano"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorReferenciaCatastralReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verPlanoPorIdVia");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "datosLocalizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoVia"), ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoVia.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "URLsPlano"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorIdViaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verPlanoPorIdNumeroPolicia");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "datosLocalizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoPortal"), ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoPortal.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "URLsPlano"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorIdNumeroPoliciaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verPlanosPublicados");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINEMunicipio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Mapas"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanosPublicadosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarVia");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "nombreVia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINEMunicipio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Vias"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarViaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarPortal");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idVia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "numeroPortal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portales"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarPortalReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerPortal");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idPortal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portal"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerPortalReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarDireccionPostal");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "tipoVia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "nombreVia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "numeroPortal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINEMunicipio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "RetornoLogico"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarDireccionPostalReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validarDireccionPostalCompleta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "tipoVia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "nombreVia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "numeroPortal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINEMunicipio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Via"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Via.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarDireccionPostalCompletaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerProvincias");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Provincias"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerProvinciasReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerMunicipios");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idProvincia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Municipios"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerMunicipiosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerTiposDeVia");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "TiposVia"));
        oper.setReturnClass(ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerTiposDeViaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

    }

    public GeoLocalizacionWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public GeoLocalizacionWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public GeoLocalizacionWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfCoordenada");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenada[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenada");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfCoordenadas");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenadas[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenadas");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfMapa");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapa[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Mapa");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfMunicipio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipio[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Municipio");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfPortal");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portal");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfProvincia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincia[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Provincia");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfTipoVia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.TipoVia[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "TipoVia");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfVia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Via[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Via");
            qName2 = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ConjuntoCoordenadas");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.ConjuntoCoordenadas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenada");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenada.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenadas");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenadas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Mapa");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Mapas");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Municipio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Municipios");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlano");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlano.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoCoordenadas");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoCoordenadas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoPortal");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoPortal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoReferenciaCatastral");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoReferenciaCatastral.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoVia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoVia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portal");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portales");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Provincia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Provincias");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "RetornoLogico");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "TiposVia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "TipoVia");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.TipoVia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "URLsPlano");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Via");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Via.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Vias");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias.class;
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

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorCoordenadas(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoCoordenadas datosLocalizacion) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorCoordenadas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {datosLocalizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorReferenciaCatastral(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoReferenciaCatastral datosLocalizacion) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorReferenciaCatastral"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {datosLocalizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorIdVia(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoVia datosLocalizacion) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorIdVia"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {datosLocalizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorIdNumeroPolicia(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoPortal datosLocalizacion) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanoPorIdNumeroPolicia"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {datosLocalizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas verPlanosPublicados(int codigoINEMunicipio) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "verPlanosPublicados"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(codigoINEMunicipio)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias validarVia(java.lang.String nombreVia, int codigoINEMunicipio) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarVia"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {nombreVia, new java.lang.Integer(codigoINEMunicipio)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales validarPortal(int idVia, java.lang.String numeroPortal) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarPortal"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idVia), numeroPortal});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal obtenerPortal(int idPortal) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerPortal"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idPortal)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico validarDireccionPostal(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarDireccionPostal"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {tipoVia, nombreVia, numeroPortal, new java.lang.Integer(codigoINEMunicipio)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Via validarDireccionPostalCompleta(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "validarDireccionPostalCompleta"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {tipoVia, nombreVia, numeroPortal, new java.lang.Integer(codigoINEMunicipio)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Via) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Via) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Via.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias obtenerProvincias() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerProvincias"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios obtenerMunicipios(int idProvincia) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerMunicipios"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idProvincia)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia obtenerTiposDeVia() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "obtenerTiposDeVia"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
