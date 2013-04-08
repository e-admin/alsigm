/**
 * TransmisionesTransmisionDatos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.respuesta;

public class TransmisionesTransmisionDatos  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatosDatosGenericos datosGenericos;

    private java.lang.String datosEspecificos;

    public TransmisionesTransmisionDatos() {
    }

    public TransmisionesTransmisionDatos(
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatosDatosGenericos datosGenericos,
           java.lang.String datosEspecificos) {
           this.datosGenericos = datosGenericos;
           this.datosEspecificos = datosEspecificos;
    }


    /**
     * Gets the datosGenericos value for this TransmisionesTransmisionDatos.
     * 
     * @return datosGenericos
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatosDatosGenericos getDatosGenericos() {
        return datosGenericos;
    }


    /**
     * Sets the datosGenericos value for this TransmisionesTransmisionDatos.
     * 
     * @param datosGenericos
     */
    public void setDatosGenericos(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.TransmisionesTransmisionDatosDatosGenericos datosGenericos) {
        this.datosGenericos = datosGenericos;
    }


    /**
     * Gets the datosEspecificos value for this TransmisionesTransmisionDatos.
     * 
     * @return datosEspecificos
     */
    public java.lang.String getDatosEspecificos() {
        return datosEspecificos;
    }


    /**
     * Sets the datosEspecificos value for this TransmisionesTransmisionDatos.
     * 
     * @param datosEspecificos
     */
    public void setDatosEspecificos(java.lang.String datosEspecificos) {
        this.datosEspecificos = datosEspecificos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransmisionesTransmisionDatos)) return false;
        TransmisionesTransmisionDatos other = (TransmisionesTransmisionDatos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datosGenericos==null && other.getDatosGenericos()==null) || 
             (this.datosGenericos!=null &&
              this.datosGenericos.equals(other.getDatosGenericos()))) &&
            ((this.datosEspecificos==null && other.getDatosEspecificos()==null) || 
             (this.datosEspecificos!=null &&
              this.datosEspecificos.equals(other.getDatosEspecificos())));
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
        if (getDatosGenericos() != null) {
            _hashCode += getDatosGenericos().hashCode();
        }
        if (getDatosEspecificos() != null) {
            _hashCode += getDatosEspecificos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransmisionesTransmisionDatos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>Transmisiones>TransmisionDatos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosGenericos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "DatosGenericos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">>>Transmisiones>TransmisionDatos>DatosGenericos"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.map.es/scsp/esquemas/datosespecificos", "DatosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
        
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
