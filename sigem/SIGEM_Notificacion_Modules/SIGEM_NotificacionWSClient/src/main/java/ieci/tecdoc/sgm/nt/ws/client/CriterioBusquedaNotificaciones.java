/**
 * CriterioBusquedaNotificaciones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.ws.client;

public class CriterioBusquedaNotificaciones  implements java.io.Serializable {
    private java.lang.String conDetalle;

    private java.lang.String estado;

    private java.lang.String fechaDesde;

    private java.lang.String fechaHasta;

    private java.lang.String nif;

    private java.lang.String notificacion;

    private java.lang.String tipo;

    public CriterioBusquedaNotificaciones() {
    }

    public CriterioBusquedaNotificaciones(
           java.lang.String conDetalle,
           java.lang.String estado,
           java.lang.String fechaDesde,
           java.lang.String fechaHasta,
           java.lang.String nif,
           java.lang.String notificacion,
           java.lang.String tipo) {
           this.conDetalle = conDetalle;
           this.estado = estado;
           this.fechaDesde = fechaDesde;
           this.fechaHasta = fechaHasta;
           this.nif = nif;
           this.notificacion = notificacion;
           this.tipo = tipo;
    }


    /**
     * Gets the conDetalle value for this CriterioBusquedaNotificaciones.
     * 
     * @return conDetalle
     */
    public java.lang.String getConDetalle() {
        return conDetalle;
    }


    /**
     * Sets the conDetalle value for this CriterioBusquedaNotificaciones.
     * 
     * @param conDetalle
     */
    public void setConDetalle(java.lang.String conDetalle) {
        this.conDetalle = conDetalle;
    }


    /**
     * Gets the estado value for this CriterioBusquedaNotificaciones.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this CriterioBusquedaNotificaciones.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaDesde value for this CriterioBusquedaNotificaciones.
     * 
     * @return fechaDesde
     */
    public java.lang.String getFechaDesde() {
        return fechaDesde;
    }


    /**
     * Sets the fechaDesde value for this CriterioBusquedaNotificaciones.
     * 
     * @param fechaDesde
     */
    public void setFechaDesde(java.lang.String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }


    /**
     * Gets the fechaHasta value for this CriterioBusquedaNotificaciones.
     * 
     * @return fechaHasta
     */
    public java.lang.String getFechaHasta() {
        return fechaHasta;
    }


    /**
     * Sets the fechaHasta value for this CriterioBusquedaNotificaciones.
     * 
     * @param fechaHasta
     */
    public void setFechaHasta(java.lang.String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }


    /**
     * Gets the nif value for this CriterioBusquedaNotificaciones.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this CriterioBusquedaNotificaciones.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the notificacion value for this CriterioBusquedaNotificaciones.
     * 
     * @return notificacion
     */
    public java.lang.String getNotificacion() {
        return notificacion;
    }


    /**
     * Sets the notificacion value for this CriterioBusquedaNotificaciones.
     * 
     * @param notificacion
     */
    public void setNotificacion(java.lang.String notificacion) {
        this.notificacion = notificacion;
    }


    /**
     * Gets the tipo value for this CriterioBusquedaNotificaciones.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this CriterioBusquedaNotificaciones.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CriterioBusquedaNotificaciones)) return false;
        CriterioBusquedaNotificaciones other = (CriterioBusquedaNotificaciones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.conDetalle==null && other.getConDetalle()==null) || 
             (this.conDetalle!=null &&
              this.conDetalle.equals(other.getConDetalle()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaDesde==null && other.getFechaDesde()==null) || 
             (this.fechaDesde!=null &&
              this.fechaDesde.equals(other.getFechaDesde()))) &&
            ((this.fechaHasta==null && other.getFechaHasta()==null) || 
             (this.fechaHasta!=null &&
              this.fechaHasta.equals(other.getFechaHasta()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.notificacion==null && other.getNotificacion()==null) || 
             (this.notificacion!=null &&
              this.notificacion.equals(other.getNotificacion()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
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
        if (getConDetalle() != null) {
            _hashCode += getConDetalle().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechaDesde() != null) {
            _hashCode += getFechaDesde().hashCode();
        }
        if (getFechaHasta() != null) {
            _hashCode += getFechaHasta().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getNotificacion() != null) {
            _hashCode += getNotificacion().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CriterioBusquedaNotificaciones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "CriterioBusquedaNotificaciones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conDetalle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "conDetalle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDesde");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "fechaDesde"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaHasta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "fechaHasta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "notificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "tipo"));
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
