/**
 * ObtenerMunicipiosResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.ln;

public class ObtenerMunicipiosResponse  implements java.io.Serializable {
    private com.localgis.model.ot.ArrayOfMunicipioOT out;

    public ObtenerMunicipiosResponse() {
    }

    public ObtenerMunicipiosResponse(
           com.localgis.model.ot.ArrayOfMunicipioOT out) {
           this.out = out;
    }


    /**
     * Gets the out value for this ObtenerMunicipiosResponse.
     * 
     * @return out
     */
    public com.localgis.model.ot.ArrayOfMunicipioOT getOut() {
        return out;
    }


    /**
     * Sets the out value for this ObtenerMunicipiosResponse.
     * 
     * @param out
     */
    public void setOut(com.localgis.model.ot.ArrayOfMunicipioOT out) {
        this.out = out;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerMunicipiosResponse)) return false;
        ObtenerMunicipiosResponse other = (ObtenerMunicipiosResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.out==null && other.getOut()==null) || 
             (this.out!=null &&
              this.out.equals(other.getOut())));
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
        if (getOut() != null) {
            _hashCode += getOut().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerMunicipiosResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ln.localgis.com", ">obtenerMunicipiosResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("out");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "out"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfMunicipioOT"));
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
