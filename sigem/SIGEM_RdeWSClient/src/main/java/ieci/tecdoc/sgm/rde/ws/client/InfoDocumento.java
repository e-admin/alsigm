/**
 * InfoDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.rde.ws.client;

public class InfoDocumento  extends ieci.tecdoc.sgm.rde.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String contenidoB64;

    private java.lang.String extension;

    private java.lang.String guid;

    private java.lang.String hash;

    private java.lang.String mimeType;

    public InfoDocumento() {
    }

    public InfoDocumento(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String contenidoB64,
           java.lang.String extension,
           java.lang.String guid,
           java.lang.String hash,
           java.lang.String mimeType) {
        super(
            errorCode,
            returnCode);
        this.contenidoB64 = contenidoB64;
        this.extension = extension;
        this.guid = guid;
        this.hash = hash;
        this.mimeType = mimeType;
    }


    /**
     * Gets the contenidoB64 value for this InfoDocumento.
     * 
     * @return contenidoB64
     */
    public java.lang.String getContenidoB64() {
        return contenidoB64;
    }


    /**
     * Sets the contenidoB64 value for this InfoDocumento.
     * 
     * @param contenidoB64
     */
    public void setContenidoB64(java.lang.String contenidoB64) {
        this.contenidoB64 = contenidoB64;
    }


    /**
     * Gets the extension value for this InfoDocumento.
     * 
     * @return extension
     */
    public java.lang.String getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this InfoDocumento.
     * 
     * @param extension
     */
    public void setExtension(java.lang.String extension) {
        this.extension = extension;
    }


    /**
     * Gets the guid value for this InfoDocumento.
     * 
     * @return guid
     */
    public java.lang.String getGuid() {
        return guid;
    }


    /**
     * Sets the guid value for this InfoDocumento.
     * 
     * @param guid
     */
    public void setGuid(java.lang.String guid) {
        this.guid = guid;
    }


    /**
     * Gets the hash value for this InfoDocumento.
     * 
     * @return hash
     */
    public java.lang.String getHash() {
        return hash;
    }


    /**
     * Sets the hash value for this InfoDocumento.
     * 
     * @param hash
     */
    public void setHash(java.lang.String hash) {
        this.hash = hash;
    }


    /**
     * Gets the mimeType value for this InfoDocumento.
     * 
     * @return mimeType
     */
    public java.lang.String getMimeType() {
        return mimeType;
    }


    /**
     * Sets the mimeType value for this InfoDocumento.
     * 
     * @param mimeType
     */
    public void setMimeType(java.lang.String mimeType) {
        this.mimeType = mimeType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoDocumento)) return false;
        InfoDocumento other = (InfoDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contenidoB64==null && other.getContenidoB64()==null) || 
             (this.contenidoB64!=null &&
              this.contenidoB64.equals(other.getContenidoB64()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.guid==null && other.getGuid()==null) || 
             (this.guid!=null &&
              this.guid.equals(other.getGuid()))) &&
            ((this.hash==null && other.getHash()==null) || 
             (this.hash!=null &&
              this.hash.equals(other.getHash()))) &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType())));
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
        if (getContenidoB64() != null) {
            _hashCode += getContenidoB64().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (getGuid() != null) {
            _hashCode += getGuid().hashCode();
        }
        if (getHash() != null) {
            _hashCode += getHash().hashCode();
        }
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "InfoDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenidoB64");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "contenidoB64"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "guid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "hash"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mimeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "mimeType"));
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
