/**
 * Servicio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.ieci.scsp.verifdata.model.dao;

public class Servicio  implements java.io.Serializable {
    private java.lang.String codcertificado;

    private java.lang.String coreEmisorCertificado;

    private java.lang.String descripcion;

    public Servicio() {
    }

    public Servicio(
           java.lang.String codcertificado,
           java.lang.String coreEmisorCertificado,
           java.lang.String descripcion) {
           this.codcertificado = codcertificado;
           this.coreEmisorCertificado = coreEmisorCertificado;
           this.descripcion = descripcion;
    }


    /**
     * Gets the codcertificado value for this Servicio.
     * 
     * @return codcertificado
     */
    public java.lang.String getCodcertificado() {
        return codcertificado;
    }


    /**
     * Sets the codcertificado value for this Servicio.
     * 
     * @param codcertificado
     */
    public void setCodcertificado(java.lang.String codcertificado) {
        this.codcertificado = codcertificado;
    }


    /**
     * Gets the coreEmisorCertificado value for this Servicio.
     * 
     * @return coreEmisorCertificado
     */
    public java.lang.String getCoreEmisorCertificado() {
        return coreEmisorCertificado;
    }


    /**
     * Sets the coreEmisorCertificado value for this Servicio.
     * 
     * @param coreEmisorCertificado
     */
    public void setCoreEmisorCertificado(java.lang.String coreEmisorCertificado) {
        this.coreEmisorCertificado = coreEmisorCertificado;
    }


    /**
     * Gets the descripcion value for this Servicio.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this Servicio.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Servicio)) return false;
        Servicio other = (Servicio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codcertificado==null && other.getCodcertificado()==null) || 
             (this.codcertificado!=null &&
              this.codcertificado.equals(other.getCodcertificado()))) &&
            ((this.coreEmisorCertificado==null && other.getCoreEmisorCertificado()==null) || 
             (this.coreEmisorCertificado!=null &&
              this.coreEmisorCertificado.equals(other.getCoreEmisorCertificado()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion())));
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
        if (getCodcertificado() != null) {
            _hashCode += getCodcertificado().hashCode();
        }
        if (getCoreEmisorCertificado() != null) {
            _hashCode += getCoreEmisorCertificado().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Servicio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dao.model.verifdata.scsp.ieci.es", "Servicio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codcertificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dao.model.verifdata.scsp.ieci.es", "codcertificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coreEmisorCertificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dao.model.verifdata.scsp.ieci.es", "coreEmisorCertificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dao.model.verifdata.scsp.ieci.es", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
