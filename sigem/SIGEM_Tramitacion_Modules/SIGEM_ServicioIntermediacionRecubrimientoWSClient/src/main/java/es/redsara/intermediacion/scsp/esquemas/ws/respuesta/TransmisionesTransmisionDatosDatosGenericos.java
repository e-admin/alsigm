/**
 * TransmisionesTransmisionDatosDatosGenericos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.respuesta;

public class TransmisionesTransmisionDatosDatosGenericos  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Emisor emisor;

    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Solicitante solicitante;

    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Titular titular;

    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Transmision transmision;

    public TransmisionesTransmisionDatosDatosGenericos() {
    }

    public TransmisionesTransmisionDatosDatosGenericos(
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Emisor emisor,
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Solicitante solicitante,
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Titular titular,
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Transmision transmision) {
           this.emisor = emisor;
           this.solicitante = solicitante;
           this.titular = titular;
           this.transmision = transmision;
    }


    /**
     * Gets the emisor value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @return emisor
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Emisor getEmisor() {
        return emisor;
    }


    /**
     * Sets the emisor value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @param emisor
     */
    public void setEmisor(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Emisor emisor) {
        this.emisor = emisor;
    }


    /**
     * Gets the solicitante value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @return solicitante
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Solicitante getSolicitante() {
        return solicitante;
    }


    /**
     * Sets the solicitante value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @param solicitante
     */
    public void setSolicitante(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Solicitante solicitante) {
        this.solicitante = solicitante;
    }


    /**
     * Gets the titular value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @return titular
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Titular getTitular() {
        return titular;
    }


    /**
     * Sets the titular value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @param titular
     */
    public void setTitular(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Titular titular) {
        this.titular = titular;
    }


    /**
     * Gets the transmision value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @return transmision
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Transmision getTransmision() {
        return transmision;
    }


    /**
     * Sets the transmision value for this TransmisionesTransmisionDatosDatosGenericos.
     * 
     * @param transmision
     */
    public void setTransmision(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Transmision transmision) {
        this.transmision = transmision;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransmisionesTransmisionDatosDatosGenericos)) return false;
        TransmisionesTransmisionDatosDatosGenericos other = (TransmisionesTransmisionDatosDatosGenericos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.emisor==null && other.getEmisor()==null) || 
             (this.emisor!=null &&
              this.emisor.equals(other.getEmisor()))) &&
            ((this.solicitante==null && other.getSolicitante()==null) || 
             (this.solicitante!=null &&
              this.solicitante.equals(other.getSolicitante()))) &&
            ((this.titular==null && other.getTitular()==null) || 
             (this.titular!=null &&
              this.titular.equals(other.getTitular()))) &&
            ((this.transmision==null && other.getTransmision()==null) || 
             (this.transmision!=null &&
              this.transmision.equals(other.getTransmision())));
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
        if (getEmisor() != null) {
            _hashCode += getEmisor().hashCode();
        }
        if (getSolicitante() != null) {
            _hashCode += getSolicitante().hashCode();
        }
        if (getTitular() != null) {
            _hashCode += getTitular().hashCode();
        }
        if (getTransmision() != null) {
            _hashCode += getTransmision().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransmisionesTransmisionDatosDatosGenericos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>>Transmisiones>TransmisionDatos>DatosGenericos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Emisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Emisor"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Solicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Solicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titular");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Titular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Titular"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transmision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Transmision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Transmision"));
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
