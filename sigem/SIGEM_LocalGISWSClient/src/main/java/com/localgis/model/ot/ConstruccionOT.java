/**
 * ConstruccionOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class ConstruccionOT  implements java.io.Serializable {
    private java.lang.String codigoUso;

    private java.lang.String escalera;

    private java.lang.String identificador;

    private java.lang.String planta;

    private java.lang.String puerta;

    private java.lang.Double superficieTotal;

    public ConstruccionOT() {
    }

    public ConstruccionOT(
           java.lang.String codigoUso,
           java.lang.String escalera,
           java.lang.String identificador,
           java.lang.String planta,
           java.lang.String puerta,
           java.lang.Double superficieTotal) {
           this.codigoUso = codigoUso;
           this.escalera = escalera;
           this.identificador = identificador;
           this.planta = planta;
           this.puerta = puerta;
           this.superficieTotal = superficieTotal;
    }


    /**
     * Gets the codigoUso value for this ConstruccionOT.
     * 
     * @return codigoUso
     */
    public java.lang.String getCodigoUso() {
        return codigoUso;
    }


    /**
     * Sets the codigoUso value for this ConstruccionOT.
     * 
     * @param codigoUso
     */
    public void setCodigoUso(java.lang.String codigoUso) {
        this.codigoUso = codigoUso;
    }


    /**
     * Gets the escalera value for this ConstruccionOT.
     * 
     * @return escalera
     */
    public java.lang.String getEscalera() {
        return escalera;
    }


    /**
     * Sets the escalera value for this ConstruccionOT.
     * 
     * @param escalera
     */
    public void setEscalera(java.lang.String escalera) {
        this.escalera = escalera;
    }


    /**
     * Gets the identificador value for this ConstruccionOT.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this ConstruccionOT.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the planta value for this ConstruccionOT.
     * 
     * @return planta
     */
    public java.lang.String getPlanta() {
        return planta;
    }


    /**
     * Sets the planta value for this ConstruccionOT.
     * 
     * @param planta
     */
    public void setPlanta(java.lang.String planta) {
        this.planta = planta;
    }


    /**
     * Gets the puerta value for this ConstruccionOT.
     * 
     * @return puerta
     */
    public java.lang.String getPuerta() {
        return puerta;
    }


    /**
     * Sets the puerta value for this ConstruccionOT.
     * 
     * @param puerta
     */
    public void setPuerta(java.lang.String puerta) {
        this.puerta = puerta;
    }


    /**
     * Gets the superficieTotal value for this ConstruccionOT.
     * 
     * @return superficieTotal
     */
    public java.lang.Double getSuperficieTotal() {
        return superficieTotal;
    }


    /**
     * Sets the superficieTotal value for this ConstruccionOT.
     * 
     * @param superficieTotal
     */
    public void setSuperficieTotal(java.lang.Double superficieTotal) {
        this.superficieTotal = superficieTotal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConstruccionOT)) return false;
        ConstruccionOT other = (ConstruccionOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoUso==null && other.getCodigoUso()==null) || 
             (this.codigoUso!=null &&
              this.codigoUso.equals(other.getCodigoUso()))) &&
            ((this.escalera==null && other.getEscalera()==null) || 
             (this.escalera!=null &&
              this.escalera.equals(other.getEscalera()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.planta==null && other.getPlanta()==null) || 
             (this.planta!=null &&
              this.planta.equals(other.getPlanta()))) &&
            ((this.puerta==null && other.getPuerta()==null) || 
             (this.puerta!=null &&
              this.puerta.equals(other.getPuerta()))) &&
            ((this.superficieTotal==null && other.getSuperficieTotal()==null) || 
             (this.superficieTotal!=null &&
              this.superficieTotal.equals(other.getSuperficieTotal())));
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
        if (getCodigoUso() != null) {
            _hashCode += getCodigoUso().hashCode();
        }
        if (getEscalera() != null) {
            _hashCode += getEscalera().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getPlanta() != null) {
            _hashCode += getPlanta().hashCode();
        }
        if (getPuerta() != null) {
            _hashCode += getPuerta().hashCode();
        }
        if (getSuperficieTotal() != null) {
            _hashCode += getSuperficieTotal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConstruccionOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "ConstruccionOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoUso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "codigoUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("escalera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "escalera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "planta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puerta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "puerta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "superficieTotal"));
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
