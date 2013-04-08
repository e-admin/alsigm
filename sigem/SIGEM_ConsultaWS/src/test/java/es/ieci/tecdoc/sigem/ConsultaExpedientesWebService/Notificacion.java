/**
 * Notificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class Notificacion  extends es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String descripcion;

    private java.lang.String deu;

    private java.lang.String expediente;

    private java.lang.String fechaNotificacion;

    private java.lang.String hitoId;

    private java.lang.String notificacionId;

    private java.lang.String servicioNotificionesId;

    public Notificacion() {
    }

    public Notificacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String descripcion,
           java.lang.String deu,
           java.lang.String expediente,
           java.lang.String fechaNotificacion,
           java.lang.String hitoId,
           java.lang.String notificacionId,
           java.lang.String servicioNotificionesId) {
        super(
            errorCode,
            returnCode);
        this.descripcion = descripcion;
        this.deu = deu;
        this.expediente = expediente;
        this.fechaNotificacion = fechaNotificacion;
        this.hitoId = hitoId;
        this.notificacionId = notificacionId;
        this.servicioNotificionesId = servicioNotificionesId;
    }


    /**
     * Gets the descripcion value for this Notificacion.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this Notificacion.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the deu value for this Notificacion.
     * 
     * @return deu
     */
    public java.lang.String getDeu() {
        return deu;
    }


    /**
     * Sets the deu value for this Notificacion.
     * 
     * @param deu
     */
    public void setDeu(java.lang.String deu) {
        this.deu = deu;
    }


    /**
     * Gets the expediente value for this Notificacion.
     * 
     * @return expediente
     */
    public java.lang.String getExpediente() {
        return expediente;
    }


    /**
     * Sets the expediente value for this Notificacion.
     * 
     * @param expediente
     */
    public void setExpediente(java.lang.String expediente) {
        this.expediente = expediente;
    }


    /**
     * Gets the fechaNotificacion value for this Notificacion.
     * 
     * @return fechaNotificacion
     */
    public java.lang.String getFechaNotificacion() {
        return fechaNotificacion;
    }


    /**
     * Sets the fechaNotificacion value for this Notificacion.
     * 
     * @param fechaNotificacion
     */
    public void setFechaNotificacion(java.lang.String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }


    /**
     * Gets the hitoId value for this Notificacion.
     * 
     * @return hitoId
     */
    public java.lang.String getHitoId() {
        return hitoId;
    }


    /**
     * Sets the hitoId value for this Notificacion.
     * 
     * @param hitoId
     */
    public void setHitoId(java.lang.String hitoId) {
        this.hitoId = hitoId;
    }


    /**
     * Gets the notificacionId value for this Notificacion.
     * 
     * @return notificacionId
     */
    public java.lang.String getNotificacionId() {
        return notificacionId;
    }


    /**
     * Sets the notificacionId value for this Notificacion.
     * 
     * @param notificacionId
     */
    public void setNotificacionId(java.lang.String notificacionId) {
        this.notificacionId = notificacionId;
    }


    /**
     * Gets the servicioNotificionesId value for this Notificacion.
     * 
     * @return servicioNotificionesId
     */
    public java.lang.String getServicioNotificionesId() {
        return servicioNotificionesId;
    }


    /**
     * Sets the servicioNotificionesId value for this Notificacion.
     * 
     * @param servicioNotificionesId
     */
    public void setServicioNotificionesId(java.lang.String servicioNotificionesId) {
        this.servicioNotificionesId = servicioNotificionesId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notificacion)) return false;
        Notificacion other = (Notificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.deu==null && other.getDeu()==null) || 
             (this.deu!=null &&
              this.deu.equals(other.getDeu()))) &&
            ((this.expediente==null && other.getExpediente()==null) || 
             (this.expediente!=null &&
              this.expediente.equals(other.getExpediente()))) &&
            ((this.fechaNotificacion==null && other.getFechaNotificacion()==null) || 
             (this.fechaNotificacion!=null &&
              this.fechaNotificacion.equals(other.getFechaNotificacion()))) &&
            ((this.hitoId==null && other.getHitoId()==null) || 
             (this.hitoId!=null &&
              this.hitoId.equals(other.getHitoId()))) &&
            ((this.notificacionId==null && other.getNotificacionId()==null) || 
             (this.notificacionId!=null &&
              this.notificacionId.equals(other.getNotificacionId()))) &&
            ((this.servicioNotificionesId==null && other.getServicioNotificionesId()==null) || 
             (this.servicioNotificionesId!=null &&
              this.servicioNotificionesId.equals(other.getServicioNotificionesId())));
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
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getDeu() != null) {
            _hashCode += getDeu().hashCode();
        }
        if (getExpediente() != null) {
            _hashCode += getExpediente().hashCode();
        }
        if (getFechaNotificacion() != null) {
            _hashCode += getFechaNotificacion().hashCode();
        }
        if (getHitoId() != null) {
            _hashCode += getHitoId().hashCode();
        }
        if (getNotificacionId() != null) {
            _hashCode += getNotificacionId().hashCode();
        }
        if (getServicioNotificionesId() != null) {
            _hashCode += getServicioNotificionesId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Notificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "deu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "expediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaNotificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fechaNotificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hitoId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "hitoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "notificacionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicioNotificionesId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "servicioNotificionesId"));
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
