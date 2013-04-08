/**
 * GetCertInfoByEntidadResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class GetCertInfoByEntidadResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoByEntidadReturn;

    public GetCertInfoByEntidadResponse() {
    }

    public GetCertInfoByEntidadResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoByEntidadReturn) {
           this.getCertInfoByEntidadReturn = getCertInfoByEntidadReturn;
    }


    /**
     * Gets the getCertInfoByEntidadReturn value for this GetCertInfoByEntidadResponse.
     * 
     * @return getCertInfoByEntidadReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getGetCertInfoByEntidadReturn() {
        return getCertInfoByEntidadReturn;
    }


    /**
     * Sets the getCertInfoByEntidadReturn value for this GetCertInfoByEntidadResponse.
     * 
     * @param getCertInfoByEntidadReturn
     */
    public void setGetCertInfoByEntidadReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.X509CertificadoInfo getCertInfoByEntidadReturn) {
        this.getCertInfoByEntidadReturn = getCertInfoByEntidadReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCertInfoByEntidadResponse)) return false;
        GetCertInfoByEntidadResponse other = (GetCertInfoByEntidadResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCertInfoByEntidadReturn==null && other.getGetCertInfoByEntidadReturn()==null) || 
             (this.getCertInfoByEntidadReturn!=null &&
              this.getCertInfoByEntidadReturn.equals(other.getGetCertInfoByEntidadReturn())));
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
        if (getGetCertInfoByEntidadReturn() != null) {
            _hashCode += getGetCertInfoByEntidadReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCertInfoByEntidadResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">getCertInfoByEntidadResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCertInfoByEntidadReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "getCertInfoByEntidadReturn"));
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
