/**
 * PeticionSincrona.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class PeticionSincrona  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos atributos;

    private es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision[] solicitudes;

    public PeticionSincrona() {
    }

    public PeticionSincrona(
           es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos atributos,
           es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision[] solicitudes) {
           this.atributos = atributos;
           this.solicitudes = solicitudes;
    }


    /**
     * Gets the atributos value for this PeticionSincrona.
     * 
     * @return atributos
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos getAtributos() {
        return atributos;
    }


    /**
     * Sets the atributos value for this PeticionSincrona.
     * 
     * @param atributos
     */
    public void setAtributos(es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos atributos) {
        this.atributos = atributos;
    }


    /**
     * Gets the solicitudes value for this PeticionSincrona.
     * 
     * @return solicitudes
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision[] getSolicitudes() {
        return solicitudes;
    }


    /**
     * Sets the solicitudes value for this PeticionSincrona.
     * 
     * @param solicitudes
     */
    public void setSolicitudes(es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision[] solicitudes) {
        this.solicitudes = solicitudes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PeticionSincrona)) return false;
        PeticionSincrona other = (PeticionSincrona) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.atributos==null && other.getAtributos()==null) || 
             (this.atributos!=null &&
              this.atributos.equals(other.getAtributos()))) &&
            ((this.solicitudes==null && other.getSolicitudes()==null) || 
             (this.solicitudes!=null &&
              java.util.Arrays.equals(this.solicitudes, other.getSolicitudes())));
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
        if (getAtributos() != null) {
            _hashCode += getAtributos().hashCode();
        }
        if (getSolicitudes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSolicitudes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSolicitudes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PeticionSincrona.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionSincrona"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atributos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Atributos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Atributos"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitudes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Solicitudes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Solicitudes"));
        elemField.setItemQName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "SolicitudTransmision"));
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
