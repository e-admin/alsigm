/**
 * ValidarFirmaByEntidadResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class ValidarFirmaByEntidadResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaByEntidadReturn;

    public ValidarFirmaByEntidadResponse() {
    }

    public ValidarFirmaByEntidadResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaByEntidadReturn) {
           this.validarFirmaByEntidadReturn = validarFirmaByEntidadReturn;
    }


    /**
     * Gets the validarFirmaByEntidadReturn value for this ValidarFirmaByEntidadResponse.
     * 
     * @return validarFirmaByEntidadReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma getValidarFirmaByEntidadReturn() {
        return validarFirmaByEntidadReturn;
    }


    /**
     * Sets the validarFirmaByEntidadReturn value for this ValidarFirmaByEntidadResponse.
     * 
     * @param validarFirmaByEntidadReturn
     */
    public void setValidarFirmaByEntidadReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaByEntidadReturn) {
        this.validarFirmaByEntidadReturn = validarFirmaByEntidadReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidarFirmaByEntidadResponse)) return false;
        ValidarFirmaByEntidadResponse other = (ValidarFirmaByEntidadResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.validarFirmaByEntidadReturn==null && other.getValidarFirmaByEntidadReturn()==null) || 
             (this.validarFirmaByEntidadReturn!=null &&
              this.validarFirmaByEntidadReturn.equals(other.getValidarFirmaByEntidadReturn())));
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
        if (getValidarFirmaByEntidadReturn() != null) {
            _hashCode += getValidarFirmaByEntidadReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidarFirmaByEntidadResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirmaByEntidadResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validarFirmaByEntidadReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "validarFirmaByEntidadReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ResultadoValidacionFirma"));
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
