/**
 * ClienteLigeroSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.ieci.scsp.verifdata.services;

public class ClienteLigeroSoapBindingSkeleton implements es.ieci.scsp.verifdata.services.ClienteLigero, org.apache.axis.wsdl.Skeleton {
    private es.ieci.scsp.verifdata.services.ClienteLigero impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://services.verifdata.scsp.ieci.es", "nifFuncionario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://services.verifdata.scsp.ieci.es", "codigoProcedimiento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("consultaProcedimientoByNIF", _params, new javax.xml.namespace.QName("http://services.verifdata.scsp.ieci.es", "item"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://dao.model.verifdata.scsp.ieci.es", "Servicio"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://services.verifdata.scsp.ieci.es", "consultaProcedimientoByNIF"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("consultaProcedimientoByNIF") == null) {
            _myOperations.put("consultaProcedimientoByNIF", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("consultaProcedimientoByNIF")).add(_oper);
    }

    public ClienteLigeroSoapBindingSkeleton() {
        this.impl = new es.ieci.scsp.verifdata.services.ClienteLigeroSoapBindingImpl();
    }

    public ClienteLigeroSoapBindingSkeleton(es.ieci.scsp.verifdata.services.ClienteLigero impl) {
        this.impl = impl;
    }
    public es.ieci.scsp.verifdata.model.dao.Servicio[] consultaProcedimientoByNIF(java.lang.String nifFuncionario, java.lang.String codigoProcedimiento) throws java.rmi.RemoteException
    {
        es.ieci.scsp.verifdata.model.dao.Servicio[] ret = impl.consultaProcedimientoByNIF(nifFuncionario, codigoProcedimiento);
        return ret;
    }

}
