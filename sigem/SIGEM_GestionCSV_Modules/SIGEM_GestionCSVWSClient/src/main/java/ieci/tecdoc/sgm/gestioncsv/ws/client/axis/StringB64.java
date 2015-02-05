/**
 * StringB64.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.gestioncsv.ws.client.axis;

public class StringB64  extends ieci.tecdoc.sgm.gestioncsv.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private java.lang.String stringB64;

    public StringB64() {
    }

    public StringB64(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String stringB64) {
        super(
            errorCode,
            returnCode);
        this.stringB64 = stringB64;
    }


    /**
     * Gets the stringB64 value for this StringB64.
     * 
     * @return stringB64
     */
    public java.lang.String getStringB64() {
        return stringB64;
    }


    /**
     * Sets the stringB64 value for this StringB64.
     * 
     * @param stringB64
     */
    public void setStringB64(java.lang.String stringB64) {
        this.stringB64 = stringB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StringB64)) return false;
        StringB64 other = (StringB64) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.stringB64==null && other.getStringB64()==null) || 
             (this.stringB64!=null &&
              this.stringB64.equals(other.getStringB64())));
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
        if (getStringB64() != null) {
            _hashCode += getStringB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StringB64.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "StringB64"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stringB64");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "stringB64"));
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
