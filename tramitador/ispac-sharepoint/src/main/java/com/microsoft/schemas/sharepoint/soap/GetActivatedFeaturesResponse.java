/**
 * GetActivatedFeaturesResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class GetActivatedFeaturesResponse  implements java.io.Serializable {
    private java.lang.String getActivatedFeaturesResult;

    public GetActivatedFeaturesResponse() {
    }

    public GetActivatedFeaturesResponse(
           java.lang.String getActivatedFeaturesResult) {
           this.getActivatedFeaturesResult = getActivatedFeaturesResult;
    }


    /**
     * Gets the getActivatedFeaturesResult value for this GetActivatedFeaturesResponse.
     * 
     * @return getActivatedFeaturesResult
     */
    public java.lang.String getGetActivatedFeaturesResult() {
        return getActivatedFeaturesResult;
    }


    /**
     * Sets the getActivatedFeaturesResult value for this GetActivatedFeaturesResponse.
     * 
     * @param getActivatedFeaturesResult
     */
    public void setGetActivatedFeaturesResult(java.lang.String getActivatedFeaturesResult) {
        this.getActivatedFeaturesResult = getActivatedFeaturesResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetActivatedFeaturesResponse)) return false;
        GetActivatedFeaturesResponse other = (GetActivatedFeaturesResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getActivatedFeaturesResult==null && other.getGetActivatedFeaturesResult()==null) || 
             (this.getActivatedFeaturesResult!=null &&
              this.getActivatedFeaturesResult.equals(other.getGetActivatedFeaturesResult())));
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
        if (getGetActivatedFeaturesResult() != null) {
            _hashCode += getGetActivatedFeaturesResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetActivatedFeaturesResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">GetActivatedFeaturesResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getActivatedFeaturesResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetActivatedFeaturesResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
