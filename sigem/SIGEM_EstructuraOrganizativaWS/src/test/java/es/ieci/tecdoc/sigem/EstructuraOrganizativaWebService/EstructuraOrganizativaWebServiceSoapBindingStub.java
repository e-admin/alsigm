/**
 * EstructuraOrganizativaWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService;

public class EstructuraOrganizativaWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[36];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosDepartamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idDept"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosDepartamentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioPorId");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idUser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioPorIdReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioPorNombre");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "nameUser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioPorNombreReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosAplicacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "sinPermisos"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuarios"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "superusuarios"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosAplicacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosGrupo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idGrupo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosGrupoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDepartamentosPorPadre");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idDeptPadre"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamentos"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentosPorPadreReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getGrupos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Grupos"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGruposReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDepartamentos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamentos"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getGrupo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idGrupo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Grupo"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDepartamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idDepto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamento"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearArchivador");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "archive"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Archive"), es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Archive.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "RetornoEntero"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "crearArchivadorReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("editarDepartamentoCompleto");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "departamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamento"), es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "editarDepartamentoCompletoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("editarUsuario");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario"), es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "editarUsuarioReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarArchivador");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "archiveId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "eliminarArchivadorReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getListas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Listas"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getListasReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarios");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idsUser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "superusers"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosAsociation");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "deptId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "superusers"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosAsociationReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosYaAsociados");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idsUser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idOfic"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosYaAsociadosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosAplicacionPorDepartamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "deptId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "sinPermisos"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "users"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "superusuarios"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosAplicacionPorDepartamentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearGrupoLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ldapGuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ldapDn"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "type"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GrupoLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "crearGrupoLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getGrupoLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ldapGuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GrupoLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupoLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearUsuarioLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ldapGuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "name"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idPerfil"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "crearUsuarioLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioLdapByGuid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ldapguid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapByGuidReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosLdapYaAsociados");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idsUser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idOfic"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapYaAsociadosReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("editarUsuarioLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuarioLdap"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"), es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "editarUsuarioLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminarUsuarioLdap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "eliminarUsuarioLdapReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioLdapBasicById");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "RetornoCadena"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapBasicByIdReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDepartamentoLite");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamento"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentoLiteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getGrupoLdapById");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GrupoLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupoLdapByIdReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosLdapAplicacionPorDepartamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "deptId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "sinPermisos"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuarios"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "superusuarios"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapAplicacionPorDepartamentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuariosLdapAplicacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idsUser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "superusers"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapAplicacionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioPorIdCertificado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idCert"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioPorIdCertificadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioLdapPorIdCertificado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idCert"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapPorIdCertificadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUsuarioLdapByFullName");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "fullName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
        oper.setReturnClass(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapByFullNameReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[35] = oper;

    }

    public EstructuraOrganizativaWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public EstructuraOrganizativaWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public EstructuraOrganizativaWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Archive");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Archive.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveFld");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArchiveFld.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveFlds");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArchiveFlds.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveIdx");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArchiveIdx.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveIdxs");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArchiveIdxs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveMisc");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArchiveMisc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOf_xsd_int");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOf_xsd_int.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfArchiveFld");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfArchiveFld.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfArchiveIdx");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfArchiveIdx.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfDepartamento");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfDepartamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfGrupo");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfGrupo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfLista");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfLista.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfPerfilUsuario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfPerfilUsuario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfPermiso");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfPermiso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfUsuario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfUsuarioBasico");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuarioBasico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfUsuarioLdap");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuarioLdap.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamento");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Departamentos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "DepartamentosLista");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.DepartamentosLista.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Grupo");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GrupoLdap");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Grupos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GruposLista");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GruposLista.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Lista");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Lista.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Listas");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesGenericos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilesGenericos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesUsuario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilesUsuario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilUsuario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilUsuario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Permiso");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Permiso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Permisos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Permisos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PermisosGenericos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PermisosGenericos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "RetornoCadena");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "RetornoEntero");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioBasico");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioBasico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosBasicos");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosBasicos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosLdap");
            cachedSerQNames.add(qName);
            cls = es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class;
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

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosDepartamento(int idDept, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosDepartamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idDept), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario getUsuarioPorId(int idUser, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioPorId"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idUser), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario getUsuarioPorNombre(java.lang.String nameUser, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioPorNombre"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {nameUser, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosAplicacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(aplicacion), new java.lang.Boolean(sinPermisos), new java.lang.Boolean(usuarios), new java.lang.Boolean(superusuarios), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosGrupo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idGrupo), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos getDepartamentosPorPadre(int idDeptPadre, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentosPorPadre"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idDeptPadre), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos getGrupos(java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos getDepartamentos(java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo getGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idGrupo), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento getDepartamento(int idDepto, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(idDepto), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero crearArchivador(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Archive archive, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "crearArchivador"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {archive, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio editarDepartamentoCompleto(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento departamento, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "editarDepartamentoCompleto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {departamento, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio editarUsuario(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario usuario, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "editarUsuario"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {usuario, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio eliminarArchivador(int userId, int archiveId, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "eliminarArchivador"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(userId), new java.lang.Integer(archiveId), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas getListas(java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getListas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuarios(int aplicacion, int[] idsUser, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarios"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(aplicacion), idsUser, new java.lang.Boolean(superusers), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosAsociation(int aplicacion, int deptId, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosAsociation"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(aplicacion), new java.lang.Integer(deptId), new java.lang.Boolean(superusers), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosYaAsociados(int[] idsUser, int idOfic, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosYaAsociados"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idsUser, new java.lang.Integer(idOfic), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion, int[] deptId, boolean sinPermisos, boolean users, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosAplicacionPorDepartamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(aplicacion), deptId, new java.lang.Boolean(sinPermisos), new java.lang.Boolean(users), new java.lang.Boolean(superusuarios), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap crearGrupoLdap(java.lang.String ldapGuid, java.lang.String ldapDn, int type, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "crearGrupoLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ldapGuid, ldapDn, new java.lang.Integer(type), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap getGrupoLdap(java.lang.String ldapGuid, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupoLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ldapGuid, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap crearUsuarioLdap(java.lang.String ldapGuid, java.lang.String name, int idPerfil, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "crearUsuarioLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ldapGuid, name, new java.lang.Integer(idPerfil), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdapByGuid(java.lang.String ldapguid, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapByGuid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ldapguid, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdap(java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdap(int id, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(id), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdapYaAsociados(int[] idsUser, int idOfic, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapYaAsociados"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idsUser, new java.lang.Integer(idOfic), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio editarUsuarioLdap(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap usuarioLdap, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "editarUsuarioLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {usuarioLdap, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio eliminarUsuarioLdap(int id, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "eliminarUsuarioLdap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(id), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena getUsuarioLdapBasicById(int id, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapBasicById"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(id), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento getDepartamentoLite(int arg0, java.lang.String arg1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getDepartamentoLite"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(arg0), arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap getGrupoLdapById(int arg0, java.lang.String arg1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getGrupoLdapById"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(arg0), arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(int aplicacion, int[] deptId, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapAplicacionPorDepartamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(aplicacion), deptId, new java.lang.Boolean(sinPermisos), new java.lang.Boolean(usuarios), new java.lang.Boolean(superusuarios), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdapAplicacion(int aplicacion, int[] idsUser, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuariosLdapAplicacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(aplicacion), idsUser, new java.lang.Boolean(superusers), entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario getUsuarioPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioPorIdCertificado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idCert, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdapPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapPorIdCertificado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idCert, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdapByFullName(java.lang.String fullName, java.lang.String entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "getUsuarioLdapByFullName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {fullName, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap) org.apache.axis.utils.JavaUtils.convert(_resp, es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }
}
