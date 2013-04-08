/**
 * VerPlanoPorReferenciaCatastral.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.ln;

public class VerPlanoPorReferenciaCatastral  implements java.io.Serializable {
    private int in0;

    private java.lang.String in1;

    private int in2;

    private int in3;

    private int in4;

    private int in5;

    public VerPlanoPorReferenciaCatastral() {
    }

    public VerPlanoPorReferenciaCatastral(
           int in0,
           java.lang.String in1,
           int in2,
           int in3,
           int in4,
           int in5) {
           this.in0 = in0;
           this.in1 = in1;
           this.in2 = in2;
           this.in3 = in3;
           this.in4 = in4;
           this.in5 = in5;
    }


    /**
     * Gets the in0 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @return in0
     */
    public int getIn0() {
        return in0;
    }


    /**
     * Sets the in0 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @param in0
     */
    public void setIn0(int in0) {
        this.in0 = in0;
    }


    /**
     * Gets the in1 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @return in1
     */
    public java.lang.String getIn1() {
        return in1;
    }


    /**
     * Sets the in1 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @param in1
     */
    public void setIn1(java.lang.String in1) {
        this.in1 = in1;
    }


    /**
     * Gets the in2 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @return in2
     */
    public int getIn2() {
        return in2;
    }


    /**
     * Sets the in2 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @param in2
     */
    public void setIn2(int in2) {
        this.in2 = in2;
    }


    /**
     * Gets the in3 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @return in3
     */
    public int getIn3() {
        return in3;
    }


    /**
     * Sets the in3 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @param in3
     */
    public void setIn3(int in3) {
        this.in3 = in3;
    }


    /**
     * Gets the in4 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @return in4
     */
    public int getIn4() {
        return in4;
    }


    /**
     * Sets the in4 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @param in4
     */
    public void setIn4(int in4) {
        this.in4 = in4;
    }


    /**
     * Gets the in5 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @return in5
     */
    public int getIn5() {
        return in5;
    }


    /**
     * Sets the in5 value for this VerPlanoPorReferenciaCatastral.
     * 
     * @param in5
     */
    public void setIn5(int in5) {
        this.in5 = in5;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerPlanoPorReferenciaCatastral)) return false;
        VerPlanoPorReferenciaCatastral other = (VerPlanoPorReferenciaCatastral) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.in0 == other.getIn0() &&
            ((this.in1==null && other.getIn1()==null) || 
             (this.in1!=null &&
              this.in1.equals(other.getIn1()))) &&
            this.in2 == other.getIn2() &&
            this.in3 == other.getIn3() &&
            this.in4 == other.getIn4() &&
            this.in5 == other.getIn5();
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
        if (getIn1() != null) {
            _hashCode += getIn1().hashCode();
        }
        _hashCode += getIn2();
        _hashCode += getIn3();
        _hashCode += getIn4();
        _hashCode += getIn5();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerPlanoPorReferenciaCatastral.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ln.localgis.com", ">verPlanoPorReferenciaCatastral"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in0");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in0"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
