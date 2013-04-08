/**
 * ValidarFirmaByEntidad.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class ValidarFirmaByEntidad  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature;

    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido contenido;

    private java.lang.String idEntidad;

    public ValidarFirmaByEntidad() {
    }

    public ValidarFirmaByEntidad(
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature,
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido contenido,
           java.lang.String idEntidad) {
           this.signature = signature;
           this.contenido = contenido;
           this.idEntidad = idEntidad;
    }


    /**
     * Gets the signature value for this ValidarFirmaByEntidad.
     * 
     * @return signature
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido getSignature() {
        return signature;
    }


    /**
     * Sets the signature value for this ValidarFirmaByEntidad.
     * 
     * @param signature
     */
    public void setSignature(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature) {
        this.signature = signature;
    }


    /**
     * Gets the contenido value for this ValidarFirmaByEntidad.
     * 
     * @return contenido
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido getContenido() {
        return contenido;
    }


    /**
     * Sets the contenido value for this ValidarFirmaByEntidad.
     * 
     * @param contenido
     */
    public void setContenido(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido contenido) {
        this.contenido = contenido;
    }


    /**
     * Gets the idEntidad value for this ValidarFirmaByEntidad.
     * 
     * @return idEntidad
     */
    public java.lang.String getIdEntidad() {
        return idEntidad;
    }


    /**
     * Sets the idEntidad value for this ValidarFirmaByEntidad.
     * 
     * @param idEntidad
     */
    public void setIdEntidad(java.lang.String idEntidad) {
        this.idEntidad = idEntidad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidarFirmaByEntidad)) return false;
        ValidarFirmaByEntidad other = (ValidarFirmaByEntidad) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.signature==null && other.getSignature()==null) || 
             (this.signature!=null &&
              this.signature.equals(other.getSignature()))) &&
            ((this.contenido==null && other.getContenido()==null) || 
             (this.contenido!=null &&
              this.contenido.equals(other.getContenido()))) &&
            ((this.idEntidad==null && other.getIdEntidad()==null) || 
             (this.idEntidad!=null &&
              this.idEntidad.equals(other.getIdEntidad())));
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
        if (getSignature() != null) {
            _hashCode += getSignature().hashCode();
        }
        if (getContenido() != null) {
            _hashCode += getContenido().hashCode();
        }
        if (getIdEntidad() != null) {
            _hashCode += getIdEntidad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidarFirmaByEntidad.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirmaByEntidad"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "contenido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idEntidad"));
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
