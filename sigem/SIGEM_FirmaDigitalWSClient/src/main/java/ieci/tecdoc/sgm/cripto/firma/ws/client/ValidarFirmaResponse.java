/**
 * ValidarFirmaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class ValidarFirmaResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaReturn;

    public ValidarFirmaResponse() {
    }

    public ValidarFirmaResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaReturn) {
           this.validarFirmaReturn = validarFirmaReturn;
    }


    /**
     * Gets the validarFirmaReturn value for this ValidarFirmaResponse.
     * 
     * @return validarFirmaReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma getValidarFirmaReturn() {
        return validarFirmaReturn;
    }


    /**
     * Sets the validarFirmaReturn value for this ValidarFirmaResponse.
     * 
     * @param validarFirmaReturn
     */
    public void setValidarFirmaReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirmaReturn) {
        this.validarFirmaReturn = validarFirmaReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidarFirmaResponse)) return false;
        ValidarFirmaResponse other = (ValidarFirmaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.validarFirmaReturn==null && other.getValidarFirmaReturn()==null) || 
             (this.validarFirmaReturn!=null &&
              this.validarFirmaReturn.equals(other.getValidarFirmaReturn())));
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
        if (getValidarFirmaReturn() != null) {
            _hashCode += getValidarFirmaReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidarFirmaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">validarFirmaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validarFirmaReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "validarFirmaReturn"));
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
