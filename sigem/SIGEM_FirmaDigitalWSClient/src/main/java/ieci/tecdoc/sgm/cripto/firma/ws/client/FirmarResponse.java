/**
 * FirmarResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class FirmarResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarReturn;

    public FirmarResponse() {
    }

    public FirmarResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarReturn) {
           this.firmarReturn = firmarReturn;
    }


    /**
     * Gets the firmarReturn value for this FirmarResponse.
     * 
     * @return firmarReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Firma getFirmarReturn() {
        return firmarReturn;
    }


    /**
     * Sets the firmarReturn value for this FirmarResponse.
     * 
     * @param firmarReturn
     */
    public void setFirmarReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarReturn) {
        this.firmarReturn = firmarReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FirmarResponse)) return false;
        FirmarResponse other = (FirmarResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firmarReturn==null && other.getFirmarReturn()==null) || 
             (this.firmarReturn!=null &&
              this.firmarReturn.equals(other.getFirmarReturn())));
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
        if (getFirmarReturn() != null) {
            _hashCode += getFirmarReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FirmarResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmarResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmarReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmarReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firma"));
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
