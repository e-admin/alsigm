/**
 * CriterioBusquedaExpedientes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class CriterioBusquedaExpedientes  implements java.io.Serializable {
    private java.lang.String NIF;

    private java.lang.String estado;

    private java.lang.String fechaDesde;

    private java.lang.String fechaHasta;

    private java.lang.String operadorConsulta;

    public CriterioBusquedaExpedientes() {
    }

    public CriterioBusquedaExpedientes(
           java.lang.String NIF,
           java.lang.String estado,
           java.lang.String fechaDesde,
           java.lang.String fechaHasta,
           java.lang.String operadorConsulta) {
           this.NIF = NIF;
           this.estado = estado;
           this.fechaDesde = fechaDesde;
           this.fechaHasta = fechaHasta;
           this.operadorConsulta = operadorConsulta;
    }


    /**
     * Gets the NIF value for this CriterioBusquedaExpedientes.
     * 
     * @return NIF
     */
    public java.lang.String getNIF() {
        return NIF;
    }


    /**
     * Sets the NIF value for this CriterioBusquedaExpedientes.
     * 
     * @param NIF
     */
    public void setNIF(java.lang.String NIF) {
        this.NIF = NIF;
    }


    /**
     * Gets the estado value for this CriterioBusquedaExpedientes.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this CriterioBusquedaExpedientes.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaDesde value for this CriterioBusquedaExpedientes.
     * 
     * @return fechaDesde
     */
    public java.lang.String getFechaDesde() {
        return fechaDesde;
    }


    /**
     * Sets the fechaDesde value for this CriterioBusquedaExpedientes.
     * 
     * @param fechaDesde
     */
    public void setFechaDesde(java.lang.String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }


    /**
     * Gets the fechaHasta value for this CriterioBusquedaExpedientes.
     * 
     * @return fechaHasta
     */
    public java.lang.String getFechaHasta() {
        return fechaHasta;
    }


    /**
     * Sets the fechaHasta value for this CriterioBusquedaExpedientes.
     * 
     * @param fechaHasta
     */
    public void setFechaHasta(java.lang.String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }


    /**
     * Gets the operadorConsulta value for this CriterioBusquedaExpedientes.
     * 
     * @return operadorConsulta
     */
    public java.lang.String getOperadorConsulta() {
        return operadorConsulta;
    }


    /**
     * Sets the operadorConsulta value for this CriterioBusquedaExpedientes.
     * 
     * @param operadorConsulta
     */
    public void setOperadorConsulta(java.lang.String operadorConsulta) {
        this.operadorConsulta = operadorConsulta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CriterioBusquedaExpedientes)) return false;
        CriterioBusquedaExpedientes other = (CriterioBusquedaExpedientes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.NIF==null && other.getNIF()==null) || 
             (this.NIF!=null &&
              this.NIF.equals(other.getNIF()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaDesde==null && other.getFechaDesde()==null) || 
             (this.fechaDesde!=null &&
              this.fechaDesde.equals(other.getFechaDesde()))) &&
            ((this.fechaHasta==null && other.getFechaHasta()==null) || 
             (this.fechaHasta!=null &&
              this.fechaHasta.equals(other.getFechaHasta()))) &&
            ((this.operadorConsulta==null && other.getOperadorConsulta()==null) || 
             (this.operadorConsulta!=null &&
              this.operadorConsulta.equals(other.getOperadorConsulta())));
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
        if (getNIF() != null) {
            _hashCode += getNIF().hashCode();
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
        if (getOperadorConsulta() != null) {
            _hashCode += getOperadorConsulta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CriterioBusquedaExpedientes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "CriterioBusquedaExpedientes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NIF");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "NIF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDesde");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fechaDesde"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaHasta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fechaHasta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operadorConsulta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "operadorConsulta"));
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
