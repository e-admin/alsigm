/**
 * CapaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class CapaOT  implements java.io.Serializable {
    private java.lang.String nombreCapa;

    private java.lang.String patron;

    private com.localgis.model.ot.ArrayOfSubtipoOT subtipos;

    public CapaOT() {
    }

    public CapaOT(
           java.lang.String nombreCapa,
           java.lang.String patron,
           com.localgis.model.ot.ArrayOfSubtipoOT subtipos) {
           this.nombreCapa = nombreCapa;
           this.patron = patron;
           this.subtipos = subtipos;
    }


    /**
     * Gets the nombreCapa value for this CapaOT.
     * 
     * @return nombreCapa
     */
    public java.lang.String getNombreCapa() {
        return nombreCapa;
    }


    /**
     * Sets the nombreCapa value for this CapaOT.
     * 
     * @param nombreCapa
     */
    public void setNombreCapa(java.lang.String nombreCapa) {
        this.nombreCapa = nombreCapa;
    }


    /**
     * Gets the patron value for this CapaOT.
     * 
     * @return patron
     */
    public java.lang.String getPatron() {
        return patron;
    }


    /**
     * Sets the patron value for this CapaOT.
     * 
     * @param patron
     */
    public void setPatron(java.lang.String patron) {
        this.patron = patron;
    }


    /**
     * Gets the subtipos value for this CapaOT.
     * 
     * @return subtipos
     */
    public com.localgis.model.ot.ArrayOfSubtipoOT getSubtipos() {
        return subtipos;
    }


    /**
     * Sets the subtipos value for this CapaOT.
     * 
     * @param subtipos
     */
    public void setSubtipos(com.localgis.model.ot.ArrayOfSubtipoOT subtipos) {
        this.subtipos = subtipos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CapaOT)) return false;
        CapaOT other = (CapaOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nombreCapa==null && other.getNombreCapa()==null) || 
             (this.nombreCapa!=null &&
              this.nombreCapa.equals(other.getNombreCapa()))) &&
            ((this.patron==null && other.getPatron()==null) || 
             (this.patron!=null &&
              this.patron.equals(other.getPatron()))) &&
            ((this.subtipos==null && other.getSubtipos()==null) || 
             (this.subtipos!=null &&
              this.subtipos.equals(other.getSubtipos())));
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
        if (getNombreCapa() != null) {
            _hashCode += getNombreCapa().hashCode();
        }
        if (getPatron() != null) {
            _hashCode += getPatron().hashCode();
        }
        if (getSubtipos() != null) {
            _hashCode += getSubtipos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CapaOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "CapaOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCapa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombreCapa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patron");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "patron"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subtipos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "subtipos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfSubtipoOT"));
        elemField.setMinOccurs(0);
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
