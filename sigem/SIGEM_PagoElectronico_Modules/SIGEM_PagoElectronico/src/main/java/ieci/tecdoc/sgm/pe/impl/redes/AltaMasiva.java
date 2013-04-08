/**
 * AltaMasiva.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

import ieci.tecdoc.sgm.pe.impl.redes.AltaMasiva;
import ieci.tecdoc.sgm.pe.impl.redes.InfoAccessor;

public class AltaMasiva  extends InfoAccessor  implements java.io.Serializable {
    private java.lang.String codOrganismo;

    private byte[] datos;

    private java.lang.String organismoEmisor;

    private java.lang.String superorganismo;

    public AltaMasiva() {
    }

    public AltaMasiva(
           java.lang.String[] columsInfoOrder,
           int[] typesInfoOrder,
           java.lang.String usuarioPeticion,
           java.lang.String codOrganismo,
           byte[] datos,
           java.lang.String organismoEmisor,
           java.lang.String superorganismo) {
        super(
            columsInfoOrder,
            typesInfoOrder,
            usuarioPeticion);
        this.codOrganismo = codOrganismo;
        this.datos = datos;
        this.organismoEmisor = organismoEmisor;
        this.superorganismo = superorganismo;
    }


    /**
     * Gets the codOrganismo value for this AltaMasiva.
     * 
     * @return codOrganismo
     */
    public java.lang.String getCodOrganismo() {
        return codOrganismo;
    }


    /**
     * Sets the codOrganismo value for this AltaMasiva.
     * 
     * @param codOrganismo
     */
    public void setCodOrganismo(java.lang.String codOrganismo) {
        this.codOrganismo = codOrganismo;
    }


    /**
     * Gets the datos value for this AltaMasiva.
     * 
     * @return datos
     */
    public byte[] getDatos() {
        return datos;
    }


    /**
     * Sets the datos value for this AltaMasiva.
     * 
     * @param datos
     */
    public void setDatos(byte[] datos) {
        this.datos = datos;
    }


    /**
     * Gets the organismoEmisor value for this AltaMasiva.
     * 
     * @return organismoEmisor
     */
    public java.lang.String getOrganismoEmisor() {
        return organismoEmisor;
    }


    /**
     * Sets the organismoEmisor value for this AltaMasiva.
     * 
     * @param organismoEmisor
     */
    public void setOrganismoEmisor(java.lang.String organismoEmisor) {
        this.organismoEmisor = organismoEmisor;
    }


    /**
     * Gets the superorganismo value for this AltaMasiva.
     * 
     * @return superorganismo
     */
    public java.lang.String getSuperorganismo() {
        return superorganismo;
    }


    /**
     * Sets the superorganismo value for this AltaMasiva.
     * 
     * @param superorganismo
     */
    public void setSuperorganismo(java.lang.String superorganismo) {
        this.superorganismo = superorganismo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AltaMasiva)) return false;
        AltaMasiva other = (AltaMasiva) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codOrganismo==null && other.getCodOrganismo()==null) || 
             (this.codOrganismo!=null &&
              this.codOrganismo.equals(other.getCodOrganismo()))) &&
            ((this.datos==null && other.getDatos()==null) || 
             (this.datos!=null &&
              java.util.Arrays.equals(this.datos, other.getDatos()))) &&
            ((this.organismoEmisor==null && other.getOrganismoEmisor()==null) || 
             (this.organismoEmisor!=null &&
              this.organismoEmisor.equals(other.getOrganismoEmisor()))) &&
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
        int _hashCode = super.hashCode();
        if (getCodOrganismo() != null) {
            _hashCode += getCodOrganismo().hashCode();
        }
        if (getDatos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOrganismoEmisor() != null) {
            _hashCode += getOrganismoEmisor().hashCode();
        }
        if (getSuperorganismo() != null) {
            _hashCode += getSuperorganismo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AltaMasiva.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "AltaMasiva"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codOrganismo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codOrganismo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organismoEmisor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organismoEmisor"));
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
