/**
 * RemoveContentTypeXmlDocumentResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class RemoveContentTypeXmlDocumentResponse  implements java.io.Serializable {
    private com.microsoft.schemas.sharepoint.soap.RemoveContentTypeXmlDocumentResponseRemoveContentTypeXmlDocumentResult removeContentTypeXmlDocumentResult;

    public RemoveContentTypeXmlDocumentResponse() {
    }

    public RemoveContentTypeXmlDocumentResponse(
           com.microsoft.schemas.sharepoint.soap.RemoveContentTypeXmlDocumentResponseRemoveContentTypeXmlDocumentResult removeContentTypeXmlDocumentResult) {
           this.removeContentTypeXmlDocumentResult = removeContentTypeXmlDocumentResult;
    }


    /**
     * Gets the removeContentTypeXmlDocumentResult value for this RemoveContentTypeXmlDocumentResponse.
     * 
     * @return removeContentTypeXmlDocumentResult
     */
    public com.microsoft.schemas.sharepoint.soap.RemoveContentTypeXmlDocumentResponseRemoveContentTypeXmlDocumentResult getRemoveContentTypeXmlDocumentResult() {
        return removeContentTypeXmlDocumentResult;
    }


    /**
     * Sets the removeContentTypeXmlDocumentResult value for this RemoveContentTypeXmlDocumentResponse.
     * 
     * @param removeContentTypeXmlDocumentResult
     */
    public void setRemoveContentTypeXmlDocumentResult(com.microsoft.schemas.sharepoint.soap.RemoveContentTypeXmlDocumentResponseRemoveContentTypeXmlDocumentResult removeContentTypeXmlDocumentResult) {
        this.removeContentTypeXmlDocumentResult = removeContentTypeXmlDocumentResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemoveContentTypeXmlDocumentResponse)) return false;
        RemoveContentTypeXmlDocumentResponse other = (RemoveContentTypeXmlDocumentResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.removeContentTypeXmlDocumentResult==null && other.getRemoveContentTypeXmlDocumentResult()==null) || 
             (this.removeContentTypeXmlDocumentResult!=null &&
              this.removeContentTypeXmlDocumentResult.equals(other.getRemoveContentTypeXmlDocumentResult())));
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
        if (getRemoveContentTypeXmlDocumentResult() != null) {
            _hashCode += getRemoveContentTypeXmlDocumentResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemoveContentTypeXmlDocumentResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">RemoveContentTypeXmlDocumentResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removeContentTypeXmlDocumentResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "RemoveContentTypeXmlDocumentResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>RemoveContentTypeXmlDocumentResponse>RemoveContentTypeXmlDocumentResult"));
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
