/**
 * LocalizacionOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class LocalizacionOT  implements java.io.Serializable {
    private java.lang.String bloque;

    private java.lang.Integer codigoPostal;

    private java.lang.Double kilometro;

    private java.lang.String nombreMunicipio;

    private java.lang.String nombreProvincia;

    private java.lang.String nombreVia;

    private java.lang.Integer primerNumero;

    private java.lang.String primeraLetra;

    private java.lang.String segundaLetra;

    private java.lang.Integer segundoNumero;

    public LocalizacionOT() {
    }

    public LocalizacionOT(
           java.lang.String bloque,
           java.lang.Integer codigoPostal,
           java.lang.Double kilometro,
           java.lang.String nombreMunicipio,
           java.lang.String nombreProvincia,
           java.lang.String nombreVia,
           java.lang.Integer primerNumero,
           java.lang.String primeraLetra,
           java.lang.String segundaLetra,
           java.lang.Integer segundoNumero) {
           this.bloque = bloque;
           this.codigoPostal = codigoPostal;
           this.kilometro = kilometro;
           this.nombreMunicipio = nombreMunicipio;
           this.nombreProvincia = nombreProvincia;
           this.nombreVia = nombreVia;
           this.primerNumero = primerNumero;
           this.primeraLetra = primeraLetra;
           this.segundaLetra = segundaLetra;
           this.segundoNumero = segundoNumero;
    }


    /**
     * Gets the bloque value for this LocalizacionOT.
     * 
     * @return bloque
     */
    public java.lang.String getBloque() {
        return bloque;
    }


    /**
     * Sets the bloque value for this LocalizacionOT.
     * 
     * @param bloque
     */
    public void setBloque(java.lang.String bloque) {
        this.bloque = bloque;
    }


    /**
     * Gets the codigoPostal value for this LocalizacionOT.
     * 
     * @return codigoPostal
     */
    public java.lang.Integer getCodigoPostal() {
        return codigoPostal;
    }


    /**
     * Sets the codigoPostal value for this LocalizacionOT.
     * 
     * @param codigoPostal
     */
    public void setCodigoPostal(java.lang.Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }


    /**
     * Gets the kilometro value for this LocalizacionOT.
     * 
     * @return kilometro
     */
    public java.lang.Double getKilometro() {
        return kilometro;
    }


    /**
     * Sets the kilometro value for this LocalizacionOT.
     * 
     * @param kilometro
     */
    public void setKilometro(java.lang.Double kilometro) {
        this.kilometro = kilometro;
    }


    /**
     * Gets the nombreMunicipio value for this LocalizacionOT.
     * 
     * @return nombreMunicipio
     */
    public java.lang.String getNombreMunicipio() {
        return nombreMunicipio;
    }


    /**
     * Sets the nombreMunicipio value for this LocalizacionOT.
     * 
     * @param nombreMunicipio
     */
    public void setNombreMunicipio(java.lang.String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }


    /**
     * Gets the nombreProvincia value for this LocalizacionOT.
     * 
     * @return nombreProvincia
     */
    public java.lang.String getNombreProvincia() {
        return nombreProvincia;
    }


    /**
     * Sets the nombreProvincia value for this LocalizacionOT.
     * 
     * @param nombreProvincia
     */
    public void setNombreProvincia(java.lang.String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }


    /**
     * Gets the nombreVia value for this LocalizacionOT.
     * 
     * @return nombreVia
     */
    public java.lang.String getNombreVia() {
        return nombreVia;
    }


    /**
     * Sets the nombreVia value for this LocalizacionOT.
     * 
     * @param nombreVia
     */
    public void setNombreVia(java.lang.String nombreVia) {
        this.nombreVia = nombreVia;
    }


    /**
     * Gets the primerNumero value for this LocalizacionOT.
     * 
     * @return primerNumero
     */
    public java.lang.Integer getPrimerNumero() {
        return primerNumero;
    }


    /**
     * Sets the primerNumero value for this LocalizacionOT.
     * 
     * @param primerNumero
     */
    public void setPrimerNumero(java.lang.Integer primerNumero) {
        this.primerNumero = primerNumero;
    }


    /**
     * Gets the primeraLetra value for this LocalizacionOT.
     * 
     * @return primeraLetra
     */
    public java.lang.String getPrimeraLetra() {
        return primeraLetra;
    }


    /**
     * Sets the primeraLetra value for this LocalizacionOT.
     * 
     * @param primeraLetra
     */
    public void setPrimeraLetra(java.lang.String primeraLetra) {
        this.primeraLetra = primeraLetra;
    }


    /**
     * Gets the segundaLetra value for this LocalizacionOT.
     * 
     * @return segundaLetra
     */
    public java.lang.String getSegundaLetra() {
        return segundaLetra;
    }


    /**
     * Sets the segundaLetra value for this LocalizacionOT.
     * 
     * @param segundaLetra
     */
    public void setSegundaLetra(java.lang.String segundaLetra) {
        this.segundaLetra = segundaLetra;
    }


    /**
     * Gets the segundoNumero value for this LocalizacionOT.
     * 
     * @return segundoNumero
     */
    public java.lang.Integer getSegundoNumero() {
        return segundoNumero;
    }


    /**
     * Sets the segundoNumero value for this LocalizacionOT.
     * 
     * @param segundoNumero
     */
    public void setSegundoNumero(java.lang.Integer segundoNumero) {
        this.segundoNumero = segundoNumero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LocalizacionOT)) return false;
        LocalizacionOT other = (LocalizacionOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bloque==null && other.getBloque()==null) || 
             (this.bloque!=null &&
              this.bloque.equals(other.getBloque()))) &&
            ((this.codigoPostal==null && other.getCodigoPostal()==null) || 
             (this.codigoPostal!=null &&
              this.codigoPostal.equals(other.getCodigoPostal()))) &&
            ((this.kilometro==null && other.getKilometro()==null) || 
             (this.kilometro!=null &&
              this.kilometro.equals(other.getKilometro()))) &&
            ((this.nombreMunicipio==null && other.getNombreMunicipio()==null) || 
             (this.nombreMunicipio!=null &&
              this.nombreMunicipio.equals(other.getNombreMunicipio()))) &&
            ((this.nombreProvincia==null && other.getNombreProvincia()==null) || 
             (this.nombreProvincia!=null &&
              this.nombreProvincia.equals(other.getNombreProvincia()))) &&
            ((this.nombreVia==null && other.getNombreVia()==null) || 
             (this.nombreVia!=null &&
              this.nombreVia.equals(other.getNombreVia()))) &&
            ((this.primerNumero==null && other.getPrimerNumero()==null) || 
             (this.primerNumero!=null &&
              this.primerNumero.equals(other.getPrimerNumero()))) &&
            ((this.primeraLetra==null && other.getPrimeraLetra()==null) || 
             (this.primeraLetra!=null &&
              this.primeraLetra.equals(other.getPrimeraLetra()))) &&
            ((this.segundaLetra==null && other.getSegundaLetra()==null) || 
             (this.segundaLetra!=null &&
              this.segundaLetra.equals(other.getSegundaLetra()))) &&
            ((this.segundoNumero==null && other.getSegundoNumero()==null) || 
             (this.segundoNumero!=null &&
              this.segundoNumero.equals(other.getSegundoNumero())));
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
        if (getBloque() != null) {
            _hashCode += getBloque().hashCode();
        }
        if (getCodigoPostal() != null) {
            _hashCode += getCodigoPostal().hashCode();
        }
        if (getKilometro() != null) {
            _hashCode += getKilometro().hashCode();
        }
        if (getNombreMunicipio() != null) {
            _hashCode += getNombreMunicipio().hashCode();
        }
        if (getNombreProvincia() != null) {
            _hashCode += getNombreProvincia().hashCode();
        }
        if (getNombreVia() != null) {
            _hashCode += getNombreVia().hashCode();
        }
        if (getPrimerNumero() != null) {
            _hashCode += getPrimerNumero().hashCode();
        }
        if (getPrimeraLetra() != null) {
            _hashCode += getPrimeraLetra().hashCode();
        }
        if (getSegundaLetra() != null) {
            _hashCode += getSegundaLetra().hashCode();
        }
        if (getSegundoNumero() != null) {
            _hashCode += getSegundoNumero().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LocalizacionOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "LocalizacionOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bloque");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "bloque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPostal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "codigoPostal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("kilometro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "kilometro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombreMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombreProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombreVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primerNumero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "primerNumero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primeraLetra");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "primeraLetra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segundaLetra");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "segundaLetra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segundoNumero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "segundoNumero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
