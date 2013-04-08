/**
 * RegistrarFirmaByEntidadResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class RegistrarFirmaByEntidadResponse  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirmaByEntidadReturn;

    public RegistrarFirmaByEntidadResponse() {
    }

    public RegistrarFirmaByEntidadResponse(
           ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirmaByEntidadReturn) {
           this.registrarFirmaByEntidadReturn = registrarFirmaByEntidadReturn;
    }


    /**
     * Gets the registrarFirmaByEntidadReturn value for this RegistrarFirmaByEntidadResponse.
     * 
     * @return registrarFirmaByEntidadReturn
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma getRegistrarFirmaByEntidadReturn() {
        return registrarFirmaByEntidadReturn;
    }


    /**
     * Sets the registrarFirmaByEntidadReturn value for this RegistrarFirmaByEntidadResponse.
     * 
     * @param registrarFirmaByEntidadReturn
     */
    public void setRegistrarFirmaByEntidadReturn(ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirmaByEntidadReturn) {
        this.registrarFirmaByEntidadReturn = registrarFirmaByEntidadReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrarFirmaByEntidadResponse)) return false;
        RegistrarFirmaByEntidadResponse other = (RegistrarFirmaByEntidadResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registrarFirmaByEntidadReturn==null && other.getRegistrarFirmaByEntidadReturn()==null) || 
             (this.registrarFirmaByEntidadReturn!=null &&
              this.registrarFirmaByEntidadReturn.equals(other.getRegistrarFirmaByEntidadReturn())));
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
        if (getRegistrarFirmaByEntidadReturn() != null) {
            _hashCode += getRegistrarFirmaByEntidadReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrarFirmaByEntidadResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">registrarFirmaByEntidadResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registrarFirmaByEntidadReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "registrarFirmaByEntidadReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "RegistroFirma"));
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
