/**
 * MunicipioOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class MunicipioOT  implements java.io.Serializable {
    private java.lang.Integer codigoINE;

    private java.lang.String nombreCoOficial;

    private java.lang.String nombreOficial;

    public MunicipioOT() {
    }

    public MunicipioOT(
           java.lang.Integer codigoINE,
           java.lang.String nombreCoOficial,
           java.lang.String nombreOficial) {
           this.codigoINE = codigoINE;
           this.nombreCoOficial = nombreCoOficial;
           this.nombreOficial = nombreOficial;
    }


    /**
     * Gets the codigoINE value for this MunicipioOT.
     * 
     * @return codigoINE
     */
    public java.lang.Integer getCodigoINE() {
        return codigoINE;
    }


    /**
     * Sets the codigoINE value for this MunicipioOT.
     * 
     * @param codigoINE
     */
    public void setCodigoINE(java.lang.Integer codigoINE) {
        this.codigoINE = codigoINE;
    }


    /**
     * Gets the nombreCoOficial value for this MunicipioOT.
     * 
     * @return nombreCoOficial
     */
    public java.lang.String getNombreCoOficial() {
        return nombreCoOficial;
    }


    /**
     * Sets the nombreCoOficial value for this MunicipioOT.
     * 
     * @param nombreCoOficial
     */
    public void setNombreCoOficial(java.lang.String nombreCoOficial) {
        this.nombreCoOficial = nombreCoOficial;
    }


    /**
     * Gets the nombreOficial value for this MunicipioOT.
     * 
     * @return nombreOficial
     */
    public java.lang.String getNombreOficial() {
        return nombreOficial;
    }


    /**
     * Sets the nombreOficial value for this MunicipioOT.
     * 
     * @param nombreOficial
     */
    public void setNombreOficial(java.lang.String nombreOficial) {
        this.nombreOficial = nombreOficial;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MunicipioOT)) return false;
        MunicipioOT other = (MunicipioOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoINE==null && other.getCodigoINE()==null) || 
             (this.codigoINE!=null &&
              this.codigoINE.equals(other.getCodigoINE()))) &&
            ((this.nombreCoOficial==null && other.getNombreCoOficial()==null) || 
             (this.nombreCoOficial!=null &&
              this.nombreCoOficial.equals(other.getNombreCoOficial()))) &&
            ((this.nombreOficial==null && other.getNombreOficial()==null) || 
             (this.nombreOficial!=null &&
              this.nombreOficial.equals(other.getNombreOficial())));
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
        if (getCodigoINE() != null) {
            _hashCode += getCodigoINE().hashCode();
        }
        if (getNombreCoOficial() != null) {
            _hashCode += getNombreCoOficial().hashCode();
        }
        if (getNombreOficial() != null) {
            _hashCode += getNombreOficial().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MunicipioOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "MunicipioOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoINE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "codigoINE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCoOficial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombreCoOficial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreOficial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombreOficial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
