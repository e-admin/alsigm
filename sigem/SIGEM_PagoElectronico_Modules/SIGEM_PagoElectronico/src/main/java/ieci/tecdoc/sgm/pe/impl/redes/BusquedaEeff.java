/**
 * BusquedaEeff.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

public class BusquedaEeff  implements java.io.Serializable {
    private java.lang.String codEntidad;

    private java.lang.String codOrganismo;

    private java.lang.String descripcionEntidad;

    private java.lang.String identificadorMedioPago;

    private java.lang.String idioma;

    private java.lang.String superorganismo;

    public BusquedaEeff() {
    }

    public BusquedaEeff(
           java.lang.String codEntidad,
           java.lang.String codOrganismo,
           java.lang.String descripcionEntidad,
           java.lang.String identificadorMedioPago,
           java.lang.String idioma,
           java.lang.String superorganismo) {
           this.codEntidad = codEntidad;
           this.codOrganismo = codOrganismo;
           this.descripcionEntidad = descripcionEntidad;
           this.identificadorMedioPago = identificadorMedioPago;
           this.idioma = idioma;
           this.superorganismo = superorganismo;
    }


    /**
     * Gets the codEntidad value for this BusquedaEeff.
     * 
     * @return codEntidad
     */
    public java.lang.String getCodEntidad() {
        return codEntidad;
    }


    /**
     * Sets the codEntidad value for this BusquedaEeff.
     * 
     * @param codEntidad
     */
    public void setCodEntidad(java.lang.String codEntidad) {
        this.codEntidad = codEntidad;
    }


    /**
     * Gets the codOrganismo value for this BusquedaEeff.
     * 
     * @return codOrganismo
     */
    public java.lang.String getCodOrganismo() {
        return codOrganismo;
    }


    /**
     * Sets the codOrganismo value for this BusquedaEeff.
     * 
     * @param codOrganismo
     */
    public void setCodOrganismo(java.lang.String codOrganismo) {
        this.codOrganismo = codOrganismo;
    }


    /**
     * Gets the descripcionEntidad value for this BusquedaEeff.
     * 
     * @return descripcionEntidad
     */
    public java.lang.String getDescripcionEntidad() {
        return descripcionEntidad;
    }


    /**
     * Sets the descripcionEntidad value for this BusquedaEeff.
     * 
     * @param descripcionEntidad
     */
    public void setDescripcionEntidad(java.lang.String descripcionEntidad) {
        this.descripcionEntidad = descripcionEntidad;
    }


    /**
     * Gets the identificadorMedioPago value for this BusquedaEeff.
     * 
     * @return identificadorMedioPago
     */
    public java.lang.String getIdentificadorMedioPago() {
        return identificadorMedioPago;
    }


    /**
     * Sets the identificadorMedioPago value for this BusquedaEeff.
     * 
     * @param identificadorMedioPago
     */
    public void setIdentificadorMedioPago(java.lang.String identificadorMedioPago) {
        this.identificadorMedioPago = identificadorMedioPago;
    }


    /**
     * Gets the idioma value for this BusquedaEeff.
     * 
     * @return idioma
     */
    public java.lang.String getIdioma() {
        return idioma;
    }


    /**
     * Sets the idioma value for this BusquedaEeff.
     * 
     * @param idioma
     */
    public void setIdioma(java.lang.String idioma) {
        this.idioma = idioma;
    }


    /**
     * Gets the superorganismo value for this BusquedaEeff.
     * 
     * @return superorganismo
     */
    public java.lang.String getSuperorganismo() {
        return superorganismo;
    }


    /**
     * Sets the superorganismo value for this BusquedaEeff.
     * 
     * @param superorganismo
     */
    public void setSuperorganismo(java.lang.String superorganismo) {
        this.superorganismo = superorganismo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BusquedaEeff)) return false;
        BusquedaEeff other = (BusquedaEeff) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codEntidad==null && other.getCodEntidad()==null) || 
             (this.codEntidad!=null &&
              this.codEntidad.equals(other.getCodEntidad()))) &&
            ((this.codOrganismo==null && other.getCodOrganismo()==null) || 
             (this.codOrganismo!=null &&
              this.codOrganismo.equals(other.getCodOrganismo()))) &&
            ((this.descripcionEntidad==null && other.getDescripcionEntidad()==null) || 
             (this.descripcionEntidad!=null &&
              this.descripcionEntidad.equals(other.getDescripcionEntidad()))) &&
            ((this.identificadorMedioPago==null && other.getIdentificadorMedioPago()==null) || 
             (this.identificadorMedioPago!=null &&
              this.identificadorMedioPago.equals(other.getIdentificadorMedioPago()))) &&
            ((this.idioma==null && other.getIdioma()==null) || 
             (this.idioma!=null &&
              this.idioma.equals(other.getIdioma()))) &&
            ((this.superorganismo==null && other.getSuperorganismo()==null) || 
             (this.superorganismo!=null &&
              this.superorganismo.equals(other.getSuperorganismo())));
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
        if (getCodEntidad() != null) {
            _hashCode += getCodEntidad().hashCode();
        }
        if (getCodOrganismo() != null) {
            _hashCode += getCodOrganismo().hashCode();
        }
        if (getDescripcionEntidad() != null) {
            _hashCode += getDescripcionEntidad().hashCode();
        }
        if (getIdentificadorMedioPago() != null) {
            _hashCode += getIdentificadorMedioPago().hashCode();
        }
        if (getIdioma() != null) {
            _hashCode += getIdioma().hashCode();
        }
        if (getSuperorganismo() != null) {
            _hashCode += getSuperorganismo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BusquedaEeff.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "BusquedaEeff"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codEntidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codOrganismo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codOrganismo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionEntidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadorMedioPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificadorMedioPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idioma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idioma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superorganismo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superorganismo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
