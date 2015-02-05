/**
 * X509CertificadoInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class X509CertificadoInfo  extends ieci.tecdoc.sgm.cripto.firma.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.Object cert;

    private java.lang.Object certStore;

    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido[] certs;

    private java.lang.Object privKey;

    public X509CertificadoInfo() {
    }

    public X509CertificadoInfo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.Object cert,
           java.lang.Object certStore,
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido[] certs,
           java.lang.Object privKey) {
        super(
            errorCode,
            returnCode);
        this.cert = cert;
        this.certStore = certStore;
        this.certs = certs;
        this.privKey = privKey;
    }


    /**
     * Gets the cert value for this X509CertificadoInfo.
     * 
     * @return cert
     */
    public java.lang.Object getCert() {
        return cert;
    }


    /**
     * Sets the cert value for this X509CertificadoInfo.
     * 
     * @param cert
     */
    public void setCert(java.lang.Object cert) {
        this.cert = cert;
    }


    /**
     * Gets the certStore value for this X509CertificadoInfo.
     * 
     * @return certStore
     */
    public java.lang.Object getCertStore() {
        return certStore;
    }


    /**
     * Sets the certStore value for this X509CertificadoInfo.
     * 
     * @param certStore
     */
    public void setCertStore(java.lang.Object certStore) {
        this.certStore = certStore;
    }


    /**
     * Gets the certs value for this X509CertificadoInfo.
     * 
     * @return certs
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido[] getCerts() {
        return certs;
    }


    /**
     * Sets the certs value for this X509CertificadoInfo.
     * 
     * @param certs
     */
    public void setCerts(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido[] certs) {
        this.certs = certs;
    }


    /**
     * Gets the privKey value for this X509CertificadoInfo.
     * 
     * @return privKey
     */
    public java.lang.Object getPrivKey() {
        return privKey;
    }


    /**
     * Sets the privKey value for this X509CertificadoInfo.
     * 
     * @param privKey
     */
    public void setPrivKey(java.lang.Object privKey) {
        this.privKey = privKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof X509CertificadoInfo)) return false;
        X509CertificadoInfo other = (X509CertificadoInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cert==null && other.getCert()==null) || 
             (this.cert!=null &&
              this.cert.equals(other.getCert()))) &&
            ((this.certStore==null && other.getCertStore()==null) || 
             (this.certStore!=null &&
              this.certStore.equals(other.getCertStore()))) &&
            ((this.certs==null && other.getCerts()==null) || 
             (this.certs!=null &&
              java.util.Arrays.equals(this.certs, other.getCerts()))) &&
            ((this.privKey==null && other.getPrivKey()==null) || 
             (this.privKey!=null &&
              this.privKey.equals(other.getPrivKey())));
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
        if (getCert() != null) {
            _hashCode += getCert().hashCode();
        }
        if (getCertStore() != null) {
            _hashCode += getCertStore().hashCode();
        }
        if (getCerts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCerts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCerts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrivKey() != null) {
            _hashCode += getPrivKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(X509CertificadoInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "X509CertificadoInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cert");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "cert"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certStore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "certStore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "certs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("privKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "privKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
