/**
 * ArrayOfConstruccionOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ArrayOfConstruccionOT  implements java.io.Serializable {
    private com.localgis.model.ot.ConstruccionOT[] construccionOT;

    public ArrayOfConstruccionOT() {
    }

    public ArrayOfConstruccionOT(
           com.localgis.model.ot.ConstruccionOT[] construccionOT) {
           this.construccionOT = construccionOT;
    }


    /**
     * Gets the construccionOT value for this ArrayOfConstruccionOT.
     * 
     * @return construccionOT
     */
    public com.localgis.model.ot.ConstruccionOT[] getConstruccionOT() {
        return construccionOT;
    }


    /**
     * Sets the construccionOT value for this ArrayOfConstruccionOT.
     * 
     * @param construccionOT
     */
    public void setConstruccionOT(com.localgis.model.ot.ConstruccionOT[] construccionOT) {
        this.construccionOT = construccionOT;
    }

    public com.localgis.model.ot.ConstruccionOT getConstruccionOT(int i) {
        return this.construccionOT[i];
    }

    public void setConstruccionOT(int i, com.localgis.model.ot.ConstruccionOT _value) {
        this.construccionOT[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfConstruccionOT)) return false;
        ArrayOfConstruccionOT other = (ArrayOfConstruccionOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.construccionOT==null && other.getConstruccionOT()==null) || 
             (this.construccionOT!=null &&
              java.util.Arrays.equals(this.construccionOT, other.getConstruccionOT())));
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
        if (getConstruccionOT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConstruccionOT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConstruccionOT(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfConstruccionOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfConstruccionOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("construccionOT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ConstruccionOT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ConstruccionOT"));
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
