/**
 * ServicioRegistroWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class ServicioRegistroWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[30];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "createFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "updateFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("importFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "importFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consolidateFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "consolidateFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folderQuery"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegistersInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "findFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInputFolderForNumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInputFolderForNumberReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOutputFolderForNumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOutputFolderForNumberReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOutputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOutputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDocumentFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "document"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document"), ieci.tecdoc.sgm.registropresencial.ws.server.Document.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.Document.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getDocumentFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionsInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOutputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionsInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOutputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("countInputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "countInputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("countOutputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "countOutputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("acceptDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "acceptDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("acceptDistributionEx");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "acceptDistributionExReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("archiveDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "archiveDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("archiveDistributionEx");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "archiveDistributionExReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("rejectDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "rejectDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("rejectDistributionEx");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "rejectDistributionExReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changeInputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "changeInputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changeOutputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "changeOutputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addDocument");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "addDocumentReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInterestedInputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ListaPersonInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInterestedInputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInterestedOutputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ListaPersonInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInterestedOutputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("canCreateRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "canCreateRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getBooksForType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BooksInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getBooksForTypeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOfficeCanCreateRegisterByUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficesInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOfficeCanCreateRegisterByUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("existMatterTypeInOffice");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "matterTypeCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "officeCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.core.services.dto.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "existMatterTypeInOfficeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    public ServicioRegistroWebServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ServicioRegistroWebServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ServicioRegistroWebServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = ieci.tecdoc.sgm.core.services.dto.Entidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfBookInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookInfo");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfDistributionInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfDocument");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Document[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfDocumentsWithPage");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DocumentsWithPage[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DocumentsWithPage");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfFieldInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfo");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfFieldInfoSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfoSearchCriteria[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfoSearchCriteria");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfOfficeInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficeInfo");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPage");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Page[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Page");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfRegisterInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo");
            qName2 = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookId");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.BookId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BooksInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionsInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Document.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Documents");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Documents.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DocumentsWithPage");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.DocumentsWithPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfoSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfoSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Fields");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Fields.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldsSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ListaPersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficeInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficesInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Page");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.Page.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegistersInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo.class;
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

    public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo getDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, distributionInfo, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo createFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "createFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, inter, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo updateFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "updateFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, inter, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo importFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "importFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, inter, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo consolidateFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "consolidateFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, inter, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo findFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria folderQuery, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "findFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folderQuery, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.Folder getInputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInputFolderForNumber"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo getInputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInputRegister"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.Folder getOutputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOutputFolderForNumber"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.Folder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo getOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOutputRegister"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.Document getDocumentFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Document document, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getDocumentFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, document, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.Document) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.Document) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.Document.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo getInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInputDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, criteria, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo getOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOutputDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, criteria, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo countInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "countInputDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, criteria, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo countOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "countOutputDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, criteria, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio acceptDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "acceptDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio acceptDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "acceptDistributionEx"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, distributionInfo, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio archiveDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "archiveDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio archiveDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "archiveDistributionEx"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, distributionInfo, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio rejectDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "rejectDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, options, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio rejectDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "rejectDistributionEx"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, distributionInfo, options, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio changeInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "changeInputDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, options, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio changeOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "changeOutputDistribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, options, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.core.services.dto.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.core.services.dto.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio addDocument(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "addDocument"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo getInterestedInputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInterestedInputRegister"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo getInterestedOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getInterestedOutputRegister"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio canCreateRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "canCreateRegister"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, criteria, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo getBooksForType(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getBooksForType"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, criteria, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo getOfficeCanCreateRegisterByUser(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "getOfficeCanCreateRegisterByUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, folder, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio existMatterTypeInOffice(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, java.lang.String matterTypeCode, java.lang.String officeCode, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "existMatterTypeInOffice"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {user, matterTypeCode, officeCode, entidad});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
