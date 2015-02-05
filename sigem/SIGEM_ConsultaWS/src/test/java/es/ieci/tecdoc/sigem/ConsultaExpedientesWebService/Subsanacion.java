/**
 * Subsanacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class Subsanacion  extends es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String fecha;

    private java.lang.String idDocumento;

    private java.lang.String identificador;

    private java.lang.String identificadorHito;

    private java.lang.String mensajeParaElCiudadano;

    private java.lang.String numeroExpediente;

    public Subsanacion() {
    }

    public Subsanacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String fecha,
           java.lang.String idDocumento,
           java.lang.String identificador,
           java.lang.String identificadorHito,
           java.lang.String mensajeParaElCiudadano,
           java.lang.String numeroExpediente) {
        super(
            errorCode,
            returnCode);
        this.fecha = fecha;
        this.idDocumento = idDocumento;
        this.identificador = identificador;
        this.identificadorHito = identificadorHito;
        this.mensajeParaElCiudadano = mensajeParaElCiudadano;
        this.numeroExpediente = numeroExpediente;
    }


    /**
     * Gets the fecha value for this Subsanacion.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this Subsanacion.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the idDocumento value for this Subsanacion.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this Subsanacion.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }


    /**
     * Gets the identificador value for this Subsanacion.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this Subsanacion.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the identificadorHito value for this Subsanacion.
     * 
     * @return identificadorHito
     */
    public java.lang.String getIdentificadorHito() {
        return identificadorHito;
    }


    /**
     * Sets the identificadorHito value for this Subsanacion.
     * 
     * @param identificadorHito
     */
    public void setIdentificadorHito(java.lang.String identificadorHito) {
        this.identificadorHito = identificadorHito;
    }


    /**
     * Gets the mensajeParaElCiudadano value for this Subsanacion.
     * 
     * @return mensajeParaElCiudadano
     */
    public java.lang.String getMensajeParaElCiudadano() {
        return mensajeParaElCiudadano;
    }


    /**
     * Sets the mensajeParaElCiudadano value for this Subsanacion.
     * 
     * @param mensajeParaElCiudadano
     */
    public void setMensajeParaElCiudadano(java.lang.String mensajeParaElCiudadano) {
        this.mensajeParaElCiudadano = mensajeParaElCiudadano;
    }


    /**
     * Gets the numeroExpediente value for this Subsanacion.
     * 
     * @return numeroExpediente
     */
    public java.lang.String getNumeroExpediente() {
        return numeroExpediente;
    }


    /**
     * Sets the numeroExpediente value for this Subsanacion.
     * 
     * @param numeroExpediente
     */
    public void setNumeroExpediente(java.lang.String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Subsanacion)) return false;
        Subsanacion other = (Subsanacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            ((this.idDocumento==null && other.getIdDocumento()==null) || 
             (this.idDocumento!=null &&
              this.idDocumento.equals(other.getIdDocumento()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.identificadorHito==null && other.getIdentificadorHito()==null) || 
             (this.identificadorHito!=null &&
              this.identificadorHito.equals(other.getIdentificadorHito()))) &&
            ((this.mensajeParaElCiudadano==null && other.getMensajeParaElCiudadano()==null) || 
             (this.mensajeParaElCiudadano!=null &&
              this.mensajeParaElCiudadano.equals(other.getMensajeParaElCiudadano()))) &&
            ((this.numeroExpediente==null && other.getNumeroExpediente()==null) || 
             (this.numeroExpediente!=null &&
              this.numeroExpediente.equals(other.getNumeroExpediente())));
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
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getIdDocumento() != null) {
            _hashCode += getIdDocumento().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getIdentificadorHito() != null) {
            _hashCode += getIdentificadorHito().hashCode();
        }
        if (getMensajeParaElCiudadano() != null) {
            _hashCode += getMensajeParaElCiudadano().hashCode();
        }
        if (getNumeroExpediente() != null) {
            _hashCode += getNumeroExpediente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Subsanacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadorHito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "identificadorHito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeParaElCiudadano");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "mensajeParaElCiudadano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroExpediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "numeroExpediente"));
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
