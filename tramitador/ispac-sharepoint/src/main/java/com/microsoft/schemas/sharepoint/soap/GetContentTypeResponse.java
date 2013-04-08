/**
 * GetContentTypeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class GetContentTypeResponse  implements java.io.Serializable {
    private com.microsoft.schemas.sharepoint.soap.GetContentTypeResponseGetContentTypeResult getContentTypeResult;

    public GetContentTypeResponse() {
    }

    public GetContentTypeResponse(
           com.microsoft.schemas.sharepoint.soap.GetContentTypeResponseGetContentTypeResult getContentTypeResult) {
           this.getContentTypeResult = getContentTypeResult;
    }


    /**
     * Gets the getContentTypeResult value for this GetContentTypeResponse.
     * 
     * @return getContentTypeResult
     */
    public com.microsoft.schemas.sharepoint.soap.GetContentTypeResponseGetContentTypeResult getGetContentTypeResult() {
        return getContentTypeResult;
    }


    /**
     * Sets the getContentTypeResult value for this GetContentTypeResponse.
     * 
     * @param getContentTypeResult
     */
    public void setGetContentTypeResult(com.microsoft.schemas.sharepoint.soap.GetContentTypeResponseGetContentTypeResult getContentTypeResult) {
        this.getContentTypeResult = getContentTypeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetContentTypeResponse)) return false;
        GetContentTypeResponse other = (GetContentTypeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getContentTypeResult==null && other.getGetContentTypeResult()==null) || 
             (this.getContentTypeResult!=null &&
              this.getContentTypeResult.equals(other.getGetContentTypeResult())));
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
        if (getGetContentTypeResult() != null) {
            _hashCode += getGetContentTypeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetContentTypeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">GetContentTypeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getContentTypeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetContentTypeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>GetContentTypeResponse>GetContentTypeResult"));
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
