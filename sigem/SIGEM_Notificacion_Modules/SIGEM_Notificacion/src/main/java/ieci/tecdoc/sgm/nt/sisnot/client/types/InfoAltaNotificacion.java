/**
 * InfoAltaNotificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client.types;

public class InfoAltaNotificacion  implements java.io.Serializable {
    private java.lang.String asunto;

    private java.lang.String codProcedimiento;

    private java.lang.String codSistemaEmisor;

    private java.lang.String cuerpo;

    private java.lang.String fechaRegistro;

    private java.lang.String nombreArchivo;

    private java.lang.String notificacion64;

    private java.lang.String numExpediente;

    private java.lang.String numRegistro;

    private java.lang.String tipoCorrespondencia;

    public InfoAltaNotificacion() {
    }

    public InfoAltaNotificacion(
           java.lang.String asunto,
           java.lang.String codProcedimiento,
           java.lang.String codSistemaEmisor,
           java.lang.String cuerpo,
           java.lang.String fechaRegistro,
           java.lang.String nombreArchivo,
           java.lang.String notificacion64,
           java.lang.String numExpediente,
           java.lang.String numRegistro,
           java.lang.String tipoCorrespondencia) {
           this.asunto = asunto;
           this.codProcedimiento = codProcedimiento;
           this.codSistemaEmisor = codSistemaEmisor;
           this.cuerpo = cuerpo;
           this.fechaRegistro = fechaRegistro;
           this.nombreArchivo = nombreArchivo;
           this.notificacion64 = notificacion64;
           this.numExpediente = numExpediente;
           this.numRegistro = numRegistro;
           this.tipoCorrespondencia = tipoCorrespondencia;
    }


    /**
     * Gets the asunto value for this InfoAltaNotificacion.
     * 
     * @return asunto
     */
    public java.lang.String getAsunto() {
        return asunto;
    }


    /**
     * Sets the asunto value for this InfoAltaNotificacion.
     * 
     * @param asunto
     */
    public void setAsunto(java.lang.String asunto) {
        this.asunto = asunto;
    }


    /**
     * Gets the codProcedimiento value for this InfoAltaNotificacion.
     * 
     * @return codProcedimiento
     */
    public java.lang.String getCodProcedimiento() {
        return codProcedimiento;
    }


    /**
     * Sets the codProcedimiento value for this InfoAltaNotificacion.
     * 
     * @param codProcedimiento
     */
    public void setCodProcedimiento(java.lang.String codProcedimiento) {
        this.codProcedimiento = codProcedimiento;
    }


    /**
     * Gets the codSistemaEmisor value for this InfoAltaNotificacion.
     * 
     * @return codSistemaEmisor
     */
    public java.lang.String getCodSistemaEmisor() {
        return codSistemaEmisor;
    }


    /**
     * Sets the codSistemaEmisor value for this InfoAltaNotificacion.
     * 
     * @param codSistemaEmisor
     */
    public void setCodSistemaEmisor(java.lang.String codSistemaEmisor) {
        this.codSistemaEmisor = codSistemaEmisor;
    }


    /**
     * Gets the cuerpo value for this InfoAltaNotificacion.
     * 
     * @return cuerpo
     */
    public java.lang.String getCuerpo() {
        return cuerpo;
    }


    /**
     * Sets the cuerpo value for this InfoAltaNotificacion.
     * 
     * @param cuerpo
     */
    public void setCuerpo(java.lang.String cuerpo) {
        this.cuerpo = cuerpo;
    }


    /**
     * Gets the fechaRegistro value for this InfoAltaNotificacion.
     * 
     * @return fechaRegistro
     */
    public java.lang.String getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * Sets the fechaRegistro value for this InfoAltaNotificacion.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(java.lang.String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    /**
     * Gets the nombreArchivo value for this InfoAltaNotificacion.
     * 
     * @return nombreArchivo
     */
    public java.lang.String getNombreArchivo() {
        return nombreArchivo;
    }


    /**
     * Sets the nombreArchivo value for this InfoAltaNotificacion.
     * 
     * @param nombreArchivo
     */
    public void setNombreArchivo(java.lang.String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }


    /**
     * Gets the notificacion64 value for this InfoAltaNotificacion.
     * 
     * @return notificacion64
     */
    public java.lang.String getNotificacion64() {
        return notificacion64;
    }


    /**
     * Sets the notificacion64 value for this InfoAltaNotificacion.
     * 
     * @param notificacion64
     */
    public void setNotificacion64(java.lang.String notificacion64) {
        this.notificacion64 = notificacion64;
    }


    /**
     * Gets the numExpediente value for this InfoAltaNotificacion.
     * 
     * @return numExpediente
     */
    public java.lang.String getNumExpediente() {
        return numExpediente;
    }


    /**
     * Sets the numExpediente value for this InfoAltaNotificacion.
     * 
     * @param numExpediente
     */
    public void setNumExpediente(java.lang.String numExpediente) {
        this.numExpediente = numExpediente;
    }


    /**
     * Gets the numRegistro value for this InfoAltaNotificacion.
     * 
     * @return numRegistro
     */
    public java.lang.String getNumRegistro() {
        return numRegistro;
    }


    /**
     * Sets the numRegistro value for this InfoAltaNotificacion.
     * 
     * @param numRegistro
     */
    public void setNumRegistro(java.lang.String numRegistro) {
        this.numRegistro = numRegistro;
    }


    /**
     * Gets the tipoCorrespondencia value for this InfoAltaNotificacion.
     * 
     * @return tipoCorrespondencia
     */
    public java.lang.String getTipoCorrespondencia() {
        return tipoCorrespondencia;
    }


    /**
     * Sets the tipoCorrespondencia value for this InfoAltaNotificacion.
     * 
     * @param tipoCorrespondencia
     */
    public void setTipoCorrespondencia(java.lang.String tipoCorrespondencia) {
        this.tipoCorrespondencia = tipoCorrespondencia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoAltaNotificacion)) return false;
        InfoAltaNotificacion other = (InfoAltaNotificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.asunto==null && other.getAsunto()==null) || 
             (this.asunto!=null &&
              this.asunto.equals(other.getAsunto()))) &&
            ((this.codProcedimiento==null && other.getCodProcedimiento()==null) || 
             (this.codProcedimiento!=null &&
              this.codProcedimiento.equals(other.getCodProcedimiento()))) &&
            ((this.codSistemaEmisor==null && other.getCodSistemaEmisor()==null) || 
             (this.codSistemaEmisor!=null &&
              this.codSistemaEmisor.equals(other.getCodSistemaEmisor()))) &&
            ((this.cuerpo==null && other.getCuerpo()==null) || 
             (this.cuerpo!=null &&
              this.cuerpo.equals(other.getCuerpo()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            ((this.nombreArchivo==null && other.getNombreArchivo()==null) || 
             (this.nombreArchivo!=null &&
              this.nombreArchivo.equals(other.getNombreArchivo()))) &&
            ((this.notificacion64==null && other.getNotificacion64()==null) || 
             (this.notificacion64!=null &&
              this.notificacion64.equals(other.getNotificacion64()))) &&
            ((this.numExpediente==null && other.getNumExpediente()==null) || 
             (this.numExpediente!=null &&
              this.numExpediente.equals(other.getNumExpediente()))) &&
            ((this.numRegistro==null && other.getNumRegistro()==null) || 
             (this.numRegistro!=null &&
              this.numRegistro.equals(other.getNumRegistro()))) &&
            ((this.tipoCorrespondencia==null && other.getTipoCorrespondencia()==null) || 
             (this.tipoCorrespondencia!=null &&
              this.tipoCorrespondencia.equals(other.getTipoCorrespondencia())));
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
        if (getAsunto() != null) {
            _hashCode += getAsunto().hashCode();
        }
        if (getCodProcedimiento() != null) {
            _hashCode += getCodProcedimiento().hashCode();
        }
        if (getCodSistemaEmisor() != null) {
            _hashCode += getCodSistemaEmisor().hashCode();
        }
        if (getCuerpo() != null) {
            _hashCode += getCuerpo().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
        }
        if (getNombreArchivo() != null) {
            _hashCode += getNombreArchivo().hashCode();
        }
        if (getNotificacion64() != null) {
            _hashCode += getNotificacion64().hashCode();
        }
        if (getNumExpediente() != null) {
            _hashCode += getNumExpediente().hashCode();
        }
        if (getNumRegistro() != null) {
            _hashCode += getNumRegistro().hashCode();
        }
        if (getTipoCorrespondencia() != null) {
            _hashCode += getTipoCorrespondencia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoAltaNotificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "InfoAltaNotificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSistemaEmisor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codSistemaEmisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuerpo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuerpo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreArchivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreArchivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacion64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notificacion64"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numExpediente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numExpediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCorrespondencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCorrespondencia"));
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
