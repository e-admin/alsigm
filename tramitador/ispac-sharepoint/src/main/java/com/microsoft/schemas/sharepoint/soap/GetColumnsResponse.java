/**
 * GetColumnsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class GetColumnsResponse  implements java.io.Serializable {
    private com.microsoft.schemas.sharepoint.soap.GetColumnsResponseGetColumnsResult getColumnsResult;

    public GetColumnsResponse() {
    }

    public GetColumnsResponse(
           com.microsoft.schemas.sharepoint.soap.GetColumnsResponseGetColumnsResult getColumnsResult) {
           this.getColumnsResult = getColumnsResult;
    }


    /**
     * Gets the getColumnsResult value for this GetColumnsResponse.
     * 
     * @return getColumnsResult
     */
    public com.microsoft.schemas.sharepoint.soap.GetColumnsResponseGetColumnsResult getGetColumnsResult() {
        return getColumnsResult;
    }


    /**
     * Sets the getColumnsResult value for this GetColumnsResponse.
     * 
     * @param getColumnsResult
     */
    public void setGetColumnsResult(com.microsoft.schemas.sharepoint.soap.GetColumnsResponseGetColumnsResult getColumnsResult) {
        this.getColumnsResult = getColumnsResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetColumnsResponse)) return false;
        GetColumnsResponse other = (GetColumnsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getColumnsResult==null && other.getGetColumnsResult()==null) || 
             (this.getColumnsResult!=null &&
              this.getColumnsResult.equals(other.getGetColumnsResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGetColumnsResult() != null) {
            _hashCode += getGetColumnsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetColumnsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">GetColumnsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getColumnsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetColumnsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>GetColumnsResponse>GetColumnsResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
