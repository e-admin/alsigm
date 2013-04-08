/**
 * BajaCallejero.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.ln;

public class BajaCallejero  implements java.io.Serializable {
    private int in0;

    private int in1;

    private java.lang.String in2;

    private java.lang.String in3;

    private java.lang.String in4;

    private java.util.Calendar in5;

    public BajaCallejero() {
    }

    public BajaCallejero(
           int in0,
           int in1,
           java.lang.String in2,
           java.lang.String in3,
           java.lang.String in4,
           java.util.Calendar in5) {
           this.in0 = in0;
           this.in1 = in1;
           this.in2 = in2;
           this.in3 = in3;
           this.in4 = in4;
           this.in5 = in5;
    }


    /**
     * Gets the in0 value for this BajaCallejero.
     * 
     * @return in0
     */
    public int getIn0() {
        return in0;
    }


    /**
     * Sets the in0 value for this BajaCallejero.
     * 
     * @param in0
     */
    public void setIn0(int in0) {
        this.in0 = in0;
    }


    /**
     * Gets the in1 value for this BajaCallejero.
     * 
     * @return in1
     */
    public int getIn1() {
        return in1;
    }


    /**
     * Sets the in1 value for this BajaCallejero.
     * 
     * @param in1
     */
    public void setIn1(int in1) {
        this.in1 = in1;
    }


    /**
     * Gets the in2 value for this BajaCallejero.
     * 
     * @return in2
     */
    public java.lang.String getIn2() {
        return in2;
    }


    /**
     * Sets the in2 value for this BajaCallejero.
     * 
     * @param in2
     */
    public void setIn2(java.lang.String in2) {
        this.in2 = in2;
    }


    /**
     * Gets the in3 value for this BajaCallejero.
     * 
     * @return in3
     */
    public java.lang.String getIn3() {
        return in3;
    }


    /**
     * Sets the in3 value for this BajaCallejero.
     * 
     * @param in3
     */
    public void setIn3(java.lang.String in3) {
        this.in3 = in3;
    }


    /**
     * Gets the in4 value for this BajaCallejero.
     * 
     * @return in4
     */
    public java.lang.String getIn4() {
        return in4;
    }


    /**
     * Sets the in4 value for this BajaCallejero.
     * 
     * @param in4
     */
    public void setIn4(java.lang.String in4) {
        this.in4 = in4;
    }


    /**
     * Gets the in5 value for this BajaCallejero.
     * 
     * @return in5
     */
    public java.util.Calendar getIn5() {
        return in5;
    }


    /**
     * Sets the in5 value for this BajaCallejero.
     * 
     * @param in5
     */
    public void setIn5(java.util.Calendar in5) {
        this.in5 = in5;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BajaCallejero)) return false;
        BajaCallejero other = (BajaCallejero) obj;
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
            ((this.in2==null && other.getIn2()==null) || 
             (this.in2!=null &&
              this.in2.equals(other.getIn2()))) &&
            ((this.in3==null && other.getIn3()==null) || 
             (this.in3!=null &&
              this.in3.equals(other.getIn3()))) &&
            ((this.in4==null && other.getIn4()==null) || 
             (this.in4!=null &&
              this.in4.equals(other.getIn4()))) &&
            ((this.in5==null && other.getIn5()==null) || 
             (this.in5!=null &&
              this.in5.equals(other.getIn5())));
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
        _hashCode += getIn1();
        if (getIn2() != null) {
            _hashCode += getIn2().hashCode();
        }
        if (getIn3() != null) {
            _hashCode += getIn3().hashCode();
        }
        if (getIn4() != null) {
            _hashCode += getIn4().hashCode();
        }
        if (getIn5() != null) {
            _hashCode += getIn5().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BajaCallejero.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ln.localgis.com", ">bajaCallejero"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in0");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in0"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in5");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ln.localgis.com", "in5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
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
