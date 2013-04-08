/**
 * ArrayOfBienInmuebleOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfBienInmuebleOT  implements java.io.Serializable {
    private com.localgis.model.ot.BienInmuebleOT[] bienInmuebleOT;

    public ArrayOfBienInmuebleOT() {
    }

    public ArrayOfBienInmuebleOT(
           com.localgis.model.ot.BienInmuebleOT[] bienInmuebleOT) {
           this.bienInmuebleOT = bienInmuebleOT;
    }


    /**
     * Gets the bienInmuebleOT value for this ArrayOfBienInmuebleOT.
     * 
     * @return bienInmuebleOT
     */
    public com.localgis.model.ot.BienInmuebleOT[] getBienInmuebleOT() {
        return bienInmuebleOT;
    }


    /**
     * Sets the bienInmuebleOT value for this ArrayOfBienInmuebleOT.
     * 
     * @param bienInmuebleOT
     */
    public void setBienInmuebleOT(com.localgis.model.ot.BienInmuebleOT[] bienInmuebleOT) {
        this.bienInmuebleOT = bienInmuebleOT;
    }

    public com.localgis.model.ot.BienInmuebleOT getBienInmuebleOT(int i) {
        return this.bienInmuebleOT[i];
    }

    public void setBienInmuebleOT(int i, com.localgis.model.ot.BienInmuebleOT _value) {
        this.bienInmuebleOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfBienInmuebleOT)) return false;
        ArrayOfBienInmuebleOT other = (ArrayOfBienInmuebleOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bienInmuebleOT==null && other.getBienInmuebleOT()==null) || 
             (this.bienInmuebleOT!=null &&
              java.util.Arrays.equals(this.bienInmuebleOT, other.getBienInmuebleOT())));
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
        if (getBienInmuebleOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBienInmuebleOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBienInmuebleOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfBienInmuebleOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfBienInmuebleOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bienInmuebleOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "BienInmuebleOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "BienInmuebleOT"));
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
