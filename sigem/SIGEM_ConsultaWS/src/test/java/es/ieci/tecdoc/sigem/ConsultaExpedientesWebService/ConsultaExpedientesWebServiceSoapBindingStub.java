/**
 * ConsultaExpedientesWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class ConsultaExpedientesWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[38];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultarExpedientesNIF");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poNif"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "CriterioBusquedaExpedientes"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expedientes"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "consultarExpedientesNIFReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultarExpedientes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poCriterio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "CriterioBusquedaExpedientes"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expedientes"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "consultarExpedientesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerURLAportacionExpedientes");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Url"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerURLAportacionExpedientesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerURLNotificacionExpedientes");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Url"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerURLNotificacionExpedientesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerURLPagoTasas");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Url"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerURLPagoTasasReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerDetalle");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poNumExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerDetalleReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerHistoricoExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poNumExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitosExpediente"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerHistoricoExpedienteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerHitoEstado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poNumExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerHitoEstadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerFicherosHito");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poGuidHito"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHito"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerFicherosHitoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerFicherosHitos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHitos"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitosExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHitos"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerFicherosHitosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nuevoExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poInteresado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Interesado"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nuevoExpedienteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarExpedienteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nuevoInteresado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poInteresado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Interesado"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nuevoInteresadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarInteresado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poInteresado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Interesado"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarInteresadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarInteresadoExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "numeroExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarInteresadoExpedienteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("establecerHitoActual");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHito"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poFicheros"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHito"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHistorico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Historico"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Historico.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "establecerHitoActualReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarHitoActual");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poNumExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarHitoActualReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nuevoHitoHistorico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHito"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nuevoHitoHistoricoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarHitoHistorico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHito"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarHitoHistoricoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cargarDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poInfodoc"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "InfoDocumento"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "InfoDocumento"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "cargarDocumentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recogerDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poInfodoc"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "InfoDocumento"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "InfoDocumento"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "recogerDocumentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("busquedaExpedientes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poCriterio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "CriterioBusquedaExpedientes"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expedientes"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "busquedaExpedientesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("anexarFicherosHitoActual");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poFicheros"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHito"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "anexarFicherosHitoActualReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recogerNotificaciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "NotificacionesPendientes"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "recogerNotificacionesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("altaSolicitudSubsanacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poSubsanacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanacion"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanacion.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "altaSolicitudSubsanacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerSubsanacionesHitoActual");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanaciones"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerSubsanacionesHitoActualReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerSubsanacionesHito");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHitoExp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanaciones"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerSubsanacionesHitoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("altaSolicitudPago");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poPago"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Pago"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pago.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "altaSolicitudPagoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerPagosHitoActual");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Pagos"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerPagosHitoActualReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerPagosHito");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHitoExp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Pagos"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerPagosHitoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("altaNotificacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poNotificacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificacion"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificacion.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "altaNotificacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerNotificionesHitoActual");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificaciones"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerNotificionesHitoActualReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerNotificionesHito");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poHitoExp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificaciones"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerNotificionesHitoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("existenNotificaciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "RetornoLogico"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "existenNotificacionesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("existenPagos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "RetornoLogico"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "existenPagosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("existenSubsanaciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "poExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "RetornoLogico"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "existenSubsanacionesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("actualizarEstadoLocalGIS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "idExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "estado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "RetornoLogico"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "actualizarEstadoLocalGISReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("publicarExpedienteLocalGIS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "idExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nif"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "tipoExpediente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "estado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fecha"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "RetornoLogico"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "publicarExpedienteLocalGISReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[37] = oper;

    }

    public ConsultaExpedientesWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ConsultaExpedientesWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ConsultaExpedientesWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfExpediente");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfExpediente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfFicheroHito");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfFicheroHito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfFicherosHito");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfFicherosHito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfHitoExpediente");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfHitoExpediente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfNotificacion");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfPago");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfPago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfSubsanacion");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfSubsanacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "CriterioBusquedaExpedientes");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expedientes");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicheroHito");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicheroHito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHito");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHitos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Historico");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Historico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitoExpediente");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "HitosExpediente");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "InfoDocumento");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Interesado");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificacion");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificaciones");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "NotificacionesPendientes");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Pago");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Pagos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "RetornoLogico");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanacion");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanaciones");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Url");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class;
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

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes consultarExpedientesNIF(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes poNif, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "consultarExpedientesNIF"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poNif, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes consultarExpedientes(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes poCriterio, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "consultarExpedientes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poCriterio, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url obtenerURLAportacionExpedientes() throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerURLAportacionExpedientes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url obtenerURLNotificacionExpedientes() throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerURLNotificacionExpedientes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url obtenerURLPagoTasas() throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerURLPagoTasas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente obtenerDetalle(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poNumExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerDetalle"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poNumExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente obtenerHistoricoExpediente(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poNumExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerHistoricoExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poNumExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente obtenerHitoEstado(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poNumExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerHitoEstado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poNumExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito obtenerFicherosHito(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poGuidHito, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerFicherosHito"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poGuidHito, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos obtenerFicherosHitos(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente poHitos, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerFicherosHitos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHitos, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio nuevoExpediente(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado poInteresado, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nuevoExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, poInteresado, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio eliminarExpediente(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio nuevoInteresado(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado poInteresado, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nuevoInteresado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poInteresado, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio eliminarInteresado(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado poInteresado, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarInteresado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poInteresado, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio eliminarInteresadoExpediente(java.lang.String numeroExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarInteresadoExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {numeroExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio establecerHitoActual(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poHito, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito poFicheros, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Historico poHistorico, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "establecerHitoActual"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHito, poFicheros, poHistorico, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio eliminarHitoActual(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poNumExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarHitoActual"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poNumExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio nuevoHitoHistorico(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poHito, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nuevoHitoHistorico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHito, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio eliminarHitoHistorico(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poHito, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "eliminarHitoHistorico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHito, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento cargarDocumento(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento poInfodoc, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "cargarDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poInfodoc, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento recogerDocumento(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento poInfodoc, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "recogerDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poInfodoc, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes busquedaExpedientes(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes poCriterio, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "busquedaExpedientes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poCriterio, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio anexarFicherosHitoActual(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito poFicheros, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "anexarFicherosHitoActual"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poFicheros, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes recogerNotificaciones(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "recogerNotificaciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio altaSolicitudSubsanacion(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanacion poSubsanacion, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "altaSolicitudSubsanacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poSubsanacion, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones obtenerSubsanacionesHitoActual(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerSubsanacionesHitoActual"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones obtenerSubsanacionesHito(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poHitoExp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerSubsanacionesHito"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHitoExp, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio altaSolicitudPago(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pago poPago, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "altaSolicitudPago"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poPago, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos obtenerPagosHitoActual(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerPagosHitoActual"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos obtenerPagosHito(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poHitoExp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerPagosHito"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHitoExp, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio altaNotificacion(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificacion poNotificacion, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "altaNotificacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poNotificacion, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones obtenerNotificionesHitoActual(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerNotificionesHitoActual"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones obtenerNotificionesHito(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente poHitoExp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "obtenerNotificionesHito"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poHitoExp, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico existenNotificaciones(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "existenNotificaciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico existenPagos(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "existenPagos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico existenSubsanaciones(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente poExpediente, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "existenSubsanaciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {poExpediente, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico actualizarEstadoLocalGIS(java.lang.String idExpediente, java.lang.String estado, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "actualizarEstadoLocalGIS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idExpediente, estado, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico publicarExpedienteLocalGIS(java.lang.String idExpediente, java.lang.String nif, java.lang.String tipoExpediente, java.lang.String estado, java.lang.String fecha, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "publicarExpedienteLocalGIS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idExpediente, nif, tipoExpediente, estado, fecha, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
