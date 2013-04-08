/**
 * ContenedorDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.rde.ws.client;

public class ContenedorDocumento  extends ieci.tecdoc.sgm.rde.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String contentB64;

    private java.lang.String contentSize;

    private java.lang.String extension;

    private java.lang.String guid;

    private java.lang.String hash;

    private java.lang.String timestamp;

    public ContenedorDocumento() {
    }

    public ContenedorDocumento(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String contentB64,
           java.lang.String contentSize,
           java.lang.String extension,
           java.lang.String guid,
           java.lang.String hash,
           java.lang.String timestamp) {
        super(
            errorCode,
            returnCode);
        this.contentB64 = contentB64;
        this.contentSize = contentSize;
        this.extension = extension;
        this.guid = guid;
        this.hash = hash;
        this.timestamp = timestamp;
    }


    /**
     * Gets the contentB64 value for this ContenedorDocumento.
     * 
     * @return contentB64
     */
    public java.lang.String getContentB64() {
        return contentB64;
    }


    /**
     * Sets the contentB64 value for this ContenedorDocumento.
     * 
     * @param contentB64
     */
    public void setContentB64(java.lang.String contentB64) {
        this.contentB64 = contentB64;
    }


    /**
     * Gets the contentSize value for this ContenedorDocumento.
     * 
     * @return contentSize
     */
    public java.lang.String getContentSize() {
        return contentSize;
    }


    /**
     * Sets the contentSize value for this ContenedorDocumento.
     * 
     * @param contentSize
     */
    public void setContentSize(java.lang.String contentSize) {
        this.contentSize = contentSize;
    }


    /**
     * Gets the extension value for this ContenedorDocumento.
     * 
     * @return extension
     */
    public java.lang.String getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ContenedorDocumento.
     * 
     * @param extension
     */
    public void setExtension(java.lang.String extension) {
        this.extension = extension;
    }


    /**
     * Gets the guid value for this ContenedorDocumento.
     * 
     * @return guid
     */
    public java.lang.String getGuid() {
        return guid;
    }


    /**
     * Sets the guid value for this ContenedorDocumento.
     * 
     * @param guid
     */
    public void setGuid(java.lang.String guid) {
        this.guid = guid;
    }


    /**
     * Gets the hash value for this ContenedorDocumento.
     * 
     * @return hash
     */
    public java.lang.String getHash() {
        return hash;
    }


    /**
     * Sets the hash value for this ContenedorDocumento.
     * 
     * @param hash
     */
    public void setHash(java.lang.String hash) {
        this.hash = hash;
    }


    /**
     * Gets the timestamp value for this ContenedorDocumento.
     * 
     * @return timestamp
     */
    public java.lang.String getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this ContenedorDocumento.
     * 
     * @param timestamp
     */
    public void setTimestamp(java.lang.String timestamp) {
        this.timestamp = timestamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContenedorDocumento)) return false;
        ContenedorDocumento other = (ContenedorDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contentB64==null && other.getContentB64()==null) || 
             (this.contentB64!=null &&
              this.contentB64.equals(other.getContentB64()))) &&
            ((this.contentSize==null && other.getContentSize()==null) || 
             (this.contentSize!=null &&
              this.contentSize.equals(other.getContentSize()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.guid==null && other.getGuid()==null) || 
             (this.guid!=null &&
              this.guid.equals(other.getGuid()))) &&
            ((this.hash==null && other.getHash()==null) || 
             (this.hash!=null &&
              this.hash.equals(other.getHash()))) &&
            ((this.timestamp==null && other.getTimestamp()==null) || 
             (this.timestamp!=null &&
              this.timestamp.equals(other.getTimestamp())));
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
        if (getContentB64() != null) {
            _hashCode += getContentB64().hashCode();
        }
        if (getContentSize() != null) {
            _hashCode += getContentSize().hashCode();
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
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContenedorDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "ContenedorDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentB64");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "contentB64"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "contentSize"));
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
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "timestamp"));
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
