/**
 * ArrayOfSubtipoOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfSubtipoOT  implements java.io.Serializable {
    private com.localgis.model.ot.SubtipoOT[] subtipoOT;

    public ArrayOfSubtipoOT() {
    }

    public ArrayOfSubtipoOT(
           com.localgis.model.ot.SubtipoOT[] subtipoOT) {
           this.subtipoOT = subtipoOT;
    }


    /**
     * Gets the subtipoOT value for this ArrayOfSubtipoOT.
     * 
     * @return subtipoOT
     */
    public com.localgis.model.ot.SubtipoOT[] getSubtipoOT() {
        return subtipoOT;
    }


    /**
     * Sets the subtipoOT value for this ArrayOfSubtipoOT.
     * 
     * @param subtipoOT
     */
    public void setSubtipoOT(com.localgis.model.ot.SubtipoOT[] subtipoOT) {
        this.subtipoOT = subtipoOT;
    }

    public com.localgis.model.ot.SubtipoOT getSubtipoOT(int i) {
        return this.subtipoOT[i];
    }

    public void setSubtipoOT(int i, com.localgis.model.ot.SubtipoOT _value) {
        this.subtipoOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfSubtipoOT)) return false;
        ArrayOfSubtipoOT other = (ArrayOfSubtipoOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.subtipoOT==null && other.getSubtipoOT()==null) || 
             (this.subtipoOT!=null &&
              java.util.Arrays.equals(this.subtipoOT, other.getSubtipoOT())));
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
        if (getSubtipoOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubtipoOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubtipoOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfSubtipoOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfSubtipoOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subtipoOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "SubtipoOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "SubtipoOT"));
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
