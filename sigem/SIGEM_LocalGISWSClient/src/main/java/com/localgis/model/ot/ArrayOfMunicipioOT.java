/**
 * ArrayOfMunicipioOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfMunicipioOT  implements java.io.Serializable {
    private com.localgis.model.ot.MunicipioOT[] municipioOT;

    public ArrayOfMunicipioOT() {
    }

    public ArrayOfMunicipioOT(
           com.localgis.model.ot.MunicipioOT[] municipioOT) {
           this.municipioOT = municipioOT;
    }


    /**
     * Gets the municipioOT value for this ArrayOfMunicipioOT.
     * 
     * @return municipioOT
     */
    public com.localgis.model.ot.MunicipioOT[] getMunicipioOT() {
        return municipioOT;
    }


    /**
     * Sets the municipioOT value for this ArrayOfMunicipioOT.
     * 
     * @param municipioOT
     */
    public void setMunicipioOT(com.localgis.model.ot.MunicipioOT[] municipioOT) {
        this.municipioOT = municipioOT;
    }

    public com.localgis.model.ot.MunicipioOT getMunicipioOT(int i) {
        return this.municipioOT[i];
    }

    public void setMunicipioOT(int i, com.localgis.model.ot.MunicipioOT _value) {
        this.municipioOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfMunicipioOT)) return false;
        ArrayOfMunicipioOT other = (ArrayOfMunicipioOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.municipioOT==null && other.getMunicipioOT()==null) || 
             (this.municipioOT!=null &&
              java.util.Arrays.equals(this.municipioOT, other.getMunicipioOT())));
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
        if (getMunicipioOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMunicipioOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMunicipioOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfMunicipioOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfMunicipioOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipioOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "MunicipioOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "MunicipioOT"));
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
