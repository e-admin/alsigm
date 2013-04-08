/**
 * ListaBytes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.mensajesCortos.ws.client.dto;

public class ListaBytes  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private byte[] bytes;

    public ListaBytes() {
    }

    public ListaBytes(
           java.lang.String errorCode,
           java.lang.String returnCode,
           byte[] bytes) {
       this.errorCode=errorCode;
       this.returnCode=returnCode;
        this.bytes = bytes;
    }


    /**
     * Gets the bytes value for this ListaBytes.
     * 
     * @return bytes
     */
    public byte[] getBytes() {
        return bytes;
    }


    /**
     * Sets the bytes value for this ListaBytes.
     * 
     * @param bytes
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaBytes)) return false;
        ListaBytes other = (ListaBytes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bytes==null && other.getBytes()==null) || 
             (this.bytes!=null &&
              java.util.Arrays.equals(this.bytes, other.getBytes())));
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
        if (getBytes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBytes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBytes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListaBytes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.server.ws.mensajesCortos.sgm.tecdoc.ieci", "ListaBytes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bytes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.mensajesCortos.sgm.tecdoc.ieci", "bytes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
