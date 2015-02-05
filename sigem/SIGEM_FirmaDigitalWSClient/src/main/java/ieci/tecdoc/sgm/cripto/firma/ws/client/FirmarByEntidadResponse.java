/**
 * FirmarByEntidadResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class FirmarByEntidadResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarByEntidadReturn;

    public FirmarByEntidadResponse() {
    }

    public FirmarByEntidadResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarByEntidadReturn) {
           this.firmarByEntidadReturn = firmarByEntidadReturn;
    }


    /**
     * Gets the firmarByEntidadReturn value for this FirmarByEntidadResponse.
     * 
     * @return firmarByEntidadReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Firma getFirmarByEntidadReturn() {
        return firmarByEntidadReturn;
    }


    /**
     * Sets the firmarByEntidadReturn value for this FirmarByEntidadResponse.
     * 
     * @param firmarByEntidadReturn
     */
    public void setFirmarByEntidadReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmarByEntidadReturn) {
        this.firmarByEntidadReturn = firmarByEntidadReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FirmarByEntidadResponse)) return false;
        FirmarByEntidadResponse other = (FirmarByEntidadResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firmarByEntidadReturn==null && other.getFirmarByEntidadReturn()==null) || 
             (this.firmarByEntidadReturn!=null &&
              this.firmarByEntidadReturn.equals(other.getFirmarByEntidadReturn())));
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
        if (getFirmarByEntidadReturn() != null) {
            _hashCode += getFirmarByEntidadReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FirmarByEntidadResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmarByEntidadResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmarByEntidadReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmarByEntidadReturn"));
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
