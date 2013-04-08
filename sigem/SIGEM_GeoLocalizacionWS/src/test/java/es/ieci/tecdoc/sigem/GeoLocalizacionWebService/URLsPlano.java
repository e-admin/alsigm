/**
 * URLsPlano.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class URLsPlano  extends es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String urlGuiaUrbana;

    private java.lang.String urlMapServer;

    public URLsPlano() {
    }

    public URLsPlano(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String urlGuiaUrbana,
           java.lang.String urlMapServer) {
        super(
            errorCode,
            returnCode);
        this.urlGuiaUrbana = urlGuiaUrbana;
        this.urlMapServer = urlMapServer;
    }


    /**
     * Gets the urlGuiaUrbana value for this URLsPlano.
     * 
     * @return urlGuiaUrbana
     */
    public java.lang.String getUrlGuiaUrbana() {
        return urlGuiaUrbana;
    }


    /**
     * Sets the urlGuiaUrbana value for this URLsPlano.
     * 
     * @param urlGuiaUrbana
     */
    public void setUrlGuiaUrbana(java.lang.String urlGuiaUrbana) {
        this.urlGuiaUrbana = urlGuiaUrbana;
    }


    /**
     * Gets the urlMapServer value for this URLsPlano.
     * 
     * @return urlMapServer
     */
    public java.lang.String getUrlMapServer() {
        return urlMapServer;
    }


    /**
     * Sets the urlMapServer value for this URLsPlano.
     * 
     * @param urlMapServer
     */
    public void setUrlMapServer(java.lang.String urlMapServer) {
        this.urlMapServer = urlMapServer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof URLsPlano)) return false;
        URLsPlano other = (URLsPlano) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.urlGuiaUrbana==null && other.getUrlGuiaUrbana()==null) || 
             (this.urlGuiaUrbana!=null &&
              this.urlGuiaUrbana.equals(other.getUrlGuiaUrbana()))) &&
            ((this.urlMapServer==null && other.getUrlMapServer()==null) || 
             (this.urlMapServer!=null &&
              this.urlMapServer.equals(other.getUrlMapServer())));
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
        if (getUrlGuiaUrbana() != null) {
            _hashCode += getUrlGuiaUrbana().hashCode();
        }
        if (getUrlMapServer() != null) {
            _hashCode += getUrlMapServer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(URLsPlano.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "URLsPlano"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urlGuiaUrbana");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "urlGuiaUrbana"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urlMapServer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "urlMapServer"));
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
