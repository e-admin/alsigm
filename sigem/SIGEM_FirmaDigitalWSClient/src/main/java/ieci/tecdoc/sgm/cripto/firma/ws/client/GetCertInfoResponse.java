/**
 * GetCertInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class GetCertInfoResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoReturn;

    public GetCertInfoResponse() {
    }

    public GetCertInfoResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoReturn) {
           this.getCertInfoReturn = getCertInfoReturn;
    }


    /**
     * Gets the getCertInfoReturn value for this GetCertInfoResponse.
     * 
     * @return getCertInfoReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getGetCertInfoReturn() {
        return getCertInfoReturn;
    }


    /**
     * Sets the getCertInfoReturn value for this GetCertInfoResponse.
     * 
     * @param getCertInfoReturn
     */
    public void setGetCertInfoReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoReturn) {
        this.getCertInfoReturn = getCertInfoReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCertInfoResponse)) return false;
        GetCertInfoResponse other = (GetCertInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCertInfoReturn==null && other.getGetCertInfoReturn()==null) || 
             (this.getCertInfoReturn!=null &&
              this.getCertInfoReturn.equals(other.getGetCertInfoReturn())));
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
        if (getGetCertInfoReturn() != null) {
            _hashCode += getGetCertInfoReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCertInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">getCertInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCertInfoReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "getCertInfoReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "X509CertificadoInfo"));
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
