/**
 * GetListTemplatesResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class GetListTemplatesResponse  implements java.io.Serializable {
    private com.microsoft.schemas.sharepoint.soap.GetListTemplatesResponseGetListTemplatesResult getListTemplatesResult;

    public GetListTemplatesResponse() {
    }

    public GetListTemplatesResponse(
           com.microsoft.schemas.sharepoint.soap.GetListTemplatesResponseGetListTemplatesResult getListTemplatesResult) {
           this.getListTemplatesResult = getListTemplatesResult;
    }


    /**
     * Gets the getListTemplatesResult value for this GetListTemplatesResponse.
     * 
     * @return getListTemplatesResult
     */
    public com.microsoft.schemas.sharepoint.soap.GetListTemplatesResponseGetListTemplatesResult getGetListTemplatesResult() {
        return getListTemplatesResult;
    }


    /**
     * Sets the getListTemplatesResult value for this GetListTemplatesResponse.
     * 
     * @param getListTemplatesResult
     */
    public void setGetListTemplatesResult(com.microsoft.schemas.sharepoint.soap.GetListTemplatesResponseGetListTemplatesResult getListTemplatesResult) {
        this.getListTemplatesResult = getListTemplatesResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetListTemplatesResponse)) return false;
        GetListTemplatesResponse other = (GetListTemplatesResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getListTemplatesResult==null && other.getGetListTemplatesResult()==null) || 
             (this.getListTemplatesResult!=null &&
              this.getListTemplatesResult.equals(other.getGetListTemplatesResult())));
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
        if (getGetListTemplatesResult() != null) {
            _hashCode += getGetListTemplatesResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetListTemplatesResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">GetListTemplatesResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getListTemplatesResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "GetListTemplatesResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>GetListTemplatesResponse>GetListTemplatesResult"));
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
