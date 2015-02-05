/**
 * CopySoap12Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class CopySoap12Stub extends org.apache.axis.client.Stub implements com.microsoft.schemas.sharepoint.soap.CopySoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CopyIntoItemsLocal");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "SourceUrl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "DestinationUrls"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "DestinationUrlCollection"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsLocalResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Results"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResultCollection"), com.microsoft.schemas.sharepoint.soap.CopyResult[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResult"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CopyIntoItems");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "SourceUrl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "DestinationUrls"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "DestinationUrlCollection"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Fields"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformationCollection"), com.microsoft.schemas.sharepoint.soap.FieldInformation[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformation"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Stream"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Results"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResultCollection"), com.microsoft.schemas.sharepoint.soap.CopyResult[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResult"));
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetItem");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Url"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetItemResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), org.apache.axis.types.UnsignedInt.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Fields"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformationCollection"), com.microsoft.schemas.sharepoint.soap.FieldInformation[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformation"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Stream"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public CopySoap12Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public CopySoap12Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public CopySoap12Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "guid");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyErrorCode");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.CopyErrorCode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResult");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.CopyResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResultCollection");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.CopyResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResult");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyResult");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "DestinationUrlCollection");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformation");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.FieldInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformationCollection");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.FieldInformation[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformation");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldInformation");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldType");
            cachedSerQNames.add(qName);
            cls = com.microsoft.schemas.sharepoint.soap.FieldType.class;
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

    public void copyIntoItemsLocal(java.lang.String sourceUrl, java.lang.String[] destinationUrls, org.apache.axis.holders.UnsignedIntHolder copyIntoItemsLocalResult, com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder results) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/CopyIntoItemsLocal");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsLocal"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sourceUrl, destinationUrls});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                copyIntoItemsLocalResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsLocalResult"));
            } catch (java.lang.Exception _exception) {
                copyIntoItemsLocalResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsLocalResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                results.value = (com.microsoft.schemas.sharepoint.soap.CopyResult[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Results"));
            } catch (java.lang.Exception _exception) {
                results.value = (com.microsoft.schemas.sharepoint.soap.CopyResult[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Results")), com.microsoft.schemas.sharepoint.soap.CopyResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void copyIntoItems(java.lang.String sourceUrl, java.lang.String[] destinationUrls, com.microsoft.schemas.sharepoint.soap.FieldInformation[] fields, byte[] stream, org.apache.axis.holders.UnsignedIntHolder copyIntoItemsResult, com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder results) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/CopyIntoItems");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItems"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sourceUrl, destinationUrls, fields, stream});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                copyIntoItemsResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsResult"));
            } catch (java.lang.Exception _exception) {
                copyIntoItemsResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyIntoItemsResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                results.value = (com.microsoft.schemas.sharepoint.soap.CopyResult[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Results"));
            } catch (java.lang.Exception _exception) {
                results.value = (com.microsoft.schemas.sharepoint.soap.CopyResult[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Results")), com.microsoft.schemas.sharepoint.soap.CopyResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getItem(java.lang.String url, org.apache.axis.holders.UnsignedIntHolder getItemResult, com.microsoft.schemas.sharepoint.soap.holders.FieldInformationCollectionHolder fields, javax.xml.rpc.holders.ByteArrayHolder stream) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.microsoft.com/sharepoint/soap/GetItem");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetItem"));

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
                getItemResult.value = (org.apache.axis.types.UnsignedInt) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetItemResult"));
            } catch (java.lang.Exception _exception) {
                getItemResult.value = (org.apache.axis.types.UnsignedInt) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetItemResult")), org.apache.axis.types.UnsignedInt.class);
            }
            try {
                fields.value = (com.microsoft.schemas.sharepoint.soap.FieldInformation[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Fields"));
            } catch (java.lang.Exception _exception) {
                fields.value = (com.microsoft.schemas.sharepoint.soap.FieldInformation[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Fields")), com.microsoft.schemas.sharepoint.soap.FieldInformation[].class);
            }
            try {
                stream.value = (byte[]) _output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Stream"));
            } catch (java.lang.Exception _exception) {
                stream.value = (byte[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Stream")), byte[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
