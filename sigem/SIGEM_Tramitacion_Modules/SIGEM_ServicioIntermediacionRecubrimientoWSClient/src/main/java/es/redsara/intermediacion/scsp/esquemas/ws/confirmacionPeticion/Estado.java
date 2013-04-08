/**
 * Estado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion;

public class Estado  implements java.io.Serializable {
    private java.lang.String codigoEstado;

    private java.lang.String codigoEstadoSecundario;

    private java.lang.String literalError;

    private java.lang.Long tiempoEstimadoRespuesta;

    public Estado() {
    }

    public Estado(
           java.lang.String codigoEstado,
           java.lang.String codigoEstadoSecundario,
           java.lang.String literalError,
           java.lang.Long tiempoEstimadoRespuesta) {
           this.codigoEstado = codigoEstado;
           this.codigoEstadoSecundario = codigoEstadoSecundario;
           this.literalError = literalError;
           this.tiempoEstimadoRespuesta = tiempoEstimadoRespuesta;
    }


    /**
     * Gets the codigoEstado value for this Estado.
     * 
     * @return codigoEstado
     */
    public java.lang.String getCodigoEstado() {
        return codigoEstado;
    }


    /**
     * Sets the codigoEstado value for this Estado.
     * 
     * @param codigoEstado
     */
    public void setCodigoEstado(java.lang.String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }


    /**
     * Gets the codigoEstadoSecundario value for this Estado.
     * 
     * @return codigoEstadoSecundario
     */
    public java.lang.String getCodigoEstadoSecundario() {
        return codigoEstadoSecundario;
    }


    /**
     * Sets the codigoEstadoSecundario value for this Estado.
     * 
     * @param codigoEstadoSecundario
     */
    public void setCodigoEstadoSecundario(java.lang.String codigoEstadoSecundario) {
        this.codigoEstadoSecundario = codigoEstadoSecundario;
    }


    /**
     * Gets the literalError value for this Estado.
     * 
     * @return literalError
     */
    public java.lang.String getLiteralError() {
        return literalError;
    }


    /**
     * Sets the literalError value for this Estado.
     * 
     * @param literalError
     */
    public void setLiteralError(java.lang.String literalError) {
        this.literalError = literalError;
    }


    /**
     * Gets the tiempoEstimadoRespuesta value for this Estado.
     * 
     * @return tiempoEstimadoRespuesta
     */
    public java.lang.Long getTiempoEstimadoRespuesta() {
        return tiempoEstimadoRespuesta;
    }


    /**
     * Sets the tiempoEstimadoRespuesta value for this Estado.
     * 
     * @param tiempoEstimadoRespuesta
     */
    public void setTiempoEstimadoRespuesta(java.lang.Long tiempoEstimadoRespuesta) {
        this.tiempoEstimadoRespuesta = tiempoEstimadoRespuesta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Estado)) return false;
        Estado other = (Estado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoEstado==null && other.getCodigoEstado()==null) || 
             (this.codigoEstado!=null &&
              this.codigoEstado.equals(other.getCodigoEstado()))) &&
            ((this.codigoEstadoSecundario==null && other.getCodigoEstadoSecundario()==null) || 
             (this.codigoEstadoSecundario!=null &&
              this.codigoEstadoSecundario.equals(other.getCodigoEstadoSecundario()))) &&
            ((this.literalError==null && other.getLiteralError()==null) || 
             (this.literalError!=null &&
              this.literalError.equals(other.getLiteralError()))) &&
            ((this.tiempoEstimadoRespuesta==null && other.getTiempoEstimadoRespuesta()==null) || 
             (this.tiempoEstimadoRespuesta!=null &&
              this.tiempoEstimadoRespuesta.equals(other.getTiempoEstimadoRespuesta())));
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
        if (getCodigoEstado() != null) {
            _hashCode += getCodigoEstado().hashCode();
        }
        if (getCodigoEstadoSecundario() != null) {
            _hashCode += getCodigoEstadoSecundario().hashCode();
        }
        if (getLiteralError() != null) {
            _hashCode += getLiteralError().hashCode();
        }
        if (getTiempoEstimadoRespuesta() != null) {
            _hashCode += getTiempoEstimadoRespuesta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Estado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">Estado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "CodigoEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEstadoSecundario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "CodigoEstadoSecundario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("literalError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "LiteralError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tiempoEstimadoRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "TiempoEstimadoRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
