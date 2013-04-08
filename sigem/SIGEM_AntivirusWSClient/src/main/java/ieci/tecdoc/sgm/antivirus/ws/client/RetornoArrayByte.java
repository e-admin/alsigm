/**
 * RetornoArrayByte.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.antivirus.ws.client;

public class RetornoArrayByte  extends ieci.tecdoc.sgm.antivirus.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String contenido;

    public RetornoArrayByte() {
    }

    public RetornoArrayByte(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String contenido) {
        super(
            errorCode,
            returnCode);
        this.contenido = contenido;
    }


    /**
     * Gets the contenido value for this RetornoArrayByte.
     * 
     * @return contenido
     */
    public java.lang.String getContenido() {
        return contenido;
    }


    /**
     * Sets the contenido value for this RetornoArrayByte.
     * 
     * @param contenido
     */
    public void setContenido(java.lang.String contenido) {
        this.contenido = contenido;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetornoArrayByte)) return false;
        RetornoArrayByte other = (RetornoArrayByte) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contenido==null && other.getContenido()==null) || 
             (this.contenido!=null &&
              this.contenido.equals(other.getContenido())));
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
        if (getContenido() != null) {
            _hashCode += getContenido().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetornoArrayByte.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.antivirus.sgm.tecdoc.ieci", "RetornoArrayByte"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.antivirus.sgm.tecdoc.ieci", "contenido"));
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
