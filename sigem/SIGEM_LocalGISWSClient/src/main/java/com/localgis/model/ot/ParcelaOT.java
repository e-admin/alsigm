/**
 * ParcelaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ParcelaOT  implements java.io.Serializable {
    private com.localgis.model.ot.LocalizacionOT direccion;

    private com.localgis.model.ot.ArrayOfBienInmuebleOT lstBienesInmuebles;

    private java.lang.String refCatastral;

    private java.lang.Double superficie;

    private java.lang.Double superficieConstruida;

    public ParcelaOT() {
    }

    public ParcelaOT(
           com.localgis.model.ot.LocalizacionOT direccion,
           com.localgis.model.ot.ArrayOfBienInmuebleOT lstBienesInmuebles,
           java.lang.String refCatastral,
           java.lang.Double superficie,
           java.lang.Double superficieConstruida) {
           this.direccion = direccion;
           this.lstBienesInmuebles = lstBienesInmuebles;
           this.refCatastral = refCatastral;
           this.superficie = superficie;
           this.superficieConstruida = superficieConstruida;
    }


    /**
     * Gets the direccion value for this ParcelaOT.
     * 
     * @return direccion
     */
    public com.localgis.model.ot.LocalizacionOT getDireccion() {
        return direccion;
    }


    /**
     * Sets the direccion value for this ParcelaOT.
     * 
     * @param direccion
     */
    public void setDireccion(com.localgis.model.ot.LocalizacionOT direccion) {
        this.direccion = direccion;
    }


    /**
     * Gets the lstBienesInmuebles value for this ParcelaOT.
     * 
     * @return lstBienesInmuebles
     */
    public com.localgis.model.ot.ArrayOfBienInmuebleOT getLstBienesInmuebles() {
        return lstBienesInmuebles;
    }


    /**
     * Sets the lstBienesInmuebles value for this ParcelaOT.
     * 
     * @param lstBienesInmuebles
     */
    public void setLstBienesInmuebles(com.localgis.model.ot.ArrayOfBienInmuebleOT lstBienesInmuebles) {
        this.lstBienesInmuebles = lstBienesInmuebles;
    }


    /**
     * Gets the refCatastral value for this ParcelaOT.
     * 
     * @return refCatastral
     */
    public java.lang.String getRefCatastral() {
        return refCatastral;
    }


    /**
     * Sets the refCatastral value for this ParcelaOT.
     * 
     * @param refCatastral
     */
    public void setRefCatastral(java.lang.String refCatastral) {
        this.refCatastral = refCatastral;
    }


    /**
     * Gets the superficie value for this ParcelaOT.
     * 
     * @return superficie
     */
    public java.lang.Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this ParcelaOT.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the superficieConstruida value for this ParcelaOT.
     * 
     * @return superficieConstruida
     */
    public java.lang.Double getSuperficieConstruida() {
        return superficieConstruida;
    }


    /**
     * Sets the superficieConstruida value for this ParcelaOT.
     * 
     * @param superficieConstruida
     */
    public void setSuperficieConstruida(java.lang.Double superficieConstruida) {
        this.superficieConstruida = superficieConstruida;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ParcelaOT)) return false;
        ParcelaOT other = (ParcelaOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.direccion==null && other.getDireccion()==null) || 
             (this.direccion!=null &&
              this.direccion.equals(other.getDireccion()))) &&
            ((this.lstBienesInmuebles==null && other.getLstBienesInmuebles()==null) || 
             (this.lstBienesInmuebles!=null &&
              this.lstBienesInmuebles.equals(other.getLstBienesInmuebles()))) &&
            ((this.refCatastral==null && other.getRefCatastral()==null) || 
             (this.refCatastral!=null &&
              this.refCatastral.equals(other.getRefCatastral()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.superficieConstruida==null && other.getSuperficieConstruida()==null) || 
             (this.superficieConstruida!=null &&
              this.superficieConstruida.equals(other.getSuperficieConstruida())));
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
        if (getDireccion() != null) {
            _hashCode += getDireccion().hashCode();
        }
        if (getLstBienesInmuebles() != null) {
            _hashCode += getLstBienesInmuebles().hashCode();
        }
        if (getRefCatastral() != null) {
            _hashCode += getRefCatastral().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getSuperficieConstruida() != null) {
            _hashCode += getSuperficieConstruida().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ParcelaOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ParcelaOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "direccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "LocalizacionOT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lstBienesInmuebles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "lstBienesInmuebles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfBienInmuebleOT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refCatastral");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "refCatastral"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieConstruida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "superficieConstruida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
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
