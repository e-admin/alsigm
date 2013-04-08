/**
 * ServicioRegistroWebServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.client.axis;

import java.rmi.RemoteException;

public class ServicioRegistroWebServiceSoapBindingStub extends org.apache.axis.client.Stub implements ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType {
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
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "createFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "updateFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("importFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "importFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consolidateFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "inter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPersonInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "consolidateFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folderQuery"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegistersInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInputFolderForNumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getInputFolderForNumberReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getInputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOutputFolderForNumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getOutputFolderForNumberReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOutputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getOutputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDocumentFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "document"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getDocumentFolderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionsInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getInputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOutputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionsInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getOutputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("countInputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "countInputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("countOutputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "countOutputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("acceptDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "acceptDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("acceptDistributionEx");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "acceptDistributionExReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("archiveDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "archiveDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("archiveDistributionEx");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "archiveDistributionExReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("rejectDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "rejectDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("rejectDistributionEx");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "distributionInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "rejectDistributionExReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changeInputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "changeInputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changeOutputDistribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "changeOutputDistributionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addDocument");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "addDocumentReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInterestedInputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getInterestedInputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInterestedOutputRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getInterestedOutputRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("canCreateRegister");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "canCreateRegisterReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getBooksForType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "criteria"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BooksInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getBooksForTypeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOfficeCanCreateRegisterByUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "folder"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficesInfo"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getOfficeCanCreateRegisterByUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("existMatterTypeInOffice");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "matterTypeCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "officeCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entidad"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "Entidad"), ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio"));
        oper.setReturnClass(ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "existMatterTypeInOfficeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
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
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.services.core.sgm.tecdoc.ieci", "RetornoServicio");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfBookInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfDistributionInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfDocument");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfDocumentsWithPage");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DocumentsWithPage[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DocumentsWithPage");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfFieldInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfFieldInfoSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfoSearchCriteria[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfoSearchCriteria");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfOfficeInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficeInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficeInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPage");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Page[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Page");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfPersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ArrayOfRegisterInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookId");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BooksInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionOptions");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionsInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Documents");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Documents.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DocumentsWithPage");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.DocumentsWithPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldInfoSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfoSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Fields");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Fields.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldsSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldsSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderSearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficeInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficeInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "OfficesInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Page");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.Page.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegistersInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "UserInfo");
            cachedSerQNames.add(qName);
            cls = ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo.class;
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

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo getDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo createFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo updateFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo importFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo consolidateFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo findFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria folderQuery, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getInputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo getInputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getOutputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo getOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document getDocumentFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document document, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo getInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo getOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo countInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo countOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio acceptDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio acceptDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio archiveDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio archiveDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio rejectDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio rejectDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio changeInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio changeOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio addDocument(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio getInterestedInputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio getInterestedOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio canCreateRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria criteria, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo getBooksForType(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria criteria, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo getOfficeCanCreateRegisterByUser(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio existMatterTypeInOffice(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user, java.lang.String matterTypeCode, java.lang.String officeCode, ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException {
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
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) _resp;
            } catch (java.lang.Exception _exception) {
                return (ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio) org.apache.axis.utils.JavaUtils.convert(_resp, ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
