/**
 * ScspwsSoap11Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.Handler;

public class ScspwsSoap11Stub extends org.apache.axis.client.Stub implements es.redsara.intermediacion.scsp.esquemas.ws.Scspws {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("peticionSincrona");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "PeticionSincrona"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionSincrona"), es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionSincrona.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Respuesta"));
        oper.setReturnClass(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Respuesta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "fault"),
                      "es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault",
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("peticionAsincrona");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "PeticionAsincrona"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionAsincrona"), es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionAsincrona.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">ConfirmacionPeticion"));
        oper.setReturnClass(es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "ConfirmacionPeticion"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "fault"),
                      "es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault",
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("peticionPdf");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "PeticionPdf"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionPdf"), es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionPdf.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">RespuestaPdf"));
        oper.setReturnClass(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "RespuestaPdf"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "fault"),
                      "es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault",
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("peticionRecover");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "PeticionRecover"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionRecover"), es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionRecover.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Respuesta"));
        oper.setReturnClass(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Respuesta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "fault"),
                      "es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault",
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("solicitudRespuesta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", "SolicitudRespuesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">SolicitudRespuesta"), es.redsara.intermediacion.scsp.esquemas.ws.solicitudRespuesta.SolicitudRespuesta.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Respuesta"));
        oper.setReturnClass(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Respuesta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "fault"),
                      "es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault",
                      new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault"), 
                      true
                     ));
        _operations[4] = oper;

    }

    public ScspwsSoap11Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ScspwsSoap11Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ScspwsSoap11Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
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
            java.lang.Class documentsf = org.apache.axis.encoding.ser.DocumentSerializerFactory.class;
            java.lang.Class documentdf = org.apache.axis.encoding.ser.DocumentDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">>Estado>CodigoEstado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">>Estado>CodigoEstadoSecundario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">>Estado>LiteralError");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">>Estado>TiempoEstimadoRespuesta");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">Atributos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Atributos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">CodigoCertificado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">ConfirmacionPeticion");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">Estado");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Estado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">IdPeticion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">NumElementos");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">TimeStamp");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">>fault>CodigoCertificado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">>fault>CodigoEstado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">>fault>IdPeticion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">>fault>LiteralError");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">>fault>TimeStamp");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Estado>CodigoEstado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Estado>CodigoEstadoSecundario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Estado>LiteralError");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Estado>TiempoEstimadoRespuesta");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Funcionario>NifFuncionario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Funcionario>NombreCompletoFuncionario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Procedimiento>CodProcedimiento");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Procedimiento>NombreProcedimiento");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>SolicitudTransmision>DatosGenericos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Titular>Apellido1");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Titular>Apellido2");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Titular>Documentacion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Titular>Nombre");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>Titular>NombreCompleto");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Atributos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">CodigoCertificado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Consentimiento");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Consentimiento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Estado");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Estado.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Finalidad");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Funcionario");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Funcionario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">IdentificadorSolicitante");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">IdExpediente");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">IdPeticion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">NombreSolicitante");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">NumElementos");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionAsincrona");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionAsincrona.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionPdf");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionPdf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionRecover");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionRecover.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionSincrona");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionSincrona.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Procedimiento");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Procedimiento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Solicitante");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Solicitudes");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "SolicitudTransmision");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">SolicitudTransmision");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">TimeStamp");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">TipoDocumentacion");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Titular");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">UnidadTramitadora");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>>Transmisiones>TransmisionDatos>DatosGenericos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatosDatosGenericos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>ConfirmacionPeticion>Atributos");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.ConfirmacionPeticionAtributos.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Emisor>NifEmisor");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Emisor>NombreEmisor");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Estado>CodigoEstado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Estado>CodigoEstadoSecundario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Estado>LiteralError");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Estado>TiempoEstimadoRespuesta");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Funcionario>NifFuncionario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Funcionario>NombreCompletoFuncionario");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Procedimiento>CodProcedimiento");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Procedimiento>NombreProcedimiento");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Respuesta>Atributos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaAtributos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>SolicitudRespuesta>Atributos");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.SolicitudRespuestaAtributos.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);
//
//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>SolicitudTransmision>DatosGenericos");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.SolicitudTransmisionDatosGenericos.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Titular>Apellido1");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Titular>Apellido2");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Titular>Documentacion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Titular>Nombre");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Titular>NombreCompleto");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmision>CodigoCertificado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmision>FechaGeneracion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmision>IdSolicitud");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmision>IdTransmision");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmisiones>TransmisionDatos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Atributos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Atributos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">CodigoCertificado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">ConfirmacionPeticion");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.ConfirmacionPeticion.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Consentimiento");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Consentimiento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Emisor");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Emisor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Estado");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Estado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Finalidad");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Funcionario");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Funcionario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">IdentificadorSolicitante");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">IdExpediente");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">IdPeticion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">NombreSolicitante");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">NumElementos");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">PeticionAsincrona");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.PeticionAsincrona.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);
//
//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">PeticionSincrona");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.PeticionSincrona.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Procedimiento");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Procedimiento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Respuesta");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">RespuestaPdf");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Solicitante");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Solicitante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Solicitudes");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.SolicitudTransmision[].class;
//            cachedSerClasses.add(cls);
//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "SolicitudTransmision");
//            qName2 = null;
//            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
//            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
//
//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">SolicitudRespuesta");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.SolicitudRespuesta.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);
            
//            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"); 
//            cachedSerQNames.add(qName); 
//            cls = java.lang.Object.class; 
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf); 
//            cachedDeserFactories.add(beandf);
            //cachedSerFactories.add(simplesf); 
            //cachedDeserFactories.add(simpledf);
            //org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName)

    }
    private void addBindings1() {
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
//            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">SolicitudTransmision");
//            cachedSerQNames.add(qName);
//            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.SolicitudTransmision.class;
//            cachedSerClasses.add(cls);
//            cachedSerFactories.add(beansf);
//            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">TimeStamp");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">TipoDocumentacion");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TipoDocumentacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Titular");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Titular.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Transmision");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Transmision.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Transmisiones");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatos[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmisiones>TransmisionDatos");
            qName2 = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "TransmisionDatos");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">UnidadTramitadora");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">Atributos");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.solicitudRespuesta.Atributos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">CodigoCertificado");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">IdPeticion");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">NumElementos");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">SolicitudRespuesta");
            cachedSerQNames.add(qName);
            cls = es.redsara.intermediacion.scsp.esquemas.ws.solicitudRespuesta.SolicitudRespuesta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            
//            ArrayList handlersInfo=new ArrayList();
//            HandlerInfo hInfo=new HandlerInfo(ResponseHandler.class,null,null);
//            handlersInfo.add(hInfo);
//            _call.getService().getHandlerRegistry().setHandlerChain(new QName("piPort"), handlersInfo);
            
            //ResponseHandler handler=new ResponseHandler();
            //_call.setClientHandlers(new HTTPSender(), handler);
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

    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta peticionSincrona(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionSincrona peticionSincrona) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("peticionSincrona");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "peticionSincrona"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {peticionSincrona});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta) org.apache.axis.utils.JavaUtils.convert(_resp, es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class);
            }
        }
  } catch (AxisFault axisFaultException) {
	  tratarExcepcion(axisFaultException);
  throw axisFaultException;
}
    }

    public es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion peticionAsincrona(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionAsincrona peticionAsincrona) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("peticionAsincrona");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "peticionAsincrona"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {peticionAsincrona});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion) org.apache.axis.utils.JavaUtils.convert(_resp, es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
	  tratarExcepcion(axisFaultException);
  throw axisFaultException;
}
    }

    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf peticionPdf(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionPdf peticionPdf) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("peticionPdf");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "peticionPdf"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {peticionPdf});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf) org.apache.axis.utils.JavaUtils.convert(_resp, es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
	  tratarExcepcion(axisFaultException);
  throw axisFaultException;
}
    }

    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta peticionRecover(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionRecover peticionRecover) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("peticionRecover");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "peticionRecover"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {peticionRecover});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta) org.apache.axis.utils.JavaUtils.convert(_resp, es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
	  tratarExcepcion(axisFaultException);
  throw axisFaultException;
}
    }

    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta solicitudRespuesta(es.redsara.intermediacion.scsp.esquemas.ws.solicitudRespuesta.SolicitudRespuesta solicitudRespuesta) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("solicitudRespuesta");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "solicitudRespuesta"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudRespuesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta) org.apache.axis.utils.JavaUtils.convert(_resp, es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
	  tratarExcepcion(axisFaultException);
	  throw axisFaultException;
  }
    }
    
    private Handler getRequestHandler(org.apache.axis.client.Call _call){
    	//_call.getClass().getField("myservice");
    	return null;
    }
    
    private void tratarExcepcion(org.apache.axis.AxisFault axisFaultException) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault{
    	if (axisFaultException.detail != null) {
	        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
	              throw (java.rmi.RemoteException) axisFaultException.detail;
	         }
	        if (axisFaultException.detail instanceof es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault) {
	            throw (es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault) axisFaultException.detail;
	        }
	        if (axisFaultException.detail.getCause() instanceof es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault) {
	              throw (es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault) axisFaultException.detail.getCause();
	         }
	   }
    }

}
