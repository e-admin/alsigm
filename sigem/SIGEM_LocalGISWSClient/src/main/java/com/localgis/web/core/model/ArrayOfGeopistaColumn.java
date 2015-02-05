/**
 * ArrayOfGeopistaColumn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class ArrayOfGeopistaColumn  implements java.io.Serializable {
    private com.localgis.web.core.model.GeopistaColumn[] geopistaColumn;

    public ArrayOfGeopistaColumn() {
    }

    public ArrayOfGeopistaColumn(
           com.localgis.web.core.model.GeopistaColumn[] geopistaColumn) {
           this.geopistaColumn = geopistaColumn;
    }


    /**
     * Gets the geopistaColumn value for this ArrayOfGeopistaColumn.
     * 
     * @return geopistaColumn
     */
    public com.localgis.web.core.model.GeopistaColumn[] getGeopistaColumn() {
        return geopistaColumn;
    }


    /**
     * Sets the geopistaColumn value for this ArrayOfGeopistaColumn.
     * 
     * @param geopistaColumn
     */
    public void setGeopistaColumn(com.localgis.web.core.model.GeopistaColumn[] geopistaColumn) {
        this.geopistaColumn = geopistaColumn;
    }

    public com.localgis.web.core.model.GeopistaColumn getGeopistaColumn(int i) {
        return this.geopistaColumn[i];
    }

    public void setGeopistaColumn(int i, com.localgis.web.core.model.GeopistaColumn _value) {
        this.geopistaColumn[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfGeopistaColumn)) return false;
        ArrayOfGeopistaColumn other = (ArrayOfGeopistaColumn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.geopistaColumn==null && other.getGeopistaColumn()==null) || 
             (this.geopistaColumn!=null &&
              java.util.Arrays.equals(this.geopistaColumn, other.getGeopistaColumn())));
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
        if (getGeopistaColumn() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGeopistaColumn());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGeopistaColumn(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfGeopistaColumn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "ArrayOfGeopistaColumn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geopistaColumn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "GeopistaColumn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "GeopistaColumn"));
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
