/**
 * SolicitudTransmision.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class SolicitudTransmision  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos datosGenericos;

    private java.lang.Object datosEspecificos;

    private java.lang.Object datosEspecificos2;

    private java.lang.Object datosEspecificos3;

    public SolicitudTransmision() {
    }

    public SolicitudTransmision(
           es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos datosGenericos,
           java.lang.Object datosEspecificos,
           java.lang.Object datosEspecificos2,
           java.lang.Object datosEspecificos3) {
           this.datosGenericos = datosGenericos;
           this.datosEspecificos = datosEspecificos;
           this.datosEspecificos2 = datosEspecificos2;
           this.datosEspecificos3 = datosEspecificos3;
    }


    /**
     * Gets the datosGenericos value for this SolicitudTransmision.
     * 
     * @return datosGenericos
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos getDatosGenericos() {
        return datosGenericos;
    }


    /**
     * Sets the datosGenericos value for this SolicitudTransmision.
     * 
     * @param datosGenericos
     */
    public void setDatosGenericos(es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos datosGenericos) {
        this.datosGenericos = datosGenericos;
    }


    /**
     * Gets the datosEspecificos value for this SolicitudTransmision.
     * 
     * @return datosEspecificos
     */
    public java.lang.Object getDatosEspecificos() {
        return datosEspecificos;
    }


    /**
     * Sets the datosEspecificos value for this SolicitudTransmision.
     * 
     * @param datosEspecificos
     */
    public void setDatosEspecificos(java.lang.Object datosEspecificos) {
        this.datosEspecificos = datosEspecificos;
    }


    /**
     * Gets the datosEspecificos2 value for this SolicitudTransmision.
     * 
     * @return datosEspecificos2
     */
    public java.lang.Object getDatosEspecificos2() {
        return datosEspecificos2;
    }


    /**
     * Sets the datosEspecificos2 value for this SolicitudTransmision.
     * 
     * @param datosEspecificos2
     */
    public void setDatosEspecificos2(java.lang.Object datosEspecificos2) {
        this.datosEspecificos2 = datosEspecificos2;
    }


    /**
     * Gets the datosEspecificos3 value for this SolicitudTransmision.
     * 
     * @return datosEspecificos3
     */
    public java.lang.Object getDatosEspecificos3() {
        return datosEspecificos3;
    }


    /**
     * Sets the datosEspecificos3 value for this SolicitudTransmision.
     * 
     * @param datosEspecificos3
     */
    public void setDatosEspecificos3(java.lang.Object datosEspecificos3) {
        this.datosEspecificos3 = datosEspecificos3;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudTransmision)) return false;
        SolicitudTransmision other = (SolicitudTransmision) obj;
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
              this.datosEspecificos.equals(other.getDatosEspecificos()))) &&
            ((this.datosEspecificos2==null && other.getDatosEspecificos2()==null) || 
             (this.datosEspecificos2!=null &&
              this.datosEspecificos2.equals(other.getDatosEspecificos2()))) &&
            ((this.datosEspecificos3==null && other.getDatosEspecificos3()==null) || 
             (this.datosEspecificos3!=null &&
              this.datosEspecificos3.equals(other.getDatosEspecificos3())));
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
        if (getDatosEspecificos2() != null) {
            _hashCode += getDatosEspecificos2().hashCode();
        }
        if (getDatosEspecificos3() != null) {
            _hashCode += getDatosEspecificos3().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudTransmision.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">SolicitudTransmision"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosGenericos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "DatosGenericos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>SolicitudTransmision>DatosGenericos"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">>SolicitudTransmision>DatosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.map.es/scsp/esquemas/datosespecificos", ">>SolicitudTransmision>DatosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/datosespecificos", ">>SolicitudTransmision>DatosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
