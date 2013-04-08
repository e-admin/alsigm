/**
 * Expediente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class Expediente  extends es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String aportacion;

    private java.lang.String codigoPresentacion;

    private java.lang.String estado;

    private java.lang.String fechaInicio;

    private java.lang.String fechaRegistro;

    private java.lang.String informacionAuxiliar;

    private java.lang.String nif;

    private java.lang.String notificacion;

    private java.lang.String numero;

    private java.lang.String numeroRegistro;

    private java.lang.String procedimiento;

    public Expediente() {
    }

    public Expediente(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String aportacion,
           java.lang.String codigoPresentacion,
           java.lang.String estado,
           java.lang.String fechaInicio,
           java.lang.String fechaRegistro,
           java.lang.String informacionAuxiliar,
           java.lang.String nif,
           java.lang.String notificacion,
           java.lang.String numero,
           java.lang.String numeroRegistro,
           java.lang.String procedimiento) {
        super(
            errorCode,
            returnCode);
        this.aportacion = aportacion;
        this.codigoPresentacion = codigoPresentacion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaRegistro = fechaRegistro;
        this.informacionAuxiliar = informacionAuxiliar;
        this.nif = nif;
        this.notificacion = notificacion;
        this.numero = numero;
        this.numeroRegistro = numeroRegistro;
        this.procedimiento = procedimiento;
    }


    /**
     * Gets the aportacion value for this Expediente.
     * 
     * @return aportacion
     */
    public java.lang.String getAportacion() {
        return aportacion;
    }


    /**
     * Sets the aportacion value for this Expediente.
     * 
     * @param aportacion
     */
    public void setAportacion(java.lang.String aportacion) {
        this.aportacion = aportacion;
    }


    /**
     * Gets the codigoPresentacion value for this Expediente.
     * 
     * @return codigoPresentacion
     */
    public java.lang.String getCodigoPresentacion() {
        return codigoPresentacion;
    }


    /**
     * Sets the codigoPresentacion value for this Expediente.
     * 
     * @param codigoPresentacion
     */
    public void setCodigoPresentacion(java.lang.String codigoPresentacion) {
        this.codigoPresentacion = codigoPresentacion;
    }


    /**
     * Gets the estado value for this Expediente.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Expediente.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaInicio value for this Expediente.
     * 
     * @return fechaInicio
     */
    public java.lang.String getFechaInicio() {
        return fechaInicio;
    }


    /**
     * Sets the fechaInicio value for this Expediente.
     * 
     * @param fechaInicio
     */
    public void setFechaInicio(java.lang.String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    /**
     * Gets the fechaRegistro value for this Expediente.
     * 
     * @return fechaRegistro
     */
    public java.lang.String getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * Sets the fechaRegistro value for this Expediente.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(java.lang.String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    /**
     * Gets the informacionAuxiliar value for this Expediente.
     * 
     * @return informacionAuxiliar
     */
    public java.lang.String getInformacionAuxiliar() {
        return informacionAuxiliar;
    }


    /**
     * Sets the informacionAuxiliar value for this Expediente.
     * 
     * @param informacionAuxiliar
     */
    public void setInformacionAuxiliar(java.lang.String informacionAuxiliar) {
        this.informacionAuxiliar = informacionAuxiliar;
    }


    /**
     * Gets the nif value for this Expediente.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this Expediente.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the notificacion value for this Expediente.
     * 
     * @return notificacion
     */
    public java.lang.String getNotificacion() {
        return notificacion;
    }


    /**
     * Sets the notificacion value for this Expediente.
     * 
     * @param notificacion
     */
    public void setNotificacion(java.lang.String notificacion) {
        this.notificacion = notificacion;
    }


    /**
     * Gets the numero value for this Expediente.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this Expediente.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }


    /**
     * Gets the numeroRegistro value for this Expediente.
     * 
     * @return numeroRegistro
     */
    public java.lang.String getNumeroRegistro() {
        return numeroRegistro;
    }


    /**
     * Sets the numeroRegistro value for this Expediente.
     * 
     * @param numeroRegistro
     */
    public void setNumeroRegistro(java.lang.String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }


    /**
     * Gets the procedimiento value for this Expediente.
     * 
     * @return procedimiento
     */
    public java.lang.String getProcedimiento() {
        return procedimiento;
    }


    /**
     * Sets the procedimiento value for this Expediente.
     * 
     * @param procedimiento
     */
    public void setProcedimiento(java.lang.String procedimiento) {
        this.procedimiento = procedimiento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Expediente)) return false;
        Expediente other = (Expediente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.aportacion==null && other.getAportacion()==null) || 
             (this.aportacion!=null &&
              this.aportacion.equals(other.getAportacion()))) &&
            ((this.codigoPresentacion==null && other.getCodigoPresentacion()==null) || 
             (this.codigoPresentacion!=null &&
              this.codigoPresentacion.equals(other.getCodigoPresentacion()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaInicio==null && other.getFechaInicio()==null) || 
             (this.fechaInicio!=null &&
              this.fechaInicio.equals(other.getFechaInicio()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            ((this.informacionAuxiliar==null && other.getInformacionAuxiliar()==null) || 
             (this.informacionAuxiliar!=null &&
              this.informacionAuxiliar.equals(other.getInformacionAuxiliar()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.notificacion==null && other.getNotificacion()==null) || 
             (this.notificacion!=null &&
              this.notificacion.equals(other.getNotificacion()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero()))) &&
            ((this.numeroRegistro==null && other.getNumeroRegistro()==null) || 
             (this.numeroRegistro!=null &&
              this.numeroRegistro.equals(other.getNumeroRegistro()))) &&
            ((this.procedimiento==null && other.getProcedimiento()==null) || 
             (this.procedimiento!=null &&
              this.procedimiento.equals(other.getProcedimiento())));
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
        if (getAportacion() != null) {
            _hashCode += getAportacion().hashCode();
        }
        if (getCodigoPresentacion() != null) {
            _hashCode += getCodigoPresentacion().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechaInicio() != null) {
            _hashCode += getFechaInicio().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
        }
        if (getInformacionAuxiliar() != null) {
            _hashCode += getInformacionAuxiliar().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getNotificacion() != null) {
            _hashCode += getNotificacion().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        if (getNumeroRegistro() != null) {
            _hashCode += getNumeroRegistro().hashCode();
        }
        if (getProcedimiento() != null) {
            _hashCode += getProcedimiento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Expediente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expediente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aportacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "aportacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPresentacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "codigoPresentacion"));
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
        elemField.setFieldName("fechaInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fechaInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informacionAuxiliar");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "informacionAuxiliar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "notificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "numeroRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "procedimiento"));
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
