/**
 * DocumentoPago.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;

public class DocumentoPago  extends ieci.tecdoc.sgm.pe.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String documentoPago;

    public DocumentoPago() {
    }

    public DocumentoPago(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String documentoPago) {
        super(
            errorCode,
            returnCode);
        this.documentoPago = documentoPago;
    }


    /**
     * Gets the documentoPago value for this DocumentoPago.
     * 
     * @return documentoPago
     */
    public java.lang.String getDocumentoPago() {
        return documentoPago;
    }


    /**
     * Sets the documentoPago value for this DocumentoPago.
     * 
     * @param documentoPago
     */
    public void setDocumentoPago(java.lang.String documentoPago) {
        this.documentoPago = documentoPago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoPago)) return false;
        DocumentoPago other = (DocumentoPago) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.documentoPago==null && other.getDocumentoPago()==null) || 
             (this.documentoPago!=null &&
              this.documentoPago.equals(other.getDocumentoPago())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDocumentoPago() != null) {
            _hashCode += getDocumentoPago().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoPago.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "DocumentoPago"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentoPago");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "documentoPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
