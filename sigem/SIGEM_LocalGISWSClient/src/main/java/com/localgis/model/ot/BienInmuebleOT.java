/**
 * BienInmuebleOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class BienInmuebleOT  implements java.io.Serializable {
    private java.lang.String claseBienInmueble;

    private java.lang.String claseUso;

    private com.localgis.model.ot.LocalizacionOT direccionLocalizacion;

    private com.localgis.model.ot.ArrayOfConstruccionOT lstConstrucciones;

    private com.localgis.model.ot.ArrayOfCultivoOT lstCultivos;

    private java.lang.String referencia_catastral;

    private java.lang.Double superficie;

    public BienInmuebleOT() {
    }

    public BienInmuebleOT(
           java.lang.String claseBienInmueble,
           java.lang.String claseUso,
           com.localgis.model.ot.LocalizacionOT direccionLocalizacion,
           com.localgis.model.ot.ArrayOfConstruccionOT lstConstrucciones,
           com.localgis.model.ot.ArrayOfCultivoOT lstCultivos,
           java.lang.String referencia_catastral,
           java.lang.Double superficie) {
           this.claseBienInmueble = claseBienInmueble;
           this.claseUso = claseUso;
           this.direccionLocalizacion = direccionLocalizacion;
           this.lstConstrucciones = lstConstrucciones;
           this.lstCultivos = lstCultivos;
           this.referencia_catastral = referencia_catastral;
           this.superficie = superficie;
    }


    /**
     * Gets the claseBienInmueble value for this BienInmuebleOT.
     * 
     * @return claseBienInmueble
     */
    public java.lang.String getClaseBienInmueble() {
        return claseBienInmueble;
    }


    /**
     * Sets the claseBienInmueble value for this BienInmuebleOT.
     * 
     * @param claseBienInmueble
     */
    public void setClaseBienInmueble(java.lang.String claseBienInmueble) {
        this.claseBienInmueble = claseBienInmueble;
    }


    /**
     * Gets the claseUso value for this BienInmuebleOT.
     * 
     * @return claseUso
     */
    public java.lang.String getClaseUso() {
        return claseUso;
    }


    /**
     * Sets the claseUso value for this BienInmuebleOT.
     * 
     * @param claseUso
     */
    public void setClaseUso(java.lang.String claseUso) {
        this.claseUso = claseUso;
    }


    /**
     * Gets the direccionLocalizacion value for this BienInmuebleOT.
     * 
     * @return direccionLocalizacion
     */
    public com.localgis.model.ot.LocalizacionOT getDireccionLocalizacion() {
        return direccionLocalizacion;
    }


    /**
     * Sets the direccionLocalizacion value for this BienInmuebleOT.
     * 
     * @param direccionLocalizacion
     */
    public void setDireccionLocalizacion(com.localgis.model.ot.LocalizacionOT direccionLocalizacion) {
        this.direccionLocalizacion = direccionLocalizacion;
    }


    /**
     * Gets the lstConstrucciones value for this BienInmuebleOT.
     * 
     * @return lstConstrucciones
     */
    public com.localgis.model.ot.ArrayOfConstruccionOT getLstConstrucciones() {
        return lstConstrucciones;
    }


    /**
     * Sets the lstConstrucciones value for this BienInmuebleOT.
     * 
     * @param lstConstrucciones
     */
    public void setLstConstrucciones(com.localgis.model.ot.ArrayOfConstruccionOT lstConstrucciones) {
        this.lstConstrucciones = lstConstrucciones;
    }


    /**
     * Gets the lstCultivos value for this BienInmuebleOT.
     * 
     * @return lstCultivos
     */
    public com.localgis.model.ot.ArrayOfCultivoOT getLstCultivos() {
        return lstCultivos;
    }


    /**
     * Sets the lstCultivos value for this BienInmuebleOT.
     * 
     * @param lstCultivos
     */
    public void setLstCultivos(com.localgis.model.ot.ArrayOfCultivoOT lstCultivos) {
        this.lstCultivos = lstCultivos;
    }


    /**
     * Gets the referencia_catastral value for this BienInmuebleOT.
     * 
     * @return referencia_catastral
     */
    public java.lang.String getReferencia_catastral() {
        return referencia_catastral;
    }


    /**
     * Sets the referencia_catastral value for this BienInmuebleOT.
     * 
     * @param referencia_catastral
     */
    public void setReferencia_catastral(java.lang.String referencia_catastral) {
        this.referencia_catastral = referencia_catastral;
    }


    /**
     * Gets the superficie value for this BienInmuebleOT.
     * 
     * @return superficie
     */
    public java.lang.Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this BienInmuebleOT.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BienInmuebleOT)) return false;
        BienInmuebleOT other = (BienInmuebleOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.claseBienInmueble==null && other.getClaseBienInmueble()==null) || 
             (this.claseBienInmueble!=null &&
              this.claseBienInmueble.equals(other.getClaseBienInmueble()))) &&
            ((this.claseUso==null && other.getClaseUso()==null) || 
             (this.claseUso!=null &&
              this.claseUso.equals(other.getClaseUso()))) &&
            ((this.direccionLocalizacion==null && other.getDireccionLocalizacion()==null) || 
             (this.direccionLocalizacion!=null &&
              this.direccionLocalizacion.equals(other.getDireccionLocalizacion()))) &&
            ((this.lstConstrucciones==null && other.getLstConstrucciones()==null) || 
             (this.lstConstrucciones!=null &&
              this.lstConstrucciones.equals(other.getLstConstrucciones()))) &&
            ((this.lstCultivos==null && other.getLstCultivos()==null) || 
             (this.lstCultivos!=null &&
              this.lstCultivos.equals(other.getLstCultivos()))) &&
            ((this.referencia_catastral==null && other.getReferencia_catastral()==null) || 
             (this.referencia_catastral!=null &&
              this.referencia_catastral.equals(other.getReferencia_catastral()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie())));
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
        if (getClaseBienInmueble() != null) {
            _hashCode += getClaseBienInmueble().hashCode();
        }
        if (getClaseUso() != null) {
            _hashCode += getClaseUso().hashCode();
        }
        if (getDireccionLocalizacion() != null) {
            _hashCode += getDireccionLocalizacion().hashCode();
        }
        if (getLstConstrucciones() != null) {
            _hashCode += getLstConstrucciones().hashCode();
        }
        if (getLstCultivos() != null) {
            _hashCode += getLstCultivos().hashCode();
        }
        if (getReferencia_catastral() != null) {
            _hashCode += getReferencia_catastral().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BienInmuebleOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "BienInmuebleOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claseBienInmueble");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "claseBienInmueble"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claseUso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "claseUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionLocalizacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "direccionLocalizacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "LocalizacionOT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lstConstrucciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "lstConstrucciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfConstruccionOT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lstCultivos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "lstCultivos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ArrayOfCultivoOT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia_catastral");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "referencia_catastral"));
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
