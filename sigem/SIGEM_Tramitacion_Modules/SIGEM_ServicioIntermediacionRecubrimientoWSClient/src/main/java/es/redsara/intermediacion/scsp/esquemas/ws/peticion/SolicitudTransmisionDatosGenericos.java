/**
 * SolicitudTransmisionDatosGenericos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class SolicitudTransmisionDatosGenericos  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante solicitante;

    private es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular titular;

    public SolicitudTransmisionDatosGenericos() {
    }

    public SolicitudTransmisionDatosGenericos(
           es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante solicitante,
           es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular titular) {
           this.solicitante = solicitante;
           this.titular = titular;
    }


    /**
     * Gets the solicitante value for this SolicitudTransmisionDatosGenericos.
     * 
     * @return solicitante
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante getSolicitante() {
        return solicitante;
    }


    /**
     * Sets the solicitante value for this SolicitudTransmisionDatosGenericos.
     * 
     * @param solicitante
     */
    public void setSolicitante(es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante solicitante) {
        this.solicitante = solicitante;
    }


    /**
     * Gets the titular value for this SolicitudTransmisionDatosGenericos.
     * 
     * @return titular
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular getTitular() {
        return titular;
    }


    /**
     * Sets the titular value for this SolicitudTransmisionDatosGenericos.
     * 
     * @param titular
     */
    public void setTitular(es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular titular) {
        this.titular = titular;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudTransmisionDatosGenericos)) return false;
        SolicitudTransmisionDatosGenericos other = (SolicitudTransmisionDatosGenericos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.solicitante==null && other.getSolicitante()==null) || 
             (this.solicitante!=null &&
              this.solicitante.equals(other.getSolicitante()))) &&
            ((this.titular==null && other.getTitular()==null) || 
             (this.titular!=null &&
              this.titular.equals(other.getTitular())));
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
        if (getSolicitante() != null) {
            _hashCode += getSolicitante().hashCode();
        }
        if (getTitular() != null) {
            _hashCode += getTitular().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudTransmisionDatosGenericos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>SolicitudTransmision>DatosGenericos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Solicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Solicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titular");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Titular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Titular"));
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
