/**
 * PeticionPdf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class PeticionPdf  implements java.io.Serializable {
    private java.lang.String idPeticion;

    private java.lang.String idTransmision;

    public PeticionPdf() {
    }

    public PeticionPdf(
           java.lang.String idPeticion,
           java.lang.String idTransmision) {
           this.idPeticion = idPeticion;
           this.idTransmision = idTransmision;
    }


    /**
     * Gets the idPeticion value for this PeticionPdf.
     * 
     * @return idPeticion
     */
    public java.lang.String getIdPeticion() {
        return idPeticion;
    }


    /**
     * Sets the idPeticion value for this PeticionPdf.
     * 
     * @param idPeticion
     */
    public void setIdPeticion(java.lang.String idPeticion) {
        this.idPeticion = idPeticion;
    }


    /**
     * Gets the idTransmision value for this PeticionPdf.
     * 
     * @return idTransmision
     */
    public java.lang.String getIdTransmision() {
        return idTransmision;
    }


    /**
     * Sets the idTransmision value for this PeticionPdf.
     * 
     * @param idTransmision
     */
    public void setIdTransmision(java.lang.String idTransmision) {
        this.idTransmision = idTransmision;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PeticionPdf)) return false;
        PeticionPdf other = (PeticionPdf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idPeticion==null && other.getIdPeticion()==null) || 
             (this.idPeticion!=null &&
              this.idPeticion.equals(other.getIdPeticion()))) &&
            ((this.idTransmision==null && other.getIdTransmision()==null) || 
             (this.idTransmision!=null &&
              this.idTransmision.equals(other.getIdTransmision())));
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
        if (getIdPeticion() != null) {
            _hashCode += getIdPeticion().hashCode();
        }
        if (getIdTransmision() != null) {
            _hashCode += getIdTransmision().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PeticionPdf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">PeticionPdf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPeticion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "IdPeticion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTransmision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "IdTransmision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
