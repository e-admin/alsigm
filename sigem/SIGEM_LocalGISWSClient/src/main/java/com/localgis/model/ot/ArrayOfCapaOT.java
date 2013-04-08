/**
 * ArrayOfCapaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfCapaOT  implements java.io.Serializable {
    private com.localgis.model.ot.CapaOT[] capaOT;

    public ArrayOfCapaOT() {
    }

    public ArrayOfCapaOT(
           com.localgis.model.ot.CapaOT[] capaOT) {
           this.capaOT = capaOT;
    }


    /**
     * Gets the capaOT value for this ArrayOfCapaOT.
     * 
     * @return capaOT
     */
    public com.localgis.model.ot.CapaOT[] getCapaOT() {
        return capaOT;
    }


    /**
     * Sets the capaOT value for this ArrayOfCapaOT.
     * 
     * @param capaOT
     */
    public void setCapaOT(com.localgis.model.ot.CapaOT[] capaOT) {
        this.capaOT = capaOT;
    }

    public com.localgis.model.ot.CapaOT getCapaOT(int i) {
        return this.capaOT[i];
    }

    public void setCapaOT(int i, com.localgis.model.ot.CapaOT _value) {
        this.capaOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfCapaOT)) return false;
        ArrayOfCapaOT other = (ArrayOfCapaOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.capaOT==null && other.getCapaOT()==null) || 
             (this.capaOT!=null &&
              java.util.Arrays.equals(this.capaOT, other.getCapaOT())));
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
        if (getCapaOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCapaOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCapaOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfCapaOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfCapaOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capaOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "CapaOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "CapaOT"));
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
