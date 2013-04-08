/**
 * ArrayOfLocalgisLayer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class ArrayOfLocalgisLayer  implements java.io.Serializable {
    private com.localgis.web.core.model.LocalgisLayer[] localgisLayer;

    public ArrayOfLocalgisLayer() {
    }

    public ArrayOfLocalgisLayer(
           com.localgis.web.core.model.LocalgisLayer[] localgisLayer) {
           this.localgisLayer = localgisLayer;
    }


    /**
     * Gets the localgisLayer value for this ArrayOfLocalgisLayer.
     * 
     * @return localgisLayer
     */
    public com.localgis.web.core.model.LocalgisLayer[] getLocalgisLayer() {
        return localgisLayer;
    }


    /**
     * Sets the localgisLayer value for this ArrayOfLocalgisLayer.
     * 
     * @param localgisLayer
     */
    public void setLocalgisLayer(com.localgis.web.core.model.LocalgisLayer[] localgisLayer) {
        this.localgisLayer = localgisLayer;
    }

    public com.localgis.web.core.model.LocalgisLayer getLocalgisLayer(int i) {
        return this.localgisLayer[i];
    }

    public void setLocalgisLayer(int i, com.localgis.web.core.model.LocalgisLayer _value) {
        this.localgisLayer[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfLocalgisLayer)) return false;
        ArrayOfLocalgisLayer other = (ArrayOfLocalgisLayer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.localgisLayer==null && other.getLocalgisLayer()==null) || 
             (this.localgisLayer!=null &&
              java.util.Arrays.equals(this.localgisLayer, other.getLocalgisLayer())));
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
        if (getLocalgisLayer() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLocalgisLayer());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLocalgisLayer(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfLocalgisLayer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "ArrayOfLocalgisLayer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localgisLayer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "LocalgisLayer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "LocalgisLayer"));
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
