/**
 * ArrayOfTipoViaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfTipoViaOT  implements java.io.Serializable {
    private com.localgis.model.ot.TipoViaOT[] tipoViaOT;

    public ArrayOfTipoViaOT() {
    }

    public ArrayOfTipoViaOT(
           com.localgis.model.ot.TipoViaOT[] tipoViaOT) {
           this.tipoViaOT = tipoViaOT;
    }


    /**
     * Gets the tipoViaOT value for this ArrayOfTipoViaOT.
     * 
     * @return tipoViaOT
     */
    public com.localgis.model.ot.TipoViaOT[] getTipoViaOT() {
        return tipoViaOT;
    }


    /**
     * Sets the tipoViaOT value for this ArrayOfTipoViaOT.
     * 
     * @param tipoViaOT
     */
    public void setTipoViaOT(com.localgis.model.ot.TipoViaOT[] tipoViaOT) {
        this.tipoViaOT = tipoViaOT;
    }

    public com.localgis.model.ot.TipoViaOT getTipoViaOT(int i) {
        return this.tipoViaOT[i];
    }

    public void setTipoViaOT(int i, com.localgis.model.ot.TipoViaOT _value) {
        this.tipoViaOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfTipoViaOT)) return false;
        ArrayOfTipoViaOT other = (ArrayOfTipoViaOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoViaOT==null && other.getTipoViaOT()==null) || 
             (this.tipoViaOT!=null &&
              java.util.Arrays.equals(this.tipoViaOT, other.getTipoViaOT())));
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
        if (getTipoViaOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipoViaOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipoViaOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfTipoViaOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfTipoViaOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoViaOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "TipoViaOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "TipoViaOT"));
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
