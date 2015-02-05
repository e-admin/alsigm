/**
 * CriterioBusquedaLiquidacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;

public class CriterioBusquedaLiquidacion  implements java.io.Serializable {
    private java.lang.String ejercicio;

    private java.lang.String estado;

    private java.lang.String idEntidadEmisora;

    private java.lang.String idTasa;

    private java.lang.String nif;

    private java.lang.String nrc;

    private java.lang.String referencia;

    public CriterioBusquedaLiquidacion() {
    }

    public CriterioBusquedaLiquidacion(
           java.lang.String ejercicio,
           java.lang.String estado,
           java.lang.String idEntidadEmisora,
           java.lang.String idTasa,
           java.lang.String nif,
           java.lang.String nrc,
           java.lang.String referencia) {
           this.ejercicio = ejercicio;
           this.estado = estado;
           this.idEntidadEmisora = idEntidadEmisora;
           this.idTasa = idTasa;
           this.nif = nif;
           this.nrc = nrc;
           this.referencia = referencia;
    }


    /**
     * Gets the ejercicio value for this CriterioBusquedaLiquidacion.
     * 
     * @return ejercicio
     */
    public java.lang.String getEjercicio() {
        return ejercicio;
    }


    /**
     * Sets the ejercicio value for this CriterioBusquedaLiquidacion.
     * 
     * @param ejercicio
     */
    public void setEjercicio(java.lang.String ejercicio) {
        this.ejercicio = ejercicio;
    }


    /**
     * Gets the estado value for this CriterioBusquedaLiquidacion.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this CriterioBusquedaLiquidacion.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the idEntidadEmisora value for this CriterioBusquedaLiquidacion.
     * 
     * @return idEntidadEmisora
     */
    public java.lang.String getIdEntidadEmisora() {
        return idEntidadEmisora;
    }


    /**
     * Sets the idEntidadEmisora value for this CriterioBusquedaLiquidacion.
     * 
     * @param idEntidadEmisora
     */
    public void setIdEntidadEmisora(java.lang.String idEntidadEmisora) {
        this.idEntidadEmisora = idEntidadEmisora;
    }


    /**
     * Gets the idTasa value for this CriterioBusquedaLiquidacion.
     * 
     * @return idTasa
     */
    public java.lang.String getIdTasa() {
        return idTasa;
    }


    /**
     * Sets the idTasa value for this CriterioBusquedaLiquidacion.
     * 
     * @param idTasa
     */
    public void setIdTasa(java.lang.String idTasa) {
        this.idTasa = idTasa;
    }


    /**
     * Gets the nif value for this CriterioBusquedaLiquidacion.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this CriterioBusquedaLiquidacion.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the nrc value for this CriterioBusquedaLiquidacion.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this CriterioBusquedaLiquidacion.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the referencia value for this CriterioBusquedaLiquidacion.
     * 
     * @return referencia
     */
    public java.lang.String getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this CriterioBusquedaLiquidacion.
     * 
     * @param referencia
     */
    public void setReferencia(java.lang.String referencia) {
        this.referencia = referencia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CriterioBusquedaLiquidacion)) return false;
        CriterioBusquedaLiquidacion other = (CriterioBusquedaLiquidacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ejercicio==null && other.getEjercicio()==null) || 
             (this.ejercicio!=null &&
              this.ejercicio.equals(other.getEjercicio()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.idEntidadEmisora==null && other.getIdEntidadEmisora()==null) || 
             (this.idEntidadEmisora!=null &&
              this.idEntidadEmisora.equals(other.getIdEntidadEmisora()))) &&
            ((this.idTasa==null && other.getIdTasa()==null) || 
             (this.idTasa!=null &&
              this.idTasa.equals(other.getIdTasa()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia())));
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
        if (getEjercicio() != null) {
            _hashCode += getEjercicio().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getIdEntidadEmisora() != null) {
            _hashCode += getIdEntidadEmisora().hashCode();
        }
        if (getIdTasa() != null) {
            _hashCode += getIdTasa().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CriterioBusquedaLiquidacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "CriterioBusquedaLiquidacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ejercicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ejercicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidadEmisora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "idEntidadEmisora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTasa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "idTasa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "nrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "referencia"));
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
