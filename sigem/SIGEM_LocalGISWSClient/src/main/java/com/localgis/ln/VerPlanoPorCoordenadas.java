/**
 * VerPlanoPorCoordenadas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.ln;

public class VerPlanoPorCoordenadas  implements java.io.Serializable {
    private int in0;

    private double in1;

    private double in2;

    private int in3;

    private int in4;

    private int in5;

    private int in6;

    public VerPlanoPorCoordenadas() {
    }

    public VerPlanoPorCoordenadas(
           int in0,
           double in1,
           double in2,
           int in3,
           int in4,
           int in5,
           int in6) {
           this.in0 = in0;
           this.in1 = in1;
           this.in2 = in2;
           this.in3 = in3;
           this.in4 = in4;
           this.in5 = in5;
           this.in6 = in6;
    }


    /**
     * Gets the in0 value for this VerPlanoPorCoordenadas.
     * 
     * @return in0
     */
    public int getIn0() {
        return in0;
    }


    /**
     * Sets the in0 value for this VerPlanoPorCoordenadas.
     * 
     * @param in0
     */
    public void setIn0(int in0) {
        this.in0 = in0;
    }


    /**
     * Gets the in1 value for this VerPlanoPorCoordenadas.
     * 
     * @return in1
     */
    public double getIn1() {
        return in1;
    }


    /**
     * Sets the in1 value for this VerPlanoPorCoordenadas.
     * 
     * @param in1
     */
    public void setIn1(double in1) {
        this.in1 = in1;
    }


    /**
     * Gets the in2 value for this VerPlanoPorCoordenadas.
     * 
     * @return in2
     */
    public double getIn2() {
        return in2;
    }


    /**
     * Sets the in2 value for this VerPlanoPorCoordenadas.
     * 
     * @param in2
     */
    public void setIn2(double in2) {
        this.in2 = in2;
    }


    /**
     * Gets the in3 value for this VerPlanoPorCoordenadas.
     * 
     * @return in3
     */
    public int getIn3() {
        return in3;
    }


    /**
     * Sets the in3 value for this VerPlanoPorCoordenadas.
     * 
     * @param in3
     */
    public void setIn3(int in3) {
        this.in3 = in3;
    }


    /**
     * Gets the in4 value for this VerPlanoPorCoordenadas.
     * 
     * @return in4
     */
    public int getIn4() {
        return in4;
    }


    /**
     * Sets the in4 value for this VerPlanoPorCoordenadas.
     * 
     * @param in4
     */
    public void setIn4(int in4) {
        this.in4 = in4;
    }


    /**
     * Gets the in5 value for this VerPlanoPorCoordenadas.
     * 
     * @return in5
     */
    public int getIn5() {
        return in5;
    }


    /**
     * Sets the in5 value for this VerPlanoPorCoordenadas.
     * 
     * @param in5
     */
    public void setIn5(int in5) {
        this.in5 = in5;
    }


    /**
     * Gets the in6 value for this VerPlanoPorCoordenadas.
     * 
     * @return in6
     */
    public int getIn6() {
        return in6;
    }


    /**
     * Sets the in6 value for this VerPlanoPorCoordenadas.
     * 
     * @param in6
     */
    public void setIn6(int in6) {
        this.in6 = in6;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerPlanoPorCoordenadas)) return false;
        VerPlanoPorCoordenadas other = (VerPlanoPorCoordenadas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.in0 == other.getIn0() &&
            this.in1 == other.getIn1() &&
            this.in2 == other.getIn2() &&
            this.in3 == other.getIn3() &&
            this.in4 == other.getIn4() &&
            this.in5 == other.getIn5() &&
            this.in6 == other.getIn6();
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
        _hashCode += getIn0();
        _hashCode += new Double(getIn1()).hashCode();
        _hashCode += new Double(getIn2()).hashCode();
        _hashCode += getIn3();
        _hashCode += getIn4();
        _hashCode += getIn5();
        _hashCode += getIn6();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerPlanoPorCoordenadas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ln.localgis.com", ">verPlanoPorCoordenadas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in0");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in0"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in5");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in6");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
