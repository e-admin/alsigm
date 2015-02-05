/**
 * ByteArrayB64.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class ByteArrayB64  extends ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio  implements java.io.Serializable {

	private java.lang.String byteArrayB64;

    public ByteArrayB64() {
    }

    public ByteArrayB64(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String byteArrayB64) {
        super(
            errorCode,
            returnCode);
        this.byteArrayB64 = byteArrayB64;
    }


    /**
     * Gets the byteArrayB64 value for this ByteArrayB64.
     * 
     * @return byteArrayB64
     */
    public java.lang.String getByteArrayB64() {
        return byteArrayB64;
    }


    /**
     * Sets the byteArrayB64 value for this ByteArrayB64.
     * 
     * @param byteArrayB64
     */
    public void setByteArrayB64(java.lang.String byteArrayB64) {
        this.byteArrayB64 = byteArrayB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ByteArrayB64)) return false;
        ByteArrayB64 other = (ByteArrayB64) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.byteArrayB64==null && other.getByteArrayB64()==null) || 
             (this.byteArrayB64!=null &&
              this.byteArrayB64.equals(other.getByteArrayB64())));
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
        if (getByteArrayB64() != null) {
            _hashCode += getByteArrayB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ByteArrayB64.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "ByteArrayB64"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("byteArrayB64");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "byteArrayB64"));
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
