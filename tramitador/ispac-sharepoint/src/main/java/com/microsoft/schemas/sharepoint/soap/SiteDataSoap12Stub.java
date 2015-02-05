/**
 * SiteDataSoap12Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class SiteDataSoap12Stub extends org.apache.axis.client.Stub implements com.microsoft.schemas.sharepoint.soap.SiteDataSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[12];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetSiteAndWeb");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strUrl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteAndWebResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strSite"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strWeb"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetSite");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sSiteMetadata"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sSiteMetadata"), com.microsoft.schemas.sharepoint.soap._sSiteMetadata.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vWebs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sWebWithTime"), com.microsoft.schemas.sharepoint.soap._sWebWithTime[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebWithTime"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strUsers"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strGroups"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vGroups"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOfString"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetWeb");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetWebResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sWebMetadata"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebMetadata"), com.microsoft.schemas.sharepoint.soap._sWebMetadata.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vWebs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sWebWithTime"), com.microsoft.schemas.sharepoint.soap._sWebWithTime[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebWithTime"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vLists"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sListWithTime"), com.microsoft.schemas.sharepoint.soap._sListWithTime[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListWithTime"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vFPUrls"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sFPUrl"), com.microsoft.schemas.sharepoint.soap._sFPUrl[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sFPUrl"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strRoles"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vRolesUsers"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOfString"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vRolesGroups"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOfString"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strListName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sListMetadata"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListMetadata"), com.microsoft.schemas.sharepoint.soap._sListMetadata.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vProperties"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sProperty"), com.microsoft.schemas.sharepoint.soap._sProperty[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sProperty"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetListItems");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strListName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strQuery"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strViewFields"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "uRowLimit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListItemsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("EnumerateFolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strFolderUrl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "EnumerateFolderResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vUrls"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sFPUrl"), com.microsoft.schemas.sharepoint.soap._sFPUrl[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sFPUrl"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAttachments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strListName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strItemId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetAttachmentsResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vAttachments"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOfString"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetURLSegments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strURL"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetURLSegmentsResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strWebID"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strBucketID"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strListID"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strItemID"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetListCollection");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListCollectionResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vLists"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sList"), com.microsoft.schemas.sharepoint.soap._sList[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sList"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetContent");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "objectType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ObjectType"), com.microsoft.schemas.sharepoint.soap.ObjectType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "objectId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "folderUrl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "itemId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "retrieveChildItems"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "securityOnly"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "lastItemIdOnPage"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetContentResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetSiteUrl");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Url"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteUrlResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "siteUrl"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "siteId"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetChanges");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "objectType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ObjectType"), com.microsoft.schemas.sharepoint.soap.ObjectType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "contentDatabaseId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "LastChangeId"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CurrentChangeId"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Timeout"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetChangesResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "moreChanges"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

    }

    public SiteDataSoap12Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SiteDataSoap12Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SiteDataSoap12Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sFPUrl");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sFPUrl.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sList");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListMetadata");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sListMetadata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListWithTime");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sListWithTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sProperty");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sProperty.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sSiteMetadata");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sSiteMetadata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebMetadata");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sWebMetadata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebWithTime");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sWebWithTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sFPUrl");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sFPUrl[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sFPUrl");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sFPUrl");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sList");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sList[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sList");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sList");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sListWithTime");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sListWithTime[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListWithTime");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListWithTime");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sProperty");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sProperty[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sProperty");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sProperty");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOf_sWebWithTime");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap._sWebWithTime[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebWithTime");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebWithTime");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ArrayOfString");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ObjectType");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.ObjectType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

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

    public void getSiteAndWeb(java.lang.String strUrl, org.apache.axis.holders.UnsignedIntHolder getSiteAndWebResult, javax.xml.rpc.holders.StringHolder strSite, javax.xml.rpc.holders.StringHolder strWeb) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetSiteAndWeb");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteAndWeb"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strUrl});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getSiteAndWebResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteAndWebResult"));
            } catch (java.lang.Exception _exception) {
                getSiteAndWebResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteAndWebResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                strSite.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strSite"));
            } catch (java.lang.Exception _exception) {
                strSite.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strSite")), java.lang.String.class);
            }
            try {
                strWeb.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strWeb"));
            } catch (java.lang.Exception _exception) {
                strWeb.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strWeb")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getSite(org.apache.axis.holders.UnsignedIntHolder getSiteResult, com.microsoft.schemas.sharepoint.soap.holders._sSiteMetadataHolder sSiteMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder vWebs, javax.xml.rpc.holders.StringHolder strUsers, javax.xml.rpc.holders.StringHolder strGroups, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vGroups) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetSite");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSite"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getSiteResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteResult"));
            } catch (java.lang.Exception _exception) {
                getSiteResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                sSiteMetadata.value = (com.microsoft.schemas.sharepoint.soap._sSiteMetadata) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sSiteMetadata"));
            } catch (java.lang.Exception _exception) {
                sSiteMetadata.value = (com.microsoft.schemas.sharepoint.soap._sSiteMetadata) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sSiteMetadata")), com.microsoft.schemas.sharepoint.soap._sSiteMetadata.class);
            }
            try {
                vWebs.value = (com.microsoft.schemas.sharepoint.soap._sWebWithTime[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vWebs"));
            } catch (java.lang.Exception _exception) {
                vWebs.value = (com.microsoft.schemas.sharepoint.soap._sWebWithTime[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vWebs")), com.microsoft.schemas.sharepoint.soap._sWebWithTime[].class);
            }
            try {
                strUsers.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strUsers"));
            } catch (java.lang.Exception _exception) {
                strUsers.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strUsers")), java.lang.String.class);
            }
            try {
                strGroups.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strGroups"));
            } catch (java.lang.Exception _exception) {
                strGroups.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strGroups")), java.lang.String.class);
            }
            try {
                vGroups.value = (java.lang.String[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vGroups"));
            } catch (java.lang.Exception _exception) {
                vGroups.value = (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vGroups")), java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getWeb(org.apache.axis.holders.UnsignedIntHolder getWebResult, com.microsoft.schemas.sharepoint.soap.holders._sWebMetadataHolder sWebMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder vWebs, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListWithTimeHolder vLists, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder vFPUrls, javax.xml.rpc.holders.StringHolder strRoles, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vRolesUsers, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vRolesGroups) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetWeb");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetWeb"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getWebResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetWebResult"));
            } catch (java.lang.Exception _exception) {
                getWebResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetWebResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                sWebMetadata.value = (com.microsoft.schemas.sharepoint.soap._sWebMetadata) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sWebMetadata"));
            } catch (java.lang.Exception _exception) {
                sWebMetadata.value = (com.microsoft.schemas.sharepoint.soap._sWebMetadata) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sWebMetadata")), com.microsoft.schemas.sharepoint.soap._sWebMetadata.class);
            }
            try {
                vWebs.value = (com.microsoft.schemas.sharepoint.soap._sWebWithTime[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vWebs"));
            } catch (java.lang.Exception _exception) {
                vWebs.value = (com.microsoft.schemas.sharepoint.soap._sWebWithTime[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vWebs")), com.microsoft.schemas.sharepoint.soap._sWebWithTime[].class);
            }
            try {
                vLists.value = (com.microsoft.schemas.sharepoint.soap._sListWithTime[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vLists"));
            } catch (java.lang.Exception _exception) {
                vLists.value = (com.microsoft.schemas.sharepoint.soap._sListWithTime[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vLists")), com.microsoft.schemas.sharepoint.soap._sListWithTime[].class);
            }
            try {
                vFPUrls.value = (com.microsoft.schemas.sharepoint.soap._sFPUrl[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vFPUrls"));
            } catch (java.lang.Exception _exception) {
                vFPUrls.value = (com.microsoft.schemas.sharepoint.soap._sFPUrl[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vFPUrls")), com.microsoft.schemas.sharepoint.soap._sFPUrl[].class);
            }
            try {
                strRoles.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strRoles"));
            } catch (java.lang.Exception _exception) {
                strRoles.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strRoles")), java.lang.String.class);
            }
            try {
                vRolesUsers.value = (java.lang.String[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vRolesUsers"));
            } catch (java.lang.Exception _exception) {
                vRolesUsers.value = (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vRolesUsers")), java.lang.String[].class);
            }
            try {
                vRolesGroups.value = (java.lang.String[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vRolesGroups"));
            } catch (java.lang.Exception _exception) {
                vRolesGroups.value = (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vRolesGroups")), java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getList(java.lang.String strListName, org.apache.axis.holders.UnsignedIntHolder getListResult, com.microsoft.schemas.sharepoint.soap.holders._sListMetadataHolder sListMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sPropertyHolder vProperties) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetList");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strListName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getListResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListResult"));
            } catch (java.lang.Exception _exception) {
                getListResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                sListMetadata.value = (com.microsoft.schemas.sharepoint.soap._sListMetadata) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sListMetadata"));
            } catch (java.lang.Exception _exception) {
                sListMetadata.value = (com.microsoft.schemas.sharepoint.soap._sListMetadata) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "sListMetadata")), com.microsoft.schemas.sharepoint.soap._sListMetadata.class);
            }
            try {
                vProperties.value = (com.microsoft.schemas.sharepoint.soap._sProperty[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vProperties"));
            } catch (java.lang.Exception _exception) {
                vProperties.value = (com.microsoft.schemas.sharepoint.soap._sProperty[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vProperties")), com.microsoft.schemas.sharepoint.soap._sProperty[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getListItems(java.lang.String strListName, java.lang.String strQuery, java.lang.String strViewFields, org.apache.axis.types.UnsignedInt uRowLimit) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetListItems");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListItems"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strListName, strQuery, strViewFields, uRowLimit});

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

    public void enumerateFolder(java.lang.String strFolderUrl, org.apache.axis.holders.UnsignedIntHolder enumerateFolderResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder vUrls) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/EnumerateFolder");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "EnumerateFolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strFolderUrl});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                enumerateFolderResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "EnumerateFolderResult"));
            } catch (java.lang.Exception _exception) {
                enumerateFolderResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "EnumerateFolderResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                vUrls.value = (com.microsoft.schemas.sharepoint.soap._sFPUrl[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vUrls"));
            } catch (java.lang.Exception _exception) {
                vUrls.value = (com.microsoft.schemas.sharepoint.soap._sFPUrl[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vUrls")), com.microsoft.schemas.sharepoint.soap._sFPUrl[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAttachments(java.lang.String strListName, java.lang.String strItemId, org.apache.axis.holders.UnsignedIntHolder getAttachmentsResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vAttachments) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetAttachments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetAttachments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strListName, strItemId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getAttachmentsResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetAttachmentsResult"));
            } catch (java.lang.Exception _exception) {
                getAttachmentsResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetAttachmentsResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                vAttachments.value = (java.lang.String[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vAttachments"));
            } catch (java.lang.Exception _exception) {
                vAttachments.value = (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vAttachments")), java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getURLSegments(java.lang.String strURL, javax.xml.rpc.holders.BooleanHolder getURLSegmentsResult, javax.xml.rpc.holders.StringHolder strWebID, javax.xml.rpc.holders.StringHolder strBucketID, javax.xml.rpc.holders.StringHolder strListID, javax.xml.rpc.holders.StringHolder strItemID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetURLSegments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetURLSegments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strURL});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getURLSegmentsResult.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetURLSegmentsResult"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                getURLSegmentsResult.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetURLSegmentsResult")), boolean.class)).booleanValue();
            }
            try {
                strWebID.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strWebID"));
            } catch (java.lang.Exception _exception) {
                strWebID.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strWebID")), java.lang.String.class);
            }
            try {
                strBucketID.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strBucketID"));
            } catch (java.lang.Exception _exception) {
                strBucketID.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strBucketID")), java.lang.String.class);
            }
            try {
                strListID.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strListID"));
            } catch (java.lang.Exception _exception) {
                strListID.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strListID")), java.lang.String.class);
            }
            try {
                strItemID.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strItemID"));
            } catch (java.lang.Exception _exception) {
                strItemID.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "strItemID")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getListCollection(org.apache.axis.holders.UnsignedIntHolder getListCollectionResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListHolder vLists) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetListCollection");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListCollection"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getListCollectionResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListCollectionResult"));
            } catch (java.lang.Exception _exception) {
                getListCollectionResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListCollectionResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                vLists.value = (com.microsoft.schemas.sharepoint.soap._sList[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vLists"));
            } catch (java.lang.Exception _exception) {
                vLists.value = (com.microsoft.schemas.sharepoint.soap._sList[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "vLists")), com.microsoft.schemas.sharepoint.soap._sList[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getContent(com.microsoft.schemas.sharepoint.soap.ObjectType objectType, java.lang.String objectId, java.lang.String folderUrl, java.lang.String itemId, boolean retrieveChildItems, boolean securityOnly, javax.xml.rpc.holders.StringHolder lastItemIdOnPage) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetContent");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetContent"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {objectType, objectId, folderUrl, itemId, new java.lang.Boolean(retrieveChildItems), new java.lang.Boolean(securityOnly), lastItemIdOnPage.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                lastItemIdOnPage.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "lastItemIdOnPage"));
            } catch (java.lang.Exception _exception) {
                lastItemIdOnPage.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "lastItemIdOnPage")), java.lang.String.class);
            }
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

    public void getSiteUrl(java.lang.String url, org.apache.axis.holders.UnsignedIntHolder getSiteUrlResult, javax.xml.rpc.holders.StringHolder siteUrl, javax.xml.rpc.holders.StringHolder siteId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetSiteUrl");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteUrl"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {url});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getSiteUrlResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteUrlResult"));
            } catch (java.lang.Exception _exception) {
                getSiteUrlResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetSiteUrlResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                siteUrl.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "siteUrl"));
            } catch (java.lang.Exception _exception) {
                siteUrl.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "siteUrl")), java.lang.String.class);
            }
            try {
                siteId.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "siteId"));
            } catch (java.lang.Exception _exception) {
                siteId.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "siteId")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getChanges(com.microsoft.schemas.sharepoint.soap.ObjectType objectType, java.lang.String contentDatabaseId, javax.xml.rpc.holders.StringHolder lastChangeId, javax.xml.rpc.holders.StringHolder currentChangeId, int timeout, javax.xml.rpc.holders.StringHolder getChangesResult, javax.xml.rpc.holders.BooleanHolder moreChanges) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetChanges");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetChanges"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {objectType, contentDatabaseId, lastChangeId.value, currentChangeId.value, new java.lang.Integer(timeout)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                lastChangeId.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "LastChangeId"));
            } catch (java.lang.Exception _exception) {
                lastChangeId.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "LastChangeId")), java.lang.String.class);
            }
            try {
                currentChangeId.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CurrentChangeId"));
            } catch (java.lang.Exception _exception) {
                currentChangeId.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CurrentChangeId")), java.lang.String.class);
            }
            try {
                getChangesResult.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetChangesResult"));
            } catch (java.lang.Exception _exception) {
                getChangesResult.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetChangesResult")), java.lang.String.class);
            }
            try {
                moreChanges.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "moreChanges"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                moreChanges.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "moreChanges")), boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
