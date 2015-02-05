/**
 * ArrayOfLocalgisMap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class ArrayOfLocalgisMap  implements java.io.Serializable {
    private com.localgis.web.core.model.LocalgisMap[] localgisMap;

    public ArrayOfLocalgisMap() {
    }

    public ArrayOfLocalgisMap(
           com.localgis.web.core.model.LocalgisMap[] localgisMap) {
           this.localgisMap = localgisMap;
    }


    /**
     * Gets the localgisMap value for this ArrayOfLocalgisMap.
     * 
     * @return localgisMap
     */
    public com.localgis.web.core.model.LocalgisMap[] getLocalgisMap() {
        return localgisMap;
    }


    /**
     * Sets the localgisMap value for this ArrayOfLocalgisMap.
     * 
     * @param localgisMap
     */
    public void setLocalgisMap(com.localgis.web.core.model.LocalgisMap[] localgisMap) {
        this.localgisMap = localgisMap;
    }

    public com.localgis.web.core.model.LocalgisMap getLocalgisMap(int i) {
        return this.localgisMap[i];
    }

    public void setLocalgisMap(int i, com.localgis.web.core.model.LocalgisMap _value) {
        this.localgisMap[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfLocalgisMap)) return false;
        ArrayOfLocalgisMap other = (ArrayOfLocalgisMap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.localgisMap==null && other.getLocalgisMap()==null) || 
             (this.localgisMap!=null &&
              java.util.Arrays.equals(this.localgisMap, other.getLocalgisMap())));
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
        if (getLocalgisMap() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLocalgisMap());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLocalgisMap(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfLocalgisMap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "ArrayOfLocalgisMap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localgisMap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "LocalgisMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "LocalgisMap"));
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
