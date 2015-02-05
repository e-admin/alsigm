/**
 * ArrayOfCultivoOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfCultivoOT  implements java.io.Serializable {
    private com.localgis.model.ot.CultivoOT[] cultivoOT;

    public ArrayOfCultivoOT() {
    }

    public ArrayOfCultivoOT(
           com.localgis.model.ot.CultivoOT[] cultivoOT) {
           this.cultivoOT = cultivoOT;
    }


    /**
     * Gets the cultivoOT value for this ArrayOfCultivoOT.
     * 
     * @return cultivoOT
     */
    public com.localgis.model.ot.CultivoOT[] getCultivoOT() {
        return cultivoOT;
    }


    /**
     * Sets the cultivoOT value for this ArrayOfCultivoOT.
     * 
     * @param cultivoOT
     */
    public void setCultivoOT(com.localgis.model.ot.CultivoOT[] cultivoOT) {
        this.cultivoOT = cultivoOT;
    }

    public com.localgis.model.ot.CultivoOT getCultivoOT(int i) {
        return this.cultivoOT[i];
    }

    public void setCultivoOT(int i, com.localgis.model.ot.CultivoOT _value) {
        this.cultivoOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfCultivoOT)) return false;
        ArrayOfCultivoOT other = (ArrayOfCultivoOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cultivoOT==null && other.getCultivoOT()==null) || 
             (this.cultivoOT!=null &&
              java.util.Arrays.equals(this.cultivoOT, other.getCultivoOT())));
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
        if (getCultivoOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCultivoOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCultivoOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfCultivoOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfCultivoOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cultivoOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "CultivoOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "CultivoOT"));
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
