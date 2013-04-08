/**
 * RegistrarFirmaByEntidad.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class RegistrarFirmaByEntidad  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature;

    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate;

    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash;

    private java.lang.String idEntidad;

    public RegistrarFirmaByEntidad() {
    }

    public RegistrarFirmaByEntidad(
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature,
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate,
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash,
           java.lang.String idEntidad) {
           this.signature = signature;
           this.certificate = certificate;
           this.hash = hash;
           this.idEntidad = idEntidad;
    }


    /**
     * Gets the signature value for this RegistrarFirmaByEntidad.
     * 
     * @return signature
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido getSignature() {
        return signature;
    }


    /**
     * Sets the signature value for this RegistrarFirmaByEntidad.
     * 
     * @param signature
     */
    public void setSignature(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature) {
        this.signature = signature;
    }


    /**
     * Gets the certificate value for this RegistrarFirmaByEntidad.
     * 
     * @return certificate
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido getCertificate() {
        return certificate;
    }


    /**
     * Sets the certificate value for this RegistrarFirmaByEntidad.
     * 
     * @param certificate
     */
    public void setCertificate(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate) {
        this.certificate = certificate;
    }


    /**
     * Gets the hash value for this RegistrarFirmaByEntidad.
     * 
     * @return hash
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido getHash() {
        return hash;
    }


    /**
     * Sets the hash value for this RegistrarFirmaByEntidad.
     * 
     * @param hash
     */
    public void setHash(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash) {
        this.hash = hash;
    }


    /**
     * Gets the idEntidad value for this RegistrarFirmaByEntidad.
     * 
     * @return idEntidad
     */
    public java.lang.String getIdEntidad() {
        return idEntidad;
    }


    /**
     * Sets the idEntidad value for this RegistrarFirmaByEntidad.
     * 
     * @param idEntidad
     */
    public void setIdEntidad(java.lang.String idEntidad) {
        this.idEntidad = idEntidad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrarFirmaByEntidad)) return false;
        RegistrarFirmaByEntidad other = (RegistrarFirmaByEntidad) obj;
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
            ((this.certificate==null && other.getCertificate()==null) || 
             (this.certificate!=null &&
              this.certificate.equals(other.getCertificate()))) &&
            ((this.hash==null && other.getHash()==null) || 
             (this.hash!=null &&
              this.hash.equals(other.getHash()))) &&
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
        if (getCertificate() != null) {
            _hashCode += getCertificate().hashCode();
        }
        if (getHash() != null) {
            _hashCode += getHash().hashCode();
        }
        if (getIdEntidad() != null) {
            _hashCode += getIdEntidad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrarFirmaByEntidad.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">registrarFirmaByEntidad"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "signature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "certificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "hash"));
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
