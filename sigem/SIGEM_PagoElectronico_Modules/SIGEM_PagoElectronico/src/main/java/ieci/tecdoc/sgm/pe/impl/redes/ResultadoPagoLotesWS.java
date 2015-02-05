/**
 * ResultadoPagoLotesWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

import ieci.tecdoc.sgm.pe.impl.redes.ResultadoPagoLotesWS;

public class ResultadoPagoLotesWS  implements java.io.Serializable {
    private int codigoRetorno;

    private java.lang.String idLote;

    public ResultadoPagoLotesWS() {
    }

    public ResultadoPagoLotesWS(
           int codigoRetorno,
           java.lang.String idLote) {
           this.codigoRetorno = codigoRetorno;
           this.idLote = idLote;
    }


    /**
     * Gets the codigoRetorno value for this ResultadoPagoLotesWS.
     * 
     * @return codigoRetorno
     */
    public int getCodigoRetorno() {
        return codigoRetorno;
    }


    /**
     * Sets the codigoRetorno value for this ResultadoPagoLotesWS.
     * 
     * @param codigoRetorno
     */
    public void setCodigoRetorno(int codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }


    /**
     * Gets the idLote value for this ResultadoPagoLotesWS.
     * 
     * @return idLote
     */
    public java.lang.String getIdLote() {
        return idLote;
    }


    /**
     * Sets the idLote value for this ResultadoPagoLotesWS.
     * 
     * @param idLote
     */
    public void setIdLote(java.lang.String idLote) {
        this.idLote = idLote;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoPagoLotesWS)) return false;
        ResultadoPagoLotesWS other = (ResultadoPagoLotesWS) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoRetorno == other.getCodigoRetorno() &&
            ((this.idLote==null && other.getIdLote()==null) || 
             (this.idLote!=null &&
              this.idLote.equals(other.getIdLote())));
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
        _hashCode += getCodigoRetorno();
        if (getIdLote() != null) {
            _hashCode += getIdLote().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultadoPagoLotesWS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "ResultadoPagoLotesWS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRetorno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoRetorno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idLote");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idLote"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
