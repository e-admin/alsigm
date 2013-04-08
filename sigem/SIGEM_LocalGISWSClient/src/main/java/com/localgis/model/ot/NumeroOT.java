/**
 * NumeroOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class NumeroOT  implements java.io.Serializable {
    private java.lang.String calificador;

    private java.lang.Integer codigoINEMunicipio;

    private java.util.Calendar fechaEjecucion;

    private java.lang.Integer idVia;

    private java.lang.Integer idacl;

    private java.lang.String numero;

    public NumeroOT() {
    }

    public NumeroOT(
           java.lang.String calificador,
           java.lang.Integer codigoINEMunicipio,
           java.util.Calendar fechaEjecucion,
           java.lang.Integer idVia,
           java.lang.Integer idacl,
           java.lang.String numero) {
           this.calificador = calificador;
           this.codigoINEMunicipio = codigoINEMunicipio;
           this.fechaEjecucion = fechaEjecucion;
           this.idVia = idVia;
           this.idacl = idacl;
           this.numero = numero;
    }


    /**
     * Gets the calificador value for this NumeroOT.
     * 
     * @return calificador
     */
    public java.lang.String getCalificador() {
        return calificador;
    }


    /**
     * Sets the calificador value for this NumeroOT.
     * 
     * @param calificador
     */
    public void setCalificador(java.lang.String calificador) {
        this.calificador = calificador;
    }


    /**
     * Gets the codigoINEMunicipio value for this NumeroOT.
     * 
     * @return codigoINEMunicipio
     */
    public java.lang.Integer getCodigoINEMunicipio() {
        return codigoINEMunicipio;
    }


    /**
     * Sets the codigoINEMunicipio value for this NumeroOT.
     * 
     * @param codigoINEMunicipio
     */
    public void setCodigoINEMunicipio(java.lang.Integer codigoINEMunicipio) {
        this.codigoINEMunicipio = codigoINEMunicipio;
    }


    /**
     * Gets the fechaEjecucion value for this NumeroOT.
     * 
     * @return fechaEjecucion
     */
    public java.util.Calendar getFechaEjecucion() {
        return fechaEjecucion;
    }


    /**
     * Sets the fechaEjecucion value for this NumeroOT.
     * 
     * @param fechaEjecucion
     */
    public void setFechaEjecucion(java.util.Calendar fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }


    /**
     * Gets the idVia value for this NumeroOT.
     * 
     * @return idVia
     */
    public java.lang.Integer getIdVia() {
        return idVia;
    }


    /**
     * Sets the idVia value for this NumeroOT.
     * 
     * @param idVia
     */
    public void setIdVia(java.lang.Integer idVia) {
        this.idVia = idVia;
    }


    /**
     * Gets the idacl value for this NumeroOT.
     * 
     * @return idacl
     */
    public java.lang.Integer getIdacl() {
        return idacl;
    }


    /**
     * Sets the idacl value for this NumeroOT.
     * 
     * @param idacl
     */
    public void setIdacl(java.lang.Integer idacl) {
        this.idacl = idacl;
    }


    /**
     * Gets the numero value for this NumeroOT.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this NumeroOT.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NumeroOT)) return false;
        NumeroOT other = (NumeroOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.calificador==null && other.getCalificador()==null) || 
             (this.calificador!=null &&
              this.calificador.equals(other.getCalificador()))) &&
            ((this.codigoINEMunicipio==null && other.getCodigoINEMunicipio()==null) || 
             (this.codigoINEMunicipio!=null &&
              this.codigoINEMunicipio.equals(other.getCodigoINEMunicipio()))) &&
            ((this.fechaEjecucion==null && other.getFechaEjecucion()==null) || 
             (this.fechaEjecucion!=null &&
              this.fechaEjecucion.equals(other.getFechaEjecucion()))) &&
            ((this.idVia==null && other.getIdVia()==null) || 
             (this.idVia!=null &&
              this.idVia.equals(other.getIdVia()))) &&
            ((this.idacl==null && other.getIdacl()==null) || 
             (this.idacl!=null &&
              this.idacl.equals(other.getIdacl()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero())));
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
        if (getCalificador() != null) {
            _hashCode += getCalificador().hashCode();
        }
        if (getCodigoINEMunicipio() != null) {
            _hashCode += getCodigoINEMunicipio().hashCode();
        }
        if (getFechaEjecucion() != null) {
            _hashCode += getFechaEjecucion().hashCode();
        }
        if (getIdVia() != null) {
            _hashCode += getIdVia().hashCode();
        }
        if (getIdacl() != null) {
            _hashCode += getIdacl().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NumeroOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "NumeroOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("calificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "calificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoINEMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "codigoINEMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEjecucion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "fechaEjecucion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "idVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idacl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "idacl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "numero"));
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
