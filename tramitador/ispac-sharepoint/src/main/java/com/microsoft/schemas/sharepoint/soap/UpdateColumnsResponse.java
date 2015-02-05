/**
 * UpdateColumnsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class UpdateColumnsResponse  implements java.io.Serializable {
    private com.microsoft.schemas.sharepoint.soap.UpdateColumnsResponseUpdateColumnsResult updateColumnsResult;

    public UpdateColumnsResponse() {
    }

    public UpdateColumnsResponse(
           com.microsoft.schemas.sharepoint.soap.UpdateColumnsResponseUpdateColumnsResult updateColumnsResult) {
           this.updateColumnsResult = updateColumnsResult;
    }


    /**
     * Gets the updateColumnsResult value for this UpdateColumnsResponse.
     * 
     * @return updateColumnsResult
     */
    public com.microsoft.schemas.sharepoint.soap.UpdateColumnsResponseUpdateColumnsResult getUpdateColumnsResult() {
        return updateColumnsResult;
    }


    /**
     * Sets the updateColumnsResult value for this UpdateColumnsResponse.
     * 
     * @param updateColumnsResult
     */
    public void setUpdateColumnsResult(com.microsoft.schemas.sharepoint.soap.UpdateColumnsResponseUpdateColumnsResult updateColumnsResult) {
        this.updateColumnsResult = updateColumnsResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateColumnsResponse)) return false;
        UpdateColumnsResponse other = (UpdateColumnsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.updateColumnsResult==null && other.getUpdateColumnsResult()==null) || 
             (this.updateColumnsResult!=null &&
              this.updateColumnsResult.equals(other.getUpdateColumnsResult())));
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
        if (getUpdateColumnsResult() != null) {
            _hashCode += getUpdateColumnsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateColumnsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">UpdateColumnsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateColumnsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "UpdateColumnsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>UpdateColumnsResponse>UpdateColumnsResult"));
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
