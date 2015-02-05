/**
 * ArrayOfGeopistaMunicipio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class ArrayOfGeopistaMunicipio  implements java.io.Serializable {
    private com.localgis.web.core.model.GeopistaMunicipio[] geopistaMunicipio;

    public ArrayOfGeopistaMunicipio() {
    }

    public ArrayOfGeopistaMunicipio(
           com.localgis.web.core.model.GeopistaMunicipio[] geopistaMunicipio) {
           this.geopistaMunicipio = geopistaMunicipio;
    }


    /**
     * Gets the geopistaMunicipio value for this ArrayOfGeopistaMunicipio.
     * 
     * @return geopistaMunicipio
     */
    public com.localgis.web.core.model.GeopistaMunicipio[] getGeopistaMunicipio() {
        return geopistaMunicipio;
    }


    /**
     * Sets the geopistaMunicipio value for this ArrayOfGeopistaMunicipio.
     * 
     * @param geopistaMunicipio
     */
    public void setGeopistaMunicipio(com.localgis.web.core.model.GeopistaMunicipio[] geopistaMunicipio) {
        this.geopistaMunicipio = geopistaMunicipio;
    }

    public com.localgis.web.core.model.GeopistaMunicipio getGeopistaMunicipio(int i) {
        return this.geopistaMunicipio[i];
    }

    public void setGeopistaMunicipio(int i, com.localgis.web.core.model.GeopistaMunicipio _value) {
        this.geopistaMunicipio[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfGeopistaMunicipio)) return false;
        ArrayOfGeopistaMunicipio other = (ArrayOfGeopistaMunicipio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.geopistaMunicipio==null && other.getGeopistaMunicipio()==null) || 
             (this.geopistaMunicipio!=null &&
              java.util.Arrays.equals(this.geopistaMunicipio, other.getGeopistaMunicipio())));
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
        if (getGeopistaMunicipio() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGeopistaMunicipio());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGeopistaMunicipio(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfGeopistaMunicipio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "ArrayOfGeopistaMunicipio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geopistaMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "GeopistaMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "GeopistaMunicipio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
